/*
 * @(#)LinkedinComments.java   1.0   26/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.linkedin;

import it.expertfinding.utils.linkedin.Linkedin;

import java.util.ArrayList;
import java.util.List;

public class LinkedinComments {

   @Linkedin("values")
   private List<LinkedinComment> commentsList;
   @Linkedin("_total")
   private Long total;

   public List<LinkedinComment> getCommentsList() {
      if (commentsList == null) {
         commentsList = new ArrayList<LinkedinComment>();
      }
      return this.commentsList;
   }

   public void setCommentsList(List<LinkedinComment> commentsList) {
      this.commentsList = commentsList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
}
