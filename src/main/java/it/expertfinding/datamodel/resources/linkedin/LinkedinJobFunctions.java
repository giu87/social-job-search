/*
 * @(#)LinkedinJobFunctions.java   1.0   18/apr/2012
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

public class LinkedinJobFunctions {

   @Linkedin("values")
   private List<LinkedinNamedCodeField> jobFunctionsList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinNamedCodeField> getJobFunctionsList() {
      if (jobFunctionsList == null) {
         jobFunctionsList = new ArrayList<LinkedinNamedCodeField>();
      }
      return this.jobFunctionsList;
   }

   public void setJobFunctionsList(List<LinkedinNamedCodeField> jobFunctionsList) {
      this.jobFunctionsList = jobFunctionsList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
}
