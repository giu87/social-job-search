/*
 * @(#)LinkedinJob.java   1.0   26/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.linkedin;

import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.datamodel.users.linkedin.LinkedinDate;
import it.expertfinding.datamodel.users.linkedin.LinkedinPosition;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.linkedin.Linkedin;

import java.util.List;

public class LinkedinJob extends LinkedinResource {

   // salary?referral-bonus?
   // customerJobCode
   @Linkedin
   private boolean active;
   @Linkedin
   private LinkedinDate postingDate;
   @Linkedin
   private LinkedinDate expirationDate;
   @Linkedin
   private Long postingTimestamp;
   @Linkedin
   private LinkedinPosition position;
   @Linkedin
   private Long expirationTimestamp;
   @Linkedin
   private LinkedinNamedObject company;
   @Linkedin
   private String skillsAndExperience;
   @Linkedin
   private String descriptionSnippet;
   @Linkedin
   private String description;
   @Linkedin
   private String salary;
   @Linkedin
   private LinkedinUserInfo jobPoster;
   @Linkedin
   private String referralBonus;
   @Linkedin
   private String siteJobUrl;
   @Linkedin
   private String locationDescription;

   public LinkedinJob() {

      super();
      setrType(RType.LINKEDIN_JOB);
   }

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public LinkedinDate getPostingDate() {
      return this.postingDate;
   }

   public void setPostingDate(LinkedinDate postingDate) {
      this.postingDate = postingDate;
   }

   public LinkedinDate getExpirationDate() {
      return this.expirationDate;
   }

   public void setExpirationDate(LinkedinDate expirationDate) {
      this.expirationDate = expirationDate;
   }

   public Long getPostingTimestamp() {
      return this.postingTimestamp;
   }

   public void setPostingTimestamp(Long postingTimestamp) {
      this.postingTimestamp = postingTimestamp;
   }

   public LinkedinPosition getPosition() {
      return this.position;
   }

   public void setPosition(LinkedinPosition position) {
      this.position = position;
   }

   public Long getExpirationTimestamp() {
      return this.expirationTimestamp;
   }

   public void setExpirationTimestamp(Long expirationTimestamp) {
      this.expirationTimestamp = expirationTimestamp;
   }

   public LinkedinNamedObject getCompany() {
      return this.company;
   }

   public void setCompany(LinkedinNamedObject company) {
      this.company = company;
   }

   public String getSkillsAndExperience() {
      return this.skillsAndExperience;
   }

   public void setSkillsAndExperience(String skillsAndExperience) {
      this.skillsAndExperience = skillsAndExperience;
   }

   public String getDescriptionSnippet() {
      return this.descriptionSnippet;
   }

   public void setDescriptionSnippet(String descriptionSnippet) {
      this.descriptionSnippet = descriptionSnippet;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getSalary() {
      return this.salary;
   }

   public void setSalary(String salary) {
      this.salary = salary;
   }

   public LinkedinUserInfo getJobPoster() {
      return this.jobPoster;
   }

   public void setJobPoster(LinkedinUserInfo jobPoster) {
      this.jobPoster = jobPoster;
   }

   public String getReferralBonus() {
      return this.referralBonus;
   }

   public void setReferralBonus(String referralBonus) {
      this.referralBonus = referralBonus;
   }

   public String getSiteJobUrl() {
      return this.siteJobUrl;
   }

   public void setSiteJobUrl(String siteJobUrl) {
      this.siteJobUrl = siteJobUrl;
   }

   public String getLocationDescription() {
      return this.locationDescription;
   }

   public void setLocationDescription(String locationDescription) {
      this.locationDescription = locationDescription;
   }

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { skillsAndExperience, description };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         if (position != null)
            s.append(position.getText()).append(". ");
         if (company != null)
            s.append(company.getName()).append(". ");

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));

         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            if (!url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error("Limit reached while processing linkedin job id: {}",
                        get_id());
                  throw e;
               } catch (Exception e) {
               }
         }

         String rText = Constants.removeCommonMessages(s.toString());
         setResourceText(rText);
         MongoUtils.updateResourceText(this.getChannel(), this.get_id(), rText);
         return rText;
      } else
         return resourceText;
   }
}
