/*
 * @(#)TwitterUserHighlight.java   1.0   19/apr/2012
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

public class TwitterUserHighlight extends TwitterObject {

   @Twitter("screen_name")
   private String screenName;

   @Twitter
   private String name;

   /**
    * The scree_name field for this type.
    * 
    * @return The screen_name field for this type.
    */
   public String getScreenName() {
      return this.screenName;
   }

   public void setScreenName(String screenName) {
      this.screenName = screenName;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
