package club.koumakan.web.framework.business.security;

import club.koumakan.web.framework.business.proxy.security.SecurityProxy;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

public class SecurityVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    new ServiceBinder(vertx)
      .setAddress("security")
      .register(SecurityProxy.class, new SecurityImpl());
    startPromise.complete();
  }
}
