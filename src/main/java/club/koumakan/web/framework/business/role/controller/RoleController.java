package club.koumakan.web.framework.business.role.controller;

import club.koumakan.web.framework.ReceiveHandler;
import club.koumakan.web.framework.abstracts.AbstractController;
import club.koumakan.web.framework.business.proxy.role.RoleDaoProxy;
import club.koumakan.web.framework.utils.MessageUtils;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;

public class RoleController extends AbstractController {

  private static final String ROLE_DAO = "roleDao";

  @Override
  public void execute() {
    final RoleDaoProxy roleDaoProxy = RoleDaoProxy.createProxy(vertx, ROLE_DAO);

    ReceiveHandler.receiveHandler(router, "/api/role")
      .get("/list", ctx -> {
        HttpServerResponse response = ctx.response();

        roleDaoProxy.findAll(result -> {
          if (result.succeeded()) {
            response.end(MessageUtils.success(new JsonArray(result.result())));
          } else {
            result.cause().printStackTrace();
            response.end(MessageUtils.error());
          }
        });
      });
  }
}
