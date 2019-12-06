package club.koumakan.web.framework.business;

import club.koumakan.web.framework.abstracts.AbstractController;
import club.koumakan.web.framework.business.proxy.security.SecurityProxy;
import club.koumakan.web.framework.business.proxy.user.UserDaoProxy;
import club.koumakan.web.framework.business.proxy.user.UserInfo;
import club.koumakan.web.framework.throwable.LoginException;
import club.koumakan.web.framework.utils.MessageUtils;
import club.koumakan.web.framework.utils.receive.ReceiveHandler;
import club.koumakan.web.framework.utils.transform.MultiMapTransform;
import com.google.common.base.Strings;
import io.vavr.Function1;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jwt.JWTOptions;
import reactor.core.publisher.Mono;

import java.util.List;

public class LoginController extends AbstractController {

  private static final String USER_DAO = "userDao";
  private static final String SECURITY = "security";
  private static final Function1<MultiMap, UserInfo> MULTI_MAP_TO_USER = MultiMapTransform.defaultMultiMapToEntity(UserInfo::new);

  @Override
  public void execute() {
    final UserDaoProxy userDaoProxy = UserDaoProxy.createProxy(vertx, USER_DAO);
    final SecurityProxy securityProxy = SecurityProxy.createProxy(vertx, SECURITY);

    ReceiveHandler.receiveHandler(router)
      .post("/login", ctx -> {
        HttpServerResponse response = ctx.response();
        UserInfo userInfo = MULTI_MAP_TO_USER.apply(ctx.queryParams());

        if (Strings.isNullOrEmpty(userInfo.getUsername()) || Strings.isNullOrEmpty(userInfo.getPassword())) {
          response.end(MessageUtils.error());
        } else {
          Mono.<JsonObject>create(monoSink -> userDaoProxy.findUserAndRole(new UserInfo().setUsername(userInfo.getUsername()), result -> {
            if (result.succeeded()) {
              List<JsonObject> userAndRoleList = result.result();

              if (userAndRoleList.size() == 1) {
                monoSink.success(userAndRoleList.get(0));
              } else {
                monoSink.error(new LoginException());
              }
            } else {
              monoSink.error(result.cause());
            }
          })).flatMap(userAndRole -> Mono.<JsonObject>create(monoSink -> securityProxy.verify(
            userInfo.getPassword(), (String) userAndRole.getJsonObject("userInfo").remove("password"), result -> {
              if (result.succeeded()) {
                if (result.result()) {
                  monoSink.success(userAndRole.put("jwt", jwtAuth.generateToken(new JsonObject(), new JWTOptions()
                    // 24h
                    .setExpiresInSeconds((int) System.currentTimeMillis() / 1000 + 86400)
                    .setIssuer("koumakan.club")
                    .setPermissions(List.of("role:" + userAndRole.getJsonObject("roleInfo").getString("code")))
                  )));
                } else {
                  monoSink.error(new LoginException());
                }
              } else {
                monoSink.error(result.cause());
              }
            })
          )).subscribe(result -> response.end(MessageUtils.success(result)), throwable -> {
            if (throwable instanceof LoginException) {
              response.end(MessageUtils.error("Login failed"));
            } else {
              throwable.printStackTrace();
              response.end(MessageUtils.error());
            }
          });
        }
      });
  }
}
