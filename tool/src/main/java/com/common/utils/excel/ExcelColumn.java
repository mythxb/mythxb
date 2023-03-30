package com.common.utils.excel;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

    /**
     * Excel标题
     *
     * @return
     * @author Lynch
     */
    String value() default "";

    /**
     * Excel从左往右排列位置
     *
     * @return
     * @author Lynch
     */
    int col() default 0;



    /**
     * Excel内容格式 0.默认 1.整数数值 2.小数数值 3.时间类型
     *
     * @return
     * @author xwx
     */
    int contentType() default 0;
}
