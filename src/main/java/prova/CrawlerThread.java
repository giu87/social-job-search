/*
 * @(#)ProvaThread.java   1.0   08/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import it.expertfinding.datamodel.resources.AbstractResource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;


public class CrawlerThread extends RecursiveAction {

   private static final long serialVersionUID = 1L;
   boolean first;
   List<String> resources;
   List<String> results = new ArrayList<String>();   
   
   public CrawlerThread(boolean first, List<String> resources){

      this.resources = resources;
      this.first = first;
   }
   
   protected void compute() {
      
      if(!first){
         System.out.println(resources.get(0));
         results.add(resources.get(0));
      }
      else{
         
         ArrayList<CrawlerThread> crawlers = new ArrayList<CrawlerThread>();
         for(int i=0;i<8;i++) {
            crawlers.add(new CrawlerThread(false, resources.subList(i*resources.size()/8, (i+1)*resources.size()/8)));
         }
         invokeAll(crawlers);
         for(int i=0;i<8;i++) {
            results.addAll(crawlers.get(i).results);
         }
      }
   }
   
   class GainTextFromListOfResources {
      
      public List<AbstractResource> resources;

      public GainTextFromListOfResources(List<AbstractResource> resources){
         this.resources = resources;
      }
   }

}