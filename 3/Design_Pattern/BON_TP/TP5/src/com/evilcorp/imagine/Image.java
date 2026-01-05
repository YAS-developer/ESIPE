package com.evilcorp.imagine;

public sealed interface Image permits RealImage, LazyImage {
  String name();
  int size();
  double hue();

  static Image download(String url) {
    return RealImage.download(url);
  }

  static Image downloadLazy(String url) {
    return new LazyImage(url);
  }
}
