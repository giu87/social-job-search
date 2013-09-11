/*
 * @(#)AlchemyException.java   1.0   26/apr/2012
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
 * Root of the Alchemy exception hierarchy.
 * 
 * @author Matteo Silvestri
 */
public class AlchemyException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   private int errorCode;

   /**
    * Creates an exception with the given message.
    * 
    * @param message
    *           A message describing this exception.
    */
   public AlchemyException(String message) {
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
   public AlchemyException(String message, Throwable cause) {
      super(message, cause);
   }

   /**
    * Creates an exception with the given message and cause.
    * 
    * @param message
    *           A message describing this exception.
    * @param code
    *           The error code for this exception.
    * @param cause
    *           The exception that caused this exception to be thrown.
    */
   public AlchemyException(String message, int code, Throwable cause) {
      super(message, cause);
      this.errorCode = code;

   }

   public int getErrorCode() {
      return this.errorCode;
   }

}
