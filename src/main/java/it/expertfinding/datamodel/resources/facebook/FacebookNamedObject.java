/*
 * @(#)FacebookNamedType.java   1.0   15/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.facebook;

import com.restfb.Facebook;

public class FacebookNamedObject extends FacebookObject {

   @Facebook
   private String name;

   /**
    * The name field for this type.
    * 
    * @return The name field for this type.
    */
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
