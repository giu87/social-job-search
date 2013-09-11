/*
 * @(#)LinkedinCompany.java   1.0   18/apr/2012
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
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.linkedin.Linkedin;

import java.util.List;

public class LinkedinCompany extends LinkedinResource {

   @Linkedin
   private String universalName;
   @Linkedin
   private String description;
   @Linkedin
   private String industry;
   @Linkedin
   private String logoUrl;
   @Linkedin
   private String name;
   @Linkedin
   private String type;
   @Linkedin
   private LinkedinNamedCodeField companyType;
   @Linkedin
   private String size;
   @Linkedin
   private LinkedinNamedCodeField stockExchange;
   @Linkedin
   private String ticker;
   @Linkedin
   private LinkedinSpecialties specialties;
   @Linkedin
   private String blogRssUrl;
   @Linkedin
   private String twitterId;
   @Linkedin
   private String squareLogoUrl;
   @Linkedin
   private LinkedinLocations locations;
   @Linkedin
   private Long foundedYear;
   @Linkedin
   private Long endYear;
   @Linkedin
   private Long numFollowers;
   @Linkedin
   private LinkedinEmailDomains emailDomains;
   @Linkedin
   private String websiteUrl;
   @Linkedin
   private LinkedinNamedCodeField status;
   @Linkedin
   private LinkedinNamedCodeField employeeCountRange;

   // @Linkedin
   // private String key;

   public LinkedinCompany() {

      super();
      setrType(RType.LINKEDIN_COMPANY);
   }

   public String getUniversalName() {
      return this.universalName;
   }

   public void setUniversalName(String universalName) {
      this.universalName = universalName;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getIndustry() {
      return this.industry;
   }

   public void setIndustry(String industry) {
      this.industry = industry;
   }

   public String getLogoUrl() {
      return this.logoUrl;
   }

   public void setLogoUrl(String logoUrl) {
      this.logoUrl = logoUrl;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public LinkedinNamedCodeField getCompanyType() {
      return this.companyType;
   }

   public void setCompanyType(LinkedinNamedCodeField companyType) {
      this.companyType = companyType;
   }

   public String getSize() {
      return this.size;
   }

   public void setSize(String size) {
      this.size = size;
   }

   public LinkedinNamedCodeField getStockExchange() {
      return this.stockExchange;
   }

   public void setStockExchange(LinkedinNamedCodeField stockExchange) {
      this.stockExchange = stockExchange;
   }

   public String getTicker() {
      return this.ticker;
   }

   public void setTicker(String ticker) {
      this.ticker = ticker;
   }

   public LinkedinSpecialties getSpecialties() {
      return this.specialties;
   }

   public void setSpecialties(LinkedinSpecialties specialties) {
      this.specialties = specialties;
   }

   public String getBlogRssUrl() {
      return this.blogRssUrl;
   }

   public void setBlogRssUrl(String blogRssUrl) {
      this.blogRssUrl = blogRssUrl;
   }

   public String getTwitterId() {
      return this.twitterId;
   }

   public void setTwitterId(String twitterId) {
      this.twitterId = twitterId;
   }

   public String getSquareLogoUrl() {
      return this.squareLogoUrl;
   }

   public void setSquareLogoUrl(String squareLogoUrl) {
      this.squareLogoUrl = squareLogoUrl;
   }

   public LinkedinLocations getLocations() {
      return this.locations;
   }

   public void setLocations(LinkedinLocations locations) {
      this.locations = locations;
   }

   public Long getFoundedYear() {
      return this.foundedYear;
   }

   public void setFoundedYear(Long foundedYear) {
      this.foundedYear = foundedYear;
   }

   public Long getEndYear() {
      return this.endYear;
   }

   public void setEndYear(Long endYear) {
      this.endYear = endYear;
   }

   public Long getNumFollowers() {
      return this.numFollowers;
   }

   public void setNumFollowers(Long numFollowers) {
      this.numFollowers = numFollowers;
   }

   public LinkedinEmailDomains getEmailDomains() {
      return this.emailDomains;
   }

   public void setEmailDomains(LinkedinEmailDomains emailDomains) {
      this.emailDomains = emailDomains;
   }

   public String getWebsiteUrl() {
      return this.websiteUrl;
   }

   public void setWebsiteUrl(String websiteUrl) {
      this.websiteUrl = websiteUrl;
   }

   public LinkedinNamedCodeField getStatus() {
      return this.status;
   }

   public void setStatus(LinkedinNamedCodeField status) {
      this.status = status;
   }

   public LinkedinNamedCodeField getEmployeeCountRange() {
      return this.employeeCountRange;
   }

   public void setEmployeeCountRange(LinkedinNamedCodeField employeeCountRange) {
      this.employeeCountRange = employeeCountRange;
   }

   // public String getKey() {
   // return this.key;
   // }
   //
   // public void setKey(String key) {
   // this.key = key;
   // }

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { universalName, description, industry, name, type,
               ticker };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         if (specialties != null)
            for (String specialty : specialties.getSpecialtyList()) {
               s.append(specialty).append(". ");
            }

         if (status != null)
            s.append(status.getName()).append(" .");
         if (companyType != null)
            s.append(companyType.getName()).append(" .");

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));
         if (websiteUrl != null)
            urls.add(websiteUrl);

         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            if (!url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error("Limit reached while processing linkedin company id: {}",
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
