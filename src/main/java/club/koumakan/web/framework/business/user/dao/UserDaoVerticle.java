package club.koumakan.web.framework.business.user.dao;

import club.koumakan.web.framework.business.proxy.security.SecurityProxy;
import club.koumakan.web.framework.business.proxy.user.UserDaoProxy;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

public class UserDaoVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    new ServiceBinder(vertx)
      .setAddress("userDao")
      .register(
        UserDaoProxy.class,
        new UserDaoImpl(SecurityProxy.createProxy(vertx, "security"))
      );
    startPromise.complete();
  }
}
