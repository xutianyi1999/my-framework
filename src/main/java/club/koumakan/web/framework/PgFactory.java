package club.koumakan.web.framework;

import io.vertx.pgclient.PgPool;

public class PgFactory {

  static PgPool pgPool;

  public static PgPool client() {
    return pgPool;
  }
}
