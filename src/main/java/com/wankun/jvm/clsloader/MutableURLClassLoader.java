package com.wankun.jvm.clsloader;

import java.net.URL;
import java.net.URLClassLoader;

public class MutableURLClassLoader extends URLClassLoader {

  public MutableURLClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }

  @Override
  protected void addURL(URL url) {
    super.addURL(url);
  }
}
