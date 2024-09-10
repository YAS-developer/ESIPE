package fr.uge.dom;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class DOMDocument{
	
	private final Map<String, DOMNode> idNodeMap = new HashMap<>();
	
	public DOMNode createElement(String name){
		Objects.requireNonNull(name);
		return new DOMElement(name, this, Map.of());
	}
	
	public DOMNode createElement(String name, Map<String, Object> attributes){
		Objects.requireNonNull(name);
		Objects.requireNonNull(attributes);	
		var map = Map.copyOf(attributes);
		checkValue(map);
		var node = new DOMElement(name, this, Map.copyOf(attributes));
	    if(attributes.containsKey("id")) {
	      addNodeId(node);
	    }
	    return node;
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
	
	
	public DOMNode getElementById(String id) {
		Objects.requireNonNull(id);
		return idNodeMap.get(id);
	}

	
	private void addNodeId(DOMNode node) {
	    Objects.requireNonNull(node);
	    var id = node.attributes().get("id");
	    switch (id) {
	    case String s -> {
	      if(s.isEmpty()) {
	        throw new IllegalArgumentException("Ne peut pas être empty");
	      }
	      idNodeMap.putIfAbsent(s, node);
	    }
	    default -> throw new IllegalArgumentException("Unexpected value: " + id);
	    }
	  }
}
