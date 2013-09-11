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

public class LinkedinObjectsList {

   @Linkedin("values")
   private List<LinkedinNamedObject> resourceList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinNamedObject> getResourceList() {
      if (resourceList == null) {
         resourceList = new ArrayList<LinkedinNamedObject>();
      }
      return this.resourceList;
   }

   public void setResourceList(List<LinkedinNamedObject> value) {
      this.resourceList = value;
   }

   public Long getTotal() {
      return total;
   }

   public void setTotal(Long value) {
      this.total = value;
   }
}
