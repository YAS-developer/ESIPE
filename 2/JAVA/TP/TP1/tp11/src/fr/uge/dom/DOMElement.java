package fr.uge.dom;

import java.util.*;
import java.util.stream.Collectors;

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