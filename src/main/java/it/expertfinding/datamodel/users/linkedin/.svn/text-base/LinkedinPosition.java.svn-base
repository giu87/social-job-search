/*
 * @(#)LinkedinPosition.java   1.0   18/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinIndustries;
import it.expertfinding.datamodel.resources.linkedin.LinkedinJobFunctions;
import it.expertfinding.datamodel.resources.linkedin.LinkedinLocation;
import it.expertfinding.datamodel.resources.linkedin.LinkedinNamedCodeField;
import it.expertfinding.datamodel.resources.linkedin.LinkedinNamedObject;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinPosition {

   @Linkedin
   private String id;
   @Linkedin
   private LinkedinNamedObject company;
   @Linkedin
   private boolean isCurrent;
   @Linkedin
   protected String title;
   @Linkedin
   protected String summary;
   @Linkedin
   protected LinkedinDate startDate;
   @Linkedin
   protected LinkedinDate endDate;
   @Linkedin
   protected String description;
   @Linkedin
   protected String descriptionSnippet;
   @Linkedin
   protected String skillsAndExperience;
   @Linkedin
   protected LinkedinLocation location;
   @Linkedin
   protected LinkedinJobFunctions jobFunctions;
   @Linkedin
   protected LinkedinIndustries industries;
   @Linkedin
   protected LinkedinNamedCodeField jobType;
   @Linkedin
   protected LinkedinNamedCodeField experienceLevel;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public LinkedinNamedObject getCompany() {
      return this.company;
   }

   public void setCompany(LinkedinNamedObject company) {
      this.company = company;
   }

   public boolean isCurrent() {
      return this.isCurrent;
   }

   public void setCurrent(boolean isCurrent) {
      this.isCurrent = isCurrent;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getSummary() {
      return this.summary;
   }

   public void setSummary(String summary) {
      this.summary = summary;
   }

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

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDescriptionSnippet() {
      return this.descriptionSnippet;
   }

   public void setDescriptionSnippet(String descriptionSnippet) {
      this.descriptionSnippet = descriptionSnippet;
   }

   public String getSkillsAndExperience() {
      return this.skillsAndExperience;
   }

   public void setSkillsAndExperience(String skillsAndExperience) {
      this.skillsAndExperience = skillsAndExperience;
   }

   public LinkedinLocation getLocation() {
      return this.location;
   }

   public void setLocation(LinkedinLocation location) {
      this.location = location;
   }

   public LinkedinJobFunctions getJobFunctions() {
      return this.jobFunctions;
   }

   public void setJobFunctions(LinkedinJobFunctions jobFunctions) {
      this.jobFunctions = jobFunctions;
   }

   public LinkedinIndustries getIndustries() {
      return this.industries;
   }

   public void setIndustries(LinkedinIndustries industries) {
      this.industries = industries;
   }

   public LinkedinNamedCodeField getJobType() {
      return this.jobType;
   }

   public void setJobType(LinkedinNamedCodeField jobType) {
      this.jobType = jobType;
   }

   public LinkedinNamedCodeField getExperienceLevel() {
      return this.experienceLevel;
   }

   public void setExperienceLevel(LinkedinNamedCodeField experienceLevel) {
      this.experienceLevel = experienceLevel;
   }

   public String getText() {

      StringBuilder s = new StringBuilder();

      String[] relevantFields = { title, summary, description, skillsAndExperience };

      for (String field : relevantFields)
         if (field != null)
            s.append(field).append(". ");

      if (jobType != null) {
         s.append(jobType.getName()).append(". ");
      }

      if (experienceLevel != null) {
         s.append(experienceLevel.getName()).append(". ");
      }
      if (industries != null) {
         s.append(industries.getText());
      }
      if(jobFunctions!=null)
      for (LinkedinNamedCodeField jobFunction : jobFunctions.getJobFunctionsList())
         s.append(jobFunction.getName()).append(". ");

      return s.toString();
   }
}
