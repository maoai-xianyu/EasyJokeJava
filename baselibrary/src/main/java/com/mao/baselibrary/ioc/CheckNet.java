package com.mao.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangkun
 * @time 2020-04-15 09:10
 * 检测网络的Annotation
 */
@Target(ElementType.METHOD) // 代表Annotation的位置， FIELD属性  TYPE类上  CONSTRUCTOR构造函数上
@Retention(RetentionPolicy.RUNTIME) // 编译时   RUNTIME 运行时 SOURCE 源码资源
public @interface CheckNet {
}
