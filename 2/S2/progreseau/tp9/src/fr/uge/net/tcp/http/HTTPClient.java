package fr.uge.net.tcp.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.logging.Logger;

public class HTTPClient {
    private static final Logger logger = Logger.getLogger(HTTPClient.class.getName());
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: HTTPClient server resource");
            System.out.println("Example: HTTPClient www.example.com /index.html");
            return;
        }
        String server = args[0];
        String resource = args[1];

        try {
            String content = getResource(server, resource);
            System.out.println("Response content:");
            System.out.println(content);
        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Se connecte au serveur, envoie une requête GET pour la ressource spécifiée,
     * lit la réponse et renvoie le corps décodé (si c'est du HTML). 
     * En cas de redirection (301, 302), le client suit la nouvelle adresse.
     */
    private static String getResource(String server, String resource) throws IOException, URISyntaxException {
        var serverAddress = new InetSocketAddress(server, 80);
        try (var sc = SocketChannel.open()) {
            sc.connect(serverAddress);
            var request = "GET " + resource + " HTTP/1.1\r\n" +
                             "Host: " + server + "\r\n" +
                             "Connection: close\r\n\r\n";
            sc.write(UTF8.encode(request));

            var reader = new HTTPReader(sc, ByteBuffer.allocate(1024));
            var header = reader.readHeader();
            logger.info("Received header: " + header);

            if (header.getCode() == 301 || header.getCode() == 302) {
                var location = header.getFields().get("location");
                if (location == null) {
                    throw new IOException("Redirection response without location header.");
                }
                var uri = new URI(location);
                var newHost = uri.getHost();
                var newResource = uri.getPath();
                if (newHost == null || newResource == null) {
                    throw new IOException("Invalid Location header: " + location);
                }
                logger.info("Redirecting to " + location);
                return getResource(newHost, newResource);
            }
            
            Optional<String> contentTypeOpt = header.getContentType();
            if (contentTypeOpt.isPresent() && !contentTypeOpt.get().toLowerCase().contains("html")) {
                return "Content-Type is not HTML: " + contentTypeOpt.get();
            }

            ByteBuffer body;
            if (header.isChunkedTransfer()) {
                body = reader.readChunks();
            } else {
                var contentLength = header.getContentLength();
                if (contentLength < 0) {
                    throw new IOException("No Content-Length in header and not chunked.");
                }
                body = reader.readBytes(contentLength);
            }
            body.flip();
            var responseCharset = header.getCharset().orElse(UTF8);
            return responseCharset.decode(body).toString();
        }
    }
}
