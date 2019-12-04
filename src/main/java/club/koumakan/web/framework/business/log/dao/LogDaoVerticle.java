package club.koumakan.web.framework.business.log.dao;

import club.koumakan.web.framework.FrameworkFactory;
import club.koumakan.web.framework.dsl.tables.Log;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;

public class LogDaoVerticle extends AbstractVerticle {

  private static final Log LOG = Log.LOG;
  private static final PgPool PG_POOL = FrameworkFactory.pgPool();

  @Override
  public void start(Promise<Void> startPromise) {
    EventBus eventBus = vertx.eventBus();

    eventBus.consumer("logDao", (Handler<Message<JsonObject>>) args -> {
//      JsonObject document = args.body().getJsonObject(FrameworkUtils.DOCUMENT);
//
//      Map<Field<?>, Object> map = FrameworkUtils.jsonObjectToSQLMap(
//        LOG, document, MapTransform.LOWER_CAMEL_TO_LOWER_UNDERSCORE, null
//      );

//      String sql = Constant.CREATE.insertInto(LOG)
//        .set(map).getSQL(ParamType.INLINED);
//
//      PG_POOL.query(sql, result -> {
//        if (result.failed()) {
//          result.cause().printStackTrace();
//        }
//      });
    });
    startPromise.complete();
  }
}
