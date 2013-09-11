/*
 * @(#)KeywordResponse.java   1.0   30/apr/2012
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

public class KeywordResponse {

   @Alchemy
   private List<Keyword> keywords = new ArrayList<Keyword>();

   /**
    * @return Returns the keywords.
    */
   public List<Keyword> getKeywords() {
      return this.keywords;
   }

   /**
    * @param keywords
    *           The keywords to set.
    */
   public void setKeywords(List<Keyword> keywords) {
      this.keywords = keywords;
   }

   public static class Keyword {

      @Alchemy
      private String text;
      @Alchemy
      private Double relevance;

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

   }
}
