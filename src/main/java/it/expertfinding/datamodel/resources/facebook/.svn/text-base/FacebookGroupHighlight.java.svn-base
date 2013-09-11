/*
 * @(#)FacebookGroupHighlight.java   1.0   16/apr/2012
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

public class FacebookGroupHighlight extends FacebookNamedObject {

   @Facebook
   private Long version;

   @Facebook
   private Long unread;

   @Facebook("bookmark_order")
   private Long bookmarkOrder;
   @Facebook
   private Boolean administrator;

   public Long getVersion() {
      return this.version;
   }

   public void setVersion(Long version) {
      this.version = version;
   }

   public Long getUnread() {
      return this.unread;
   }

   public void setUnread(Long unread) {
      this.unread = unread;
   }

   public Long getBookmarkOrder() {
      return this.bookmarkOrder;
   }

   public void setBookmarkOrder(Long bookmarkOrder) {
      this.bookmarkOrder = bookmarkOrder;
   }

   public Boolean getAdministrator() {
      return this.administrator;
   }

   public void setAdministrator(Boolean administrator) {
      this.administrator = administrator;
   }

}
