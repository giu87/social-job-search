/*
 * @(#)LinkedinIndustries.java   1.0   18/apr/2012
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

import java.util.List;

public class LinkedinIndustries {

   @Linkedin("values")
   private List<LinkedinNamedCodeObject> industriesList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinNamedCodeObject> getIndustriesList() {
      return this.industriesList;
   }

   public void setIndustriesList(List<LinkedinNamedCodeObject> industriesList) {
      this.industriesList = industriesList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
   
   public String getText(){
      StringBuilder s = new StringBuilder();
      for(LinkedinNamedCodeObject industry : industriesList){
         s.append(industry.getName()).append(". ");
      }
      return s.toString();
   }
}
