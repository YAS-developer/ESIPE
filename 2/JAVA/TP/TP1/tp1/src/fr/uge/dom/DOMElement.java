package fr.uge.dom;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
//                switch(node){
//                    case DOMElement nodeElement ->{
//                    	nodeElement.children.remove(child);
//                    	nodeElement.cache = null; // Invalider le cache du parent précédent
//                    }
//                }
//            }
//        }
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
        
        
        switch(child) {
        	case DOMElement childElement -> {
        		if (childElement.parent != null) {
                    childElement.parent.children.remove(child);
                    invalidateParentCaches(childElement.parent);
                }
        		childElement.parent = this;
                children.add(child);
                invalidateParentCaches(this);
        	}	
        }  
    }

    private boolean wouldCreateCycle(DOMElement potentialParent, DOMNode potentialChild) {
        if (potentialParent == potentialChild) {
            return true;
        }
        var current = potentialParent.parent;
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
            cache = Stream.concat(
                attributes.entrySet().stream()
                    .map(entry -> " " + entry.getKey() + "=\"" + entry.getValue() + "\""),
                Stream.of(">", 
                    children.stream().map(DOMNode::toString).collect(Collectors.joining()),
                    "</" + name + ">")
            ).collect(Collectors.joining("", "<" + name, ""));
        }
        return cache;
    }

}