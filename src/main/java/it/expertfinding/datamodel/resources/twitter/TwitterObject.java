/*
 * @(#)TwitterObject.java   1.0   19/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.twitter;

import it.expertfinding.utils.twitter.Twitter;

public class TwitterObject {

   @Twitter("id")
   private Long _id;

   /**
    * This object's unique Twitter ID.
    * 
    * @return This object's unique Twitter ID.
    */
   public Long get_id() {
      return this._id;
   }

   public void set_id(Long _id) {
      this._id = _id;
   }
}
