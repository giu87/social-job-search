/*
 * @(#)Groundtruth.java   1.0   30/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.groundtruth;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Groundtruth {

   private ObjectId _id;
   private ObjectId userId;
   private List<Answer> answers = new ArrayList<Answer>();

   public Groundtruth() {

   }

   public Groundtruth(ObjectId userId, List<Answer> answers) {
      this.userId = userId;
      this.answers = answers;
   }

   /**
    * @return Returns the _id.
    */
   public ObjectId get_id() {
      return this._id;
   }

   /**
    * @param _id
    *           The _id to set.
    */
   public void set_id(ObjectId _id) {
      this._id = _id;
   }

   public ObjectId getUserId() {
      return this.userId;
   }

   public void setUserId(ObjectId userId) {
      this.userId = userId;
   }

   public List<Answer> getAnswers() {
      return this.answers;
   }

   public void setAnswers(List<Answer> answers) {
      this.answers = answers;
   }
}