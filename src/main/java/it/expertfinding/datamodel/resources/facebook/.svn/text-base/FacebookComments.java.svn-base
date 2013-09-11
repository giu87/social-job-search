/*
 * @(#)FacebookComments.java   1.0   16/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.facebook;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

public class FacebookComments {

   @Facebook
   private Long count;

   @Facebook
   private List<FacebookComment> data = new ArrayList<FacebookComment>();

   /**
    * The number of data elements.
    * 
    * @return The number of data elements.
    */
   public Long getCount() {
      return this.count;
   }

   public void setCount(Long count) {
      this.count = count;
   }

   /**
    * The data elements.
    * 
    * @return The data elements.
    */
   public List<FacebookComment> getData() {
      return this.data;
   }

   public void setData(List<FacebookComment> data) {
      this.data = data;
   }

}
