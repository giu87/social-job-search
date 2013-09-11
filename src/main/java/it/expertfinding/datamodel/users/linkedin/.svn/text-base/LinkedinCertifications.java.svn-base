/*
 * @(#)LinkedinCertifications.java   1.0   18/apr/2012
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

public class LinkedinCertifications {

   @Linkedin("values")
   private List<LinkedinCertification> certificationsList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinCertification> getCertificationsList() {
      if (certificationsList == null) {
         certificationsList = new ArrayList<LinkedinCertification>();
      }
      return this.certificationsList;
   }

   public void setCertificationsList(List<LinkedinCertification> certificationsList) {
      this.certificationsList = certificationsList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
}
