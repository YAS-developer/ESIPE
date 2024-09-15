package fr.uge.dom;

import java.util.List;
import java.util.Map;

public sealed interface DOMNode permits DOMElement {
    String name();
    Map<String, Object> attributes();
    List<DOMNode> children();
    void appendChild(DOMNode child);
}