package sed.uge.fr;

//import java.util.Locale;

//record LowerCaseTransformer() implements Transformer{
//	@Override
//	public String transform(String line) {
//		return line.toLowerCase(Locale.ROOT);
//	}
//}


record LowerCaseTransformer(int res) {}