/*
 * @(#)TagMeUtils.java   1.0   21/feb/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.tagme;

import it.expertfinding.utils.Facade;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.xml.sax.InputSource;

public class TagMeUtils {

   protected static Logger log = Facade.log;
   protected static Configuration conf = Facade.conf;

   public static List<TagMeAnnotation> getNamedEntities(String text, Float threshold) {
      return getNamedEntities(text, null, threshold);
   }

   public static List<TagMeAnnotation> getNamedEntities(String text, String lang,
         Float threshold) {

      URL url;
      HttpURLConnection connection = null;

      try {
         String queryUrl = conf.getString("tagme.url");
         StringBuilder data = new StringBuilder("key=").append(conf.getString("tagme.key"))
               .append("&text=").append(URLEncoder.encode(text, "UTF-8"));
         if (lang != null)
            data.append("&lang=").append(lang);
         log.debug(queryUrl);
         url = new URL(queryUrl);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("POST");
         connection.setDoOutput(true);
         OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
         os.write(data.toString());
         os.flush();

         // Get Response
         InputStream is = connection.getInputStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         String line;
         /** TODO handling incorrect response if any */
         StringBuffer response = new StringBuffer();
         while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
         }
         rd.close();
         log.debug("response: " + response);
         TagMeResponseHandler handler = new TagMeResponseHandler(threshold);
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser parser = factory.newSAXParser();
         InputSource iSource = new InputSource(new StringReader(response.toString()));
         iSource.setEncoding("UTF-8");
         parser.parse(iSource, handler);
         return handler.getNamedEntities();

      } catch (Exception e) {
         e.printStackTrace();
         return null;
      } finally {
         if (connection != null) {
            connection.disconnect();
         }
      }

   }

   public static List<String> getNamedEntitiesString(String text, Float threshold) {
      List<TagMeAnnotation> annotations = getNamedEntities(text, threshold);
      List<String> stringAnnotation = new ArrayList<String>();

      for (TagMeAnnotation annotation : annotations) {
         stringAnnotation.add(annotation.getSpot());
      }

      return stringAnnotation;
   }
}
