/*
 * @(#)CategoryStatistics.java   1.0   09/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;


public class CategoryStatistics {

   public static void main(String[] args ){
      
      try {
         FileReader fr = new FileReader("C:/Users/Giuliano/prova2.txt");
         BufferedReader br = new BufferedReader(fr); 
         String s;
         StringBuilder jsonString = new StringBuilder();
         while((s = br.readLine()) != null) { 
            jsonString.append(s);
         }
         fr.close(); 

         //System.out.print(jsonString.toString());
         JsonArray json = new JsonArray(jsonString.toString());
         List<String> alchemy = new ArrayList<String>();
         List<String> real = new ArrayList<String>();
         Set<String> set = new HashSet<String>();
         
         for(int i=0;i<json.length();i++){
            
            alchemy.add(new JsonObject(json.get(i).toString()).getString("alchemyCategory"));
            real.add(new JsonObject(json.get(i).toString()).getString("realCategory"));
            set.add(new JsonObject(json.get(i).toString()).getString("alchemyCategory"));
            set.add(new JsonObject(json.get(i).toString()).getString("realCategory"));
         }
         
         Map<String, List<Integer>> test = new HashMap<String, List<Integer>>();
         
         for(String category: set){
            List<Integer> l = new ArrayList<Integer>();
            for(int i=0;i<4;i++) l.add(0);
            test.put(category, l);
         }
         
         
         for(String category : test.keySet()){
            
            for(int j=0;j<alchemy.size();j++){
               
               if( alchemy.get(j).equals(category) && category.equals(real.get(j))) // VERO POSITIVO
                  test.get(category).set(0, test.get(category).get(0) + 1);
               
               if( alchemy.get(j).equals(category) && !category.equals(real.get(j))) // FALSO POSITIVO
                  test.get(category).set(1, test.get(category).get(1) + 1);
               
               if( !alchemy.get(j).equals(category) && category.equals(real.get(j))) // FALSO NEGATIVO
                  test.get(category).set(2, test.get(category).get(2) + 1);

               if( !alchemy.get(j).equals(category) && !category.equals(real.get(j))) // VERO NEGATIVO
                  test.get(category).set(3, test.get(category).get(3) + 1);
            }
         }
         
         for(String category : test.keySet()){
            System.out.println(category.toUpperCase());
            System.out.println("vero positivo: "+test.get(category).get(0));
            System.out.println("falso positivo: "+test.get(category).get(1));
            System.out.println("falso negativo: "+test.get(category).get(2));
            System.out.println("vero negativo: "+test.get(category).get(3));
            System.out.println("totale: "+(test.get(category).get(0)+test.get(category).get(1)+test.get(category).get(2)+test.get(category).get(3)));
            System.out.println();
         }
         
         for(String category : test.keySet()){
            
            System.out.println(category.toUpperCase());
            System.out.println("vero positivo: "+test.get(category).get(0));
            System.out.println("falso positivo: "+test.get(category).get(1));
            System.out.println("falso negativo: "+test.get(category).get(2));
            System.out.println("vero negativo: "+test.get(category).get(3));
            Double precision = (double) test.get(category).get(0) / (double) (test.get(category).get(0) + test.get(category).get(1));
            System.out.println("PRECISION: "+ (precision * 100) +" %");
            Double recall = (double) test.get(category).get(0) / (double) (test.get(category).get(0) + test.get(category).get(2));
            System.out.println("RECALL: "+ (recall * 100) +" %");
            Double trueNegativeRate = (double) test.get(category).get(3) / (double) (test.get(category).get(3) + test.get(category).get(1));
            System.out.println("TRUE NEGATIVE RATE: "+ (trueNegativeRate * 100) +" %");
            Double accuracy = (double) (test.get(category).get(0) + test.get(category).get(3)) / (double) (test.get(category).get(0)+test.get(category).get(1)+test.get(category).get(2)+test.get(category).get(3));
            System.out.println("ACCURACY: "+ (accuracy * 100) +" %");
            Double fMeasure = precision * recall / (precision + recall) * 2;
            System.out.println("F-MEASURE: "+ (fMeasure * 100) +" %");
            System.out.println();

         }
            
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
}
