/*
 * @(#)FacebookSport.java   1.0   15/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.facebook;

import static java.util.Collections.unmodifiableList;
import it.expertfinding.datamodel.resources.facebook.FacebookNamedObject;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Facebook;

public class FacebookSport extends FacebookNamedObject {

   @Facebook
   private final List<FacebookNamedObject> with = new ArrayList<FacebookNamedObject>();

   /**
    * Friends associated with this sport.
    * 
    * @return Friends associated with this sport.
    */
   public List<FacebookNamedObject> getWith() {
      return unmodifiableList(with);
   }

}
