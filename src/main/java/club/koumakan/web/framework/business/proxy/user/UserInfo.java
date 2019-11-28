package club.koumakan.web.framework.business.proxy.user;

import club.koumakan.web.framework.JsonEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true)
public class UserInfo implements JsonEntity {

  private Long id;
  private String username;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String email;
  private Short status;
  private Long createTime;
  private Long roleId;

  public UserInfo() {
  }

  public UserInfo(JsonObject jsonObject) {
    UserInfoConverter.fromJson(jsonObject, this);
  }

  public Long getId() {
    return id;
  }

  public UserInfo setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserInfo setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserInfo setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserInfo setEmail(String email) {
    this.email = email;
    return this;
  }

  public Short getStatus() {
    return status;
  }

  public UserInfo setStatus(Short status) {
    this.status = status;
    return this;
  }

  public Long getRoleId() {
    return roleId;
  }

  public UserInfo setRoleId(Long roleId) {
    this.roleId = roleId;
    return this;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public UserInfo setCreateTime(Long createTime) {
    this.createTime = createTime;
    return this;
  }

  @Override
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    UserInfoConverter.toJson(this, json);
    return json;
  }
}
