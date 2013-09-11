/*
 * @(#)TagMeAnnotation.java   1.0   07/mar/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.tagme;

public class TagMeAnnotation {

   private String spot;
   private String title;
   private String id;
   private Float rho;

   public TagMeAnnotation(String spot, String title, String id, Float rho) {
      super();
      this.spot = spot;
      this.title = title;
      this.id = id;
      this.rho = rho;
   }

   public String getSpot() {
      return this.spot;
   }

   public void setSpot(String spot) {
      this.spot = spot;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public Float getRho() {
      return this.rho;
   }

   public void setRho(Float rho) {
      this.rho = rho;
   }

}
