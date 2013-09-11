/*
 * @(#)ShowResults.java   1.0   11/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.servlets;

import it.expertfinding.datamodel.Constants.Channel;
import it.expertfinding.datamodel.query.Query;
import it.expertfinding.utils.Facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

@SuppressWarnings("serial")
@WebServlet(name = "CalculateResults", urlPatterns = "/CalculateResults")
public class CalculateResults extends HttpServlet {

   public static void main(String[] args) {
      Query q = new Query("how i met your mother", "probabilistic", null, null, "yes", null,
            null);

      DBObject ob = Converter.toDBObject(q);
      // System.out.println(ob.keySet());
      // System.out.println(ob.get("text"));
      // System.out.println(ob.get("_id"));
      // System.out.println(ob.get("lan"));
      // System.out.println(ob.get("channels"));
      // System.out.println(ob.get("bestUsers"));
      // DBObject bestUsers = (DBObject) ob.get("bestUsers");
      // for (String a : bestUsers.keySet()) {
      //
      // System.out.println(a);
      // System.out.println(bestUsers.get(a));
      // DBObject currentUser = (DBObject) ((DBObject) bestUsers.get(a)).get("map");
      // for (String b : currentUser.keySet()) {
      // System.out.println(currentUser.get(b));
      //
      // }
      // }
      // System.out.println(ob.get("textType"));
      // System.out.println(ob.get("entities"));

      Facade.db.getCollection(Facade.TABLE_QUERY).insert(ob);
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {
      req.setCharacterEncoding("UTF-8");
      String query = req.getParameter("query");
      String lan = req.getParameter("language");
      String[] channels = req.getParameterValues("channels");
      String entityType = req.getParameter("entityType");
      String textType = req.getParameter("textType");
      String method = req.getParameter("method");

      List<Channel> cs = new ArrayList<Channel>();

      if (channels != null) {
         for (String channel : channels) {
            switch (channel) {
               case "all":
                  break;
               case "twitter":
                  cs.add(Channel.TWITTER);
                  break;
               case "facebook":
                  cs.add(Channel.FACEBOOK);
                  break;
               case "linkedin":
                  cs.add(Channel.LINKEDIN);
                  break;
            }
         }
      }

      // create a query instance
      Query q;
      if (method.equals("textual"))
         q = new Query(query, method, cs, lan, entityType, textType, "50");
      // probabilistic method
      else
         q = new Query(query, method, null, null, "yes", null, null);

      Facade.db.getCollection(Facade.TABLE_QUERY).insert(Converter.toDBObject(q));
      req.setAttribute("query", q);

      this.getServletContext().getRequestDispatcher("/showResults.jsp").forward(req, resp);
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {
      doPost(req, resp);
   }
}
