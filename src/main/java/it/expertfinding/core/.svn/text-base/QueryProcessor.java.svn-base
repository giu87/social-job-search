/*
 * @(#)QueryProcessor.java   1.0   10/mag/2012
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
import it.expertfinding.datamodel.query.Query.ValueComparator;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.utils.MongoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryProcessor {

   public static void main(String[] args) {
      // Map<AbstractResource, SimpleQueryResponse> result = processSimpleQuery(
      // "I am looking for a graphic card to play skyrim and other high graphic games but i don't want spend too much. what do you suggest?",
      // Channel.TWITTER, "en", null);
      // log.debug("Count {}", result.keySet().size());
      // for (AbstractResource r : result.keySet()) {
      // log.debug("Id {}", r.getSolrId());
      // log.debug("Category {}", r.getAlchemyCategory());
      // log.debug("Score {}", result.get(r));
      // System.out.println();
      // }
   }

   // protected static final Float THRESHOLD = 0.3f;
   protected static Logger log = LoggerFactory.getLogger("Expertfinding");

   public static Map<String, ProbQueryResponse> processProbQuery(
         Map<String, Double> domainProbabilities) {

      StringBuilder queryBuilder = new StringBuilder();
      for (Entry<String, Double> domain : domainProbabilities.entrySet()) {
         queryBuilder.append("domains:").append(domain.getKey()).append("^")
               .append(domain.getValue().floatValue()).append(" ");
      }
      String queryString = StringUtils.chomp(queryBuilder.toString());
      QueryResponse response = null;
      Map<String, ProbQueryResponse> resultMap = new LinkedHashMap<String, ProbQueryResponse>();

      try {
         response = SolrManager.probQuery(queryString);

         SolrDocumentList results = response.getResults();
         for (SolrDocument result : results) {
            String id = (String) result.get("id");
            Double score = ((Float) result.get("score")).doubleValue();
            String domains = (String) result.getFirstValue("domains");

            Map<String, Double> userDomains = new HashMap<String, Double>();

            String[] domainsArray = domains.split(" ");
            for (String domain : domainsArray) {
               String[] dNV = domain.split("\\|");
               if (domainProbabilities.keySet().contains(dNV[0])) {
                  userDomains.put(dNV[0], Double.parseDouble(dNV[1]));
               }
            }
            @SuppressWarnings("unchecked")
            Map<String, Double> orderedUserDomains = new TreeMap<String, Double>(
                  new ValueComparator(userDomains));
            orderedUserDomains.putAll(userDomains);

            ProbQueryResponse userResp = new ProbQueryResponse(score, orderedUserDomains);
            resultMap.put(id, userResp);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return resultMap;

   }

   public static Map<AbstractResource, SimpleQueryResponse> processSimpleQuery(String query,
         List<Channel> channels, String textType, String entityType, String lang, String rows) {

      try {
         Map<AbstractResource, SimpleQueryResponse> resultMap = new HashMap<AbstractResource, SimpleQueryResponse>();
         QueryResponse response = null;

         switch (entityType) {
            case "no":
               response = SolrManager.textQuery(query, lang, textType, rows,
                     channels.toArray());
               break;
            case "yes":
               response = SolrManager.entitiesQuery(query, lang, textType, rows,
                     channels.toArray());
               break;
            case "entityOnly":
               response = SolrManager.entitiesOnlyQuery(query, lang, textType, rows,
                     channels.toArray());
               break;
            case "bd":
               throw new NotImplementedException();
         }

         SolrDocumentList results = response.getResults();
         Map<String, Map<String, List<String>>> highlightsMap = response.getHighlighting();
         if (results.size() > 0) {
            Double maxScore = ((Float) results.get(0).get("score")).doubleValue();
            for (SolrDocument result : results) {
               String id = (String) result.get("id");
               String choppedid = StringUtils.chop(id);

               resultMap.put(MongoUtils.getResourceById(choppedid,
                     RType.valueOf((String) result.get("rType"))),
                     new SimpleQueryResponse(((Float) result.get("score")).doubleValue()
                           / maxScore, highlightsMap.get(id).get(textType), highlightsMap
                           .get(id).get("entityIdList")));
            }
         }
         return resultMap;

      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;
   }

   public static class ProbQueryResponse {

      Double score;
      Map<String, Double> domains = new HashMap<String, Double>();

      public ProbQueryResponse() {

      }

      public ProbQueryResponse(Double score, Map<String, Double> domains) {
         super();
         this.score = score;
         this.domains = domains;
      }

      /**
       * @return Returns the score.
       */
      public Double getScore() {
         return this.score;
      }

      /**
       * @param score
       *           The score to set.
       */
      public void setScore(Double score) {
         this.score = score;
      }

      /**
       * @return Returns the domains.
       */
      public Map<String, Double> getDomains() {
         return this.domains;
      }

      /**
       * @param domains
       *           The domains to set.
       */
      public void setDomains(Map<String, Double> domains) {
         this.domains = domains;
      }

   }

   public static class SimpleQueryResponse {

      Double score;
      List<String> highlights = new ArrayList<String>();
      List<String> entityHighlights = new ArrayList<String>();

      public SimpleQueryResponse() {

      }

      public SimpleQueryResponse(Double score, List<String> highlights,
            List<String> entityHighlights) {
         super();
         this.score = score;
         this.highlights = highlights;
         this.entityHighlights = entityHighlights;
      }

      public Double getScore() {
         return this.score;
      }

      public void setScore(Double score) {
         this.score = score;
      }

      public List<String> getHighlights() {
         return this.highlights;
      }

      public void setHighlights(List<String> highlights) {
         this.highlights = highlights;
      }

      /**
       * @return Returns the entityHighlights.
       */
      public List<String> getEntityHighlights() {
         return this.entityHighlights;
      }

      /**
       * @param entityHighlights
       *           The entityHighlights to set.
       */
      public void setEntityHighlights(List<String> entityHighlights) {
         this.entityHighlights = entityHighlights;
      }

   }

}
