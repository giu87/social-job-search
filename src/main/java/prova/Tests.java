/*
 * @(#)Tests.java   1.0   05/giu/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import it.expertfinding.datamodel.resources.linkedin.LinkedinResource;
import it.expertfinding.utils.Facade;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;


public class Tests {

   public static void main(String[] args){

      DBCursor cur = Facade.db.getCollection("linkedin").find(new BasicDBObject("solrId",null));
      
      while(cur.hasNext()){
         
         LinkedinResource l = Converter.toObject(LinkedinResource.class, cur.next());
         //if(l.getSolrId() == null)
            
      }
      
//      DBCursor cur = Facade.db.getCollection("groundtruth").find();
//      while(cur.hasNext()){
//         Groundtruth user = Converter.toObject(Groundtruth.class, cur.next());
//         for(Answer a : user.getAnswers())
//            if(a.getQuestionId() == 1){
//               CrowdUser u = Converter.toObject(CrowdUser.class, Facade.db.getCollection(Facade.TABLE_USER).findOne(new BasicDBObject("_id",user.getUserId())));
//               if(u.getFacebook() != null)
//                  System.out.println("USER: "+u.getFacebook().getFirstName()+" "+u.getFacebook().getLastName()+", answer = "+a.getAnswer());
//               else
//                  System.out.println("USER: "+u.getLinkedin().getFirstName()+" "+u.getLinkedin().getLastName()+", answer = "+a.getAnswer());
//            }
//      }
   }
}
