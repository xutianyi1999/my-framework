package club.koumakan.web.framework.business.proxy.dictionary;

import club.koumakan.web.framework.JsonEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true)
public class DataDictionaryInfo implements JsonEntity {

  private Long id;
  private String code;
  private String name;
  private JsonObject data;

  public DataDictionaryInfo() {
  }

  public DataDictionaryInfo(JsonObject jsonObject) {
    DataDictionaryInfoConverter.fromJson(jsonObject, this);
  }
  public Long getId() {
    return id;
  }

  public DataDictionaryInfo setId(Long id) {
    this.id = id;
    return this;
  }

  public String getCode() {
    return code;
  }

  public DataDictionaryInfo setCode(String code) {
    this.code = code;
    return this;
  }

  public String getName() {
    return name;
  }

  public DataDictionaryInfo setName(String name) {
    this.name = name;
    return this;
  }

  public JsonObject getData() {
    return data;
  }

  public DataDictionaryInfo setData(JsonObject data) {
    this.data = data;
    return this;
  }

  @Override
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    DataDictionaryInfoConverter.toJson(this, json);
    return json;
  }
}
