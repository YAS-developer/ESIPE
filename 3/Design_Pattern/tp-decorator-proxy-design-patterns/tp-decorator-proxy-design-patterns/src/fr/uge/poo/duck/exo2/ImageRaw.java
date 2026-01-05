package fr.uge.poo.duck.exo2;

public class ImageRaw implements ImageService {
    @Override
    public Image downloadLazy(String url) {
        return Image.download(url);
    }
}