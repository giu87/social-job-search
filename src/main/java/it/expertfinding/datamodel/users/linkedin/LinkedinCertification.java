/*
 * @(#)LinkedinCertification.java   1.0   17/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinNamedField;
import it.expertfinding.datamodel.resources.linkedin.LinkedinNamedObject;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinCertification extends LinkedinNamedObject {

   @Linkedin
   private LinkedinNamedField authority;
   @Linkedin
   private String number;
   @Linkedin
   private LinkedinDate startDate;
   @Linkedin
   private LinkedinDate endDate;

   public LinkedinNamedField getAuthority() {
      return this.authority;
   }

   public void setAuthority(LinkedinNamedField authority) {
      this.authority = authority;
   }

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public LinkedinDate getStartDate() {
      return this.startDate;
   }

   public void setStartDate(LinkedinDate startDate) {
      this.startDate = startDate;
   }

   public LinkedinDate getEndDate() {
      return this.endDate;
   }

   public void setEndDate(LinkedinDate endDate) {
      this.endDate = endDate;
   }
   
   public String getResourceText(){
      
      StringBuilder s = new StringBuilder();

      if(getName() != null)
         s.append(getName()).append(". ");

      if(authority != null)
         s.append(authority).append(". ");
      
      return s.toString();
   }
}
