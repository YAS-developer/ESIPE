package fr.uge.dom;

import java.util.Map;

public class Main {
	public static void main(String args) {
		var document = new DOMDocument();
		var node = document.createElement("div", Map.of("color", "red", "visible", true));
		System.out.println(node.name());  // div
		System.out.println(node.attributes());  // {color=red, visible=true}
		     
	}

}
