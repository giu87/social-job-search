/*
 * @(#)FacebookTypedObject.java   1.0   15/apr/2012
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

public class FacebookTypedObject extends FacebookNamedObject {

   @Facebook
   private String type;

   /**
    * This object's type metadata, available by including the {@code metadata=1} URL
    * parameter in an API request.
    * 
    * @return This object's type metadata, available by including the {@code metadata=1} URL
    *         parameter in an API request.
    */
   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

}
