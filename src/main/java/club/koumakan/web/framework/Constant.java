package club.koumakan.web.framework;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public interface Constant {

  DSLContext CREATE = DSL.using(SQLDialect.POSTGRES);
}
