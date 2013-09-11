/*
 * @(#)EntitiesStatistics.java   1.0   05/giu/2012
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
import it.expertfinding.utils.freebase.Entity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

public class EntitiesStatistics {

   @SuppressWarnings({ "unchecked", "rawtypes" })
   private static Map sortByComparator(Map unsortMap) {

      List list = new LinkedList(unsortMap.entrySet());

      // sort list based on comparator
      Collections.sort(list, new Comparator() {

         @Override
         public int compare(Object o1, Object o2) {
            return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2))
                  .getValue());
         }
      });

      // put sorted list into map again
      Map sortedMap = new LinkedHashMap();
      for (Iterator it = list.iterator(); it.hasNext();) {
         Map.Entry entry = (Map.Entry) it.next();
         sortedMap.put(entry.getKey(), entry.getValue());
      }
      return sortedMap;
   }

   @SuppressWarnings("unchecked")
   public static void main(String[] args) throws IOException {
      DBCollection eColl = Facade.db.getCollection(Facade.TABLE_ENTITIES);

      DBCursor cur = eColl.find().batchSize(5);

      LinkedHashMap<String, Integer> statistics = new LinkedHashMap<String, Integer>();

      while (cur.hasNext()) {

         DBObject next = cur.next();
         Entity ent = Converter.toObject(Entity.class, next);

         List<String> domains = ent.getDomains();

         for (int i = 0; i < 3; i++) {
            if (domains.size() > i) {
               String curDom = domains.get(i);
               if (statistics.containsKey(curDom))
                  statistics.put(curDom, statistics.get(curDom) + 1);
               else
                  statistics.put(curDom, 1);
            }
         }

      }

      Map<String, Integer> sortedMap = sortByComparator(statistics);
      FileWriter writer = new FileWriter("/Users/Teo/Desktop/entitiesStatistics.txt", true);
      BufferedWriter w = new BufferedWriter(writer);
      for (Entry<String, Integer> entry : sortedMap.entrySet()) {
         w.write(entry.getKey() + ": " + entry.getValue());
         w.newLine();
         w.flush();

      }
      w.close();

   }
}
