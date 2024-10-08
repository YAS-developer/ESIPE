package PatternMatching;

// Définition de l'interface scellée
sealed interface Shape permits Circle, Rectangle, Triangle {
    double area();
}

// Classes concrètes implémentant l'interface scellée
final class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

final class Rectangle implements Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}

final class Triangle implements Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }
}

public class PatternMatchingExample {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 4)
        };

        for (Shape shape : shapes) {
            String description = switch (shape) {
                case Circle c -> "Cercle de rayon";
                case Rectangle r -> "Rectangle de dimensions ";
                case Triangle t -> "Triangle de base ";
            };
            System.out.println(description + " - Aire: " + shape.area());
        }
    }
}
