/*
 * @(#)LinkedinPublication.java   1.0   18/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinCreator;
import it.expertfinding.datamodel.resources.linkedin.LinkedinCreators;
import it.expertfinding.datamodel.resources.linkedin.LinkedinNamedField;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinPublication {

   @Linkedin
   private LinkedinDate date;
   @Linkedin
   private String id;
   @Linkedin
   private String summary;
   @Linkedin
   private String title;
   @Linkedin
   private String url;
   @Linkedin
   private LinkedinCreators authors;
   @Linkedin
   private LinkedinNamedField publisher;

   public LinkedinDate getDate() {
      return date;
   }

   public void setDate(LinkedinDate value) {
      this.date = value;
   }

   public String getId() {
      return id;
   }

   public void setId(String value) {
      this.id = value;
   }

   public String getSummary() {
      return summary;
   }

   public void setSummary(String value) {
      this.summary = value;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String value) {
      this.title = value;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String value) {
      this.url = value;
   }

   public LinkedinCreators getAuthors() {
      return authors;
   }

   public void setAuthors(LinkedinCreators value) {
      this.authors = value;
   }

   public LinkedinNamedField getPublisher() {
      return publisher;
   }

   public void setPublisher(LinkedinNamedField value) {
      this.publisher = value;
   }

   public String getResourceText() {
      
      StringBuilder s = new StringBuilder();

      if(title != null)
         s.append(title).append(". ");
      if(summary != null)
         s.append(summary).append(". ");
      if(url != null)
         s.append(url).append(". ");
      if(publisher != null)
         s.append(publisher.getName()).append(". ");

      if(authors != null && authors.getTotal() > 0)
         for(LinkedinCreator author: authors.getCreatorsList())
            s.append(author.getName()).append(". ");
      
      return s.toString();

   }
}
