# TP1 JAVA YASSIN HAMROUNI

#### 1 -  créer l'interface DOMNode avec une méthode name qui renvoie le nom du nœud de l'arbre DOM. Créer la classe DOMDocument avec une méthode createElement(name) qui créé un nouveau nœud. Il vous faut aussi créer une classe qui implante l'interface DOMNode, on souhaite que cette classe soit la seule implantation possible de DOMNode.
#### On souhaite de plus que le document (DOMDocument) qui a servi a créé un nœud continue à exister tant que le nœud existe. 

```java

public final class DOMDocument {
    public DOMNode createElement(String name) {
        Objects.requireNonNull(name);
        return new DOMElement(name, this, Map.of());
    }
}

public sealed interface DOMNode permits DOMElement {
    String name();
}

record DOMElement(String name, DOMDocument document, Map<String, Object> attributes) implements DOMNode {
}

```

#### 2 - On souhaite maintenant pouvoir créer un nœud avec un nom et des attributs tel que le code ci-dessous fonctionne 

```java

public final class DOMDocument {
    public DOMNode createElement(String name, Map<String, Object> attributes) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(attributes);
        var map = Map.copyOf(attributes);
        checkValue(map);
        return new DOMElement(name, this, Map.copyOf(attributes));
    }

    private void checkValue(Map<String, Object> map) {
        map.forEach((__, value) -> {
            switch(value) {
                case String s -> {}
                case Integer i -> {}
                case Boolean b -> {}
                case Long i -> {}
                case Float f -> {}
                case Double d -> {}
                default -> throw new IllegalArgumentException("Invalid value" + value);
            }
        });
    }
}

public sealed interface DOMNode permits DOMElement {
    String name();
    Map<String, Object> attributes();
}

```

#### 3 - On souhaite pouvoir afficher un nœud en utilisant le même format que le HTML. 


```java



```

#### 4 - On souhaite ajouter une méthode getElementById qui renvoie un nœud par son id.
#### En HTML, un id doit être une chaine de caractère (non vide) et si pour un même document, il y a plusieurs nœuds avec le même id seul le premier nœud est enregistré avec cet id. 


```java

public final class DOMDocument {
    private final Map<String, DOMNode> idNodeMap = new HashMap<>();

    public DOMNode createElement(String name, Map<String, Object> attributes) {
        // ... (code précédent)
        var node = new DOMElement(name, this, Map.copyOf(attributes));
        if (attributes.containsKey("id")) {
            addNodeId(node);
        }
        return node;
    }

    public DOMNode getElementById(String id) {
        Objects.requireNonNull(id);
        return idNodeMap.get(id);
    }

    private void addNodeId(DOMNode node) {
        Objects.requireNonNull(node);
        var id = node.attributes().get("id");
        switch (id) {
            case String s -> {
                if (s.isEmpty()) {
                    throw new IllegalArgumentException("Ne peut pas être empty");
                }
                idNodeMap.putIfAbsent(s, node);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + id);
        }
    }
}

```



#### 5 - On souhaite pouvoir ajouter des fils à un nœud existant en utilisant la méthode appendChild(child) et accéder à ces enfants en utilisant la méthode children().
#### Il ne doit être possible d'ajouter un nœud que si le nœud parent et le nœud enfant sont issues du même document. 

``` java


record DOMElement(String name, DOMDocument document, Map<String, Object> attributes, List<DOMNode> children) implements DOMNode {

    @Override
    public List<DOMNode> children() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void appendChild(DOMNode child) {
        Objects.requireNonNull(child, "Child node cannot be null");
        if (child.document() != this.document) {
            throw new IllegalStateException("Child node must be from the same document");
        }
        ((ArrayList<DOMNode>)children).add(child);
    }

    @Override
    public String toString() {
        var attributesString = attributes.entrySet().stream()
            .map(entry -> " " + entry.getKey() + "=\"" + entry.getValue() + "\"")
            .collect(Collectors.joining());
        
        var childrenString = children.stream()
            .map(Object::toString)
            .collect(Collectors.joining());
        
        return "<" + name + attributesString + ">" + childrenString + "</" + name + ">";
    }
}

```