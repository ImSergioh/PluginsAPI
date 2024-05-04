package me.imsergioh.pluginsapi.language;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DefaultLanguageItem {

    String material() default "BARRIER";
    String name() default "";
    String[] description() default {};
    int amount() default 1;
}
