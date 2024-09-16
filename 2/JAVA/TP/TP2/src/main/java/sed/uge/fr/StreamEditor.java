package sed.uge.fr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public record StreamEditor() {
	
	
	
	public static Transformer createTransformer(String s) {
		Objects.requireNonNull(s, "command must be not null");
	
	
		return switch(s) {
	        case "u" -> new UpperCaseTransformer();
	        case "l" -> new LowerCaseTransformer();
	        case "*1", "*2", "*3", "*4", "*5", "*6", "*7", "*8", "*9" -> {
	            int res = Integer.parseInt(s, s.charAt(1));
	            yield new StarTransformer(res);
	        }
	        default -> throw new IllegalArgumentException("invalid argument " + s);
	    };
	}
	
	
	public static void rewrite(BufferedReader reader, Writer writer, Transformer transformer) throws IOException {
		var tmp = new String();
		var result = new String();
		while((tmp = reader.readLine()) != null) {
			result = transformer.transform(tmp);
			writer.write(result+"\n");
		}
	}

}
