package club.koumakan.web.framework.abstracts;

import io.vertx.core.Vertx;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;

public abstract class AbstractController {

  protected Vertx vertx;
  protected Router router;
  protected JWTAuth jwtAuth;

  public AbstractController init(Vertx vertx, Router router, JWTAuth jwtAuth) {
    this.vertx = vertx;
    this.router = router;
    this.jwtAuth = jwtAuth;
    return this;
  }

  public abstract void execute();
}
