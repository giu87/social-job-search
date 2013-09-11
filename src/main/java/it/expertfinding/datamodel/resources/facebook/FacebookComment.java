/*
 * @(#)FacebookComment.java   1.0   14/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.facebook;

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

public class FacebookComment extends FacebookObject {

   @Facebook
   private FacebookCategorizedObject from;

   @Facebook
   private String message;

   @Facebook
   private Long likes;

   @Facebook("created_time")
   private String createdTime;

   @Facebook("message_tags")
   private List<FacebookMessageTag> messageTags = new ArrayList<FacebookMessageTag>();

   public FacebookCategorizedObject getFrom() {
      return this.from;
   }

   public void setFrom(FacebookCategorizedObject from) {
      this.from = from;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public Long getLikes() {
      return this.likes;
   }

   public void setLikes(Long likes) {
      this.likes = likes;
   }

   public Date getCreatedTime() {
      return toDateFromLongFormat(createdTime);
   }

   public void setCreatedTime(Date createdTime) {
      this.createdTime = createdTime.toString();
   }

   public List<FacebookMessageTag> getMessageTags() {
      return unmodifiableList(messageTags);
   }

   public void setMessageTags(List<FacebookMessageTag> messageTags) {
      this.messageTags = messageTags;
   }

}
