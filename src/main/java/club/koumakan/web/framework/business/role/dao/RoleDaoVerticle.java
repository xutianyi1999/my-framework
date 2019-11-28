package club.koumakan.web.framework.business.role.dao;

import club.koumakan.web.framework.business.proxy.role.RoleDaoProxy;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

public class RoleDaoVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    new ServiceBinder(vertx)
      .setAddress("roleDao")
      .register(RoleDaoProxy.class, new RoleDaoImpl());
    startPromise.complete();
  }
}
