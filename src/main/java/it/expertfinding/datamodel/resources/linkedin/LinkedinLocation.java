package it.expertfinding.datamodel.resources.linkedin;

import it.expertfinding.utils.linkedin.Linkedin;

public class LinkedinLocation {

   @Linkedin
   private String description;
   @Linkedin
   private boolean isHeadquarters;
   @Linkedin
   private boolean isActive;
   @Linkedin
   private LinkedinAddress address;
   @Linkedin
   private LinkedinContactInfo contactInfo;
   @Linkedin
   private String name;
   @Linkedin
   private String postalCode;
   @Linkedin
   private LinkedinCodeField country;

   public String getDescription() {
      return description;
   }

   public void setDescription(String value) {
      this.description = value;
   }

   public boolean isIsHeadquarters() {
      return isHeadquarters;
   }

   public void setIsHeadquarters(boolean value) {
      this.isHeadquarters = value;
   }

   public boolean isIsActive() {
      return isActive;
   }

   public void setIsActive(boolean value) {
      this.isActive = value;
   }

   public LinkedinAddress getAddress() {
      return address;
   }

   public void setAddress(LinkedinAddress value) {
      this.address = value;
   }

   public LinkedinContactInfo getContactInfo() {
      return contactInfo;
   }

   public void setContactInfo(LinkedinContactInfo value) {
      this.contactInfo = value;
   }

   public String getName() {
      return name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getPostalCode() {
      return postalCode;
   }

   public void setPostalCode(String value) {
      this.postalCode = value;
   }

   public LinkedinCodeField getCountry() {
      return country;
   }

   public void setCountry(LinkedinCodeField value) {
      this.country = value;
   }

   public static class LinkedinAddress {

      @Linkedin
      private String street1;
      @Linkedin
      private String street2;
      @Linkedin
      private String city;
      @Linkedin
      private String state;
      @Linkedin
      private String postalCode;
      @Linkedin
      private String countryCode;
      @Linkedin
      private String regionCode;

      public String getStreet1() {
         return street1;
      }

      public void setStreet1(String value) {
         this.street1 = value;
      }

      public String getStreet2() {
         return street2;
      }

      public void setStreet2(String value) {
         this.street2 = value;
      }

      public String getCity() {
         return city;
      }

      public void setCity(String value) {
         this.city = value;
      }

      public String getState() {
         return state;
      }

      public void setState(String value) {
         this.state = value;
      }

      public String getPostalCode() {
         return postalCode;
      }

      public void setPostalCode(String value) {
         this.postalCode = value;
      }

      public String getCountryCode() {
         return countryCode;
      }

      public void setCountryCode(String value) {
         this.countryCode = value;
      }

      public String getRegionCode() {
         return regionCode;
      }

      public void setRegionCode(String value) {
         this.regionCode = value;
      }
   }

   public static class LinkedinContactInfo {

      @Linkedin
      private String phone1;
      @Linkedin
      private String phone2;
      @Linkedin
      private String fax;

      public String getPhone1() {
         return phone1;
      }

      public void setPhone1(String value) {
         this.phone1 = value;
      }

      public String getPhone2() {
         return phone2;
      }

      public void setPhone2(String value) {
         this.phone2 = value;
      }

      public String getFax() {
         return fax;
      }

      public void setFax(String value) {
         this.fax = value;
      }
   }
}
