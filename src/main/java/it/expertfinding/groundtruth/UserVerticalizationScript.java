/*
 * @(#)UserVerticalizationScript.java   1.0   27/giu/2012
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
import it.expertfinding.datamodel.users.CrowdUser;
import it.expertfinding.utils.Facade;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.DBCursor;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;


public class UserVerticalizationScript {

   public static void main(String[] args){
      
      
      List<Channel> cs = new ArrayList<Channel>();
//      cs.add(Channel.TWITTER);
      DBCursor c = Facade.db.getCollection(Facade.TABLE_USER).find();
      Map<String, CrowdUser> map = new LinkedHashMap<String, CrowdUser>();
      while(c.hasNext()){
         CrowdUser user = Converter.toObject(CrowdUser.class, c.next());
         map.put(user.get_id().toString(), user);
      }
      
      int cont = 0;
      List<String> set = PrecisionRecallExcelCalculatorDomains.deleteRetrievedNotInGroundtruth(new ArrayList(map.keySet()) , cs);
      for(String s : set){
         System.out.println(s);
         cont++;
      }
      System.out.println(cont);

//      
//      Set<String> list = PrecisionRecallExcelCalculatorDomains.deleteRetrievedNotInGroundtruth(map.keySet(), );
//      
//      System.out.println("number of users:"+list.size());
//      for(String user : list){
//         System.out.println(user);
//         if(map.get(user).getFacebook() != null)
//            System.out.print("FACEBOOK,");
//         if(map.get(user).getTwitter() != null)
//            System.out.print("TWITTER,");
//         if(map.get(user).getLinkedin() != null)
//            System.out.print("LINKEDIN");
//         System.out.println("");
//         System.out.println("");
//      }
   }
}
