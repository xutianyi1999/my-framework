/*
 * This file is generated by jOOQ.
 */
package club.koumakan.web.framework.dsl.tables.records;


import club.koumakan.web.framework.dsl.tables.User;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.processing.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
  value = {
    "http://www.jooq.org",
    "jOOQ version:3.12.3"
  },
  comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record7<Long, String, String, String, Long, Long, Short> {

  private static final long serialVersionUID = -1675772243;

  /**
   * Create a detached UserRecord
   */
  public UserRecord() {
    super(User.USER);
  }

  /**
   * Create a detached, initialised UserRecord
   */
  public UserRecord(Long id, String username, String password, String email, Long createTime, Long roleId, Short status) {
    super(User.USER);

    set(0, id);
    set(1, username);
    set(2, password);
    set(3, email);
    set(4, createTime);
    set(5, roleId);
    set(6, status);
  }

  /**
   * Getter for <code>public.user.id</code>.
   */
  public Long getId() {
    return (Long) get(0);
  }

  /**
   * Setter for <code>public.user.id</code>.
   */
  public void setId(Long value) {
    set(0, value);
  }

  /**
   * Getter for <code>public.user.username</code>.
   */
  public String getUsername() {
    return (String) get(1);
  }

  /**
   * Setter for <code>public.user.username</code>.
   */
  public void setUsername(String value) {
    set(1, value);
  }

  /**
   * Getter for <code>public.user.password</code>.
   */
  public String getPassword() {
    return (String) get(2);
  }

  /**
   * Setter for <code>public.user.password</code>.
   */
  public void setPassword(String value) {
    set(2, value);
  }

  /**
   * Getter for <code>public.user.email</code>.
   */
  public String getEmail() {
    return (String) get(3);
  }

  /**
   * Setter for <code>public.user.email</code>.
   */
  public void setEmail(String value) {
    set(3, value);
  }

  /**
   * Getter for <code>public.user.create_time</code>.
   */
  public Long getCreateTime() {
    return (Long) get(4);
  }

  /**
   * Setter for <code>public.user.create_time</code>.
   */
  public void setCreateTime(Long value) {
    set(4, value);
  }

  /**
   * Getter for <code>public.user.role_id</code>.
   */
  public Long getRoleId() {
    return (Long) get(5);
  }

  /**
   * Setter for <code>public.user.role_id</code>.
   */
  public void setRoleId(Long value) {
    set(5, value);
  }

  // -------------------------------------------------------------------------
  // Primary key information
  // -------------------------------------------------------------------------

  /**
   * Getter for <code>public.user.status</code>.
   */
  public Short getStatus() {
    return (Short) get(6);
  }

  // -------------------------------------------------------------------------
  // Record7 type implementation
  // -------------------------------------------------------------------------

  /**
   * Setter for <code>public.user.status</code>.
   */
  public void setStatus(Short value) {
    set(6, value);
  }

  @Override
  public Record1<Long> key() {
    return (Record1) super.key();
  }

  @Override
  public Row7<Long, String, String, String, Long, Long, Short> fieldsRow() {
    return (Row7) super.fieldsRow();
  }

  @Override
  public Row7<Long, String, String, String, Long, Long, Short> valuesRow() {
    return (Row7) super.valuesRow();
  }

  @Override
  public Field<Long> field1() {
    return User.USER.ID;
  }

  @Override
  public Field<String> field2() {
    return User.USER.USERNAME;
  }

  @Override
  public Field<String> field3() {
    return User.USER.PASSWORD;
  }

  @Override
  public Field<String> field4() {
    return User.USER.EMAIL;
  }

  @Override
  public Field<Long> field5() {
    return User.USER.CREATE_TIME;
  }

  @Override
  public Field<Long> field6() {
    return User.USER.ROLE_ID;
  }

  @Override
  public Field<Short> field7() {
    return User.USER.STATUS;
  }

  @Override
  public Long component1() {
    return getId();
  }

  @Override
  public String component2() {
    return getUsername();
  }

  @Override
  public String component3() {
    return getPassword();
  }

  @Override
  public String component4() {
    return getEmail();
  }

  @Override
  public Long component5() {
    return getCreateTime();
  }

  @Override
  public Long component6() {
    return getRoleId();
  }

  @Override
  public Short component7() {
    return getStatus();
  }

  @Override
  public Long value1() {
    return getId();
  }

  @Override
  public String value2() {
    return getUsername();
  }

  @Override
  public String value3() {
    return getPassword();
  }

  @Override
  public String value4() {
    return getEmail();
  }

  @Override
  public Long value5() {
    return getCreateTime();
  }

  @Override
  public Long value6() {
    return getRoleId();
  }

  @Override
  public Short value7() {
    return getStatus();
  }

  @Override
  public UserRecord value1(Long value) {
    setId(value);
    return this;
  }

  @Override
  public UserRecord value2(String value) {
    setUsername(value);
    return this;
  }

  @Override
  public UserRecord value3(String value) {
    setPassword(value);
    return this;
  }

  @Override
  public UserRecord value4(String value) {
    setEmail(value);
    return this;
  }

  @Override
  public UserRecord value5(Long value) {
    setCreateTime(value);
    return this;
  }

  @Override
  public UserRecord value6(Long value) {
    setRoleId(value);
    return this;
  }

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  @Override
  public UserRecord value7(Short value) {
    setStatus(value);
    return this;
  }

  @Override
  public UserRecord values(Long value1, String value2, String value3, String value4, Long value5, Long value6, Short value7) {
    value1(value1);
    value2(value2);
    value3(value3);
    value4(value4);
    value5(value5);
    value6(value6);
    value7(value7);
    return this;
  }
}
