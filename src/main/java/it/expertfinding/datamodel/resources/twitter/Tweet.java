/*
 * @(#)Tweet.java   1.0   20/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.twitter;

import static it.expertfinding.utils.DateUtils.toDateFromFormat;
import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.datamodel.resources.twitter.Tweet.Entities.Hashtag;
import it.expertfinding.datamodel.resources.twitter.Tweet.Entities.Url;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.twitter.Twitter;
import it.expertfinding.utils.twitter.TwitterJsonMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

public class Tweet extends TwitterResource {

   @Twitter("created_at")
   private String createdAt;

   @Twitter("possibly_sensitive")
   private String possiblySensitive;

   @Twitter("in_reply_to_screen_name")
   private String inReplyToScreenName;

   @Twitter
   private Place place;

   @Twitter("retweet_count")
   private Long retweetCount;

   @Twitter("in_reply_to_status_id")
   private Long inReplyToStatusId;

   @Twitter
   private TwitterUserHighlight user;

   @Twitter
   private Boolean retweeted;

   @Twitter("in_reply_to_user_id")
   private Long inReplyToUserId;

   @Twitter
   private Boolean truncated;

   @Twitter
   private Coordinates coordinates;

   @Twitter
   private String source;

   @Twitter
   private List<Long> contributors = new ArrayList<Long>();

   @Twitter
   private String text;

   @Twitter("retweeted_status")
   private Retweet retweetedStatus;

   @Twitter
   private Entities entities;

   @Twitter
   private JsonObject annotations;

   public Tweet() {
      super();
      this.setrType(RType.TWEET);
   }

   /**
    * Time when this Tweet was created.
    * 
    * @return Date the tweet was created.
    */
   public Date getCreatedAt() {
      return toDateFromFormat(createdAt);
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt.toString();
   }

   /**
    * Represents the geographic location of this Tweet.
    * 
    * @return The geographic location of this Tweet.
    */
   public Coordinates getCoordinates() {
      return this.coordinates;
   }

   public void setCoordinates(Coordinates coordinates) {
      this.coordinates = coordinates;
   }

   /**
    * This flag indicates that the URL contained in the tweet may contain content or media
    * identified as sensitive content.
    * 
    * @return This flag indicates that the URL contained in the tweet may contain content or
    *         media identified as sensitive content.
    */
   public String getPossiblySensitive() {
      return this.possiblySensitive;
   }

   public void setPossiblySensitive(String possiblySensitive) {
      this.possiblySensitive = possiblySensitive;
   }

   /**
    * If the represented Tweet is a reply, this field will contain the screen name of the
    * original Tweet's author.<br>
    * Example:<br>
    * {@code "in_reply_to_screen_name":"twitterapi"}
    * 
    * @return If the represented Tweet is a reply, this field will contain the screen name of
    *         the original Tweet's author.<br>
    *         Example:<br>
    *         {@code "in_reply_to_screen_name":"twitterapi"}
    */
   public String getInReplyToScreenName() {
      return this.inReplyToScreenName;
   }

   public void setInReplyToScreenName(String inReplyToScreenName) {
      this.inReplyToScreenName = inReplyToScreenName;
   }

   /**
    * The place of this tweet.<br>
    * Example:<br>
    * <code>"place":{"attributes":{}, "bounding_box":{"coordinates":[ [ [-77.119759,38.791645], [-76.909393,38.791645], [-76.909393,38.995548], [-77.119759,38.995548] ]</code>
    * 
    * @return The place of this tweet.<br>
    *         Example:<br>
    *         <code>"place":{"attributes":{}, "bounding_box":{"coordinates":[ [ [-77.119759,38.791645], [-76.909393,38.791645], [-76.909393,38.995548], [-77.119759,38.995548] ]</code>
    */
   public Place getPlace() {
      return this.place;
   }

   public void setPlace(Place place) {
      this.place = place;
   }

   /**
    * @return Returns the retweetCount.
    */
   public Long getRetweetCount() {
      return this.retweetCount;
   }

   /**
    * @param retweetCount
    *           The retweetCount to set.
    */
   public void setRetweetCount(Long retweetCount) {
      this.retweetCount = retweetCount;
   }

   /**
    * @return Returns the inReplyToStatusId.
    */
   public Long getInReplyToStatusId() {
      return this.inReplyToStatusId;
   }

   /**
    * @param inReplyToStatusId
    *           The inReplyToStatusId to set.
    */
   public void setInReplyToStatusId(Long inReplyToStatusId) {
      this.inReplyToStatusId = inReplyToStatusId;
   }

   /**
    * @return Returns the user.
    */
   public TwitterUserHighlight getUser() {
      return this.user;
   }

   /**
    * @param user
    *           The user to set.
    */
   public void setUser(TwitterUserHighlight user) {
      this.user = user;
   }

   /**
    * @return Returns the retweeted.
    */
   public Boolean getRetweeted() {
      return this.retweeted;
   }

   /**
    * @param retweeted
    *           The retweeted to set.
    */
   public void setRetweeted(Boolean retweeted) {
      this.retweeted = retweeted;
   }

   /**
    * @return Returns the inReplyToUserId.
    */
   public Long getInReplyToUserId() {
      return this.inReplyToUserId;
   }

   /**
    * @param inReplyToUserId
    *           The inReplyToUserId to set.
    */
   public void setInReplyToUserId(Long inReplyToUserId) {
      this.inReplyToUserId = inReplyToUserId;
   }

   /**
    * @return Returns the truncated.
    */
   public Boolean getTruncated() {
      return this.truncated;
   }

   /**
    * @param truncated
    *           The truncated to set.
    */
   public void setTruncated(Boolean truncated) {
      this.truncated = truncated;
   }

   /**
    * @return Returns the source.
    */
   public String getSource() {
      return this.source;
   }

   /**
    * @param source
    *           The source to set.
    */
   public void setSource(String source) {
      this.source = source;
   }

   /**
    * @return Returns the contributors.
    */
   public List<Long> getContributors() {
      return this.contributors;
   }

   /**
    * @param contributors
    *           The contributors to set.
    */
   public void setContributors(List<Long> contributors) {
      this.contributors = contributors;
   }

   /**
    * @return Returns the text.
    */
   public String getText() {
      return this.text;
   }

   /**
    * @param text
    *           The text to set.
    */
   public void setText(String text) {
      this.text = text;
   }

   /**
    * @return Returns the retweetedStatus.
    */
   public Retweet getRetweetedStatus() {
      return this.retweetedStatus;
   }

   /**
    * @param retweetedStatus
    *           The retweetedStatus to set.
    */
   public void setRetweetedStatus(Retweet retweetedStatus) {
      this.retweetedStatus = retweetedStatus;
   }

   /**
    * @return Returns the entities.
    */
   public Entities getEntities() {
      return this.entities;
   }

   /**
    * @param entities
    *           The entities to set.
    */
   public void setEntities(Entities entities) {
      this.entities = entities;
   }

   /**
    * @return Returns the annotations.
    */
   public JsonObject getAnnotations() {
      return this.annotations;
   }

   /**
    * @param annotations
    *           The annotations to set.
    */
   public void setAnnotations(JsonObject annotations) {
      this.annotations = annotations;
   }

   /**
    * @param createdAt
    *           The createdAt to set.
    */
   public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
   }

   @Deprecated
   public String getTextWithoutComments() {
      Facade.log.debug("Analyzing text of tweet id {}", get_id());
      String temp = getText();
      temp = temp.replaceAll("@[^\\s]+", "");

      for (Hashtag hT : getEntities().getHashtags()) {
         temp = temp.replace("#" + hT.getText(), hT.getText());
      }

      return temp;
   }

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {
         Facade.log.debug("Analyzing text of tweet id {}", get_id());

         StringBuilder urlText = new StringBuilder();
         String temp = getText();

         temp = temp.replaceAll("@[^\\s]+", "");

         for (Hashtag hT : getEntities().getHashtags()) {
            temp = temp.replace("#" + hT.getText(), hT.getText());
         }
         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (Url u : getEntities().getUrls()) {
            temp = temp.replace(u.getUrl(), "");
            try {
               Facade.log.debug(u.getUrl());
               String text = null;

               text = alchemyApi.HTMLGetText(URLUtils.getURLContent(u.getUrl()), null);
               // do {
               // text = alchemyApi.URLGetText(u.getUrl());
               // } while (text.contains("registrandoti potrai utilizzare"));

               urlText.append(text).append(". ");

            } catch (AlchemyLimitException e) {
               Facade.log.error("Limit reached while processing tweet id: {}", get_id());
               throw e;
            } catch (Exception e) {
               Facade.log.error("Error while processing tweet id: " + get_id(), e);
            }
         }
         List<String> remainingUrls = URLUtils.getURLfromString(temp);
         temp = remainingUrls.remove(0);
         for (String url : remainingUrls) {
            try {
               urlText.append(alchemyApi.HTMLGetText(URLUtils.getURLContent(url), null))
                     .append(". ");
            } catch (AlchemyLimitException e) {
               Facade.log.error("Limit reached while processing tweet id: {}", get_id());
               throw e;
            } catch (Exception e) {
            }
         }
         StringBuilder s = new StringBuilder(temp).append(". ").append(urlText);

         String rText = Constants.removeCommonMessages(s.toString());
         setResourceText(rText);
         MongoUtils.updateResourceText(this.getChannel(), this.get_id(), rText);
         return rText;
      } else
         return resourceText;

   }

   public static class Retweet {

      @Twitter("id")
      private Long _id;

      @Twitter
      private TwitterUserHighlight user;

      /**
       * @return Returns the _id.
       */
      public Long get_id() {
         return this._id;
      }

      /**
       * @param _id
       *           The _id to set.
       */
      public void set_id(Long _id) {
         this._id = _id;
      }

      /**
       * @return Returns the user.
       */
      public TwitterUserHighlight getUser() {
         return this.user;
      }

      /**
       * @param user
       *           The user to set.
       */
      public void setUser(TwitterUserHighlight user) {
         this.user = user;
      }

   }

   public static class Coordinates {

      @Twitter
      private String type;
      @Twitter
      private List<Double> coordinates = new ArrayList<Double>();

      /**
       * The type of data encoded in the coordinates property. This will be "Point" for Tweet
       * coordinates fields.<br>
       * Example:<br>
       * {@code "type":"Point"}
       * 
       * @return The type of data encoded in the coordinates property. This will be "Point"
       *         for Tweet coordinates fields.<br>
       *         Example:<br>
       *         {@code "type":"Point"}
       */
      public String getType() {
         return this.type;
      }

      public void setType(String type) {
         this.type = type;
      }

      /**
       * The longitude and latitude of the Tweet's location, as an array in the form of
       * [longitude, latitude].<br>
       * Example:<br>
       * {@code "coordinates":[-97.51087576,35.46500176]}
       * 
       * @return The longitude and latitude of the Tweet's location, as an array in the form
       *         of [longitude, latitude].<br>
       *         Example:<br>
       *         {@code "coordinates":[-97.51087576,35.46500176]}
       */
      public List<Double> getCoordinates() {
         return this.coordinates;
      }

      public void setCoordinates(List<Double> coordinates) {
         this.coordinates = coordinates;
      }

   }

   public static class Entities {

      @Twitter
      private List<Hashtag> hashtags = new ArrayList<Tweet.Entities.Hashtag>();
      @Twitter
      private List<Url> urls = new ArrayList<Tweet.Entities.Url>();
      @Twitter
      private List<Media> media = new ArrayList<Tweet.Entities.Media>();
      @Twitter("user_mentions")
      private List<UserMention> userMentions = new ArrayList<Tweet.Entities.UserMention>();

      /**
       * Represents hashtags which have been parsed out of the Tweet text.<br>
       * Example:<br>
       * <code>"hashtags":[{"indices":[32,36],"text":"lol"}]</code>
       * 
       * @return Returns the hashtags.
       */
      public List<Hashtag> getHashtags() {
         return this.hashtags;
      }

      /**
       * @see Entities#getHashtags()
       * @param hashtags
       *           The hashtags to set.
       */
      public void setHashtags(List<Hashtag> hashtags) {
         this.hashtags = hashtags;
      }

      /**
       * Represents URLs included in the text of the Tweet.<br>
       * Example:<br>
       * <code>"urls":[{"indices":[32,52], "url":"http:\/\/t.co\/IOwBrTZR",
       * "display_url":"youtube.com\/watch?v=oHg5SJ\u2026",
       * "expanded_url":"http:\/\/www.youtube.com\/watch?v=oHg5SJYRHA0"}]</code>
       * 
       * @return Returns the urls.
       */
      public List<Url> getUrls() {
         return this.urls;
      }

      /**
       * @see Entities#getUrls()
       * @param urls
       *           The urls to set.
       */
      public void setUrls(List<Url> urls) {
         this.urls = urls;
      }

      /**
       * Represents media elements uploaded with the Tweet.<br>
       * Example:<br>
       * <code>"media":[{"type":"photo", "sizes":{"thumb":{"h":150, "resize":"crop", "w":150},
       * "large":{"h":238, "resize":"fit", "w":226}, "medium":{"h":238, "resize":"fit",
       * "w":226}, "small":{"h":238, "resize":"fit", "w":226}}, "indices":[15,35],
       * "url":"http:\/\/t.co\/rJC5Pxsu",
       * "media_url":"http:\/\/p.twimg.com\/AZVLmp-CIAAbkyy.jpg",
       * "display_url":"pic.twitter.com\/rJC5Pxsu", "id":114080493040967680,
       * "id_str":"114080493040967680", "expanded_url":
       * "http:\/\/twitter.com\/yunorno\/status\/114080493036773378\/photo\/1",
       * "media_url_https":"https:\/\/p.twimg.com\/AZVLmp-CIAAbkyy.jpg"}]</code>
       * 
       * @return Returns the media.
       */
      public List<Media> getMedia() {
         return this.media;
      }

      /**
       * @see Entities#getMedia()
       * @param media
       *           The media to set.
       */
      public void setMedia(List<Media> media) {
         this.media = media;
      }

      /**
       * Represents other Twitter users mentioned in the text of the Tweet.<br>
       * Example:<br>
       * <code>"user_mentions":[{"name":"Twitter API", "indices":[4,15],
       * "screen_name":"twitterapi", "id":6253282, "id_str":"6253282"}]</code>
       * 
       * @return Returns the userMentions.
       */
      public List<UserMention> getUserMentions() {
         return this.userMentions;
      }

      /**
       * @see Entities#getUserMentions()
       * @param userMentions
       *           The userMentions to set.
       */
      public void setUserMentions(List<UserMention> userMentions) {
         this.userMentions = userMentions;
      }

      public static class Hashtag {

         @Twitter
         private List<Long> indices = new ArrayList<Long>();

         @Twitter
         private String text;

         /**
          * An array of integers indicating the offsets within the Tweet text where the
          * hashtag begins and ends. The first integer represents the location of the #
          * character in the Tweet text string. The second integer represents the location of
          * the first character after the hashtag. Therefore the difference between the two
          * numbers will be the length of the hashtag name plus one (for the '#' character).<br>
          * Example:<br>
          * {@code "indices":[32,36]}
          * 
          * @return Returns the indices.
          */
         public List<Long> getIndices() {
            return this.indices;
         }

         /**
          * @see Hashtag#getIndices
          * @param indices
          *           The indices to set.
          */
         public void setIndices(List<Long> indices) {
            this.indices = indices;
         }

         /**
          * Name of the hashtag, minus the leading '#' character.<br>
          * Example:<br>
          * {@code "text":"lol"}
          * 
          * @return Returns the text.
          */
         public String getText() {
            return this.text;
         }

         /**
          * @see Hashtag#getText
          * @param text
          *           The text to set.
          */
         public void setText(String text) {
            this.text = text;
         }

      }

      public static class Url {

         @Twitter
         private List<Long> indices = new ArrayList<Long>();

         @Twitter("display_url")
         private String displayUrl;

         @Twitter("expanded_url")
         private String expandedUrl;

         @Twitter
         private String url;

         /**
          * An array of integers representing offsets within the Tweet text where the URL
          * begins and ends. The first integer represents the location of the first character
          * of the URL in the Tweet text. The second integer represents the location of the
          * first non-URL character after the end of the URL.<br>
          * Example:<br>
          * {@code "indices":[32,36]}
          * 
          * @return Returns the indices.
          */
         public List<Long> getIndices() {
            return this.indices;
         }

         /**
          * @see Url#getIndices
          * @param indices
          *           The indices to set.
          */
         public void setIndices(List<Long> indices) {
            this.indices = indices;
         }

         /**
          * Version of the URL to display to clients.<br>
          * Example:<br>
          * {@code"display_url":"youtube.com\/watch?v=oHg5SJ\u2026"}
          * 
          * @return Returns the displayUrl.
          */
         public String getDisplayUrl() {
            return this.displayUrl;
         }

         /**
          * @see Url#getDisplayUrl()
          * @param displayUrl
          *           The displayUrl to set.
          */
         public void setDisplayUrl(String displayUrl) {
            this.displayUrl = displayUrl;
         }

         /**
          * Expanded version of {@link Url#displayUrl display_url}.<br>
          * Example:<br>
          * {@code "expanded_url":"http:\/\/www.youtube.com\/watch?v=oHg5SJYRHA0"}
          * 
          * @return Returns the expandedUrl.
          */
         public String getExpandedUrl() {
            return this.expandedUrl;
         }

         /**
          * @see Url#getExpandedUrl()
          * @param expandedUrl
          *           The expandedUrl to set.
          */
         public void setExpandedUrl(String expandedUrl) {
            this.expandedUrl = expandedUrl;
         }

         /**
          * Wrapped URL, corresponding to the value embedded directly into the raw Tweet
          * text, and the values for the {@link Url#indices indices} parameter.<br>
          * Example:<br>
          * {@code "url":"http:\/\/t.co\/IOwBrTZR"}
          * 
          * @return Returns the url.
          */
         public String getUrl() {
            return this.url;
         }

         /**
          * @see Url#getUrl()
          * @param url
          *           The url to set.
          */
         public void setUrl(String url) {
            this.url = url;
         }

      }

      public static class Media extends Url {

         @Twitter("id")
         private Long _id;

         @Twitter("media_url")
         private String mediaUrl;

         @Twitter("media_url_https")
         private String mediaUrlHttps;

         @Twitter
         private String type;

         @Twitter
         private Sizes sizes;

         /**
          * @return Returns the _id.
          */
         public Long get_id() {
            return this._id;
         }

         /**
          * @param _id
          *           The _id to set.
          */
         public void set_id(Long _id) {
            this._id = _id;
         }

         /**
          * An http:// URL pointing directly to the uploaded media file.<br>
          * Example:<br>
          * {@code "media_url":"http:\/\/p.twimg.com\/AZVLmp-CIAAbkyy.jpg"}
          * 
          * @return Returns the mediaUrl.
          */
         public String getMediaUrl() {
            return this.mediaUrl;
         }

         /**
          * @see Media#getMediaUrl()
          * @param mediaUrl
          *           The mediaUrl to set.
          */
         public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
         }

         /**
          * An https:// URL pointing directly to the uploaded media file, for embedding on
          * https pages.<br>
          * Example:<br>
          * {@code "media_url_https":"https:\/\/p.twimg.com\/AZVLmp-CIAAbkyy.jpg"}
          * 
          * @return Returns the mediaUrlHttps.
          */
         public String getMediaUrlHttps() {
            return this.mediaUrlHttps;
         }

         /**
          * @see Media#getMediaUrlHttps()
          * @param mediaUrlHttps
          *           The mediaUrlHttps to set.
          */
         public void setMediaUrlHttps(String mediaUrlHttps) {
            this.mediaUrlHttps = mediaUrlHttps;
         }

         /**
          * Type of uploaded media.<br>
          * Example:<br>
          * {@code "type":"photo"}
          * 
          * @return Returns the type.
          */
         public String getType() {
            return this.type;
         }

         /**
          * @see Media#getType()
          * @param type
          *           The type to set.
          */
         public void setType(String type) {
            this.type = type;
         }

         /**
          * An object showing available sizes for the media file.<br>
          * Example:<br>
          * <code>
          * "sizes":{"thumb":{"h":150, "resize":"crop", "w":150}, "large":{"h":238,
          * "resize":"fit", "w":226}, "medium":{"h":238, "resize":"fit", "w":226},
          * "small":{"h":238, "resize":"fit", "w":226}}</code>
          * 
          * @return Returns the sizes.
          */
         public Sizes getSizes() {
            return this.sizes;
         }

         /**
          * @see Media#getSizes()
          * @param sizes
          *           The sizes to set.
          */
         public void setSizes(Sizes sizes) {
            this.sizes = sizes;
         }

         public static class Sizes {

            @Twitter
            private Size thumb;
            @Twitter
            private Size medium;

            @Twitter
            private Size large;
            @Twitter
            private Size small;

            /**
             * Information for a thumbnail-sized version of the media.<br>
             * Example:<br>
             * <code>"thumb":{"h":150, "resize":"crop", "w":150}</code>
             * 
             * @return Returns the thumb.
             */
            public Size getThumb() {
               return this.thumb;
            }

            /**
             * @see Sizes#getThumb()
             * @param thumb
             *           The thumb to set.
             */
            public void setThumb(Size thumb) {
               this.thumb = thumb;
            }

            /**
             * Information for a medium-sized version of the media.<br>
             * Example:<br>
             * <code> "medium":{"h":238,
             * "resize":"fit", "w":226}</code>
             * 
             * @return Returns the medium.
             */
            public Size getMedium() {
               return this.medium;
            }

            /**
             * @see Sizes#getMedium()
             * @param medium
             *           The medium to set.
             */
            public void setMedium(Size medium) {
               this.medium = medium;
            }

            /**
             * Information for a large-sized version of the media.<br>
             * Example:<br>
             * <code> "large":{"h":238,
             * "resize":"fit", "w":226}</code>
             * 
             * @return Returns the large.
             */
            public Size getLarge() {
               return this.large;
            }

            /**
             * @see Sizes#getLarge()
             * @param large
             *           The large to set.
             */
            public void setLarge(Size large) {
               this.large = large;
            }

            /**
             * Information for a small-sized version of the media.<br>
             * Example:<br>
             * <code> "small":{"h":238,
             * "resize":"fit", "w":226}</code>
             * 
             * @return Returns the small.
             */
            public Size getSmall() {
               return this.small;
            }

            /**
             * @see Sizes#getSmall()
             * @param small
             *           The small to set.
             */
            public void setSmall(Size small) {
               this.small = small;
            }

            public static class Size {

               @Twitter
               private Long h;
               @Twitter
               private Long w;
               @Twitter
               private String resize;

               /**
                * Height in pixels of this size.<br>
                * Example:<br>
                * {@code "h":150}
                * 
                * @return Returns the h.
                */
               public Long getH() {
                  return this.h;
               }

               /**
                * @see Size#getH()
                * @param h
                *           The h to set.
                */
               public void setH(Long h) {
                  this.h = h;
               }

               /**
                * Width in pixels of this size.<br>
                * Example:<br>
                * {@code "w":150}
                * 
                * @return Returns the w.
                */
               public Long getW() {
                  return this.w;
               }

               /**
                * @see Size#getW()
                * @param w
                *           The w to set.
                */
               public void setW(Long w) {
                  this.w = w;
               }

               /**
                * Resizing method used to obtain this size. A value of fit means that the
                * media was resized to fit one dimension, keeping its native aspect ratio. A
                * value of crop means that the media was cropped in order to fit a specific
                * resolution.<br>
                * Example:<br>
                * {@code "resize":"crop"}
                * 
                * @return Returns the resize.
                */
               public String getResize() {
                  return this.resize;
               }

               /**
                * @see Size#getResize()
                * @param resize
                *           The resize to set.
                */
               public void setResize(String resize) {
                  this.resize = resize;
               }

            }
         }

      }

      public static class UserMention extends TwitterUserHighlight {

         @Twitter
         private List<Long> indices = new ArrayList<Long>();

         /**
          * An array of integers representing the offsets within the Tweet text where the
          * user reference begins and ends. The first integer represents the location of the
          * '@' character of the user mention. The second integer represents the location of
          * the first non-screenname character following the user mention.<br>
          * Example:<br>
          * {@code "indices":[32,36]}
          * 
          * @return Returns the indices.
          */
         public List<Long> getIndices() {
            return this.indices;
         }

         /**
          * @see UserMention#getIndices
          * @param indices
          *           The indices to set.
          */
         public void setIndices(List<Long> indices) {
            this.indices = indices;
         }

      }
   }

   public static class Place {

      @Twitter("bounding_box")
      private BoundingBox boundingBox;
      @Twitter
      private String country;
      @Twitter("country_code")
      private String countryCode;
      @Twitter("full_name")
      private String fullName;
      @Twitter("id")
      private String _id;
      @Twitter
      private String name;
      @Twitter("place_type")
      private String placeType;
      @Twitter
      private String url;
      @Twitter
      private Map<String, String> attributes = new HashMap<String, String>();

      /**
       * A bounding box of coordinates which encloses this place.<br>
       * Example:<br>
       * <code> "bounding_box": { "coordinates":[ [ [2.2241006,48.8155414],
       * [2.4699099,48.8155414], [2.4699099,48.9021461], [2.2241006,48.9021461] ] ],
       * "type":"Polygon"}</code>
       * 
       * @return A bounding box of coordinates which encloses this place.<br>
       *         Example:<br>
       *         <code> "bounding_box": { "coordinates":[ [ [2.2241006,48.8155414],
       *         [2.4699099,48.8155414], [2.4699099,48.9021461], [2.2241006,48.9021461] ] ],
       *         "type":"Polygon"}</code>
       */
      public BoundingBox getBoundingBox() {
         return this.boundingBox;
      }

      public void setBoundingBox(BoundingBox boundingBox) {
         this.boundingBox = boundingBox;
      }

      /**
       * Name of the country containing this place.<br>
       * Example:<br>
       * {@code "country":"France"}
       * 
       * @return Name of the country containing this place.<br>
       *         Example:<br>
       *         {@code "country":"France"}
       */
      public String getCountry() {
         return this.country;
      }

      public void setCountry(String country) {
         this.country = country;
      }

      /**
       * Shortened country code representing the country containing this place.<br>
       * Example:<br>
       * {@code "country_code":"FR"}
       * 
       * @return Shortened country code representing the country containing this place.<br>
       *         Example:<br>
       *         {@code "country_code":"FR"}
       */
      public String getCountryCode() {
         return this.countryCode;
      }

      public void setCountryCode(String countryCode) {
         this.countryCode = countryCode;
      }

      /**
       * Full human-readable representation of the place's name.<br>
       * Example:<br>
       * {@code "full_name":"Paris, Paris" }
       * 
       * @return Full human-readable representation of the place's name.<br>
       *         Example:<br>
       *         {@code "full_name":"Paris, Paris" }
       */
      public String getFullName() {
         return this.fullName;
      }

      public void setFullName(String fullName) {
         this.fullName = fullName;
      }

      /**
       * ID representing this place. Note that this is represented as a string, not an
       * integer.<br>
       * Example:<br>
       * {@code "id":"7238f93a3e899af6"}
       * 
       * @return ID representing this place. Note that this is represented as a string, not
       *         an integer.<br>
       *         Example:<br>
       *         {@code "id":"7238f93a3e899af6"}
       */
      public String get_id() {
         return this._id;
      }

      public void set_id(String _id) {
         this._id = _id;
      }

      /**
       * Short human-readable representation of the place's name.<br>
       * Example:<br>
       * {@code "name":"Paris"}
       * 
       * @return Short human-readable representation of the place's name.<br>
       *         Example:<br>
       *         {@code "name":"Paris"}
       */
      public String getName() {
         return this.name;
      }

      public void setName(String name) {
         this.name = name;
      }

      /**
       * The type of location represented by this place.<br>
       * Example:<br>
       * {@code "place_type":"city"}
       * 
       * @return The type of location represented by this place.<br>
       *         Example:<br>
       *         {@code "place_type":"city"}
       */
      public String getPlaceType() {
         return this.placeType;
      }

      public void setPlaceType(String placeType) {
         this.placeType = placeType;
      }

      /**
       * URL representing the location of additional place metadata for this place.<br>
       * Example:<br>
       * {@code "url":"http://api.twitter.com/1/geo/id/7238f93a3e899af6.json"}
       * 
       * @return URL representing the location of additional place metadata for this place.<br>
       *         Example:<br>
       *         {@code "url":"http://api.twitter.com/1/geo/id/7238f93a3e899af6.json"}
       */
      public String getUrl() {
         return this.url;
      }

      public void setUrl(String url) {
         this.url = url;
      }

      /**
       * An attribute is a key-value pair of arbitrary strings, but with some conventions.<br>
       * Example:<br>
       * <code> "attributes": { "street_address": "795 Folsom St", "623:id": "210176",
       * "twitter": "twitter" }</code><br>
       * <br>
       * <a href="https://dev.twitter.com/docs/about-geo-place-attributes">About Geo Place
       * Attributes</a>
       * 
       * @return The attributes of this place.<br>
       *         Example:<br>
       *         <code> "attributes": {
       *         "street_address": "795 Folsom St", "623:id": "210176", "twitter": "twitter"
       *         }</code><br>
       */
      public Map<String, String> getAttributes() {
         return this.attributes;
      }

      public void setAttributes(Map<String, String> attributes) {
         this.attributes = attributes;
      }

      public static class BoundingBox {

         @Twitter
         private String type;
         @Twitter("coordinates")
         private JsonArray coordinatesString;

         private List<List<List<Double>>> coordinates = new ArrayList<List<List<Double>>>();

         /**
          * The type of data encoded in the coordinates property. This will be "Polygon" for
          * bounding boxes.<br>
          * Example:<br>
          * {@code "type":"Polygon"}
          * 
          * @return The type of data encoded in the coordinates property. This will be
          *         "Polygon" for bounding boxes.<br>
          *         Example:<br>
          *         {@code "type":"Polygon"}
          */
         public String getType() {
            return this.type;
         }

         public void setType(String type) {
            this.type = type;
         }

         /**
          * A series of longitude and latitude points, defining a box which will contain the
          * Place entity this bounding box is related to. Each point is an array in the form
          * of [longitude, latitude]. Points are grouped into an array per bounding box.
          * Bounding box arrays are wrapped in one additional array to be compatible with the
          * polygon notation.<br>
          * Example:<br>
          * {@code "coordinates":[ [ [2.2241006,48.8155414],
          * [2.4699099,48.8155414], [2.4699099,48.9021461], [2.2241006,48.9021461] ] ]}
          * 
          * @return A series of longitude and latitude points, defining a box which will
          *         contain the Place entity this bounding box is related to. Each point is
          *         an array in the form of [longitude, latitude]. Points are grouped into an
          *         array per bounding box. Bounding box arrays are wrapped in one additional
          *         array to be compatible with the polygon notation.<br>
          *         Example:<br>
          *         {@code "coordinates":[ [ [2.2241006,48.8155414],
          * [2.4699099,48.8155414], [2.4699099,48.9021461], [2.2241006,48.9021461] ] ]}
          */
         public List<List<List<Double>>> getCoordinates() {
            if (getCoordinatesString() != null) {
               try {
                  return getCoordinatesList(getCoordinatesString());
               } catch (Exception e) {
                  // TODO: handle exception
               }

            }

            return this.coordinates;
         }

         public void setCoordinates(List<List<List<Double>>> coordinates) {
            if (coordinates != null)
               this.coordinates = coordinates;
            else if (getCoordinatesString() != null) {
               try {
                  this.coordinates = getCoordinatesList(getCoordinatesString());
               } catch (Exception e) {
                  // TODO: handle exception
               }

            }

         }

         private List<List<List<Double>>> getCoordinatesList(JsonArray jsonCoordinates) {
            TwitterJsonMapper jsonMapper = new TwitterJsonMapper();
            JsonArray jsonArray = (JsonArray) jsonCoordinates.get(0);
            List<List<Double>> coordinates = new ArrayList<List<Double>>();
            for (int i = 0; i < jsonArray.length(); i++) {
               coordinates.add(jsonMapper.toJavaList(jsonArray.get(i).toString(),
                     Double.class));
            }
            List<List<List<Double>>> polygon = new ArrayList<List<List<Double>>>();
            polygon.add(coordinates);
            return polygon;
         }

         public JsonArray getCoordinatesString() {
            return this.coordinatesString;
         }

      }

   }
}
