/*
 * This file is generated by jOOQ.
 */
package club.koumakan.web.framework.dsl;


import club.koumakan.web.framework.dsl.tables.DataDictionary;
import club.koumakan.web.framework.dsl.tables.Log;
import club.koumakan.web.framework.dsl.tables.Role;
import club.koumakan.web.framework.dsl.tables.User;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in public
 */
@Generated(
  value = {
    "http://www.jooq.org",
    "jOOQ version:3.12.3"
  },
  comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Tables {

  /**
   * The table <code>public.data_dictionary</code>.
   */
  public static final DataDictionary DATA_DICTIONARY = DataDictionary.DATA_DICTIONARY;

  /**
   * The table <code>public.log</code>.
   */
  public static final Log LOG = Log.LOG;

  /**
   * The table <code>public.role</code>.
   */
  public static final Role ROLE = Role.ROLE;

  /**
   * The table <code>public.user</code>.
   */
  public static final User USER = User.USER;
}
