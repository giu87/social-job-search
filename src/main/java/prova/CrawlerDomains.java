/*
 * @(#)Crawler.java   1.0   08/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.datamodel.resources.facebook.FacebookCheckin;
import it.expertfinding.datamodel.resources.facebook.FacebookGroup;
import it.expertfinding.datamodel.resources.facebook.FacebookPage;
import it.expertfinding.datamodel.resources.facebook.FacebookPost;
import it.expertfinding.datamodel.resources.linkedin.LinkedinCompany;
import it.expertfinding.datamodel.resources.linkedin.LinkedinGroup;
import it.expertfinding.datamodel.resources.linkedin.LinkedinGroupPost;
import it.expertfinding.datamodel.resources.linkedin.LinkedinJob;
import it.expertfinding.datamodel.resources.linkedin.LinkedinUpdate;
import it.expertfinding.datamodel.resources.twitter.Tweet;
import it.expertfinding.datamodel.resources.twitter.TwitterPage;
import it.expertfinding.datamodel.users.facebook.FacebookUser;
import it.expertfinding.datamodel.users.linkedin.LinkedinUser;
import it.expertfinding.datamodel.users.twitter.TwitterUser;
import it.expertfinding.utils.Facade;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.slf4j.Logger;

public class CrawlerDomains {

   private static Logger log = Facade.log;

   private static boolean stop = false;

   private final int NUM_THREADS = 8;

   private final ExecutorService threads = Executors.newFixedThreadPool(NUM_THREADS);

   private final BlockingQueue<Runnable> resources = new ArrayBlockingQueue<Runnable>(3000,
         true);

   public static synchronized void setStop() {
      CrawlerDomains.stop = true;
   }

   /**
    * @return Returns the stop.
    */
   public static boolean isStop() {
      return CrawlerDomains.stop;
   }

   private void getResourcesFromCursor(DBCursor cursor) {

      while (cursor.hasNext()) {
         try {
            DBObject next = cursor.next();
            switch (RType.valueOf((String) next.get("rType"))) {
               case LINKEDIN_COMPANY:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        LinkedinCompany.class, next), next));
                  break;
               case LINKEDIN_JOB:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        LinkedinJob.class, next), next));
                  break;
               case LINKEDIN_GROUP:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        LinkedinGroup.class, next), next));
                  break;
               case LINKEDIN_GROUP_POST:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        LinkedinGroupPost.class, next), next));
                  break;
               case LINKEDIN_UPDATE:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        LinkedinUpdate.class, next), next));
                  break;
               case TWITTER_PAGE:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        TwitterPage.class, next), next));
                  break;
               case TWEET:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(Tweet.class,
                        next), next));
                  break;
               case FACEBOOK_PAGE:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        FacebookPage.class, next), next));
                  break;
               case FACEBOOK_GROUP:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        FacebookGroup.class, next), next));
                  break;
               case FACEBOOK_CHECKIN:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        FacebookCheckin.class, next), next));
                  break;
               case FACEBOOK_POST:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        FacebookPost.class, next), next));
                  break;
               case FACEBOOK_PROFILE:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        FacebookUser.class, next), next));
                  break;
               case TWITTER_PROFILE:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        TwitterUser.class, next), next));
               case LINKEDIN_PROFILE:
                  resources.put(new ResourceDomainProbabilities(Converter.toObject(
                        LinkedinUser.class, next), next));

            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   private void startConsuming() {

      new Thread(new Runnable() {

         @Override
         public void run() {
            for (;;) {
               try {
                  log.debug("Picking a thread to execute from queque");
                  threads.execute(resources.take());

                  if (stop) {
                     log.debug("ShuttingDown");
                     threads.shutdown();
                     System.exit(0);
                  }
                  // try {
                  // result.get();
                  // } catch (ExecutionException e) {
                  // e.printStackTrace();
                  // threads.shutdown();
                  // System.exit(0);
                  // }
               } catch (InterruptedException e) {
                  // TODO: handle exception
               }
            }
         }
      }).start();
   }

   private void start(DBCursor cursor) {

      startConsuming();
      getResourcesFromCursor(cursor);
   }

   public static void main(String[] args) throws InterruptedException {

      DB db = Facade.db;
      
      DBCursor cursor = db.getCollection(Facade.TABLE_FACEBOOK).find(new BasicDBObject("tagMeEntitiesIdList", new BasicDBObject("$ne", null)).append("domainProbabilities", null));
      System.out.println(cursor.size());
      CrawlerDomains crawler = new CrawlerDomains();
      crawler.start(cursor);
      
      // /** LINKEDIN GROUP POSTS **/
      // DBCollection lColl = db.getCollection("linkedin");
      // DBCursor cursor = lColl.find(new BasicDBObject("rType", "LINKEDIN_GROUP_POST"));
      // System.out.println(cursor.size());
      // Crawler crawler = new Crawler();
      // crawler.start(cursor);

      /** FACEBOOK GROUP & PAGE POSTS **/
//      DBCollection fColl = db.getCollection("facebook");
//      List<String> pages = fColl
//            .distinct("_id", new BasicDBObject("rType", "FACEBOOK_PAGE"));
//      Collections.sort(pages);
//      System.out.println(pages.size());
//      // List<String> sublist = pages.subList(1490, 1510);
//      // Crawler crawler = new Crawler();
//      // crawler.startConsuming();
//      int i = 0;
//      for (String id : pages) {
//         i++;
//         int percent = i * 100 / pages.size();
//         BasicDBObject query = new BasicDBObject("from._id", id);
//         DBCursor cursor = fColl.find(query).setOptions(Bytes.QUERYOPTION_NOTIMEOUT);
//         while (cursor.hasNext()) {
//            System.out.println("COMPLETED: " + percent + "%");
//            DBObject next = cursor.next();
//            FacebookPost post = Converter.toObject(FacebookPost.class, next);
//            post.setTagMeEntities((Map<String, List<String>>) next.get("tagMeEntities"));
//            post.setTagMeEntitiesId((Map<String, List<String>>) next.get("tagMeEntitiesId"));
//            post.setTagMeEntitiesSpot((Map<String, List<String>>) next
//                  .get("tagMeEntitiesSpot"));
//            post.setTagMeEntitiesList((List<String>) next.get("tagMeEntitiesList"));
//            post.setTagMeEntitiesIdList((List<String>) next.get("tagMeEntitiesIdList"));
//            post.setTagMeEntitiesSpotList((List<String>) next.get("tagMeEntitiesSpotList"));
//            post.getResourceText();
//            post.getAlchemyCategory();
//            post.getResourceLang();
//            post.getTagMeEntities();
//            try {
//               SolrManager.addBeanResource(post);
//            } catch (IOException e) {
//               log.debug("Problem with resource id", post.getSolrId());
//               log.error("Error", e);
//            } catch (SolrServerException e) {
//               log.debug("Problem with resource id", post.getSolrId());
//               log.error("Error", e);
//            } catch (Exception e) {
//               log.debug("Problem with resource id", post.getSolrId());
//               log.error("Error", e);
//            }
//         }
//         log.debug("waiting 0.5 seconds");
//         Thread.sleep(500l);
//          crawler.getResourcesFromCursor(cursor);
//      }

      // List<String> groups = fColl.distinct("_id", new BasicDBObject("rType",
      // "FACEBOOK_GROUP"));
      // for (String id : groups) {
      // Pattern p = Pattern.compile(id + "_.+");
      // BasicDBObject query = new BasicDBObject("_id", p);
      // BasicDBObject sort = new BasicDBObject("createdTime", 1);
      // DBCursor cursor = fColl.find(query).sort(sort).limit(30);
      // crawler.getResourcesFromCursor(cursor);
      // }

      /** TWITTER PAGE POSTS **/
      // DBCollection tColl = db.getCollection("twitter");
      //
      // List<Long> pages = db.getCollection("twitter").distinct("_id",
      // new BasicDBObject("rType", "TWITTER_PAGE"));
      // Crawler crawler = new Crawler();
      // crawler.startConsuming();
      // for (Long id : pages) {
      // BasicDBObject query = new BasicDBObject("user._id", id);
      // BasicDBObject sort = new BasicDBObject("createdAt", 1);
      // DBCursor cursor = tColl.find(query).sort(sort).limit(30);
      // crawler.getResourcesFromCursor(cursor);
      // }
      /** LINKEDIN PROFILES **/
      // DBCollection luColl = db.getCollection("linkedinUser");
      //
      // DBCursor cursor = luColl.find();
      // Crawler crawler = new Crawler();
      // crawler.start(cursor);
      /** TWITTER PROFILES **/
      // DBCollection tuColl = db.getCollection("twitterUser");
      // // BasicDBObject query = new BasicDBObject("_id", new BasicDBObject("$ne", null));
      // //
      // // BasicDBObject object = new BasicDBObject().append(
      // // "$set",
      // // new BasicDBObject().append("tagMeEntities", null)
      // // .append("tagMeEntitiesId", null).append("tagMeEntitiesSpot", null)
      // // .append("tagMeEntitiesList", null).append("tagMeEntitiesIdList", null)
      // // .append("tagMeEntitiesSpotList", null)
      // // .append("tagMeEntitiesIdListMongo", null).append("resourceLang", null)
      // // .append("resourceText", null).append("alchemyCategory", null)
      // // .append("alchemyCategoryScore", null));
      // // WriteResult r = tuColl.update(query, object, false, true);
      // // log.debug("ciao");
      //
      // DBCursor cursor = tuColl.find();
      //
      // log.debug("size {}", cursor.size());
      // Crawler crawler = new Crawler();
      // crawler.start(cursor);
      // /** FACEBOOK PROFILES **/
      // DBCollection fuColl = db.getCollection("facebookUser");
      //
      // BasicDBObject query = new BasicDBObject("_id", new BasicDBObject("$ne",
      // null));
      // BasicDBObject update = new BasicDBObject("$set", new BasicDBObject("channel",
      // "FACEBOOK").append("rType", "FACEBOOK_PROFILE"));
      // // // fuColl.update(query, update, false, true);
      // // // BasicDBObject query = new BasicDBObject("_id", "613962852");
      // // // fuColl.update(query,
      // // // new BasicDBObject("$set", new BasicDBObject("resourceText", null)));
      // cursor = fuColl.find();
      // // log.debug("size {}", cursor.size());
      // crawler = new Crawler();
      // crawler.start(cursor);
      /** LINKEDIN **/
      // DBCollection lColl = db.getCollection("linkedin");
      //
      // BasicDBObject query = new BasicDBObject();
      // // query.put("_id", "UNIU-1013461-5596317522044399616-SHARE");
      // query.put("rType", new BasicDBObject("$ne", "LINKEDIN_GROUP_POST"));
      // // query.put("resourceText", null);
      // DBCursor cursor = lColl.find(query);
      // log.debug("size {}", cursor.size());
      // Crawler crawler = new Crawler();
      // crawler.start(cursor);
      //
      /** FACEBOOK **/
      // DBCollection fColl = db.getCollection("facebook");
      // BasicDBObject query = new BasicDBObject();
      // // query.put("_id", "7807422276");
      // query.put("rType", new BasicDBObject("$ne", "FACEBOOK_POST"));
      // // // query.put("resourceText", new BasicDBObject("$ne", null));
      // // query.put("alchemyCategory", null);
      // // query.put("resourceLang", new BasicDBObject("$ne", "unsopported"));
      // DBCursor cursor = fColl.find(query);
      // log.debug("size {}", cursor.size());
      // Crawler crawler = new Crawler();
      // crawler.start(cursor);
      //
      /** TWITTER **/
      // DBCollection tColl = db.getCollection("twitter");
      // BasicDBObject query = new BasicDBObject();
      // // query.put("channel", "TWITTER");
      // // query.put("_id", 295L);
      // query.put("rType", "TWITTER_PAGE");
      // // query.put("tagMeEntities", new BasicDBObject("$ne", null));
      // // query.put("resourceText", new BasicDBObject("$ne", null));
      // // query.put("alchemyCategory", null);
      // // query.put("resourceLang", new BasicDBObject("$ne", "unsopported"));
      //
      // // BasicDBObject object = new BasicDBObject().append(
      // // "$set",
      // // new BasicDBObject().append("tagMeEntities", null)
      // // .append("tagMeEntitiesId", null).append("tagMeEntitiesSpot", null)
      // // .append("tagMeEntitiesList", null).append("tagMeEntitiesIdList", null)
      // // .append("tagMeEntitiesSpotList", null)
      // // .append("tagMeEntitiesIdListMongo", null));
      // // WriteResult r = tColl.update(query, object, false, true);
      // // log.debug("ciao");
      // DBCursor cursor = tColl.find(query);
      // Crawler crawler = new Crawler();
      // crawler.start(cursor);

      // List<String> users = db.getCollection("twitterUser").distinct("_id");
      // //
      // DBCollection tColl = db.getCollection("twitter");
      // BasicDBObject o = new BasicDBObject("user._id", new BasicDBObject("$in", users));
      // o.put("rType", "TWEET");
      // // o.put("entities.urls", new BasicDBObject("$ne", new BasicDBList()));
      // // o.put("resourceText", new BasicDBObject("$ne", null));
      // // o.put("alchemyCategory", new BasicDBObject("$ne", null));
      // // o.put("resourceLang", new BasicDBObject("$ne", null));
      //
      // DBCursor tweetsCursor = tColl.find(o);
      // log.debug("Cursor size {} ", tweetsCursor.size());
      // Crawler crawler = new Crawler();
      // crawler.start(tweetsCursor);

      // List<String> users = db.getCollection("facebookUser").distinct("_id");
      // List<Pattern> patterns = new ArrayList<Pattern>();
      // for (String id : users)
      // patterns.add(Pattern.compile(id + "_.+"));

      // List<String> users = db.getCollection("facebook").distinct("_id",
      // new BasicDBObject("rType", "FACEBOOK_PAGE"));
      // List<Pattern> patterns = new ArrayList<Pattern>();
      // for (String id : users)
      // patterns.add(Pattern.compile(id + "_.+"));

      // BasicDBObject o = new BasicDBObject("_id", new BasicDBObject("$in", patterns));
      // o.append("rType", "FACEBOOK_POST");
      // o.put("resourceText", new BasicDBObject("$ne", null));
      // o.append("link", new BasicDBObject("$ne", null));
      // o.append("resourceLang", new BasicDBObject("$ne", "unsopported"));
      // DBCursor postsCursor = fColl.find(o);
      // System.out.println(postsCursor.size());
      // Crawler crawler = new Crawler();
      // crawler.start(postsCursor);
      // while (postsCursor.hasNext())
      // System.out.println(Converter.toObject(FacebookPost.class, postsCursor.next())
      // .get_id());

   }
}
