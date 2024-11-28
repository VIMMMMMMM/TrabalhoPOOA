package br.com.ucsal.controller.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ImplementedBy {
    Class<?> value();
}
