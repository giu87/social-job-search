/*
 * @(#)ResourceToBeAnalyzed.java   1.0   08/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import it.expertfinding.core.SolrManager;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.datamodel.resources.twitter.Tweet;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.freebase.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.solr.client.solrj.SolrServerException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceToBeAnalyzed implements Runnable {

   public static void main(String[] args) {

      DBObject ob = Facade.db.getCollection("twitter").findOne(
            new BasicDBObject("_id", 17821590355L));
      Tweet howImet = Converter.toObject(Tweet.class, ob);
      howImet.setTagMeEntitiesIdList((List<String>) ob.get("tagMeEntitiesIdList"));
      Map<String, Double> probabilities = computeDomainProbability(howImet);
      System.out.println("ciao");

   }

   private final AbstractResource resource;
   private static Logger log = LoggerFactory.getLogger("Solr");

   @SuppressWarnings("unchecked")
   public ResourceToBeAnalyzed(AbstractResource resource, DBObject resourceOb) {

      this.resource = resource;
      this.resource.setTagMeEntities((Map<String, List<String>>) resourceOb
            .get("tagMeEntities"));
      this.resource.setTagMeEntitiesId((Map<String, List<String>>) resourceOb
            .get("tagMeEntitiesId"));
      this.resource.setTagMeEntitiesSpot((Map<String, List<String>>) resourceOb
            .get("tagMeEntitiesSpot"));
      this.resource.setTagMeEntitiesList((List<String>) resourceOb.get("tagMeEntitiesList"));
      this.resource.setTagMeEntitiesIdList((List<String>) resourceOb
            .get("tagMeEntitiesIdList"));
      this.resource.setTagMeEntitiesSpotList((List<String>) resourceOb
            .get("tagMeEntitiesSpotList"));
      // this.resource.setTagMeEntitiesIdListMongo((List<DBObject>) resourceOb
      // .get("tagMeEntitiesIdListMongo"));
   }

   public static Map<String, Double> computeDomainProbability(AbstractResource ar) {

      Map<String, Double> domainProb = new HashMap<String, Double>();
      Set<String> allowedDomains = Facade.domains;

      Map<String, Double> normalizedTagMeScores = new HashMap<String, Double>();

      Double count = 0D;
      for (String eText : ar.getTagMeEntitiesIdList()) {
         if (!normalizedTagMeScores.containsKey(eText.split("\\|")[0]))
            normalizedTagMeScores.put(eText.split("\\|")[0],
                  Double.parseDouble(eText.split("\\|")[1]));
         else
            normalizedTagMeScores.put(
                  eText.split("\\|")[0],
                  normalizedTagMeScores.get(eText.split("\\|")[0])
                        + Double.parseDouble(eText.split("\\|")[1]));
         count += Double.parseDouble(eText.split("\\|")[1]);
      }

      for (String key : normalizedTagMeScores.keySet()) {

         normalizedTagMeScores.put(key, normalizedTagMeScores.get(key) / count);

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

   @Override
   public void run() {

      if (Crawler.isStop()) {
         return;
      }

      try {

         resource.getResourceText();
         resource.getAlchemyCategory();
         resource.getResourceLang();
         resource.getTagMeEntities();
         // Map<String, Float> r = computeDomainProbability(resource);
         // log.debug("c");
      } catch (AlchemyLimitException e) {
         log.debug("Setting stop variable");
         System.exit(1);
         Crawler.setStop();
      } catch (Exception e) {
         e.printStackTrace();
         log.debug("exception");
      }

      try {
         SolrManager.addBeanResource(resource);
      } catch (IOException e) {
         log.debug("Problem with resource id", resource.getSolrId());
         log.error("Error", e);
      } catch (SolrServerException e) {
         log.debug("Problem with resource id", resource.getSolrId());
         log.error("Error", e);
      } catch (Exception e) {
         log.debug("Problem with resource id", resource.getSolrId());
         log.error("Error", e);
      }

   }
}
