/*
 * @(#)SolrManager.java   1.0   01/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.core;

import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.datamodel.resources.twitter.TwitterPage;
import it.expertfinding.datamodel.users.twitter.TwitterUser;
import it.expertfinding.utils.Facade;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.StreamingUpdateSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrManager {

   protected static Logger log = LoggerFactory.getLogger("Expertfinding");
   protected static Configuration conf = Facade.conf;
   protected static final String TWITTER_ITALIAN_CORE = "/twitterItalian";
   protected static final String TWITTER_ENGLISH_CORE = "/twitterEnglish";
   protected static final String FACEBOOK_ITALIAN_CORE = "/facebookItalian";
   protected static final String FACEBOOK_ENGLISH_CORE = "/facebookEnglish";
   protected static final String LINKEDIN_ITALIAN_CORE = "/linkedinItalian";
   protected static final String LINKEDIN_ENGLISH_CORE = "/linkedinEnglish";
   protected static final String MERGED_ITALIAN_CORE = "/mergedItalian";
   protected static final String MERGED_ENGLISH_CORE = "/mergedEnglish";
   private static final String PROB_CORE = "/probCore";
   private static final String PROB_CORE_IT = "/probCoreIt";
   private static final String PROB_CORE_ENG = "/probCoreEng";
   private static SolrServer itFacebookServer = null;
   private static SolrServer enFacebookServer = null;
   private static SolrServer itTwitterServer = null;
   private static SolrServer enTwitterServer = null;
   private static SolrServer itLinkedinServer = null;
   private static SolrServer enLinkedinServer = null;
   private static SolrServer itMergedServer = null;
   private static SolrServer enMergedServer = null;
   private static SolrServer itMergedServerShort = null;
   private static SolrServer enMergedServerShort = null;
   private static SolrServer itMergedServerProfiles = null;
   private static SolrServer enMergedServerProfiles = null;

   public static class Item {

      private String id;

      private String prova;

      @Field("*_txt_en")
      private Map<String, String> entities = new HashMap<String, String>();

      public Map<String, String> getEntities() {
         return this.entities;
      }

      public void setEntities(Map<String, String> entities) {
         this.entities = entities;
      }

      /**
       * @return Returns the id.
       */
      public String getId() {
         return this.id;
      }

      /**
       * @param id
       *           The id to set.
       */
      @Field
      public void setId(String id) {
         this.id = id;
      }

      /**
       * @return Returns the prova.
       */
      public String getProva() {
         return this.prova;
      }

      /**
       * @param prova
       *           The prova to set.
       */
      @Field("prova_txt_en")
      public void setProva(String prova) {
         this.prova = prova;
      }

   }

   public static void main(String[] args) throws IOException, SolrServerException {

      SolrServer ser = null;
      try {
         ser = getServer("en");
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      SolrInputDocument doc = new SolrInputDocument();
      doc.setField("id", "ciao");
      doc.setField("entityIdList", new String[] { "ciao|3.0", "puo|4.0" });
      doc.setField("text", "ciao ciao");

      SolrInputDocument doc2 = new SolrInputDocument();

      doc2.setField("id", "ciao2");
      doc2.setField("entityIdList", new String[] { "ciao|8.0", "ciao|7.0" });
      doc2.setField("text", "ciao puo puo puo");

      ser.add(doc);
      ser.add(doc2);
      // prova();
      // Item prova = new Item();
      // prova.setId("caccuuuzzz");
      // prova.setProva("I\'m writing a client using SolrJ and was wondering how to handle a multi \ncore installation.  We want to use the facility to rebuild the index on one \nof the cores at a scheduled time and then use the SWAP facility to switch \nthe \"live\" core to the newly rebuilt core.  I think I can do the SWAP with \nCoreAdminRequest.setAction() with a suitable parameter. \n");
      // LanguageDetector detector = new LanguageDetector();
      // String fields[] = { prova.getProva() };
      // log.debug(detector.detect(fields));
      // prova.getEntities().put("city_txt_en", "Milano Roma");
      // prova.getEntities().put("person_txt_en", "Matteo Frocio");
      // SolrManager manager = new SolrManager();
      // manager.addBeanDocument(prova, "en");

   }

   // public static void addBeanDocument(Object bean, String lang) throws IOException,
   // SolrServerException {
   //
   // if (itServer == null) {
   // itServer = new CommonsHttpSolrServer(ITALIAN_CORE);
   // }
   //
   // if (enServer == null) {
   // enServer = new CommonsHttpSolrServer(ENGLISH_CORE);
   // }
   //
   // if (lang.equals(Locale.ITALIAN.getLanguage())) {
   // itServer.addBean(bean);
   // itServer.commit();
   // }
   //
   // if (lang.equals(Locale.ENGLISH.getLanguage())) {
   // enServer.addBean(bean);
   // enServer.commit();
   // }
   // }

   public static void addUserDomains(Collection<SolrInputDocument> users)
         throws SolrServerException, IOException {

      SolrServer server = new CommonsHttpSolrServer(conf.getString("solr2.url") + PROB_CORE_ENG);

      server.add(users);
      server.commit();
   }

   public static void addBeanResource(AbstractResource resource) throws IOException,
         SolrServerException, Exception {
      if (resource.getrType().equals(RType.FACEBOOK_PROFILE)
            || resource.getrType().equals(RType.LINKEDIN_PROFILE)
            || resource.getrType().equals(RType.TWITTER_PROFILE)) {
         SolrServer it = getServer("it");
         it.addBean(resource);
         SolrServer en = getServer("en");
         en.addBean(resource);
      } else {
         SolrServer server = getServer(resource.getResourceLang());
         if (server != null) {
            server.addBean(resource);
         } else {
            log.debug("Unable to get the correct Solr Server for resource id: {}",
                  resource.getSolrId());
         }
      }
   }

   public static SolrServer getServer(String lang) throws IOException, SolrServerException,
         Exception {
      if ("it".equals(lang)) {
         if (itMergedServer == null) {
            itMergedServer = new StreamingUpdateSolrServer(conf.getString("solr.url")
                  + MERGED_ITALIAN_CORE, 1024, 8);
         }
         return itMergedServer;
      }
      if ("en".equals(lang)) {
         if (enMergedServer == null) {
            enMergedServer = new StreamingUpdateSolrServer(conf.getString("solr.url")
                  + MERGED_ENGLISH_CORE, 1024, 8);
         }
         return enMergedServer;
      } else
         return null;
   }

   public static SolrServer getQueryServer(String lang, String textType, String channel)
         throws IOException, SolrServerException, Exception {
      if ("it".equals(lang) && "profiles".equals(textType)) {
         if (itMergedServerProfiles == null) {
            itMergedServerProfiles = new CommonsHttpSolrServer(conf.getString("solr0.url")
                  + MERGED_ITALIAN_CORE);
         }
         return itMergedServerProfiles;
      }
      if ("en".equals(lang) && "profiles".equals(textType)) {
         if (enMergedServerProfiles == null) {
            enMergedServerProfiles = new CommonsHttpSolrServer(conf.getString("solr0.url")
                  + MERGED_ENGLISH_CORE);
         }
         return enMergedServerProfiles;
      }

      if ("it".equals(lang) && "textShort".equals(textType)) {
         if (itMergedServerShort == null) {
            itMergedServerShort = new CommonsHttpSolrServer(conf.getString("solr2.url")
                  + channel + MERGED_ITALIAN_CORE);
         }
         return itMergedServerShort;
      }
      if ("en".equals(lang) && "textShort".equals(textType)) {
         if (enMergedServerShort == null) {
            enMergedServerShort = new CommonsHttpSolrServer(conf.getString("solr2.url")
                  + channel + MERGED_ENGLISH_CORE);
         }
         return enMergedServerShort;
      }
      if ("it".equals(lang) && "text".equals(textType)) {
         if (itMergedServer == null) {
            itMergedServer = new CommonsHttpSolrServer(conf.getString("solr1.url") + channel
                  + MERGED_ITALIAN_CORE);
         }
         return itMergedServer;
      }
      if ("en".equals(lang) && "text".equals(textType)) {
         if (enMergedServer == null) {
            enMergedServer = new CommonsHttpSolrServer(conf.getString("solr1.url") + channel
                  + MERGED_ENGLISH_CORE);
         }
         return enMergedServer;
      }

      return null;
   }

   @Deprecated
   public static SolrServer getServer(Channel channel, String lang) throws IOException,
         SolrServerException, Exception {

      switch (channel) {
         case TWITTER:
            if ("it".equals(lang)) {
               if (itTwitterServer == null) {
                  itTwitterServer = new CommonsHttpSolrServer(conf.getString("solr.url")
                        + TWITTER_ITALIAN_CORE);
               }
               return itTwitterServer;
            }
            if ("en".equals(lang)) {
               if (enTwitterServer == null) {
                  enTwitterServer = new CommonsHttpSolrServer(conf.getString("solr.url")
                        + TWITTER_ENGLISH_CORE);
               }
               return enTwitterServer;
            } else
               return null;
         case FACEBOOK:
            if ("it".equals(lang)) {
               if (itFacebookServer == null) {
                  itFacebookServer = new CommonsHttpSolrServer(conf.getString("solr.url")
                        + FACEBOOK_ITALIAN_CORE);
               }
               return itFacebookServer;
            }
            if ("en".equals(lang)) {
               if (enFacebookServer == null) {
                  enFacebookServer = new CommonsHttpSolrServer(conf.getString("solr.url")
                        + FACEBOOK_ENGLISH_CORE);
               }
               return enFacebookServer;
            }
         case LINKEDIN:
            if ("it".equals(lang)) {
               if (itLinkedinServer == null) {
                  itLinkedinServer = new CommonsHttpSolrServer(conf.getString("solr.url")
                        + LINKEDIN_ITALIAN_CORE);
               }
               return itLinkedinServer;
            }
            if ("en".equals(lang)) {
               if (enLinkedinServer == null) {
                  enLinkedinServer = new CommonsHttpSolrServer(conf.getString("solr.url")
                        + LINKEDIN_ENGLISH_CORE);
               }
               return enLinkedinServer;
            }

         default:
            log.debug("Language {}", lang);
            return null;
      }

   }

   public static QueryResponse probQuery(String query) throws MalformedURLException,
         SolrServerException {
      SolrServer server = new CommonsHttpSolrServer(conf.getString("solr2.url") + PROB_CORE);
      SolrQuery solrQuery = new SolrQuery();
      solrQuery.setQueryType("/domains");
      solrQuery.setQuery(query);
      solrQuery.setFields("score", "id", "domains");
      solrQuery.setRows(60);
      return server.query(solrQuery);
   }

   // TODO test!!!
   public static QueryResponse entitiesOnlyQuery(String query, String lang, String textType,
         String rows, Object... filters) throws IOException, SolrServerException, Exception {
      String channel = "";
      if (filters.length > 0 && filters[0] instanceof Channel)
         switch ((Channel) filters[0]) {
            case FACEBOOK:
               channel = "Facebook";
               break;
            case TWITTER:
               channel = "Twitter";
               break;
            case LINKEDIN:
               channel = "Linkedin";
               break;
         }
      SolrServer server = getQueryServer(lang, textType, channel);
      if (server == null) {
         log.debug("Unable to get the correct Solr Server");
         return null;
      }
      Long queryRows;
      if (rows.contains("%")) {
         int percentage = Integer.parseInt(StringUtils.chop(rows));
         SolrQuery rowsQuery = new SolrQuery();
         rowsQuery.setQueryType("/entities");
         rowsQuery.set("qf", "entityIdList");
         rowsQuery.set("hl.fl", "entityIdList");
         rowsQuery.setFields("id");
         rowsQuery.setRows(1);
         rowsQuery.setQuery(query);
         queryRows = server.query(rowsQuery).getResults().getNumFound() * percentage / 100;
      } else
         queryRows = Long.parseLong(rows);
      SolrQuery solrQuery = new SolrQuery();
      solrQuery.setQueryType("/entities");

      solrQuery.setFields("id,rType,score");
      solrQuery.set("qf", "entityIdList");
      solrQuery.set("hl.fl", "entityIdList");
      solrQuery.setRows(queryRows.intValue());

      solrQuery.setQuery(query);

      return server.query(solrQuery);
   }

   public static QueryResponse entitiesQuery(String query, String lang, String textType,
         String rows, Object... filters) throws IOException, SolrServerException, Exception {
      String channel = "";
      if (filters.length > 0 && filters[0] instanceof Channel)
         switch ((Channel) filters[0]) {
            case FACEBOOK:
               channel = "Facebook";
               break;
            case TWITTER:
               channel = "Twitter";
               break;
            case LINKEDIN:
               channel = "Linkedin";
               break;
         }

      SolrServer server = getQueryServer(lang, textType, channel);
      if (server == null) {
         log.debug("Unable to get the correct Solr Server");
         return null;
      }
      Long queryRows;
      if (rows.contains("%")) {
         int percentage = Integer.parseInt(StringUtils.chop(rows));
         SolrQuery rowsQuery = new SolrQuery();
         rowsQuery.setQueryType("/entities");
         rowsQuery.set("qf", "entityIdList^" + conf.getString("entities.weight") + " "
               + "textShort^1.0");
         rowsQuery.set("hl.fl", "textShort,entityIdList");
         rowsQuery.setFields("id");
         rowsQuery.setRows(1);
         rowsQuery.setQuery(query);
         queryRows = server.query(rowsQuery).getResults().getNumFound() * percentage / 100;
      } else
         queryRows = Long.parseLong(rows);
      SolrQuery solrQuery = new SolrQuery();
      solrQuery.setQueryType("/entities");
      solrQuery.setFields("id,rType,score");
      
      solrQuery.set("qf", "entityIdList^" + conf.getString("entities.weight") + " "
            + "textShort^1.0");
      // solrQuery.set("qf", "entityIdList^" + conf.getString("entities.weight") + " "
      // + textType + "^1.0");
      solrQuery.set("hl.fl", "textShort,entityIdList");
      // solrQuery.set("hl.fl", textType + ",entityIdList");
      solrQuery.setRows(queryRows.intValue());

      solrQuery.setQuery(query);

      return server.query(solrQuery);
   }

   public static QueryResponse textQuery(String query, String lang, String textType,
         String rows, Object... filters) throws IOException, SolrServerException, Exception {
      String channel = "";
      if (filters.length > 0 && filters[0] instanceof Channel)
         switch ((Channel) filters[0]) {
            case FACEBOOK:
               channel = "Facebook";
               break;
            case TWITTER:
               channel = "Twitter";
               break;
            case LINKEDIN:
               channel = "Linkedin";
               break;
         }

      SolrServer server = getQueryServer(lang, textType, channel);
      if (server == null) {
         log.debug("Unable to get the correct Solr Server");
         return null;
      }
      Long queryRows;
      if (rows.contains("%")) {
         int percentage = Integer.parseInt(StringUtils.chop(rows));
         SolrQuery rowsQuery = new SolrQuery();
         rowsQuery.setQueryType("/text");
         rowsQuery.setFields("id");
         rowsQuery.setRows(1);
         rowsQuery.setQuery(query);
         queryRows = server.query(rowsQuery).getResults().getNumFound() * percentage / 100;
      } else
         queryRows = Long.parseLong(rows);
      SolrQuery solrQuery = new SolrQuery();
      solrQuery.setQueryType("/text");
      // if (textType.equals("text")) { // TODO togliere questa
      // solrQuery.set("qf", "text");
      // solrQuery.set("hl.fl", "text");
      // }
      solrQuery.setRows(queryRows.intValue());
      solrQuery.setQuery(query);
      solrQuery.setFields("id,rType,score");
      
      return server.query(solrQuery);
   }

   @Deprecated
   public static QueryResponse entitiesByTypeQuery(String query, Map<String, Float> domains,
         String lang, String textType, Object... filters) throws IOException,
         SolrServerException, Exception {
      String channel = "";
      if (filters.length > 0 && filters[0] instanceof Channel)
         switch ((Channel) filters[0]) {
            case FACEBOOK:
               channel = "Facebook";
               break;
            case TWITTER:
               channel = "Twitter";
               break;
            case LINKEDIN:
               channel = "Linkedin";
               break;
         }

      SolrServer server = getQueryServer(lang, textType, channel);
      if (server == null) {
         log.debug("Unable to get the correct Solr Server");
         return null;
      }

      StringBuilder qfBuilder = new StringBuilder((textType.equals("text")) ? "text^1.0"
            : "textShort^1.0");
      StringBuilder hlFlBuilder = new StringBuilder((textType.equals("text")) ? "text"
            : "textShort");
      for (String dom : domains.keySet()) {
         qfBuilder.append(" ").append(dom).append("_entitiesId^")
               .append(domains.get(dom) * 10);
         hlFlBuilder.append(",").append(dom).append("_entitiesId");
      }

      SolrQuery solrQuery = new SolrQuery();
      solrQuery.setQueryType("/entitiesByType");
      solrQuery.setParam("qf", qfBuilder.toString());
      solrQuery.setParam("hl.fl", hlFlBuilder.toString());
      solrQuery.setQuery(query);

      return server.query(solrQuery);
   }

   public static QueryResponse simpleQuery(String query, Channel channel, String lang,
         String queryType) throws IOException, SolrServerException, Exception {

      SolrServer server = getServer(channel, lang);
      if (server == null) {
         log.debug("Unable to get the correct Solr Server");
         return null;
      }

      SolrQuery solrQuery = new SolrQuery();
      solrQuery.setRows(50);
      solrQuery.setIncludeScore(true);
      solrQuery.setHighlight(true);
      solrQuery.setHighlightSnippets(5);
      solrQuery.setParam("hl.fl", "text");

      if ("dismax".equals(queryType)) {
         solrQuery.setParam("q", query);
         solrQuery.setParam("defType", queryType);
         solrQuery.setParam("qf", "text");
      } else {
         String q = "text:" + query.replace("?", "").replace("!", "").replace(" ", " text:");
         solrQuery.setParam("q", q);
      }

      return server.query(solrQuery);

   }

   public static void prova() {

      DB db = Facade.db;
      DBCollection fColl = db.getCollection("twitterUser");

      DBObject o = fColl.findOne(new BasicDBObject("screenName", "Matteosilv"));
      TwitterUser user = Converter.toObject(TwitterUser.class, o);

      List<Long> toFetch = new ArrayList<Long>();
      toFetch.addAll(user.getFollowing());
      toFetch.removeAll(user.getFollowers());
      List<String> toPrint = new ArrayList<String>();
      for (Long id : toFetch) {
         // if (id.equals(38001497L)) {
         DBObject pageDB = db.getCollection(Facade.TABLE_TWITTER).findOne(
               new BasicDBObject("_id", id));
         TwitterPage page = Converter.toObject(TwitterPage.class, pageDB);

         String toAdd = "Page: " + page.getName();
         try {
            String category = page.getAlchemyCategory();
            toAdd += " Category: " + category;
            toAdd += " Rank: " + page.getAlchemyCategoryScore();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         toAdd += " Language: " + page.getResourceLang();
         toPrint.add(toAdd);
         // }
      }

      for (String s : toPrint) {
         log.debug(s);
      }

      // SolrManager manager;
      // try {
      //
      // LanguageDetector detector = new LanguageDetector();
      // log.debug(detector.detect(page.getResourceText()));
      // manager = new SolrManager();
      // manager.addBeanDocument(page, "en");
      // log.debug("" + page.get_id());
      // log.debug(page.getResourceText());
      // } catch (IOException | SolrServerException e) {
      // e.printStackTrace();
      // }

      // String temp = page.getResourceText().replaceAll("\\s+", " ");
      // ResourceAnalyzer ra = new ResourceAnalyzer();
      // log.debug("length: {}", temp.length());
      // log.debug("Bytes: {}", temp.getBytes().length);
      // temp = temp.substring(0, 95000);
      // log.debug("STRING :{}", temp);
      // List<FreebaseUtils.Entity> result = ra.getDisambiguatedEntitiesFromString(temp,
      // "en");
      // log.debug("count: {}", result.size());
      // for (FreebaseUtils.Entity ent : result) {
      // try {
      // log.debug("Entity:" + ent.getName() + " Domain:" + ent.getDomains().get(0)
      // + "Type:" + ent.getTypes().get(0));
      //
      // } catch (Exception e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      // }
      // AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
      // try {
      // String temp = page.getResourceText().replaceAll("\\s+", " ");
      // log.debug(temp);
      // EntityResponse e = alchemyApi.HTMLGetRankedNamedEntities(temp, null);
      // log.debug(e.getStatus());
      // log.debug(e.getStatusInfo());
      // for (Entity ent : e.getEntities()) {
      // log.debug("Entity {}", ent.getText());
      // log.debug("Type {}", ent.getType());
      // try {
      // log.debug("Disambiguated {}", ent.getDisambiguated().getSubType().get(0));
      // } catch (Exception e1) {
      // // TODO Auto-generated catch block
      // log.error("paolo gay");
      // }
      // log.debug("Rank {}", ent.getRelevance());
      // }
      //
      // } catch (XPathExpressionException | IOException | SAXException
      // | ParserConfigurationException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
   }
}
