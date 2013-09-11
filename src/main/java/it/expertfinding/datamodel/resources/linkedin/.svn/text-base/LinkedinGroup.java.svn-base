/*
 * @(#)LinkedinGroup.java   1.0   19/apr/2012
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
import it.expertfinding.datamodel.users.linkedin.LinkedinRelationToViewer;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.linkedin.Linkedin;

import java.util.List;

public class LinkedinGroup extends LinkedinResource {

   @Linkedin
   private String name;
   @Linkedin
   private String shortDescription;
   @Linkedin
   private String description;
   @Linkedin
   private LinkedinRelationToViewer relationToViewer;
   // @Linkedin
   // private CountsByCategoryImpl countsByCategory; CountForCategoryImpl
   @Linkedin
   private boolean isOpenToNonMembers;
   @Linkedin
   private LinkedinCodeField category;
   @Linkedin
   private String siteGroupUrl;
   @Linkedin
   private String contactEmail;
   @Linkedin
   private String locale;
   @Linkedin
   private boolean allowMemberInvites;
   @Linkedin
   private String smallLogoUrl;
   @Linkedin
   private String largeLogoUrl;

   // private List<LinkedinGroupPost> posts;

   public LinkedinGroup() {

      super();
      setrType(RType.LINKEDIN_GROUP);
   }

   // public List<LinkedinGroupPost> getPosts() {
   // return this.posts;
   // }
   //
   // public void setPosts(List<LinkedinGroupPost> posts) {
   // this.posts = posts;
   // }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getShortDescription() {
      return this.shortDescription;
   }

   public void setShortDescription(String shortDescription) {
      this.shortDescription = shortDescription;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public LinkedinRelationToViewer getRelationToViewer() {
      return this.relationToViewer;
   }

   public void setRelationToViewer(LinkedinRelationToViewer relationToViewer) {
      this.relationToViewer = relationToViewer;
   }

   public boolean isOpenToNonMembers() {
      return this.isOpenToNonMembers;
   }

   public void setOpenToNonMembers(boolean isOpenToNonMembers) {
      this.isOpenToNonMembers = isOpenToNonMembers;
   }

   public LinkedinCodeField getCategory() {
      return this.category;
   }

   public void setCategory(LinkedinCodeField category) {
      this.category = category;
   }

   public String getSiteGroupUrl() {
      return this.siteGroupUrl;
   }

   public void setSiteGroupUrl(String siteGroupUrl) {
      this.siteGroupUrl = siteGroupUrl;
   }

   public String getContactEmail() {
      return this.contactEmail;
   }

   public void setContactEmail(String contactEmail) {
      this.contactEmail = contactEmail;
   }

   public String getLocale() {
      return this.locale;
   }

   public void setLocale(String locale) {
      this.locale = locale;
   }

   public boolean isAllowMemberInvites() {
      return this.allowMemberInvites;
   }

   public void setAllowMemberInvites(boolean allowMemberInvites) {
      this.allowMemberInvites = allowMemberInvites;
   }

   public String getSmallLogoUrl() {
      return this.smallLogoUrl;
   }

   public void setSmallLogoUrl(String smallLogoUrl) {
      this.smallLogoUrl = smallLogoUrl;
   }

   public String getLargeLogoUrl() {
      return this.largeLogoUrl;
   }

   public void setLargeLogoUrl(String largeLogoUrl) {
      this.largeLogoUrl = largeLogoUrl;
   }

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { name, shortDescription, description };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));

         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            if (!url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.HTMLGetText(URLUtils.getURLContent(url), null))
                        .append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error("Limit reached while processing linkedin group id: {}",
                        get_id());
                  throw e;
               } catch (Exception e) {
               }
         }

         String rText = Constants.removeCommonMessages(s.toString());
         setResourceTextShort(rText);
         MongoUtils.updateResourceTextShort(this.getChannel(), this.get_id(), rText);

         s = new StringBuilder(rText);

         for (LinkedinGroupPost post : MongoUtils.getLinkedinGroupPostsFromGroupId(get_id())) {
            try {
               s.append(post.getResourceText());
            } catch (AlchemyLimitException e) {
               Facade.log.error("Limit reached while processing linkedin group id: {}",
                     get_id());
               throw e;
            }
         }

         setResourceText(s.toString());
         MongoUtils.updateResourceText(this.getChannel(), this.get_id(), s.toString());
         return s.toString();
      } else
         return resourceText;
   }

   /**
    * @return Returns the resourceTextShort.
    */
   @Override
   public String getResourceTextShort() {
      return this.resourceTextShort;
   }

}
