/*
 * @(#)TwitterUtils.java   1.0   19/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.twitter;

import it.expertfinding.datamodel.resources.twitter.Tweet;
import it.expertfinding.datamodel.resources.twitter.TwitterPage;
import it.expertfinding.datamodel.users.twitter.TwitterUser;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException.DuplicateKey;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.commons.configuration.Configuration;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Parameter;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;

/**
 * @author Teo
 * @version 1.0
 */
public class TwitterUtils {

   protected static Logger log = Facade.log;
   protected static Configuration conf = Facade.conf;

   protected static DBCollection tColl = Facade.db.getCollection(Facade.TABLE_TWITTER);

   private static OAuthService twitter = new ServiceBuilder().provider(TwitterApi.class)
         .apiKey(conf.getString("twitter.API_KEY"))
         .apiSecret(conf.getString("twitter.API_SECRET")).build();

   protected static ResponseWrapper getJsonFromTwitter(String url, Token token)
         throws TwitterException {

      StringBuilder message = new StringBuilder("Making a GET request to ");
      message.append(url);
      message.append("\nProcessing Response");
      log.debug(message.toString());
      OAuthRequest request = null;
      Response response;
      do {
         try {
            request = new OAuthRequest(Verb.GET, url);
            twitter.signRequest(token, request);
            response = request.send();
         } catch (Exception e) {
            response = null;
         }
         if (response != null && response.getCode() == 200) {
            message = new StringBuilder(
                  "Twitter responded with HTTP status code 200 and response body: ");
            message.append(response.getBody());

            log.debug(message.toString());
            message = null;
         } else if (response != null && response.getCode() == 401) {
            message = new StringBuilder(
                  "Twitter responded with HTTP status code 401 and response body: ");
            message.append(response.getBody());
            log.error(message.toString());
            message = null;
            break;
         } else {
            try {
               log.error(response.getBody());
               message = new StringBuilder("Rate limit remaining is: ");
               Long rateLimitRemaining = Long.parseLong(response.getHeaders().get(
                     "X-RateLimit-Remaining"));
               message.append(rateLimitRemaining);
               message.append(" and Rate limit reset time is: ");
               Long resetTimeInSeconds = Long.parseLong(response.getHeaders().get(
                     "X-RateLimit-Reset"));
               message.append(resetTimeInSeconds);
               log.debug(message.toString());
               if (rateLimitRemaining <= 1) {
                  Long waitTime = resetTimeInSeconds * 1000 - new Date().getTime() + 500L;
                  log.debug("Waiting " + waitTime + " seconds");
                  Thread.sleep(waitTime);
               }
               message = null;
            } catch (Exception e) {
               log.error("Error while executing request, retrying!!!!!!!!!!!!!");
            }
         }
      } while (response == null || response.getCode() != 200);

      message = new StringBuilder("Rate limit remaining is: ");
      Long rateLimitRemaining = Long.parseLong(response.getHeaders().get(
            "X-RateLimit-Remaining"));
      message.append(rateLimitRemaining);
      message.append(" and Rate limit reset time is: ");
      Long resetTimeInSeconds = Long.parseLong(response.getHeaders()
            .get("X-RateLimit-Reset"));
      message.append(resetTimeInSeconds);
      log.debug(message.toString());
      message = null;
      return new ResponseWrapper(response.getBody(), rateLimitRemaining, resetTimeInSeconds);
   }

   protected static String buildUrl(String path, Parameter... parameters) {
      StringBuilder url = new StringBuilder();
      url.append(path);
      int i = 0;
      if (parameters != null) {
         for (Parameter parameter : parameters) {
            url.append((i == 0) ? "?" : "&");
            url.append(parameter.asUrlEncodedPair());
            i++;
         }
      }
      return url.toString();
   }

   public static <T> T fetchObject(Token token, String apiPath, Class<T> objectType,
         Parameter... parameters) throws Exception {

      String url = buildUrl(apiPath, parameters);

      TwitterJsonMapper jsonMapper = new TwitterJsonMapper();
      ResponseWrapper response = getJsonFromTwitter(url, token);
      if (response.getRateLimitRemaining() <= 1) {
         log.debug("Reaching the rate limit of Twitter API");
         log.debug("Remaining: " + response.getRateLimitRemaining()
               + " operations, reset time in seconds: " + response.getResetTimeInSeconds());
         Long waitTime = response.getResetTimeInSeconds() * 1000 - new Date().getTime()
               + 500L;
         log.debug("Waiting " + waitTime + " seconds");
         Thread.sleep(waitTime);
      }
      return jsonMapper.toJavaObject(response.getBody(), objectType);

   }

   @SuppressWarnings("unchecked")
   public static <T> List<T> fetchConnection(Token token, String apiPath,
         Class<T> connectionType, Parameter... parameters) throws Exception {

      String url = buildUrl(apiPath, parameters);

      ResponseWrapper response = getJsonFromTwitter(url, token);
      if (response.getRateLimitRemaining() <= 1) {
         log.debug("Reaching the rate limit of Twitter API");
         log.debug("Remaining: " + response.getRateLimitRemaining()
               + " operations, reset time in seconds: " + response.getResetTimeInSeconds());
         Long waitTime = response.getResetTimeInSeconds() * 1000 - new Date().getTime()
               + 500L;
         log.debug("Waiting " + waitTime + " seconds");
         Thread.sleep(waitTime);
      }
      List<T> data = new ArrayList<T>();
      JsonArray jsonArray;
      try {
         jsonArray = new JsonArray(response.getBody());
         TwitterJsonMapper jsonMapper = new TwitterJsonMapper();
         for (int i = 0; i < jsonArray.length(); i++)
            data.add(connectionType.equals(JsonObject.class) ? (T) jsonArray.get(i)
                  : jsonMapper.toJavaObject(jsonArray.get(i).toString(), connectionType));
      } catch (JsonException e) {
         throw new TwitterJsonMappingException(
               "The connection JSON you provided was invalid: " + response.getBody(), e);
      }

      return data;

      // for (int i = 0; i < jsonData.length(); i++)
      // data.add(connectionType.equals(JsonObject.class) ? (T) jsonData.get(i) :
      // facebookClient.getJsonMapper()
      // .toJavaObject(jsonData.get(i).toString(), connectionType));

   }

   public static List<Tweet> fetchTimeline(Token token, String apiPath,
         Parameter... parameters) throws Exception {

      String url = buildUrl(apiPath, parameters);

      ResponseWrapper response = getJsonFromTwitter(url, token);
      if (response.getRateLimitRemaining() <= 1) {
         log.debug("Reaching the rate limit of Twitter API");
         log.debug("Remaining: " + response.getRateLimitRemaining()
               + " operations, reset time in seconds: " + response.getResetTimeInSeconds());
         Long waitTime = response.getResetTimeInSeconds() * 1000 - new Date().getTime()
               + 500L;
         log.debug("Waiting " + waitTime + " seconds");
         Thread.sleep(waitTime);
      }
      List<Tweet> tweets = new ArrayList<Tweet>();
      JsonArray jsonArray;
      try {
         jsonArray = new JsonArray(response.getBody());
         TwitterJsonMapper jsonMapper = new TwitterJsonMapper();
         for (int i = 0; i < jsonArray.length(); i++)
            tweets.add(jsonMapper.toJavaObject(jsonArray.get(i).toString(), Tweet.class));
      } catch (JsonException e) {
         throw new TwitterJsonMappingException(
               "The connection JSON you provided was invalid: " + response.getBody(), e);
      }

      return tweets;

   }

   public static List<Long> getFollowers(Token token, String twitterId) throws Exception {
      ConnectionsWrapper followers;
      try {

         followers = fetchObject(token, conf.getString("twitter.followers"),
               ConnectionsWrapper.class, new Parameter("user_id", twitterId));
      } catch (Exception e) {
         throw new Exception("Exception retrieving followers of twitter user " + twitterId,
               e);
      }

      return followers.getIds();
   }

   public static List<Long> getFollowing(Token token, String twitterId) throws Exception {

      ConnectionsWrapper followings;
      try {
         followings = fetchObject(token, conf.getString("twitter.following"),
               ConnectionsWrapper.class, new Parameter("user_id", twitterId));
      } catch (Exception e) {
         throw new Exception("Exception retrieving followings of twitter user " + twitterId,
               e);
      }
      // TODO user with more than 5000 followers
      return followings.getIds();
   }

   public static List<Long> getFavorites(Token token, String twitterId) throws Exception {
      List<Tweet> favorites = new ArrayList<Tweet>();
      List<Long> ids = new ArrayList<Long>();
      try {
         favorites = fetchConnection(token, conf.getString("twitter.favorites"),
               Tweet.class, new Parameter("include_entities", "true"), new Parameter(
                     "count", "200"), new Parameter("id", twitterId), new Parameter(
                     "include_rts", "true"));
      } catch (Exception e) {
         throw new Exception(
               "Error while retrieving favorites of twitter user " + twitterId, e);
      }

      int i = 0;

      do {
         if (!favorites.isEmpty()) {
            i++;
            log.debug("Retrieved page: " + i);
            List<DBObject> dbTweets = new ArrayList<DBObject>();
            try {
               for (Tweet t : favorites) {
                  ids.add(t.get_id());
                  dbTweets.add(Converter.toDBObject(t));
               }
               MongoUtils.insertObjectsData(tColl, dbTweets);
            } catch (DuplicateKey e) {
               throw new Exception("Duplicate Key", e);
            } catch (Exception e) {
               e.printStackTrace();
               throw new Exception("Error while retrieving favorites of twitter user "
                     + twitterId, e);
            }
            // setting maxId to create next page url
            Long maxId = null;
            try {
               maxId = favorites.get(favorites.size() - 1).get_id() - 1;
               log.debug("maxId = " + maxId.toString());
            } catch (Exception e) {
               log.debug("Unable to get maxId");
            }
            try {
               favorites = null;
               favorites = fetchConnection(token, conf.getString("twitter.favorites"),
                     Tweet.class, new Parameter("include_entities", "true"), new Parameter(
                           "count", "200"), new Parameter("id", twitterId), new Parameter(
                           "max_id", maxId.toString()), new Parameter("include_rts", "true"));
            } catch (Exception e) {
               throw new Exception("Error while retrieving favorites of twitter user "
                     + twitterId, e);
            }
         }

      } while (!favorites.isEmpty());
      return ids;
   }

   public static TwitterUser createTwitterUser(Token token, String twitterId)
         throws Exception {

      try {
         TwitterUser user = fetchObject(token, conf.getString("twitter.user"),
               TwitterUser.class, new Parameter("user_id", twitterId));
         user.setSinceIdRecovered(0L);
         user.setMaxIdRecovered(0L);
         user.setToken(token.getToken());
         user.setSecret(token.getSecret());
         return user;
      } catch (Exception e) {
         throw new Exception("Exception retrieving user profile", e);
      }

   }

   private static List<Tweet> buildConnection(Token token, String twitterId, Long maxId,
         Long sinceId) throws Exception {
      if (maxId != null && sinceId != null)
         return fetchTimeline(token, conf.getString("twitter.feed"), new Parameter(
               "include_entities", "true"), new Parameter("count", "200"), new Parameter(
               "user_id", twitterId), new Parameter("max_id", maxId.toString()),
               new Parameter("since_id", sinceId.toString()), new Parameter("include_rts",
                     "true"));
      else if (maxId != null && sinceId == null)
         return fetchTimeline(token, conf.getString("twitter.feed"), new Parameter(
               "include_entities", "true"), new Parameter("count", "200"), new Parameter(
               "user_id", twitterId), new Parameter("max_id", maxId.toString()),
               new Parameter("include_rts", "true"));
      else if (maxId == null && sinceId != null)
         return fetchTimeline(token, conf.getString("twitter.feed"), new Parameter(
               "include_entities", "true"), new Parameter("count", "200"), new Parameter(
               "user_id", twitterId), new Parameter("since_id", sinceId.toString()),
               new Parameter("include_rts", "true"));
      return fetchTimeline(token, conf.getString("twitter.feed"), new Parameter(
            "include_entities", "true"), new Parameter("count", "200"), new Parameter(
            "user_id", twitterId), new Parameter("include_rts", "true"));

   }

   private static List<Parameter> buildRequestBatchs(List<Long> ids, int size) {
      List<Parameter> requestBatchs = new ArrayList<Parameter>();
      int i = 0;
      StringBuilder paramBuilder = new StringBuilder();

      for (Long l : ids) {
         // splitting into smaller requests
         if (i < size) {

            if (i != 0)
               paramBuilder.append(",");

            paramBuilder.append(l.toString());
            i++;
         } else {

            requestBatchs.add(new Parameter("user_id", paramBuilder.toString()));
            paramBuilder = new StringBuilder(l.toString());
            i = 1;

         }
      }

      requestBatchs.add(new Parameter("user_id", paramBuilder.toString()));

      return requestBatchs;
   }

   public static void saveTwitterPagesOfUser(Token token, String twitterId, Long sinceId,
         List<Long> following, List<Long> followers) throws Exception {

      following.removeAll(followers);

      List<Parameter> requestBatchs = buildRequestBatchs(following, 75);

      for (Parameter userIds : requestBatchs) {

         List<TwitterPage> pages = null;
         try {
            pages = fetchConnection(token, conf.getString("twitter.pages"),
                  TwitterPage.class, userIds);
         } catch (Exception e) {
            throw new Exception("Error while retrieving pages of twitter user " + twitterId,
                  e);

         }

         try {
            for (TwitterPage p : pages) {
               DBObject page = MongoUtils.isTwitterPageIdPresent(tColl, p.get_id());
               Long sinceIdNew = null;
               if (page != null) {
                  if ((Long) page.get("sinceIdRecovered") == null)
                     sinceIdNew = saveTimeLineFromSource(token, p.get_id().toString(), null,
                           null, 10, tColl);
                  else if ((Long) page.get("sinceIdRecovered") != 0L)
                     sinceIdNew = saveTimeLineFromSource(token, p.get_id().toString(), null,
                           (Long) page.get("sinceIdRecovered"), 10, tColl);
                  else
                     saveTimeLineFromSource(token, p.get_id().toString(),
                           (Long) page.get("maxIdRecovered"), null, 10, tColl);
               } else {
                  MongoUtils.saveDocumentData(tColl, p);
                  sinceIdNew = saveTimeLineFromSource(token, p.get_id().toString(), null,
                        null, 10, tColl);
               }
               p.setSinceIdRecovered(sinceIdNew);
               page = MongoUtils.isTwitterPageIdPresent(tColl, p.get_id());
               p.setMaxIdRecovered((Long) page.get("maxIdRecovered"));
               MongoUtils.updateTwitterData(p.get_id(), p);

            }

         } catch (DuplicateKey e) {
            throw new Exception("Duplicate Key", e);
         } catch (Exception e) {
            throw new Exception("Error while retrieving pages of twitter user " + twitterId,
                  e);
         }
      }

   }

   /**
    * Retrieve tweet of user and save them to Twitter collection in MongoDB. Return the id of
    * the first post, necessary to implement paging.<br>
    * See also:<br>
    * <a href="https://dev.twitter.com/docs/working-with-timelines">Working with
    * timelines</a>
    * 
    * @param twitterId
    *           Id of the user timeline.
    * @param maxId
    *           First post id to retrieve (descending). Inclusive. May be {@code null}.
    * @param sinceId
    *           Fetrieve post with id greater than sinceId. Not Inclusive. May be
    *           {@code null}.
    * @param pages
    *           Max number of pages to retrieve.
    * @return The first post retrieved id
    */
   public static Long saveTimeLineFromSource(Token token, String twitterId, Long maxId,
         Long sinceId, int pages, DBCollection pageOrUser) throws Exception {
      List<Tweet> tweets = new ArrayList<Tweet>();

      try {
         tweets = buildConnection(token, twitterId, maxId, sinceId);
      } catch (Exception e) {
         throw new Exception("Error while retrieving timeline of twitter user " + twitterId,
               e);
      }

      int i = 0;
      Long toReturn = null;
      if (!tweets.isEmpty())
         toReturn = tweets.get(0).get_id();
      else
         return null;
      do {
         i++;
         log.debug("Retrieved page: " + i);
         List<DBObject> dbTweets = new ArrayList<DBObject>();
         try {
            for (Tweet t : tweets) {
               dbTweets.add(Converter.toDBObject(t));
            }
            MongoUtils.insertObjectsData(tColl, dbTweets);
         } catch (DuplicateKey e) {
            throw new Exception("Duplicate Key", e);
         } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while retrieving timeline of twitter user "
                  + twitterId, e);
         }
         // setting maxId to create next page url
         try {
            maxId = tweets.get(tweets.size() - 1).get_id() - 1;
            MongoUtils.updateMaxIdData(pageOrUser, Long.parseLong(twitterId), maxId);
            log.debug("maxId = " + maxId.toString());
         } catch (Exception e) {
            log.debug("Unable to get maxId");
         }

         if (pages == i)
            break;

         try {
            tweets = buildConnection(token, twitterId, maxId, sinceId);
         } catch (Exception e) {
            throw new Exception("Error while retrieving timeline of twitter user "
                  + twitterId, e);
         }

      } while (!tweets.isEmpty());

      return (toReturn != null) ? toReturn : (Long) MongoUtils.isTwitterPageIdPresent(tColl,
            Long.parseLong(twitterId)).get("sinceIdRecovered");
   }

   public static class ConnectionsWrapper {

      @Twitter
      private final List<Long> ids = new ArrayList<Long>();

      /**
       * @return Returns the ids.
       */
      public List<Long> getIds() {
         return this.ids;
      }

   }

   public static class ResponseWrapper {

      private String body;

      @Twitter
      private final List<Error> errors = new ArrayList<Error>();

      private Long rateLimitRemaining;

      private Long resetTimeInSeconds;

      /**
       * @return Returns the body.
       */
      public String getBody() {
         return this.body;
      }

      /**
       * @param body
       *           The body to set.
       */
      public void setBody(String body) {
         this.body = body;
      }

      /**
       * @return Returns the errors.
       */
      public List<Error> getErrors() {
         return this.errors;
      }

      /**
       * @return Returns the rateLimitRemaining.
       */
      public Long getRateLimitRemaining() {
         return this.rateLimitRemaining;
      }

      /**
       * @param rateLimitRemaining
       *           The rateLimitRemaining to set.
       */
      public void setRateLimitRemaining(Long rateLimitRemaining) {
         this.rateLimitRemaining = rateLimitRemaining;
      }

      /**
       * @return Returns the resetTimeInSeconds.
       */
      public Long getResetTimeInSeconds() {
         return this.resetTimeInSeconds;
      }

      /**
       * @param resetTimeInSeconds
       *           The resetTimeInSeconds to set.
       */
      public void setResetTimeInSeconds(Long resetTimeInSeconds) {
         this.resetTimeInSeconds = resetTimeInSeconds;
      }

      public ResponseWrapper() {
         super();
      }

      public ResponseWrapper(String body, Long rateLimitRemaining, Long resetTimeInSeconds) {
         super();
         this.body = body;
         this.rateLimitRemaining = rateLimitRemaining;
         this.resetTimeInSeconds = resetTimeInSeconds;
      }

      public static class Error {

         @Twitter
         private String message;
         @Twitter
         private Long code;

         /**
          * @return Returns the message.
          */
         public String getMessage() {
            return this.message;
         }

         /**
          * @param message
          *           The message to set.
          */
         public void setMessage(String message) {
            this.message = message;
         }

         /**
          * @return Returns the code.
          */
         public Long getCode() {
            return this.code;
         }

         /**
          * @param code
          *           The code to set.
          */
         public void setCode(Long code) {
            this.code = code;
         }

      }

   }
}
