/*
 * @(#)LinkedinGroupMemberships.java   1.0   18/apr/2012
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

public class LinkedinGroupMemberships {

   @Linkedin("values")
   private List<LinkedinGroupMembership> groupMembershipsList;
   @Linkedin("_total")
   private Long total;
   @Linkedin("_count")
   private Long count;
   @Linkedin("_start")
   private Long start;

   public List<LinkedinGroupMembership> getGroupMembershipsList() {
      if (groupMembershipsList == null) {
         groupMembershipsList = new ArrayList<LinkedinGroupMembership>();
      }
      return this.groupMembershipsList;
   }

   public void setGroupMembershipsList(List<LinkedinGroupMembership> groupMembershipsList) {
      this.groupMembershipsList = groupMembershipsList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }

   public Long getCount() {
      return this.count;
   }

   public void setCount(Long count) {
      this.count = count;
   }

   public Long getStart() {
      return this.start;
   }

   public void setStart(Long start) {
      this.start = start;
   }
}
