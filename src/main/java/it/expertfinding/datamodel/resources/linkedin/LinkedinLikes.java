/*
 * @(#)LinkedinLikes.java   1.0   18/apr/2012
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

public class LinkedinLikes {

   @Linkedin("values")
   private List<LinkedinLike> likesList;
   @Linkedin("_total")
   protected Long total;

   public List<LinkedinLike> getLikesList() {
      if (likesList == null) {
         likesList = new ArrayList<LinkedinLike>();
      }
      return this.likesList;
   }

   public void setLikesList(List<LinkedinLike> likesList) {
      this.likesList = likesList;
   }

   public Long getTotal() {
      return this.total;
   }

   public void setTotal(Long total) {
      this.total = total;
   }
}
