/*
 * @(#)UserDetailAjax.java   1.0   11/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.servlets;

import it.expertfinding.datamodel.query.Query;
import it.expertfinding.datamodel.resources.AbstractResource;
import it.expertfinding.datamodel.resources.facebook.FacebookResource;
import it.expertfinding.datamodel.resources.linkedin.LinkedinResource;
import it.expertfinding.datamodel.resources.twitter.TwitterResource;
import it.expertfinding.utils.Facade;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@SuppressWarnings("serial")
@WebServlet(name = "ResourceDetailAjax", urlPatterns = "/ResourceDetailAjax")
public class ResourceDetailAjax extends HttpServlet {

   @Override
   public void init(ServletConfig config) throws ServletException {
      super.init(config);
   }

   @Override
   public void destroy() {

   }

   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");

      PrintWriter out = response.getWriter();

      String id_text = request.getParameter("id");
      String qId = request.getParameter("qId");
      String resource = id_text.substring(id_text.length() - 1, id_text.length());
      String id = StringUtils.chop(id_text);

      Facade.log.debug("id_text = " + id_text);
      Facade.log.debug("id = " + id);
      Facade.log.debug("qId = " + qId);

      AbstractResource res;

      if (resource.equals("T"))
         res = Converter.toObject(
               TwitterResource.class,
               Facade.db.getCollection(Facade.TABLE_TWITTER).findOne(
                     new BasicDBObject("_id", Long.parseLong(id))));
      else if (resource.equals("F"))
         res = Converter.toObject(
               FacebookResource.class,
               Facade.db.getCollection(Facade.TABLE_FACEBOOK).findOne(
                     new BasicDBObject("_id", id)));
      else
         res = Converter.toObject(
               LinkedinResource.class,
               Facade.db.getCollection(Facade.TABLE_LINKEDIN).findOne(
                     new BasicDBObject("_id", id)));
      String text = res.getResourceText();
      String rType = res.getrType().toString();
      String category = res.getAlchemyCategory();

      Facade.log.debug("executing query...");
      Query q = Converter.toObject(Query.class, Facade.db.getCollection(Facade.TABLE_QUERY)
            .findOne(new BasicDBObject("_id", new ObjectId(qId))));
      Facade.log.debug("query executed");
      Facade.log.debug("numero utenti = " + q.getBestUsers().size());

      StringBuilder s = new StringBuilder();
      s.append("<h3>Resource Detail</h3><h4>Resource Type</h4><div>").append(rType)
            .append("</div>").append("<h4>Resource Id</h4><div>").append(id)
            .append("</div><h4>Category</h4><div>").append(category)
            .append("</div><h4>Highlights</h4>").append("<div id=\"highlights\">")
            .append("<ul>");

      Whitelist clean = Whitelist.simpleText().addTags("em");
      for (String h : q.getAllResources().get(id).getHighlights()) {
         s.append("<li>").append(Jsoup.clean(Jsoup.parse(h).text(),clean)).append("</li>");
      }

      s.append("</ul>").append("</div>").append("<h4>Resource text</h4>").append("<div>")
            .append(Jsoup.parse(text).text()).append("</div>");
      out.println(s.toString().replace("<em>", "<strong>").replace("</em>", "</strong>"));
      Facade.log.debug(s.toString());
   }

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
      Facade.log.debug("executing get Method");
      doPost(request, response);
   }
}
