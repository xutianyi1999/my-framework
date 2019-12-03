package club.koumakan.web.framework.utils;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Streams;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import reactor.core.publisher.Flux;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface TransformUtils {

  DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * 小驼峰转下划线
   */
  Function1<String, String> LOWER_CAMEL_TO_LOWER_UNDERSCORE =
    value -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value);

  /**
   * 下划线转小驼峰
   */
  Function1<String, String> LOWER_UNDERSCORE_TO_LOWER_CAMEL =
    value -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);

  /**
   * json转FieldMap
   */
  Function1<Function1<String, Field<?>>, Function1<JsonObject, Map<Field<?>, Object>>> JSON_OBJECT_TO_FIELD_MAP =
    keyToField -> jsonObject -> jsonObject.stream()
      .map(element -> Tuple.<Field<?>, Object>of(keyToField.apply(element.getKey()), element.getValue()))
      .filter(tuple -> tuple._1() != null)
      .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));

  /**
   * 默认key转换
   */
  Function1<TableImpl<? extends Record>, Function1<String, Field<?>>> DEFAULT_KEY_TO_FIELD =
    table -> key -> table.field(LOWER_CAMEL_TO_LOWER_UNDERSCORE.apply(key));

  /**
   * 复数map转实体类
   * @param jsonObjectToEntity json转实体类 function
   * @param <T> 实体类类型
   * @return map转实体类功能
   */
  static <T> Function1<MultiMap, T> multiMapToEntity(Function1<JsonObject, T> jsonObjectToEntity) {
    return multiMap -> jsonObjectToEntity.apply(Flux.fromStream(Streams.stream(multiMap.iterator()))
      .reduce(new JsonObject(), (jsonObject, element) -> jsonObject.put(element.getKey(), element.getValue()))
      .block()
    );
  }

  /**
   * 默认列转换
   */
  Function2<String, Object, Tuple2<String, Object>> DEFAULT_COLUMN_TRANSFORM =
    (column, value) -> new Tuple2<>(LOWER_UNDERSCORE_TO_LOWER_CAMEL.apply(column), value);

  /**
   * 行转json
   */
  Function1<Function2<String, Object, Tuple2<String, Object>>, Function1<Row, JsonObject>> ROW_TO_JSON_OBJECT =
    toTuple -> row -> Flux.range(0, row.size())
      .map(i -> toTuple.apply(row.getColumnName(i), row.getValue(i)))
      .reduce(new JsonObject(), (jsonObject, tuple) -> jsonObject.put(tuple._1(), tuple._2()))
      .block();

  Function1<Row, JsonObject> DEFAULT_ROW_TO_JSON_OBJECT = ROW_TO_JSON_OBJECT.apply(DEFAULT_COLUMN_TRANSFORM);

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

  static <T> Function1<Function1<Row, JsonObject>, Function1<Row, T>> rowToEntity(Function1<JsonObject, T> jsonObjectToEntity) {
    return rowToJsonObject -> row -> jsonObjectToEntity.apply(rowToJsonObject.apply(row));
  }
}
