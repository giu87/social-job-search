/*
 * @(#)Entity.java   1.0   11/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.freebase;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Entity {

   private String name;
   private ObjectId _id;
   private String it_id;
   private String en_id;
   private String it_title;
   private String en_title;
   private List<String> types = new ArrayList<String>();
   private List<String> domains = new ArrayList<String>();
   private Location geo;
   private Float score;
   private String spot;

   /**
    * @return Returns the name.
    */
   public String getName() {
      return this.name;
   }

   /**
    * @param name
    *           The name to set.
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * @return Returns the id.
    */
   public ObjectId get_id() {
      return this._id;
   }

   /**
    * @param id
    *           The id to set.
    */
   public void set_id(ObjectId _id) {
      this._id = _id;
   }

   /**
    * @return Returns the types.
    */
   public List<String> getTypes() {
      return this.types;
   }

   /**
    * @param types
    *           The types to set.
    */
   public void setTypes(List<String> types) {
      this.types = types;
   }

   /**
    * @return Returns the domains.
    */
   public List<String> getDomains() {
      return this.domains;
   }

   /**
    * @param domains
    *           The domains to set.
    */
   public void setDomains(List<String> domains) {
      this.domains = domains;
   }

   /**
    * @return Returns the it_id.
    */
   public String getIt_id() {
      return this.it_id;
   }

   /**
    * @param it_id
    *           The it_id to set.
    */
   public void setIt_id(String it_id) {
      this.it_id = it_id;
   }

   /**
    * @return Returns the en_id.
    */
   public String getEn_id() {
      return this.en_id;
   }

   /**
    * @param en_id
    *           The en_id to set.
    */
   public void setEn_id(String en_id) {
      this.en_id = en_id;
   }

   /**
    * @return Returns the it_title.
    */
   public String getIt_title() {
      return this.it_title;
   }

   /**
    * @param it_title
    *           The it_title to set.
    */
   public void setIt_title(String it_title) {
      this.it_title = it_title;
   }

   /**
    * @return Returns the en_title.
    */
   public String getEn_title() {
      return this.en_title;
   }

   /**
    * @param en_title
    *           The en_title to set.
    */
   public void setEn_title(String en_title) {
      this.en_title = en_title;
   }

   /**
    * @return Returns the geo.
    */
   public Location getGeo() {
      return this.geo;
   }

   /**
    * @param geo
    *           The geo to set.
    */
   public void setGeo(Location geo) {
      this.geo = geo;
   }

   /**
    * @return Returns the score.
    */
   public Float getScore() {
      return this.score;
   }

   /**
    * @param score
    *           The score to set.
    */
   public Entity addScore(Float score) {
      this.score = score;
      return this;
   }

   /**
    * @return Returns the spot.
    */
   public String getSpot() {
      return this.spot;
   }

   /**
    * @param spot
    *           The spot to set.
    */
   public Entity addSpot(String spot) {
      this.spot = spot;
      return this;
   }

   public static class Location {

      private Double latitude;
      private Double longitude;

      public Location() {

      }

      public Location(Double latitude, Double longitude) {
         super();
         this.latitude = latitude;
         this.longitude = longitude;
      }

      /**
       * @return Returns the latitude.
       */
      public Double getLatitude() {
         return this.latitude;
      }

      /**
       * @return Returns the longitude.
       */
      public Double getLongitude() {
         return this.longitude;
      }

      /**
       * @param latitude
       *           The latitude to set.
       */
      public void setLatitude(Double latitude) {
         this.latitude = latitude;
      }

      /**
       * @param longitude
       *           The longitude to set.
       */
      public void setLongitude(Double longitude) {
         this.longitude = longitude;
      }

   }

}
