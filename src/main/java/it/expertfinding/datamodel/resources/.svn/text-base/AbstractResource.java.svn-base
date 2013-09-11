package it.expertfinding.datamodel.resources;

import it.expertfinding.core.ResourceAnalyzer;
import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.freebase.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

public abstract class AbstractResource {

   @Field
   protected Channel channel;

   @Field
   protected RType rType;

   protected String resourceText;

   protected String solrId;

   protected String alchemyCategory;

   protected Double alchemyCategoryScore;

   protected String resourceLang;

   protected String resourceTextShort;

   protected Map<String, List<String>> tagMeEntities;

   protected Map<String, List<String>> tagMeEntitiesId;
   protected Map<String, List<String>> tagMeEntitiesSpot;

   protected List<String> tagMeEntitiesList = new ArrayList<String>();

   protected List<String> tagMeEntitiesSpotList = new ArrayList<String>();

   // protected List<DBObject> tagMeEntitiesIdListMongo = new ArrayList<DBObject>();

   protected List<String> tagMeEntitiesIdList = new ArrayList<String>();

   protected Map<String, Double> domainProbabilities = new HashMap<String, Double>();
   
   public Map<String, Double> getDomainProbabilities() {
      return this.domainProbabilities;
   }
   
   public void setDomainProbabilities(Map<String, Double> domainProbabilities) {
      this.domainProbabilities = domainProbabilities;
   }

   public Channel getChannel() {
      return channel;
   }

   public void setChannel(Channel channel) {
      this.channel = channel;
   }

   public RType getrType() {
      return rType;
   }

   public void setrType(RType rType) {
      this.rType = rType;
   }

   public String getResourceText() throws AlchemyLimitException {
      return this.resourceText;
   }

   public void setResourceText(String resourceText) {
      this.resourceText = resourceText;
   }

   /**
    * @return Returns the resourceTextShort.
    */
   public String getResourceTextShort() {
      return this.resourceText;
   }

   /**
    * @param resourceTextShort
    *           The resourceTextShort to set.
    */
   @Field("textShort")
   public void setResourceTextShort(String resourceTextShort) {
      this.resourceTextShort = resourceTextShort;
   }

   public Map<String, List<String>> getTagMeEntities() {
      if (!"en".equals(this.getResourceLang()) && !"it".equals(this.getResourceLang())
            && !this.getrType().equals(RType.FACEBOOK_PROFILE)
            && !this.getrType().equals(RType.LINKEDIN_PROFILE)
            && !this.getrType().equals(RType.TWITTER_PROFILE))
         return null;
      if (tagMeEntities == null) {
         List<Entity> entities = ResourceAnalyzer.getDisambiguatedEntitiesFromString(this
               .getResourceTextShort(), "unsopported".equals(this.getResourceLang()) ? "en"
               : this.getResourceLang());
         Map<String, List<String>> entityByDomain = new HashMap<String, List<String>>();
         Map<String, List<String>> entityIdByDomain = new HashMap<String, List<String>>();
         Map<String, List<String>> entitySpotByDomain = new HashMap<String, List<String>>();
         List<String> entityList = new ArrayList<String>();
         List<String> entityIdList = new ArrayList<String>();
         List<String> entitySpotList = new ArrayList<String>();
         List<DBObject> entityIdListMongo = new ArrayList<DBObject>();
         for (Entity e : entities) {

            StringBuilder entityId = new StringBuilder(e.get_id().toString()).append("|")
                  .append(e.getScore());
            String entityIdString = entityId.toString();

            String entityString = e.getName();
            String entitySpotString = e.getSpot();

            if (!e.getDomains().isEmpty()) {
               String domain = e.getDomains().get(0);
               // Setting map of entityType/List<name> example sportTeam --> Milan Inter
               if (entityByDomain.containsKey(domain + "_entities")) {
                  entityByDomain.get(domain + "_entities").add(entityString);
               } else {
                  List<String> keyList = new ArrayList<String>();
                  keyList.add(entityString);

                  entityByDomain.put(domain + "_entities", keyList);
               }

               // Setting map of entityType/List<ids> example sportTeam --> 1214141 414141
               // 414141
               if (entityIdByDomain.containsKey(domain + "_entitiesId")) {
                  entityIdByDomain.get(domain + "_entitiesId").add(entityIdString);
               } else {
                  List<String> keyList = new ArrayList<String>();
                  keyList.add(entityIdString);

                  entityIdByDomain.put(domain + "_entitiesId", keyList);
               }

               if (entitySpotByDomain.containsKey(domain + "_entitiesSpot")) {
                  entitySpotByDomain.get(domain + "_entitiesSpot").add(entitySpotString);
               } else {
                  List<String> keyList = new ArrayList<String>();
                  keyList.add(entitySpotString);

                  entitySpotByDomain.put(domain + "_entitiesSpot", keyList);
               }

            }

            entityList.add(entityString);
            entityIdList.add(entityIdString);
            entitySpotList.add(entitySpotString);
            // entityIdListMongo.add(new BasicDBObject().append("_id", e.get_id()).append(
            // "score", e.getScore()));

         }
         setTagMeEntities(entityByDomain);
         setTagMeEntitiesId(entityIdByDomain);
         setTagMeEntitiesSpot(entitySpotByDomain);
         setTagMeEntitiesList(entityList);
         setTagMeEntitiesIdList(entityIdList);
         setTagMeEntitiesSpotList(entitySpotList);
         // setTagMeEntitiesIdListMongo(entityIdListMongo);
         Object id = (this.getChannel().equals(Channel.TWITTER)) ? Long
               .parseLong(StringUtils.chop(getSolrId())) : StringUtils.chop(getSolrId());
         MongoUtils.updateTagMeEntities(this.getrType(), id, entityByDomain,
               entityIdByDomain, entitySpotByDomain, entityList, entityIdList,
               entitySpotList, entityIdListMongo);
         return entityByDomain;
      }

      return this.tagMeEntities;
   }

   @Field("*_entities")
   public void setTagMeEntities(Map<String, List<String>> tagMeEntities) {
      this.tagMeEntities = tagMeEntities;
   }

   /**
    * @return Returns the tagMeEntitiesId.
    */
   public Map<String, List<String>> getTagMeEntitiesId() {
      return this.tagMeEntitiesId;
   }

   /**
    * @param tagMeEntitiesId
    *           The tagMeEntitiesId to set.
    */
   @Field("*_entitiesId")
   public void setTagMeEntitiesId(Map<String, List<String>> tagMeEntitiesId) {
      this.tagMeEntitiesId = tagMeEntitiesId;
   }

   /**
    * @return Returns the tagMeEntitiesSpot.
    */
   public Map<String, List<String>> getTagMeEntitiesSpot() {
      return this.tagMeEntitiesSpot;
   }

   /**
    * @param tagMeEntitiesSpot
    *           The tagMeEntitiesSpot to set.
    */
   @Field("*_entitiesSpot")
   public void setTagMeEntitiesSpot(Map<String, List<String>> tagMeEntitiesSpot) {
      this.tagMeEntitiesSpot = tagMeEntitiesSpot;
   }

   /**
    * @return Returns the tagMeEntitiesList.
    */
   public List<String> getTagMeEntitiesList() {
      return this.tagMeEntitiesList;
   }

   /**
    * @param tagMeEntitiesList
    *           The tagMeEntitiesList to set.
    */
   @Field("entityList")
   public void setTagMeEntitiesList(List<String> tagMeEntitiesList) {
      this.tagMeEntitiesList = tagMeEntitiesList;
   }

   // /**
   // * @return Returns the tagMeEntitiesIdList.
   // */
   // public List<DBObject> getTagMeEntitiesIdListMongo() {
   // return this.tagMeEntitiesIdListMongo;
   // }
   //
   // /**
   // * @param tagMeEntitiesIdList
   // * The tagMeEntitiesIdList to set.
   // */
   //
   // public void setTagMeEntitiesIdListMongo(List<DBObject> tagMeEntitiesIdListMongo) {
   // this.tagMeEntitiesIdListMongo = tagMeEntitiesIdListMongo;
   // }

   public List<String> getTagMeEntitiesIdList() {
      return this.tagMeEntitiesIdList;
   }

   @Field("entityIdList")
   public void setTagMeEntitiesIdList(List<String> tagMeEntitiesIdList) {
      this.tagMeEntitiesIdList = tagMeEntitiesIdList;
   }

   /**
    * @return Returns the tagMeEntitiesSpotList.
    */
   public List<String> getTagMeEntitiesSpotList() {
      return this.tagMeEntitiesSpotList;
   }

   /**
    * @param tagMeEntitiesSpotList
    *           The tagMeEntitiesSpotList to set.
    */
   @Field("entitySpotList")
   public void setTagMeEntitiesSpotList(List<String> tagMeEntitiesSpotList) {
      this.tagMeEntitiesSpotList = tagMeEntitiesSpotList;
   }

   public String getSolrId() {
      return this.solrId;
   }

   @Field("id")
   public void setSolrId(String solrId) {
      this.solrId = solrId;
   }

   /**
    * @return Returns the alchemyCategory.
    */
   public String getAlchemyCategory() throws AlchemyLimitException {
      return this.alchemyCategory;
   }

   /**
    * @param alchemyCategory
    *           The alchemyCategory to set.
    */
   @Field
   public void setAlchemyCategory(String alchemyCategory) {
      this.alchemyCategory = alchemyCategory;
   }

   /**
    * @return Returns the alchemyCategoryScore.
    */
   public Double getAlchemyCategoryScore() {
      return this.alchemyCategoryScore;
   }

   /**
    * @param alchemyCategoryScore
    *           The alchemyCategoryScore to set.
    */
   public void setAlchemyCategoryScore(Double alchemyCategoryScore) {
      this.alchemyCategoryScore = alchemyCategoryScore;
   }

   /**
    * @return Returns the lang.
    */
   public String getResourceLang() {
      return this.resourceLang;
   }

   /**
    * @param lang
    *           The lang to set.
    */
   public void setResourceLang(String resourceLang) {
      this.resourceLang = resourceLang;
   }
}
