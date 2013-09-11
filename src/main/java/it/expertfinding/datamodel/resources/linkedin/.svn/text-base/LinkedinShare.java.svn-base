/*
 * @(#)LinkedinCurrentShare.java   1.0   17/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.linkedin;

import it.expertfinding.datamodel.users.linkedin.LinkedinRelationToViewer;
import it.expertfinding.datamodel.users.linkedin.LinkedinStandardProfileRequest;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinShare {

   @Linkedin
   private String id;
   @Linkedin
   private Long timestamp;
   @Linkedin
   private String comment;
   @Linkedin
   private LinkedinContent content;
   @Linkedin
   private LinkedinCodeField visibility;
   @Linkedin
   private LinkedinSource source;
   @Linkedin
   private LinkedinAuthor author;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public Long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(Long timestamp) {
      this.timestamp = timestamp;
   }

   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public LinkedinContent getContent() {
      return this.content;
   }

   public void setContent(LinkedinContent content) {
      this.content = content;
   }

   public LinkedinCodeField getVisibility() {
      return this.visibility;
   }

   public void setVisibility(LinkedinCodeField visibility) {
      this.visibility = visibility;
   }

   public LinkedinSource getSource() {
      return this.source;
   }

   public void setSource(LinkedinSource source) {
      this.source = source;
   }

   public LinkedinAuthor getAuthor() {
      return this.author;
   }

   public void setAuthor(LinkedinAuthor author) {
      this.author = author;
   }

   public static class LinkedinContent {

      @Linkedin
      private String id;
      @Linkedin
      private String title;
      @Linkedin
      private String description;
      @Linkedin
      private String submittedUrl;
      @Linkedin
      private String shortenedUrl;
      @Linkedin
      private String submittedImageUrl;
      @Linkedin
      private String thumbnailUrl;
      @Linkedin
      private String resolvedUrl;
      @Linkedin
      private String eyebrowUrl;

      public String getId() {
         return this.id;
      }

      public void setId(String id) {
         this.id = id;
      }

      public String getTitle() {
         return this.title;
      }

      public void setTitle(String title) {
         this.title = title;
      }

      public String getDescription() {
         return this.description;
      }

      public void setDescription(String description) {
         this.description = description;
      }

      public String getSubmittedUrl() {
         return this.submittedUrl;
      }

      public void setSubmittedUrl(String submittedUrl) {
         this.submittedUrl = submittedUrl;
      }

      public String getShortenedUrl() {
         return this.shortenedUrl;
      }

      public void setShortenedUrl(String shortenedUrl) {
         this.shortenedUrl = shortenedUrl;
      }

      public String getSubmittedImageUrl() {
         return this.submittedImageUrl;
      }

      public void setSubmittedImageUrl(String submittedImageUrl) {
         this.submittedImageUrl = submittedImageUrl;
      }

      public String getThumbnailUrl() {
         return this.thumbnailUrl;
      }

      public void setThumbnailUrl(String thumbnailUrl) {
         this.thumbnailUrl = thumbnailUrl;
      }

      public String getResolvedUrl() {
         return this.resolvedUrl;
      }

      public void setResolvedUrl(String resolvedUrl) {
         this.resolvedUrl = resolvedUrl;
      }

      public String getEyebrowUrl() {
         return this.eyebrowUrl;
      }

      public void setEyebrowUrl(String eyebrowUrl) {
         this.eyebrowUrl = eyebrowUrl;
      }
   }

   public static class LinkedinSource {

      @Linkedin
      private LinkedinNamedField serviceProvider;
      @Linkedin
      private LinkedinNamedField application;

      public LinkedinNamedField getServiceProvider() {
         return serviceProvider;
      }

      public void setServiceProvider(LinkedinNamedField value) {
         this.serviceProvider = value;
      }

      public LinkedinNamedField getApplication() {
         return application;
      }

      public void setApplication(LinkedinNamedField value) {
         this.application = value;
      }
   }

   public static class LinkedinAuthor {

      @Linkedin
      private String id;
      @Linkedin
      private String firstName;
      @Linkedin
      private String lastName;
      @Linkedin
      private String headline;
      @Linkedin
      private LinkedinRelationToViewer relationToViewer;
      @Linkedin
      private LinkedinStandardProfileRequest apiStandardProfileRequest;
      @Linkedin
      private LinkedinStandardProfileRequest siteStandardProfileRequest;

      public String getId() {
         return id;
      }

      public void setId(String value) {
         this.id = value;
      }

      public String getFirstName() {
         return firstName;
      }

      public void setFirstName(String value) {
         this.firstName = value;
      }

      public String getLastName() {
         return lastName;
      }

      public void setLastName(String value) {
         this.lastName = value;
      }

      public String getHeadline() {
         return headline;
      }

      public void setHeadline(String value) {
         this.headline = value;
      }

      public LinkedinRelationToViewer getRelationToViewer() {
         return relationToViewer;
      }

      public void setRelationToViewer(LinkedinRelationToViewer value) {
         this.relationToViewer = value;
      }

      public LinkedinStandardProfileRequest getApiStandardProfileRequest() {
         return apiStandardProfileRequest;
      }

      public void setApiStandardProfileRequest(LinkedinStandardProfileRequest value) {
         this.apiStandardProfileRequest = value;
      }

      public LinkedinStandardProfileRequest getSiteStandardProfileRequest() {
         return siteStandardProfileRequest;
      }

      public void setSiteStandardProfileRequest(LinkedinStandardProfileRequest value) {
         this.siteStandardProfileRequest = value;
      }
   }

   public String getText(){
      
      StringBuilder s = new StringBuilder();
     
      String[] relevantFields = {comment, content.getDescription(), content.getTitle()};
      
      for(String field: relevantFields)
         if(field!=null) s.append(field).append(". ");
      
      return s.toString();
   }
}