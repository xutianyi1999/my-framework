package club.koumakan.web.framework.business.role.dao;

import club.koumakan.web.framework.Constant;
import club.koumakan.web.framework.FrameworkFactory;
import club.koumakan.web.framework.business.proxy.role.RoleDaoProxy;
import club.koumakan.web.framework.business.proxy.role.RoleInfo;
import club.koumakan.web.framework.dsl.tables.Role;
import club.koumakan.web.framework.utils.transform.RowTransform;
import io.vavr.Function1;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import org.jooq.conf.ParamType;

import java.util.List;

public class RoleDaoImpl implements RoleDaoProxy {

  private static final Role ROLE = Role.ROLE;
  private static final PgPool PG_POOL = FrameworkFactory.pgPool();

  private static final Function1<Row, RoleInfo> ROW_TO_ENTITY = RowTransform.defaultRowToEntity(RoleInfo::new);
  private static final Function1<RowSet<Row>, List<RoleInfo>> ROW_SET_TO_ROLE_LIST = RowTransform.rowSetToList(ROW_TO_ENTITY);
  private static final String FIND_ALL_SQL = Constant.CREATE.selectFrom(ROLE).getSQL(ParamType.INLINED);

  @Override
  public void findAll(Handler<AsyncResult<List<RoleInfo>>> handler) {
    PG_POOL.query(FIND_ALL_SQL, result -> {
      if (result.succeeded()) {
        handler.handle(Future.succeededFuture(ROW_SET_TO_ROLE_LIST.apply(result.result())));
      } else {
        handler.handle(Future.failedFuture(result.cause()));
      }
    });
  }

  @Override
  public void findById(Long id, Handler<AsyncResult<RoleInfo>> handler) {
    PG_POOL.query(Constant.CREATE.selectFrom(ROLE).where(ROLE.ID.eq(id)).getSQL(ParamType.INLINED), result -> {
      if (result.succeeded()) {
        RowSet<Row> rowSet = result.result();

        if (rowSet.rowCount() == 1) {
          rowSet.forEach(row -> handler.handle(Future.succeededFuture(ROW_TO_ENTITY.apply(row))));
        } else {
          handler.handle(Future.succeededFuture(null));
        }
      } else {
        handler.handle(Future.failedFuture(result.cause()));
      }
    });
  }

  @Override
  public void save(RoleInfo roleInfo, Handler<AsyncResult<Long>> handler) {

  }

  @Override
  public void edit(RoleInfo roleInfo, Handler<AsyncResult<Void>> handler) {

  }

  @Override
  public void delete(List<Long> idList, Handler<AsyncResult<Void>> handler) {

  }
}
