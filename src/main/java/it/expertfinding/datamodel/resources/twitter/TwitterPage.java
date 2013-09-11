/*
 * @(#)TwitterPage.java   1.0   20/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.twitter;

import static it.expertfinding.utils.DateUtils.toDateFromFormat;
import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.twitter.Twitter;

import java.util.Date;
import java.util.List;

public class TwitterPage extends TwitterResource {

   @Twitter("screen_name")
   private String screenName;

   @Twitter("profile_image_url")
   private String profileImageUrl;

   @Twitter("profile_image_url_https")
   private String profileImageUrlHttps;

   @Twitter("profile_background_image_url")
   private String profileBackgroundImageUrl;

   @Twitter("profile_background_image_url_https")
   private String profileBackgroundImageUrlHttps;

   @Twitter("profile_background_color")
   private String profileBackgroundHexColor;

   @Twitter("profile_link_color")
   private String profileLinkHexColor;

   @Twitter("profile_sidebar_border_color")
   private String profileSidebarBorderHexColor;

   @Twitter("profile_sidebar_fill_color")
   private String profileSidebarFillColor;

   @Twitter("profile_text_color")
   private String profileTextColor;

   @Twitter("profile_use_background_image")
   private Boolean profileUseBackgroundImage;

   @Twitter("contributors_enabled")
   private Boolean contributorsEnabled;

   @Twitter("created_at")
   private String createdAt;

   @Twitter("default_profile")
   private Boolean defaultProfile;

   @Twitter("default_profile_image")
   private Boolean defaultProfileImage;

   @Twitter
   private String description;

   @Twitter("favourites_count")
   private Long favouritesCount;

   @Twitter("followers_count")
   private Long followersCount;

   @Twitter("friends_count")
   private Long friendsCount;

   @Twitter("geo_enabled")
   private Boolean geoEnabled;

   @Twitter
   private String lang;

   @Twitter("listed_count")
   private Long listedCount;
   @Twitter
   private String location;

   @Twitter
   private String name;

   @Twitter
   private Boolean notifications;

   @Twitter("protected")
   private Boolean isProtected;

   @Twitter("statuses_count")
   private Long statusesCount;

   @Twitter("time_zone")
   private String timeZone;

   @Twitter
   private String url;

   @Twitter("utc_offset")
   private Long utcOffset;

   @Twitter
   private Boolean verified;

   @Twitter("is_translator")
   private Boolean isTranslator;

   private Long maxIdRecovered = 0L;

   private Long sinceIdRecovered = 0L;

   public TwitterPage() {
      super();
      this.setrType(RType.TWITTER_PAGE);
   }

   /**
    * @return Returns the screenName.
    */
   public String getScreenName() {
      return this.screenName;
   }

   /**
    * @param screenName
    *           The screenName to set.
    */
   public void setScreenName(String screenName) {
      this.screenName = screenName;
   }

   /**
    * @return Returns the profileImageUrl.
    */
   public String getProfileImageUrl() {
      return this.profileImageUrl;
   }

   /**
    * @param profileImageUrl
    *           The profileImageUrl to set.
    */
   public void setProfileImageUrl(String profileImageUrl) {
      this.profileImageUrl = profileImageUrl;
   }

   /**
    * @return Returns the profileImageUrlHttps.
    */
   public String getProfileImageUrlHttps() {
      return this.profileImageUrlHttps;
   }

   /**
    * @param profileImageUrlHttps
    *           The profileImageUrlHttps to set.
    */
   public void setProfileImageUrlHttps(String profileImageUrlHttps) {
      this.profileImageUrlHttps = profileImageUrlHttps;
   }

   /**
    * @return Returns the profileBackgroundImageUrl.
    */
   public String getProfileBackgroundImageUrl() {
      return this.profileBackgroundImageUrl;
   }

   /**
    * @param profileBackgroundImageUrl
    *           The profileBackgroundImageUrl to set.
    */
   public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
      this.profileBackgroundImageUrl = profileBackgroundImageUrl;
   }

   /**
    * @return Returns the profileBackgroundImageUrlHttps.
    */
   public String getProfileBackgroundImageUrlHttps() {
      return this.profileBackgroundImageUrlHttps;
   }

   /**
    * @param profileBackgroundImageUrlHttps
    *           The profileBackgroundImageUrlHttps to set.
    */
   public void setProfileBackgroundImageUrlHttps(String profileBackgroundImageUrlHttps) {
      this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
   }

   /**
    * @return Returns the profileBackgroundHexColor.
    */
   public String getProfileBackgroundHexColor() {
      return this.profileBackgroundHexColor;
   }

   /**
    * @param profileBackgroundHexColor
    *           The profileBackgroundHexColor to set.
    */
   public void setProfileBackgroundHexColor(String profileBackgroundHexColor) {
      this.profileBackgroundHexColor = profileBackgroundHexColor;
   }

   /**
    * @return Returns the profileLinkHexColor.
    */
   public String getProfileLinkHexColor() {
      return this.profileLinkHexColor;
   }

   /**
    * @param profileLinkHexColor
    *           The profileLinkHexColor to set.
    */
   public void setProfileLinkHexColor(String profileLinkHexColor) {
      this.profileLinkHexColor = profileLinkHexColor;
   }

   /**
    * @return Returns the profileSidebarBorderHexColor.
    */
   public String getProfileSidebarBorderHexColor() {
      return this.profileSidebarBorderHexColor;
   }

   /**
    * @param profileSidebarBorderHexColor
    *           The profileSidebarBorderHexColor to set.
    */
   public void setProfileSidebarBorderHexColor(String profileSidebarBorderHexColor) {
      this.profileSidebarBorderHexColor = profileSidebarBorderHexColor;
   }

   /**
    * @return Returns the profileSidebarFillColor.
    */
   public String getProfileSidebarFillColor() {
      return this.profileSidebarFillColor;
   }

   /**
    * @param profileSidebarFillColor
    *           The profileSidebarFillColor to set.
    */
   public void setProfileSidebarFillColor(String profileSidebarFillColor) {
      this.profileSidebarFillColor = profileSidebarFillColor;
   }

   /**
    * @return Returns the profileTextColor.
    */
   public String getProfileTextColor() {
      return this.profileTextColor;
   }

   /**
    * @param profileTextColor
    *           The profileTextColor to set.
    */
   public void setProfileTextColor(String profileTextColor) {
      this.profileTextColor = profileTextColor;
   }

   /**
    * @return Returns the profileUseBackgroundImage.
    */
   public Boolean getProfileUseBackgroundImage() {
      return this.profileUseBackgroundImage;
   }

   /**
    * @param profileUseBackgroundImage
    *           The profileUseBackgroundImage to set.
    */
   public void setProfileUseBackgroundImage(Boolean profileUseBackgroundImage) {
      this.profileUseBackgroundImage = profileUseBackgroundImage;
   }

   /**
    * @return Returns the contributorsEnabled.
    */
   public Boolean getContributorsEnabled() {
      return this.contributorsEnabled;
   }

   /**
    * @param contributorsEnabled
    *           The contributorsEnabled to set.
    */
   public void setContributorsEnabled(Boolean contributorsEnabled) {
      this.contributorsEnabled = contributorsEnabled;
   }

   /**
    * @return Returns the createdAt.
    */
   public Date getCreatedAt() {
      return toDateFromFormat(createdAt);
   }

   /**
    * @param createdAt
    *           The createdAt to set.
    */
   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt.toString();
   }

   /**
    * @return Returns the defaultProfile.
    */
   public Boolean getDefaultProfile() {
      return this.defaultProfile;
   }

   /**
    * @param defaultProfile
    *           The defaultProfile to set.
    */
   public void setDefaultProfile(Boolean defaultProfile) {
      this.defaultProfile = defaultProfile;
   }

   /**
    * @return Returns the defaultProfileImage.
    */
   public Boolean getDefaultProfileImage() {
      return this.defaultProfileImage;
   }

   /**
    * @param defaultProfileImage
    *           The defaultProfileImage to set.
    */
   public void setDefaultProfileImage(Boolean defaultProfileImage) {
      this.defaultProfileImage = defaultProfileImage;
   }

   /**
    * @return Returns the description.
    */
   public String getDescription() {
      return this.description;
   }

   /**
    * @param description
    *           The description to set.
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * @return Returns the favouritesCount.
    */
   public Long getFavouritesCount() {
      return this.favouritesCount;
   }

   /**
    * @param favouritesCount
    *           The favouritesCount to set.
    */
   public void setFavouritesCount(Long favouritesCount) {
      this.favouritesCount = favouritesCount;
   }

   /**
    * @return Returns the followersCount.
    */
   public Long getFollowersCount() {
      return this.followersCount;
   }

   /**
    * @param followersCount
    *           The followersCount to set.
    */
   public void setFollowersCount(Long followersCount) {
      this.followersCount = followersCount;
   }

   /**
    * @return Returns the friendsCount.
    */
   public Long getFriendsCount() {
      return this.friendsCount;
   }

   /**
    * @param friendsCount
    *           The friendsCount to set.
    */
   public void setFriendsCount(Long friendsCount) {
      this.friendsCount = friendsCount;
   }

   /**
    * @return Returns the geoEnabled.
    */
   public Boolean getGeoEnabled() {
      return this.geoEnabled;
   }

   /**
    * @param geoEnabled
    *           The geoEnabled to set.
    */
   public void setGeoEnabled(Boolean geoEnabled) {
      this.geoEnabled = geoEnabled;
   }

   /**
    * @return Returns the lang.
    */
   public String getLang() {
      return this.lang;
   }

   /**
    * @param lang
    *           The lang to set.
    */
   public void setLang(String lang) {
      this.lang = lang;
   }

   /**
    * @return Returns the listedCount.
    */
   public Long getListedCount() {
      return this.listedCount;
   }

   /**
    * @param listedCount
    *           The listedCount to set.
    */
   public void setListedCount(Long listedCount) {
      this.listedCount = listedCount;
   }

   /**
    * @return Returns the location.
    */
   public String getLocation() {
      return this.location;
   }

   /**
    * @param location
    *           The location to set.
    */
   public void setLocation(String location) {
      this.location = location;
   }

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
    * @return Returns the notifications.
    */
   public Boolean getNotifications() {
      return this.notifications;
   }

   /**
    * @param notifications
    *           The notifications to set.
    */
   public void setNotifications(Boolean notifications) {
      this.notifications = notifications;
   }

   /**
    * @return Returns the isProtected.
    */
   public Boolean getIsProtected() {
      return this.isProtected;
   }

   /**
    * @param isProtected
    *           The isProtected to set.
    */
   public void setIsProtected(Boolean isProtected) {
      this.isProtected = isProtected;
   }

   /**
    * @return Returns the statusesCount.
    */
   public Long getStatusesCount() {
      return this.statusesCount;
   }

   /**
    * @param statusesCount
    *           The statusesCount to set.
    */
   public void setStatusesCount(Long statusesCount) {
      this.statusesCount = statusesCount;
   }

   /**
    * @return Returns the timeZone.
    */
   public String getTimeZone() {
      return this.timeZone;
   }

   /**
    * @param timeZone
    *           The timeZone to set.
    */
   public void setTimeZone(String timeZone) {
      this.timeZone = timeZone;
   }

   /**
    * @return Returns the url.
    */
   public String getUrl() {
      return this.url;
   }

   /**
    * @param url
    *           The url to set.
    */
   public void setUrl(String url) {
      this.url = url;
   }

   /**
    * @return Returns the utcOffset.
    */
   public Long getUtcOffset() {
      return this.utcOffset;
   }

   /**
    * @param utcOffset
    *           The utcOffset to set.
    */
   public void setUtcOffset(Long utcOffset) {
      this.utcOffset = utcOffset;
   }

   /**
    * @return Returns the verified.
    */
   public Boolean getVerified() {
      return this.verified;
   }

   /**
    * @param verified
    *           The verified to set.
    */
   public void setVerified(Boolean verified) {
      this.verified = verified;
   }

   /**
    * @return Returns the isTranslator.
    */
   public Boolean getIsTranslator() {
      return this.isTranslator;
   }

   /**
    * @param isTranslator
    *           The isTranslator to set.
    */
   public void setIsTranslator(Boolean isTranslator) {
      this.isTranslator = isTranslator;
   }

   /**
    * @return Returns the maxIdRecovered.
    */
   public Long getMaxIdRecovered() {
      return this.maxIdRecovered;
   }

   /**
    * @param maxIdRecovered
    *           The maxIdRecovered to set.
    */
   public void setMaxIdRecovered(Long maxIdRecovered) {
      this.maxIdRecovered = maxIdRecovered;
   }

   /**
    * @return Returns the sinceIdRecovered.
    */
   public Long getSinceIdRecovered() {
      return this.sinceIdRecovered;
   }

   /**
    * @param sinceIdRecovered
    *           The sinceIdRecovered to set.
    */
   public void setSinceIdRecovered(Long sinceIdRecovered) {
      this.sinceIdRecovered = sinceIdRecovered;
   }

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {

         Facade.log.debug("Analyzing text of Twitter page id {}", get_id());

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { name, description };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         List<String> urls = URLUtils.getURLfromString(s.toString());
         if (url != null)
            urls.add(url);
         s = new StringBuilder(urls.remove(0));

         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            try {
               s.append(alchemyApi.HTMLGetText(URLUtils.getURLContent(url), null)).append(
                     ". ");
            } catch (AlchemyLimitException e) {
               Facade.log.error("Limit reached while processing twitter page id: {}",
                     get_id());
               throw e;
            } catch (Exception e) {
               Facade.log.error("Error while processing twitter page id: " + get_id(), e);
            }
         }

         String rText = Constants.removeCommonMessages(s.toString());
         setResourceTextShort(rText);
         MongoUtils.updateResourceTextShort(this.getChannel(), this.get_id(), rText);

         s = new StringBuilder(rText);

         for (Tweet tweet : MongoUtils.getTweetsFromActorId(get_id(), 30)) {
            try {
               s.append(tweet.getResourceText());
            } catch (AlchemyLimitException e) {
               Facade.log.error("Limit reached while processing twitter page id: {}",
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

}
