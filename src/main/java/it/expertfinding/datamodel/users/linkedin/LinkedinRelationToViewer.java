/*
 * @(#)LinkedinRelationToViewer.java   1.0   17/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinCodeField;
import it.expertfinding.utils.linkedin.Linkedin;

import java.util.ArrayList;
import java.util.List;

public class LinkedinRelationToViewer {

   @Linkedin
   private LinkedinConnections relatedConnections;
   @Linkedin
   private Long distance;
   @Linkedin
   private LinkedinMembershipState membershipState;
   @Linkedin
   private boolean isFollowing;
   @Linkedin
   private boolean isLiked;
   @Linkedin
   private LinkedinAvailableActions availableActions;

   public LinkedinConnections getRelatedConnections() {
      return this.relatedConnections;
   }

   public void setRelatedConnections(LinkedinConnections relatedConnections) {
      this.relatedConnections = relatedConnections;
   }

   public Long getDistance() {
      return this.distance;
   }

   public void setDistance(Long distance) {
      this.distance = distance;
   }

   public LinkedinMembershipState getMembershipState() {
      return this.membershipState;
   }

   public void setMembershipState(LinkedinMembershipState membershipState) {
      this.membershipState = membershipState;
   }

   public boolean isFollowing() {
      return this.isFollowing;
   }

   public void setFollowing(boolean isFollowing) {
      this.isFollowing = isFollowing;
   }

   public boolean isLiked() {
      return this.isLiked;
   }

   public void setLiked(boolean isLiked) {
      this.isLiked = isLiked;
   }

   public LinkedinAvailableActions getAvailableActions() {
      return this.availableActions;
   }

   public void setAvailableActions(LinkedinAvailableActions availableActions) {
      this.availableActions = availableActions;
   }

   public static class LinkedinAvailableActions {

      @Linkedin
      private List<LinkedinCodeField> actionList;
      @Linkedin("_total")
      private Long total;

      public List<LinkedinCodeField> getActionList() {
         if (actionList == null) {
            actionList = new ArrayList<LinkedinCodeField>();
         }
         return this.actionList;
      }

      public void setActionList(List<LinkedinCodeField> actionList) {
         this.actionList = actionList;
      }

      public Long getTotal() {
         return this.total;
      }

      public void setTotal(Long total) {
         this.total = total;
      }
   }

   public static class LinkedinMembershipState {

      @Linkedin
      private MembershipStateCode code;

      public MembershipStateCode getCode() {
         return this.code;
      }

      public void setCode(MembershipStateCode code) {
         this.code = code;
      }

      public static class MembershipStateCode {

         @Linkedin
         private String value;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

      }
   }
}
