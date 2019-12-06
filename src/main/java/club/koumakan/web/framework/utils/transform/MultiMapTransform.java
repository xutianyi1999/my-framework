package club.koumakan.web.framework.utils.transform;

import com.google.common.collect.Streams;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import reactor.core.publisher.Flux;

import java.util.Map;

public interface MultiMapTransform {

  static Flux<Map.Entry<String, String>> toFlux(MultiMap multiMap) {
    return Flux.fromStream(Streams.stream(multiMap.iterator()));
  }

  /**
   * 复数map转实体类
   * @param jsonObjectToEntity json转实体类 function
   * @param <T> 实体类类型
   * @return map转实体类功能
   */
  static <T> Function2<Function2<String, String, Tuple2<String, Object>>, MultiMap, T> multiMapToEntity(Function1<JsonObject, T> jsonObjectToEntity) {
    return (toTuple, multiMap) -> jsonObjectToEntity.apply(
      toFlux(multiMap)
        .map(entry -> toTuple.apply(entry.getKey(), entry.getValue()))
        .reduce(new JsonObject(), (jsonObject, tuple) -> jsonObject.put(tuple._1(), tuple._2()))
        .block()
    );
  }

  static <T> Function1<MultiMap, T> defaultMultiMapToEntity(Function1<JsonObject, T> jsonObjectToEntity) {
    return multiMap -> jsonObjectToEntity.apply(
      toFlux(multiMap)
        .reduce(new JsonObject(), (jsonObject, entry) -> jsonObject.put(entry.getKey(), entry.getValue()))
        .block()
    );
  }
}
