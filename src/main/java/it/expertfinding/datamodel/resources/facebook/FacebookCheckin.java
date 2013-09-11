/*
 * @(#)FacebookCheckin.java   1.0   15/apr/2012
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

import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

public class FacebookCheckin extends FacebookResource {

   @Facebook
   private String message;

   @Facebook
   private FacebookNamedObject from;

   @Facebook
   private FacebookApplication application;

   @Facebook
   private FacebookPlace place;

   @Facebook("created_time")
   private String createdTime;

   @Facebook
   private FacebookComments comments;

   @Facebook
   private FacebookDataList tags;

   @Facebook
   private FacebookDataList likes;

   /**
    * The ID, name, and location of the Facebook Page that represents the location of the
    * check-in.
    * 
    * @return The ID, name, and location of the Facebook Page that represents the location of
    *         the check-in.
    */
   public FacebookPlace getPlace() {
      return place;
   }

   public void setPlace(FacebookPlace place) {
      this.place = place;
   }

   /**
    * The ID and name of the application that made the check-in.
    * 
    * @return The ID and name of the application that made the check-in.
    */
   public FacebookApplication getApplication() {
      return application;
   }

   public void setApplication(FacebookApplication application) {
      this.application = application;
   }

   /**
    * The ID and name of the user who made the check-in.
    * 
    * @return The ID and name of the user who made the check-in.
    */
   public FacebookNamedObject getFrom() {
      return from;
   }

   public void setFrom(FacebookNamedObject from) {
      this.from = from;
   }

   /**
    * The message the user added to the check-in.
    * 
    * @return The message the user added to the check-in.
    */
   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   /**
    * The time the check-in was created.
    * 
    * @return The time the check-in was created.
    */
   public Date getCreatedTime() {
      return toDateFromLongFormat(createdTime);
   }

   public void setCreatedTime(Date createdTime) {
      this.createdTime = createdTime.toString();
   }

   /**
    * The comments for the check-in.
    * 
    * @return The comments for the check-in.
    */
   public FacebookComments getComments() {
      return comments;
   }

   public void setComments(FacebookComments comments) {
      this.comments = comments;
   }

   /**
    * The tags for the check-in.
    * 
    * @return The tags for the check-in.
    */
   public FacebookDataList getTags() {
      return tags;
   }

   public void setTags(FacebookDataList tags) {
      this.tags = tags;
   }

   /**
    * The likes for the check-in.
    * 
    * @return The likes for the check-in.
    */
   public FacebookDataList getLikes() {
      return likes;
   }

   public void setLikes(FacebookDataList likes) {
      this.likes = likes;
   }

   public FacebookCheckin() {
      super();
      this.setrType(RType.FACEBOOK_CHECKIN);
   }

   @Override
   public String getResourceText() {

      if (resourceText == null) {

         StringBuilder s = new StringBuilder();
         s.append(message);
         s.append(". ");

         if (comments != null) {
            for (FacebookComment comment : comments.getData()) {
               s.append(comment.getMessage());
               s.append(". ");
            }
         }

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));

         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            Facade.log.debug("Analyzing url: {}", url);
            if (!url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error("Limit reached while processing facebook checkin id: {}",
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
