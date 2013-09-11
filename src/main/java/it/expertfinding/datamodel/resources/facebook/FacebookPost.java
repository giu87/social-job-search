/*
 * @(#)FacebookPost.java   1.0   16/apr/2012
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
import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.restfb.DefaultJsonMapper;
import com.restfb.Facebook;
import com.restfb.JsonMapper;
import com.restfb.json.JsonObject;

public class FacebookPost extends FacebookResource {

   @Facebook
   private FacebookCategorizedObject from;

   @Facebook
   private String message;

   @Facebook
   private String story;

   @Facebook("created_time")
   private String createdTime;

   @Facebook("updated_time")
   private String updatedTime;

   @Facebook
   private FacebookComments comments;

   @Facebook
   private FacebookDataList to;

   @Facebook("with_tags")
   private FacebookDataList withTags;

   @Facebook
   private FacebookDataList likes;

   @Facebook
   private FacebookDataList shares;

   @Facebook("message_tags")
   private JsonObject messageTagsString;

   private List<FacebookMessageTag> messageTags = new ArrayList<FacebookMessageTag>();

   @Facebook("story_tags")
   private JsonObject storyTagsString;

   private List<FacebookMessageTag> storyTags = new ArrayList<FacebookMessageTag>();

   @Facebook
   private String picture;

   @Facebook
   private String icon;

   @Facebook
   private String link;

   @Facebook
   private String source;

   @Facebook
   private String name;

   @Facebook
   private String caption;

   @Facebook
   private String description;

   @Facebook
   private FacebookApplication application;

   @Facebook
   private FacebookPlace place;

   public Date getUpdatedTime() {
      return toDateFromLongFormat(updatedTime);
   }

   public void setupdatedTime(Date updatedTime) {
      this.updatedTime = updatedTime.toString();
   }

   public Date getCreatedTime() {
      return toDateFromLongFormat(createdTime);
   }

   public void setCreatedTime(Date createdTime) {
      this.createdTime = createdTime.toString();
   }

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

   public String getStory() {
      return this.story;
   }

   public void setStory(String story) {
      this.story = story;
   }

   public FacebookComments getComments() {
      return this.comments;
   }

   public void setComments(FacebookComments comments) {
      this.comments = comments;
   }

   public FacebookDataList getTo() {
      return this.to;
   }

   public void setTo(FacebookDataList to) {
      this.to = to;
   }

   public FacebookDataList getWithTags() {
      return this.withTags;
   }

   public void setWithTags(FacebookDataList withTags) {
      this.withTags = withTags;
   }

   public FacebookDataList getLikes() {
      return this.likes;
   }

   public void setLikes(FacebookDataList likes) {
      this.likes = likes;
   }

   public FacebookDataList getShares() {
      return this.shares;
   }

   public void setShares(FacebookDataList shares) {
      this.shares = shares;
   }

   public String getPicture() {
      return this.picture;
   }

   public void setPicture(String picture) {
      this.picture = picture;
   }

   public String getIcon() {
      return this.icon;
   }

   public void setIcon(String icon) {
      this.icon = icon;
   }

   public String getLink() {
      return this.link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public String getSource() {
      return this.source;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCaption() {
      return this.caption;
   }

   public void setCaption(String caption) {
      this.caption = caption;
   }

   public FacebookApplication getApplication() {
      return this.application;
   }

   public void setApplication(FacebookApplication application) {
      this.application = application;
   }

   public FacebookPlace getPlace() {
      return this.place;
   }

   public void setPlace(FacebookPlace place) {
      this.place = place;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public JsonObject getMessageTagsString() {
      return this.messageTagsString;
   }

   public JsonObject getStoryTagsString() {
      return this.storyTagsString;
   }

   public List<FacebookMessageTag> getStoryTags() {
      if (getMessageTagsString() != null) {
         try {
            return getTagsList(getStoryTagsString());
         } catch (Exception e) {
            // TODO: handle exception
         }

      }

      return this.storyTags;
   }

   public void setStoryTags(List<FacebookMessageTag> storyTags) {
      if (storyTags != null)
         this.storyTags = storyTags;
      else if (getStoryTagsString() != null) {
         try {
            this.storyTags = getTagsList(getStoryTagsString());
         } catch (Exception e) {
            // TODO: handle exception
         }

      }

   }

   public List<FacebookMessageTag> getMessageTags() {
      if (getMessageTagsString() != null) {
         try {
            return getTagsList(getMessageTagsString());
         } catch (Exception e) {
            // TODO: handle exception
         }

      }

      return this.messageTags;
   }

   public void setMessageTags(List<FacebookMessageTag> messageTags) {
      if (messageTags != null)
         this.messageTags = messageTags;
      else if (getMessageTagsString() != null) {
         try {
            this.messageTags = getTagsList(getMessageTagsString());
         } catch (Exception e) {
            // TODO: handle exception
         }

      }

   }

   public FacebookPost() {
      super();
      this.setrType(RType.FACEBOOK_POST);
   }

   @SuppressWarnings("unchecked")
   private List<FacebookMessageTag> getTagsList(JsonObject jsonTags) {
      JsonMapper jsonMapper = new DefaultJsonMapper();

      Iterator<String> keys = (Iterator<String>) jsonTags.keys();
      List<FacebookMessageTag> tagsList = new ArrayList<FacebookMessageTag>();
      while (keys.hasNext()) {

         tagsList.add(jsonMapper.toJavaObject(jsonTags.getJsonArray(keys.next())
               .getString(0), FacebookMessageTag.class));
      }
      return tagsList;
   }

   public String getResourceTextWithoutComments() throws AlchemyLimitException {

      if (resourceText == null) {

         Facade.log.debug("Analyzing text of post id {}", get_id());

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { message, name, description };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         String temp = s.toString();
         for (FacebookMessageTag tag : messageTags) {
            if (tag.getType() != null && tag.getType().equals("user"))
               temp = temp.replace(tag.getName(), "");
         }
         s = new StringBuilder(temp);

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));

         if (link != null && !urls.contains(link))
            urls.add(link);
         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            Facade.log.debug("Analyzing url: {}", url);
            if (!url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error("Limit reached while processing facebook post id: {}",
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

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {

         Facade.log.debug("Analyzing text of post id {}", get_id());

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { message, name, description };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         String temp = s.toString();
         for (FacebookMessageTag tag : messageTags) {
            if (tag.getType() != null && tag.getType().equals("user"))
               temp = temp.replace(tag.getName(), "");
         }
         s = new StringBuilder(temp);

         if (comments != null) {
            for (FacebookComment comment : comments.getData()) {
               String message = comment.getMessage();
               for (FacebookMessageTag tag : comment.getMessageTags()) {
                  if (tag.getType() != null && tag.getType().equals("user"))
                     message = message.replace(tag.getName(), "");
               }
               s.append(message).append(". ");
            }
         }

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));
         if (link != null && !urls.contains(link))
            urls.add(link);
         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            Facade.log.debug("Analyzing url: {}", url);
            if (!url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error("Limit reached while processing facebook post id: {}",
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
