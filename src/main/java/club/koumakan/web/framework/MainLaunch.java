package club.koumakan.web.framework;

import io.vertx.core.Launcher;

public class MainLaunch {

  public static void main(String[] args) {
    Launcher.main(new String[]{"run", "club.koumakan.web.framework.MainVerticle"});
  }
}
