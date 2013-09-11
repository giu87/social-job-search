package it.expertfinding.utils;

import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.datamodel.Constants.ResourceUserConnection;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.datamodel.resources.facebook.FacebookCategorizedObject;
import it.expertfinding.datamodel.resources.facebook.FacebookCheckin;
import it.expertfinding.datamodel.resources.facebook.FacebookComment;
import it.expertfinding.datamodel.resources.facebook.FacebookGroup;
import it.expertfinding.datamodel.resources.facebook.FacebookPage;
import it.expertfinding.datamodel.resources.facebook.FacebookPost;
import it.expertfinding.datamodel.resources.linkedin.LinkedinComment;
import it.expertfinding.datamodel.resources.linkedin.LinkedinCompany;
import it.expertfinding.datamodel.resources.linkedin.LinkedinGroup;
import it.expertfinding.datamodel.resources.linkedin.LinkedinGroupPost;
import it.expertfinding.datamodel.resources.linkedin.LinkedinJob;
import it.expertfinding.datamodel.resources.linkedin.LinkedinLike;
import it.expertfinding.datamodel.resources.linkedin.LinkedinUpdate;
import it.expertfinding.datamodel.resources.linkedin.LinkedinUpdateComment;
import it.expertfinding.datamodel.resources.twitter.Tweet;
import it.expertfinding.datamodel.resources.twitter.TwitterPage;
import it.expertfinding.datamodel.resources.twitter.TwitterResource;
import it.expertfinding.datamodel.users.CrowdUser;
import it.expertfinding.datamodel.users.facebook.FacebookUser;
import it.expertfinding.datamodel.users.linkedin.LinkedinUser;
import it.expertfinding.datamodel.users.twitter.TwitterUser;
import it.expertfinding.utils.freebase.Entity;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.commons.configuration.Configuration;
import org.bson.types.ObjectId;
import org.slf4j.Logger;

public class MongoUtils {

   protected static Logger log = Facade.log;
   protected static Configuration conf = Facade.conf;

   private static DBCollection fColl = Facade.db.getCollection(Facade.TABLE_FACEBOOK);
   private static DBCollection tColl = Facade.db.getCollection(Facade.TABLE_TWITTER);
   private static DBCollection lColl = Facade.db.getCollection(Facade.TABLE_LINKEDIN);

   private static DBCollection tuColl = Facade.db.getCollection(Facade.TABLE_TWITTER_USER);
   private static DBCollection luColl = Facade.db.getCollection(Facade.TABLE_LINKEDIN_USER);
   private static DBCollection fuColl = Facade.db.getCollection(Facade.TABLE_FACEBOOK_USER);

   private static DBCollection uColl = Facade.db.getCollection(Facade.TABLE_USER);

   private static DBCollection entityColl = Facade.db.getCollection(Facade.TABLE_ENTITIES);

   public static String encryptPassword(String clear) {
      String password = clear;
      String algorithm = "SHA";

      byte[] plainText = password.getBytes();
      MessageDigest md = null;

      try {
         md = MessageDigest.getInstance(algorithm);
      } catch (Exception e) {
         log.error("ERROR ENCRYPTING: " + e);
         e.printStackTrace();
      }

      md.reset();
      md.update(plainText);
      byte[] encodedPassword = md.digest();

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < encodedPassword.length; i++) {
         if ((encodedPassword[i] & 0xff) < 0x10) {
            sb.append("0");
         }
         sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
      }

      return sb.toString();
   }

   /**
    * @param uColl
    * @return
    * @throws MongoException
    */
   public static CrowdUser getUserByID(DBCollection uColl, String uID) throws MongoException {
      BasicDBObject findUserQuery = new BasicDBObject();
      findUserQuery.put("_id", new ObjectId(uID));
      DBObject findUser = uColl.findOne(findUserQuery);
      CrowdUser newU = null;
      if (findUser != null) {
         newU = Converter.toObject(CrowdUser.class, findUser);
      }
      return newU;
   }

   public static CrowdUser getUserUserName(DBCollection uColl, String uName)
         throws MongoException {
      BasicDBObject findUserQuery = new BasicDBObject();
      findUserQuery.put("username", uName);
      DBObject findUser = uColl.findOne(findUserQuery);
      CrowdUser newU = null;
      if (findUser != null) {
         newU = Converter.toObject(CrowdUser.class, findUser);
      }
      return newU;
   }

   /**
    * @param uColl
    * @param user
    * @throws MongoException
    */
   public static void saveUserData(DBCollection uColl, CrowdUser user) throws MongoException {
      DBObject dbObject = Converter.toDBObject(user);
      uColl.insert(dbObject);
   }

   /**
    * @param uColl
    * @param cUser
    * @throws MongoException
    */
   // public static void updateUserData(DBCollection uColl, CrowdUser cUser)
   // throws MongoException {
   //
   // log.debug("UPDATING USER DATA: " + cUser.get_id());
   // DBObject removeByID = new BasicDBObject("_id", new
   // ObjectId(cUser.get_id().toString()));
   // DBObject findUser = uColl.findOne(removeByID);
   // if (findUser != null) {
   // uColl.remove(removeByID);
   // DBObject dbObject = Converter.toDBObject(cUser);
   // uColl.insert(dbObject);
   // return;
   // }
   // throw new MongoException("Trying to delete a non existing user");
   // }
   //
   // /**
   // * @param uColl
   // * @return
   // * @throws MongoException
   // */
   // public static DBObject getUserByFacebookID(DBCollection uColl, String facebookID)
   // throws MongoException {
   // BasicDBObject findUserQuery = new BasicDBObject();
   // findUserQuery.put("facebook.facebookID", facebookID);
   // DBObject findUser = uColl.findOne(findUserQuery);
   // return findUser;
   // }
   //
   // // public static ArrayList<CrowdUser> getFacebookFriendsOfUser(DBCollection uColl,
   // // CrowdUser user) throws MongoException {
   // // ArrayList<CrowdUser> retFriends = new ArrayList<CrowdUser>();
   // // List<String> friends = user.getFacebook().getFacebookUser().getFriends();
   // // log.debug("User " + user.getUsername() + " has " + friends.size() + "  friends");
   // // log.debug("Friends: " + friends.toString());
   // // for (String friend : friends) {
   // // DBObject fr = getUserByFacebookID(uColl, friend);
   // // if (fr != null) {
   // // CrowdUser newU = Converter.toObject(CrowdUser.class, fr);
   // // retFriends.add(newU);
   // // }
   // // }
   // // return retFriends;
   // // }

   // public static ArrayList<CrowdUser> getFacebookFriendsOfUser(DBCollection uColl,
   // List<String> friends) throws MongoException {
   // ArrayList<CrowdUser> retFriends = new ArrayList<CrowdUser>();
   // log.debug("Friends: " + friends.toString());
   // for (String friend : friends) {
   // DBObject fr = getUserByFacebookID(uColl, friend);
   // if (fr != null) {
   // CrowdUser newU = Converter.toObject(CrowdUser.class, fr);
   // retFriends.add(newU);
   // }
   // }
   // return retFriends;
   // }

   // public static List<String> checkMissingFacebookUsers(DBCollection uColl,
   // List<String> facebookIDs) throws MongoException {
   // List<String> missing = new ArrayList<String>();
   // for (String id : facebookIDs) {
   // DBObject foundUser = MongoUtils.getUserByFacebookID(uColl, id);
   // if (foundUser == null) {
   // log.debug("\tUser was not in the DB: " + id);
   // missing.add(id);
   // }
   // }
   // return missing;
   //
   // }

   // /**
   // * @param uColl
   // * @param toFetch
   // * @throws MongoException
   // * @throws Exception
   // */
   // public static void addMissingFacebookUsers(FacebookClient fc, DBCollection uColl,
   // List<String> toFetch) throws MongoException, Exception {
   // // Check missing users
   // List<String> toGetFromFB = MongoUtils.checkMissingFacebookUsers(uColl, toFetch);
   // // Fetch data for users that are new to the platform
   // List<FacebookData> newUsers = FacebookUtils.getUsersData(fc, toGetFromFB);
   // // Insert new user for each response
   // for (FacebookData newu : newUsers) {
   // // log.debug("Adding: " + newu.getFacebookID());
   // CrowdUser userToAdd = new CrowdUser();
   // userToAdd.setFacebook(newu);
   // userToAdd.setCreationDate(11L);
   // MongoUtils.saveUserData(uColl, userToAdd);
   // }
   // }
   //
   // public static List<CrowdUser> getCrowdFapSubscribers() {
   //
   // DBCollection uColl = Facade.db.getCollection(Facade.TABLE_USER);
   // DBCursor cur = uColl.find();
   //
   // List<CrowdUser> users = new ArrayList<CrowdUser>();
   //
   // BasicDBObject findUsersQuery = new BasicDBObject();
   // findUsersQuery.put("facebook.authToken", new BasicDBObject("$ne", null));
   //
   // cur = uColl.find(findUsersQuery);
   //
   // while (cur.hasNext()) {
   //
   // DBObject user = cur.next();
   // CrowdUser crowdUser = Converter.toObject(CrowdUser.class, user);
   //
   // users.add(crowdUser);
   // }
   // return users;
   // }

   // ********************************************
   // DOCUMENT MANAGEMENT
   // ********************************************

   public static void saveDocumentData(DBCollection dColl, AbstractResource d)
         throws MongoException {
      DBObject dbObject = Converter.toDBObject(d);
      dColl.insert(dbObject);
   }

   // @SuppressWarnings("unchecked")
   // public static List<FacebookResource> getDocumentsByFacebookID(DBCollection dColl,
   // List<String> facebook_ids) {
   // BasicDBObject query = new BasicDBObject();
   // query.put("facebook_id", new BasicDBObject("$in", facebook_ids));
   // DBCursor cur = dColl.find(query);
   // ArrayList<FacebookResource> toReturn = new ArrayList<FacebookResource>();
   // while (cur.hasNext()) {
   // DBObject findDocument = cur.next();
   // FacebookResource newD = null;
   //
   // RType type = RType.valueOf((String) findDocument.get("type"));
   // switch (type) {
   // case FACEBOOK_PAGE:
   // FacebookPage newPage = Converter.toObject(FacebookPage.class, findDocument);
   // DBObject others = (DBObject) findDocument.get("others");
   // HashMap<String, String> othersMap = new HashMap<String, String>();
   // for (String key : others.keySet()) {
   // othersMap.put(key, (String) others.get(key));
   // }
   // newPage.setOthers(othersMap);
   // newPage.setLinks((ArrayList<String>) findDocument.get("links"));
   // BasicDBList pDiscussions = (BasicDBList) findDocument.get("discussions");
   // if (pDiscussions != null) {
   // for (Object discussion : pDiscussions) {
   // DBObject dbDiscussion = (DBObject) discussion;
   // FacebookDiscussion jDiscussion = Converter.toObject(
   // FacebookDiscussion.class, dbDiscussion);
   // jDiscussion.setLinks((ArrayList<String>) dbDiscussion.get("links"));
   // jDiscussion.setComments((ArrayList<String>) dbDiscussion
   // .get("comments"));
   // newPage.getDiscussions().add(jDiscussion);
   // }
   // }
   //
   // newD = newPage;
   // break;
   // case FACEBOOK_GROUP:
   // FacebookGroup newGroup = Converter
   // .toObject(FacebookGroup.class, findDocument);
   // newGroup.setLinks((ArrayList<String>) findDocument.get("links"));
   // BasicDBList gDiscussions = (BasicDBList) findDocument.get("discussions");
   // if (gDiscussions != null) {
   // for (Object discussion : gDiscussions) {
   // DBObject dbDiscussion = (DBObject) discussion;
   // FacebookDiscussion jDiscussion = Converter.toObject(
   // FacebookDiscussion.class, dbDiscussion);
   // jDiscussion.setLinks((ArrayList<String>) dbDiscussion.get("links"));
   // jDiscussion.setComments((ArrayList<String>) dbDiscussion
   // .get("comments"));
   // newGroup.getDiscussions().add(jDiscussion);
   // }
   // }
   // newD = newGroup;
   //
   // break;
   // case FACEBOOK_DISCUSSION:
   // FacebookDiscussion newDiscussion = Converter.toObject(
   // FacebookDiscussion.class, findDocument);
   // newDiscussion.setLinks((ArrayList<String>) findDocument.get("links"));
   // newDiscussion.setComments((ArrayList<String>) findDocument.get("comments"));
   // newD = newDiscussion;
   //
   // break;
   //
   // default:
   // newD = null;
   //
   // }
   // toReturn.add(newD);
   // }
   // return toReturn;
   // }

   // @SuppressWarnings("unchecked")
   // public static List<FacebookPage> getPagesByCategoryAndFacebookID(DBCollection dColl,
   // List<String> facebook_ids, String category) {
   //
   // DBObject query = BasicDBObjectBuilder
   // .start("facebook_id", new BasicDBObject("$in", facebook_ids))
   // .add("category", category).get();
   // DBCursor cur = dColl.find(query);
   // ArrayList<FacebookPage> toReturn = new ArrayList<FacebookPage>();
   // while (cur.hasNext()) {
   // DBObject findDocument = cur.next();
   // FacebookPage newD = null;
   //
   // RType type = RType.valueOf((String) findDocument.get("type"));
   // if (type == Constants.RType.FACEBOOK_PAGE) {
   // FacebookPage newPage = Converter.toObject(FacebookPage.class, findDocument);
   // DBObject others = (DBObject) findDocument.get("others");
   // HashMap<String, String> othersMap = new HashMap<String, String>();
   // for (String key : others.keySet()) {
   // othersMap.put(key, (String) others.get(key));
   // }
   // newPage.setOthers(othersMap);
   // newPage.setLinks((ArrayList<String>) findDocument.get("links"));
   // BasicDBList pDiscussions = (BasicDBList) findDocument.get("discussions");
   // if (pDiscussions != null) {
   // for (Object discussion : pDiscussions) {
   // DBObject dbDiscussion = (DBObject) discussion;
   // FacebookDiscussion jDiscussion = Converter.toObject(
   // FacebookDiscussion.class, dbDiscussion);
   // jDiscussion.setLinks((ArrayList<String>) dbDiscussion.get("links"));
   // jDiscussion.setComments((ArrayList<String>) dbDiscussion.get("comments"));
   // newPage.getDiscussions().add(jDiscussion);
   // }
   // }
   //
   // newD = newPage;
   // } else
   // newD = null;
   //
   // toReturn.add(newD);
   // }
   // return toReturn;
   // }
   //
   // @SuppressWarnings("unchecked")
   // public static FacebookResource getResourceByFacebookID(DBCollection dColl,
   // String facebook_id) {
   //
   // BasicDBObject findDocQuery = new BasicDBObject();
   // findDocQuery.put("_id", facebook_id);
   // DBObject findDocument = dColl.findOne(findDocQuery);
   // FacebookResource newD = null;
   //
   // if (findDocument != null) {
   // RType type = RType.valueOf((String) findDocument.get("type"));
   // switch (type) {
   // case FACEBOOK_PAGE:
   // FacebookPage newPage = Converter.toObject(FacebookPage.class, findDocument);
   // DBObject others = (DBObject) findDocument.get("others");
   // HashMap<String, String> othersMap = new HashMap<String, String>();
   // for (String key : others.keySet()) {
   // othersMap.put(key, (String) others.get(key));
   // }
   // newPage.setOthers(othersMap);
   // newPage.setLinks((ArrayList<String>) findDocument.get("links"));
   // BasicDBList pDiscussions = (BasicDBList) findDocument.get("discussions");
   // if (pDiscussions != null) {
   // for (Object discussion : pDiscussions) {
   // DBObject dbDiscussion = (DBObject) discussion;
   // FacebookDiscussion jDiscussion = Converter.toObject(
   // FacebookDiscussion.class, dbDiscussion);
   // jDiscussion.setLinks((ArrayList<String>) dbDiscussion.get("links"));
   // jDiscussion.setComments((ArrayList<String>) dbDiscussion
   // .get("comments"));
   // newPage.getDiscussions().add(jDiscussion);
   // }
   // }
   //
   // newD = newPage;
   // break;
   // case FACEBOOK_GROUP:
   // FacebookGroup newGroup = Converter
   // .toObject(FacebookGroup.class, findDocument);
   // newGroup.setLinks((ArrayList<String>) findDocument.get("links"));
   // BasicDBList gDiscussions = (BasicDBList) findDocument.get("discussions");
   // if (gDiscussions != null) {
   // for (Object discussion : gDiscussions) {
   // DBObject dbDiscussion = (DBObject) discussion;
   // FacebookDiscussion jDiscussion = Converter.toObject(
   // FacebookDiscussion.class, dbDiscussion);
   // jDiscussion.setLinks((ArrayList<String>) dbDiscussion.get("links"));
   // jDiscussion.setComments((ArrayList<String>) dbDiscussion
   // .get("comments"));
   // newGroup.getDiscussions().add(jDiscussion);
   // }
   // }
   // newD = newGroup;
   //
   // break;
   // case FACEBOOK_DISCUSSION:
   // FacebookDiscussion newDiscussion = Converter.toObject(
   // FacebookDiscussion.class, findDocument);
   // newDiscussion.setLinks((ArrayList<String>) findDocument.get("links"));
   // newDiscussion.setComments((ArrayList<String>) findDocument.get("comments"));
   // newD = newDiscussion;
   //
   // break;
   //
   // default:
   // newD = null;
   //
   // }
   //
   // }
   // return newD;
   //
   // }
   //
   // public static FacebookGroup checkGroupByFacebookID(DBCollection fColl, String
   // facebook_id) {
   // BasicDBObject findGroupQuery = new BasicDBObject();
   // findGroupQuery.put("_id", facebook_id);
   // DBObject findDocument = fColl.findOne(findGroupQuery);
   // FacebookGroup newGroup = null;
   // if (findDocument != null) {
   // newGroup = Converter.toObject(FacebookGroup.class, findDocument);
   // }
   // return newGroup;
   //
   // }
   //
   // public static FacebookPage checkPageByFacebookID(DBCollection fColl, String
   // facebook_id) {
   // BasicDBObject findGroupQuery = new BasicDBObject();
   // findGroupQuery.put("_id", facebook_id);
   // DBObject findDocument = fColl.findOne(findGroupQuery);
   // FacebookPage newPage = null;
   // if (findDocument != null) {
   // newPage = Converter.toObject(FacebookPage.class, findDocument);
   // }
   // return newPage;
   //
   // }
   //
   // public static String getAccessToken(DBCollection uColl, CrowdUser user) {
   //
   // if (user.getFacebook().getAuthToken() == null) {
   // uColl.find();
   // DBObject query = BasicDBObjectBuilder
   // .start("facebook.authToken", new BasicDBObject("$ne", null))
   // .add("facebook.facebookUser.friends", user.getFacebook().getFacebookID())
   // .get();
   // DBObject found = uColl.findOne(query);
   // if (found != null) {
   // CrowdUser friendWithToken = Converter.toObject(CrowdUser.class, found);
   // return friendWithToken.getFacebook().getAuthToken();
   // } else {
   // return null;
   // }
   // } else {
   // return user.getFacebook().getAuthToken();
   // }
   // }

   // public static List<String> getMicroCategoriesFromMacroCategory(DBCollection cColl,
   // String macroCategory) {
   //
   // BasicDBObject query = new BasicDBObject();
   // query.put("macroCategories", macroCategory);
   // DBCursor cur = cColl.find(query);
   // List<String> toReturn = new ArrayList<String>();
   // while (cur.hasNext()) {
   // try {
   // toReturn.add((String) cur.next().get("name"));
   // } catch (Exception e) {
   // log.debug("Error: fail to get categories");
   // }
   // }
   // return toReturn;
   // }

   // public static LinkedinCompany getLinkedinCompanyByLinkedinId(DBCollection uColl, String
   // id)
   // throws MongoException {
   //
   // BasicDBObject findCompanyQuery = new BasicDBObject();
   // findCompanyQuery.put("id", id);
   // DBObject findCompany = uColl.findOne(findCompanyQuery);
   // LinkedinCompany newC = null;
   // if (findCompany != null) {
   // newC = Converter.toObject(LinkedinCompany.class, findCompany);
   // }
   // return newC;
   // }

   // New

   public static void insertObjectsData(DBCollection coll, List<DBObject> objects)
         throws MongoException {
      coll.insert(objects);
   }

   public static boolean isFacebookIdPresent(DBCollection fColl, String facebookId) {
      BasicDBObject query = new BasicDBObject();
      query.put("_id", facebookId);
      BasicDBObject field = new BasicDBObject();
      field.put("_id", 1);
      return fColl.findOne(query, field) != null ? true : false;
   }

   public static boolean isPostPresent(DBCollection fColl, FacebookPost post)
         throws NullPointerException {

      BasicDBObject query = new BasicDBObject("from._id", post.getFrom().get_id())
            .append("message", post.getMessage())
            .append("createdTime", post.getCreatedTime())
            .append("updatedTime", post.getUpdatedTime());

      BasicDBList data = new BasicDBList();
      try {
         for (FacebookCategorizedObject to : post.getTo().getData()) {
            if (to != null)
               data.add(to.get_id());
         }
      } catch (NullPointerException e) {
         throw new NullPointerException();
      }

      query.append("to.data._id", new BasicDBObject("$all", data));
      BasicDBObject field = new BasicDBObject();
      field.put("_id", 1);
      return fColl.findOne(query) != null ? true : false;

   }

   public static DBObject isTwitterPageIdPresent(DBCollection tColl, Long twitterId) {
      BasicDBObject query = new BasicDBObject();
      query.put("_id", twitterId);
      BasicDBObject field = new BasicDBObject();
      field.put("sinceIdRecovered", 1);
      field.put("maxIdRecovered", 1);
      DBObject page = tColl.findOne(query);
      if (page != null) {
         return page;
      }
      return null;
   }

   public static void updateSinceIdData(DBCollection tColl, Long twitterId, Long sinceId)
         throws MongoException {
      BasicDBObject update = new BasicDBObject().append("$set",
            new BasicDBObject().append("sinceIdRecovered", sinceId));
      tColl.update(new BasicDBObject().append("_id", twitterId), update);
   }

   public static void updateMaxIdData(DBCollection tColl, Long twitterId, Long maxId)
         throws MongoException {
      BasicDBObject update = new BasicDBObject().append("$set",
            new BasicDBObject().append("maxIdRecovered", maxId));
      tColl.update(new BasicDBObject().append("_id", twitterId), update);
   }

   public static void updateTwitterData(Long twitterId, TwitterResource resource)
         throws MongoException {
      tColl.update(new BasicDBObject().append("_id", twitterId),
            Converter.toDBObject(resource));
   }

   @Deprecated
   public static void updateTwitterResourceText(Long twitterId, String resourceText) {

      tColl.update(
            new BasicDBObject("_id", twitterId),
            new BasicDBObject().append("$set",
                  new BasicDBObject().append("resourceText", resourceText)));

   }

   @Deprecated
   public static void updateFacebookResourceText(String facebookId, String resourceText) {

      fColl.update(
            new BasicDBObject("_id", facebookId),
            new BasicDBObject().append("$set",
                  new BasicDBObject().append("resourceText", resourceText)));

   }

   public static void updateResourceText(Channel channel, Object id, String resourceText) {
      updateResourceText(channel, id, resourceText, null);
   }

   public static void updateResourceText(Channel channel, Object id, String resourceText,
         RType rType) {
      BasicDBObject query = new BasicDBObject();
      DBCollection coll;
      if (rType != null)
         coll = getCollectionByResourceType(rType);
      else
         coll = getCollectionByChannel(channel);
      query.append("_id", id);

      BasicDBObject object = new BasicDBObject().append("$set",
            new BasicDBObject().append("resourceText", resourceText));
      if (coll != null)
         coll.update(query, object);
      else {
         log.error("unable to get right collection for the update");
      }
   }

   public static void updateResourceTextShort(Channel channel, Object id,
         String resourceTextShort) {
      BasicDBObject query = new BasicDBObject();
      DBCollection coll = getCollectionByChannel(channel);
      query.append("_id", id);

      BasicDBObject object = new BasicDBObject().append("$set",
            new BasicDBObject().append("resourceTextShort", resourceTextShort));
      if (coll != null)
         coll.update(query, object);
      else {
         log.error("unable to get right collection for the update");
      }
   }

   public static void updateDomainProbability(RType rType,
         Map<String, Double> domainProbabilities, Object id) {

      BasicDBObject object = new BasicDBObject().append("$set",
            new BasicDBObject().append("domainProbabilities", domainProbabilities));

      DBCollection coll = getCollectionByResourceType(rType);
      if (coll != null)
         coll.update(new BasicDBObject("_id", id), object);
      else {
         log.error("unable to get right collection for the update");
      }
   }

   public static void updateTagMeEntities(RType rType, Object id,
         Map<String, List<String>> entities, Map<String, List<String>> entitiesId,
         Map<String, List<String>> entitiesSpot, List<String> entityList,
         List<String> entityIdList, List<String> entitiesSpotList,
         List<DBObject> entityIdListMongo) {
      BasicDBObject query = new BasicDBObject();
      DBCollection coll = getCollectionByResourceType(rType);
      query.append("_id", id);

      BasicDBObject object = new BasicDBObject().append(
            "$set",
            new BasicDBObject().append("tagMeEntities", entities)
                  .append("tagMeEntitiesId", entitiesId)
                  .append("tagMeEntitiesSpot", entitiesSpot)
                  .append("tagMeEntitiesList", entityList)
                  .append("tagMeEntitiesIdList", entityIdList)
                  .append("tagMeEntitiesSpotList", entitiesSpotList)
                  .append("tagMeEntitiesIdListMongo", entityIdListMongo));
      if (coll != null)
         coll.update(query, object);
      else {
         log.error("unable to get right collection for the update");
      }
   }

   public static void updateResourceLang(RType rType, Object id, String resourceLang) {
      BasicDBObject query = new BasicDBObject();
      DBCollection coll = getCollectionByResourceType(rType);
      query.append("_id", id);

      BasicDBObject object = new BasicDBObject().append("$set",
            new BasicDBObject().append("resourceLang", resourceLang));
      if (coll != null)
         coll.update(query, object);
      else {
         log.error("unable to get right collection for the update");
      }
   }

   public static void updateResourceCategory(RType rType, Object id, String category,
         Double score) {
      BasicDBObject query = new BasicDBObject();
      DBCollection coll = getCollectionByResourceType(rType);

      query.append("_id", id);

      BasicDBObject object = new BasicDBObject();
      object.append(
            "$set",
            new BasicDBObject().append("alchemyCategory", category).append(
                  "alchemyCategoryScore", score));
      if (coll != null)
         coll.update(query, object);
      else {
         log.error("unable to get right collection for the update");
      }

   }

   public static List<FacebookPost> getFacebookPostsFromActorId(String facebookId) {
      BasicDBObject query = new BasicDBObject("from._id", facebookId);
      DBCursor cursor = fColl.find(query);
      List<FacebookPost> posts = new ArrayList<FacebookPost>();
      while (cursor.hasNext()) {
         posts.add(Converter.toObject(FacebookPost.class, cursor.next()));
      }
      return posts;
   }

   public static List<FacebookPost> getFacebookPostsFromSourceId(String facebookId, int limit) {

      Pattern p = Pattern.compile(facebookId + "_.+");
      BasicDBObject query = new BasicDBObject("_id", p);
      BasicDBObject sort = new BasicDBObject("createdTime", 1);
      DBCursor cursor = null;
      if (limit != -1)
         cursor = fColl.find(query).sort(sort).limit(limit);
      else
         cursor = fColl.find(query).sort(sort);

      List<FacebookPost> posts = new ArrayList<FacebookPost>();
      while (cursor.hasNext()) {
         posts.add(Converter.toObject(FacebookPost.class, cursor.next()));
      }
      return posts;
   }

   public static List<Tweet> getTweetsFromActorId(Long twitterId, int limit) {
      BasicDBObject query = new BasicDBObject("user._id", twitterId);
      BasicDBObject sort = new BasicDBObject("createdAt", 1);
      DBCursor cursor = null;
      if (limit != -1)
         cursor = tColl.find(query).sort(sort).limit(limit);
      else
         cursor = tColl.find(query).sort(sort);
      List<Tweet> tweets = new ArrayList<Tweet>();
      while (cursor.hasNext()) {
         tweets.add(Converter.toObject(Tweet.class, cursor.next()));
      }
      return tweets;
   }

   public static List<LinkedinGroupPost> getLinkedinGroupPostsFromGroupId(String groupId) {

      BasicDBObject query = new BasicDBObject("groupId", groupId);
      DBCursor cursor = lColl.find(query);
      List<LinkedinGroupPost> posts = new ArrayList<LinkedinGroupPost>();
      while (cursor.hasNext()) {
         posts.add(Converter.toObject(LinkedinGroupPost.class, cursor.next()));
      }
      return posts;
   }

   @SuppressWarnings("unchecked")
   public static AbstractResource getResourceById(String id, RType type) {

      BasicDBObject query = new BasicDBObject();
      if (RType.TWEET.equals(type) || RType.TWITTER_PAGE.equals(type)
            || RType.TWITTER_PROFILE.equals(type))
         query.put("_id", Long.parseLong(id));
      else
         query.put("_id", id);

      DBObject resourceOb = getCollectionByResourceType(type).findOne(query);
      AbstractResource resource = Converter.toObject(getClassByResourceType(type),
            resourceOb);
      resource.setTagMeEntities((Map<String, List<String>>) resourceOb.get("tagMeEntities"));
      resource.setTagMeEntitiesId((Map<String, List<String>>) resourceOb
            .get("tagMeEntitiesId"));
      resource.setTagMeEntitiesSpot((Map<String, List<String>>) resourceOb
            .get("tagMeEntitiesSpot"));
      resource.setTagMeEntitiesList((List<String>) resourceOb.get("tagMeEntitiesList"));
      resource.setTagMeEntitiesIdList((List<String>) resourceOb.get("tagMeEntitiesIdList"));
      resource.setTagMeEntitiesSpotList((List<String>) resourceOb
            .get("tagMeEntitiesSpotList"));
      // resource.setTagMeEntitiesIdListMongo((List<DBObject>) resourceOb
      // .get("tagMeEntitiesIdListMongo"));
      return resource;

   }

   public static DBCollection getCollectionByResourceType(RType type) {
      switch (type) {
         case LINKEDIN_COMPANY:
         case LINKEDIN_JOB:
         case LINKEDIN_GROUP:
         case LINKEDIN_GROUP_POST:
         case LINKEDIN_UPDATE:
            return lColl;

         case TWITTER_PAGE:
         case TWEET:
            return tColl;

         case FACEBOOK_PAGE:
         case FACEBOOK_GROUP:
         case FACEBOOK_CHECKIN:
         case FACEBOOK_POST:
            return fColl;
         case FACEBOOK_PROFILE:
            return fuColl;
         case TWITTER_PROFILE:
            return tuColl;
         case LINKEDIN_PROFILE:
            return luColl;
         default:
            return null;
      }
   }

   public static DBCollection getCollectionByChannel(Channel channel) {
      switch (channel) {
         case LINKEDIN:
            return lColl;
         case TWITTER:
            return tColl;
         case FACEBOOK:
            return fColl;
         default:
            return null;
      }
   }

   public static Class< ? extends AbstractResource> getClassByResourceType(RType type) {
      switch (type) {
         case LINKEDIN_COMPANY:
            return LinkedinCompany.class;
         case LINKEDIN_JOB:
            return LinkedinJob.class;
         case LINKEDIN_GROUP:
            return LinkedinGroup.class;
         case LINKEDIN_GROUP_POST:
            return LinkedinGroupPost.class;
         case LINKEDIN_UPDATE:
            return LinkedinUpdate.class;
         case TWITTER_PAGE:
            return TwitterPage.class;
         case TWEET:
            return Tweet.class;
         case FACEBOOK_PAGE:
            return FacebookPage.class;
         case FACEBOOK_GROUP:
            return FacebookGroup.class;
         case FACEBOOK_CHECKIN:
            return FacebookCheckin.class;
         case FACEBOOK_POST:
            return FacebookPost.class;
         case FACEBOOK_PROFILE:
            return FacebookUser.class;
         case LINKEDIN_PROFILE:
            return LinkedinUser.class;
         case TWITTER_PROFILE:
            return TwitterUser.class;
         default:
            return null;
      }
   }

   public static Entity getFreebaseEntityById(ObjectId id) {
      BasicDBObject query = new BasicDBObject().append("_id", id);
      DBObject result = entityColl.findOne(query);
      return (result != null) ? Converter.toObject(Entity.class, result) : null;

   }

   public static Entity getFreebaseEntity(String wikiId, String lang) {

      BasicDBObject query = new BasicDBObject().append(lang + "_id", wikiId);
      DBObject result = entityColl.findOne(query);
      return (result != null) ? Converter.toObject(Entity.class, result) : null;

   }

   public static Entity getFreebaseEntityByTitle(String wikiTitle, String lang) {

      BasicDBObject query = new BasicDBObject().append(lang + "_title", wikiTitle);
      DBObject result = entityColl.findOne(query);
      return (result != null) ? Converter.toObject(Entity.class, result) : null;

   }

   public static void saveFreebaseEntity(Entity entity) {
      entityColl.insert(Converter.toDBObject(entity));
   }

   public static Map<CrowdUser, List<ResourceUserConnection>> getCrowdUsersByResource(
         AbstractResource resource, String queryType) {

      Map<CrowdUser, List<ResourceUserConnection>> users = new HashMap<CrowdUser, List<ResourceUserConnection>>();
      List<ResourceUserConnection> connections = new ArrayList<ResourceUserConnection>();
      DBCursor c, c2;
      DBObject user;
      CrowdUser u = null;
      List<String> usersId = new ArrayList<String>();

      switch (resource.getrType()) {

         case TWEET:
            // own tweet
            connections.add(ResourceUserConnection.TWEET);
            try {
               users.put(Converter.toObject(CrowdUser.class, uColl
                     .findOne(new BasicDBObject("twitter._id", ((Tweet) resource).getUser()
                           .get_id()))), connections);
            } catch (Exception e) {
               log.debug("Tweet of a page/undefined");
            }

            if (queryType.equals("textShort")) {
               // favorite tweet
               c = uColl.find(new BasicDBObject("twitter.favorites", ((Tweet) resource)
                     .get_id()));

               while (c.hasNext()) {
                  u = Converter.toObject(CrowdUser.class, c.next());
                  if (users.containsKey(u))
                     users.get(u).add(ResourceUserConnection.TWEET_FAVORITE);
                  else {
                     connections = new ArrayList<ResourceUserConnection>();
                     connections.add(ResourceUserConnection.TWEET_FAVORITE);
                     users.put(u, connections);
                  }
               }
            }
            // tweet of a following page
            c = uColl.find(new BasicDBObject("twitter.following", ((Tweet) resource)
                  .getUser().get_id()).append("twitter.followers", new BasicDBObject("$ne",
                  ((Tweet) resource).getUser().get_id())));

            while (c.hasNext()) {
               u = Converter.toObject(CrowdUser.class, c.next());
               if (users.containsKey(u))
                  users.get(u).add(ResourceUserConnection.TWEET_OF_PAGE);
               else {
                  connections = new ArrayList<ResourceUserConnection>();
                  connections.add(ResourceUserConnection.TWEET_OF_PAGE);
                  users.put(u, connections);
               }
            }
            break;

         case TWITTER_PAGE:
            c = uColl.find(new BasicDBObject("twitter.following", ((TwitterPage) resource)
                  .get_id()).append("twitter.followers", new BasicDBObject("$ne",
                  ((TwitterPage) resource).get_id())));
            connections.add(ResourceUserConnection.TWITTER_PAGE_FOLLOWING);
            while (c.hasNext())
               users.put(Converter.toObject(CrowdUser.class, c.next()), connections);
            break;

         case LINKEDIN_UPDATE:

            connections.add(ResourceUserConnection.LINKEDIN_UPDATE);
            users.put(Converter.toObject(CrowdUser.class, uColl.findOne(new BasicDBObject(
                  "linkedin._id", ((LinkedinUpdate) resource).getContent().getAuthor()
                        .getId()))), connections);
            
            //LIKERS
            usersId = new ArrayList<String>();
            if (((LinkedinUpdate) resource).getLikes() != null)
               for ( LinkedinLike id : ((LinkedinUpdate) resource).getLikes().getLikesList())
                  usersId.add(id.getPerson().getLinkedinId());
            c = uColl.find(new BasicDBObject("linkedin._id", new BasicDBObject("$in",
                  usersId)));
            
            while (c.hasNext()) {

               u = Converter.toObject(CrowdUser.class, c.next());
               if (!users.containsKey(u)) {
                  connections = new ArrayList<ResourceUserConnection>();
                  connections.add(ResourceUserConnection.LINKEDIN_UPDATE_LIKER);
                  users.put(u, connections);
               } else
                  users.get(u).add(ResourceUserConnection.LINKEDIN_UPDATE_LIKER);
            }
            
            //people commented the update
            
            usersId = new ArrayList<String>();
            if (((LinkedinUpdate) resource).getUpdateComments() != null)
               for ( LinkedinUpdateComment id : ((LinkedinUpdate) resource).getUpdateComments().getCommentsList())
                  usersId.add(id.getPerson().getLinkedinId());
            c = uColl.find(new BasicDBObject("linkedin._id", new BasicDBObject("$in",
                  usersId)));
            
            while (c.hasNext()) {

               u = Converter.toObject(CrowdUser.class, c.next());
               if (!users.containsKey(u)) {
                  connections = new ArrayList<ResourceUserConnection>();
                  connections.add(ResourceUserConnection.LINKEDIN_UPDATE_PARTECIPANT);
                  users.put(u, connections);
               } else
                  users.get(u).add(ResourceUserConnection.LINKEDIN_UPDATE_PARTECIPANT);
            }

            break;

         case LINKEDIN_COMPANY:
            // user have/had a position in company
            c = uColl.find(new BasicDBObject(
                  "linkedin.positions.positionsList.company.linkedinId",
                  ((LinkedinCompany) resource).get_id()));
            connections.add(ResourceUserConnection.LINKEDIN_COMPANY_POSITION);
            while (c.hasNext())
               users.put(Converter.toObject(CrowdUser.class, c.next()), connections);

            // user bookmarks contain a job of the company
            DBCursor cJobs = lColl.find(new BasicDBObject("company.linkedinId",
                  ((LinkedinCompany) resource).get_id()));
            while (cJobs.hasNext()) {
               c2 = uColl.find(new BasicDBObject(
                     "linkedin.jobBookmarks.jobBookmarksList.job.id", Converter.toObject(
                           LinkedinJob.class, cJobs.next()).get_id()));
               while (c2.hasNext()) {
                  u = Converter.toObject(CrowdUser.class, c2.next());
                  if (users.containsKey(u)) {
                     if (!users.get(u).contains(
                           ResourceUserConnection.LINKEDIN_COMPANY_JOB_BOOKMARK))
                        users.get(u).add(
                              ResourceUserConnection.LINKEDIN_COMPANY_JOB_BOOKMARK);
                  } else {
                     connections = new ArrayList<ResourceUserConnection>();
                     connections.add(ResourceUserConnection.LINKEDIN_COMPANY_JOB_BOOKMARK);
                     users.put(u, connections);
                  }
               }
            }
            break;

         case LINKEDIN_JOB:
            connections.add(ResourceUserConnection.LINKEDIN_JOB);
            c = uColl.find(new BasicDBObject(
                  "linkedin.jobBookmarks.jobBookmarksList.job.id", ((LinkedinJob) resource)
                        .get_id()));
            while (c.hasNext())
               users.put(Converter.toObject(CrowdUser.class, c.next()), connections);
            break;

         case LINKEDIN_GROUP:
            connections.add(ResourceUserConnection.LINKEDIN_GROUP_MEMBERSHIP);
            c = uColl.find(new BasicDBObject(
                  "linkedin.groupMemberships.groupMembershipsList.group.linkedinId",
                  ((LinkedinGroup) resource).get_id()));
            while (c.hasNext())
               users.put(Converter.toObject(CrowdUser.class, c.next()), connections);
            break;

         case LINKEDIN_GROUP_POST:

            // user member of the group in which the resource was writed
            connections.add(ResourceUserConnection.LINKEDIN_GROUP_MEMBERSHIP_POST);
            c = uColl.find(new BasicDBObject(
                  "linkedin.groupMemberships.groupMembershipsList.group.linkedinId",
                  ((LinkedinGroupPost) resource).getGroupId()));

            while (c.hasNext()) {
               u = Converter.toObject(CrowdUser.class, c.next());
               users.put(u, connections);

               // user is the creator of the post

               if (((LinkedinGroupPost) resource).getCreator() != null
                     && ((LinkedinGroupPost) resource).getCreator().getLinkedinId() != null
                     && ((LinkedinGroupPost) resource).getCreator().getLinkedinId()
                           .equals(u.get_id())) {
                  users.get(u).add(
                        ResourceUserConnection.LINKEDIN_GROUP_MEMBERSHIP_POST_CREATOR);
               }

               // user likes the post
               for (LinkedinLike like : ((LinkedinGroupPost) resource).getLikes()
                     .getLikesList()) {
                  if (like.getPerson() != null && like.getPerson().getLinkedinId() != null
                        && like.getPerson().getLinkedinId().equals(u.get_id())) {
                     users.get(u).add(
                           ResourceUserConnection.LINKEDIN_GROUP_MEMBERSHIP_POST_LIKER);
                  }
               }

               // user commented the post
               List<LinkedinComment> comments = ((LinkedinGroupPost) resource).getComments()
                     .getCommentsList();
               for (LinkedinComment comment : comments)
                  if (comment.getCreator().getLinkedinId() != null
                        && comment.getCreator().getLinkedinId().equals(u.get_id()))
                     if (!users
                           .get(u)
                           .contains(
                                 ResourceUserConnection.LINKEDIN_GROUP_MEMBERSHIP_POST_PARTECIPANT))
                        users.get(u)
                              .add(ResourceUserConnection.LINKEDIN_GROUP_MEMBERSHIP_POST_PARTECIPANT);

               connections = new ArrayList<ResourceUserConnection>();
               connections.add(ResourceUserConnection.LINKEDIN_GROUP_MEMBERSHIP_POST);
            }
            break;

         case FACEBOOK_POST:

            // user poster
            user = uColl.findOne(new BasicDBObject("facebook._id", ((FacebookPost) resource)
                  .getFrom().get_id()));
            try {
               u = Converter.toObject(CrowdUser.class, user);
               connections.add(ResourceUserConnection.FACEBOOK_POSTER);
               users.put(u, connections);
            } catch (Exception e) {
               log.debug("User not relevant");
            }

            // post commenter
            usersId = new ArrayList<String>();
            if (((FacebookPost) resource).getComments() != null) {
               for (FacebookComment comm : ((FacebookPost) resource).getComments().getData())
                  usersId.add(comm.getFrom().get_id());
               c = uColl.find(new BasicDBObject("facebook._id", new BasicDBObject("$in",
                     usersId)));
               while (c.hasNext()) {

                  u = Converter.toObject(CrowdUser.class, c.next());
                  if (!users.containsKey(u)) {
                     connections = new ArrayList<ResourceUserConnection>();
                     connections.add(ResourceUserConnection.FACEBOOK_POST_PARTECIPANT);
                     users.put(u, connections);
                  } else
                     users.get(u).add(ResourceUserConnection.FACEBOOK_POST_PARTECIPANT);

               }
            }

            // post liker
            usersId = new ArrayList<String>();
            if (((FacebookPost) resource).getLikes() != null)
               for (FacebookCategorizedObject ids : ((FacebookPost) resource).getLikes()
                     .getData())
                  usersId.add(ids.get_id());
            c = uColl.find(new BasicDBObject("facebook._id", new BasicDBObject("$in",
                  usersId)));
            while (c.hasNext()) {

               u = Converter.toObject(CrowdUser.class, c.next());
               if (!users.containsKey(u)) {
                  connections = new ArrayList<ResourceUserConnection>();
                  connections.add(ResourceUserConnection.FACEBOOK_POST_LIKER);
                  users.put(u, connections);
               } else
                  users.get(u).add(ResourceUserConnection.FACEBOOK_POST_LIKER);
            }

            // post on user's wall
            user = uColl.findOne(new BasicDBObject("facebook._id", ((FacebookPost) resource)
                  .get_id().replaceAll("_.+", "")));
            try {
               u = Converter.toObject(CrowdUser.class, user);
               if (!users.containsKey(u)) {
                  connections = new ArrayList<ResourceUserConnection>();
                  connections.add(ResourceUserConnection.FACEBOOK_POST_FEED_OWNER);
                  users.put(u, connections);
               } else
                  users.get(u).add(ResourceUserConnection.FACEBOOK_POST_FEED_OWNER);
            } catch (Exception e) {
               log.debug("User not relevant");
            }

            // post of a group subscribed by the user
            c = uColl.find(new BasicDBObject("facebook.groups._id",
                  ((FacebookPost) resource).get_id().replaceAll("_.+", "")));

            while (c.hasNext()) {

               u = Converter.toObject(CrowdUser.class, c.next());
               if (!users.containsKey(u)) {
                  connections = new ArrayList<ResourceUserConnection>();
                  connections.add(ResourceUserConnection.FACEBOOK_POST_GROUP_SUBSCRIBED);
                  users.put(u, connections);
               } else
                  users.get(u).add(ResourceUserConnection.FACEBOOK_POST_GROUP_SUBSCRIBED);
            }

            // post of a page liked by the user

            c = uColl.find(new BasicDBObject("facebook.likes._id", ((FacebookPost) resource)
                  .getFrom().get_id()));
            while (c.hasNext()) {

               u = Converter.toObject(CrowdUser.class, c.next());
               if (!users.containsKey(u)) {
                  connections = new ArrayList<ResourceUserConnection>();
                  connections.add(ResourceUserConnection.FACEBOOK_POST_PAGE_SUBSCRIBED);
                  users.put(u, connections);
               } else
                  users.get(u).add(ResourceUserConnection.FACEBOOK_POST_PAGE_SUBSCRIBED);
            }
            break;

         case FACEBOOK_GROUP:

            connections.add(ResourceUserConnection.FACEBOOK_GROUP);
            c = uColl.find(new BasicDBObject("facebook.groups._id",
                  ((FacebookGroup) resource).get_id()));
            while (c.hasNext()) {

               u = Converter.toObject(CrowdUser.class, c.next());
               users.put(u, connections);

               if (((FacebookGroup) resource).getOwner() != null
                     && ((FacebookGroup) resource).getOwner().get_id().equals(u.get_id()))
                  users.get(u).add(ResourceUserConnection.FACEBOOK_GROUP_OWNER);
            }
            break;

         case FACEBOOK_PAGE:

            connections.add(ResourceUserConnection.FACEBOOK_PAGE);
            c = uColl.find(new BasicDBObject("facebook.likes._id", ((FacebookPage) resource)
                  .get_id()));
            while (c.hasNext()) {
               u = Converter.toObject(CrowdUser.class, c.next());
               users.put(u, connections);
            }
            break;

         case FACEBOOK_PROFILE:

            connections.add(ResourceUserConnection.FACEBOOK_PROFILE);
            users.put(Converter.toObject(CrowdUser.class, uColl.findOne(new BasicDBObject(
                  "facebook._id", ((FacebookUser) resource).get_id()))), connections);
            break;

         case LINKEDIN_PROFILE:

            connections.add(ResourceUserConnection.LINKEDIN_PROFILE);
            users.put(Converter.toObject(CrowdUser.class, uColl.findOne(new BasicDBObject(
                  "linkedin._id", ((LinkedinUser) resource).get_id()))), connections);
            break;

         case TWITTER_PROFILE:

            connections.add(ResourceUserConnection.TWITTER_PROFILE);
            users.put(Converter.toObject(CrowdUser.class, uColl.findOne(new BasicDBObject(
                  "twitter._id", ((TwitterUser) resource).get_id()))), connections);
            break;
      }
      return users;
   }
}
