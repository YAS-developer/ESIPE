package fr.uge.dom;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class DOMDocument {
    private final Map<String, DOMNode> idNodeMap = new HashMap<>();
    private final Set<DOMNode> allNodes = new HashSet<>();

    Set<DOMNode> getAllNodes() {
        return Collections.unmodifiableSet(allNodes);
    }
    
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
    
    private void checkValue(Map<String, Object> map) {
        map.forEach((key, value) -> {
            Objects.requireNonNull(key, "Attribute key cannot be null");
            Objects.requireNonNull(value, "Attribute value cannot be null");
            switch (value) {
                case String s -> {}
                case Integer i -> {}
                case Boolean b -> {}
                case Long l -> {}
                case Float f -> {}
                case Double d -> {}
                default -> throw new IllegalArgumentException("Invalid value type: " + value.getClass());
            }
        });
    }

    public DOMNode getElementById(String id) {
        Objects.requireNonNull(id);
        return idNodeMap.get(id);
    }

    private void addNodeId(DOMNode node) {
        Objects.requireNonNull(node);
        var id = node.attributes().get("id");
        if (id instanceof String s) {
            if (s.isEmpty()) {
                throw new IllegalArgumentException("ID cannot be empty");
            }
            idNodeMap.putIfAbsent(s, node);
        } else {
            throw new IllegalArgumentException("ID must be a non-empty string");
        }
    }
    
    
}