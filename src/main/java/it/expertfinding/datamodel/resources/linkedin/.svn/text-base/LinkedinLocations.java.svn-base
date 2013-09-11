/*
 * @(#)LinkedinLocations.java   1.0   18/apr/2012
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

public class LinkedinLocations {

   @Linkedin("_total")
   private Long total;
   @Linkedin("values")
   private List<LinkedinLocation> locationsList;

   public List<LinkedinLocation> getLocationsList() {
      return this.locationsList;
   }

   public void setLocationsList(List<LinkedinLocation> locationsList) {
      this.locationsList = locationsList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
}
