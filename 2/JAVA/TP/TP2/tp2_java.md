### TP2 JAVA 2 Yassine HAMROUNI


#### 1 - ans un premier temps, on va prendre une commande (une chaîne de caractères comme "u" ou "*4") et créer l'objet correspondant pour éviter de re-parser la commande à chaque ligne. On se propose de nommer cette objet Transformer car son rôle est de transformer une ligne du fichier en une nouvelle ligne.En plus de la méthode createTransformer qui créé un transformer pour une commande, on a besoin d'une autre méthode rewrite qui prend un reader, un writer et un transformer. Elle lit chaque ligne du reader, la transforme avec le transformer et l'écrit sur le writer. Pour l'instant, on ne s'intéresse qu'aux commandes "u", "l" et "*" suivie d'un chiffre (pas un nombre !) indiquant la répétition ("*4", "*7", etc), et on veut une classe par type de commande. Déclarer Transformer ainsi que 3 implantations UpperCaseTransformer, LowerCaseTransformer et StarTransformer. Puis créer la méthode createTransformer qui prend une commande, regarde le premier caractère et créé une instance de Transformer avec la bonne implantation. Enfin, écrire la méthode rewrite qui boucle sur chaque ligne du reader, envoie la ligne au transformer et écrit la ligne résultante dans le writer. Pour le retour à la ligne on utilisera un '\n', ainsi le programme fonctionnera de la même façon quelque soit l'OS. 


```java
package sed.uge.fr;

public sealed interface Transformer permits StarTransformer, UpperCaseTransformer, LowerCaseTransformer{

	String transform(String line);
}

package sed.uge.fr;

record LowerCaseTransformer() implements Transformer{
	@Override
	public String transform(String line) {
		return line.toLowerCase();
	}
}


package sed.uge.fr;

record UpperCaseTransformer() implements Transformer{
	@Override
	public String transform(String line) {
		return line.toUpperCase();
	}
}


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
	        case "u" -> {
	        	yield new UpperCaseTransformer();
	        }
	        case "l" -> {
	        	yield new LowerCaseTransformer();
	        }
	        case String str when str.matches("\\*[0-9]") -> {
	            var res = Integer.parseInt(s.substring(1));
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
```

#### 2- On veut que notre programme fonctionne de la même façon, quelle que soit la machine or la norme Unicode. On demande que les méthodes String.toUpperCase() et String.toLowerCase() aient un comportement spécifique en fonction de la langue (Locale) par défaut de l'OS.Si vous ne l'avez pas déjà fait, changer votre code pour que la mise en majuscule/minuscule soit fait de façon indépendante de l'OS. Si vous ne vous souvenez plus de comment on fait, vous pouvez relire la javadoc de toUpperCase() ou toLowerCase()


```java

package sed.uge.fr;

import java.util.Locale;

record UpperCaseTransformer() implements Transformer {
    @Override
    public String transform(String line) {
        return line.toUpperCase(Locale.ROOT);
    }
}

record LowerCaseTransformer() implements Transformer {
    @Override
    public String transform(String line) {
        return line.toLowerCase(Locale.ROOT);
    }
}

```



#### 3- Vous avez sûrement utilisé le polymorphisme pour implanter Transformer alors que votre programme contrôle toutes les implantations possibles. C'est MAL, vous auriez du utiliser le pattern matching. Commenter votre code puis changer votre implantation pour utiliser le pattern matching plutôt que le polymorphisme. Vérifier que les tests marqués "Q3" passent. Rappel : Ne pas utiliser le polymorphisme veut dire ne pas avoir de méthode abstract dans Transformer. Et pattern matching, signifie implanter en faisant un switch sur toutes les implantations. 



```java

package sed.uge.fr;

public sealed interface Transformer permits StarTransformer, UpperCaseTransformer, LowerCaseTransformer{
//	String transform(String line);
}



package sed.uge.fr;

//import java.util.Locale;

record LowerCaseTransformer() implements Transformer{
//	@Override
//	public String transform(String line) {
//		return line.toLowerCase(Locale.ROOT);
//	}
}



package sed.uge.fr;

//import java.util.Locale;

record UpperCaseTransformer() implements Transformer{
//	@Override
//	public String transform(String line) {
//		return line.toUpperCase(Locale.ROOT);
//	}
}


package sed.uge.fr;

record StarTransformer(int res) implements Transformer {
//	@Override
//    public String transform(String line) {
//        if (res == 0) {
//            return line.replace("*", "");
//        }
//        return line.replace("*", "*".repeat(res));
//    }
}


	
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
	
	
	public static void rewrite(BufferedReader reader, Writer writer, Transformer transformer) throws IOException {
        Objects.requireNonNull(reader, "reader must not be null");
        Objects.requireNonNull(writer, "writer must not be null");
        Objects.requireNonNull(transformer, "transformer must not be null");
        
        String line;
        while ((line = reader.readLine()) != null) {
            String transformedLine = switch (transformer) {
                case UpperCaseTransformer u -> line.toUpperCase(Locale.ROOT);
                case LowerCaseTransformer l -> line.toLowerCase(Locale.ROOT);
                case StarTransformer s -> {
                    if (s.res() == 0) {
                        yield line.replace("*", "");
                    } else {
                        yield line.replace("*", "*".repeat(s.res()));
                    }
                }
            };
            writer.write(transformedLine + "\n");
        }
    }

```



#### 4- En fait, les transformations sont des actions, donc pour chaque transformation, on pourrait utiliser une lambda comme cela le code serait plus simple à écrire et donc à maintenir.Comment indiquer que Transformer est implantée en utilisant des lambdas ? Comment faire pour que l'on empêche d'autre code de fournir une autre implantation ? Vérifier que les tests marqués "Q4" passent. 

#### On utilise une interface fonctionnelle

```java

package sed.uge.fr;

@FunctionalInterface
public interface Transformer {
    String transform(String line);
}

package sed.uge.fr;

import java.util.Locale;
import java.util.Objects;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public final class StreamEditor {
    
    private StreamEditor() {
        throw new AssertionError("This class is not meant to be instantiated");
    }
    
    private static final Transformer UPPER_CASE_TRANSFORMER = line -> line.toUpperCase(Locale.ROOT);
    private static final Transformer LOWER_CASE_TRANSFORMER = line -> line.toLowerCase(Locale.ROOT);
    
    public static Transformer createTransformer(String s) {
        Objects.requireNonNull(s, "command must be not null");
        return switch(s) {
            case "u" -> UPPER_CASE_TRANSFORMER;
            case "l" -> LOWER_CASE_TRANSFORMER;
            case String str when str.matches("\\*[0-9]") -> {
                int res = Character.getNumericValue(str.charAt(1));
                yield line -> res == 0 ? line.replace("*", "") : line.replace("*", "*".repeat(res));
            }
            default -> throw new IllegalArgumentException("invalid argument " + s);
        };
    }
    
    public static void rewrite(BufferedReader reader, Writer writer, Transformer transformer) throws IOException {
        Objects.requireNonNull(reader, "reader must not be null");
        Objects.requireNonNull(writer, "writer must not be null");
        Objects.requireNonNull(transformer, "transformer must not be null");
        
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(transformer.transform(line) + "\n");
        }
    }

```




#### 5- On veut maintenant pouvoir gérer une commande qui est elle-même composée de plusieurs commandes comme lu ou 4*u (qui respectivement mette la ligne en minuscule puis majuscule, remplace les étoiles par quatre étoiles puis mette la ligne en majuscule). Cela veut dire qu'il va falloir décomposer une commande en plusieurs commandes, pour cela, on se propose d'écrire une méthode parse(command, transformer, index) qui renvoie un tuple composé de la commande à l'index index ainsi que de l'index de la prochaine commande (pour la mise en majuscule/miniscule la prochaine commande est à l'index suivante mais pour les étoiles il faut sauter deux cases). Comment représente-t-on des tuples en Java ? Dans notre cas, quel est la représentation d'un tuple qui contient un Transformer et un index. Écrire la méthode parse(command, transformer, index) et changer le code de la méthode createTransformer(command) en conséquence. Vérifier que les tests marqués "Q5" passent.


#### Pour représenter des tuples en Java, nous avons utilisé un record `ParseResult` qui contient un `Transformer` et un index. Cela nous permet de renvoyer à la fois la transformation à appliquer et l'index de la prochaine commande. 

#### La méthode `parse` a été implémentée pour décomposer une commande en ses composants individuels, et la méthode `createTransformer` a été modifiée pour utiliser `parse` de manière répétée, appliquant chaque transformation dans l'ordre spécifié par la commande.

#### Cette approche nous permet de gérer des commandes composées comme "ul", "lu", "*2u", etc., tout en maintenant la flexibilité et l'extensibilité de notre système de transformation.


```java

package sed.uge.fr;

record ParseResult(Transformer transformer, int nextIndex) {}





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
	

```
#### 6- En fait, le code précédent est la façon fonctionnelle de voir la décomposition en transformer, on peut aussi écrire une version plus objet des choses. En POO, on va encapsuler les mutations, ici, la mutation est l'index qui nous indique là où décoder/parser le prochain transformer dans la commande. Encapsuler la mutation revient donc à déclarer une classe Parser avec un champ mutable qui va être modifié à chaque fois que l'on décode une transformation.

#### Classe `ParseResult` interne à la classe `StreamEditor` qui encapsule la logique de parsing des commandes. Voici l'implémentation :

```java
public final class StreamEditor {
    // ... autres méthodes et champs ...

    static final class ParseResult {
        private final String commands;
        private int index;

        ParseResult(String commands) {
            this.commands = Objects.requireNonNull(commands);
            this.index = 0;
        }

        boolean canParse() {
            return index < commands.length();
        }

        Transformer parse(Transformer t) {
            if (!canParse()) {
                return t;
            }

            char c = commands.charAt(index);
            switch (c) {
                case 'u':
                    index++;
                    return line -> t.transform(line).toUpperCase(Locale.ROOT);
                case 'l':
                    index++;
                    return line -> t.transform(line).toLowerCase(Locale.ROOT);
                case '*':
                    if (index + 1 >= commands.length()) {
                        throw new IllegalArgumentException("Invalid star command at index " + index);
                    }
                    int repeat = Character.getNumericValue(commands.charAt(index + 1));
                    index += 2;
                    return line -> {
                        String transformed = t.transform(line);
                        return repeat == 0 ? transformed.replace("*", "") : transformed.replace("*", "*".repeat(repeat));
                    };
                default:
                    throw new IllegalArgumentException("Invalid command '" + c + "' at index " + index);
            }
        }

        public static Transformer createTransformer(String command) {
            Objects.requireNonNull(command, "command must be not null");

            var parser = new ParseResult(command);
            Transformer transformer = line -> line; // Identity transformer

            while (parser.canParse()) {
                transformer = parser.parse(transformer);
            }

            return transformer;
        }
    }
}







