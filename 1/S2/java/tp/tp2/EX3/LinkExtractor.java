import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkExtractor {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java LinkExtractor <path_to_html_file>");
            return;
        }

        var path = Path.of(args[0]);
        var lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);

        Pattern linkPattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=\"([^\"]*)\"", Pattern.CASE_INSENSITIVE);

        for (var line : lines) {
            Matcher matcher = linkPattern.matcher(line);
            while (matcher.find()) {
                String url = matcher.group(1);
                if (url.toLowerCase().contains("java")) {
                    System.out.println("===> " + url);
                }
            }
        }
    }
}
