package sed.uge.fr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class StreamEditor{
	
	private static final Transformer UPPER_CASE_TRANSFORMER = line -> line.toUpperCase(Locale.ROOT);
    private static final Transformer LOWER_CASE_TRANSFORMER = line -> line.toLowerCase(Locale.ROOT);
	
	private StreamEditor() {
        throw new AssertionError("This class is not meant to be instantiated");
    }
	
	
//	public static Transformer createTransformer(String s) {
//		Objects.requireNonNull(s, "command must be not null");
//	
//	
//		return switch(s) {
//	        case "u" -> new UpperCaseTransformer();
//	        case "l" -> new LowerCaseTransformer();
//	        case String str when str.matches("\\*[0-9]") -> {
//	            var res = Integer.parseInt(s.substring(1));
//	            yield new StarTransformer(res);
//	        }
//	        default -> throw new IllegalArgumentException("invalid argument " + s);
//	    };
//	}
	
	
//	public static void rewrite(BufferedReader reader, Writer writer, Transformer transformer) throws IOException {
//        Objects.requireNonNull(reader, "reader must not be null");
//        Objects.requireNonNull(writer, "writer must not be null");
//        Objects.requireNonNull(transformer, "transformer must not be null");
//        
//        var line = new String();
//        while ((line = reader.readLine()) != null) {
//            writer.write(transformer.transform(line) + "\n");
//        }
//    }
	
	
//	public static void rewrite(BufferedReader reader, Writer writer, Transformer transformer) throws IOException {
//        Objects.requireNonNull(reader, "reader must not be null");
//        Objects.requireNonNull(writer, "writer must not be null");
//        Objects.requireNonNull(transformer, "transformer must not be null");
//        
//        String line;
//        while ((line = reader.readLine()) != null) {
//            String transformedLine = switch (transformer) {
//                case UpperCaseTransformer u -> line.toUpperCase(Locale.ROOT);
//                case LowerCaseTransformer l -> line.toLowerCase(Locale.ROOT);
//                case StarTransformer s -> {
//                    if (s.res() == 0) {
//                        yield line.replace("*", "");
//                    } else {
//                        yield line.replace("*", "*".repeat(s.res()));
//                    }
//                }
//            };
//            writer.write(transformedLine + "\n");
//        }
//    }

	
	
//	Q4
//	public static Transformer createTransformer(String s) {
//        Objects.requireNonNull(s, "command must be not null");
//        return switch(s) {
//            case "u" -> UPPER_CASE_TRANSFORMER;
//            case "l" -> LOWER_CASE_TRANSFORMER;
//            case String str when str.matches("\\*[0-9]") -> {
//                var res = Character.getNumericValue(str.charAt(1));
//                yield line -> res == 0 ? line.replace("*", "") : line.replace("*", "*".repeat(res));
//            }
//            default -> throw new IllegalArgumentException("invalid argument " + s);
//        };
//    }
    
	

    public static void rewrite(BufferedReader reader, Writer writer, Transformer transformer) throws IOException {
        Objects.requireNonNull(reader, "reader must not be null");
        Objects.requireNonNull(writer, "writer must not be null");
        Objects.requireNonNull(transformer, "transformer must not be null");
        
        var line = new String();
        while ((line = reader.readLine()) != null) {
            writer.write(transformer.transform(line) + "\n");
        }
    }
	

	

// Q5   
    private static ParseResult parse(String command, int index) {
        if (index >= command.length()) {
            return new ParseResult(line -> line, index);
        }
        
        var c = command.charAt(index);
        return switch(c) {
            case 'u' -> new ParseResult(UPPER_CASE_TRANSFORMER, index + 1);
            case 'l' -> new ParseResult(LOWER_CASE_TRANSFORMER, index + 1);
            case '*' -> {
                if (index + 1 >= command.length()) {
                    throw new IllegalArgumentException("Invalid star command at index " + index);
                }
                var repeat = Character.getNumericValue(command.charAt(index + 1));
                yield new ParseResult(
                    line -> repeat == 0 ? line.replace("*", "") : line.replace("*", "*".repeat(repeat)),
                    index + 2
                );
            }
            default -> throw new IllegalArgumentException("Invalid command '" + c + "' at index " + index);
        };
    }
    
    public static Transformer createTransformer(String command) {
        Objects.requireNonNull(command, "command must be not null");
        
        if (command.isEmpty()) {
            return line -> line;
        }
        
        // Vérifier la validité de la commande immédiatement
        var transformers = new ArrayList<Transformer>();
        int index = 0;
        while (index < command.length()) {
            var parseResult = parse(command, index);
            transformers.add(parseResult.transformer());
            index = parseResult.nextIndex();
        }
        
        // Retourner le transformer composé
        return line -> {
            var result = line;
            for (Transformer t : transformers) {
                result = t.transform(result);
            }
            return result;
        };
    }
	
	
	
	
}
