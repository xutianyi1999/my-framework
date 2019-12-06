package club.koumakan.web.framework.utils.transform;

import com.google.common.base.CaseFormat;
import io.vavr.Function1;

import java.time.format.DateTimeFormatter;

public interface TransformCommons {

  DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * 小驼峰转下划线
   */
  Function1<String, String> LOWER_CAMEL_TO_LOWER_UNDERSCORE =
    value -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value);

  /**
   * 下划线转小驼峰
   */
  Function1<String, String> LOWER_UNDERSCORE_TO_LOWER_CAMEL =
    value -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);
}
