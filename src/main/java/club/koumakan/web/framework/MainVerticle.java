package club.koumakan.web.framework;

import club.koumakan.web.framework.abstracts.AbstractController;
import club.koumakan.web.framework.business.dictionary.dao.DataDictionaryDaoVerticle;
import club.koumakan.web.framework.utils.ClassScan;
import com.google.common.collect.Sets;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class MainVerticle extends AbstractVerticle {

  private static final String ABSTRACT_CONTROLLER = AbstractController.class.getName();
  private static final String ABSTRACT_VERTICLE = AbstractVerticle.class.getName();

  private static final DeploymentOptions DEPLOYMENT_OPTIONS = new DeploymentOptions().setInstances(8);
  private static final Logger LOGGER = Logger.getGlobal();

  @Override
  public void start(Promise<Void> startPromise) {
    PgFactory.pgPool = initPgPool();
    JWTAuth jwtAuth = initJWTAuth();
    Router router = initRouter(jwtAuth);

    List<String> filterClassList = List.of(
      DataDictionaryDaoVerticle.class.getName(),
      MainVerticle.class.getName()
    );

    vertx.executeBlocking(blockingPromise -> {
      try {
        ClassScan.execute(clazz -> {
          try {
            if (filterClassList.contains(clazz.getName())) {
              return;
            }

            Type type = clazz.getGenericSuperclass();

            if (type != null) {
              if (type.getTypeName().equals(ABSTRACT_VERTICLE)) {
                vertx.deployVerticle((Class<? extends Verticle>) clazz, DEPLOYMENT_OPTIONS, result -> {
                  if (result.succeeded()) {
                    LOGGER.info("Success in deploying: " + clazz.getName());
                  } else {
                    LOGGER.severe("Error in deploying: " + clazz.getName());
                  }
                });
              } else if (type.getTypeName().equals(ABSTRACT_CONTROLLER)) {
                AbstractController abstractController = (AbstractController) clazz.getConstructor().newInstance();
                abstractController.init(vertx, router, jwtAuth).execute();
                LOGGER.info("Controller install: " + clazz.getName());
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }, "club/koumakan");

        blockingPromise.complete();
      } catch (Exception e) {
        blockingPromise.fail(e.getCause());
      }
    }, result -> {
      if (result.succeeded()) {
        vertx.createHttpServer().requestHandler(router)
          .listen(8888, http -> {
            if (http.succeeded()) {
              startPromise.complete();
              System.out.println("HTTP server started on port 8888");
            } else {
              startPromise.fail(http.cause());
            }
          });
      } else {
        startPromise.fail(result.cause());
      }
    });
  }

  private JWTAuth initJWTAuth() {
    return JWTAuth.create(vertx, new JWTAuthOptions()
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm("HS256")
        .setPublicKey(UUID.randomUUID().toString())
        .setSymmetric(true)));
  }

  private Router initRouter(JWTAuth jwtAuth) {
    Router router = Router.router(vertx);

    router.route().handler(
      CorsHandler.create("*")
        .allowedMethods(Sets.newHashSet(HttpMethod.values()))
    );

//    router.route("/api/*").handler(JWTAuthHandler.create(jwtAuth).addAuthority("role:admin"));
    return router;
  }

  private PgPool initPgPool() {
    return PgPool.pool(
      vertx,
      new PgConnectOptions()
        .setPort(5432)
        .setHost("192.168.199.216")
        .setDatabase("framework")
        .setUser("postgres")
        .setPassword("123"),
      new PoolOptions()
    );
  }
}
