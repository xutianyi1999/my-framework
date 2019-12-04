package club.koumakan.web.framework.utils;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.jar.JarEntry;

public class ClassScan {

  private static final ClassLoader CLASS_LOADER = ClassLoader.getSystemClassLoader();
  private static Consumer<Class<?>> consumer;

  private static void installHandler(File file, String parentPath) throws Exception {
    if (file != null && file.exists()) {
      if (file.isDirectory()) {
        File[] files = file.listFiles();

        if (files != null) {
          for (File fileTemp : files) {
            installHandler(fileTemp, parentPath);
          }
        }
      } else if (file.isFile() && file.getName().endsWith(".class")) {
        String classpath = file.getPath()
          .substring(0, file.getPath().length() - 6)
          .replace(parentPath, "");

        if (classpath.startsWith("/") || classpath.startsWith("\\")) {
          classpath = classpath.substring(1);
        }

        classpath = classpath
          .replace("/", ".")
          .replace("\\", ".");

        Class<?> clazz = CLASS_LOADER.loadClass(classpath);
        consumer.accept(clazz);
      }
    }
  }

  public static void execute(Consumer<Class<?>> consumer, String... packageNames) throws Exception {
    ClassScan.consumer = consumer;
    Class<?> handlerScanClass = ClassScan.class;
    URL url = handlerScanClass.getResource("");

    if (url.getProtocol().equals("file")) {
      final String parentPath = new File(handlerScanClass.getResource("/").toURI()).toString();

      for (String packageName : packageNames) {
        URI resource = handlerScanClass.getResource("/" + packageName).toURI();
        installHandler(new File(resource), parentPath);
      }
    } else if (url.getProtocol().equals("jar")) {
      JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
      Enumeration<JarEntry> entries = jarURLConnection.getJarFile().entries();

      while (entries.hasMoreElements()) {
        JarEntry jarEntry = entries.nextElement();
        String jarEntryName = jarEntry.getName();

        if (!jarEntry.isDirectory() && jarEntryName.endsWith(".class")) {
          boolean flag = false;

          for (String packageName : packageNames) {
            if (jarEntryName.contains(packageName)) {
              flag = true;
              break;
            }
          }

          if (flag) {
            Class<?> clazz = CLASS_LOADER.loadClass(
              jarEntryName
                .substring(0, jarEntryName.length() - 6)
                .replace("/", ".")
            );
            consumer.accept(clazz);
          }
        }
      }
    } else {
      System.out.println("ERROR: Environment unrecognized");
    }
  }
}
