package com.smart.framework.library.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JoJo on 2018/4/11.
 * wechat:18510829974
 * description:  Android 进阶 教你打造 Android 中的 IOC 框架 【ViewInject】 （上）:https://blog.csdn.net/lmj623565791/article/details/39269193
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
    int value();
}
