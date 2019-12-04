package club.koumakan.web.framework;

import io.vertx.ext.web.Router;
import io.vertx.pgclient.PgPool;

public class FrameworkFactory {

  static PgPool pgPool;
  static Router router;

  public static Router router() {
    return router;
  }

  public static PgPool pgPool() {
    return pgPool;
  }
}
