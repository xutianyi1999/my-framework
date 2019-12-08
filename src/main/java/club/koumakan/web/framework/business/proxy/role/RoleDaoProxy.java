package club.koumakan.web.framework.business.proxy.role;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

@ProxyGen
public interface RoleDaoProxy {

  static RoleDaoProxy createProxy(Vertx vertx, String address) {
    return new RoleDaoProxyVertxEBProxy(vertx, address);
  }

  void findAll(Handler<AsyncResult<List<RoleInfo>>> handler);

  void save(RoleInfo roleInfo, Handler<AsyncResult<Long>> handler);

  void edit(RoleInfo roleInfo, Handler<AsyncResult<Void>> handler);

  void delete(List<Long> idList, Handler<AsyncResult<Void>> handler);
}
