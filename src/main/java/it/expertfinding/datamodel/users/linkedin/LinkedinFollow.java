/*
 * @(#)LinkedinToFollow.java   1.0   26/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinObjectsList;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinFollow {

   @Linkedin
   private LinkedinObjectsList companies;
   @Linkedin
   private LinkedinObjectsList industries;
   @Linkedin
   private LinkedinObjectsList newsSources;
   @Linkedin
   private LinkedinObjectsList people;

   public LinkedinObjectsList getCompanies() {
      return this.companies;
   }

   public void setCompanies(LinkedinObjectsList companies) {
      this.companies = companies;
   }

   public LinkedinObjectsList getIndustries() {
      return this.industries;
   }

   public void setIndustries(LinkedinObjectsList industries) {
      this.industries = industries;
   }

   public LinkedinObjectsList getNewsSources() {
      return this.newsSources;
   }

   public void setNewsSources(LinkedinObjectsList newsSources) {
      this.newsSources = newsSources;
   }

   public LinkedinObjectsList getPeople() {
      return this.people;
   }

   public void setPeople(LinkedinObjectsList people) {
      this.people = people;
   }
}
