package it.expertfinding.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Facade {

   // Logger
   public static Logger log = LoggerFactory.getLogger("Expertfinding");
   // Configuration
   public static Configuration conf;

   // Servlet base link
   public static final String baseLink;
   public static Set<String> domains;

   // DB Reference
   public static String dbName;
   public static Mongo mDb;
   public static DB db;
   public static final String TABLE_USER = "users";
   public static final String TABLE_STATS = "statistics";
   public static final String TABLE_FACEBOOK = "facebook";
   public static final String TABLE_LINKEDIN = "linkedin";
   public static final String TABLE_TWITTER = "twitter";
   public static final String TABLE_CATEGORIES = "categories";
   public static final String TABLE_QUERY = "queries";

   public static final String TABLE_ENTITIES_IT = "itEntities";
   public static final String TABLE_ENTITIES_EN = "enEntities";
   public static final String TABLE_ENTITIES = "entities";

   public static final String TABLE_TWITTER_USER = "twitterUser";
   public static final String TABLE_FACEBOOK_USER = "facebookUser";
   public static final String TABLE_LINKEDIN_USER = "linkedinUser";

   static {
      // Load the configuration file
      log.info("Loading configuration file");
      conf = PropertyLoader.load();

      baseLink = conf.getString("app.url");
      domains = new HashSet<String>(Arrays.asList(conf.getStringArray("domains")));
      log.debug("Base link: " + baseLink);
      try {
         String mongoUrl = conf.getString("mongo.url");
         int mongoPort = conf.getInt("mongo.port");
         String dbName = conf.getString("mongo.dbName");

         mDb = new Mongo(mongoUrl, mongoPort);
         db = mDb.getDB(dbName);
      } catch (Exception e) {
         log.error("Unable to open Mongo DB", e);
      }

      // twitter user indexes
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("twitter._id", 1));
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("twitter.following", 1));
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("twitter.followers", 1));
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("twitter.favorites", 1));

      // linkedin user indexes
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("linkedin._id", 1));
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("company.linkedinId", 1));
      db.getCollection(TABLE_USER).createIndex(
            new BasicDBObject("linkedin.positions.positionsList.company.linkedinId", 1));
      db.getCollection(TABLE_USER).createIndex(
            new BasicDBObject("linkedin.jobBookmarks.jobBookmarksList.job.id", 1));
      db.getCollection(TABLE_USER).createIndex(
            new BasicDBObject(
                  "linkedin.groupMemberships.groupMembershipsList.group.linkedinId", 1));

      // linkedin user indexes
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("facebook._id", 1));
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("facebook.groups._id", 1));
      db.getCollection(TABLE_USER).createIndex(new BasicDBObject("facebook.likes._id", 1));

      // resources
      db.getCollection(TABLE_FACEBOOK).createIndex(new BasicDBObject("to.data._id", 1));
      db.getCollection(TABLE_TWITTER).createIndex(new BasicDBObject("user._id", 1));
      db.getCollection(TABLE_LINKEDIN).createIndex(new BasicDBObject("likes.total", 1));
   }
}
