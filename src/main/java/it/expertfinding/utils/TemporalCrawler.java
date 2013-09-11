/*
 * @(#)TokenGatherer.java   1.0   12/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils;

import it.expertfinding.datamodel.users.CrowdUser;
import it.expertfinding.datamodel.users.facebook.FacebookUser;
import it.expertfinding.datamodel.users.linkedin.LinkedinUser;
import it.expertfinding.utils.linkedin.LinkedinUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class TemporalCrawler {
      
   protected static Configuration conf = Facade.conf;
         
   public static String gatherTokens() {
       
      HttpURLConnection connection = null;
      URL url;
      StringBuilder users = null;

      try {
         String queryUrl = "http://expertfinding.altervista.org/admin/getContacts.php";
         url = new URL(queryUrl);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("POST");
         connection.setDoOutput(true);
         OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

         // write parameters
         writer.write("user=expertfinding&pw=ajc478");
         writer.flush();
         // Get Response
         InputStream is = connection.getInputStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         String line;
         users = new StringBuilder();

         while ((line = rd.readLine()) != null) {
            users.append(line);
         }
         rd.close();
         writer.close();
         return users.toString();
         
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      } finally {
         if (connection != null) {
            connection.disconnect();
         }
      }
   }
      
   public static FacebookUser getFacebookUser(String token, String id){
      
      return null;
      //System.out.println("FB token = "+token+", FB id = "+id);
   }
   
   public static void getTwitterUser(String token, String secret, String id){
      
      //System.out.println("Twitter token = "+token+"Twitter secret = "+secret+", Twitter id = "+id);
   }
   
   public static void main(String[] args) {
      
      String tokens = Crawler.gatherTokens();
      System.out.println(tokens);
      ObjectMapper mapper = new ObjectMapper();
      try {
         
         JsonNode u = mapper.readTree(tokens).get("users");
         for(JsonNode user : u){

            CrowdUser c = new CrowdUser();
         
            FacebookUser fu = getFacebookUser(user.get("facebookToken").getTextValue(), user.toString());
            System.out.println("Getting user data:");

            LinkedinUser lu = LinkedinUtils.getLinkedinUser(user.get("linkedinToken").getTextValue(),user.get("linkedinTokenSecret").getTextValue());
            //TwitterUser tw = getTwitterUser(user.get("twitterToken").getTextValue(),user.get("twitterTokenSecret").getTextValue(),user.get("twitterId").getTextValue());
                        
            c.setFacebook(fu);
            c.setLinkedin(lu);
            
            
            if(lu != null && lu.getFirstName()!= null){

               lu = LinkedinUtils.getLinkedinGroupMembershipsOfUser(lu);
               LinkedinUtils.storeUser(lu);
               LinkedinUtils.getResourcesOfUser(lu);
            }
         }
         
      } catch (JsonProcessingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}