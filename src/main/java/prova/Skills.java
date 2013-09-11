/*
 * @(#)Skills.java   1.0   18/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import it.expertfinding.utils.Facade;
import it.expertfinding.utils.freebase.FreebaseUtils;

import java.net.URLDecoder;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Skills {

   private static Logger logSkills = LoggerFactory.getLogger("Skills");

   public static void main(String[] args) throws Exception {
      FreebaseUtils fb = new FreebaseUtils();
      DBCursor cur = Facade.db.getCollection("linkedinSkills")
            .find(new BasicDBObject("wikiUrl", new BasicDBObject("$ne", null)))
            .batchSize(10);
      while (cur.hasNext()) {
         DBObject skill = cur.next();

         String[] parts = URLDecoder.decode(skill.get("wikiUrl").toString(), "UTF-8").split(
               "/");
         String title = parts[parts.length - 1];
         title = fb.encodeFreeBase(title);
         Facade.log.debug("Disambiguating entity: {}", title);
         DBObject entity;
         try {
            entity = Converter.toDBObject(fb.disambiguateEntityFromWikiTitle(title, 10));

            Facade.log.debug("Disambiguated entity /wikipedia/en_id: {}", entity
                  .get("en_id").toString());
            Facade.db.getCollection("linkedinSkills").update(skill,
                  new BasicDBObject("$set", new BasicDBObject("entity", entity)));
         } catch (Exception e) {
            logSkills.debug("Unable to get entitities from skill title :{}", title);
         }
      }

   }
}
