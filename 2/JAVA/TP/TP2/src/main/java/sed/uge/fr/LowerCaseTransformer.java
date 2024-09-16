package sed.uge.fr;

record LowerCaseTransformer() implements Transformer{
	@Override
	public String transform(String line) {
		return line.toLowerCase();
	}
}
