package club.koumakan.web.framework.business.security;

import club.koumakan.web.framework.business.proxy.security.SecurityProxy;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.nio.charset.StandardCharsets;

public class SecurityImpl implements SecurityProxy {

  private static final Argon2 ARGON_2 = Argon2Factory.create();

  @Override
  public void encrypt(String password, Handler<AsyncResult<String>> handler) {
    handler.handle(Future.succeededFuture(
      ARGON_2.hash(4, 64, 1, password.getBytes(StandardCharsets.UTF_8))
    ));
  }

  @Override
  public void verify(String password, String hash, Handler<AsyncResult<Boolean>> handler) {
    handler.handle(Future.succeededFuture(
      ARGON_2.verify(hash, password.getBytes(StandardCharsets.UTF_8))
    ));
  }
}
