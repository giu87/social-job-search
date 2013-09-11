/*
 * @(#)AlchemyResponse.java   1.0   30/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils.alchemy;

public class AlchemyResponse {

   @Alchemy
   private String status;
   @Alchemy
   private String url;
   @Alchemy
   private String statusInfo;
   @Alchemy
   private String language;

   /**
    * @return Returns the status.
    */
   public String getStatus() {
      return this.status;
   }

   /**
    * @return Returns the url.
    */
   public String getUrl() {
      return this.url;
   }

   /**
    * @return Returns the statusInfo.
    */
   public String getStatusInfo() {
      return this.statusInfo;
   }

   /**
    * @return Returns the language.
    */
   public String getLanguage() {
      return this.language;
   }

   /**
    * @param status
    *           The status to set.
    */
   public void setStatus(String status) {
      this.status = status;
   }

   /**
    * @param url
    *           The url to set.
    */
   public void setUrl(String url) {
      this.url = url;
   }

   /**
    * @param statusInfo
    *           The statusInfo to set.
    */
   public void setStatusInfo(String statusInfo) {
      this.statusInfo = statusInfo;
   }

   /**
    * @param language
    *           The language to set.
    */
   public void setLanguage(String language) {
      this.language = language;
   }

}
