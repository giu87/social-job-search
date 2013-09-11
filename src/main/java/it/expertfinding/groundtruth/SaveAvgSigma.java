/*
 * @(#)SaveAvgSigma.java   1.0   24/giu/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.groundtruth;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.DBCursor;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import it.expertfinding.utils.Facade;


public class SaveAvgSigma {

   public static void main(String[] args){
    
      Groundtruth groundtruthUser = Converter.toObject(Groundtruth.class, Facade.db
            .getCollection("groundtruth").findOne());

      Integer total;
      int count = 0;
      Double avg, sigma, avgQuadro, totalQuadro;
      for (Answer a : groundtruthUser.getAnswers()) {

         Integer queryId = a.getQuestionId();
         Map<String, Integer> userAnswers = new HashMap<String, Integer>();
         DBCursor cursor = Facade.db.getCollection("groundtruth").find();

         total = 0;
         totalQuadro = 0D;
         while (cursor.hasNext()) {
               
            Groundtruth user = Converter.toObject(Groundtruth.class, cursor.next());

            Integer answer = user.getAnswers().get(queryId - 1).getAnswer();            
            total += answer;
            totalQuadro += Math.pow(answer, 2);
            userAnswers.put(user.getUserId().toString(), answer);
         }

         avg = (double) total / (double) userAnswers.size();
         avgQuadro = (double) totalQuadro / (double) userAnswers.size();
         sigma = Math.sqrt(avgQuadro - Math.pow(avg, 2));
         
         Facade.db.getCollection("queryParameters").insert(Converter.toDBObject(new QueryParameter(queryId, avg, sigma)));
      }
   }
}
