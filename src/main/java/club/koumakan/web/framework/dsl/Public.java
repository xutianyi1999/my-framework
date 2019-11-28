/*
 * This file is generated by jOOQ.
 */
package club.koumakan.web.framework.dsl;


import club.koumakan.web.framework.dsl.tables.DataDictionary;
import club.koumakan.web.framework.dsl.tables.Log;
import club.koumakan.web.framework.dsl.tables.Role;
import club.koumakan.web.framework.dsl.tables.User;
import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
public class Public extends SchemaImpl {

  /**
   * The reference instance of <code>public</code>
   */
  public static final Public PUBLIC = new Public();
  private static final long serialVersionUID = 48789130;
  /**
   * The table <code>public.data_dictionary</code>.
   */
  public final DataDictionary DATA_DICTIONARY = club.koumakan.web.framework.dsl.tables.DataDictionary.DATA_DICTIONARY;

  /**
   * The table <code>public.log</code>.
   */
  public final Log LOG = club.koumakan.web.framework.dsl.tables.Log.LOG;

  /**
   * The table <code>public.role</code>.
   */
  public final Role ROLE = club.koumakan.web.framework.dsl.tables.Role.ROLE;

  /**
   * The table <code>public.user</code>.
   */
  public final User USER = club.koumakan.web.framework.dsl.tables.User.USER;

  /**
   * No further instances allowed
   */
  private Public() {
    super("public", null);
  }


  @Override
  public Catalog getCatalog() {
    return DefaultCatalog.DEFAULT_CATALOG;
  }

  @Override
  public final List<Sequence<?>> getSequences() {
    List result = new ArrayList();
    result.addAll(getSequences0());
    return result;
  }

  private final List<Sequence<?>> getSequences0() {
    return Arrays.<Sequence<?>>asList(
      Sequences.DATA_DICTIONARY_ID_SEQ,
      Sequences.LOG_ID_SEQ,
      Sequences.ROLE_ID_SEQ,
      Sequences.USER_ID_SEQ);
  }

  @Override
  public final List<Table<?>> getTables() {
    List result = new ArrayList();
    result.addAll(getTables0());
    return result;
  }

  private final List<Table<?>> getTables0() {
    return Arrays.<Table<?>>asList(
      DataDictionary.DATA_DICTIONARY,
      Log.LOG,
      Role.ROLE,
      User.USER);
  }
}