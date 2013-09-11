/*
 * @(#)LinkedinSiteStandardProfileRequest.java   1.0   17/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.users.linkedin;

import it.expertfinding.utils.linkedin.Linkedin;

import java.util.ArrayList;
import java.util.List;

public class LinkedinStandardProfileRequest {

   @Linkedin
   private String url;
   @Linkedin
   private LinkedinHeaders headers;

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public LinkedinHeaders getHeaders() {
      return this.headers;
   }

   public void setHeaders(LinkedinHeaders headers) {
      this.headers = headers;
   }

   public static class LinkedinHeaders {

      @Linkedin("values")
      private List<HttpHeader> httpHeaderList;
      @Linkedin("_total")
      private Long total;

      public List<HttpHeader> getHttpHeaderList() {
         if (httpHeaderList == null) {
            httpHeaderList = new ArrayList<HttpHeader>();
         }
         return this.httpHeaderList;
      }

      public void setHttpHeaderList(List<HttpHeader> httpHeaderList) {
         this.httpHeaderList = httpHeaderList;
      }

      public Long getTotal() {
         return total;
      }

      public void setTotal(Long value) {
         this.total = value;
      }

      public static class HttpHeader {

         @Linkedin
         private String name;
         @Linkedin
         private String value;

         public String getName() {
            return this.name;
         }

         public void setName(String name) {
            this.name = name;
         }

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }
      }
   }
}
