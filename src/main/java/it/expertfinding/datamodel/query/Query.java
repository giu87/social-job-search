/*
 * @(#)Query.java   1.0   14/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.query;

import it.expertfinding.core.QueryProcessor;
import it.expertfinding.core.QueryProcessor.ProbQueryResponse;
import it.expertfinding.core.QueryProcessor.SimpleQueryResponse;
import it.expertfinding.core.ResourceAnalyzer;
import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.Constants.ResourceUserConnection;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.datamodel.users.CrowdUser;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.freebase.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

public class Query {

   private ObjectId _id;
   private String method;
   private String text;
   private String lan;
   private Map<String, Double> queryDomains = new HashMap<String, Double>();
   private Map<String, AnswerResourceMap> bestUsers;
   private Map<String, ProbQueryResponse> bestUsersProb;
   private String textType;
   private String entityType;
   private List<Channel> channels = new ArrayList<Channel>();
   private List<Entity> entities = new ArrayList<Entity>();
   private String rows;

   public Query() {
      super();
   }

   public Query(String text, String method, List<Channel> channels, String lan,
         String entityType, String textType, String rows) {
      this._id = new ObjectId();
      this.method = method;
      this.text = text;
      this.lan = lan;
      this.channels = channels;
      this.entityType = entityType;
      this.textType = textType;
      this.rows = rows;
      if ("yes".equals(entityType) || "entityOnly".equals(entityType))
         this.entities = ResourceAnalyzer.getDisambiguatedEntitiesFromString(text, lan);

      if (method.equals("textual"))
         calculateResults();
      else if (method.equals("probabilistic"))
         calculateResultsProb();
   }

   public String getLan() {
      return this.lan;
   }

   public void setLan(String lan) {
      this.lan = lan;
   }

   /**
    * @return Returns the rows.
    */
   public String getRows() {
      return this.rows;
   }

   /**
    * @param rows
    *           The rows to set.
    */
   public void setRows(String rows) {
      this.rows = rows;
   }

   /**
    * @return Returns the method.
    */
   public String getMethod() {
      return this.method;
   }

   /**
    * @param method
    *           The method to set.
    */
   public void setMethod(String method) {
      this.method = method;
   }

   public Map<String, AnswerResource> getAllResources() {

      Map<String, AnswerResource> map = new HashMap<String, AnswerResource>();
      for (AnswerResourceMap m : bestUsers.values()) {
         Facade.log.debug("size map of =  " + m.getMap().size());
         map.putAll(m.getMap());
      }
      return map;
   }

   public Map<String, AnswerResourceMap> getBestUsers() {
      return this.bestUsers;
   }

   public List<CrowdUser> getOrderedBestUsers(List<CrowdUser> users) {

      Map<CrowdUser, Double> tempMap = new HashMap<CrowdUser, Double>();
      for (CrowdUser u : users) {
         tempMap.put(u, getScoreFromResources(u.get_id().toString()));
      }

      @SuppressWarnings("unchecked")
      TreeMap<CrowdUser, Double> sortedMap = new TreeMap<CrowdUser, Double>(
            new ValueComparator(tempMap));
      sortedMap.putAll(tempMap);

      // List<CrowdUser> mapKeys = new ArrayList<CrowdUser>(tempMap.keySet());
      // List<Double> mapValues = new ArrayList<Double>(tempMap.values());
      // HashMap<CrowdUser, Double> sortedMap = new LinkedHashMap<CrowdUser, Double>();
      // TreeList<Double> sortedSet = new TreeList<Double>(mapValues);
      // Object[] sortedArray = sortedSet.toArray();
      // int size = sortedArray.length;
      // for (int i = size - 1; i >= 0; i--) {
      // sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),
      // (Double) sortedArray[i]);
      // }
      List<CrowdUser> uTemp = new ArrayList<CrowdUser>();

      Iterator<Entry<CrowdUser, Double>> i = sortedMap.entrySet().iterator();
      while (i.hasNext())
         uTemp.add(i.next().getKey());
      return uTemp;

   }

   public List<CrowdUser> getOrderedBestUsers() {

      List<CrowdUser> list = new ArrayList<CrowdUser>();
      if (method.equals("textual")) {
         Set<ObjectId> idSet = new HashSet<ObjectId>();
         for (String s : getBestUsers().keySet())
            idSet.add(new ObjectId(s));

         DBCursor c = Facade.db.getCollection(Facade.TABLE_USER).find(
               new BasicDBObject("_id", new BasicDBObject("$in", idSet)));
         while (c.hasNext())
            list.add(Converter.toObject(CrowdUser.class, c.next()));

         return getOrderedBestUsers(list);

      } else {
         for (String user : getBestUsersProb().keySet()) {

            list.add(Converter.toObject(
                  CrowdUser.class,
                  Facade.db.getCollection(Facade.TABLE_USER).findOne(
                        new BasicDBObject("_id", new ObjectId(user)))));
         }
         return list;
      }
   }

   public List<String> getOrderedBestUserIds() {

      List<String> ids = new ArrayList<String>();
      for (CrowdUser user : getOrderedBestUsers())
         ids.add(user.get_id().toString());

      return ids;
   }

   // @Deprecated
   // public Map<String, Double> getOrderedBestUsers() {
   //
   // Map<String, Double> tempMap = new HashMap<String, Double>();
   // for (String user : bestUsers.keySet()) {
   // tempMap.put(user, getScoreFromResources(user));
   // }
   //
   // List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
   // List<Double> mapValues = new ArrayList<Double>(tempMap.values());
   // HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
   // TreeSet<Double> sortedSet = new TreeSet<Double>(mapValues);
   // Object[] sortedArray = sortedSet.toArray();
   // int size = sortedArray.length;
   // for (int i = size - 1; i >= 0; i--) {
   // sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),
   // (Double) sortedArray[i]);
   // }
   // return sortedMap;
   // }

   public void setBestUsers(Map<String, AnswerResourceMap> bestUsers) {
      this.bestUsers = bestUsers;
   }

   /**
    * @return Returns the bestUsersProb.
    */
   public Map<String, ProbQueryResponse> getBestUsersProb() {
      return this.bestUsersProb;
   }

   /**
    * @param bestUsersProb
    *           The bestUsersProb to set.
    */
   public void setBestUsersProb(Map<String, ProbQueryResponse> bestUsersProb) {
      this.bestUsersProb = bestUsersProb;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   /**
    * @return Returns the textType.
    */
   public String getTextType() {
      return this.textType;
   }

   /**
    * @param textType
    *           The textType to set.
    */
   public void setTextType(String textType) {
      this.textType = textType;
   }

   /**
    * @return Returns the entities.
    */
   public String getEntityType() {
      return this.entityType;
   }

   /**
    * @param entities
    *           The entities to set.
    */
   public void setEntityType(String entityType) {
      this.entityType = entityType;
   }

   /**
    * @return Returns the channels.
    */
   public List<Channel> getChannels() {
      return this.channels;
   }

   /**
    * @param channels
    *           The channels to set.
    */
   public void setChannels(List<Channel> channels) {
      this.channels = channels;
   }

   /**
    * @return Returns the entities.
    */
   public List<Entity> getEntities() {
      return this.entities;
   }

   /**
    * @param entities
    *           The entities to set.
    */
   public void setEntities(List<Entity> entities) {
      this.entities = entities;
   }

   /**
    * @return Returns the queryDomains.
    */
   public Map<String, Double> getQueryDomains() {
      return this.queryDomains;
   }

   /**
    * @param queryDomains
    *           The queryDomains to set.
    */
   public void setQueryDomains(Map<String, Double> queryDomains) {
      this.queryDomains = queryDomains;
   }

   public List<AnswerResource> getOrderedResourcesOfUser(String user) {

      AnswerResourceComparator comparator = new AnswerResourceComparator();
      List<AnswerResource> temp = new ArrayList<AnswerResource>();
      temp.addAll(bestUsers.get(user).getMap().values());
      Collections.sort(temp, comparator);
      return temp;
   }

   public Double getScoreFromResources(String cu) {

      Double temp = 0D;
      for (AnswerResource r : bestUsers.get(cu).getMap().values())
         temp += r.getScore();
      return temp;
   }

   public void calculateResults() {

      Map<AbstractResource, SimpleQueryResponse> resultMap = new HashMap<AbstractResource, SimpleQueryResponse>();
      StringBuilder queryText;
      if (!entityType.equals("entityOnly"))
         queryText = new StringBuilder(text).append(" ");
      else
         queryText = new StringBuilder();
      for (Entity e : entities) {
         // if (e.getScore() > 0.1f) // TODO DA TOGLIERE PER RITORNARE ALLA CONFIGURAZIONE
         // BASE
         queryText.append(e.get_id().toString()).append(" ");
      }

      resultMap.putAll(QueryProcessor.processSimpleQuery(queryText.toString(), channels,
            textType, entityType, lan, rows));

      setBestUsers(getUsersFromResources(resultMap));
   }

   public void calculateResultsProb() {

      Map<String, Double> domainProbabilities = computeDomainProbability(entities);

      this.setQueryDomains(domainProbabilities);
      setBestUsersProb(QueryProcessor.processProbQuery(domainProbabilities));
   }

   public Map<String, Double> computeDomainProbability(List<Entity> entities) {
      Map<String, Double> domainProb = new HashMap<String, Double>();
      Set<String> allowedDomains = Facade.domains;

      Map<String, Double> normalizedTagMeScores = new HashMap<String, Double>();

      Double count = 0D;
      for (Entity e : entities) {
         String eKey = e.get_id().toString();
         Double eValue = e.getScore().doubleValue();
         if (!normalizedTagMeScores.containsKey(eKey))
            normalizedTagMeScores.put(eKey, eValue);
         else
            normalizedTagMeScores.put(eKey, normalizedTagMeScores.get(eKey) + eValue);
         count += eValue;

      }
      for (String key : normalizedTagMeScores.keySet()) {

         normalizedTagMeScores.put(key, (count != 0F) ? normalizedTagMeScores.get(key)
               / count : 0F);
         ObjectId id = new ObjectId(key);
         /* Get P(E|R) as TagMeScore */
         Double pER = normalizedTagMeScores.get(key);
         Double pDE;
         Entity e = MongoUtils.getFreebaseEntityById(id);
         /* Get the ordererd list of allowed domains for the entity */
         List<String> domainsList = new ArrayList<String>();
         for (String domain : e.getDomains())
            if (allowedDomains.contains(domain))
               domainsList.add(domain);
         Set<String> entityDomains = new HashSet<String>(domainsList);
         /* Compute the normalization factor */
         Double norm = 0d;
         for (int j = 2; j < domainsList.size() + 2; j++) {
            norm += (Math.log(2) / Math.log(j));
         }
         /* Compute P(D|E) */

         for (String domain : entityDomains) {
            Double temp = 0d;
            int i = 2;
            for (String actualDomain : domainsList) {
               if (actualDomain.equals(domain))
                  temp += (Math.log(2) / Math.log(i));
               i++;
            }
            Double prob = temp / norm;
            pDE = prob;
            if (domainProb.containsKey(domain))
               domainProb.put(domain, domainProb.get(domain) + pER * pDE);
            else
               domainProb.put(domain, pER * pDE);
            System.out.println("P(" + e.getName() + "|R) = "
                  + normalizedTagMeScores.get(key));

            System.out.println("P(" + domain + "|" + e.getName() + ") = " + pDE);
         }
      }
      return domainProb;

   }

   public Map<String, AnswerResourceMap> getUsersFromResources(
         Map<AbstractResource, SimpleQueryResponse> map) {

      Map<String, AnswerResourceMap> result = new HashMap<String, AnswerResourceMap>();

      for (AbstractResource r : map.keySet()) {

         Facade.log.debug("processing resource : " + r.getSolrId());
         Map<CrowdUser, List<ResourceUserConnection>> usersOfResource = MongoUtils
               .getCrowdUsersByResource(r, textType);

         for (CrowdUser user : usersOfResource.keySet()) {

            AnswerResource ar = new AnswerResource(r.getSolrId(), (r.getResourceText()
                  .length() < 50) ? r.getResourceText() : r.getResourceText().substring(0,
                  50), Constants.getConnectionScore(usersOfResource.get(user))
                  * map.get(r).getScore(), map.get(r).getHighlights(), map.get(r)
                  .getEntityHighlights());

            if (result.containsKey(user.get_id().toString()))
               result.get(user.get_id().toString()).getMap()
                     .put(StringUtils.chop(r.getSolrId()), ar);
            else {
               AnswerResourceMap temp = new AnswerResourceMap();
               temp.getMap().put(StringUtils.chop(r.getSolrId()), ar);
               result.put(user.get_id().toString(), temp);
            }
         }
      }
      return result;
   }

   public ObjectId get_id() {
      return this._id;
   }

   public void set_id(ObjectId _id) {
      this._id = _id;
   }

   public static class AnswerResourceMap {

      Map<String, AnswerResource> map = new HashMap<String, AnswerResource>();

      public Map<String, AnswerResource> getMap() {
         return this.map;
      }

      public void setMap(Map<String, AnswerResource> map) {
         this.map = map;
      }
   }

   @SuppressWarnings("rawtypes")
   public static class ValueComparator implements Comparator {

      Map base;

      public ValueComparator(Map base) {
         this.base = base;
      }

      @Override
      public int compare(Object a, Object b) {

         if ((Double) base.get(a) < (Double) base.get(b)) {
            return 1;
         } else if ((Double) base.get(a) == (Double) base.get(b)) {
            return -1;
         } else {
            return -1;
         }
      }

   }
}
