package it.expertfinding.datamodel.resources.facebook;

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;

import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

public class FacebookGroup extends FacebookResource {

   @Facebook
   private String name;

   @Facebook
   private String description;

   @Facebook
   private FacebookNamedObject owner;

   @Facebook
   private String link;

   // private final List<String> links = new ArrayList<String>();

   @Facebook
   private FacebookLocation venue;

   @Facebook
   private String privacy;

   @Facebook("updated_time")
   private String updatedTime;

   @Facebook
   private String email;

   @Facebook
   private String icon;

   public FacebookGroup() {
      super();
      this.setrType(RType.FACEBOOK_GROUP);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   /**
    * The group description.
    * 
    * @return The group description.
    */
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * The URL for the group's website.
    * 
    * @return The URL for the group's website.
    */
   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   /**
    * The list of URLs for the group.
    * 
    * @return The list of URLs for the group.
    */
   // public List<String> getLinks() {
   // if (getLink() != null) {
   // List<String> result = URLUtils.getURLfromString(getLink());
   // result.remove(0);
   // return result;
   // }
   // return links;
   // }
   //
   // public void setLinks(List<String> links) {
   // if (links != null)
   // this.links = links;
   // else if (getLink() != null) {
   // List<String> result = URLUtils.getURLfromString(getLink());
   // result.remove(0);
   // this.links = result;
   // }
   //
   // }

   /**
    * An object containing the name and ID of the user who owns the group.
    * 
    * @return An object containing the name and ID of the user who owns the group.
    */
   public FacebookNamedObject getOwner() {
      return this.owner;
   }

   public void setOwner(FacebookNamedObject owner) {
      this.owner = owner;
   }

   public FacebookLocation getVenue() {
      return venue;
   }

   /**
    * The privacy setting of the group, either 'OPEN', 'CLOSED', or 'SECRET'.
    * 
    * @return The privacy setting of the group, either 'OPEN', 'CLOSED', or 'SECRET'.
    */

   public void setVenue(FacebookLocation venue) {
      this.venue = venue;
   }

   public String getPrivacy() {
      return privacy;
   }

   public void setPrivacy(String privacy) {
      this.privacy = privacy;
   }

   /**
    * The last time the group was updated.
    * 
    * @return The last time the group was updated.
    */
   public Date getUpdatedTime() {
      return toDateFromLongFormat(updatedTime);
   }

   public void setUpdatedTime(Date updatedTime) {
      this.updatedTime = updatedTime.toString();
   }

   /**
    * The email to post on the group (from a connected mail).
    * 
    * @return The email to post on the group (from a connected mail).
    */
   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   /**
    * The icon of the group.
    * 
    * @return The icon of the group.
    */
   public String getIcon() {
      return this.icon;
   }

   public void setIcon(String icon) {
      this.icon = icon;
   }

   @Override
   public String getResourceText() {

      if (resourceText == null) {

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { name, description, link };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));

         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            if (!url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error("Limit reached while processing facebook group id: {}",
                        get_id());
                  throw e;
               } catch (Exception e) {
               }
         }
         String rText = Constants.removeCommonMessages(s.toString());
         setResourceTextShort(rText);
         MongoUtils.updateResourceTextShort(this.getChannel(), this.get_id(), rText);

         s = new StringBuilder(rText);

         for (FacebookPost post : MongoUtils.getFacebookPostsFromSourceId(get_id(), 30)) {
            try {
               s.append(post.getResourceText());
            } catch (AlchemyLimitException e) {
               Facade.log.error("Limit reached while processing facebook group id: {}",
                     get_id());
               throw e;
            }
         }

         setResourceText(s.toString());
         MongoUtils.updateResourceText(this.getChannel(), this.get_id(), s.toString());
         return s.toString();
      } else
         return resourceText;
   }

   /**
    * @return Returns the resourceTextShort.
    */
   @Override
   public String getResourceTextShort() {
      return this.resourceTextShort;
   }

   // @Override
   // public Map<String, List<String>> getTagMeEntities() {
   // if (tagMeEntities == null) {
   // List<Entity> entities = ResourceAnalyzer.getDisambiguatedEntitiesFromString(
   // resourceText, resourceLang);
   // Map<String, List<String>> entityByType = new HashMap<String, List<String>>();
   // for (Entity e : entities) {
   // if (entityByType.containsKey(e.getTypes().get(0))) {
   // entityByType.get(e.getTypes().get(0)).add(e.get_id());
   // } else {
   // List<String> keyList = new ArrayList<String>();
   // keyList.add(e.get_id());
   // entityByType.put(e.getTypes().get(0), keyList);
   // }
   // }
   // for (FacebookPost post : MongoUtils.getFacebookPostsFromActorId(get_id())) {
   // Map<String, List<String>> postEntityMap = post.getTagMeEntities();
   // for (String key : postEntityMap.keySet()) {
   // if (entityByType.containsKey(key))
   // entityByType.get(key).addAll(postEntityMap.get(key));
   // else
   // entityByType.put(key, postEntityMap.get(key));
   // }
   // }
   // setTagMeEntities(entityByType);
   // MongoUtils.updateTagMeEntities(this.getChannel(), this.get_id(), entityByType);
   // return entityByType;
   // }
   // return tagMeEntities;
   //
   // }

}
