/*
 * @(#)Crawler.java   1.0   12/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils;

import it.expertfinding.datamodel.resources.facebook.FacebookNamedObject;
import it.expertfinding.datamodel.users.facebook.FacebookUser;
import it.expertfinding.datamodel.users.twitter.TwitterUser;
import it.expertfinding.utils.facebook.FacebookUtils;
import it.expertfinding.utils.twitter.TwitterUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.commons.configuration.Configuration;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.model.Token;
import org.slf4j.Logger;

public class Crawler {

   protected static Logger log = Facade.log;
   protected static Configuration conf = Facade.conf;

   public static String gatherTokens() {

      HttpURLConnection connection = null;
      URL url;

      try {
         String queryUrl = "http://expertfinding.altervista.org/admin/getContacts.php";
         url = new URL(queryUrl);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("POST");
         connection.setDoOutput(true);
         OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

         // write parameters
         writer.write("user=expertfinding&pw=dwr56yuri8");
         writer.flush();
         // Get Response
         InputStream is = connection.getInputStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         String line;
         StringBuilder users = new StringBuilder();
         // StringBuffer result = new StringBuffer();
         while ((line = rd.readLine()) != null) {
            users.append(line);
         }
         rd.close();
         writer.close();
         return users.toString();
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      } finally {
         if (connection != null) {
            connection.disconnect();
         }
      }
   }

   public static FacebookUser getFacebookData(String token, String facebookId) {

      try {

         FacebookClient fc = new DefaultFacebookClient(token);
         FacebookUtils facebook = new FacebookUtils();
         facebook.setFc(fc);
         FacebookUser user = facebook.createFacebookUser(facebookId, token);
         user.setFriends(facebook.getFriendsofUser(facebookId));
         facebook.saveCheckinsOfUser(facebookId, null);
         facebook.savePostsFromSource(facebookId, "/feed", 150, null, null, 0);
         Facade.db.getCollection("facebookUser").insert(Converter.toDBObject(user));
         BasicDBObject queryUser = new BasicDBObject().append("_id", user.get_id());
         user.setGroups(facebook.saveGroupsOfUser(facebookId));
         Facade.db.getCollection("facebookUser").update(queryUser,
               Converter.toDBObject(user));
         user.setLikes(facebook.saveLikesOfUser(facebookId, null));
         Facade.db.getCollection("facebookUser").update(queryUser,
               Converter.toDBObject(user));

         return user;
      } catch (Exception e) {
         log.error("Error gathering FB data", e);
      }

      return null;

   }

   public static TwitterUser getTwitterData(String token, String secret, String twitterId) {
      try {

         Token twitter = new Token(token, secret);
         TwitterUser user = TwitterUtils.createTwitterUser(twitter, twitterId);
         user.setFollowing(TwitterUtils.getFollowing(twitter, twitterId));
         user.setFollowers(TwitterUtils.getFollowers(twitter, twitterId));
         user.setFavorites(TwitterUtils.getFavorites(twitter, twitterId));

         Long sinceId = TwitterUtils.saveTimeLineFromSource(twitter, twitterId, null, null,
               0, Facade.db.getCollection("twitterUser"));
         user.setSinceIdRecovered(sinceId);
         Facade.db.getCollection("twitterUser").insert(Converter.toDBObject(user));
         List<Long> pages = new ArrayList<Long>();
         pages.addAll(user.getFollowing());
         TwitterUtils.saveTwitterPagesOfUser(twitter, twitterId, null, pages,
               user.getFollowers());

         return user;
      } catch (Exception e) {
         log.error("Error gathering Twitter data", e);
      }

      return null;
   }

   public static class CrawlerThreadTwitter implements Runnable {

      Thread t;
      String token;
      String secret;
      String id;

      CrawlerThreadTwitter(String token, String secret, String id) {

         this.token = token;
         this.secret = secret;
         this.id = id;
         // Create a new, second thread
         t = new Thread(this, "Twitter " + this.id + " Thread");
         System.out.println("Child thread Twitter id: " + id);
         t.start(); // Start the thread
      }

      // This is the entry point for the second thread.
      @Override
      public void run() {
         try {
            getTwitterData(token, secret, id);
            // log.debug("Retrieving friends of user id: " + id);
            // List<Long> friends = new ArrayList<Long>();
            // friends.addAll(user.getFollowers());
            // friends.retainAll(user.getFollowing());
            // for (Long friendId : friends) {
            // log.debug("Retrieving information of friend id: " + friendId);
            // getTwitterData(token, secret, friendId.toString());
            // }

         } catch (Exception e) {
            System.out.println("Child Exception.");
         }
         System.out.println("Exiting child thread.");
      }
   }

   public static class CrawlerThreadFacebook implements Runnable {

      Thread t;
      String token;
      String id;

      CrawlerThreadFacebook(String token, String id) {

         this.token = token;
         this.id = id;
         // Create a new, second thread
         t = new Thread(this, "Facebook " + this.id + " Thread");
         log.debug("Child thread Facebook id: " + id);
         t.start(); // Start the thread
      }

      // This is the entry point for the second thread.
      @Override
      public void run() {
         try {
            getFacebookData(token, id);

         } catch (Exception e) {
            log.debug("Child Exception: " + id);
         }
         log.debug("Exiting child thread user: " + id);
      }
   }

   public static class CrawlerThreadFacebookFriends implements Runnable {

      Thread t;
      String token;
      FacebookUser user;

      CrawlerThreadFacebookFriends(String token, FacebookUser user) {

         this.token = token;
         this.user = user;
         // Create a new, second thread
         t = new Thread(this, "Facebook " + this.user.get_id() + " Thread");
         log.debug("Child thread Facebook id: " + this.user.get_id());
         t.start(); // Start the thread
      }

      // This is the entry point for the second thread.
      @Override
      public void run() {

         for (FacebookNamedObject friend : user.getFriends()) {
            log.debug("Getting friend id " + friend.get_id() + " of user " + user.get_id());
            FacebookClient fc = new DefaultFacebookClient(token);
            FacebookUtils facebook = new FacebookUtils();
            facebook.setFc(fc);
            BasicDBObject queryUser = new BasicDBObject().append("_id", friend.get_id());
            try {
               if (!MongoUtils.isFacebookIdPresent(Facade.db.getCollection("facebookUser"),
                     friend.get_id())
                     && !MongoUtils.isFacebookIdPresent(
                           Facade.db.getCollection("facebookFriend"), friend.get_id())) {

                  FacebookUser user = facebook.createFacebookUser(friend.get_id(), token);
                  facebook.saveCheckinsOfUser(friend.get_id(), null);
                  facebook.savePostsFromSource(friend.get_id(), "/feed", 150, null, null, 0);
                  Facade.db.getCollection("facebookFriend").insert(
                        Converter.toDBObject(user));

                  user.setGroups(facebook.saveGroupsOfUser(friend.get_id()));
                  Facade.db.getCollection("facebookFriend").update(queryUser,
                        Converter.toDBObject(user));
                  user.setLikes(facebook.saveLikesOfUser(friend.get_id(), null));
                  Facade.db.getCollection("facebookFriend").update(queryUser,
                        Converter.toDBObject(user));
               }
               if (MongoUtils.isFacebookIdPresent(Facade.db.getCollection("facebookUser"),
                     friend.get_id())) {
                  FacebookUser user = Converter.toObject(
                        FacebookUser.class,
                        Facade.db.getCollection("facebookUser").findOne(
                              new BasicDBObject("_id", friend.get_id())));
                  if (user.getGroups().isEmpty()) {
                     user.setGroups(facebook.saveGroupsOfUser(friend.get_id()));
                     Facade.db.getCollection("facebookUser").update(queryUser,
                           Converter.toDBObject(user));
                  }
                  if (user.getLikes().isEmpty()) {
                     user.setLikes(facebook.saveLikesOfUser(friend.get_id(), null));
                     Facade.db.getCollection("facebookUser").update(queryUser,
                           Converter.toDBObject(user));
                  }
                  facebook.saveCheckinsOfUser(friend.get_id(), null);

               }

            } catch (Exception e) {
               log.debug("Child Exception: " + user.get_id());
            }
         }

         log.debug("Exiting child thread user: " + user.get_id());
      }
   }

   public static void main(String[] args) {
      // List<FacebookUser> users = new ArrayList<FacebookUser>();
      // DBCursor cur = Facade.db.getCollection("facebookUser")
      // .find(new BasicDBObject("likes", new BasicDBObject("$ne", new BasicDBList())))
      // .sort(new BasicDBObject("_id", 1)).skip(8).limit(8);
      //
      // // DBCursor cur = Facade.db.getCollection("facebookUser").find(
      // // new BasicDBObject("_id", "1398301256"));
      // while (cur.hasNext()) {
      // users.add(Converter.toObject(FacebookUser.class, cur.next()));
      // }
      // for (FacebookUser user : users) {
      // new CrawlerThreadFacebookFriends(user.getToken(), user);
      // }

      // DBCursor cur = Facade.db.getCollection("facebookUser").find().batchSize(10)
      // .addOption(Bytes.QUERYOPTION_NOTIMEOUT);
      // List<String> ids = new ArrayList<String>();
      // while (cur.hasNext()) {
      // FacebookUser user = Converter.toObject(FacebookUser.class, cur.next());
      // log.debug("Number of user seen: " + cur.numSeen());
      // FacebookClient fc = new DefaultFacebookClient(user.getToken());
      // FacebookUtils facebook = new FacebookUtils();
      // facebook.setFc(fc);
      // for (FacebookPageHighlight f : user.getLikes()) {
      // if (!ids.contains(f.get_id())) {
      // try {
      // facebook.savePostsFromActorIdAndSourceId(f.get_id(), f.get_id());
      // } catch (InterruptedException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      // try {
      // facebook.savePostsFromActorIdAndSourceId(user.get_id(), f.get_id());
      // } catch (InterruptedException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      // ids.add(f.get_id());
      // }
      // }
      // }
      // cur.close();

      // getFacebookData(
      // "AAACEdEose0cBAGzwvNt2QZBJkYqlLZAlqpUFbcwDB5Oaycvujt3cs99s38U8n2PJsvvjWUGeEycRlNZCaIZBSU9OL3jBGFMZD",
      // "613962852");

      String json = gatherTokens();
      ObjectMapper mapper = new ObjectMapper();

      JsonNode u = null;
      try {
         u = mapper.readTree(json.toString()).get("users");
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      log.debug(u.toString());
      Iterator<String> ids = u.getFieldNames();
      while (ids.hasNext()) {
         String facebookId = ids.next();

         BasicDBObject crowdUser = new BasicDBObject();

         BasicDBObject query = new BasicDBObject("_id", facebookId);
         DBObject fUser = Facade.db.getCollection("facebookUser").findOne(query);
         crowdUser.put("facebook", fUser);
         BasicDBObject queryT = new BasicDBObject("_id", u.get(facebookId).get("twitterId")
               .asLong());
         DBObject tUser = Facade.db.getCollection("twitterUser").findOne(queryT);
         crowdUser.put("twitter", tUser);
         BasicDBObject queryL = new BasicDBObject("token", u.get(facebookId)
               .get("linkedinToken").getTextValue());
         DBObject lUser = Facade.db.getCollection("linkedinUser").findOne(queryL);
         crowdUser.put("linkedin", lUser);
         Facade.db.getCollection("users").insert(crowdUser);

      }

      // log.debug("Thread user: " + u.toString());
      // log.debug("Token: " + u.get(facebookId).get("facebookToken").getTextValue());
      // if (u.get(facebookId).get("twitterId").getTextValue() != null) {
      //
      // }
      // // new
      // //
      // CrawlerThreadFacebook(u.get(facebookId).get("facebookToken").getTextValue(),
      // // facebookId);
      //

      // CalaisClient client = new CalaisRestClient("d7mhvmntw7gcmc2a3f8c789d");
      // CalaisResponse response = null;
      //
      // try {
      // response = client
      // .analyze("Like if you think El Shaarawy deserves a chance for the senior side... if he keeps up his 1st team play for the rossoneri we very well may see him on the bench for the euro squad... with rossi out injured, and cassano still a maybe and all ?");
      // } catch (IOException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      //
      // for (CalaisObject relation : response.getRelations()) {
      // System.out.println(relation.getField("_type") + ":" + relation.getField("name"));
      // System.out.println(relation.getFieldNames());
      //
      // }
      // System.out.println();
      //
      // for (CalaisObject entity : response.getEntities()) {
      // System.out.println(entity.getField("_type") + ":" + entity.getField("name"));
      // System.out.println(entity.getFieldNames());
      //
      // }
      // System.out.println();
      // for (CalaisObject topic : response.getTopics()) {
      // System.out.println(topic.getField("categoryName"));
      // System.out.println(topic.getFieldNames());
      //
      // }
      // System.out.println();
      // for (CalaisObject tags : response.getSocialTags()) {
      // System.out.println(tags.getField("_typeGroup") + ":" + tags.getField("name"));
      // System.out.println(tags.getFieldNames());
      //
      // }

   }
}
