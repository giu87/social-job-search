package it.expertfinding.utils.twitter;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies how a Twitter JSON response attribute maps to a Java field.
 * 
 * @author Matteo Silvestri</a>
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Twitter {

   /**
    * Name of the Twitter API result attribute to map to.
    * 
    * @return Name of the Twitter API result attribute to map to.
    */
   String value() default "";
}
