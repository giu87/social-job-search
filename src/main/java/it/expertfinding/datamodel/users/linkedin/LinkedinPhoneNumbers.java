/*
 * @(#)LinkedinPhoneNumbers.java   1.0   18/apr/2012
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

import java.util.List;

public class LinkedinPhoneNumbers {

   @Linkedin("values")
   private List<LinkedinPhoneNumber> phoneNumbersList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinPhoneNumber> getPhoneNumbersList() {
      return this.phoneNumbersList;
   }

   public void setPhoneNumbersList(List<LinkedinPhoneNumber> phoneNumbersList) {
      this.phoneNumbersList = phoneNumbersList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
}
