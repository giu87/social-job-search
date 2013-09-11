/*
 * @(#)LinkedinComment.java   1.0   26/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.resources.linkedin;

import it.expertfinding.datamodel.users.linkedin.LinkedinRelationToViewer;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinComment {

   @Linkedin
   private Long creationTimestamp;
   @Linkedin
   private LinkedinUserInfo creator;
   @Linkedin
   private String id;
   @Linkedin
   private LinkedinRelationToViewer relationToViewer;
   @Linkedin
   private String text;

   public Long getCreationTimestamp() {
      return this.creationTimestamp;
   }

   public void setCreationTimestamp(Long creationTimestamp) {
      this.creationTimestamp = creationTimestamp;
   }

   public LinkedinUserInfo getCreator() {
      return this.creator;
   }

   public void setCreator(LinkedinUserInfo creator) {
      this.creator = creator;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public LinkedinRelationToViewer getRelationToViewer() {
      return this.relationToViewer;
   }

   public void setRelationToViewer(LinkedinRelationToViewer relationToViewer) {
      this.relationToViewer = relationToViewer;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }
}
