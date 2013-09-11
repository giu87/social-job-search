/*
 * @(#)LinkedinGroupMembership.java   1.0   18/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.datamodel.resources.linkedin.LinkedinNamedObject;
import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinGroupMembership {

   @Linkedin("_key")
   private String key;
   @Linkedin
   private LinkedinNamedObject group;
   @Linkedin
   private LinkedinMembership membershipState;

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public LinkedinNamedObject getGroup() {
      return this.group;
   }

   public void setGroup(LinkedinNamedObject group) {
      this.group = group;
   }

   public LinkedinMembership getMembershipState() {
      return this.membershipState;
   }

   public void setMembershipState(LinkedinMembership membershipState) {
      this.membershipState = membershipState;
   }

   public static class LinkedinMembership {

      @Linkedin
      private String code;

      public String getCode() {
         return this.code;
      }

      public void setCode(String code) {
         this.code = code;
      }
   }
}
