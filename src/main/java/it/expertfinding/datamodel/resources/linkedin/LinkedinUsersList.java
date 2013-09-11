/*
 * @(#)LinkedinListObjects.java   1.0   18/apr/2012
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

import java.util.ArrayList;
import java.util.List;

public class LinkedinUsersList {

   @Linkedin("values")
   private List<LinkedinUserInfo> usersList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinUserInfo> getUsersList() {
      if (usersList == null) {
         usersList = new ArrayList<LinkedinUserInfo>();
      }
      return this.usersList;
   }

   public void setUsersList(List<LinkedinUserInfo> value) {
      this.usersList = value;
   }

   public Long getTotal() {
      return total;
   }

   public void setTotal(Long value) {
      this.total = value;
   }
}
