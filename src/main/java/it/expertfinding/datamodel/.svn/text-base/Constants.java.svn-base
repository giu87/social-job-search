package it.expertfinding.datamodel;

import java.util.List;

public class Constants {

   public enum RType {
      FACEBOOK_POST, FACEBOOK_GROUP, FACEBOOK_PAGE, TWITTER_PAGE, FACEBOOK_CHECKIN, TWEET, LINKEDIN_UPDATE, LINKEDIN_COMPANY, LINKEDIN_JOB, LINKEDIN_GROUP_POST, LINKEDIN_GROUP, TWITTER_PROFILE, FACEBOOK_PROFILE, LINKEDIN_PROFILE
   }

   public enum Channel {
      FACEBOOK, TWITTER, LINKEDIN
   }

   public static final String[] commonMessages = {
         "Registrazione Facebook ti aiuta a connetterti e rimanere in contatto con le persone della tua vita",
         "Facebook è una piattaforma sociale che ti consente di connetterti con i tuoi amici e con chiunque lavori, studi e viva vicino a te. Puoi usare Facebook per rimanere in contatto con i tuoi amici, caricare tutte le foto che vuoi, pubblicare link e video o per saperne di più sulle persone che incontri",
         "Instantly connect to what's most important to you. Follow your friends, experts, favorite celebrities, and breaking news",
         "Connettiti istantaneamente a ciò che è importante per te. Segui i tuoi amici, esperti, celebrità preferite e ultime notizie",
         "Facebook is a social utility that connects people with friends and others who work, study and live around them. People use Facebook to keep up with friends, upload an unlimited number of photos, post links and videos, and learn more about the people they meet.." };

   public static String removeCommonMessages(String text) {
      for (String common : commonMessages) {
         text.replace(common, "");
      }
      return text;
   }

   public static final String wikiItBaseUrl = "it.wikipedia.org/wiki/index.html?curid=";
   public static final String wikiEnBaseUrl = "en.wikipedia.org/wiki/index.html?curid=";
   public static final String freebaseDescriptionBaseUrl = "https://www.googleapis.com/freebase/v1/text/";

   public enum ResourceUserConnection {
      TWEET, TWITTER_PAGE_FOLLOWING, TWEET_FAVORITE, LINKEDIN_UPDATE, LINKEDIN_COMPANY_POSITION, LINKEDIN_COMPANY_JOB_BOOKMARK, LINKEDIN_JOB, LINKEDIN_GROUP_MEMBERSHIP, LINKEDIN_GROUP_MEMBERSHIP_POST, LINKEDIN_GROUP_MEMBERSHIP_POST_CREATOR, LINKEDIN_GROUP_MEMBERSHIP_POST_PARTECIPANT, LINKEDIN_GROUP_MEMBERSHIP_POST_LIKER, FACEBOOK_POSTER, FACEBOOK_POST_PARTECIPANT, FACEBOOK_POST_LIKER, FACEBOOK_POST_FEED_OWNER, FACEBOOK_GROUP, FACEBOOK_PAGE, FACEBOOK_GROUP_OWNER, FACEBOOK_PROFILE, FACEBOOK_POST_GROUP_SUBSCRIBED, FACEBOOK_POST_PAGE_SUBSCRIBED, LINKEDIN_PROFILE, TWITTER_PROFILE, TWEET_OF_PAGE, LINKEDIN_UPDATE_LIKER, LINKEDIN_UPDATE_PARTECIPANT
   }

   public static Float getConnectionScore(ResourceUserConnection connection) {

      switch (connection) {
         case TWEET:
            return 1F;
         case TWITTER_PAGE_FOLLOWING:
            return 1F;
         case TWEET_FAVORITE:
            return 1F;
         case LINKEDIN_UPDATE:
            return 1F;
         case LINKEDIN_COMPANY_POSITION:
            return 1F;
         case LINKEDIN_COMPANY_JOB_BOOKMARK:
            return 1F;
         case LINKEDIN_JOB:
            return 1F;
         case LINKEDIN_GROUP_MEMBERSHIP:
            return 1F;
         case LINKEDIN_GROUP_MEMBERSHIP_POST:
            return 0.2F;
         case LINKEDIN_GROUP_MEMBERSHIP_POST_CREATOR:
            return 1F;
         case LINKEDIN_GROUP_MEMBERSHIP_POST_PARTECIPANT:
            return 1F;
         case LINKEDIN_GROUP_MEMBERSHIP_POST_LIKER:
            return 1F;
         case FACEBOOK_POSTER:
            return 1F;
         case FACEBOOK_POST_PARTECIPANT:
            return 1F;
         case FACEBOOK_POST_LIKER:
            return 1F;
         case FACEBOOK_POST_FEED_OWNER:
            return 1F;
         case FACEBOOK_GROUP:
            return 1F;
         case FACEBOOK_PAGE:
            return 1F;
         case FACEBOOK_GROUP_OWNER:
            return 1F;
         case LINKEDIN_UPDATE_LIKER:
            return 1F;
         case LINKEDIN_UPDATE_PARTECIPANT:
            return 1F;
         case FACEBOOK_POST_GROUP_SUBSCRIBED:
            return 0.2F;
         case FACEBOOK_POST_PAGE_SUBSCRIBED:
            return 0.2F;
         case TWEET_OF_PAGE:
            return 0.2F;
      }
      return 0F;
   }

   public static Float getConnectionScore(List<ResourceUserConnection> connections) {

      if( (connections.contains(ResourceUserConnection.FACEBOOK_POST_PAGE_SUBSCRIBED)
            ||
            connections.contains(ResourceUserConnection.FACEBOOK_POST_GROUP_SUBSCRIBED)
            ||
            connections.contains(ResourceUserConnection.TWEET_OF_PAGE)
            ||
            connections.contains(ResourceUserConnection.LINKEDIN_GROUP_MEMBERSHIP_POST))
            &&
         connections.size()==1)
         return 0.2F;
      
      if( (connections.contains(ResourceUserConnection.FACEBOOK_POST_PAGE_SUBSCRIBED)
            ||
            connections.contains(ResourceUserConnection.FACEBOOK_POST_GROUP_SUBSCRIBED))
            &&
         connections.size()==1)
         return 0.2F;

      return 1F;
   }
}