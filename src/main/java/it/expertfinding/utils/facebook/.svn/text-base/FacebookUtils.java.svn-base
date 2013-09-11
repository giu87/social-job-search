/*
 * @(#)FacebookUtils.java   1.0   16/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.facebook;

import it.expertfinding.datamodel.resources.facebook.FacebookCheckin;
import it.expertfinding.datamodel.resources.facebook.FacebookGroup;
import it.expertfinding.datamodel.resources.facebook.FacebookGroupHighlight;
import it.expertfinding.datamodel.resources.facebook.FacebookNamedObject;
import it.expertfinding.datamodel.resources.facebook.FacebookPage;
import it.expertfinding.datamodel.resources.facebook.FacebookPageHighlight;
import it.expertfinding.datamodel.resources.facebook.FacebookPost;
import it.expertfinding.datamodel.users.facebook.FacebookUser;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.json.JsonObject;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;

public class FacebookUtils {

   private FacebookClient fc;
   protected static Logger log = Facade.log;
   protected static Configuration conf = Facade.conf;

   protected static DBCollection fColl = Facade.db.getCollection(Facade.TABLE_FACEBOOK);

   public List<FacebookNamedObject> getFriendsofUser(String facebookId) throws Exception {

      Connection<FacebookNamedObject> friends = null;
      do {
         try {
            friends = fc.fetchConnection(facebookId + "/friends", FacebookNamedObject.class);
            return friends.getData();
         } catch (Exception e) {
            friends = null;
            log.error("Exception retrieving friends. Retrying in 10 seconds");
            Thread.sleep(10000L);
         }
      } while (friends == null);

      return friends.getData();

   }

   public FacebookUser createFacebookUser(String facebookId, String token) throws Exception {
      FacebookUser user = null;
      int i = 0;
      do {
         i++;
         if (i > 10)
            throw new Exception("Error while retrieving data");
         try {
            user = fc.fetchObject(facebookId, FacebookUser.class);
            user.setToken(token);
            user.setLastProfileUpdate(new Date());
            return user;
         } catch (Exception e) {
            user = null;
            log.error("Exception creating User. Retrying in 10 seconds");
            Thread.sleep(10000L);
         }
      } while (user == null);
      return user;

   }

   public List<FacebookCheckin> saveCheckinsOfUser(String facebookId, Object since)
         throws Exception {

      List<FacebookCheckin> toReturn = new ArrayList<FacebookCheckin>();
      log.debug("Fetching chekins of user " + facebookId);
      Connection<FacebookCheckin> checkins;
      do {
         try {
            checkins = (since == null) ? fc.fetchConnection(facebookId + "/checkins",
                  FacebookCheckin.class) : fc.fetchConnection(facebookId + "/checkins",
                  FacebookCheckin.class, Parameter.with("limit", 100),
                  Parameter.with("since", since));
         } catch (Exception e) {
            checkins = null;
            log.error("Unable to retrieve information: there may be a problem with the authentication token. Retrying in 10 seconds");
            Thread.sleep(10000L);
         }
      } while (checkins == null);

      do {

         for (FacebookCheckin checkin : checkins.getData()) {
            // Not necessary-> the unique index ensure no object with the same facebookId
            // if (!MongoUtils.isFacebookIdPresent(fColl, checkin.getFacebookId()))
            MongoUtils.saveDocumentData(fColl, checkin);

         }
         toReturn.addAll(checkins.getData());
         if (since != null)
            break;
         if (checkins.hasNext()) {
            String next = checkins.getNextPageUrl();
            do {
               try {
                  checkins = fc.fetchConnectionPage(next, FacebookCheckin.class);
               } catch (Exception e) {
                  checkins = null;
                  log.error("Unable to retrieve information: there may be a problem with the authentication token. Retrying in 10 seconds");
                  Thread.sleep(10000L);
               }
            } while (checkins == null);
         }

      } while (checkins.hasNext());

      return toReturn;
   }

   public List<FacebookPageHighlight> saveLikesOfUser(String facebookId, Object since)
         throws Exception {
      List<FacebookPageHighlight> toReturn = new ArrayList<FacebookPageHighlight>();

      log.debug("Fetching likes of user " + facebookId);
      Connection<FacebookPageHighlight> likes;
      do {
         try {
            likes = (since == null) ? fc.fetchConnection(facebookId + "/likes",
                  FacebookPageHighlight.class, Parameter.with("limit", 150)) : fc
                  .fetchConnection(facebookId + "/likes", FacebookPageHighlight.class,
                        Parameter.with("limit", 100), Parameter.with("since", since));
         } catch (Exception e) {
            likes = null;
            log.error("Retrying in 10 seconds", e);
            Thread.sleep(10000L);
            // likes = (since == null) ? fc.fetchConnection(facebookId + "/likes",
            // FacebookPageHighlight.class, Parameter.with("limit", 150)) : fc
            // .fetchConnection(facebookId + "/likes", FacebookPageHighlight.class,
            // Parameter.with("limit", 100), Parameter.with("since", since));
         }
      } while (likes == null);
      do {

         try {
            List<String> ids = new ArrayList<String>();
            for (FacebookPageHighlight h : likes.getData()) {
               if (!MongoUtils.isFacebookIdPresent(fColl, h.get_id())) {

                  log.debug("Page id " + h.get_id()
                        + " not present in the database.\nSaving it.");
                  ids.add(h.get_id());
               } else
                  log.debug("Page id " + h.get_id()
                        + " already in the datbase.\nNot saving it.");

               savePostsFromActorIdAndSourceId(facebookId, h.get_id());

            }
            if (!ids.isEmpty()) {
               // List<DBObject> dbLikesList = new ArrayList<DBObject>();
               JsonObject jsonLikes = null;
               do {
                  try {
                     jsonLikes = fc.fetchObjects(ids, JsonObject.class);
                  } catch (Exception e) {
                     jsonLikes = null;
                     log.error("Retrying in 10 seconds", e);
                     Thread.sleep(10000L);
                  }
               } while (jsonLikes == null);

               JsonMapper jsonMapper = new DefaultJsonMapper();
               @SuppressWarnings("unchecked")
               Iterator<String> keys = (Iterator<String>) jsonLikes.keys();
               while (keys.hasNext()) {
                  FacebookPage like = jsonMapper.toJavaObject(
                        jsonLikes.getString(keys.next()), FacebookPage.class);
                  MongoUtils.saveDocumentData(fColl, like);
                  savePostsFromActorIdAndSourceId(like.get_id(), like.get_id());
                  savePostsFromSource(like.get_id(), "/feed", 150, null, null, 1);

                  // dbLikesList.add(Converter.toDBObject(like));
               }
               // MongoUtils.insertObjectsData(fColl, dbLikesList);
            }
            toReturn.addAll(likes.getData());
         } catch (MongoException e) {
            log.error("Error saving likes to Database");
            throw new Exception("Exception retrieving likes of user: " + facebookId, e);
         } catch (Exception e) {
            log.error("Error gathering Facebook Likes");
            throw new Exception("Exception retrieving likes of user: " + facebookId, e);
         }
         if (since != null)
            break;
         if (likes.hasNext()) {
            String next = likes.getNextPageUrl();
            do {
               try {
                  likes = fc.fetchConnectionPage(next, FacebookPageHighlight.class);
               } catch (Exception e) {
                  likes = null;
                  log.error("Retrying in 10 seconds", e);
                  Thread.sleep(10000L);
               }
            } while (likes == null);
         }

      } while (likes.hasNext());

      return toReturn;
   }

   public List<FacebookGroupHighlight> saveGroupsOfUser(String facebookId) throws Exception {

      List<FacebookGroupHighlight> toReturn = new ArrayList<FacebookGroupHighlight>();

      log.debug("Fetching groups of user " + facebookId);
      Connection<FacebookGroupHighlight> groups;
      do {
         try {
            groups = fc
                  .fetchConnection(facebookId + "/groups", FacebookGroupHighlight.class);
         } catch (Exception e) {
            groups = null;
            log.error("Retrying in 10 seconds");
            Thread.sleep(10000L);
         }
      } while (groups == null);
      do {

         try {
            List<String> ids = new ArrayList<String>();
            for (FacebookGroupHighlight h : groups.getData()) {
               if (!MongoUtils.isFacebookIdPresent(fColl, h.get_id())) {
                  log.debug("Group id " + h.get_id()
                        + " not present in the database.\nSaving it.");
                  ids.add(h.get_id());
               } else
                  log.debug("Group id " + h.get_id()
                        + " already in the datbase.\nNot saving it.");

            }
            if (!ids.isEmpty()) {
               // List<DBObject> dbGroupsList = new ArrayList<DBObject>();
               JsonObject jsonGroups = null;
               do {
                  try {
                     jsonGroups = fc.fetchObjects(ids, JsonObject.class);
                  } catch (Exception e) {
                     jsonGroups = null;
                     log.error("Retrying in 10 seconds", e);
                     Thread.sleep(10000L);
                  }
               } while (jsonGroups == null);
               JsonMapper jsonMapper = new DefaultJsonMapper();
               @SuppressWarnings("unchecked")
               Iterator<String> keys = (Iterator<String>) jsonGroups.keys();
               // List<FacebookGroup> groupsList = new ArrayList<FacebookGroup>();

               while (keys.hasNext()) {
                  FacebookGroup group = jsonMapper.toJavaObject(
                        jsonGroups.getString(keys.next()), FacebookGroup.class);
                  MongoUtils.saveDocumentData(fColl, group);

                  // group.setFeed(savePostsFromSource(fc, group.getFacebookId(), "/feed",
                  // null,
                  // null, null, 0));
                  savePostsFromSource(group.get_id(), "/feed", 150, null, null, 0);

                  // dbGroupsList.add(Converter.toDBObject(group));

               }
               // MongoUtils.insertObjectsData(fColl, dbGroupsList);
            }

            toReturn.addAll(groups.getData());
         } catch (MongoException e) {
            log.error("Error saving groups to Database");
            throw new Exception("Exception retrieving groups of user: " + facebookId, e);
         } catch (Exception e) {
            log.error("Error gathering Facebook Groups");
            throw new Exception("Exception retrieving groups of user: " + facebookId, e);
         }
         if (groups.hasNext()) {
            String next = groups.getNextPageUrl();
            do {
               try {
                  groups = fc.fetchConnectionPage(next, FacebookGroupHighlight.class);
               } catch (Exception e) {
                  groups = null;
                  log.error("Retrying in 10 seconds");
                  Thread.sleep(10000L);
               }
            } while (groups == null);
         }

      } while (groups.hasNext());

      return toReturn;
   }

   private Connection<FacebookPost> buildConnection(String sourceId, String path,
         Object limit, Object since, Object until) throws Exception {

      try {
         if (limit != null && since != null && until != null)
            return fc.fetchConnection(sourceId + path, FacebookPost.class,
                  Parameter.with("limit", limit), Parameter.with("since", since),
                  Parameter.with("until", until));
         else if (limit != null && since != null && until == null)
            return fc.fetchConnection(sourceId + path, FacebookPost.class,
                  Parameter.with("limit", limit), Parameter.with("since", since));
         else if (limit != null && since == null && until != null)
            return fc.fetchConnection(sourceId + path, FacebookPost.class,
                  Parameter.with("limit", limit), Parameter.with("until", until));
         else if (limit == null && since != null && until != null)
            return fc.fetchConnection(sourceId + path, FacebookPost.class,
                  Parameter.with("since", since), Parameter.with("until", until));
         else if (limit != null && since == null && until == null)
            return fc.fetchConnection(sourceId + path, FacebookPost.class,
                  Parameter.with("limit", limit));
         else if (limit == null && since == null && until != null)
            return fc.fetchConnection(sourceId + path, FacebookPost.class,
                  Parameter.with("until", until));
         else if (limit == null && since != null && until == null)
            return fc.fetchConnection(sourceId + path, FacebookPost.class,
                  Parameter.with("since", since));

         else
            return fc.fetchConnection(sourceId + path, FacebookPost.class);
      } catch (Exception e) {
         throw new Exception("Error building post connection", e);
      }
   }

   public List<FacebookPost> savePostsFromSource(String sourceId, String path, Object limit,
         Object since, Object until, int pages) throws Exception {
      List<FacebookPost> toReturn = new ArrayList<FacebookPost>();
      Connection<FacebookPost> posts;
      do {

         try {
            posts = buildConnection(sourceId, path, limit, since, until);

         } catch (Exception e) {
            posts = null;
            log.error("Retrying in 10 seconds");
            Thread.sleep(10000L);
            break;
         }
      } while (posts == null);
      int i = 0;
      do {
         i++;
         log.debug("Retrieved page: " + i);
         List<FacebookPost> temp = new ArrayList<FacebookPost>();
         temp.addAll(posts.getData());
         try {
            List<String> uncompletePosts = new ArrayList<String>();

            for (FacebookPost post : posts.getData()) {

               // try {
               // FacebookPost fullCommentedPost = fc.fetchObject(post.getFacebookId(),
               // FacebookPost.class);
               // post.setComments(fullCommentedPost.getComments());
               // } catch (FacebookOAuthException e) {
               // log.debug("Unable to retrieve information: there may be a problem with the authentication token");
               // throw new Exception("Exception retrieving user profile", e);
               // }

               if (post.getStory() == null) {

                  if (post.getComments() != null && post.getComments().getCount() > 3) {
                     uncompletePosts.add(post.get_id());
                  } else {
                     MongoUtils.saveDocumentData(fColl, post);
                  }

               }

            }

            if (!uncompletePosts.isEmpty()) {
               JsonObject jsonPosts = null;
               do {
                  try {
                     jsonPosts = fc.fetchObjects(uncompletePosts, JsonObject.class);
                  } catch (Exception e) {
                     jsonPosts = null;
                     log.error("Retrying in 10 seconds", e);
                     Thread.sleep(10000L);
                  }
               } while (jsonPosts == null);
               JsonMapper jsonMapper = new DefaultJsonMapper();
               @SuppressWarnings("unchecked")
               Iterator<String> keys = (Iterator<String>) jsonPosts.keys();

               while (keys.hasNext()) {
                  FacebookPost completePost = jsonMapper.toJavaObject(
                        jsonPosts.getString(keys.next()), FacebookPost.class);
                  MongoUtils.saveDocumentData(fColl, completePost);
                  temp.add(completePost);
               }
            }
            // OLD METOD
            // List<BatchRequest> requests = new ArrayList<BatchRequest>();
            //
            // if (!uncompletePosts.isEmpty()) {
            // int j = 0;
            // for (String id : uncompletePosts) {
            // if (j < 50) {
            // BatchRequest req = new BatchRequest.BatchRequestBuilder(id).build();
            // requests.add(req);
            // j++;
            // } else {
            //
            // List<BatchResponse> responses;
            // do {
            // try {
            // responses = fc
            // .executeBatch(requests.toArray(new BatchRequest[0]));
            // for (BatchResponse resp : responses) {
            // if (false)
            // throw new Exception();
            // }
            // requests.clear();
            // } catch (Exception e) {
            // responses = null;
            // log.error("Retrying in 10 seconds");
            // Thread.sleep(10000L);
            //
            // }
            // } while (responses == null);
            //
            // for (BatchResponse resp : responses) {
            //
            // if (resp == null) {
            // log.error("Null response");
            // } else if (resp.getCode() != 200) {
            // log.error("Got error response for: " + resp.toString());
            // } else {
            // JsonMapper jsonMapper = new DefaultJsonMapper();
            // FacebookPost completePost = jsonMapper.toJavaObject(
            // resp.getBody(), FacebookPost.class);
            // MongoUtils.saveDocumentData(fColl, completePost);
            // temp.add(completePost);
            // }
            // }
            // BatchRequest req = new BatchRequest.BatchRequestBuilder(id).build();
            // requests.add(req);
            // j = 1;
            // }
            //
            // }
            // // processing last request
            // List<BatchResponse> responses;
            // do {
            // try {
            // responses = fc.executeBatch(requests.toArray(new BatchRequest[0]));
            // for (BatchResponse resp : responses) {
            // if (false)
            // throw new Exception();
            // }
            // requests.clear();
            // } catch (Exception e) {
            // responses = null;
            // log.error("Retrying in 10 seconds");
            // Thread.sleep(10000L);
            //
            // }
            // } while (responses == null);
            // for (BatchResponse resp : responses) {
            //
            // if (resp == null) {
            // log.error("Null response");
            // } else if (resp.getCode() != 200) {
            // log.error("Got error response for: " + resp.toString());
            // } else {
            // JsonMapper jsonMapper = new DefaultJsonMapper();
            // FacebookPost completePost = jsonMapper.toJavaObject(resp.getBody(),
            // FacebookPost.class);
            // try {
            // MongoUtils.saveDocumentData(fColl, completePost);
            // } catch (Exception e) {
            // if (completePost != null)
            // log.debug("Error saving post id: " + completePost.get_id());
            // log.error("Error saving to db", e);
            // }
            // temp.add(completePost);
            // }
            // }
            // }
            // END OLD METOD

         } catch (MongoException e) {
            log.error("Error saving posts to Database");
            throw new Exception("Exception retrieving post from source: " + sourceId, e);
         } catch (Exception e) {
            log.error("Error gathering Facebook Posts");
            throw new Exception("Exception retrieving post from source: " + sourceId, e);
         }
         toReturn.addAll(temp);
         // Stop at the page chosen by the pages param
         if (pages == i)
            break;
         if (posts.hasNext()) {
            String next = posts.getNextPageUrl();
            do {
               try {
                  posts = fc.fetchConnectionPage(next, FacebookPost.class);
               } catch (Exception e) {
                  posts = null;
                  log.error("Retrying in 10 seconds");
                  Thread.sleep(10000L);
               }
            } while (posts == null);
         }

      } while (posts.hasNext());
      return toReturn;
   }

   /**
    * @return Returns the fc.
    */
   public FacebookClient getFc() {
      return this.fc;
   }

   /**
    * @param fc
    *           The fc to set.
    */
   public void setFc(FacebookClient fc) {
      this.fc = fc;
   }

   public void savePostsFromActorIdAndSourceId(String actorId, String sourceId)
         throws InterruptedException {

      String query = "SELECT post_id FROM stream WHERE actor_id=" + actorId
            + " AND source_id=" + sourceId;
      List<FacebookObjectFQL> idsFQL;
      do {
         try {
            idsFQL = fc.executeQuery(query, FacebookObjectFQL.class);
         } catch (Exception e) {
            idsFQL = null;
         }

      } while (idsFQL == null);
      List<String> ids = new ArrayList<String>();
      for (FacebookObjectFQL id : idsFQL) {
         ids.add(id.get_id());
      }
      if (!ids.isEmpty()) {
         JsonObject jsonPosts = null;
         do {
            try {
               jsonPosts = fc.fetchObjects(ids, JsonObject.class);
            } catch (FacebookNetworkException e) {
               jsonPosts = null;
               log.error("Retrying in 10 seconds", e);
               if (e.getHttpStatusCode().equals(404))
                  break;
               Thread.sleep(10000L);
            } catch (FacebookJsonMappingException e) {
               jsonPosts = null;
               break;
            } catch (Exception e) {
               jsonPosts = null;
               log.error("Retrying in 10 seconds", e);
               Thread.sleep(10000L);
            }
         } while (jsonPosts == null);
         if (jsonPosts != null) {
            JsonMapper jsonMapper = new DefaultJsonMapper();
            @SuppressWarnings("unchecked")
            Iterator<String> keys = (Iterator<String>) jsonPosts.keys();
            while (keys.hasNext()) {
               FacebookPost post = jsonMapper.toJavaObject(jsonPosts.getString(keys.next()),
                     FacebookPost.class);
               MongoUtils.saveDocumentData(fColl, post);
            }
         }

      }

   }

   public static class FacebookObjectFQL {

      @Facebook("post_id")
      private String _id;

      /**
       * This object's unique Facebook ID.
       * 
       * @return This object's unique Facebook ID.
       */
      public String get_id() {
         return this._id;
      }

      public void set_id(String _id) {
         this._id = _id;
      }
   }

   public static FacebookUtils getUtilsInstance(String token) {
      FacebookClient fc = new DefaultFacebookClient(token);
      FacebookUtils utils = new FacebookUtils();
      utils.setFc(fc);
      return utils;
   }

   public Pictures getProfilePictures(String id) {

      String query = "SELECT pic,pic_big,pic_small,pic_square FROM user WHERE uid=" + id;

      Pictures pictures;
      try {
         pictures = fc.executeQuery(query, Pictures.class).get(0);
      } catch (Exception e) {
         pictures = new Pictures();
         pictures
               .setPic("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg");
         pictures
               .setPic_big("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg");
         pictures
               .setPic_small("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg");
         pictures
               .setPic_square("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg");

      }
      return pictures;

   }

   public static class Pictures {

      @Facebook
      String pic;
      @Facebook
      String pic_big;
      @Facebook
      String pic_small;
      @Facebook
      String pic_square;

      public String getPic() {
         return this.pic;
      }

      public String getPic_big() {
         return this.pic_big;
      }

      public String getPic_small() {
         return this.pic_small;
      }

      public String getPic_square() {
         return this.pic_square;
      }

      /**
       * @param pic
       *           The pic to set.
       */
      public void setPic(String pic) {
         this.pic = pic;
      }

      /**
       * @param pic_big
       *           The pic_big to set.
       */
      public void setPic_big(String pic_big) {
         this.pic_big = pic_big;
      }

      /**
       * @param pic_small
       *           The pic_small to set.
       */
      public void setPic_small(String pic_small) {
         this.pic_small = pic_small;
      }

      /**
       * @param pic_square
       *           The pic_square to set.
       */
      public void setPic_square(String pic_square) {
         this.pic_square = pic_square;
      }

   }
}
