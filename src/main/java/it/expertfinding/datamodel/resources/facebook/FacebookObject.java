/*
 * @(#)FacebookType.java   1.0   15/apr/2012
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

public class FacebookObject {

   @Facebook("id")
   private String _id;

   /**
    * This object's unique Facebook ID.
    * 
    * @return This object's unique Facebook ID.
    */
   public String get_id() {
      return this._id;
   }

   public void set_id(String _id) {
      this._id = _id;
   }

}
