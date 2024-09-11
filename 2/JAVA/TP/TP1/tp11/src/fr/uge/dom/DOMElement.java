package fr.uge.dom;

import java.util.*;
import java.util.stream.Collectors;

final class DOMElement implements DOMNode {
    private final String name;
    private final DOMDocument document;
    private final Map<String, Object> attributes;
    private final List<DOMNode> children;
    private String cache;
    private DOMElement parent;
    
    DOMElement(String name, DOMDocument document, Map<String, Object> attributes) {
        this.name = name;
        this.document = document;
        this.attributes = Map.copyOf(attributes);
        this.children = new ArrayList<>();
    }

    @Override
    public String name() {
        return name;
    }
    
 
    @Override
    public Map<String, Object> attributes() {
        return attributes;
    }

    @Override
    public List<DOMNode> children() {
        return Collections.unmodifiableList(children);
    }

//    @Override
//    public void appendChild(DOMNode child) {
//        Objects.requireNonNull(child, "Child node cannot be null");
//        if (child.equals(this)) {
//            throw new IllegalArgumentException("A node cannot be its own child");
//        }
//        if (!document.containsNode(child)) {
//            throw new IllegalStateException("Child node must be from the same document");
//        }
//        removeChildFromCurrentParent(child);
//        children.add(child);
//        cache = null;  // Invalider le cache
//    }
//
//    private void removeChildFromCurrentParent(DOMNode child) {
//        for (DOMNode node : document.getAllNodes()) {
//            if (node != this && node.children().contains(child)) {
//                ((DOMElement) node).children.remove(child);
//                ((DOMElement) node).cache = null;  // Invalider le cache du parent précédent
//                break;
//            }
//        }
//    }
//    
//    @Override
//    public String toString() {
//        if (cache == null) {
//            var builder = new StringBuilder();
//            builder.append('<').append(name);
//            
//            attributes.forEach((key, value) -> 
//                builder.append(' ').append(key).append("=\"").append(value).append('"'));
//            
//            builder.append('>');
//            
//            children.forEach(child -> builder.append(child.toString()));
//            
//            builder.append("</").append(name).append('>');
//            
//            cache = builder.toString();
//        }
//        return cache;
//    }
    
    
    @Override
    public void appendChild(DOMNode child) {
        Objects.requireNonNull(child, "Child node cannot be null");
        if (!document.containsNode(child)) {
            throw new IllegalStateException("Child node must be from the same document");
        }
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

    @Override
    public String toString() {
        if (cache == null) {
            StringBuilder builder = new StringBuilder();
            builder.append('<').append(name);
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                builder.append(' ').append(entry.getKey()).append("=\"").append(entry.getValue()).append('"');
            }
            builder.append('>');
            for (DOMNode child : children) {
                builder.append(child.toString());
            }
            builder.append("</").append(name).append('>');
            cache = builder.toString();
        }
        return cache;
    }

}