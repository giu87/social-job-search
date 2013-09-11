/*
 * @(#)ResourceDomainProbabilities.java   1.0   06/giu/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.freebase.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.DBObject;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResourceDomainProbabilities implements Runnable {

   private AbstractResource resource;
   private static Logger log = LoggerFactory.getLogger("Solr");
   
   @SuppressWarnings("unchecked")
   public ResourceDomainProbabilities(AbstractResource resource, DBObject resourceOb) {

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
   }
   
   public static Map<String, Double> computeDomainProbability(AbstractResource ar) {

      Map<String, Double> domainProb = new HashMap<String, Double>();
      Set<String> allowedDomains = Facade.domains;

      Map<String, Double> normalizedTagMeScores = new HashMap<String, Double>();

      Double count = 0D;
      for (String eText : ar.getTagMeEntitiesIdList()) {
         String eKey= eText.split("\\|")[0];
         Double eValue = Double.parseDouble(eText.split("\\|")[1]);
         if (!normalizedTagMeScores.containsKey(eKey))
            normalizedTagMeScores.put(eKey, eValue);
         else
            normalizedTagMeScores.put(
                  eKey, normalizedTagMeScores.get(eKey)
                        + eValue);
         count += eValue;
      }

      for (String key : normalizedTagMeScores.keySet()) {

         normalizedTagMeScores.put(key, (count != 0F) ? normalizedTagMeScores.get(key) / count : 0F);
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
      
      Object id = (ar.getChannel().equals(Channel.TWITTER)) ? Long
            .parseLong(StringUtils.chop(ar.getSolrId())) : StringUtils.chop(ar.getSolrId());
            
      MongoUtils.updateDomainProbability(ar.getrType(), domainProb, id);
      return domainProb;
   }

   @Override
   public void run() {

      if (Crawler.isStop()) {
         return;
      }
      log.debug("Analyzing resource "+resource.getSolrId());
      computeDomainProbability(resource);
   }
}
