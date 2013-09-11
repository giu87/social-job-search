/*
 * @(#)LinkedinLike.java   1.0   18/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.linkedin;

import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinLike {

   @Linkedin
   private Long timestamp;
   @Linkedin
   private LinkedinUserInfo person;

   public Long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(Long timestamp) {
      this.timestamp = timestamp;
   }

   public LinkedinUserInfo getPerson() {
      return this.person;
   }

   public void setPerson(LinkedinUserInfo person) {
      this.person = person;
   }
}
