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

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.types.ObjectId;

public class PrecisionRecallExcelCalculator2 {

   private static String method = "textual";
   private static String lan = "en";
   private static List<Channel> cs = new ArrayList<Channel>();
   private static String entityType = "no";
   private static String textType = "text";

   public static void main(String[] args) {

      Double MAP = 0D;
      Double MRR = 0D;
      Double RPrecision = 0D;
      Double PrecisionAt10 = 0D;
      Double RPrecisionAt10 = 0D;
      Double avgPrecision = 0D;
      Double avgRecall = 0D;
      Double avgPrecision2 = 0D;
      Double avgRecall2 = 0D;
      Double varPrecision;
      Double varRecall;
      Double NDCG = 0D;
      Double ndcg_query;

      // Create Excel file
      HSSFWorkbook workbook = createExcelFileHead();

      HSSFSheet sheetGraph = workbook.createSheet("PrecisionRecallGraph");
      sheetGraph.setColumnWidth(0, 10000);

      HSSFSheet sheet = workbook.getSheetAt(0);
      HSSFRow row;

      Groundtruth groundtruthUser = Converter.toObject(Groundtruth.class, Facade.db
            .getCollection("groundtruth").findOne());

      int i = 1;
      for (Answer a : groundtruthUser.getAnswers()) {

         // if (i == 1 || i == 7 || i == 22 || i == 27 || i == 28) {
         // i++;
         // continue;
         // }
         Facade.log.debug("Processing question: " + a.getQuestionText());
         Set<String> relevantDocuments = getRelevantUserForQuery(a.getQuestionId());
         Facade.log.debug("#Relevant=" + relevantDocuments.size());
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
         Double precisionAt10 = calculatePrecisionAtN(bestUsersInGroundtruth,
               relevantDocuments, 10);
         Double rPrecision = calculatePrecisionAtN(bestUsersInGroundtruth,
               relevantDocuments, relevantDocuments.size());
         Double rPrecisionAt10 = calculatePrecisionAtN(bestUsersInGroundtruth,
               relevantDocuments,
               (relevantDocuments.size() > 10) ? 10 : relevantDocuments.size());
         // NDCG calculus
         ndcg_query = ndcgOfQuery(bestUsersInGroundtruth, a.getQuestionId());

         buildGraphPrecisionRecall(bestUsersInGroundtruth, relevantDocuments, i, sheetGraph,
               a.getQuestionText());

         MAP += aveP;
         MRR += reciprocalRank;
         RPrecision += rPrecision;
         PrecisionAt10 += precisionAt10;
         RPrecisionAt10 += rPrecisionAt10;
         avgPrecision += precision;
         avgRecall += recall;
         avgPrecision2 += precision * precision;
         avgRecall2 += recall * recall;
         NDCG += ndcg_query;
         row = sheet.createRow(i);

         printValues(row, a.getQuestionText(), precision, recall, Fmeasure, aveP,
               reciprocalRank, ndcg_query);

         System.out.println("Question: " + a.getQuestionText());
         System.out.println("Precision: " + precision);
         System.out.println("Recall: " + recall);
         System.out.println("F-Measure: " + Fmeasure);
         System.out.println("AveP: " + aveP);
         System.out.println("RR: " + reciprocalRank);
         System.out.println();
         System.out.println();

         i++;
      }

      MAP = MAP / (groundtruthUser.getAnswers().size());
      MRR = MRR / (groundtruthUser.getAnswers().size());
      PrecisionAt10 = PrecisionAt10 / (groundtruthUser.getAnswers().size());
      RPrecision = RPrecision / (groundtruthUser.getAnswers().size());
      RPrecisionAt10 = RPrecisionAt10 / (groundtruthUser.getAnswers().size());
      // System.out.println("RPrecision: " + RPrecision);
      avgPrecision = avgPrecision / (groundtruthUser.getAnswers().size());
      avgRecall = avgRecall / (groundtruthUser.getAnswers().size());
      avgPrecision2 = avgPrecision2 / (groundtruthUser.getAnswers().size());
      avgRecall2 = avgRecall2 / (groundtruthUser.getAnswers().size());
      NDCG = NDCG / (groundtruthUser.getAnswers().size());

      varPrecision = avgPrecision2 - (avgPrecision * avgPrecision);
      varRecall = avgRecall2 - (avgRecall * avgRecall);

      row = sheet.createRow(i + 1);
      printHeadFinalMeasures(row);

      row = sheet.createRow(i + 2);
      printFinalMeasures(row, MAP, MRR, avgPrecision, avgRecall, varPrecision, varRecall,
            NDCG, PrecisionAt10, RPrecision, RPrecisionAt10);

      FileOutputStream fOut;
      try {
         fOut = new FileOutputStream(
               "/Users/Teo/Dropbox/Crowdsearch-Vesci-Silvestri-Thesis/tests/baselineSoloProfili/Textual_En_Text_noEntity.xls");
         workbook.write(fOut);

         fOut.flush();
         fOut.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static Double ndcgOfQuery(List<String> bestUsersInGroundtruth, Integer queryId) {

      int m = 1;
      Double value = 0D;
      List<Integer> ordered = new ArrayList<Integer>();

      DBCursor cursor = Facade.db.getCollection("groundtruth").find();
      while (cursor.hasNext()) {
         Groundtruth user = Converter.toObject(Groundtruth.class, cursor.next());
         if (bestUsersInGroundtruth.contains(user.getUserId().toString())) {

            int k = 0;
            while (ordered.size() > k
                  && ordered.get(k) > user.getAnswers().get(queryId - 1).getAnswer()) {
               k++;
            }
            ordered.add(k, user.getAnswers().get(queryId - 1).getAnswer());
            value += (Math.pow(2, user.getAnswers().get(queryId - 1).getAnswer()) - 1)
                  / Math.log(1 + m) * Math.log(2);
            m++;
         }
      }

      Double z = 0D;
      for (int i = 0; i < ordered.size(); i++) {
         z += (Math.pow(2, ordered.get(i)) - 1) / Math.log(2 + i) * Math.log(2);
      }

      return value / z;
   }

   private static Double calculatePrecisionAtN(List<String> retrievedDocuments,
         Set<String> relevantDocuments, int n) {
      Set<String> retrievedN = null;
      if (retrievedDocuments.size() >= n)
         retrievedN = new HashSet<String>(retrievedDocuments.subList(0, n));
      else
         retrievedN = new HashSet<String>(retrievedDocuments);
      Set<String> intersection = getIntersection(relevantDocuments, retrievedN);
      return (double) intersection.size() / n;
   }

   private static void buildGraphPrecisionRecall(List<String> retrievedDocuments,
         Set<String> relevantDocuments, int columnNumber, HSSFSheet sheet, String query) {

      HSSFRow row;
      HSSFCell cell;
      if (sheet.getRow(0) == null)
         row = sheet.createRow(0);
      else
         row = sheet.getRow(0);

      cell = row.createCell((columnNumber - 1) * 5);
      cell.setCellValue(query);

      if (sheet.getRow(1) == null)
         row = sheet.createRow(1);
      else
         row = sheet.getRow(1);

      cell = row.createCell((columnNumber - 1) * 5);
      cell.setCellValue("Retrieved");
      cell = row.createCell((columnNumber - 1) * 5 + 1);
      cell.setCellValue("Recall");
      cell = row.createCell((columnNumber - 1) * 5 + 2);
      cell.setCellValue("Precision");
      cell = row.createCell((columnNumber - 1) * 5 + 3);
      cell.setCellValue("Int_Precision");

      List<Double> precision = new ArrayList<Double>();
      List<Double> recall = new ArrayList<Double>();
      for (int k = 0; k < retrievedDocuments.size(); k++) {

         Set<String> retrievedK = new HashSet<String>(retrievedDocuments.subList(0, k + 1));

         Set<String> intersection = getIntersection(relevantDocuments, retrievedK);

         precision.add((double) intersection.size() / (double) retrievedK.size());
         recall.add((double) intersection.size() / (double) relevantDocuments.size());
      }

      List<Double> intPrecision = new ArrayList<Double>();
      for (int i = 0; i < recall.size(); i++) {

         Double max = 0D;
         for (Double p : precision.subList(i, recall.size())) {
            if (p > max)
               max = p;
         }
         intPrecision.add(max);

         if (sheet.getRow(i + 2) == null)
            row = sheet.createRow(i + 2);
         else
            row = sheet.getRow(i + 2);
         cell = row.createCell((columnNumber - 1) * 5);
         cell.setCellValue(i + 1);
         cell = row.createCell((columnNumber - 1) * 5 + 1);
         cell.setCellValue(recall.get(i));
         cell = row.createCell((columnNumber - 1) * 5 + 2);
         cell.setCellValue(precision.get(i));
         cell = row.createCell((columnNumber - 1) * 5 + 3);
         cell.setCellValue(max);
      }

      Double r;
      int line;
      if (method.equals("textual"))
         line = 30;
      else
         line = 60;

      for (int j = 0; j < 11; j++) {

         r = (double) j / 10;
         if (sheet.getRow(line + j) == null) {
            row = sheet.createRow(line + j);
            cell = row.createCell((columnNumber - 1) * 5);
            cell.setCellValue(r);
         } else
            row = sheet.getRow(line + j);

         boolean found = false;
         for (int k = 0; k < recall.size(); k++) {

            if (recall.get(k) >= r) {

               cell = row.createCell((columnNumber - 1) * 5 + 2);
               cell.setCellValue(intPrecision.get(k));
               found = true;
               break;
            }
         }
         if (!found) {
            cell = row.createCell((columnNumber - 1) * 5 + 2);
            cell.setCellValue(intPrecision.get(intPrecision.size() - 1));
         }
      }
   }

   private static void printHeadFinalMeasures(HSSFRow row) {

      HSSFCell cell = row.createCell(1);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("MAP");

      cell = row.createCell(2);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("MRR");

      cell = row.createCell(3);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("avgPrecision");

      cell = row.createCell(4);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("avgRecall");

      cell = row.createCell(5);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("varPrecision");

      cell = row.createCell(6);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("varRecall");

      cell = row.createCell(7);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("NDCG");

      cell = row.createCell(8);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("Precision@10");

      cell = row.createCell(9);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("R-Precision");

      cell = row.createCell(10);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("R-Precision@10");
   }

   private static void printFinalMeasures(HSSFRow row, Double MAP, Double MRR,
         Double avgPrecision, Double avgRecall, Double varPrecision, Double varRecall,
         Double NDCG, Double PrecisionAt10, Double RPrecision, Double RPrecisionAt10) {

      HSSFCell cell = row.createCell(1);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(MAP);

      cell = row.createCell(2);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(MRR);

      cell = row.createCell(3);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(avgPrecision);

      cell = row.createCell(4);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(avgRecall);

      cell = row.createCell(5);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(varPrecision);

      cell = row.createCell(6);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(varRecall);

      cell = row.createCell(7);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(NDCG);

      cell = row.createCell(8);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(PrecisionAt10);

      cell = row.createCell(9);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(RPrecision);

      cell = row.createCell(10);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(RPrecisionAt10);
   }

   private static void printValues(HSSFRow row, String questionText, Double precision,
         Double recall, Double fmeasure, Double aveP, Double reciprocalRank,
         Double ndcg_query) {

      HSSFCell cell = row.createCell(0);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue(questionText);

      cell = row.createCell(1);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(precision);

      cell = row.createCell(2);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(recall);

      cell = row.createCell(3);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(fmeasure);

      cell = row.createCell(4);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(aveP);

      cell = row.createCell(5);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(reciprocalRank);

      cell = row.createCell(6);
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(ndcg_query);
   }

   private static HSSFWorkbook createExcelFileHead() {

      HSSFWorkbook workbook = new HSSFWorkbook();
      HSSFSheet sheet = workbook.createSheet("Statistics");
      sheet.setColumnWidth(0, 10000);

      HSSFFont font = workbook.createFont();
      font.setColor(HSSFFont.COLOR_RED);
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

      HSSFCellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont(font);

      HSSFRow row = sheet.createRow(0);
      HSSFCell cell = row.createCell(0);
      cell.setCellStyle(cellStyle);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("Query");

      cell = row.createCell(1);
      cell.setCellStyle(cellStyle);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("Precision");

      cell = row.createCell(2);
      cell.setCellStyle(cellStyle);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("Recall");

      cell = row.createCell(3);
      cell.setCellStyle(cellStyle);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("F-Measure");

      cell = row.createCell(4);
      cell.setCellStyle(cellStyle);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("AverageP");

      cell = row.createCell(5);
      cell.setCellStyle(cellStyle);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("RR");

      cell = row.createCell(6);
      cell.setCellStyle(cellStyle);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue("NDCG");

      return workbook;
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

      DBCursor cursor = Facade.db.getCollection("groundtruth").find();
      Set<String> returnSet = new HashSet<String>();

      while (cursor.hasNext()) {

         Groundtruth user = Converter.toObject(Groundtruth.class, cursor.next());
         if (user.getAnswers().get(queryId - 1).getAnswer() > 5)
            returnSet.add(user.getUserId().toString());
      }

      return returnSet;
   }
}
