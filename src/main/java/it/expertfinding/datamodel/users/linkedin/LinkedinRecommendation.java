/*
 * @(#)LinkedinRecommendation.java   1.0   18/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinCodeField;
import it.expertfinding.datamodel.resources.linkedin.LinkedinLikes;
import it.expertfinding.datamodel.resources.linkedin.LinkedinUserInfo;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinRecommendation {

   @Linkedin
   private String id;
   @Linkedin
   private LinkedinCodeField recommendationType;
   @Linkedin
   private String recommendationText;
   @Linkedin
   private String recommendationSnippet;
   @Linkedin
   private LinkedinUserInfo recommender;
   @Linkedin
   private LinkedinUserInfo recommendee;
   @Linkedin
   private String webUrl;
   @Linkedin
   private Long productId;
   @Linkedin
   private Long timestamp;
   @Linkedin
   private String text;
   @Linkedin
   private String reply;
   @Linkedin
   private LinkedinLikes likes;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public LinkedinCodeField getRecommendationType() {
      return this.recommendationType;
   }

   public void setRecommendationType(LinkedinCodeField recommendationType) {
      this.recommendationType = recommendationType;
   }

   public String getRecommendationText() {
      return this.recommendationText;
   }

   public void setRecommendationText(String recommendationText) {
      this.recommendationText = recommendationText;
   }

   public String getRecommendationSnippet() {
      return this.recommendationSnippet;
   }

   public void setRecommendationSnippet(String recommendationSnippet) {
      this.recommendationSnippet = recommendationSnippet;
   }

   public LinkedinUserInfo getRecommender() {
      return this.recommender;
   }

   public void setRecommender(LinkedinUserInfo recommender) {
      this.recommender = recommender;
   }

   public LinkedinUserInfo getRecommendee() {
      return this.recommendee;
   }

   public void setRecommendee(LinkedinUserInfo recommendee) {
      this.recommendee = recommendee;
   }

   public String getWebUrl() {
      return this.webUrl;
   }

   public void setWebUrl(String webUrl) {
      this.webUrl = webUrl;
   }

   public Long getProductId() {
      return this.productId;
   }

   public void setProductId(Long productId) {
      this.productId = productId;
   }

   public Long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(Long timestamp) {
      this.timestamp = timestamp;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getReply() {
      return this.reply;
   }

   public void setReply(String reply) {
      this.reply = reply;
   }

   public LinkedinLikes getLikes() {
      return this.likes;
   }

   public void setLikes(LinkedinLikes likes) {
      this.likes = likes;
   }

   public String getResourceText() {

      StringBuilder s = new StringBuilder();

      String[] relevantFields = { recommendationText, webUrl, text };

      for (String field : relevantFields)
         if (field != null)
            s.append(field).append(". ");

      if (recommender != null)
         s.append(recommender.getFirstName()).append(". ").append(recommender.getLastName())
               .append(". ");

      return s.toString();
   }
}
