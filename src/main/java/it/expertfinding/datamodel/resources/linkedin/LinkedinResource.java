/*
 * @(#)LinkedinResource.java   1.0   17/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.linkedin;

import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.LanguageDetector;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinResource extends AbstractResource {

   @Linkedin("id")
   private String _id;

   public String get_id() {
      return this._id;
   }

   public void set_id(String _id) {
      this._id = _id;
   }

   public LinkedinResource() {
      super();
      this.setChannel(Channel.LINKEDIN);
   }

   @Override
   public String getSolrId() {
      if (solrId != null)
         return this.solrId;
      else {
         this.solrId = get_id() + "L";
         return solrId;
      }
   }

   @Override
   public String getAlchemyCategory() {
      return null;
      // if (alchemyCategory == null) {
      // AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
      // try {
      // CategorizationResponse result = alchemyApi.TextGetCategory(getResourceText());
      // if (StatusInfo.DAILY_LIMIT_ERROR.equals(result.getStatusInfo()))
      // throw new AlchemyLimitException("Daily limit reached while processing "
      // + getrType() + " id: " + get_id());
      // setAlchemyCategory(result.getCategory());
      // setAlchemyCategoryScore(result.getScore());
      // MongoUtils.updateResourceCategory(this.getrType(), this.get_id(),
      // result.getCategory(), result.getScore());
      // return result.getCategory();
      // } catch (Exception e) {
      // e.printStackTrace();
      // setAlchemyCategory("unknown");
      // return "unknown";
      // }
      // } else
      // return this.alchemyCategory;
   }

   @Override
   public String getResourceLang() {
      if (resourceLang == null) {
         LanguageDetector detector = new LanguageDetector();
         String detectedLang;
         try {
            detectedLang = detector.detect(getResourceText());
         } catch (AlchemyLimitException e) {
            Facade.log.error("Limit reached while processing {} id: {}", getrType(),
                  get_id());
            throw e;
         }
         setResourceLang(("".equals(detectedLang)) ? "unsopported" : detectedLang);
         MongoUtils.updateResourceLang(this.getrType(), this.get_id(), resourceLang);
         return resourceLang;
      } else

         return this.resourceLang;
   }
}
