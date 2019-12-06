package club.koumakan.web.framework.utils.transform;

import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vertx.core.json.JsonObject;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.TableImpl;

import java.util.Map;
import java.util.stream.Collectors;

public interface FieldTransform {

  /**
   * json转FieldMap
   */
  Function2<Function2<String, Object, Tuple2<Field<?>, Object>>, JsonObject, Map<Field<?>, Object>> JSON_OBJECT_TO_FIELD_MAP =
    (toTuple, jsonObject) -> jsonObject.stream()
      .map(element -> toTuple.apply(element.getKey(), element.getValue()))
      .filter(tuple -> tuple._1() != null)
      .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));

  /**
   * 默认key转field
   */
  Function2<TableImpl<? extends Record>, String, Field<?>> DEFAULT_KEY_TO_FIELD =
    (table, key) -> table.field(TransformCommons.LOWER_CAMEL_TO_LOWER_UNDERSCORE.apply(key));


  /**
   * 默认json转fieldMap
   */
  Function2<TableImpl<? extends Record>, JsonObject, Map<Field<?>, Object>> DEFAULT_JSON_OBJECT_TO_FIELD_MAP =
    (table, jsonObject) -> JSON_OBJECT_TO_FIELD_MAP
      .apply((key, value) -> new Tuple2<>(DEFAULT_KEY_TO_FIELD.apply(table, key), value))
      .apply(jsonObject);
}
