package sed.uge.fr;

record StarTransformer(int res) implements Transformer {
	@Override
    public String transform(String line) {
        if (res == 0) {
            return line.replace("*", "");
        }
        return line.replace("*", "*".repeat(res));
    }
}
