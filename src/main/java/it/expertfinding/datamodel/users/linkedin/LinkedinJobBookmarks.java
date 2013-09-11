/*
 * @(#)LinkedinJobBookmarks.java   1.0   26/apr/2012
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

public class LinkedinJobBookmarks {

   @Linkedin("values")
   private List<LinkedinJobBookmark> jobBookmarksList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinJobBookmark> getJobBookmarksList() {
      if (jobBookmarksList == null) {
         jobBookmarksList = new ArrayList<LinkedinJobBookmark>();
      }
      return this.jobBookmarksList;
   }

   public void setJobBookmarksList(List<LinkedinJobBookmark> jobBookmarksList) {
      this.jobBookmarksList = jobBookmarksList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
}
