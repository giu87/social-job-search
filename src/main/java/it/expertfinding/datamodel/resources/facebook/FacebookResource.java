package it.expertfinding.datamodel.resources.facebook;

import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.LanguageDetector;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyConstants.StatusInfo;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.alchemy.CategorizationResponse;

import com.restfb.Facebook;

public abstract class FacebookResource extends AbstractResource {

   @Facebook("id")
   private String _id;

   @Facebook("type")
   private String type;

   public FacebookResource() {
      super();
      this.setChannel(Channel.FACEBOOK);
   }

   public String get_id() {
      return _id;
   }

   public void set_id(String _id) {
      this._id = _id;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   /**
    * @return Returns the solrId.
    */
   @Override
   public String getSolrId() {
      if (solrId != null)
         return this.solrId;
      else {
         this.solrId = get_id() + "F";
         return solrId;
      }
   }

   @Override
   public String getAlchemyCategory() {
      if (alchemyCategory == null) {
         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         try {
            CategorizationResponse result = alchemyApi.TextGetCategory(getResourceText());
            if (StatusInfo.DAILY_LIMIT_ERROR.equals(result.getStatusInfo()))
               throw new AlchemyLimitException("Daily limit reached while processing "
                     + getrType() + " id: " + get_id());
            setAlchemyCategory(result.getCategory());
            setAlchemyCategoryScore(result.getScore());
            MongoUtils.updateResourceCategory(this.getrType(), this.get_id(),
                  result.getCategory(), result.getScore());
            return result.getCategory();
         } catch (Exception e) {
            Facade.log.debug(this.getrType() + " " + this.get_id());
            setAlchemyCategory("unknown");
            return "unknown";
         }
      } else
         return this.alchemyCategory;
   }

   @Override
   public String getResourceLang() {
      if (resourceLang == null || resourceLang.equals("unsopported")) {
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

   // @Override
   // public Map<String, List<String>> getTagMeEntities() {
   // if (tagMeEntities == null) {
   // Map<String, List<String>> entityByType = super.getTagMeEntities();
   // MongoUtils.updateTagMeEntities(this.getChannel(), this.get_id(), entityByType);
   // return entityByType;
   // }
   //
   // return tagMeEntities;
   // }
}
