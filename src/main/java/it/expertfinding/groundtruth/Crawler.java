/*
 * @(#)Groundtruth.java   1.0   30/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.groundtruth;

import it.expertfinding.utils.Facade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;


public class Crawler {

   public static String gatherAnswers() {

      HttpURLConnection connection = null;
      URL url;

      try {
         String queryUrl = "http://expertfinding.altervista.org/admin/downloadAnswers.php";
         url = new URL(queryUrl);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("POST");
         connection.setDoOutput(true);
         OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

         // write parameters
         writer.write("user=***&pw=***");
         writer.flush();
         // Get Response
         InputStream is = connection.getInputStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         String line;
         StringBuilder users = new StringBuilder();
         // StringBuffer result = new StringBuffer();
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

   public static void main(String[] args) {
      
      String json = gatherAnswers();
      System.out.println(json);
      ObjectMapper mapper = new ObjectMapper();

      JsonNode j = null;
      try {
         j = mapper.readTree(json.toString()).get("answers");
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
            
      Iterator<Entry<String, JsonNode>> users = j.getFields();
      
      while (users.hasNext()) {
         
         Entry<String, JsonNode> nextUser = users.next();
         String userId = nextUser.getKey();
         if(userId.equals("1413903790")) continue;
         JsonNode userAnswers = nextUser.getValue();
         
         Iterator<Entry<String, JsonNode>> answers = userAnswers.getFields();
         List<Answer> gAnswers = new ArrayList<Answer>();
         
         while(answers.hasNext()){
            
            Entry<String, JsonNode> nextAnswer = answers.next();
            if(!nextAnswer.getKey().equals("linkedinToken") && !nextAnswer.getKey().equals("twitterId")){
               
               Integer questionId = Integer.parseInt(nextAnswer.getKey());
               String questionText = nextAnswer.getValue().get("questionText").getTextValue();
               Integer answer = nextAnswer.getValue().get("answer").getIntValue();
               Answer a = new Answer(questionId,questionText,answer);
               gAnswers.add(a);
            }
         }

         System.out.println(userId);
         System.out.println(userAnswers);
         
         DBObject f = Facade.db.getCollection(Facade.TABLE_USER)
               .findOne(new BasicDBObject("facebook._id", userId));
         
         ObjectId groundId;
         if(f != null && f.get("facebook") != null)
            groundId = (ObjectId) f.get("_id");
         else{
            
            DBObject t = Facade.db.getCollection(Facade.TABLE_USER)
                  .findOne(new BasicDBObject("twitter._id", Long.parseLong(userAnswers.get("twitterId").getTextValue().toString())));

            if(t != null && 
                  t.get("twitter") != null)
               groundId = (ObjectId) t.get("_id");
            
            else
               groundId = (ObjectId) Facade.db.getCollection(Facade.TABLE_USER)
                     .findOne(new BasicDBObject("linkedin.token", userAnswers.get("linkedinToken").getTextValue().toString())).get("_id");
         }
            
         Groundtruth user = new Groundtruth(groundId, gAnswers);
         Facade.db.getCollection("groundtruth").insert(Converter.toDBObject(user));
      }      
   }
}
