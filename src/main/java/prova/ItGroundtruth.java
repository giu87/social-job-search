/*
 * @(#)ItGroundtruth.java   1.0   22/giu/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import it.expertfinding.utils.Facade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ItGroundtruth {

   public static String readString() {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String input = "";
      try {
         input = br.readLine();
      } catch (IOException e) {
         System.out.println("errore di flusso");
      }

      return (input);
   }

   public static void main(String[] args) {

      // DBObject groundtruth = Facade.db.getCollection("groundtruth").findOne();
      // List<String> answerIt = new ArrayList<String>();
      // BasicDBList answers = (BasicDBList) groundtruth.get("answers");
      // for (Object o : answers) {
      // DBObject answer = (DBObject) o;
      // System.out.println(answer.get("questionText").toString());
      // answerIt.add(readString());
      // }
      //
      // DBCursor groundtruthCur = Facade.db.getCollection("groundtruth").find();
      // while (groundtruthCur.hasNext()) {
      // DBObject user = groundtruthCur.next();
      // answers = (BasicDBList) user.get("answers");
      // int i = 0;
      // BasicDBList newAnswers = new BasicDBList();
      // for (Object o : answers) {
      // DBObject answer = (DBObject) o;
      // answer.put("questionText", answerIt.get(i));
      // newAnswers.add(answer);
      // i++;
      // }
      // user.put("answers", newAnswers);
      // Facade.db.getCollection("groundtruthIt").insert(user);
      // }

      DBCursor groundtruthCur = Facade.db.getCollection("groundtruth").find();
      while (groundtruthCur.hasNext()) {
         DBObject user = groundtruthCur.next();
         DBObject oldUser = user;
         BasicDBList answers = (BasicDBList) user.get("answers");
         int i = 0;
         BasicDBList newAnswers = new BasicDBList();
         for (Object o : answers) {
            DBObject answer = (DBObject) o;
            if (i != 16)
               answer.put("questionText", /* StringUtils.chop( */
                     ((DBObject) answers.get(i)).get("questionText")/* ) */);
            else
               answer.put("questionText",
                     "Who was the actress in Pirates of the Caribbean with Johnny Depp?");
            newAnswers.add(answer);
            i++;
         }
         user.put("answers", newAnswers);
         Facade.db.getCollection("groundtruth").update(
               new BasicDBObject("_id", user.get("_id")), user);
      }
   }
}
