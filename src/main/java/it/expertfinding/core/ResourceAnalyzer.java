/*
 * @(#)ResourceAnalyzer.java   1.0   02/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.core;

import it.expertfinding.utils.Facade;
import it.expertfinding.utils.freebase.Entity;
import it.expertfinding.utils.freebase.FreebaseUtils;
import it.expertfinding.utils.tagme.TagMeAnnotation;
import it.expertfinding.utils.tagme.TagMeUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceAnalyzer {

   protected static Logger log = LoggerFactory.getLogger("Expertfinding");
   protected static Configuration conf = Facade.conf;
   protected static FreebaseUtils fb = new FreebaseUtils();

   public static void main(String[] args) throws Exception {
      // getDisambiguatedEntitiesFromString("Which Basketball Players will most likely be in Team USA for 2012 Olympics");
      // getDisambiguatedEntitiesFromString(
      // "Quali giocatori di basketball saranno pi probabilmente nella nazionale americana per le olimpiadi 2012",
      // "it");
      for (Entity e : getDisambiguatedEntitiesFromString("Which Basketball Players will most likely be in Team USA for 2012 Olympics")) {
         log.debug(e.getEn_id());
         log.debug(e.getName());
         log.debug(e.getTypes().get(0));
         log.debug(e.getDomains().get(0));
      }

   }

   public static List<Entity> getDisambiguatedEntitiesFromString(String text) {
      return getDisambiguatedEntitiesFromString(text, null);
   }

   public static List<Entity> getDisambiguatedEntitiesFromString(String text, String lang) {
      if (text != null && !text.equals("")) {
         List<TagMeAnnotation> annotations = TagMeUtils.getNamedEntities(text, lang, 0f);
         List<Entity> entities = new ArrayList<Entity>();
         if (annotations != null)
            for (TagMeAnnotation annotation : annotations) {

               try {
                  log.debug("Retrieving entity wiki id {}", annotation.getId());
                  if (lang == null)
                     entities.add(fb.disambiguateEntityFromWikiId(annotation.getId(), 15)
                           .addScore(annotation.getRho()).addSpot(annotation.getSpot()));
                  else
                     entities.add(fb
                           .disambiguateEntityFromWikiId(annotation.getId(), lang, 15)
                           .addScore(annotation.getRho()).addSpot(annotation.getSpot()));
               } catch (Exception e) {
                  log.error("Unable to retrieve entity", e);
               }
            }
         return entities;
      }
      return new ArrayList<Entity>();
   }
}
