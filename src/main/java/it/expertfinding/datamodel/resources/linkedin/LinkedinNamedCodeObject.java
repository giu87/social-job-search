/*
 * @(#)LinkedinNamedCodeObject.java   1.0   18/apr/2012
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

public class LinkedinNamedCodeObject extends LinkedinNamedCodeField {

   @Linkedin("id")
   private String linkedinId;

   public String getLinkedinId() {
      return this.linkedinId;
   }

   public void setLinkedinId(String linkedinId) {
      this.linkedinId = linkedinId;
   }
}
