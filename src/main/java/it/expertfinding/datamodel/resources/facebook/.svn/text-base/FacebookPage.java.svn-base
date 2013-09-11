/*
 * @(#)FacebookPage.java   1.0   16/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.facebook;

import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;

import java.util.List;

import com.restfb.Facebook;

public class FacebookPage extends FacebookResource {

   // TODO category come @Field

   @Facebook
   private String name;

   @Facebook
   private String description;

   @Facebook
   private String category;

   @Facebook("is_community_page")
   private Boolean isCommunityPage;

   @Facebook
   private String picture;

   @Facebook
   private String link;

   @Facebook
   private String founded;

   @Facebook("company_overview")
   private String companyOverview;

   @Facebook
   private String mission;

   @Facebook
   private String products;

   @Facebook
   private Long likes;

   @Facebook
   private String phone;

   @Facebook("access_token")
   private String accessToken;

   @Facebook
   private FacebookLocation location;

   @Facebook("is_published")
   private Boolean isPublished;

   @Facebook
   private String website;

   // private final List<String> websites = new ArrayList<String>();

   @Facebook
   private String about;

   @Facebook
   private String genre;

   @Facebook
   private String network;

   @Facebook
   private String season;

   @Facebook
   private String awards;

   @Facebook
   private String affiliation;

   @Facebook
   private String members;

   @Facebook("talking_about_count")
   private Long talkingAboutCount;

   @Facebook("can_post")
   private Boolean canPost;

   @Facebook
   private Integer checkins;

   @Facebook("general_info")
   private String generalInfo;

   /** Applicable to Band/Musician **/

   @Facebook("general_manager")
   private String generalManager;

   @Facebook("release_date")
   private String releaseDate;

   @Facebook("band_members")
   private String bandMembers;

   @Facebook
   private String hometown;

   @Facebook("current_location")
   private String currentLocation;

   @Facebook("record_label")
   private String recordLabel;

   @Facebook("booking_agent")
   private String bookingAgent;

   @Facebook("press_contact")
   private String pressContact;

   @Facebook
   private String bio;

   @Facebook("artists_we_like")
   private String artistsWeLike;

   @Facebook
   private String influences;

   @Facebook("band_interests")
   private String bandInterests;

   /** Applicable to Movie **/
   @Facebook
   private String starring;

   @Facebook("plot_outline")
   private String plotOutline;

   @Facebook("directed_by")
   private String directedBy;

   @Facebook("produced_by")
   private String producedBy;

   @Facebook
   private String studio;

   @Facebook
   private String schedule;

   @Facebook("written_by")
   private String writtenBy;

   /** Applicable to People **/
   @Facebook
   private String birthday;

   @Facebook("personal_info")
   private String personalInfo;

   @Facebook("personal_interests")
   private String personalInterests;

   /** Applicable to Vehicles **/
   @Facebook
   private String features;

   @Facebook
   private String mpg;

   @Facebook
   private String built;

   /** Applicable to Pharmaceutical companies **/
   @Facebook("pharma_safety_info")
   private String pharmaSafetyInfo;

   /** Applicable to Restaurants/Nightlife **/
   @Facebook("public_transit")
   private String publicTransit;

   @Facebook
   private String attire;

   @Facebook("culinary_team")
   private String culinaryTeam;

   @Facebook("price_range")
   private String priceRange;

   @Facebook
   private Cover cover;

   public FacebookPage() {
      super();
      this.setrType(RType.FACEBOOK_PAGE);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getCategory() {
      return this.category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public Boolean getIsCommunityPage() {
      return this.isCommunityPage;
   }

   public void setIsCommunityPage(Boolean isCommunityPage) {
      this.isCommunityPage = isCommunityPage;
   }

   public String getPicture() {
      return this.picture;
   }

   public void setPicture(String picture) {
      this.picture = picture;
   }

   public String getLink() {
      return this.link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public String getFounded() {
      return this.founded;
   }

   public void setFounded(String founded) {
      this.founded = founded;
   }

   public String getCompanyOverview() {
      return this.companyOverview;
   }

   public void setCompanyOverview(String companyOverview) {
      this.companyOverview = companyOverview;
   }

   public String getMission() {
      return this.mission;
   }

   public void setMission(String mission) {
      this.mission = mission;
   }

   public String getProducts() {
      return this.products;
   }

   public void setProducts(String products) {
      this.products = products;
   }

   public Long getLikes() {
      return this.likes;
   }

   public void setLikes(Long likes) {
      this.likes = likes;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getAccessToken() {
      return this.accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

   public FacebookLocation getLocation() {
      return this.location;
   }

   public void setLocation(FacebookLocation location) {
      this.location = location;
   }

   public Boolean getIsPublished() {
      return this.isPublished;
   }

   public void setIsPublished(Boolean isPublished) {
      this.isPublished = isPublished;
   }

   public String getWebsite() {
      return this.website;
   }

   public void setWebsite(String website) {
      this.website = website;
   }

   // public List<String> getWebsites() {
   // if (getWebsite() != null) {
   // List<String> result = URLUtils.getURLfromString(getWebsite());
   // result.remove(0);
   // return result;
   // }
   // return websites;
   // }
   //
   // public void setWebsites(List<String> websites) {
   // if (websites != null)
   // this.websites = websites;
   // else if (getWebsite() != null) {
   // List<String> result = URLUtils.getURLfromString(getWebsite());
   // result.remove(0);
   // this.websites = result;
   // }
   //
   // }

   public String getAbout() {
      return this.about;
   }

   public void setAbout(String about) {
      this.about = about;
   }

   public String getGenre() {
      return this.genre;
   }

   public void setGenre(String genre) {
      this.genre = genre;
   }

   public String getNetwork() {
      return this.network;
   }

   public void setNetwork(String network) {
      this.network = network;
   }

   public String getSeason() {
      return this.season;
   }

   public void setSeason(String season) {
      this.season = season;
   }

   public String getAwards() {
      return this.awards;
   }

   public void setAwards(String awards) {
      this.awards = awards;
   }

   public String getAffiliation() {
      return this.affiliation;
   }

   public void setAffiliation(String affiliation) {
      this.affiliation = affiliation;
   }

   public String getMembers() {
      return this.members;
   }

   public void setMembers(String members) {
      this.members = members;
   }

   public Long getTalkingAboutCount() {
      return this.talkingAboutCount;
   }

   public void setTalkingAboutCount(Long talkingAboutCount) {
      this.talkingAboutCount = talkingAboutCount;
   }

   public Boolean getCanPost() {
      return this.canPost;
   }

   public void setCanPost(Boolean canPost) {
      this.canPost = canPost;
   }

   public Integer getCheckins() {
      return this.checkins;
   }

   public void setCheckins(Integer checkins) {
      this.checkins = checkins;
   }

   public String getGeneralInfo() {
      return this.generalInfo;
   }

   public void setGeneralInfo(String generalInfo) {
      this.generalInfo = generalInfo;
   }

   public String getGeneralManager() {
      return this.generalManager;
   }

   public void setGeneralManager(String generalManager) {
      this.generalManager = generalManager;
   }

   public String getReleaseDate() {
      return this.releaseDate;
   }

   public void setReleaseDate(String releaseDate) {
      this.releaseDate = releaseDate;
   }

   public String getBandMembers() {
      return this.bandMembers;
   }

   public void setBandMembers(String bandMembers) {
      this.bandMembers = bandMembers;
   }

   public String getHometown() {
      return this.hometown;
   }

   public void setHometown(String hometown) {
      this.hometown = hometown;
   }

   public String getCurrentLocation() {
      return this.currentLocation;
   }

   public void setCurrentLocation(String currentLocation) {
      this.currentLocation = currentLocation;
   }

   public String getRecordLabel() {
      return this.recordLabel;
   }

   public void setRecordLabel(String recordLabel) {
      this.recordLabel = recordLabel;
   }

   public String getBookingAgent() {
      return this.bookingAgent;
   }

   public void setBookingAgent(String bookingAgent) {
      this.bookingAgent = bookingAgent;
   }

   public String getPressContact() {
      return this.pressContact;
   }

   public void setPressContact(String pressContact) {
      this.pressContact = pressContact;
   }

   public String getBio() {
      return this.bio;
   }

   public void setBio(String bio) {
      this.bio = bio;
   }

   public String getArtistsWeLike() {
      return this.artistsWeLike;
   }

   public void setArtistsWeLike(String artistsWeLike) {
      this.artistsWeLike = artistsWeLike;
   }

   public String getInfluences() {
      return this.influences;
   }

   public void setInfluences(String influences) {
      this.influences = influences;
   }

   public String getBandInterests() {
      return this.bandInterests;
   }

   public void setBandInterests(String bandInterests) {
      this.bandInterests = bandInterests;
   }

   public String getStarring() {
      return this.starring;
   }

   public void setStarring(String starring) {
      this.starring = starring;
   }

   public String getPlotOutline() {
      return this.plotOutline;
   }

   public void setPlotOutline(String plotOutline) {
      this.plotOutline = plotOutline;
   }

   public String getDirectedBy() {
      return this.directedBy;
   }

   public void setDirectedBy(String directedBy) {
      this.directedBy = directedBy;
   }

   public String getProducedBy() {
      return this.producedBy;
   }

   public void setProducedBy(String producedBy) {
      this.producedBy = producedBy;
   }

   public String getStudio() {
      return this.studio;
   }

   public void setStudio(String studio) {
      this.studio = studio;
   }

   public String getSchedule() {
      return this.schedule;
   }

   public void setSchedule(String schedule) {
      this.schedule = schedule;
   }

   public String getWrittenBy() {
      return this.writtenBy;
   }

   public void setWrittenBy(String writtenBy) {
      this.writtenBy = writtenBy;
   }

   public String getBirthday() {
      return this.birthday;
   }

   public void setBirthday(String birthday) {
      this.birthday = birthday;
   }

   public String getPersonalInfo() {
      return this.personalInfo;
   }

   public void setPersonalInfo(String personalInfo) {
      this.personalInfo = personalInfo;
   }

   public String getPersonalInterests() {
      return this.personalInterests;
   }

   public void setPersonalInterests(String personalInterests) {
      this.personalInterests = personalInterests;
   }

   public String getFeatures() {
      return this.features;
   }

   public void setFeatures(String features) {
      this.features = features;
   }

   public String getMpg() {
      return this.mpg;
   }

   public void setMpg(String mpg) {
      this.mpg = mpg;
   }

   public String getBuilt() {
      return this.built;
   }

   public void setBuilt(String built) {
      this.built = built;
   }

   public String getPharmaSafetyInfo() {
      return this.pharmaSafetyInfo;
   }

   public void setPharmaSafetyInfo(String pharmaSafetyInfo) {
      this.pharmaSafetyInfo = pharmaSafetyInfo;
   }

   public String getPublicTransit() {
      return this.publicTransit;
   }

   public void setPublicTransit(String publicTransit) {
      this.publicTransit = publicTransit;
   }

   public String getAttire() {
      return this.attire;
   }

   public void setAttire(String attire) {
      this.attire = attire;
   }

   public String getCulinaryTeam() {
      return this.culinaryTeam;
   }

   public void setCulinaryTeam(String culinaryTeam) {
      this.culinaryTeam = culinaryTeam;
   }

   public String getPriceRange() {
      return this.priceRange;
   }

   public void setPriceRange(String priceRange) {
      this.priceRange = priceRange;
   }

   public Cover getCover() {
      return this.cover;
   }

   public void setCover(Cover cover) {
      this.cover = cover;
   }

   public static class Cover {

      @Facebook("cover_id")
      private String coverId;
      @Facebook
      private String source;

      /**
       * The page's cover id.
       * 
       * @return The page's cover id.
       */
      public String getCoverId() {
         return this.coverId;
      }

      public void setCoverId(String coverId) {
         this.coverId = coverId;
      }

      /**
       * The page's cover url.
       * 
       * @return The page's cover url.
       */
      public String getSource() {
         return this.source;
      }

      public void setSource(String source) {
         this.source = source;
      }

   }

   @Override
   public String getResourceText() {

      if (resourceText == null) {

         Facade.log.debug("Analyzing text of page id {}", get_id());

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { name, description, category, bio, companyOverview,
               mission, products, website, about, genre, network, awards, affiliation,
               members, generalInfo, generalManager, bandMembers, bandInterests,
               recordLabel, bookingAgent, pressContact, artistsWeLike, influences, starring,
               plotOutline, directedBy, producedBy, studio, writtenBy, personalInterests,
               personalInfo, features, mpg, built, pharmaSafetyInfo, publicTransit, attire,
               culinaryTeam };

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
                  Facade.log.error("Limit reached while processing facebook page id: {}",
                        get_id());
                  throw e;
               } catch (Exception e) {
               }
         }
         String rText = Constants.removeCommonMessages(s.toString());
         setResourceTextShort(rText);
         MongoUtils.updateResourceTextShort(this.getChannel(), this.get_id(), rText);

         s = new StringBuilder(rText);

         for (FacebookPost post : MongoUtils.getFacebookPostsFromActorId(get_id())) {
            try {
               s.append(post.getResourceTextWithoutComments());
            } catch (AlchemyLimitException e) {
               Facade.log.error("Limit reached while processing facebook page id: {}",
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
