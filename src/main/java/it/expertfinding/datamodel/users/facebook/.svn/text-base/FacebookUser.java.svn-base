/*
 * @(#)FacebookUser.java   1.0   15/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.facebook;

import static com.restfb.util.DateUtils.toDateFromLongFormat;
import static com.restfb.util.DateUtils.toDateFromShortFormat;
import static com.restfb.util.StringUtils.isBlank;
import static java.util.Collections.unmodifiableList;
import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.datamodel.resources.facebook.FacebookGroupHighlight;
import it.expertfinding.datamodel.resources.facebook.FacebookNamedObject;
import it.expertfinding.datamodel.resources.facebook.FacebookPageHighlight;
import it.expertfinding.datamodel.resources.facebook.FacebookResource;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

public class FacebookUser extends FacebookResource {

   @Facebook
   private String name;

   @Facebook("first_name")
   private String firstName;

   @Facebook("middle_name")
   private String middleName;

   @Facebook("last_name")
   private String lastName;

   @Facebook
   private String link;

   @Facebook
   private String bio;

   @Facebook
   private String quotes;

   @Facebook
   private String about;

   @Facebook("relationship_status")
   private String relationshipStatus;

   @Facebook
   private String religion;

   @Facebook
   private String website;

   @Facebook
   private String birthday;

   @Facebook
   private String email;

   @Facebook
   private Double timezone;

   @Facebook
   private Boolean verified;

   @Facebook
   private String gender;

   @Facebook
   private String political;

   @Facebook
   private String locale;

   @Facebook
   private String username;

   @Facebook
   private FacebookNamedObject hometown;
   /**
    * Duplicate mapping for "hometown" since FB can return it differently in different
    * situations.
    */
   @Facebook("hometown")
   private String hometownAsString;

   @Facebook
   private FacebookNamedObject location;

   @Facebook("significant_other")
   private FacebookNamedObject significantOther;

   @Facebook("updated_time")
   private String updatedTime;

   @Facebook("third_party_id")
   private String thirdPartyId;

   @Facebook("interested_in")
   private List<String> interestedIn = new ArrayList<String>();

   @Facebook("meeting_for")
   private List<String> meetingFor = new ArrayList<String>();

   @Facebook
   private List<FacebookWork> work = new ArrayList<FacebookWork>();

   @Facebook
   private List<FacebookEducation> education = new ArrayList<FacebookEducation>();

   @Facebook
   private List<FacebookSport> sports = new ArrayList<FacebookSport>();

   @Facebook("favorite_teams")
   private List<FacebookNamedObject> favoriteTeams = new ArrayList<FacebookNamedObject>();

   @Facebook("favorite_athletes")
   private List<FacebookNamedObject> favoriteAthletes = new ArrayList<FacebookNamedObject>();

   @Facebook
   private List<FacebookNamedObject> languages = new ArrayList<FacebookNamedObject>();

   private String token;

   private Date lastProfileUpdate;

   private List<FacebookNamedObject> friends = new ArrayList<FacebookNamedObject>();

   private List<FacebookGroupHighlight> groups = new ArrayList<FacebookGroupHighlight>();

   private List<FacebookPageHighlight> likes = new ArrayList<FacebookPageHighlight>();

   public FacebookUser() {
      super();
      this.setrType(RType.FACEBOOK_PROFILE);
   }

   /**
    * The name field for this type.
    * 
    * @return The name field for this type.
    */
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   /**
    * This object's unique Facebook ID.
    * 
    * @return This object's unique Facebook ID.
    */

   /**
    * The user's first name.
    * 
    * @return The user's first name.
    */
   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * The user's middle name.
    * 
    * @return The user's middle name.
    */
   public String getMiddleName() {
      return middleName;
   }

   public void setMiddleName(String middleName) {
      this.middleName = middleName;
   }

   /**
    * The user's last name.
    * 
    * @return The user's last name.
    */
   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   /**
    * A link to the user's profile.
    * 
    * @return A link to the user's profile.
    */
   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }

   /**
    * The user's blurb that appears under their profile picture.
    * 
    * @return The user's blurb that appears under their profile picture.
    */
   public String getAbout() {
      return about;
   }

   public void setAbout(String about) {
      this.about = about;
   }

   /**
    * The user's relationship status.
    * 
    * @return The user's relationship status.
    */
   public String getRelationshipStatus() {
      return relationshipStatus;
   }

   public void setRelationshipStatus(String relationshipStatus) {
      this.relationshipStatus = relationshipStatus;
   }

   /**
    * The user's birthday as a {@code String}.
    * <p>
    * Will always succeed, even if the user has specified month/year format only. If you'd
    * like to use a typed version of this accessor, call {@link #getBirthdayAsDate()}
    * instead.
    * 
    * @return The user's birthday as a {@code String}.
    */
   public String getBirthday() {
      return birthday;
   }

   public void setBirthday(String birthday) {
      this.birthday = birthday;
   }

   /**
    * The user's birthday, typed to {@code java.util.Date} if possible.
    * 
    * @return The user's birthday, or {@code null} if unavailable or only available in
    *         month/year format.
    */
   public Date getBirthdayAsDate() {
      if (isBlank(getBirthday()) || getBirthday().split("/").length < 2)
         return null;

      return toDateFromShortFormat(birthday);
   }

   /**
    * The user's religion.
    * 
    * @return The user's religion.
    */
   public String getReligion() {
      return religion;
   }

   public void setReligion(String religion) {
      this.religion = religion;
   }

   /**
    * A link to the user's personal website.
    * 
    * @return A link to the user's personal website.
    */
   public String getWebsite() {
      return website;
   }

   public void setWebsite(String website) {
      this.website = website;
   }

   /**
    * The proxied or contact email address granted by the user.
    * 
    * @return The proxied or contact email address granted by the user.
    */
   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   /**
    * The user's favorite quotes.
    * 
    * @return The user's favorite quotes.
    */
   public String getQuotes() {
      return quotes;
   }

   public void setQuotes(String quotes) {
      this.quotes = quotes;
   }

   /**
    * The user's timezone offset.
    * 
    * @return The user's timezone offset.
    */
   public Double getTimezone() {
      return timezone;
   }

   public void setTimezone(Double timezone) {
      this.timezone = timezone;
   }

   /**
    * Is the user verified?
    * 
    * @return User verification status.
    */
   public Boolean getVerified() {
      return verified;
   }

   public void setVerified(Boolean verified) {
      this.verified = verified;
   }

   /**
    * Date the user's profile was updated.
    * 
    * @return Date the user's profile was updated.
    */
   public Date getUpdatedTime() {
      return toDateFromLongFormat(updatedTime);
   }

   public void setUpdatedTime(Date updatedTime) {
      this.updatedTime = updatedTime.toString();
   }

   /**
    * The user's gender.
    * 
    * @return The user's gender.
    */
   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   /**
    * The user's biographical snippet.
    * 
    * @return The user's biographical snippet.
    */
   public String getBio() {
      return bio;
   }

   public void setBio(String bio) {
      this.bio = bio;
   }

   /**
    * The user's political affiliation.
    * 
    * @return The user's political affiliation.
    */
   public String getPolitical() {
      return political;
   }

   public void setPolitical(String political) {
      this.political = political;
   }

   /**
    * The user's locale.
    * 
    * @return The user's locale.
    */
   public String getLocale() {
      return locale;
   }

   public void setLocale(String locale) {
      this.locale = locale;
   }

   /**
    * The user's Facebook username.
    * 
    * @return The user's Facebook username.
    * @since 1.6.5
    */
   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   /**
    * The user's hometown.
    * <p>
    * Sometimes this can be {@code null} - check {@link #getHometownName()} instead in that
    * case.
    * 
    * @return The user's hometown.
    */
   public FacebookNamedObject getHometown() {
      return hometown;
   }

   public void setHometown(FacebookNamedObject hometown) {
      this.hometown = hometown;
   }

   /**
    * The user's hometown name.
    * 
    * @return The user's hometown name.
    */
   public String getHometownName() {
      if (getHometown() != null)
         return getHometown().getName();

      return hometownAsString;

   }

   public void setHometownName(String hometownAsString) {
      if (hometownAsString != null)
         this.hometownAsString = hometownAsString;
      else if (getHometown() != null)
         this.hometownAsString = getHometown().getName();
   }

   /**
    * The user's current location.
    * 
    * @return The user's current location.
    */
   public FacebookNamedObject getLocation() {
      return location;
   }

   public void setLocation(FacebookNamedObject location) {
      this.location = location;
   }

   /**
    * The user's significant other.
    * 
    * @return The user's significant other.
    */
   public FacebookNamedObject getSignificantOther() {
      return significantOther;
   }

   public void setSignificantOther(FacebookNamedObject significantOther) {
      this.significantOther = significantOther;
   }

   /**
    * An anonymous, but unique identifier for the user.
    * 
    * @return An anonymous, but unique identifier for the user.
    */
   public String getThirdPartyId() {
      return thirdPartyId;
   }

   public void setThirdPartyId(String thirdPartyId) {
      this.thirdPartyId = thirdPartyId;
   }

   /**
    * The user's interests.
    * 
    * @return The user's interests.
    */
   public List<String> getInterestedIn() {
      return unmodifiableList(interestedIn);
   }

   public void setInterestedIn(List<String> interestedIn) {
      this.interestedIn = interestedIn;
   }

   /**
    * What genders the user is interested in meeting.
    * 
    * @return What genders the user is interested in meeting.
    */
   public List<String> getMeetingFor() {
      return unmodifiableList(meetingFor);
   }

   public void setMeetingFor(List<String> meetingFor) {
      this.meetingFor = meetingFor;
   }

   /**
    * A list of the work history from the user's profile.
    * 
    * @return A list of the work history from the user's profile.
    */
   public List<FacebookWork> getWork() {
      return unmodifiableList(work);
   }

   public void setWork(List<FacebookWork> work) {
      this.work = work;
   }

   /**
    * A list of the education history from the user's profile.
    * 
    * @return A list of the education history from the user's profile.
    */
   public List<FacebookEducation> getEducation() {
      return unmodifiableList(education);
   }

   public void setEducation(List<FacebookEducation> education) {
      this.education = education;
   }

   /**
    * A list of the sports from the user's profile.
    * 
    * @return A list of the sports from ths user's profile.
    */
   public List<FacebookSport> getSports() {
      return unmodifiableList(sports);
   }

   public void setSports(List<FacebookSport> sports) {
      this.sports = sports;
   }

   /**
    * A list of the favorite sports teams from the user's profile.
    * 
    * @return A list of the favorite sports teams from the user's profile.
    */
   public List<FacebookNamedObject> getFavoriteTeams() {
      return unmodifiableList(favoriteTeams);
   }

   public void setFavoriteTeams(List<FacebookNamedObject> favoriteTeams) {
      this.favoriteTeams = favoriteTeams;
   }

   /**
    * A list of the favorite athletes from the user's profile.
    * 
    * @return A list of the favorite athletes from the user's profile.
    */
   public List<FacebookNamedObject> getFavoriteAthletes() {
      return unmodifiableList(favoriteAthletes);
   }

   public void setFavoriteAthletes(List<FacebookNamedObject> favoriteAthletes) {
      this.favoriteAthletes = favoriteAthletes;
   }

   /**
    * A list of the languages from the user's profile.
    * 
    * @return A list of the languages from the user's profile.
    */
   public List<FacebookNamedObject> getLanguages() {
      return unmodifiableList(languages);
   }

   public void setLanguages(List<FacebookNamedObject> languages) {
      this.languages = languages;
   }

   public String getToken() {
      return this.token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public Date getLastProfileUpdate() {
      return this.lastProfileUpdate;
   }

   public void setLastProfileUpdate(Date lastProfileUpdate) {
      this.lastProfileUpdate = lastProfileUpdate;
   }

   public List<FacebookNamedObject> getFriends() {
      return this.friends;
   }

   public void setFriends(List<FacebookNamedObject> friends) {
      this.friends = friends;
   }

   public List<FacebookGroupHighlight> getGroups() {
      return this.groups;
   }

   public void setGroups(List<FacebookGroupHighlight> groups) {
      this.groups = groups;
   }

   public List<FacebookPageHighlight> getLikes() {
      return this.likes;
   }

   public void setLikes(List<FacebookPageHighlight> likes) {
      this.likes = likes;
   }

   @Override
   public String getResourceText() {

      if (resourceText == null) {

         Facade.log.debug("Analyzing text of page id {}", get_id());

         StringBuilder s = new StringBuilder();

         String[] relevantFields = { getHometownName(), bio, quotes, about, religion,
               political, relationshipStatus };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");

         if (location != null)
            s.append(location.getName()).append(". ");

         for (String interest : interestedIn)
            s.append(interest).append(". ");

         for (FacebookWork w : work)
            s.append(w.getResourceText());

         for (FacebookEducation e : education)
            s.append(e.getResourceText());

         for (FacebookSport sport : sports)
            s.append(sport.getName()).append(". ");

         for (FacebookNamedObject team : favoriteTeams)
            s.append(team.getName()).append(". ");

         for (FacebookNamedObject athlete : favoriteAthletes)
            s.append(athlete.getName()).append(". ");

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));
         if (website != null && !urls.contains(website))
            urls.add(website);

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
         setResourceText(rText);
         MongoUtils.updateResourceText(this.getChannel(), this.get_id(), rText,
               this.getrType());
         return rText;
      } else
         return resourceText;
   }
}
