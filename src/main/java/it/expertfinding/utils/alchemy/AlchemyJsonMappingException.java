/*
 * @(#)AlchemyJsonMappingException.java   1.0   26/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.alchemy;

/**
 * Indicates that an error occurred while mapping JSON data to a Java object.
 * 
 * @author Matteo Silvestri
 */
public class AlchemyJsonMappingException extends AlchemyException {

   private static final long serialVersionUID = 1L;

   /**
    * Creates an exception with the given message.
    * 
    * @param message
    *           A message describing this exception.
    */
   public AlchemyJsonMappingException(String message) {
      super(message);
   }

   /**
    * Creates an exception with the given message and cause.
    * 
    * @param message
    *           A message describing this exception.
    * @param cause
    *           The exception that caused this exception to be thrown.
    */
   public AlchemyJsonMappingException(String message, Throwable cause) {
      super(message, cause);
   }
}
