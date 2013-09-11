/*
 * @(#)LinkedinInventors.java   1.0   17/apr/2012
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

public class LinkedinCreators {

   @Linkedin("values")
   private List<LinkedinCreator> creatorsList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinCreator> getCreatorsList() {
      if (creatorsList == null) {
         creatorsList = new ArrayList<LinkedinCreator>();
      }
      return this.creatorsList;
   }

   public void setCreatorsList(List<LinkedinCreator> value) {
      this.creatorsList = value;
   }

   public Long getTotal() {
      return total;
   }

   public void setTotal(Long value) {
      this.total = value;
   }
}
