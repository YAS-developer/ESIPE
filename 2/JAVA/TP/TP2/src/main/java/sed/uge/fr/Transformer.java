package sed.uge.fr;

//public sealed interface Transformer permits StarTransformer, UpperCaseTransformer, LowerCaseTransformer{
//
//	String transform(String line);
//}


@FunctionalInterface
public interface Transformer {
    String transform(String line);
}
