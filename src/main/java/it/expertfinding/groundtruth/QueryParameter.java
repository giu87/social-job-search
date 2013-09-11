/*
 * @(#)QueryParameter.java   1.0   24/giu/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.groundtruth;

import org.bson.types.ObjectId;


public class QueryParameter {

   ObjectId _id;
   Integer id;
   Double avg;
   Double sigma;
   
   public QueryParameter(){}
   
   public QueryParameter(Integer id, Double avg, Double sigma){
      
      this.id = id;
      this.avg = avg;
      this.sigma = sigma;
   }
   
   public ObjectId get_id() {
      return this._id;
   }
   
   public void set_id(ObjectId _id) {
      this._id = _id;
   }   
   
   public Integer getId() {
      return this.id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   
   public Double getAvg() {
      return this.avg;
   }
   
   public void setAvg(Double avg) {
      this.avg = avg;
   }
   
   public Double getSigma() {
      return this.sigma;
   }
   
   public void setSigma(Double sigma) {
      this.sigma = sigma;
   }
}
