/*
 * @(#)LinkedinNamedField.java   1.0   17/apr/2012
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

public class LinkedinNamedField {

   @Linkedin
   private String name;

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
