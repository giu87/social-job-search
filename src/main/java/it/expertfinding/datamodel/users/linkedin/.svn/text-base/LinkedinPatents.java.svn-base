/*
 * @(#)LinkedinPatents.java   1.0   17/apr/2012
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

import java.util.ArrayList;
import java.util.List;

public class LinkedinPatents {

   @Linkedin("values")
   private List<LinkedinPatent> patentList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinPatent> getPatentList() {
      if (patentList == null) {
         patentList = new ArrayList<LinkedinPatent>();
      }
      return this.patentList;
   }

   public void setPatentList(List<LinkedinPatent> patentList) {
      this.patentList = patentList;
   }

   public Long getTotal() {
      return total;
   }

   public void setTotal(Long value) {
      this.total = value;
   }
}
