package club.koumakan.web.framework.business.dictionary.dao;

import club.koumakan.web.framework.Constant;
import club.koumakan.web.framework.PgFactory;
import club.koumakan.web.framework.business.proxy.dictionary.DataDictionaryInfo;
import club.koumakan.web.framework.business.proxy.dictionary.DataDictionaryProxy;
import club.koumakan.web.framework.dsl.tables.DataDictionary;
import club.koumakan.web.framework.utils.TransformUtils;
import io.vavr.Function1;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import org.jooq.Field;
import org.jooq.conf.ParamType;

import java.util.List;
import java.util.Map;

public class DataDictionaryDaoImpl implements DataDictionaryProxy {

  private static final DataDictionary DATA_DICTIONARY = DataDictionary.DATA_DICTIONARY;
  private static final PgPool PG_POOL = PgFactory.client();

  private static final Function1<Row, DataDictionaryInfo> ROW_TO_DICTIONARY =
    TransformUtils.rowToEntity(DataDictionaryInfo::new).apply(TransformUtils.DEFAULT_ROW_TO_JSON_OBJECT);

  private static final Function1<RowSet<Row>, List<DataDictionaryInfo>> ROW_SET_TO_LIST =
    TransformUtils.rowSetToList(ROW_TO_DICTIONARY);

  private static final Function1<JsonObject, Map<Field<?>, Object>> JSON_OBJECT_TO_SQL_MAP =
    TransformUtils.JSON_OBJECT_TO_FIELD_MAP.apply(TransformUtils.DEFAULT_KEY_TO_FIELD.apply(DATA_DICTIONARY));


  @Override
  public void findAll(Handler<AsyncResult<List<DataDictionaryInfo>>> handler) {
    String sql = Constant.CREATE.selectFrom(DATA_DICTIONARY).getSQL(ParamType.INLINED);

    PG_POOL.query(sql, result -> {
      if (result.succeeded()) {
        handler.handle(Future.succeededFuture(ROW_SET_TO_LIST.apply(result.result())));
      } else {
        handler.handle(Future.failedFuture(result.cause()));
      }
    });
  }

  @Override
  public void save(DataDictionaryInfo dataDictionaryInfo, Handler<AsyncResult<Long>> handler) {
    Map<Field<?>, Object> map = JSON_OBJECT_TO_SQL_MAP.apply(dataDictionaryInfo.toJson());
    String sql = Constant.CREATE.insertInto(DATA_DICTIONARY).set(map).returning(DATA_DICTIONARY.ID).getSQL(ParamType.INLINED);

    PG_POOL.query(sql, result -> {
      if (result.succeeded()) {
        result.result().forEach(row -> handler.handle(Future.succeededFuture(row.getLong("id"))));
      } else {
        handler.handle(Future.failedFuture(result.cause()));
      }
    });
  }

  @Override
  public void edit(DataDictionaryInfo dataDictionaryInfo, Handler<AsyncResult<Void>> handler) {
    Map<Field<?>, Object> map = JSON_OBJECT_TO_SQL_MAP.apply(dataDictionaryInfo.toJson());
    String sql = Constant.CREATE.update(DATA_DICTIONARY).set(map).getSQL(ParamType.INLINED);

    PG_POOL.query(sql, result -> {
      if (result.succeeded()) {
        handler.handle(Future.succeededFuture());
      } else {
        handler.handle(Future.failedFuture(result.cause()));
      }
    });
  }

  @Override
  public void delete(List<Long> idList, Handler<AsyncResult<Void>> handler) {
    String sql = Constant.CREATE.deleteFrom(DATA_DICTIONARY).where(DATA_DICTIONARY.ID.in(idList)).getSQL(ParamType.INLINED);

    PG_POOL.query(sql, result -> {
      if (result.succeeded()) {
        handler.handle(Future.succeededFuture());
      } else {
        handler.handle(Future.failedFuture(result.cause()));
      }
    });
  }
}
