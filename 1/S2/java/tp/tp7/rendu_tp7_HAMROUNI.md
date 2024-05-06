# TP7 Java Yassine Hamrouni

## Exercice 1 - Arbre d'expressions

### 1- On va créer Expr dans un fichier Expr.java 

```java
package fr.uge.calc;

public sealed interface Expr permits Value, Add, Sub, Mul{
  public int eval();
}

```

### 2- On souhaite maintenant pouvoir évaluer (trouver la valeur) d'une expression (Expr) en appelant la méthode eval comme ceci 

```java


package fr.uge.calc;

public record Value(int value) implements Expr {
    @Override
    public int eval(){
        return value;
    }
}


// fr/uge/calc/Add.java
package fr.uge.calc;

public record Add(Expr left, Expr right) implements Expr {
    @Override
    public int eval() {
        return left.eval() + right.eval();
    }
}

package fr.uge.calc;

public record Sub(Expr left, Expr right) implements Expr {
    @Override
    public int eval() {
        return left.eval() - right.eval();
    }
}

// fr/uge/calc/Mul.java
package fr.uge.calc;

public record Mul(Expr left, Expr right) implements Expr {
    @Override
    public int eval() {
        return left.eval() * right.eval();
    }
}



```
### 3- Il y a un bug dans le code que l'on a écrit, on permet à n'importe qui d'implanter Expr mais cela ne marchera pas avec la méthode parse qui elle liste tous les sous-types possibles.
###Comment corriger ce problème ? 

#### Il faut utiliser une interface scelle



### 6 Noter que prendre un Scanner en paramètre ne permet pas de ré-utiliser la méthode parse si, par exemple, l'expression à parser est stockée dans une List de String.
### Quelle interface que doit-on utiliser à la place de Scanner pour que l'on puisse appeler la méthode parse avec un Scanner ou à partir d'une List. 


#### Nous pouvons utiliser l'interface Iterator<String>. Cette interface fournit une manière standard de parcourir des collections séquentielles de données, qu'elles soient issues d'un Scanner, d'une List, ou d'autres structures.

```java
// fr/uge/calc/Parser.java
package fr.uge.calc;

import java.util.Iterator;

public class Parser {
    public static Expr parse(Iterator<String> tokens) {
        if (!tokens.hasNext()) {
            throw new IllegalArgumentException("Expression incomplete or malformed");
        }

        String token = tokens.next();
        
        return switch (token) {
            case "+" -> new Add(parse(tokens), parse(tokens));
            case "-" -> new Sub(parse(tokens), parse(tokens));
            case "*" -> new Mul(parse(tokens), parse(tokens));
            default -> {
                try {
                    int value = Integer.parseInt(token);
                    yield new Value(value);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid token: " + token);
                }
            }
        };
    }
}

```



#### Interface:

```java
public interface Transportable{
    @Override
    String toString();
}
```

#### Passenger:

```java
public record Passenger(String destination) implements Transportable{
  public Passenger{
    Objects.requireNonNull(destination, "Destination required");
  }
    
  @Override 
  public String toString(){
    return destination+" [passenger]";
  }
}
```


#### Container:

```java
@Override
public String toString(){
  return this.destination+" "+this.weight+" kg";
}
```

#### Manifest:

```java
@Override
public String toString(){
  int i=0;
  var sb = new StringBuilder();
  for(var transport: TransportableList){ 
    sb.append(i).append(" ").append(transport.toString()).append("\n");
    i++;
  }
  return sb.toString();
}
```



### 5- On souhaite factoriser ce code (on ne le ferait probablement pas dans la vraie vie car il n'y a pas assez de code à partager, mais ce n'est pas la vraie vie, c'est un exercice) en introduisant un type intermédiaire BinOp, sous-type de Expr et super-type de Add, Sub et Mul.
### Le type BinOp doit-il être un record, une classe ou une interface ?

### En faisant de BinOp  une classe sc

```java

```




### 6- On veut maintenant rajouter une méthode weight à Manifest qui renvoie le poids total en considérant qu'un passager n'a pas de poids. 



```java
public int weight(){
  int sum=0;
  for(var transported: TransportableList){
    if(transported instanceof Container){
      Container container = (Container) transported;
      sum += container.weight();
    }
  }
  return sum;
}
```

### 7- on introduit une méthode removeAllContainersFrom(destination) qui supprime tous les conteneurs liés à une destination. S'il n'y a pas de conteneur pour cette destination, on ne fait rien.

```java
public void removeAllContainersFrom(String dest){
  Objects.requireNonNull(dest, "Destination required");
       
  var iterator = manifest.iterator();
  while(iterator.hasNext()) {
    var c = iterator.next();
    if(c.isContainer() && c.destination().equals(destination)) {
      iterator.remove();
    }
  }
}
```


### 8- Pour résoudre la question précédente, au lieu de faire des appels de méthode, on peut aussi utiliser instanceof. Expliquer comment on peut utiliser instanceof. Puis expliquer, selon vous, quel est le problème d'utiliser instanceof dans ce contexte et pourquoi on ne doit pas l'utiliser. 

#### Utiliser instanceof nécessite des informations sur les types concrets d'objets, ce qui peut aller à l'encontre de l'encapsulation et de l'abstraction. L'idée de l'orienté objet est de se concentrer sur ce que fait un objet (comportement), pas ce qu'il est (son type).



