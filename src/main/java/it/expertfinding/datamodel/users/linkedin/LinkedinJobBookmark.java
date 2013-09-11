/*
 * @(#)LinkedinJobBookmark.java   1.0   26/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.JobBookmarkField;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinJobBookmark {

   @Linkedin
   private boolean isApplied;
   @Linkedin
   private boolean isSaved;
   @Linkedin
   private JobBookmarkField job;
   @Linkedin
   private Long savedTimestamp;

   public boolean isApplied() {
      return this.isApplied;
   }

   public void setApplied(boolean isApplied) {
      this.isApplied = isApplied;
   }

   public boolean isSaved() {
      return this.isSaved;
   }

   public void setSaved(boolean isSaved) {
      this.isSaved = isSaved;
   }

   public JobBookmarkField getJob() {
      return this.job;
   }

   public void setJob(JobBookmarkField job) {
      this.job = job;
   }

   public Long getSavedTimestamp() {
      return this.savedTimestamp;
   }

   public void setSavedTimestamp(Long savedTimestamp) {
      this.savedTimestamp = savedTimestamp;
   }
}
