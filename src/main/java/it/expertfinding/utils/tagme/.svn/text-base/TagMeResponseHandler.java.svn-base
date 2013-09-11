/*
 * @(#)TagMeResponseHandler.java   1.0   21/feb/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.tagme;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TagMeResponseHandler extends DefaultHandler {

   boolean bSpot = false;
   boolean bTitle = false;
   boolean bId = false;
   boolean bRho = false;

   String spot = new String("");
   String title = new String("");
   String id = new String("");

   float threshold;

   public TagMeResponseHandler(Float threshold) {
      super();
      this.threshold = threshold;
   }

   List<TagMeAnnotation> namedEntities = new ArrayList<TagMeAnnotation>();

   @Override
   public void startElement(String uri, String localName, String gName, Attributes attributes)
         throws SAXException {
      System.out.println("Start Element :" + gName);
      if (gName.equalsIgnoreCase("spot"))
         bSpot = true;
      if (gName.equalsIgnoreCase("title"))
         bTitle = true;
      if (gName.equalsIgnoreCase("id"))
         bId = true;
      if (gName.equalsIgnoreCase("rho"))
         bRho = true;

   }

   @Override
   public void characters(char ch[], int start, int length) throws SAXException {
      if (bSpot) {

         System.out.println("Spot: " + new String(ch, start, length));
         spot += new String(ch, start, length);
      }
      if (bTitle) {

         System.out.println("Title: " + new String(ch, start, length));
         title += new String(ch, start, length);
      }
      if (bId) {

         System.out.println("Id: " + new String(ch, start, length));
         id += new String(ch, start, length);
      }

      if (bRho) {
         System.out.println("Rho: " + new String(ch, start, length));
         float rho = Float.parseFloat(new String(ch, start, length).replace(",", "."));
         if (rho >= threshold) {
            TagMeAnnotation annotation = new TagMeAnnotation(spot, title, id, rho);
            namedEntities.add(annotation);
         }
      }

   }

   @Override
   public void endElement(String uri, String localName, String gName) throws SAXException {

      System.out.println("End Element :" + gName);
      if (bSpot) {
         bSpot = false;
      }
      if (bTitle) {
         bTitle = false;
      }
      if (bId) {
         bId = false;
      }
      if (bRho) {
         bRho = false;
         spot = new String("");
         title = new String("");
         id = new String("");
      }
   }

   public List<TagMeAnnotation> getNamedEntities() {
      return namedEntities;
   }

}
