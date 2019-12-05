package club.koumakan.web.framework.business.user.dao;

import club.koumakan.web.framework.FrameworkFactory;
import club.koumakan.web.framework.business.proxy.role.RoleInfo;
import club.koumakan.web.framework.business.proxy.security.SecurityProxy;
import club.koumakan.web.framework.business.proxy.user.UserDaoProxy;
import club.koumakan.web.framework.business.proxy.user.UserInfo;
import club.koumakan.web.framework.dsl.tables.Role;
import club.koumakan.web.framework.dsl.tables.User;
import club.koumakan.web.framework.utils.TransformUtils;
import com.google.common.collect.Lists;
import io.vavr.Function1;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.conf.ParamType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static club.koumakan.web.framework.Constant.CREATE;

public class UserDaoImpl implements UserDaoProxy {

  private static final User USER = User.USER;
  private static final Role ROLE = Role.ROLE;
  private static final PgPool PG_POOL = FrameworkFactory.pgPool();

  private static final Function1<RowSet<Row>, List<JsonObject>> ROW_SET_TO_USER_AND_ROLE_LIST =
    TransformUtils.rowSetToList(TransformUtils.rowToEntity(jsonObject -> new JsonObject()
      .put("userInfo", new UserInfo(jsonObject).setId(jsonObject.getLong("userId")).toJson())
      .put("roleInfo", new RoleInfo(jsonObject).setId(jsonObject.getLong("roleId")).toJson())
    ).apply(TransformUtils.DEFAULT_ROW_TO_JSON_OBJECT));

  private static final Function1<RowSet<Row>, List<UserInfo>> ROW_SET_TO_USER_INFO_LIST =
    TransformUtils.rowSetToList(TransformUtils.rowToEntity(UserInfo::new).apply(TransformUtils.DEFAULT_ROW_TO_JSON_OBJECT));

  private static final Function1<JsonObject, Map<Field<?>, Object>> JSON_OBJECT_TO_USER_FIELD_MAP =
    TransformUtils.JSON_OBJECT_TO_FIELD_MAP.apply(TransformUtils.DEFAULT_KEY_TO_FIELD.apply(USER));

  private final SecurityProxy securityProxy;

  public UserDaoImpl(SecurityProxy securityProxy) {
    this.securityProxy = securityProxy;
  }

  private static List<Condition> userInfoCondition(UserInfo userInfo) {
    ArrayList<Condition> conditionList = Lists.newArrayList();

    if (userInfo.getId() != null) {
      conditionList.add(USER.ID.eq(userInfo.getId()));
    }

    if (userInfo.getUsername() != null) {
      conditionList.add(USER.USERNAME.like("%" + userInfo.getUsername() + "%"));
    }

    if (userInfo.getEmail() != null) {
      conditionList.add(USER.EMAIL.like("%" + userInfo.getEmail() + "%"));
    }

    if (userInfo.getRoleId() != null) {
      conditionList.add(USER.ROLE_ID.eq(userInfo.getRoleId()));
    }

    if (userInfo.getStatus() != null) {
      conditionList.add(USER.STATUS.eq(userInfo.getStatus()));
    }
    return conditionList;
  }

  private static void findUserAndRoleCommon(String sql, Handler<AsyncResult<List<JsonObject>>> handler) {
    PG_POOL.query(sql, result -> {
      if (result.succeeded()) {
        handler.handle(Future.succeededFuture(ROW_SET_TO_USER_AND_ROLE_LIST.apply(result.result())));
      } else {
        handler.handle(Future.failedFuture(result.cause()));
      }
    });
  }

  @Override
  public void findUserAndRoleByPage(UserInfo userInfo, Integer pageNum, Integer pageSize, Handler<AsyncResult<List<JsonObject>>> handler) {
    Integer offset = (pageNum - 1) * pageSize;

    findUserAndRoleCommon(
      CREATE
        .select(USER.fields())
        .select(USER.ID.as("user_id"))
        .select(ROLE.fields())
        .from(USER).leftJoin(ROLE).on(USER.ROLE_ID.eq(ROLE.ID))
        .where(userInfoCondition(userInfo))
        .orderBy(USER.ID.desc())
        .limit(offset, pageSize)
        .getSQL(ParamType.INLINED),
      handler
    );
  }

  @Override
  public void findUserAndRole(UserInfo userInfo, Handler<AsyncResult<List<JsonObject>>> handler) {
    findUserAndRoleCommon(
      CREATE
        .select(USER.fields())
        .select(USER.ID.as("user_id"))
        .select(ROLE.fields())
        .from(USER).leftJoin(ROLE).on(USER.ROLE_ID.eq(ROLE.ID))
        .where(userInfoCondition(userInfo))
        .getSQL(ParamType.INLINED),
      handler
    );
  }

  @Override
  public void findUser(UserInfo userInfo, Handler<AsyncResult<List<UserInfo>>> handler) {

  }

  @Override
  public void save(UserInfo userInfo, Handler<AsyncResult<Long>> handler) {
    securityProxy.encrypt(userInfo.getPassword(), args -> {
      if (args.succeeded()) {
        PG_POOL.query(CREATE.insertInto(USER)
            .set(JSON_OBJECT_TO_USER_FIELD_MAP.apply(userInfo.setPassword(args.result()).toJson()))
            .returning(USER.ID)
            .getSQL(ParamType.INLINED),
          result -> {
            if (result.succeeded()) {
              result.result().forEach(row -> handler.handle(Future.succeededFuture(row.getLong("id"))));
            } else {
              handler.handle(Future.failedFuture(result.cause()));
            }
          });
      } else {
        handler.handle(Future.failedFuture(args.cause()));
      }
    });
  }

  @Override
  public void edit(UserInfo userInfo, Handler<AsyncResult<Void>> handler) {
    PG_POOL.query(CREATE.update(USER)
        .set(JSON_OBJECT_TO_USER_FIELD_MAP.apply(userInfo.toJson()))
        .where(USER.ID.eq(userInfo.getId()))
        .getSQL(ParamType.INLINED),
      result -> {
        if (result.succeeded()) {
          handler.handle(Future.succeededFuture());
        } else {
          handler.handle(Future.failedFuture(result.cause()));
        }
      });
  }

  @Override
  public void delete(List<Long> idList, Handler<AsyncResult<Void>> handler) {
    PG_POOL.query(CREATE.deleteFrom(USER)
        .where(USER.ID.in(idList))
        .getSQL(ParamType.INLINED),
      result -> {
        if (result.succeeded()) {
          handler.handle(Future.succeededFuture());
        } else {
          handler.handle(Future.failedFuture(result.cause()));
        }
      }
    );
  }

  @Override
  public void count(UserInfo userInfo, Handler<AsyncResult<Long>> handler) {
    PG_POOL.query(CREATE.selectCount()
        .from(USER)
        .where(userInfoCondition(userInfo))
        .getSQL(ParamType.INLINED),
      result -> {
        if (result.succeeded()) {
          result.result().forEach(row -> handler.handle(Future.succeededFuture(row.getLong("count"))));
        } else {
          handler.handle(Future.failedFuture(result.cause()));
        }
      }
    );
  }
}
