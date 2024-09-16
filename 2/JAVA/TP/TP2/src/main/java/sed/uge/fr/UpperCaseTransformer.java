package sed.uge.fr;

record UpperCaseTransformer() implements Transformer{
	@Override
	public String transform(String line) {
		return line.toUpperCase();
	}
}
