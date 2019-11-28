package club.koumakan.web.framework.business.proxy.security;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
public interface SecurityProxy {

  static SecurityProxy createProxy(Vertx vertx, String address) { // <5>
    return new SecurityProxyVertxEBProxy(vertx, address);
  }

  void encrypt(String password, Handler<AsyncResult<String>> handler);

  void verify(String password, String hash, Handler<AsyncResult<Boolean>> handler);
}
