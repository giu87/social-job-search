/*
 * @(#)LinkedinSkills.java   1.0   18/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.utils.linkedin.Linkedin;

import java.util.ArrayList;
import java.util.List;

public class LinkedinSkills {

   @Linkedin("values")
   protected List<LinkedinSkill> skillsList;
   @Linkedin("_total")
   protected Long total;

   public List<LinkedinSkill> getSkillsList() {
      if (skillsList == null) {
         skillsList = new ArrayList<LinkedinSkill>();
      }
      return this.skillsList;
   }

   public void setSkillsList(List<LinkedinSkill> skillsList) {
      this.skillsList = skillsList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
}
