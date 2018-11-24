/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package mybatis.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * A class to simplify access to resources through the classloader.
 * 通过类加载器获得resource的辅助类
 *
 * @author Clinton Begin
 */
public class Resources {

  //大多数方法都是委托给ClassLoaderWrapper，再去做真正的事
  private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

  /*
   * Charset to use when calling getResourceAsReader.
   * null means use the system default.
   */
  private static Charset charset;

  Resources() {
  }

  /*
   * Returns the default classloader (may be null).
   *
   * @return The default classloader
   */
  public static ClassLoader getDefaultClassLoader() {
    return classLoaderWrapper.defaultClassLoader;
  }

  /*
   * Sets the default classloader
   *
   * @param defaultClassLoader - the new default ClassLoader
   */
  public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
    classLoaderWrapper.defaultClassLoader = defaultClassLoader;
  }

  /*
   * Returns the URL of the resource on the classpath
   *
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static URL getResourceURL(String resource) throws IOException {
      // issue #625
      return getResourceURL(null, resource);
  }

  /*
   * Returns the URL of the resource on the classpath
   *
   * @param loader   The classloader used to fetch the resource
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
    URL url = classLoaderWrapper.getResourceAsURL(resource, loader);
    if (url == null) {
      throw new IOException("Could not find resource " + resource);
    }
    return url;
  }

  /*
   * Returns a resource on the classpath as a Stream object
   *
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static InputStream getResourceAsStream(String resource) throws IOException {
    return getResourceAsStream(null, resource);
  }

  /*
   * Returns a resource on the classpath as a Stream object
   *
   * @param loader   The classloader used to fetch the resource
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
    InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
    if (in == null) {
      throw new IOException("Could not find resource " + resource);
    }
    return in;
  }

  /*
   * Returns a resource on the classpath as a Properties object
   *
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static Properties getResourceAsProperties(String resource) throws IOException {
    Properties props = new Properties();
    InputStream in = getResourceAsStream(resource);
    props.load(in);
    in.close();
    return props;
  }

  /*
   * Returns a resource on the classpath as a Properties object
   *
   * @param loader   The classloader used to fetch the resource
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
    Properties props = new Properties();
    InputStream in = getResourceAsStream(loader, resource);
    props.load(in);
    in.close();
    return props;
  }

  /*
   * Returns a resource on the classpath as a Reader object
   *
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static Reader getResourceAsReader(String resource) throws IOException {
    Reader reader;
    if (charset == null) {
      reader = new InputStreamReader(getResourceAsStream(resource));
    } else {
      reader = new InputStreamReader(getResourceAsStream(resource), charset);
    }
    return reader;
  }

  /*
   * Returns a resource on the classpath as a Reader object
   *
   * @param loader   The classloader used to fetch the resource
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
    Reader reader;
    if (charset == null) {
      reader = new InputStreamReader(getResourceAsStream(loader, resource));
    } else {
      reader = new InputStreamReader(getResourceAsStream(loader, resource), charset);
    }
    return reader;
  }

  /*
   * Returns a resource on the classpath as a File object
   *
   * @param resource The resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static File getResourceAsFile(String resource) throws IOException {
    return new File(getResourceURL(resource).getFile());
  }

  /*
   * Returns a resource on the classpath as a File object
   *
   * @param loader   - the classloader used to fetch the resource
   * @param resource - the resource to find
   * @return The resource
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
    return new File(getResourceURL(loader, resource).getFile());
  }

  /*
   * Gets a URL as an input stream
   *
   * @param urlString - the URL to get
   * @return An input stream with the data from the URL
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static InputStream getUrlAsStream(String urlString) throws IOException {
    URL url = new URL(urlString);
    URLConnection conn = url.openConnection();
    return conn.getInputStream();
  }

  /*
   * Gets a URL as a Reader
   *
   * @param urlString - the URL to get
   * @return A Reader with the data from the URL
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static Reader getUrlAsReader(String urlString) throws IOException {
    Reader reader;
    if (charset == null) {
      reader = new InputStreamReader(getUrlAsStream(urlString));
    } else {
      reader = new InputStreamReader(getUrlAsStream(urlString), charset);
    }
    return reader;
  }

  /*
   * Gets a URL as a Properties object
   *
   * @param urlString - the URL to get
   * @return A Properties object with the data from the URL
   * @throws java.io.IOException If the resource cannot be found or read
   */
  public static Properties getUrlAsProperties(String urlString) throws IOException {
    Properties props = new Properties();
    InputStream in = getUrlAsStream(urlString);
    props.load(in);
    in.close();
    return props;
  }

  /*
   * Loads a class
   *
   * @param className - the class to fetch
   * @return The loaded class
   * @throws ClassNotFoundException If the class cannot be found (duh!)
   */
  public static Class<?> classForName(String className) throws ClassNotFoundException {
    return classLoaderWrapper.classForName(className);
  }

  public static Charset getCharset() {
    return charset;
  }

  public static void setCharset(Charset charset) {
    Resources.charset = charset;
  }

  /**
   * A class to wrap access to multiple class loaders making them work as one
   *封装了5个类加载器,见getClassLoaders方法
   *
   * @author Clinton Begin
   */
  public static class ClassLoaderWrapper {

    //defaultClassLoader没地方初始化啊?
    ClassLoader defaultClassLoader;
    ClassLoader systemClassLoader;

    ClassLoaderWrapper() {
      try {
        systemClassLoader = ClassLoader.getSystemClassLoader();
      } catch (SecurityException ignored) {
        // AccessControlException on Google App Engine
      }
    }

    /*
     * Get a resource as a URL using the current class path
     *
     * @param resource - the resource to locate
     * @return the resource or null
     */
    public URL getResourceAsURL(String resource) {
      return getResourceAsURL(resource, getClassLoaders(null));
    }

    /*
     * Get a resource from the classpath, starting with a specific class loader
     *
     * @param resource    - the resource to find
     * @param classLoader - the first classloader to try
     * @return the stream or null
     */
    public URL getResourceAsURL(String resource, ClassLoader classLoader) {
      return getResourceAsURL(resource, getClassLoaders(classLoader));
    }

    /*
     * Get a resource from the classpath
     *
     * @param resource - the resource to find
     * @return the stream or null
     */
    public InputStream getResourceAsStream(String resource) {
      return getResourceAsStream(resource, getClassLoaders(null));
    }

    /*
     * Get a resource from the classpath, starting with a specific class loader
     *
     * @param resource    - the resource to find
     * @param classLoader - the first class loader to try
     * @return the stream or null
     */
    public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
      return getResourceAsStream(resource, getClassLoaders(classLoader));
    }

    /*
     * Find a class on the classpath (or die trying)
     *
     * @param name - the class to look for
     * @return - the class
     * @throws ClassNotFoundException Duh.
     */
    public Class<?> classForName(String name) throws ClassNotFoundException {
      return classForName(name, getClassLoaders(null));
    }

    /*
     * Find a class on the classpath, starting with a specific classloader (or die trying)
     *
     * @param name        - the class to look for
     * @param classLoader - the first classloader to try
     * @return - the class
     * @throws ClassNotFoundException Duh.
     */
    public Class<?> classForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
      return classForName(name, getClassLoaders(classLoader));
    }

    /*
     * Try to get a resource from a group of classloaders
     * 用5个类加载器一个个查找资源，只要其中任何一个找到，就返回
     *
     * @param resource    - the resource to get
     * @param classLoader - the classloaders to examine
     * @return the resource or null
     */
    InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
      for (ClassLoader cl : classLoader) {
        if (null != cl) {

          // try to find the resource as passed
          InputStream returnValue = cl.getResourceAsStream(resource);

          // now, some class loaders want this leading "/", so we'll add it and try again if we didn't find the resource
          if (null == returnValue) {
            returnValue = cl.getResourceAsStream("/" + resource);
          }

          if (null != returnValue) {
            return returnValue;
          }
        }
      }
      return null;
    }

    /*
     * Get a resource as a URL using the current class path
     * 用5个类加载器一个个查找资源，只要其中任何一个找到，就返回
     *
     * @param resource    - the resource to locate
     * @param classLoader - the class loaders to examine
     * @return the resource or null
     */
    URL getResourceAsURL(String resource, ClassLoader[] classLoader) {

      URL url;

      for (ClassLoader cl : classLoader) {

        if (null != cl) {

          // look for the resource as passed in...
          url = cl.getResource(resource);

          // ...but some class loaders want this leading "/", so we'll add it
          // and try again if we didn't find the resource
          if (null == url) {
            url = cl.getResource("/" + resource);
          }

          // "It's always in the last place I look for it!"
          // ... because only an idiot would keep looking for it after finding it, so stop looking already.
          if (null != url) {
            return url;
          }

        }

      }

      // didn't find it anywhere.
      return null;

    }

    /*
     * Attempt to load a class from a group of classloaders
     * 用5个类加载器一个个调用Class.forName(加载类)，只要其中任何一个加载成功，就返回
     *
     * @param name        - the class to load
     * @param classLoader - the group of classloaders to examine
     * @return the class
     * @throws ClassNotFoundException - Remember the wisdom of Judge Smails: Well, the world needs ditch diggers, too.
     */
    Class<?> classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {

      for (ClassLoader cl : classLoader) {

        if (null != cl) {

          try {

            Class<?> c = Class.forName(name, true, cl);

            if (null != c) {
              return c;
            }

          } catch (ClassNotFoundException e) {
            // we'll ignore this until all classloaders fail to locate the class
          }

        }

      }

      throw new ClassNotFoundException("Cannot find class: " + name);

    }

    //一共5个类加载器
    ClassLoader[] getClassLoaders(ClassLoader classLoader) {
      return new ClassLoader[]{
          classLoader,
          defaultClassLoader,
          Thread.currentThread().getContextClassLoader(),
          getClass().getClassLoader(),
          systemClassLoader};
    }

  }
}
