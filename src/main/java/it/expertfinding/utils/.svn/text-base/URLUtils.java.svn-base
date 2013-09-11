package it.expertfinding.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

public class URLUtils {

   protected static Logger log = Facade.log;

   public static String getURLContent(String url) {

      BufferedReader in = null;
      URL u;
      String s = "";

      try {

         if (!url.startsWith("http"))
            url = "http://" + url;
         u = new URL(url);
         in = new BufferedReader(new InputStreamReader(u.openStream(), "UTF-8"));

         String inputLine;

         while ((inputLine = in.readLine()) != null)
            s += inputLine;
         // log.debug("URL CONTENT:" + s);
         return s;

      } catch (MalformedURLException mue) {

         log.debug("MalformedURLException happened.");
         mue.printStackTrace();
         return "";

      } catch (IOException ioe) {

         log.debug("IOException happened.");
         ioe.printStackTrace();
         return "";

      } catch (Exception e) {
         log.debug("Exception happened.");
         e.printStackTrace();
         return "";
      } finally {

         try {
            if (in != null)
               in.close();
         } catch (IOException ioe) {

         }

      }
   }

   public static ArrayList<String> getURLfromString(String text) {

      log.debug("Searching URLs in string: {}", text);
      ArrayList<String> result = new ArrayList<String>();

      Pattern pattern = Pattern.compile("\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)"
            + "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov"
            + "|mil|biz|info|mobi|name|aero|jobs|museum"
            + "|travel|[a-z]{2}))(:[\\d]{1,5})?"
            + "(((\\/([-\\w~#!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?"
            + "((\\?([-\\w~#!$+|.,*:]|%[a-f\\d{2}])+=?"
            + "([-\\w~#!$+|.,*:=]|%[a-f\\d]{2})*)"
            + "(&(?:[-\\w~#!$+|.,*:]|%[a-f\\d{2}])+=?"
            + "([-\\w~#!$+|.,*:=]|%[a-f\\d]{2})*)*)*"
            + "(#([-\\w~#!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

      Matcher matcher = pattern.matcher(text);
      while (matcher.find()) {
         if (!result.contains(matcher.group())) {
            log.debug("Found url: {}", matcher.group());
            result.add(matcher.group());
         }

      }

      log.debug("Stripped string without url: {}", matcher.replaceAll(""));
      result.add(0, matcher.replaceAll(""));
      return result;
   }

   @Deprecated
   public static ArrayList<String> getURLfromStringOld(String string) {

      ArrayList<String> links = new ArrayList<String>();
      StringBuilder strippedString = new StringBuilder();
      String[] parts = string.split("\\s");
      int i = 0;
      for (String item : parts)
         try {
            URL url = new URL(item);
            String l = url.toString();
            if (l.endsWith("/"))
               StringUtils.chop(l);
            boolean found = false;
            for (String link : links) {

               if (link.equals(l))
                  found = true;
            }
            if (!found) {
               links.add(l);
               i++;
            }

            strippedString.append("<URL");
            strippedString.append(i);
            strippedString.append("> ");
            // TODO Verify punctuation problems

         } catch (MalformedURLException e) {
            strippedString.append(item);
            strippedString.append(" ");
         }

      links.add(0, strippedString.toString());
      return links;

   }

}
