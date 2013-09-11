/*
 * @(#)AggiustatoreUserBased.java   1.0   24/giu/2012
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import com.mongodb.DBCursor;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;

public class AggiustatoreUserBased {

   protected static Logger log = Facade.log;
   protected static final String SERVER = "http://localhost:80/solr2/probCore";
   protected static final String TYPE = "textShort";

   public static void main(String[] args) {

      Set<String> usersInGroundTruth = new HashSet<String>();
      DBCursor cur = Facade.db.getCollection("groundtruth").find();
      while (cur.hasNext()) {
         usersInGroundTruth.add(cur.next().get("userId").toString());
      }

      SolrServer server = null;
      try {
         server = new CommonsHttpSolrServer(SERVER);
      } catch (MalformedURLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      SolrQuery all = new SolrQuery();
      all.setQuery("*:*");
      QueryResponse resp = null;
      try {
         resp = server.query(all);
      } catch (SolrServerException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      Long numFound = resp.getResults().getNumFound();
      all.setRows(numFound.intValue());
      all.setStart(0);
      all.set("sort", "id asc");
      all.setFields("id");
      try {
         resp = server.query(all);
      } catch (SolrServerException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      for (SolrDocument result : resp.getResults()) {
         String id = (String) result.get("id");

         if (!usersInGroundTruth.contains(id))
            try {
               server.deleteById(id);
            } catch (SolrServerException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
      }

   }

}
