package fr.uge.poo.duck.exo2;

import java.util.Map;

public class Application {
    void main(){
        var map = Map.of("cat","http://www.example.com/cat.png",
                "dog","http://www.example.com/dog.png",
                "mice","http://www.example.com/mice.png");
        var images =map.values().stream().map(Image::download).toList();
        System.out.println(images.getFirst().hue());
    }
}
