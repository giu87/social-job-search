/*
 * @(#)FacebookEducation.java   1.0   15/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.facebook;

import static java.util.Collections.unmodifiableList;
import it.expertfinding.datamodel.resources.facebook.FacebookNamedObject;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

public class FacebookEducation {

   @Facebook
   private FacebookNamedObject school;

   @Facebook
   private FacebookNamedObject year;

   @Facebook
   private FacebookNamedObject degree;

   @Facebook
   private String type;

   @Facebook
   private List<FacebookNamedObject> concentration = new ArrayList<FacebookNamedObject>();

   @Facebook
   private List<FacebookNamedObject> with = new ArrayList<FacebookNamedObject>();

   @Facebook
   private List<Class> classes = new ArrayList<Class>();

   /**
    * The school name and ID.
    * 
    * @return The school name and ID.
    */
   public FacebookNamedObject getSchool() {
      return school;
   }

   public void setSchool(FacebookNamedObject school) {
      this.school = school;
   }

   /**
    * Graduation year.
    * 
    * @return Graduation year.
    */
   public FacebookNamedObject getYear() {
      return year;
   }

   public void setYear(FacebookNamedObject year) {
      this.year = year;
   }

   /**
    * Degree acquired.
    * 
    * @return Degree acquired.
    */
   public FacebookNamedObject getDegree() {
      return degree;
   }

   public void setDegree(FacebookNamedObject degree) {
      this.degree = degree;
   }

   /**
    * Type of school, e.g. {@code College}.
    * 
    * @return Type of school.
    */
   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   /**
    * Concentrations/minors.
    * 
    * @return Concentrations/minors.
    */
   public List<FacebookNamedObject> getConcentration() {
      return unmodifiableList(concentration);
   }

   public void setConcentration(List<FacebookNamedObject> concentration) {
      this.concentration = concentration;
   }

   /**
    * Friends associated with this school.
    * 
    * @return Friends associated with this school.
    * @since 1.6.3
    */
   public List<FacebookNamedObject> getWith() {
      return unmodifiableList(with);
   }

   public void setWith(List<FacebookNamedObject> with) {
      this.with = with;
   }

   /**
    * Classes taken at this school.
    * 
    * @return Classes taken at this school.
    * @since 1.6.8
    */
   public List<Class> getClasses() {
      return unmodifiableList(classes);
   }

   public void setClasses(List<Class> classes) {
      this.classes = classes;
   }

   public static class Class extends FacebookNamedObject {

      @Facebook
      private List<FacebookNamedObject> with = new ArrayList<FacebookNamedObject>();

      @Facebook
      private String description;

      /**
       * Friends associated with this class.
       * 
       * @return Friends associated with this class.
       */
      public List<FacebookNamedObject> getWith() {
         return with;
      }

      public void setWith(List<FacebookNamedObject> with) {
         this.with = with;
      }

      /**
       * The description of this class.
       * 
       * @return The description of this class.
       */
      public String getDescription() {
         return description;
      }

      public void setDescription(String description) {
         this.description = description;
      }

      public String getResourceText() {

         StringBuilder s = new StringBuilder();

         if (description != null)
            s.append(description).append(". ");

         if (getName() != null)
            s.append(getName()).append(". ");

         return s.toString();
      }
   }

   public String getResourceText() {

      StringBuilder s = new StringBuilder();

      if (school != null)
         s.append(school.getName()).append(". ");
      if (degree != null)
         s.append(degree.getName()).append(". ");
      if (type != null)
         s.append(type).append(". ");
      if (degree != null)
         s.append(degree.getName()).append(". ");

      for (Class c : classes) {
         s.append(c.getResourceText());
      }

      for (FacebookNamedObject c : concentration) {
         s.append(c.getName()).append(". ");
      }
      return s.toString();
   }
}
