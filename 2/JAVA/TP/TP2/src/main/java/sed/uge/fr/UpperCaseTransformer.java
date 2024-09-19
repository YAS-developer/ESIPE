package sed.uge.fr;

//import java.util.Locale;

//record UpperCaseTransformer() implements Transformer{
//	@Override
//	public String transform(String line) {
//		return line.toUpperCase(Locale.ROOT);
//	}
//}


record UpperCaseTransformer(int res) {}