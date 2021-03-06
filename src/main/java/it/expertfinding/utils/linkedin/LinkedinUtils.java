/*
 * @(#)LinkedinUtils.java   1.0   19/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinCompany;
import it.expertfinding.datamodel.resources.linkedin.LinkedinGroup;
import it.expertfinding.datamodel.resources.linkedin.LinkedinGroupPost;
import it.expertfinding.datamodel.resources.linkedin.LinkedinJob;
import it.expertfinding.datamodel.resources.linkedin.LinkedinResource;
import it.expertfinding.datamodel.resources.linkedin.LinkedinShare;
import it.expertfinding.datamodel.resources.linkedin.LinkedinUpdate;
import it.expertfinding.datamodel.users.CrowdUser;
import it.expertfinding.datamodel.users.linkedin.LinkedinGroupMembership;
import it.expertfinding.datamodel.users.linkedin.LinkedinGroupMemberships;
import it.expertfinding.datamodel.users.linkedin.LinkedinUser;
import it.expertfinding.utils.Facade;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.restfb.JsonMapper;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.commons.configuration.Configuration;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class LinkedinUtils {

   protected static Configuration conf = Facade.conf;
   protected static OAuthService linkedin = new ServiceBuilder().provider(LinkedInApi.class)
         .apiKey(conf.getString("linkedin.API_KEY"))
         .apiSecret(conf.getString("linkedin.API_SECRET")).build();

   public static String getJSONFromLinkedin(String url, String token, String secret) {

      Token accessToken = new Token(token, secret);
      OAuthRequest request = new OAuthRequest(Verb.GET, url);
      linkedin.signRequest(accessToken, request);

      Response response = request.send();

      return response.getBody();
   }

   public static LinkedinUser getLinkedinUser(String token, String secret) {

      if (token != null && secret != null) {

         JsonMapper mapper = new LinkedinJsonMapper();

         String json = LinkedinUtils.getJSONFromLinkedin(
               getUrlForLinkedinData("user", null), token, secret);

         System.out.println(json);
         // lu = new LinkedinUser();
         LinkedinUser lu = mapper.toJavaObject(json, LinkedinUser.class);

         lu.setToken(token);
         lu.setSecret(secret);

         return lu;
      }
      return null;
   }

   public static LinkedinShare getLinkedinSharesOfUser(String token, String secret) {

      if (token != null && secret != null) {

         JsonMapper mapper = new LinkedinJsonMapper();

         String json = LinkedinUtils.getJSONFromLinkedin(
               getUrlForLinkedinData("share", null), token, secret);

         System.out.print(json);
         System.out.println(json);
         JsonObject j = new JsonObject(json);

         if (!json.contains("errorCode") && j.getInt("_total") > 0) {
            for (int i = 0; i < j.getJsonArray("values").length(); i++) {

               LinkedinUpdate ls = new LinkedinUpdate();
               ls = mapper.toJavaObject(j.getJsonArray("values").getString(i),
                     LinkedinUpdate.class);

               storeResource(ls);
            }
         }
      }
      return null;
   }

   public static List<LinkedinJob> getLinkedinJobsOfUser(LinkedinUser lu) {

      List<LinkedinJob> jobs = new ArrayList<LinkedinJob>();
      for (int i = 0; i < lu.getJobBookmarks().getJobBookmarksList().size(); i++) {
         if (lu.getJobBookmarks().getJobBookmarksList().get(i).getJob().getId() != null)
            jobs.add(getLinkedinJobById(lu.getJobBookmarks().getJobBookmarksList().get(i)
                  .getJob().getId(), lu.getToken(), lu.getSecret()));
      }
      return jobs;
   }

   public static LinkedinUser getLinkedinGroupMembershipsOfUser(LinkedinUser lu) {

      System.out.println("groupMemberships retrieving json");
      if (lu.getToken() != null && lu.getSecret() != null) {

         JsonMapper mapper = new LinkedinJsonMapper();

         String json = LinkedinUtils.getJSONFromLinkedin(
               getUrlForLinkedinData("groupMemberships", null), lu.getToken(),
               lu.getSecret());

         System.out.println(json);

         LinkedinGroupMemberships gm = mapper.toJavaObject(json,
               LinkedinGroupMemberships.class);

         lu.setGroupMemberships(gm);

         return lu;
      }
      return null;
   }

   public static LinkedinJob getLinkedinJobById(String id, String token, String secret) {

      if (token != null && secret != null) {

         String json = LinkedinUtils.getJSONFromLinkedin(getUrlForLinkedinData("job", id),
               token, secret);

         JsonMapper mapper = new LinkedinJsonMapper();
         LinkedinJob lj = new LinkedinJob();

         lj = mapper.toJavaObject(json, LinkedinJob.class);

         storeResource(lj);

         return lj;
      }
      return null;
   }

   public static void getLinkedinCompaniesOfUser(LinkedinUser lu) {

      System.out.println("getCompanies by user");
      for (int i = 0; i < lu.getPositions().getPositionsList().size(); i++) {
         if (lu.getPositions().getPositionsList().get(i).getCompany().getLinkedinId() != null)
            getLinkedinCompanyById(lu.getPositions().getPositionsList().get(i).getCompany()
                  .getLinkedinId(), lu.getToken(), lu.getSecret());
      }
   }

   private static void getLinkedinCompaniesOfJobsBookmarkedByUser(List<LinkedinJob> jobs,
         String token, String secret) {

      System.out.println("getCompanies by job Bookmark");
      for (int i = 0; i < jobs.size(); i++) {
         if (jobs.get(i).getCompany().getLinkedinId() != null)
            getLinkedinCompanyById(jobs.get(i).getCompany().getLinkedinId(), token, secret);
      }
   }

   public static LinkedinCompany getLinkedinCompanyById(String id, String token,
         String secret) {

      System.out.println("id = " + id);
      if (token != null && secret != null) {

         String json = LinkedinUtils.getJSONFromLinkedin(
               getUrlForLinkedinData("company", id), token, secret);
         System.out.print(json);
         JsonMapper mapper = new LinkedinJsonMapper();
         LinkedinCompany lc = new LinkedinCompany();

         lc = mapper.toJavaObject(json, LinkedinCompany.class);

         storeResource(lc);

         return lc;
      }
      return null;
   }

   public static void getLinkedinGroupsOfUser(LinkedinUser lu) {

      for (LinkedinGroupMembership g : lu.getGroupMemberships().getGroupMembershipsList()) {
         if (g.getGroup().getLinkedinId() != null) {
            LinkedinGroup lg = getLinkedinGroupById(g.getGroup().getLinkedinId(),
                  lu.getToken(), lu.getSecret());
            getLinkedinPostsOfGroup(lg.get_id(), lu.getToken(), lu.getSecret());
         }
      }
   }

   private static LinkedinGroup getLinkedinGroupById(String id, String token, String secret) {

      if (token != null && secret != null) {

         String json = LinkedinUtils.getJSONFromLinkedin(getUrlForLinkedinData("group", id),
               token, secret);

         System.out.println("GROUPS");
         System.out.println(json);

         JsonMapper mapper = new LinkedinJsonMapper();
         LinkedinGroup gr = new LinkedinGroup();

         gr = mapper.toJavaObject(json, LinkedinGroup.class);

         storeResource(gr);

         return gr;
      }
      return null;
   }

   public static List<LinkedinGroupPost> getLinkedinPostsOfGroup(String id, String token,
         String secret) {

      List<LinkedinGroupPost> posts = new ArrayList<LinkedinGroupPost>();

      if (token != null && secret != null) {

         String json = LinkedinUtils.getJSONFromLinkedin(getUrlForLinkedinData("posts", id),
               token, secret);

         System.out.println("json = '" + json + "'");
         if (!json.equals("null")) {
            JsonMapper mapper = new LinkedinJsonMapper();
            JsonObject j = new JsonObject(json);
            if (j.getInt("_total") > 0) {
               JsonArray values = j.getJsonArray("values");

               for (int i = 0; i < values.length(); i++) {

                  LinkedinGroupPost p = mapper.toJavaObject(values.get(i).toString(),
                        LinkedinGroupPost.class);
                  p.setGroupId(id);
                  storeResource(p);
                  posts.add(p);
               }
            }
         }
      }
      return posts;
   }

   public static void storeUser(LinkedinUser lu) {

      DB db = Facade.db;
      DBCollection pColl = db.getCollection("linkedinUser");

      DBObject dbObject = Converter.toDBObject(lu);
      pColl.save(dbObject);
   }

   public static void storeResource(LinkedinResource lr) {

      DB db = Facade.db;
      DBCollection pColl = db.getCollection("linkedinResource");

      DBObject dbObject = Converter.toDBObject(lr);
      pColl.save(dbObject);
   }

   public static void storeFriend(LinkedinUser lr) {

      DB db = Facade.db;
      DBCollection pColl = db.getCollection("friend");

      DBObject dbObject = Converter.toDBObject(lr);
      pColl.save(dbObject);
   }

   private static void getLinkedinConnectionsOfUser(LinkedinUser lu) {

      if (lu.getToken() != null && lu.getSecret() != null) {
      
         String json = LinkedinUtils.getJSONFromLinkedin(getUrlForLinkedinData("connection",null),
                 lu.getToken(), lu.getSecret());
            
         System.out.println(json);
         JsonMapper mapper = new LinkedinJsonMapper();
         
         JsonObject connections = new JsonObject(json);
         for(int i=0; i<connections.getJsonArray("values").length();i++){
            
            LinkedinUser l = mapper.toJavaObject(connections.getJsonArray("values").get(i).toString(), LinkedinUser.class);
            Facade.db.getCollection("friends").save(Converter.toDBObject(l));
         }         
      }      
   }
   
   public static void main(String[] args){
      
      DBCursor c = Facade.db.getCollection(Facade.TABLE_USER).find();
      while(c.hasNext()){
         
         CrowdUser user = Converter.toObject(CrowdUser.class, c.next()); 
         if(user.getLinkedin()!= null)
            getLinkedinConnectionsOfUser(user.getLinkedin());
         break;
      }
   }

   public static void getResourcesOfUser(LinkedinUser lu) {

      List<LinkedinJob> jobsOfUser = getLinkedinJobsOfUser(lu);
      getLinkedinCompaniesOfJobsBookmarkedByUser(jobsOfUser, lu.getToken(), lu.getSecret());
      getLinkedinSharesOfUser(lu.getToken(), lu.getSecret());
      getLinkedinCompaniesOfUser(lu);
      getLinkedinGroupsOfUser(lu);
   }


   public static String getUrlForLinkedinData(String type, String id) {

      switch (type) {
         case "user":
            return "http://api.linkedin.com/v1/people/~"
                  + ":(id,first-name,last-name,maiden-name,headline,location,industry,last-modified-timestamp,current-share,connections,num-connections,num-connections-capped,summary,specialties,proposal-comments,associations,honors,interests,"
                  + "phone-numbers,im-accounts,twitter-accounts,primary-twitter-account,bound-account-types,mfeed-rss-url,following,job-bookmarks,suggestions,date-of-birth,main-address,member-url-resources,picture-url,site-standard-profile-request,api-standard-profile-request,public-profile-url,related-profile-views,"
                  + "positions,publications,patents,languages,skills,certifications,educations,courses,volunteer,three-current-positions,three-past-positions,recommendations-received)?format=json";
         case "company":
            return "http://api.linkedin.com/v1/companies/"
                  + id
                  + ":(id,universal-name,description,industry,logo-url,name,type,company-type,size,stock-exchange,ticker,"
                  + "specialties,blog-rss-url,twitter-id,square-logo-url,locations,founded-year,end-year,num-followers,"
                  + "email-domains,website-url,status,employee-count-range)?format=json";
         case "share":
            return "http://api.linkedin.com/v1/people/~/network/updates?type=SHAR&scope=self&format=json&count=1000";
         case "group":
            return "http://api.linkedin.com/v1/groups/" + id
                  + ":(id,name,site-group-url,posts)?format=json";
         case "job":
            return "http://api.linkedin.com/v1/jobs/"
                  + id
                  + ":(id,customer-job-code,active,posting-date,expiration-date,posting-timestamp,company:(id,name),"
                  + "position:(title,location,job-functions,industries,job-type,experience-level),skills-and-experience,description-snippet,description,salary,"
                  + "job-poster:(id,first-name,last-name,headline),referral-bonus,site-job-url,location-description)?format=json";
         case "groupMemberships":
            return "http://api.linkedin.com/v1/people/~"
                  + "/group-memberships?format=json&count=1000";
         case "posts":
            return "http://api.linkedin.com/v1/groups/"
                  + id
                  + "/posts:(id,type,creation-timestamp,title,summary,creator,likes,attachment,relation-to-viewer,comments,category,site-group-post-url)?format=json&count=1000";
         case "connection":
            return "http://api.linkedin.com/v1/people/~/connections"
                  + ":(id,first-name,last-name,maiden-name,headline,location,industry,last-modified-timestamp,current-share,connections,num-connections,num-connections-capped,summary,specialties,proposal-comments,associations,honors,interests,"
                  + "phone-numbers,im-accounts,twitter-accounts,primary-twitter-account,bound-account-types,mfeed-rss-url,following,job-bookmarks,suggestions,date-of-birth,main-address,member-url-resources,picture-url,site-standard-profile-request,api-standard-profile-request,public-profile-url,related-profile-views,"
                  + "positions,publications,patents,languages,skills,certifications,educations,courses,volunteer,three-current-positions,three-past-positions,recommendations-received)?format=json";
         default:
            return null;
      }
   }
}
