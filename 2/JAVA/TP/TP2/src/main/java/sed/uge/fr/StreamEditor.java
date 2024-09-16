package sed.uge.fr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public final class StreamEditor{
	
	private StreamEditor() {
        throw new AssertionError("This class is not meant to be instantiated");
    }
	
	
	public static Transformer createTransformer(String s) {
		Objects.requireNonNull(s, "command must be not null");
	
	
		return switch(s) {
	        case "u" -> new UpperCaseTransformer();
	        case "l" -> new LowerCaseTransformer();
	        case String str when str.matches("\\*[0-9]") -> {
	            int res = Integer.parseInt(s.substring(1));
	            yield new StarTransformer(res);
	        }
	        default -> throw new IllegalArgumentException("invalid argument " + s);
	    };
	}
	
	
	public static void rewrite(BufferedReader reader, Writer writer, Transformer transformer) throws IOException {
        Objects.requireNonNull(reader, "reader must not be null");
        Objects.requireNonNull(writer, "writer must not be null");
        Objects.requireNonNull(transformer, "transformer must not be null");
        
        var line = new String();
        while ((line = reader.readLine()) != null) {
            writer.write(transformer.transform(line) + "\n");
        }
    }

}
