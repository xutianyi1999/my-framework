package club.koumakan.web.framework.business.user.controller;

import club.koumakan.web.framework.ReceiveHandler;
import club.koumakan.web.framework.abstracts.AbstractController;
import club.koumakan.web.framework.business.proxy.user.UserDaoProxy;
import club.koumakan.web.framework.business.proxy.user.UserInfo;
import club.koumakan.web.framework.utils.MessageUtils;
import club.koumakan.web.framework.utils.TransformUtils;
import com.google.common.base.Strings;
import io.vavr.Function1;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

public class UserController extends AbstractController {

  private static final String USER_DAO = "userDao";
  private static final Consumer<JsonObject> REMOVE_PASSWORD = jsonObject -> jsonObject.getJsonObject("userInfo").remove("password");

  private static final Function1<MultiMap, UserInfo> MULTI_MAP_TO_ENTITY =
    TransformUtils.multiMapToEntity(jsonObject -> jsonObject.mapTo(UserInfo.class));

  @Override
  public void execute() {
    final UserDaoProxy userDaoProxy = UserDaoProxy.createProxy(vertx, USER_DAO);

    ReceiveHandler.receiveHandler(router, "/api/user")
      .get("/list", ctx -> {
        MultiMap params = ctx.request().params();
        HttpServerResponse response = ctx.response();

        if (params.contains("pageSize") && params.contains("pageIndex")) {
          UserInfo userInfo = MULTI_MAP_TO_ENTITY.apply(params);

          Mono.zip(
            Mono.<Long>create(monoSink -> userDaoProxy.count(userInfo, result -> {
              if (result.succeeded()) {
                monoSink.success(result.result());
              } else {
                monoSink.error(result.cause());
              }
            })),
            Mono.<List<JsonObject>>create(monoSink -> userDaoProxy.findUserAndRoleByPage(
              userInfo,
              Integer.parseInt(params.get("pageIndex")),
              Integer.parseInt(params.get("pageSize")),
              result -> {
                if (result.succeeded()) {
                  result.result().forEach(REMOVE_PASSWORD);
                  monoSink.success(result.result());
                } else {
                  monoSink.error(result.cause());
                }
              }
            ))
          ).subscribe(result -> response.end(MessageUtils.success(
            new JsonObject()
              .put("totalCount", result.getT1())
              .put("list", result.getT2())
          )), throwable -> {
            throwable.printStackTrace();
            response.end(MessageUtils.error());
          });
        } else {
          response.end(MessageUtils.error());
        }
      })

      .post("/save", ctx -> {
        HttpServerResponse response = ctx.response();
        UserInfo userInfo = MULTI_MAP_TO_ENTITY.apply(ctx.request().params());

        if (!Strings.isNullOrEmpty(userInfo.getUsername())
          && !Strings.isNullOrEmpty(userInfo.getPassword())
          && !Strings.isNullOrEmpty(userInfo.getEmail())
          && userInfo.getRoleId() != null) {

          userDaoProxy.save(userInfo.setCreateTime(System.currentTimeMillis()), result -> {
            if (result.succeeded()) {
              response.end(MessageUtils.success());
            } else {
              result.cause().printStackTrace();
              response.end(MessageUtils.error());
            }
          });
        } else {
          response.end(MessageUtils.error());
        }
      })

      .post("/edit", ctx -> {
        HttpServerResponse response = ctx.response();
        UserInfo userInfo = MULTI_MAP_TO_ENTITY.apply(ctx.request().params());

        if (userInfo.getId() != null && Strings.isNullOrEmpty(userInfo.getPassword())) {
          userDaoProxy.edit(userInfo, result -> {
            if (result.succeeded()) {
              response.end(MessageUtils.success());
            } else {
              result.cause().printStackTrace();
              response.end(MessageUtils.error());
            }
          });
        } else {
          response.end(MessageUtils.error());
        }
      })

      .post("/delete", ctx -> {
        HttpServerResponse response = ctx.response();
        List list = new JsonArray(ctx.request().getParam("idList")).getList();

        userDaoProxy.delete(list, result -> {
          if (result.succeeded()) {
            response.end(MessageUtils.success());
          } else {
            result.cause().printStackTrace();
            response.end(MessageUtils.error());
          }
        });
      })

      .get("/isUsernameExist", ctx -> {
        HttpServerResponse response = ctx.response();
        UserInfo userInfo = MULTI_MAP_TO_ENTITY.apply(ctx.queryParams());

        if (!Strings.isNullOrEmpty(userInfo.getUsername())) {
//          userDaoProxy.isUsernameExist(userInfo.getUsername(), result -> {
//            if (result.succeeded()) {
//              response.end(MessageUtils.success(result.result()));
//            } else {
//              result.cause().printStackTrace();
//              response.end(MessageUtils.error());
//            }
//          });
        } else {
          response.end(MessageUtils.success(false));
        }
      })

      .get("/isEmailExist", ctx -> {
        HttpServerResponse response = ctx.response();
        String email = ctx.request().getParam("email");

        if (!Strings.isNullOrEmpty(email)) {
//          userDaoProxy.isEmailExist(email, result -> {
//            if (result.succeeded()) {
//              response.end(MessageUtils.success(result.result()));
//            } else {
//              result.cause().printStackTrace();
//              response.end(MessageUtils.error());
//            }
//          });
        } else {
          response.end(MessageUtils.success(false));
        }
      });
  }
}
