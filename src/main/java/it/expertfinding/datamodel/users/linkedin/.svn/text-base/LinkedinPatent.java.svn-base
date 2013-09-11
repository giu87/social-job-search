/*
 * @(#)LinkedinPatent.java   1.0   17/apr/2012
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
import it.expertfinding.datamodel.resources.linkedin.LinkedinNamedObject;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinPatent {

   @Linkedin
   private String id;
   @Linkedin
   private String title;
   @Linkedin
   private LinkedinDate date;
   @Linkedin
   private String url;
   @Linkedin
   private String summary;
   @Linkedin
   private String number;
   @Linkedin
   private LinkedinNamedObject status;
   @Linkedin
   private LinkedinNamedField office;
   @Linkedin
   private LinkedinCreators inventors;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public LinkedinDate getDate() {
      return this.date;
   }

   public void setDate(LinkedinDate date) {
      this.date = date;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getSummary() {
      return this.summary;
   }

   public void setSummary(String summary) {
      this.summary = summary;
   }

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public LinkedinNamedObject getStatus() {
      return this.status;
   }

   public void setStatus(LinkedinNamedObject status) {
      this.status = status;
   }

   public LinkedinNamedField getOffice() {
      return this.office;
   }

   public void setOffice(LinkedinNamedField office) {
      this.office = office;
   }

   public LinkedinCreators getInventors() {
      return this.inventors;
   }

   public void setInventors(LinkedinCreators inventors) {
      this.inventors = inventors;
   }

   public String getResourceText() {

      StringBuilder s = new StringBuilder();

      if(title != null)
         s.append(title).append(". ");
      if(summary != null)
         s.append(summary).append(". ");
      if(url != null)
         s.append(url).append(". ");
      if(status != null)
         s.append(status.getName()).append(". ");
      if(office != null)
         s.append(office.getName()).append(". ");
      if(inventors != null && inventors.getTotal() > 0)
         for(LinkedinCreator inventor: inventors.getCreatorsList())
            s.append(inventor.getName()).append(". ");
      
      return s.toString();
      
   }
}
