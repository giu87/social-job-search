/*
 * @(#)FacebookPageHighlight.java   1.0   16/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.facebook;

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import java.util.Date;

import com.restfb.Facebook;

public class FacebookPageHighlight extends FacebookCategorizedObject {

   @Facebook("created_time")
   private String createdTime;

   public Date getCreatedTime() {
      return toDateFromLongFormat(createdTime);
   }

   public void setCreatedTime(Date createdTime) {
      this.createdTime = createdTime.toString();
   }
}
