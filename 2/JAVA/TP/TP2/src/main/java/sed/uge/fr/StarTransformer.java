package sed.uge.fr;

record StarTransformer(int res) implements Transformer {
	@Override
	public String transform(String line) {
		var tmp = "*".repeat(res);
		line.replace("*", tmp);
		return line;
	}
}
