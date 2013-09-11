/*
 * @(#)LinkedinLanguage.java   1.0   18/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinNamedField;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinLanguage {

   @Linkedin
   private LinkedinProficiency proficiency;
   @Linkedin
   private String id;
   @Linkedin
   private LinkedinNamedField language;

   public LinkedinProficiency getProficiency() {
      return this.proficiency;
   }

   public void setProficiency(LinkedinProficiency proficiency) {
      this.proficiency = proficiency;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public LinkedinNamedField getLanguage() {
      return this.language;
   }

   public void setLanguage(LinkedinNamedField language) {
      this.language = language;
   }

   public static class LinkedinProficiency {

      @Linkedin
      private String level;
      @Linkedin
      private String name;

      public String getLevel() {
         return this.level;
      }

      public void setLevel(String level) {
         this.level = level;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String name) {
         this.name = name;
      }
   }
}
