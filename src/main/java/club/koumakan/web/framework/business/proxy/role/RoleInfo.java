package club.koumakan.web.framework.business.proxy.role;

import club.koumakan.web.framework.JsonEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true)
public class RoleInfo implements JsonEntity {

  private Long id;
  private String name;
  private String code;

  public Long getId() {
    return id;
  }

  public RoleInfo setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public RoleInfo setName(String name) {
    this.name = name;
    return this;
  }

  public String getCode() {
    return code;
  }

  public RoleInfo setCode(String code) {
    this.code = code;
    return this;
  }

  public RoleInfo(JsonObject jsonObject) {
    RoleInfoConverter.fromJson(jsonObject, this);
  }

  public RoleInfo() {
  }

  @Override
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    RoleInfoConverter.toJson(this, json);
    return json;
  }
}
