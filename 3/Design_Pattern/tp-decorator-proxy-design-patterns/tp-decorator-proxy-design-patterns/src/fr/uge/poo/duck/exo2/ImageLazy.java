package fr.uge.poo.duck.exo2;

public class ImageLazy implements ImageService {
    @Override
    public Image downloadLazy(String url) {
        return Image.download(url);
    }
}