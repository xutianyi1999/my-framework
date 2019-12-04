package club.koumakan.web.framework;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class HttpServerVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer().requestHandler(FrameworkFactory.router())
      .listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
        } else {
          startPromise.fail(http.cause());
        }
      });
  }
}
