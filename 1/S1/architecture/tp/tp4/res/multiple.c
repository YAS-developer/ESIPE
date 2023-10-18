/*
Simple X11 buffer display

Adapted from https://nakst.gitlab.io/tutorial/ui-part-6.html
*/

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define Window X11Window
#include <X11/Xatom.h>
#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <X11/cursorfont.h>
#undef Window

/////////////////////////////////////////
// From asm file
/////////////////////////////////////////

extern const uint32_t frameBuffer;
extern const int bufferWidth;
extern const int bufferHeight;
void UpdateBuffer(void);

extern const int frequency; // 0 si pas de rafraichissement

/////////////////////////////////////////
// Definitions.
/////////////////////////////////////////

typedef struct Rectangle {
    int l, r, t, b;
} Rectangle;

typedef struct Painter {
    Rectangle clip;
    uint32_t *bits;
    int width, height;
} Painter;

typedef struct Window {
    uint32_t *bits;
    int width, height;
    Rectangle updateRegion;

    X11Window window;
    XImage *image;
} Window;

typedef struct GlobalState {
    Window *window;
    Display *display;
    Visual *visual;
    Atom windowClosedID;
} GlobalState;

static const uint32_t *buffer = &frameBuffer; // alias
static void Initialise();
static int MessageLoop();

static Window *WindowCreate(const char *cTitle, int width, int height);

static Rectangle RectangleMake(int l, int r, int t, int b);
static Rectangle RectangleIntersection(Rectangle a, Rectangle b);

static void DrawBlock(Painter *painter, Rectangle r, uint32_t fill);
static void DrawBuffer(Painter *painter);

static void Update(bool clear);

/////////////////////////////////////////
// Helper functions.
/////////////////////////////////////////

Rectangle RectangleMake(int l, int r, int t, int b) {
    return (Rectangle){.l = l, .r = r, .t = t, .b = b};
}

Rectangle RectangleIntersection(Rectangle a, Rectangle b) {
    if (a.l < b.l)
        a.l = b.l;
    if (a.t < b.t)
        a.t = b.t;
    if (a.r > b.r)
        a.r = b.r;
    if (a.b > b.b)
        a.b = b.b;
    return a;
}

/////////////////////////////////////////
// Painting.
/////////////////////////////////////////

void DrawBlock(Painter *painter, Rectangle rectangle, uint32_t color) {
    rectangle = RectangleIntersection(painter->clip, rectangle);

    for (int y = rectangle.t; y < rectangle.b; y++) {
        for (int x = rectangle.l; x < rectangle.r; x++) {
            painter->bits[y * painter->width + x] = color;
        }
    }
}

void DrawBuffer(Painter *painter) {
    int w_ratio = painter->width / bufferWidth;
    int h_ratio = painter->height / bufferHeight;

    if (w_ratio < 1)
        w_ratio = 1;
    if (h_ratio < 1)
        h_ratio = 1;

    Rectangle rectangle = RectangleMake(0, bufferWidth * w_ratio, 0, bufferHeight * h_ratio);
    rectangle = RectangleIntersection(rectangle, painter->clip);

    for (int y = rectangle.t; y < rectangle.b; y++) {
        int bufy = y / h_ratio;
        for (int x = rectangle.l; x < rectangle.r; x++) {
            int bufx = x / w_ratio;
            painter->bits[y * painter->width + x] = buffer[bufy * bufferWidth + bufx];
        }
    }
}

/////////////////////////////////////////
// Core user interface logic.
/////////////////////////////////////////

static void WindowEndPaint(Window *window, Painter *painter);

GlobalState global;

void Update(bool clear) {
    Window *window = global.window;

    Painter painter;
    painter.bits = window->bits;
    painter.width = window->width;
    painter.height = window->height;
    painter.clip = RectangleMake(0, window->width, 0, window->height);

    // background
    if (clear)
        DrawBlock(&painter, painter.clip, 0);

    // buffer
    DrawBuffer(&painter);

    WindowEndPaint(window, &painter);
}

/////////////////////////////////////////
// Platform specific code.
/////////////////////////////////////////

#include <math.h>
#include <sys/select.h>

/*
XNextEvent with timeout
Source: https://stackoverflow.com/a/32551161
*/
static int wait_fd(int fd, double seconds) {
    struct timeval tv;
    fd_set in_fds;
    FD_ZERO(&in_fds);
    FD_SET(fd, &in_fds);
    tv.tv_sec = trunc(seconds);
    tv.tv_usec = (seconds - trunc(seconds)) * 1000000;
    return select(fd + 1, &in_fds, 0, 0, &tv);
}

static int XNextEventTimeout(Display *display, XEvent *event, double seconds) {
    if (XPending(display) || seconds < 0 || wait_fd(ConnectionNumber(display), seconds)) {
        XNextEvent(display, event);
        return 0;
    } else {
        return 1;
    }
}

void WindowEndPaint(Window *window, Painter *painter) {
    (void)painter;

    XPutImage(global.display, window->window, DefaultGC(global.display, 0), window->image, 0, 0, 0, 0, window->width,
              window->height);
}

Window *WindowCreate(const char *cTitle, int width, int height) {
    if (global.window != NULL)
        return NULL;
    Window *window = (Window *)calloc(1, sizeof(Window));
    global.window = window;

    XSetWindowAttributes attributes = {};
    window->window = XCreateWindow(global.display, DefaultRootWindow(global.display), 0, 0, width, height, 0, 0,
                                   InputOutput, CopyFromParent, CWOverrideRedirect, &attributes);
    XStoreName(global.display, window->window, cTitle);
    XSelectInput(global.display, window->window, StructureNotifyMask);
    XMapRaised(global.display, window->window);
    XSetWMProtocols(global.display, window->window, &global.windowClosedID, 1);
    window->image = XCreateImage(global.display, global.visual, 24, ZPixmap, 0, NULL, 10, 10, 32, 0);
    return window;
}

static time_t GetTimeMillisecond() {
    struct timespec tp;
    clock_gettime(CLOCK_REALTIME, &tp);
    return (tp.tv_sec * 1000) + (tp.tv_nsec / 1000000);
}

int MessageLoop() {
    UpdateBuffer();
    Update(false);

    time_t delay = frequency ? 1000 / frequency : -1;
    double fDelay = delay / 1000.f;
    time_t start = GetTimeMillisecond();

    while (true) {
        bool clear = false;
        XEvent event;
        if (!XNextEventTimeout(global.display, &event, fDelay)) {

            if (event.type == ClientMessage && (Atom)event.xclient.data.l[0] == global.windowClosedID) {
                break;
            } else if (event.type == Expose) {
                if (event.xexpose.window != global.window->window)
                    continue;
                XPutImage(global.display, global.window->window, DefaultGC(global.display, 0), global.window->image, 0,
                          0, 0, 0, global.window->width, global.window->height);
            } else if (event.type == ConfigureNotify) {
                if (event.xconfigure.window != global.window->window)
                    continue;
                Window *window = global.window;

                if (window->width != event.xconfigure.width || window->height != event.xconfigure.height) {
                    window->width = event.xconfigure.width;
                    window->height = event.xconfigure.height;
                    window->bits = (uint32_t *)realloc(window->bits, window->width * window->height * 4);
                    window->image->width = window->width;
                    window->image->height = window->height;
                    window->image->bytes_per_line = window->width * 4;
                    window->image->data = (char *)window->bits;
                    clear = true;
                }
            }
        }
        time_t current = GetTimeMillisecond();
        if (clear || delay < 0 || start + delay < current) {
            // Steps
            for (; delay > 0 && start + delay < current; start += delay)
                UpdateBuffer();
            // Draw
            Update(clear);
        }
    }

    free(global.window->bits);
    free(global.window);

    return 0;
}

void Initialise() {
    global.display = XOpenDisplay(NULL);
    global.visual = XDefaultVisual(global.display, 0);
    global.windowClosedID = XInternAtom(global.display, "WM_DELETE_WINDOW", 0);
    global.window = NULL;
}

/////////////////////////////////////////
// Main loop
/////////////////////////////////////////

#include <stdio.h>

int main() {
    Initialise();
    WindowCreate("Hello, world", bufferWidth, bufferHeight);
    return MessageLoop();
}
