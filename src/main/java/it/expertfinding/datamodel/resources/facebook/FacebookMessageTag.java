/*
 * @(#)FacebookMessageTag.java   1.0   14/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.facebook;

import com.restfb.Facebook;

public class FacebookMessageTag extends FacebookTypedObject {

   @Facebook
   private Long offset = 0L;
   @Facebook
   private Long length = 0L;

   public Long getOffset() {
      return this.offset;
   }

   public void setOffset(Long offset) {
      this.offset = offset;
   }

   public Long getLength() {
      return this.length;
   }

   public void setLength(Long length) {
      this.length = length;
   }

}
