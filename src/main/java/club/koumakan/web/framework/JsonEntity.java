package club.koumakan.web.framework;

import io.vertx.core.json.JsonObject;

public interface JsonEntity {

  JsonObject toJson();
}
