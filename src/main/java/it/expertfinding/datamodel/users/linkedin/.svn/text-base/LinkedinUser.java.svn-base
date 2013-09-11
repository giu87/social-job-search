/*
 * @(#)LinkedinUser.java   1.0   15/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.Constants;
import it.expertfinding.datamodel.Constants.RType;
import it.expertfinding.datamodel.resources.linkedin.LinkedinLocation;
import it.expertfinding.datamodel.resources.linkedin.LinkedinResource;
import it.expertfinding.utils.Facade;
import it.expertfinding.utils.MongoUtils;
import it.expertfinding.utils.URLUtils;
import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyLimitException;
import it.expertfinding.utils.linkedin.Linkedin;

import java.util.List;

public class LinkedinUser extends LinkedinResource {

   // TODO: proposalComments, courses, volunteer
   // da settare il CONT: recommendation, relatedProfileView

   // EXTRA
   @Linkedin
   private String maidenName;
   @Linkedin
   private String mfeedRssUrl;
   @Linkedin
   private String firstName;
   @Linkedin
   private String lastName;
   @Linkedin
   private String headline;
   @Linkedin
   private LinkedinLocation location;
   @Linkedin
   private String industry;
   @Linkedin
   private String currentStatus;
   @Linkedin
   private Long distance;
   @Linkedin
   private Long currentStatusTimestamp;
   @Linkedin
   private Long numRecommenders;
   @Linkedin
   private Long numConnections;
   @Linkedin
   private Boolean numConnectionsCapped;
   @Linkedin
   private String summary;
   @Linkedin
   private String publicProfileUrl;
   @Linkedin
   private String interests;
   @Linkedin
   private String associations;
   @Linkedin
   private String honors;
   @Linkedin
   private String specialties;
   @Linkedin
   private String pictureUrl;
   @Linkedin
   private String mainAddress;
   @Linkedin
   private String path;
   @Linkedin
   private Long lastModifiedTimestamp;
   @Linkedin
   private LinkedinDate dateOfBirth;

   @Linkedin
   private LinkedinConnections connections;
   @Linkedin
   private LinkedinRelationToViewer relationToViewer;
   @Linkedin
   private LinkedinStandardProfileRequest apiStandardProfileRequest;
   @Linkedin
   private LinkedinStandardProfileRequest siteStandardProfileRequest;
   @Linkedin
   private LinkedinCertifications certifications;
   @Linkedin
   private LinkedinPatents patents;
   @Linkedin
   private LinkedinPublications publications;
   @Linkedin
   private LinkedinPhoneNumbers phoneNumbers;
   @Linkedin
   private LinkedinImAccounts imAccounts;
   @Linkedin
   private LinkedinTwitterAccounts twitterAccounts;
   @Linkedin
   private LinkedinGroupMemberships groupMemberships;
   @Linkedin
   private LinkedinPositions positions;
   @Linkedin
   private LinkedinLanguages languages;
   @Linkedin
   private LinkedinSkills skills;
   @Linkedin
   private LinkedinEducations educations;
   @Linkedin
   private LinkedinPositions threeCurrentPositions;
   @Linkedin
   private LinkedinPositions threePastPositions;
   @Linkedin
   private LinkedinRecommendations recommendationsReceived;
   @Linkedin
   private LinkedinSuggestions suggestions;
   @Linkedin
   private LinkedinFollow following;
   @Linkedin
   private LinkedinJobBookmarks jobBookmarks;

   private String token;
   private String secret;

   public String getPictureUrl() {
      return this.pictureUrl;
   }

   public void setPictureUrl(String pictureUrl) {
      this.pictureUrl = pictureUrl;
   }

   public String getMainAddress() {
      return this.mainAddress;
   }

   public void setMainAddress(String mainAddress) {
      this.mainAddress = mainAddress;
   }

   public String getPath() {
      return this.path;
   }

   public void setPath(String path) {
      this.path = path;
   }

   public Boolean getNumConnectionsCapped() {
      return this.numConnectionsCapped;
   }

   public LinkedinConnections getConnections() {
      return this.connections;
   }

   public void setConnections(LinkedinConnections connections) {
      this.connections = connections;
   }

   public String getToken() {
      return this.token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public String getSecret() {
      return this.secret;
   }

   public void setSecret(String secret) {
      this.secret = secret;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String value) {
      this.firstName = value;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String value) {
      this.lastName = value;
   }

   public String getHeadline() {
      return headline;
   }

   public void setHeadline(String value) {
      this.headline = value;
   }

   public LinkedinLocation getLocation() {
      return location;
   }

   public void setLocation(LinkedinLocation value) {
      this.location = value;
   }

   public String getIndustry() {
      return industry;
   }

   public void setIndustry(String value) {
      this.industry = value;
   }

   public String getCurrentStatus() {
      return currentStatus;
   }

   public void setCurrentStatus(String value) {
      this.currentStatus = value;
   }

   public Long getDistance() {
      return distance;
   }

   public void setDistance(Long value) {
      this.distance = value;
   }

   public Long getCurrentStatusTimestamp() {
      return currentStatusTimestamp;
   }

   public void setCurrentStatusTimestamp(Long value) {
      this.currentStatusTimestamp = value;
   }

   public Long getNumRecommenders() {
      return numRecommenders;
   }

   public void setNumRecommenders(Long value) {
      this.numRecommenders = value;
   }

   public Long getNumConnections() {
      return numConnections;
   }

   public void setNumConnections(Long value) {
      this.numConnections = value;
   }

   public Boolean isNumConnectionsCapped() {
      return numConnectionsCapped;
   }

   public void setNumConnectionsCapped(Boolean value) {
      this.numConnectionsCapped = value;
   }

   public String getSummary() {
      return summary;
   }

   public void setSummary(String value) {
      this.summary = value;
   }

   public String getPublicProfileUrl() {
      return publicProfileUrl;
   }

   public void setPublicProfileUrl(String value) {
      this.publicProfileUrl = value;
   }

   public String getInterests() {
      return interests;
   }

   public void setInterests(String value) {
      this.interests = value;
   }

   public String getAssociations() {
      return associations;
   }

   public void setAssociations(String value) {
      this.associations = value;
   }

   public String getHonors() {
      return honors;
   }

   public void setHonors(String value) {
      this.honors = value;
   }

   public String getSpecialties() {
      return specialties;
   }

   public void setSpecialties(String value) {
      this.specialties = value;
   }

   public LinkedinCertifications getCertifications() {
      return this.certifications;
   }

   public void setCertifications(LinkedinCertifications certifications) {
      this.certifications = certifications;
   }

   public LinkedinRelationToViewer getRelationToViewer() {
      return this.relationToViewer;
   }

   public void setRelationToViewer(LinkedinRelationToViewer relationToViewer) {
      this.relationToViewer = relationToViewer;
   }

   public LinkedinStandardProfileRequest getApiStandardProfileRequest() {
      return this.apiStandardProfileRequest;
   }

   public void setApiStandardProfileRequest(
         LinkedinStandardProfileRequest apiStandardProfileRequest) {
      this.apiStandardProfileRequest = apiStandardProfileRequest;
   }

   public LinkedinStandardProfileRequest getSiteStandardProfileRequest() {
      return this.siteStandardProfileRequest;
   }

   public void setSiteStandardProfileRequest(
         LinkedinStandardProfileRequest siteStandardProfileRequest) {
      this.siteStandardProfileRequest = siteStandardProfileRequest;
   }

   public String getMaidenName() {
      return this.maidenName;
   }

   public void setMaidenName(String maidenName) {
      this.maidenName = maidenName;
   }

   public LinkedinPatents getPatents() {
      return this.patents;
   }

   public void setPatents(LinkedinPatents patents) {
      this.patents = patents;
   }

   public LinkedinPublications getPublications() {
      return this.publications;
   }

   public void setPublications(LinkedinPublications publications) {
      this.publications = publications;
   }

   public Long getLastModifiedTimestamp() {
      return this.lastModifiedTimestamp;
   }

   public void setLastModifiedTimestamp(Long lastModifiedTimestamp) {
      this.lastModifiedTimestamp = lastModifiedTimestamp;
   }

   public LinkedinPhoneNumbers getPhoneNumbers() {
      return this.phoneNumbers;
   }

   public void setPhoneNumbers(LinkedinPhoneNumbers phoneNumbers) {
      this.phoneNumbers = phoneNumbers;
   }

   public LinkedinImAccounts getImAccounts() {
      return this.imAccounts;
   }

   public void setImAccounts(LinkedinImAccounts imAccounts) {
      this.imAccounts = imAccounts;
   }

   public LinkedinTwitterAccounts getTwitterAccounts() {
      return this.twitterAccounts;
   }

   public void setTwitterAccounts(LinkedinTwitterAccounts twitterAccounts) {
      this.twitterAccounts = twitterAccounts;
   }

   public LinkedinGroupMemberships getGroupMemberships() {
      return this.groupMemberships;
   }

   public void setGroupMemberships(LinkedinGroupMemberships groupMemberships) {
      this.groupMemberships = groupMemberships;
   }

   public LinkedinDate getDateOfBirth() {
      return this.dateOfBirth;
   }

   public void setDateOfBirth(LinkedinDate dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
   }

   public LinkedinPositions getPositions() {
      return this.positions;
   }

   public void setPositions(LinkedinPositions positions) {
      this.positions = positions;
   }

   public LinkedinLanguages getLanguages() {
      return this.languages;
   }

   public void setLanguages(LinkedinLanguages languages) {
      this.languages = languages;
   }

   public LinkedinSkills getSkills() {
      return this.skills;
   }

   public void setSkills(LinkedinSkills skills) {
      this.skills = skills;
   }

   public LinkedinEducations getEducations() {
      return this.educations;
   }

   public void setEducations(LinkedinEducations educations) {
      this.educations = educations;
   }

   public LinkedinPositions getThreeCurrentPositions() {
      return this.threeCurrentPositions;
   }

   public void setThreeCurrentPositions(LinkedinPositions threeCurrentPositions) {
      this.threeCurrentPositions = threeCurrentPositions;
   }

   public LinkedinPositions getThreePastPositions() {
      return this.threePastPositions;
   }

   public void setThreePastPositions(LinkedinPositions threePastPositions) {
      this.threePastPositions = threePastPositions;
   }

   public LinkedinRecommendations getRecommendationsReceived() {
      return this.recommendationsReceived;
   }

   public void setRecommendationsReceived(LinkedinRecommendations recommendationsReceived) {
      this.recommendationsReceived = recommendationsReceived;
   }

   public LinkedinSuggestions getSuggestions() {
      return this.suggestions;
   }

   public void setSuggestions(LinkedinSuggestions suggestions) {
      this.suggestions = suggestions;
   }

   public LinkedinFollow getFollowing() {
      return this.following;
   }

   public void setFollowing(LinkedinFollow following) {
      this.following = following;
   }

   public LinkedinJobBookmarks getJobBookmarks() {
      return this.jobBookmarks;
   }

   public void setJobBookmarks(LinkedinJobBookmarks jobBookmarks) {
      this.jobBookmarks = jobBookmarks;
   }

   public String getMfeedRssUrl() {
      return this.mfeedRssUrl;
   }

   public void setMfeedRssUrl(String mfeedRssUrl) {
      this.mfeedRssUrl = mfeedRssUrl;
   }

   public LinkedinUser() {
      super();
      this.setrType(RType.LINKEDIN_PROFILE);
   }

   @Override
   public String getResourceText() throws AlchemyLimitException {

      if (resourceText == null) {

         Facade.log.debug("Analyzing text of Linkedin user id {}", get_id());
         StringBuilder s = new StringBuilder();

         String[] relevantFields = { headline, industry, summary, interests, associations,
               honors, specialties, path };

         for (String field : relevantFields)
            if (field != null)
               s.append(field).append(". ");
         if(location!=null)
            s.append(location.getName()).append(". ");
         
         if (certifications != null && certifications.getTotal() > 0)
            for (LinkedinCertification certification : certifications
                  .getCertificationsList())
               s.append(certification.getResourceText());

         if (patents != null && patents.getTotal() > 0)
            for (LinkedinPatent patent : patents.getPatentList())
               s.append(patent.getResourceText());

         if (publications != null && publications.getTotal() > 0)
            for (LinkedinPublication publication : publications.getPublicationsList())
               s.append(publication.getResourceText());

         if (positions != null && positions.getTotal() > 0)
            for (LinkedinPosition position : positions.getPositionsList())
               s.append(position.getText());

         if (skills != null && skills.getTotal() > 0)
            for (LinkedinSkill skill : skills.getSkillsList())
               s.append(skill.getResourceText());

         if (educations != null && educations.getTotal() > 0)
            for (LinkedinEducation education : educations.getEducationsList())
               s.append(education.getResourceText());

         if (recommendationsReceived != null && recommendationsReceived.getTotal() > 0)
            for (LinkedinRecommendation recommendation : recommendationsReceived
                  .getRecommendationsList())
               s.append(recommendation.getResourceText());

         List<String> urls = URLUtils.getURLfromString(s.toString());
         s = new StringBuilder(urls.remove(0));

         AlchemyAPI alchemyApi = AlchemyAPI.GetDefaultInstance();
         for (String url : urls) {
            Facade.log.debug("Analyzing url: {}", url);
            if (url != null && !url.contains("twitter.com"))
               try {
                  s.append(alchemyApi.URLGetText(url)).append(". ");
               } catch (AlchemyLimitException e) {
                  Facade.log.error(
                        "Limit reached while processing linkedin group post id: {}",
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
