/*
 * @(#)FreebaseUtils.java   1.0   29/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.freebase;

import static com.freebase.json.JSON.a;
import static com.freebase.json.JSON.o;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.freebase.Entity.Location;

import java.net.URLDecoder;
import java.util.List;

import com.freebase.api.Freebase;
import com.freebase.json.JSON;

import org.apache.commons.configuration.Configuration;
import org.bson.types.ObjectId;
import org.slf4j.Logger;

public class FreebaseUtils {

   protected static Logger log = Facade.log;
   protected static Configuration conf = Facade.conf;
   private final Freebase freebase;

   public static void main(String[] args) throws Exception {
      FreebaseUtils fb = new FreebaseUtils();
      // Location loc = fb.getGeolocationFromWiki("Ascoli_Piceno");
      // log.debug("latitude: " + loc.getLatitude());
      // log.debug("longitude: " + loc.getLongitude());
      // Entity e1 = fb.disambiguateEntityFromWikiId("5645512", 10);

      // Entity e2 = fb.disambiguateEntityFromWikiId("15881", "it", 10);
      // System.out.println(e1);
      //
      // log.debug(e1.getName());
      // for (String type : e1.getTypes()) {
      // log.debug(type);
      // }

      log.debug(URLDecoder.decode(
            "http%3A%2F%2Fen%2Ewikipedia%2Eorg%2Fwiki%2FCustomer_service", "UTF-8"));
      log.debug(fb.encodeFreeBase("C_Sharp_,(programming_language)"));
   }

   public FreebaseUtils() {
      super();
      this.freebase = Freebase.getFreebase();
   }

   public String encodeFreeBase(String toEncode) {
      return yago2.normalize.Normalize.entity(toEncode).replace("\\u", "$")
            .replace("$002d", "-");
   }

   public Entity disambiguateEntityFromWikiTitle(String wikiTitle, int typesNumber)
         throws Exception {
      return disambiguateEntityFromWikiTitle(wikiTitle, "en", typesNumber);
   }

   @SuppressWarnings("unchecked")
   public Entity disambiguateEntityFromWikiTitle(String wikiTitle, String lang,
         int typesNumber) throws Exception {

      JSON query = o("id", "/wikipedia/" + lang + "_title/" + wikiTitle, "type",
            "/common/topic", "name", null, "notable_types", a(o("optional", true)),
            "it_id:key",
            o("namespace", "/wikipedia/it_id", "value", null, "optional", true),
            "en_id:key",
            o("namespace", "/wikipedia/en_id", "value", null, "optional", true),
            "it_title:key",
            o("namespace", "/wikipedia/it_title", "value", null, "optional", true),
            "en_title:key",
            o("namespace", "/wikipedia/en_title", "value", null, "optional", true),
            "/location/location/geolocation",
            o("latitude", null, "longitude", null, "optional", true));
      JSON envelope = o("extended", 1);
      JSON params = o();
      Entity local = MongoUtils.getFreebaseEntityByTitle(wikiTitle, lang);
      if (local != null) {
         log.debug("Entity stored locally");
         return local;
      }
      JSON result = freebase.mqlread(query, envelope, params);
      log.debug(result.toString());

      Entity e = new Entity();
      if (result.get("result").get("en_id:key") != null)
         e.setEn_id(result.get("result").get("en_id:key").get("value").string());
      if (result.get("result").get("it_id:key") != null)
         e.setIt_id(result.get("result").get("it_id:key").get("value").string());
      if (result.get("result").get("en_title:key") != null)
         e.setEn_title(result.get("result").get("en_title:key").get("value").string());
      if (result.get("result").get("it_title:key") != null)
         e.setIt_title(result.get("result").get("it_title:key").get("value").string());
      if (result.get("result").get("/location/location/geolocation") != null)
         e.setGeo(new Location(result.get("result").get("/location/location/geolocation")
               .get("latitude").number().doubleValue(), result.get("result")
               .get("/location/location/geolocation").get("longitude").number()
               .doubleValue()));

      e.setName(result.get("result").get("name").string());
      List<JSON> typesJSON = result.get("result").get("notable_types").array();
      int max = (typesNumber <= typesJSON.size()) ? typesNumber : typesJSON.size();
      for (int i = 0; i < max; i++) {
         JSON type = typesJSON.get(i);
         e.getTypes().add(type.get("name").string());
         e.getDomains().add(type.get("id").string().split("/")[1]);
         e.set_id(new ObjectId());
      }

      MongoUtils.saveFreebaseEntity(e);
      return e;

   }

   public Entity disambiguateEntityFromWikiId(String wikiId, int typesNumber)
         throws Exception {
      return disambiguateEntityFromWikiId(wikiId, "en", typesNumber);
   }

   @SuppressWarnings("unchecked")
   public Entity disambiguateEntityFromWikiId(String wikiId, String lang, int typesNumber)
         throws Exception {

      JSON query = o("id", "/wikipedia/" + lang + "_id/" + wikiId, "type", "/common/topic",
            "name", null, "notable_types", a(o("optional", true)), "it_id:key",
            o("namespace", "/wikipedia/it_id", "value", null, "optional", true),
            "en_id:key",
            o("namespace", "/wikipedia/en_id", "value", null, "optional", true),
            "it_title:key",
            o("namespace", "/wikipedia/it_title", "value", null, "optional", true),
            "en_title:key",
            o("namespace", "/wikipedia/en_title", "value", null, "optional", true),
            "/location/location/geolocation",
            o("latitude", null, "longitude", null, "optional", true));
      JSON envelope = o("extended", 1);
      JSON params = o();
      Entity local = MongoUtils.getFreebaseEntity(wikiId, lang);
      if (local != null) {
         log.debug("Entity stored locally");
         return local;
      }
      JSON result = freebase.mqlread(query, envelope, params);
      log.debug(result.toString());

      Entity e = new Entity();
      if (result.get("result").get("en_id:key") != null)
         e.setEn_id(result.get("result").get("en_id:key").get("value").string());
      if (result.get("result").get("it_id:key") != null)
         e.setIt_id(result.get("result").get("it_id:key").get("value").string());
      if (result.get("result").get("en_title:key") != null)
         e.setEn_title(result.get("result").get("en_title:key").get("value").string());
      if (result.get("result").get("it_title:key") != null)
         e.setIt_title(result.get("result").get("it_title:key").get("value").string());
      if (result.get("result").get("/location/location/geolocation") != null)
         e.setGeo(new Location(result.get("result").get("/location/location/geolocation")
               .get("latitude").number().doubleValue(), result.get("result")
               .get("/location/location/geolocation").get("longitude").number()
               .doubleValue()));

      e.setName(result.get("result").get("name").string());
      List<JSON> typesJSON = result.get("result").get("notable_types").array();
      int max = (typesNumber <= typesJSON.size()) ? typesNumber : typesJSON.size();
      for (int i = 0; i < max; i++) {
         JSON type = typesJSON.get(i);
         e.getTypes().add(type.get("name").string());
         e.getDomains().add(type.get("id").string().split("/")[1]);
         e.set_id(new ObjectId());
      }

      MongoUtils.saveFreebaseEntity(e);
      return e;

   }

}
