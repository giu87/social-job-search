/*
 * @(#)LanguageDetector.java   1.0   01/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.Sets;

import org.apache.commons.configuration.Configuration;
import org.apache.tika.language.LanguageIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LanguageDetector {

   protected static Logger log = LoggerFactory.getLogger("Expertfinding");
   protected static Configuration conf = Facade.conf;

   protected final Pattern tikaSimilarityPattern = Pattern.compile(".*\\((.*?)\\)");
   protected Double threshold = conf.getDouble("threshold");
   protected HashSet<String> langWhitelist = Sets.newHashSet("it", "en");

   protected String concatFields(String[] fields) {
      StringBuffer sb = new StringBuffer();
      for (String field : fields) {
         log.debug("Appending field");

         sb.append(field);
         sb.append(" ");

      }

      return sb.toString();
   }

   public String detect(String[] fields) {
      String docLang = null;
      String allText = concatFields(fields);
      List<DetectedLanguage> languagelist = detectLanguage(allText);
      docLang = resolveLanguage(languagelist);
      return docLang;
   }

   public String detect(String text) {
      String docLang = null;
      List<DetectedLanguage> languagelist = detectLanguage(text);
      docLang = resolveLanguage(languagelist);
      return docLang;
   }

   protected List<DetectedLanguage> detectLanguage(String content) {
      List<DetectedLanguage> languages = new ArrayList<DetectedLanguage>();
      if (content.trim().length() != 0) {
         LanguageIdentifier identifier = new LanguageIdentifier(content);
         Double distance = Double.parseDouble(tikaSimilarityPattern.matcher(
               identifier.toString()).replaceFirst("$1"));
         Double certainty = 1 - (5 * distance);
         certainty = (certainty < 0) ? 0 : certainty;
         DetectedLanguage language = new DetectedLanguage(identifier.getLanguage(),
               certainty);
         languages.add(language);
         log.debug("Language detected as " + language.getLangCode()
               + " with a certainty of " + language.getCertainty() + " (Tika distance="
               + identifier.toString() + ")");
      } else {
         log.debug("No input text to detect language from, returning empty list");
      }
      return languages;
   }

   protected String resolveLanguage(List<DetectedLanguage> languages) {
      String langStr;
      if (languages.size() == 0) {
         log.debug("No language detected");
         langStr = "";
      } else {
         DetectedLanguage lang = languages.get(0);
         if (langWhitelist.isEmpty() || langWhitelist.contains(lang.getLangCode())) {
            log.debug("Language detected {} with certainty {}", lang.getLangCode(),
                  lang.getCertainty());
            if (lang.getCertainty() >= threshold) {
               langStr = lang.getLangCode();
            } else {
               log.debug("Detected language below threshold {}", threshold);
               langStr = "";
            }
         } else {
            log.debug("Detected a language not in whitelist ({})", lang.getLangCode());
            langStr = "";

         }

      }
      if (langStr == null || langStr.length() == 0) {
         log.warn("Language resolved to null or empty string");
         langStr = "";
      }

      return langStr;
   }

   public class DetectedLanguage {

      private final String langCode;
      private final Double certainty;

      DetectedLanguage(String lang, Double certainty) {
         this.langCode = lang;
         this.certainty = certainty;
      }

      /**
       * @return Returns the langCode.
       */
      public String getLangCode() {
         return this.langCode;
      }

      /**
       * @return Returns the certainty.
       */
      public Double getCertainty() {
         return this.certainty;
      }

   }
}
