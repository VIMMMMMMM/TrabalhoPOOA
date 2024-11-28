package br.com.ucsal.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  // This targets fields for injection.
@Retention(RetentionPolicy.RUNTIME)  // This makes the annotation available at runtime.
public @interface Inject {
}
