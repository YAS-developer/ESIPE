package com.evilcorp.imagine;

public final class LazyImage implements Image {
  private Image image = null;

  private final String url;

  LazyImage(String url) {
    this.url = url;
  }

  @Override
  public String name() {
    checkImage();
    return image.name();
  }

  @Override
  public int size() {
    checkImage();
    return image.size();
  }

  @Override
  public double hue() {
    checkImage();
    return image.hue();
  }

  private void checkImage() {
    if (image == null){
      image = Image.download(url);
    }
  }
}
