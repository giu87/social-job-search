/*
 * @(#)ConceptResponse.java   1.0   30/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.alchemy;

import java.util.ArrayList;
import java.util.List;

public class ConceptResponse extends AlchemyResponse {

   private List<Concept> concepts = new ArrayList<Concept>();

   /**
    * @return Returns the concepts.
    */
   public List<Concept> getConcepts() {
      return this.concepts;
   }

   /**
    * @param concepts
    *           The concepts to set.
    */
   public void setConcepts(List<Concept> concepts) {
      this.concepts = concepts;
   }

   public static class Concept {

      @Alchemy
      private String text;
      @Alchemy
      private Double relevance;
      @Alchemy
      private String website;
      @Alchemy
      private String dbpedia;
      @Alchemy
      private String freebase;
      @Alchemy
      private String ciaFactbook;
      @Alchemy
      private String opencyc;
      @Alchemy
      private String yago;
      @Alchemy
      private String geonames;
      @Alchemy
      private String census;
      @Alchemy
      private String umbel;
      @Alchemy
      private String geo;
      @Alchemy
      private String musicBrainz;
      @Alchemy
      private String crunchbase;
      @Alchemy
      private String semanticCrunchbase;

      /**
       * @return Returns the text.
       */
      public String getText() {
         return this.text;
      }

      /**
       * @return Returns the relevance.
       */
      public Double getRelevance() {
         return this.relevance;
      }

      /**
       * @return Returns the website.
       */
      public String getWebsite() {
         return this.website;
      }

      /**
       * @return Returns the dbpedia.
       */
      public String getDbpedia() {
         return this.dbpedia;
      }

      /**
       * @return Returns the freebase.
       */
      public String getFreebase() {
         return this.freebase;
      }

      /**
       * @return Returns the ciaFactbook.
       */
      public String getCiaFactbook() {
         return this.ciaFactbook;
      }

      /**
       * @return Returns the opencyc.
       */
      public String getOpencyc() {
         return this.opencyc;
      }

      /**
       * @return Returns the yago.
       */
      public String getYago() {
         return this.yago;
      }

      /**
       * @return Returns the geonames.
       */
      public String getGeonames() {
         return this.geonames;
      }

      /**
       * @return Returns the census.
       */
      public String getCensus() {
         return this.census;
      }

      /**
       * @return Returns the umbel.
       */
      public String getUmbel() {
         return this.umbel;
      }

      /**
       * @return Returns the geo.
       */
      public String getGeo() {
         return this.geo;
      }

      /**
       * @return Returns the musicBrainz.
       */
      public String getMusicBrainz() {
         return this.musicBrainz;
      }

      /**
       * @return Returns the crunchbase.
       */
      public String getCrunchbase() {
         return this.crunchbase;
      }

      /**
       * @return Returns the semanticCrunchbase.
       */
      public String getSemanticCrunchbase() {
         return this.semanticCrunchbase;
      }

      /**
       * @param text
       *           The text to set.
       */
      public void setText(String text) {
         this.text = text;
      }

      /**
       * @param relevance
       *           The relevance to set.
       */
      public void setRelevance(Double relevance) {
         this.relevance = relevance;
      }

      /**
       * @param website
       *           The website to set.
       */
      public void setWebsite(String website) {
         this.website = website;
      }

      /**
       * @param dbpedia
       *           The dbpedia to set.
       */
      public void setDbpedia(String dbpedia) {
         this.dbpedia = dbpedia;
      }

      /**
       * @param freebase
       *           The freebase to set.
       */
      public void setFreebase(String freebase) {
         this.freebase = freebase;
      }

      /**
       * @param ciaFactbook
       *           The ciaFactbook to set.
       */
      public void setCiaFactbook(String ciaFactbook) {
         this.ciaFactbook = ciaFactbook;
      }

      /**
       * @param opencyc
       *           The opencyc to set.
       */
      public void setOpencyc(String opencyc) {
         this.opencyc = opencyc;
      }

      /**
       * @param yago
       *           The yago to set.
       */
      public void setYago(String yago) {
         this.yago = yago;
      }

      /**
       * @param geonames
       *           The geonames to set.
       */
      public void setGeonames(String geonames) {
         this.geonames = geonames;
      }

      /**
       * @param census
       *           The census to set.
       */
      public void setCensus(String census) {
         this.census = census;
      }

      /**
       * @param umbel
       *           The umbel to set.
       */
      public void setUmbel(String umbel) {
         this.umbel = umbel;
      }

      /**
       * @param geo
       *           The geo to set.
       */
      public void setGeo(String geo) {
         this.geo = geo;
      }

      /**
       * @param musicBrainz
       *           The musicBrainz to set.
       */
      public void setMusicBrainz(String musicBrainz) {
         this.musicBrainz = musicBrainz;
      }

      /**
       * @param crunchbase
       *           The crunchbase to set.
       */
      public void setCrunchbase(String crunchbase) {
         this.crunchbase = crunchbase;
      }

      /**
       * @param semanticCrunchbase
       *           The semanticCrunchbase to set.
       */
      public void setSemanticCrunchbase(String semanticCrunchbase) {
         this.semanticCrunchbase = semanticCrunchbase;
      }

   }
}
