/*
 * @(#)TestProcessor.java   1.0   24/giu/2012
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

import java.util.ArrayList;
import java.util.List;

public class TestProcessor {

   private static String[] methods = {"textual", "probabilistic"};
   private static String[] lan = {"en", "it"};
   private static String[] entityType = {"no", "entityOnly", "yes"};
   private static String[] relevantsMethods = {"avg", "avg+dev"};
   private static String[] retrievedResources = {"50", "10%"};
   private static String[] resourceLevels = {"profiles", "text" , "textShort"};
   private static List<Channel> cs = new ArrayList<Channel>();
   private static final String baseFilePath = "C:\\Users\\Giuliano\\Dropbox\\Crowdsearch-Vesci-Silvestri-Thesis\\lastTests\\";

   public static void main(String[] args){

      //new PrecisionRecallExcelCalculatorDomains(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\prova\\prova.xls", "groundtruth");
      testJointVerticalization();
//      cs.add(Channel.TWITTER);
//      String[] groundtruths = {"Business", "ComputerEng", "Location", "MoviesAndTV", "Music", "Science", "Sport", "TechAndGames"}; 
//      
//      for(String groundtruth : groundtruths)
//         new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\DomainSplittedTests\\perSocialNetwork\\"+cs.get(0).toString().toLowerCase()+"\\"+groundtruth+".xls", "groundtruth"+groundtruth);
//   
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\DomainSplittedTests\\ComupterEngineering.xls", "groundtruthComputerEng");
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\DomainSplittedTests\\Science.xls", "groundtruthScience");
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\DomainSplittedTests\\Sport.xls", "groundtruthSport");

//      nineTextualConfigurationsEnTest(methods[0], lan[0],retrievedResources[0], relevantsMethods[0], "NineTextualConfigurationsEnglish\\");
//      nineTextualConfigurationsItTest(methods[0], lan[1],retrievedResources[0], relevantsMethods[0], "NineTextualConfigurationsItalian\\");
//      
//      nineTextualConfigurationsEnTest(methods[0], lan[0], retrievedResources[0], relevantsMethods[1], "NineTextualConfigurationsWithDevStEnglish\\");
//      nineTextualConfigurationsEnTest(methods[0], lan[1], retrievedResources[0], relevantsMethods[1], "NineTextualConfigurationsWithDevStItalian\\");
   }
   
   private static void testVerticalization(){
      
      cs.add(Channel.LINKEDIN);
      //new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[0],resourceLevels[1],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\channelTests\\FacebookLv1TextOnly.xls", "groundtruth");
      new PrecisionRecallExcelCalculatorDomains(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\channelTests\\LinkedinLv2Mixed_new.xls", "groundtruth");
      //new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\channelTests\\LinkedinLv2Mixed_old.xls", "groundtruth");
      
//       new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\channelTests\\LinkedinLv2Mixed.xls", "groundtruth");
//       new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\channelTests\\TwitterLv2Mixed.xls", "groundtruth");

   }
   
   private static void testJointVerticalization(){
      
      cs.add(Channel.TWITTER);
       String[] groundtruths = {"ComputerEng", "Location", "MoviesAndTV", "Music", "Science", "Sport", "TechAndGames"}; 
       
       for(String groundtruth : groundtruths){
//          new PrecisionRecallExcelCalculatorDomains(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\DomainSplittedTests\\"+groundtruth+"-Lv2Mixed.xls", "groundtruth"+groundtruth);
//          new PrecisionRecallExcelCalculatorDomains(methods[0],lan[0], cs, entityType[0],resourceLevels[1],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\DomainSplittedTests\\perSocialNetwork\\"+cs.get(0).toString().toLowerCase()+"\\"+groundtruth+"-Lv1TextOnly.xls", "groundtruth"+groundtruth);
            new PrecisionRecallExcelCalculatorDomains(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],baseFilePath+"\\DomainSplittedTests\\perSocialNetwork\\"+cs.get(0).toString().toLowerCase()+"\\"+groundtruth+"-Lv2Mixed.xls", "groundtruth"+groundtruth);
       }

   }
   
//   private static void nineTextualConfigurationsEnTest(String method, String language, String retrievedR, String relevantMethod, String folder){
//            
//      StringBuilder fileName = new StringBuilder(baseFilePath);
//      
//      fileName.append(folder);
//      for(String entity : entityType){
//         fileName.append("Entity_"+entity);
//         for(String resourceLevel: resourceLevels){
//            fileName.append("ResourceLevel_").append(resourceLevel).append(".xls");
//            new PrecisionRecallExcelCalculator(method, language, cs,
//                  entity, resourceLevel, retrievedR,
//                  relevantMethod, fileName.toString());
//         }
//      }
//   }
//   
//   private static void nineTextualConfigurationsItTest(String method, String language, String retrievedR, String relevantMethod, String folder){
//            
//      StringBuilder fileName = new StringBuilder(baseFilePath);
//      
//      fileName.append(folder);
//      for(String entity : entityType){
//         fileName.append("Entity_"+entity);
//         for(String resourceLevel: resourceLevels){
//            fileName.append("ResourceLevel_").append(resourceLevel).append(".xls");
//            new PrecisionRecallExcelCalculator(method, language, cs,
//                  entity, resourceLevel, retrievedR,
//                  relevantMethod, fileName.toString());
//         }
//      }
//   }
//   
//   private static void probabilisticTest(){
//
//      String method = methods[1];
//      String language = lan[0];
//      String retrievedR = retrievedResources[0];
//      String entity = entityType[0];
//      String resourceLevel = resourceLevels[2];
//
//      StringBuilder fileName = new StringBuilder(baseFilePath);
//      fileName.append("UserBased\\")
//      .append("userBasedAvg.xls");
//      new PrecisionRecallExcelCalculator(method, language, cs,
//            entity, resourceLevel, retrievedR,
//            relevantsMethods[0], fileName.toString());
//
//      fileName = new StringBuilder(baseFilePath);
//      fileName.append("UserBased\\")
//      .append("userBasedAvgDevSt.xls");
//      new PrecisionRecallExcelCalculator(method, language, cs,
//            entity, resourceLevel, retrievedR,
//            relevantsMethods[1], fileName.toString());
//   }  
//   
//   private static void percentageTest(){
//      
//      StringBuilder fileName;
//      for(int i=1;i<=20;i++){
//         fileName = new StringBuilder(baseFilePath)
//         .append("resourcesPercentageTest\\")
//         .append(i).append("%PercentageResourcesTest.xls");
//         new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],i+"%",relevantsMethods[0],fileName.toString());
//      }
//   }
//   
//   private static void channelsTest(){
//
//      StringBuilder fileName  = new StringBuilder(baseFilePath);
//      fileName.append("channelTests\\");
//
//      cs.add(Channel.FACEBOOK);     
//      StringBuilder fileName2 = new StringBuilder(fileName);
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[0],resourceLevels[1],retrievedResources[0],relevantsMethods[0],fileName2.append("Facebook-Entity_no-ResourceLevel1.xls").toString());
//      fileName2 = new StringBuilder(fileName);
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],fileName2.append("Facebook-Entity_mixed-ResourceLevel2.xls").toString());
//    
//      cs = new ArrayList<Channel>();
//      cs.add(Channel.TWITTER);
//      fileName2 = new StringBuilder(fileName);
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[0],resourceLevels[1],retrievedResources[0],relevantsMethods[0],fileName2.append("Twitter-Entity_no-ResourceLevel1.xls").toString());
//      fileName2 = new StringBuilder(fileName);
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],fileName2.append("Twitter-Entity_mixed-ResourceLevel2.xls").toString());
//
//      
//      cs = new ArrayList<Channel>();
//      cs.add(Channel.LINKEDIN);
//      fileName2 = new StringBuilder(fileName);
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[0],resourceLevels[1],retrievedResources[0],relevantsMethods[0],fileName2.append("Linkedin-Entity_no-ResourceLevel1.xls").toString());
//      fileName2 = new StringBuilder(fileName);
//      new PrecisionRecallExcelCalculator(methods[0],lan[0], cs, entityType[2],resourceLevels[2],retrievedResources[0],relevantsMethods[0],fileName2.append("Linkedin-Entity_mixed-ResourceLevel2.xls").toString());
//   }
}