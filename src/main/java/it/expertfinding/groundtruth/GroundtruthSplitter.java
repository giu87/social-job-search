/*
 * @(#)GroundtruthSplitter.java   1.0   25/giu/2012
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.DBCursor;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

public class GroundtruthSplitter {

   public static void main(String[] args){

      DBCursor cursor = Facade.db.getCollection("groundtruth").find();
      while(cursor.hasNext()){
      
         Groundtruth user = Converter.toObject(Groundtruth.class, cursor.next());
         List<Answer>  answers = new ArrayList<Answer>();

         Set<Integer> questionsOfDomain = new HashSet<Integer>();
         questionsOfDomain.add(13);
         questionsOfDomain.add(18);
         questionsOfDomain.add(11);
         
         for(int i=0;i<30;i++){
            
            if(questionsOfDomain.contains(user.getAnswers().get(i).getQuestionId())){
               answers.add(user.getAnswers().get(i));
            }               
         }
         
         Facade.db.getCollection("groundtruthScience").insert(Converter.toDBObject(new Groundtruth(user.getUserId(),answers)));
      }
   }
}