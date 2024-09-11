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


    // DOMElement    
    @Override
    public void appendChild(DOMNode child) {
        Objects.requireNonNull(child, "Child node cannot be null");
        if (!document.containsNode(child)) {
            throw new IllegalStateException("Child node must be from the same document");
        }
        children.add(child);
    }
    
   

    // DONDocument
    private final Set<DOMNode> allNodes = new HashSet<>();

    public DOMNode createElement(String name) {
        Objects.requireNonNull(name);
        var node = new DOMElement(name, this, Map.of());
        allNodes.add(node);
        return node;
    }

    public DOMNode createElement(String name, Map<String, Object> attributes) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(attributes);
        var map = Map.copyOf(attributes);
        checkValue(map);
        var node = new DOMElement(name, this, map);
        allNodes.add(node);
        if (attributes.containsKey("id")) {
            addNodeId(node);
        }
        return node;
    }

    
    boolean containsNode(DOMNode node) {
        return allNodes.contains(node);
    }
```

#### On veut que la liste soit non modifiable pour plusieurs raisons importantes :

#### Ça protège la structure interne de notre nœud DOM. Si n'importe qui pouvait modifier la liste des enfants directement, ça pourrait créer des problèmes et rendre notre arbre DOM incohérent.

#### Ça nous permet de contrôler comment les enfants sont ajoutés. On veut que les gens utilisent la méthode appendChild() pour ajouter des enfants, pas qu'ils modifient la liste directement.

#### Ça rend notre code plus sûr et plus facile à comprendre. Quand on voit children(), on sait qu'on ne peut pas modifier la #### liste, juste la lire.




#### 6 - Modification de l'affichage pour inclure les enfants

Pour prendre en compte les enfants dans l'affichage des nœuds, j'ai modifié la méthode `toString()` de la classe `DOMElement`. Voici l'implémentation :

```java
@Override
public String toString() {
    var builder = new StringBuilder();
    builder.append('<').append(name);
    
    attributes.forEach((key, value) -> 
        builder.append(' ').append(key).append("=\"").append(value).append('"'));
    
    builder.append('>');
    
    children.forEach(child -> builder.append(child.toString()));
    
    builder.append("</").append(name).append('>');
    
    return builder.toString();
}



#### 7 - Mise en cache de la représentation textuelle

Pour améliorer les performances lors de l'affichage répété des nœuds, j'ai implémenté un système de mise en cache de la représentation textuelle. Voici les modifications apportées à la classe `DOMElement` :

```java
final class DOMElement implements DOMNode {
    
    private String cache;  

    @Override
    public String toString() {
        if (cache == null) {
            var builder = new StringBuilder();
            // ...
            cache = builder.toString();
        }
        return cache;
    }
}
```

#### 8 - Correction du bug dans appendChild et gestion des documents différents

J'ai corrigé un bug dans l'implémentation de `appendChild` qui permettait à un nœud d'appartenir à plusieurs parents et j'ai ajouté une vérification pour s'assurer que les nœuds appartiennent au même document. Voici les modifications apportées :

```java
final class DOMElement implements DOMNode {
    // ... autres champs et méthodes ...

    @Override
    public void appendChild(DOMNode child) {
        Objects.requireNonNull(child, "Child node cannot be null");
        if (child.equals(this)) {
            throw new IllegalArgumentException("A node cannot be its own child");
        }
        if (!document.containsNode(child)) {
            throw new IllegalStateException("Child node must be from the same document");
        }
        removeChildFromCurrentParent(child);
        children.add(child);
        cache = null;  // Invalider le cache
    }

    private void removeChildFromCurrentParent(DOMNode child) {
        for (DOMNode node : document.getAllNodes()) {
            if (node != this && node.children().contains(child)) {
                ((DOMElement) node).children.remove(child);
                ((DOMElement) node).cache = null;  // Invalider le cache du parent précédent
                break;
            }
        }
    }
}


#### 9 - Prévention des cycles et invalidation intelligente du cache

J'ai apporté deux améliorations majeures à l'implémentation :

1. Prévention des cycles :
   - La méthode `wouldCreateCycle` vérifie si l'ajout d'un enfant créerait un cycle dans l'arbre DOM.
   - Elle parcourt les parents du nœud potentiel parent pour s'assurer que le nœud enfant n'est pas déjà un ancêtre.

2. Invalidation intelligente du cache :
   - La méthode `invalidateParentCaches` invalide le cache du nœud modifié et de tous ses parents.
   - Cela garantit que seuls les caches nécessaires sont invalidés lors de modifications de l'arbre.

Voici les principales modifications dans la méthode `appendChild` :

```java
@Override
public void appendChild(DOMNode child) {
    // ... vérifications initiales ...
    
    if (wouldCreateCycle(this, child)) {
        throw new IllegalStateException("Adding this child would create a cycle");
    }
    
    DOMElement childElement = (DOMElement) child;
    if (childElement.parent != null) {
        childElement.parent.children.remove(child);
        invalidateParentCaches(childElement.parent);
    }
    
    childElement.parent = this;
    children.add(child);
    invalidateParentCaches(this);
}

private boolean wouldCreateCycle(DOMElement potentialParent, DOMNode potentialChild) {
    if (potentialParent == potentialChild) {
        return true;
    }
    DOMElement current = potentialParent.parent;
    while (current != null) {
        if (current == potentialChild) {
            return true;
        }
        current = current.parent;
    }
    return false;
}

private void invalidateParentCaches(DOMElement node) {
    while (node != null) {
        node.cache = null;
        node = node.parent;
    }
}