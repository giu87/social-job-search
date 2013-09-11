/*
 * @(#)LinkedinEducation.java   1.0   18/apr/2012
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

public class LinkedinEducation {

   @Linkedin("id")
   private String linkedinId;
   @Linkedin
   private String schoolName;
   @Linkedin
   private String degree;
   @Linkedin
   private String notes;
   @Linkedin
   private String activities;
   @Linkedin
   private String fieldOfStudy;
   @Linkedin
   private LinkedinDate startDate;
   @Linkedin
   private LinkedinDate endDate;

   public LinkedinDate getStartDate() {
      return this.startDate;
   }

   public void setStartDate(LinkedinDate startDate) {
      this.startDate = startDate;
   }

   public LinkedinDate getEndDate() {
      return this.endDate;
   }

   public void setEndDate(LinkedinDate endDate) {
      this.endDate = endDate;
   }

   public String getLinkedinId() {
      return this.linkedinId;
   }

   public void setLinkedinId(String linkedinId) {
      this.linkedinId = linkedinId;
   }

   public String getSchoolName() {
      return this.schoolName;
   }

   public void setSchoolName(String schoolName) {
      this.schoolName = schoolName;
   }

   public String getDegree() {
      return this.degree;
   }

   public void setDegree(String degree) {
      this.degree = degree;
   }

   public String getNotes() {
      return this.notes;
   }

   public void setNotes(String notes) {
      this.notes = notes;
   }

   public String getActivities() {
      return this.activities;
   }

   public void setActivities(String activities) {
      this.activities = activities;
   }

   public String getFieldOfStudy() {
      return this.fieldOfStudy;
   }

   public void setFieldOfStudy(String fieldOfStudy) {
      this.fieldOfStudy = fieldOfStudy;
   }

   public String getResourceText() {

      StringBuilder s = new StringBuilder();

      String[] relevantFields = { schoolName, degree, notes, activities, fieldOfStudy };

      for (String field : relevantFields)
         if (field != null)
            s.append(field).append(". ");

      return s.toString();
   }
}