package club.koumakan.web.framework.utils;

import io.vertx.core.json.JsonObject;

public interface MessageUtils {

  int SUCCESS_CODE = 20000;
  int ERROR_CODE = 50000;

  static String success() {
    return new JsonObject().put("code", SUCCESS_CODE).toString();
  }

  static String success(Object data) {
    return new JsonObject()
      .put("code", SUCCESS_CODE)
      .put("data", data).toString();
  }

  static String error() {
    return new JsonObject().put("code", ERROR_CODE).toString();
  }

  static String error(Object data) {
    return new JsonObject()
      .put("code", ERROR_CODE)
      .put("data", data).toString();
  }
}
