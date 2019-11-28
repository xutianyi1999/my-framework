package club.koumakan.web.framework.business.log.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogInfo {

  private Long id;
  private Short type;
  private String message;
  private Long createTime;

  public Long getId() {
    return id;
  }

  public LogInfo setId(Long id) {
    this.id = id;
    return this;
  }

  public Short getType() {
    return type;
  }

  public LogInfo setType(Short type) {
    this.type = type;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public LogInfo setMessage(String message) {
    this.message = message;
    return this;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public LogInfo setCreateTime(Long createTime) {
    this.createTime = createTime;
    return this;
  }
}
