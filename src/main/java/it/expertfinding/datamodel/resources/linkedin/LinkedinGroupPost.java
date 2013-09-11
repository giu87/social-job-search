/*
 * @(#)LinkedinGroupPost.java   1.0   26/apr/2012
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

public class LinkedinGroupPost extends LinkedinResource {

   @Linkedin
   private LinkedinCodeField type;
   @Linkedin
   private LinkedinCodeField category;
   @Linkedin
   private Long creationTimestamp;
   @Linkedin
   private LinkedinUserInfo creator;
   @Linkedin
   private String summary;
   @Linkedin
   private String title;
   @Linkedin
   private LinkedinLikes likes;
   @Linkedin
   private LinkedinRelationToViewer relationToViewer;
   @Linkedin
   private LinkedinAttachment attachment;
   @Linkedin
   private LinkedinComments comments;
   @Linkedin
   private String siteGroupPostUrl;

   private String groupId;

   public LinkedinGroupPost() {

      super();
      setrType(RType.LINKEDIN_GROUP_POST);
   }

   public LinkedinCodeField getType() {
      return this.type;
   }

   public void setType(LinkedinCodeField type) {
      this.type = type;
   }

   public LinkedinCodeField getCategory() {
      return this.category;
   }

   public void setCategory(LinkedinCodeField category) {
      this.category = category;
   }

   public Long getCreationTimestamp() {
      return this.creationTimestamp;
   }

   public void setCreationTimestamp(Long creationTimestamp) {
      this.creationTimestamp = creationTimestamp;
   }

   public LinkedinUserInfo getCreator() {
      return this.creator;
   }

   public void setCreator(LinkedinUserInfo creator) {
      this.creator = creator;
   }

   public String getSummary() {
      return this.summary;
   }

   public void setSummary(String summary) {
      this.summary = summary;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public LinkedinRelationToViewer getRelationToViewer() {
      return this.relationToViewer;
   }

   public void setRelationToViewer(LinkedinRelationToViewer relationToViewer) {
      this.relationToViewer = relationToViewer;
   }

   public String getSiteGroupPostUrl() {
      return this.siteGroupPostUrl;
   }

   public void setSiteGroupPostUrl(String siteGroupPostUrl) {
      this.siteGroupPostUrl = siteGroupPostUrl;
   }

   public LinkedinComments getComments() {
      return this.comments;
   }

   public void setComments(LinkedinComments comments) {
      this.comments = comments;
   }

   public LinkedinLikes getLikes() {
      return this.likes;
   }

   public void setLikes(LinkedinLikes likes) {
      this.likes = likes;
   }

   public LinkedinAttachment getAttachment() {
      return this.attachment;
   }

   public void setAttachment(LinkedinAttachment attachment) {
      this.attachment = attachment;
   }

   public String getGroupId() {
      return this.groupId;
   }

   public void setGroupId(String groupId) {
      this.groupId = groupId;
   }

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {

         Facade.log.debug("Analyzing text of Group post id {}", get_id());
         StringBuilder s = new StringBuilder();

         String[] relevantFields = { summary, title };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         if (attachment != null)
            s.append(attachment.getContentDomain()).append(". ")
                  .append(attachment.getSummary()).append(". ")
                  .append(attachment.getTitle()).append(". ");

         if (comments != null)
            for (LinkedinComment comment : comments.getCommentsList()) {
               s.append(comment.getText()).append(". ");
            }

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));
         if (attachment != null && !urls.contains(attachment.getContentUrl()))
            urls.add(attachment.getContentUrl());
         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            Facade.log.debug("Analyzing url: {}", url);
            if (url != null && !url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error(
                        "Limit reached while processing linkedin group post id: {}",
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
