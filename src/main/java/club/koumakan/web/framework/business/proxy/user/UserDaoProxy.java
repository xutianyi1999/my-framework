package club.koumakan.web.framework.business.proxy.user;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

@ProxyGen
public interface UserDaoProxy {

  static UserDaoProxy createProxy(Vertx vertx, String address) {
    return new UserDaoProxyVertxEBProxy(vertx, address);
  }

  void findUserAndRoleByPage(UserInfo userInfo,
                             Integer pageNum,
                             Integer pageSize,
                             Handler<AsyncResult<List<JsonObject>>> handler);

  void findUserAndRole(UserInfo userInfo, Handler<AsyncResult<List<JsonObject>>> handler);

  void save(UserInfo userInfo, Handler<AsyncResult<Long>> handler);

  void edit(UserInfo userInfo, Handler<AsyncResult<Void>> handler);

  void delete(List<Long> idList, Handler<AsyncResult<Void>> handler);

  void isUsernameExist(String username, Handler<AsyncResult<Boolean>> handler);

  void isEmailExist(String email, Handler<AsyncResult<Boolean>> handler);

  void count(UserInfo userInfo, Handler<AsyncResult<Long>> handler);
}
