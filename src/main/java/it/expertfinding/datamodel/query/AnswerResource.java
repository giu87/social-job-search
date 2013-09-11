/*
 * @(#)AnswerResource.java   1.0   14/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.query;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

public class AnswerResource {

   private String id;
   private String shortText;
   private Double score;
   private List<String> highlights = new ArrayList<String>();
   private List<String> entityHighlights = new ArrayList<String>();

   public AnswerResource() {
   }

   public AnswerResource(String id, String shortText, Double score, List<String> highlights,
         List<String> entityHighlights) {
      super();
      this.id = id;
      this.shortText = Jsoup.parse(shortText).text();
      this.score = score;
      this.highlights = highlights;
      this.entityHighlights = entityHighlights;
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
   public void setId(String id) {
      this.id = id;
   }

   /**
    * @return Returns the shortText.
    */
   public String getShortText() {
      return this.shortText;
   }

   /**
    * @param shortText
    *           The shortText to set.
    */
   public void setShortText(String shortText) {
      this.shortText = shortText;
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
