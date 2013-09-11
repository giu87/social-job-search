/*
 * @(#)PrecisionRecallCalculator.java   1.0   01/giu/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.groundtruth;

import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.query.Query;
import it.expertfinding.datamodel.users.CrowdUser;
import it.expertfinding.utils.Facade;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.bson.types.ObjectId;

public class PrecisionRecallCalculator {

   private static String method = "textual";
   private static String lan = "en";
   private static List<Channel> cs = new ArrayList<Channel>();
   private static String entityType = "no";
   private static String textType = "text";

   public static void main(String[] args) {

      FileWriter writer = null;
      FileWriter writer2 = null;
      try {
         writer = new FileWriter("C:\\Users\\Giuliano\\precisionRecallWithUsers.txt", true);
         writer2 = new FileWriter("C:\\Users\\Giuliano\\precisionRecall.txt", true);
      } catch (IOException e) {
         System.err.println("Error: " + e.getMessage());
      }
      BufferedWriter w = new BufferedWriter(writer);
      BufferedWriter w2 = new BufferedWriter(writer2);

      Double MAP = 0D;
      Double MRR = 0D;
      Double avgPrecision = 0D;
      Double avgRecall = 0D;
      Double avgPrecision2 = 0D;
      Double avgRecall2 = 0D;
      Double varPrecision;
      Double varRecall;
      
      Groundtruth groundtruthUser = Converter.toObject(Groundtruth.class, Facade.db
            .getCollection("groundtruth").findOne());

      for (Answer a : groundtruthUser.getAnswers()) {

         Facade.log.debug("Processing question: " + a.getQuestionText());
         Set<String> relevantDocuments = getRelevantUserForQuery(a.getQuestionId());
         Query q = new Query(a.getQuestionText(), method, cs, lan, entityType, textType);
         Set<String> retrievedDocuments = (method.equals("textual")) ? q.getBestUsers()
               .keySet() : q.getBestUsersProb().keySet();
         System.out.println(retrievedDocuments.size());
         retrievedDocuments = deleteRetrievedNotInGroundtruth(retrievedDocuments);
         Facade.log.debug("Print relevant users");
         printDocuments(relevantDocuments);
         Facade.log.debug("Print retrieved users");
         printDocuments(retrievedDocuments);

         Set<String> intersection = getIntersection(relevantDocuments, retrievedDocuments);

         Facade.log.debug("Print intersection users");
         printDocuments(intersection);

         Double precision = (double) intersection.size()
               / (double) retrievedDocuments.size();
         Double recall = (double) intersection.size() / (double) relevantDocuments.size();
         Double Fmeasure = 2 * precision * recall / (precision + recall);

         List<String> bestUsersInGroundtruth = deleteRetrievedNotInGroundtruth((method
               .equals("textual")) ? q.getOrderedBestUserIds() : new ArrayList<String>(q
               .getBestUsersProb().keySet()));
         Double aveP = calculateAveP(bestUsersInGroundtruth, relevantDocuments);
         Double reciprocalRank = calculateRR(bestUsersInGroundtruth, relevantDocuments);
         
         System.out.println("Question: " + a.getQuestionText());
         System.out.println("Precision: " + precision);
         System.out.println("Recall: " + recall);
         System.out.println("F-Measure: " + Fmeasure);
         System.out.println("AveP: " + aveP);
         System.out.println("RR: " + reciprocalRank);
         System.out.println();
         System.out.println();

         MAP += aveP;
         MRR += reciprocalRank;
         avgPrecision += precision;
         avgRecall += recall;
         avgPrecision2 += precision * precision;
         avgRecall2 += recall * recall;
         
         try {

            /* WITH USERS */
            w.write(a.getQuestionText().toUpperCase());
            w.newLine();
            w.write("Relevant users");
            w.newLine();
            w.write("{");
            for (String doc : relevantDocuments)
               w.write(doc + ", ");
            w.write("}");
            w.newLine();
            w.write("Retrieved users");
            w.newLine();
            w.write("{");
            for (String doc : retrievedDocuments)
               w.write(doc + ", ");
            w.write("}");
            w.newLine();
            w.write("Intersection");
            w.newLine();
            w.write("{");
            for (String doc : intersection)
               w.write(doc + ", ");
            w.write("}");
            w.newLine();
            w.newLine();
            w.write("PRECISION = " + precision);
            w.newLine();
            w.write("RECALL = " + recall);
            w.newLine();
            w.write("F-MEASURE = " + Fmeasure);
            w.newLine();
            w.write("AVE_P = " + aveP);
            w.newLine();
            w.newLine();
            w.flush();

            /* WITHOUT USERS */
            w2.write(a.getQuestionText().toUpperCase());
            w2.newLine();
            w2.write("PRECISION = " + precision);
            w2.newLine();
            w2.write("RECALL = " + recall);
            w2.newLine();
            w2.write("F-MEASURE = " + Fmeasure);
            w2.newLine();
            w2.write("AVE_P = " + aveP);
            w2.newLine();
            w2.write("RR = " + reciprocalRank);
            w2.newLine();
            w2.newLine();
            w2.flush();

         } catch (IOException e) {
         }
      }

      MAP = MAP / groundtruthUser.getAnswers().size();
      MRR = MRR / groundtruthUser.getAnswers().size();
      avgPrecision = avgPrecision / groundtruthUser.getAnswers().size();
      avgRecall = avgRecall / groundtruthUser.getAnswers().size();
      avgPrecision2 = avgPrecision2 / groundtruthUser.getAnswers().size();
      avgRecall2 = avgRecall2 / groundtruthUser.getAnswers().size();
      
      varPrecision = avgPrecision2 - (avgPrecision * avgPrecision);
      varRecall = avgRecall2 - (avgRecall * avgRecall);

      
      try {
         System.out.println("----TOTAL RESULTS----");
         System.out.println("MAP = " + MAP);
         w.write("MAP = " + MAP);
         w2.write("MAP = " + MAP);
         w.write("MRR = " + MRR);
         w2.write("MRR = " + MRR);
         w.write("avgPrecision = " + avgPrecision);
         w2.write("avgPrecision = " + avgPrecision);
         w.write("avgRecall = " + avgRecall);
         w2.write("avgRecall = " + avgRecall);
         w.write("varPrecision = " + varPrecision);
         w2.write("varPrecision = " + varPrecision);
         w.write("varRecall = " + varRecall);
         w2.write("varRecall = " + varRecall);
         w.close();
         w2.close();
      } catch (IOException e) {
         System.err.println("Error: " + e.getMessage());
      }
   }

   private static Double calculateRR(List<String> bestUsersInGroundtruth,
         Set<String> relevantDocuments) {

      for (int i = 0; i < bestUsersInGroundtruth.size(); i++) {
         if (relevantDocuments.contains(bestUsersInGroundtruth.get(i)))
            return (double) 1 / (double) (i + 1);
      }
      return 0D;
   }

   private static double calculateAveP(List<String> orderedUsers,
         Set<String> relevantDocuments) {

      Double precisionSum = 0D;
      for (String relevant : relevantDocuments) {

         Set<String> tempList = new HashSet<String>();
         for (String retrieved : orderedUsers) {
            if (!retrieved.equals(relevant))
               tempList.add(retrieved);
            else {
               tempList.add(retrieved);
               break;
            }
         }

         if (tempList.contains(relevant)) {
            precisionSum += (double) getIntersection(relevantDocuments, tempList).size()
                  / (double) tempList.size();
         }
      }

      return precisionSum / relevantDocuments.size();
   }

   private static Set<String> deleteRetrievedNotInGroundtruth(Set<String> retrievedDocuments) {

      Set<String> result = new HashSet<String>();
      for (String doc : retrievedDocuments) {

         DBObject ob = Facade.db.getCollection("groundtruth").findOne(
               new BasicDBObject("userId", new ObjectId(doc)));

         if (ob != null)
            result.add(doc);
      }
      return result;
   }

   private static List<String> deleteRetrievedNotInGroundtruth(
         List<String> retrievedDocuments) {

      List<String> result = new ArrayList<String>();
      for (String doc : retrievedDocuments) {

         DBObject ob = Facade.db.getCollection("groundtruth").findOne(
               new BasicDBObject("userId", new ObjectId(doc)));

         if (ob != null)
            result.add(doc);
      }
      return result;
   }

   private static void printDocuments(Set<String> relevantDocuments) {

      for (String doc : relevantDocuments)
         Facade.log.debug(doc);
   }

   private static Set<String> getIntersection(Set<String> relevantDocuments,
         Set<String> retrievedDocuments) {

      Set<String> set = new HashSet<String>();
      for (String document : relevantDocuments) {
         CrowdUser ob = Converter.toObject(
               CrowdUser.class,
               Facade.db.getCollection(Facade.TABLE_USER).findOne(
                     new BasicDBObject("_id", new ObjectId(document))));
         if (retrievedDocuments.contains(ob.get_id().toString()))
            set.add(ob.get_id().toString());
      }
      return set;
   }

   public static Set<String> getRelevantUserForQuery(Integer queryId) {

      Map<String, Integer> userAnswers = new HashMap<String, Integer>();
      DBCursor cursor = Facade.db.getCollection("groundtruth").find();

      int total = 0;
      while (cursor.hasNext()) {

         Groundtruth user = Converter.toObject(Groundtruth.class, cursor.next());
         for (Answer a : user.getAnswers()) {

            if (a.getQuestionId().equals(queryId)) {
               total += a.getAnswer();
               userAnswers.put(user.getUserId().toString(), a.getAnswer());
               break;
            }
         }
      }

      Double avg = (double) total / (double) userAnswers.size();
      Set<String> returnSet = new HashSet<String>();
      for (String user : userAnswers.keySet()) {
         if (userAnswers.get(user) > avg)
            returnSet.add(user);
      }
      return returnSet;
   }
}