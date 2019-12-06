package club.koumakan.web.framework.utils.transform;

import com.google.common.collect.Streams;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

public interface RowTransform {

  /**
   * row转json
   */
  Function2<Function2<String, Object, Tuple2<String, Object>>, Row, JsonObject> ROW_TO_JSON_OBJECT =
    (toTuple, row) -> Flux.range(0, row.size())
      .map(i -> toTuple.apply(row.getColumnName(i), row.getValue(i)))
      .reduce(new JsonObject(), (jsonObject, tuple) -> jsonObject.put(tuple._1(), tuple._2()))
      .block();

  /**
   * row转实体类
   * @param jsonObjectToEntity json转实体类
   * @param <T> 实体类类型
   * @return function
   */
  static <T> Function2<Function1<Row, JsonObject>, Row, T> rowToEntity(Function1<JsonObject, T> jsonObjectToEntity) {
    return (rowToJsonObject, row) -> jsonObjectToEntity.apply(rowToJsonObject.apply(row));
  }

  /**
   * rows转实体类list
   * @param toEntity row转实体类函数
   * @param <T> 实体类
   * @return 实体类list
   */
  static <T> Function1<RowSet<Row>, List<T>> rowSetToList(Function1<Row, T> toEntity) {
    return rows -> Streams.stream(rows.iterator())
      .map(toEntity)
      .collect(Collectors.toList());
  }

  /**
   * 默认row转json
   */
  Function1<Row, JsonObject> DEFAULT_ROW_TO_JSON_OBJECT = ROW_TO_JSON_OBJECT.apply(
    (column, value) -> new Tuple2<>(TransformCommons.LOWER_UNDERSCORE_TO_LOWER_CAMEL.apply(column), value)
  );

  /**
   * 默认row转实体类
   * @param jsonObjectToEntity json转实体类
   * @param <T> 实体类类型
   * @return function
   */
  static <T> Function1<Row, T> defaultRowToEntity(Function1<JsonObject, T> jsonObjectToEntity) {
    return row -> jsonObjectToEntity.apply(DEFAULT_ROW_TO_JSON_OBJECT.apply(row));
  }
}
