/*
 * @(#)Alchemy.java   1.0   26/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.alchemy;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies how a Alchemy JSON response attribute maps to a Java field.
 * 
 * @author Matteo Silvestri</a>
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Alchemy {

   /**
    * Name of the Alchemy API result attribute to map to.
    * 
    * @return Name of the Alchemy API result attribute to map to.
    */
   String value() default "";
}
