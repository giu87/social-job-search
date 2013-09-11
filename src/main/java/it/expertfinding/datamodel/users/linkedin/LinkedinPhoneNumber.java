/*
 * @(#)LinkedinPhoneNumber.java   1.0   18/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinPhoneNumber {

   @Linkedin
   private String phoneNumber;
   @Linkedin
   private String phoneType;

   public String getPhoneNumber() {
      return this.phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getPhoneType() {
      return this.phoneType;
   }

   public void setPhoneType(String phoneType) {
      this.phoneType = phoneType;
   }
}
