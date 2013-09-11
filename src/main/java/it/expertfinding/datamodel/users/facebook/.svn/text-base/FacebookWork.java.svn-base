/*
 * @(#)FacebookWork.java   1.0   15/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.facebook;

import static com.restfb.util.DateUtils.toDateFromMonthYearFormat;
import static java.util.Collections.unmodifiableList;
import it.expertfinding.datamodel.resources.facebook.FacebookNamedObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

public class FacebookWork {

   @Facebook
   private FacebookNamedObject employer;

   @Facebook
   private FacebookNamedObject location;

   @Facebook
   private FacebookNamedObject position;

   @Facebook("start_date")
   private String startDate;

   @Facebook("end_date")
   private String endDate;

   @Facebook
   private String description;

   @Facebook
   private List<FacebookNamedObject> with = new ArrayList<FacebookNamedObject>();

   @Facebook
   private List<Project> projects = new ArrayList<Project>();

   /**
    * The employer for this job.
    * 
    * @return The employer for this job.
    */
   public FacebookNamedObject getEmployer() {
      return this.employer;
   }

   public void setEmployer(FacebookNamedObject employer) {
      this.employer = employer;
   }

   /**
    * The location of this job.
    * 
    * @return The location of this job.
    */
   public FacebookNamedObject getLocation() {
      return this.location;
   }

   public void setLocation(FacebookNamedObject location) {
      this.location = location;
   }

   /**
    * Position held at this job.
    * 
    * @return Position held at this job.
    */
   public FacebookNamedObject getPosition() {
      return this.position;
   }

   public void setPosition(FacebookNamedObject position) {
      this.position = position;
   }

   /**
    * Date this job was started.
    * 
    * @return Date this job was started.
    */
   public Date getStartDate() {
      return toDateFromMonthYearFormat(startDate);
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate.toString();
   }

   /**
    * Date this job ended.
    * 
    * @return Date this job ended.
    */
   public Date getEndDate() {
      return toDateFromMonthYearFormat(endDate);
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate.toString();
   }

   /**
    * Description of this job.
    * 
    * @return Description of this job.
    */
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Friends associated with this job.
    * 
    * @return Friends associated with this job.
    */
   public List<FacebookNamedObject> getWith() {
      return unmodifiableList(with);
   }

   public void setWith(List<FacebookNamedObject> with) {
      this.with = with;
   }

   /**
    * A list of the project history from this work.
    * 
    * @return A list of the project history from this work.
    */
   public List<Project> getProjects() {
      return unmodifiableList(projects);
   }

   public void setProjects(List<Project> projects) {
      this.projects = projects;
   }

   public static class Project extends FacebookNamedObject {

      @Facebook
      private List<FacebookNamedObject> with = new ArrayList<FacebookNamedObject>();

      @Facebook
      private String description;

      @Facebook("start_date")
      private String startDate;

      @Facebook("end_date")
      private String endDate;

      /**
       * Friends associated with this project.
       * 
       * @return Friends associated with this project.
       */
      public List<FacebookNamedObject> getWith() {
         return unmodifiableList(with);
      }

      public void setWith(List<FacebookNamedObject> with) {
         this.with = with;
      }

      /**
       * Description of this project.
       * 
       * @return Description of this project.
       */
      public String getDescription() {
         return description;
      }

      public void setDescription(String description) {
         this.description = description;
      }

      /**
       * Date this project was started.
       * 
       * @return Date this project was started.
       */
      public Date getStartDate() {
         return toDateFromMonthYearFormat(startDate);
      }

      public void setStartDate(Date startDate) {
         this.startDate = startDate.toString();
      }

      /**
       * Date this project ended.
       * 
       * @return Date this project ended.
       */
      public Date getEndDate() {
         return toDateFromMonthYearFormat(endDate);
      }

      public void setEndDate(Date endDate) {
         this.endDate = endDate.toString();
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
      if (employer != null)
         s.append(employer.getName()).append(". ");
      if (position != null)
         s.append(position.getName()).append(". ");
      if (description != null)
         s.append(description).append(". ");
      if (location != null)
         s.append(location.getName()).append(". ");

      for (Project w : projects) {
         s.append(w.getResourceText());
      }

      return s.toString();
   }
}
