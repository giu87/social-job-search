/*
 * @(#)LinkedinUpdate.java   1.0   25/apr/2012
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
import it.expertfinding.utils.linkedin.LinkedinJsonMapper;

import java.util.List;

import com.restfb.JsonMapper;
import com.restfb.json.JsonObject;

public class LinkedinUpdate extends LinkedinResource {

   @Linkedin
   private Long timestamp;
   @Linkedin("updateKey")
   private String _id;
   @Linkedin
   private String updateType;
   @Linkedin("updateContent")
   private JsonObject updateContentString;

   private LinkedinShare content;
   @Linkedin
   private boolean isCommentable;
   @Linkedin
   private boolean isLikable;
   @Linkedin
   private boolean isLiked;
   @Linkedin
   private Long numLikes;
   @Linkedin
   private LinkedinUpdateComments updateComments;
   @Linkedin
   private LinkedinLikes likes;

   public Long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(Long timestamp) {
      this.timestamp = timestamp;
   }

   public String getUpdateType() {
      return this.updateType;
   }

   public void setUpdateType(String updateType) {
      this.updateType = updateType;
   }

   @Override
   public String get_id() {
      return this._id;
   }

   @Override
   public void set_id(String _id) {
      this._id = _id;
   }

   public JsonObject getUpdateContentString() {
      return this.updateContentString;
   }

   public LinkedinShare getContent() {
      if (getUpdateContentString() != null) {
         try {
            JsonMapper jsonMapper = new LinkedinJsonMapper();
            return jsonMapper.toJavaObject(updateContentString.getJsonObject("person")
                  .getString("currentShare"), LinkedinShare.class);

         } catch (Exception e) {
         }
      }
      return this.content;
   }

   public void setContent(LinkedinShare content) {

      if (content != null)
         this.content = content;
      else if (getUpdateContentString() != null) {
         try {
            JsonMapper jsonMapper = new LinkedinJsonMapper();
            this.content = jsonMapper.toJavaObject(
                  updateContentString.getJsonObject("person").getString("currentShare"),
                  LinkedinShare.class);
         } catch (Exception e) {
         }
      }
   }

   public boolean isCommentable() {
      return this.isCommentable;
   }

   public void setCommentable(boolean isCommentable) {
      this.isCommentable = isCommentable;
   }

   public boolean isLikable() {
      return this.isLikable;
   }

   public void setLikable(boolean isLikable) {
      this.isLikable = isLikable;
   }

   public boolean isLiked() {
      return this.isLiked;
   }

   public void setLiked(boolean isLiked) {
      this.isLiked = isLiked;
   }

   public Long getNumLikes() {
      return this.numLikes;
   }

   public void setNumLikes(Long numLikes) {
      this.numLikes = numLikes;
   }

   public LinkedinUpdateComments getUpdateComments() {
      return this.updateComments;
   }

   public void setUpdateComments(LinkedinUpdateComments updateComments) {
      this.updateComments = updateComments;
   }

   public LinkedinLikes getLikes() {
      return this.likes;
   }

   public void setLikes(LinkedinLikes likes) {
      this.likes = likes;
   }

   public LinkedinUpdate() {
      super();
      this.setrType(RType.LINKEDIN_UPDATE);
   }

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {

         StringBuilder s = new StringBuilder();

         if (content != null)
            s.append(content.getText()).append(". ");

         if (updateComments != null)
            for (LinkedinUpdateComment comment : updateComments.getCommentsList()) {
               s.append(comment.getComment()).append(". ");
            }

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));

         if (content != null && content.getContent() != null)
            if (content.getContent().getSubmittedUrl() != null)
               urls.add(content.getContent().getSubmittedUrl());

         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            if (url != null && !url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error("Limit reached while processing linkedin update id: {}",
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
