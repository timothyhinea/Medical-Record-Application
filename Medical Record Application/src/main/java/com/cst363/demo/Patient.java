package com.cst363.demo;

public class Patient 
{
   private int id;
   
   private int ssn;
   
   private String firstName;
   
   private String lastName;
   
   private int primaryPhysicianID;
   
   private int age;
   
   private String address;
   
   private String city;
   
   private String state;
   
   private String zipCode;
   
    /* 
       getter / setter pairs
    */
   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public int getSsn() {
      return ssn;
   }
   public void setSsn(int ssn) {
      this.ssn = ssn;
   }
   public String getFirstName() {
      return firstName;
   }
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }
   public String getLastName() {
      return lastName;
   }
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }
   public int getPrimaryPhysicianID() {
      return primaryPhysicianID;
   }
   public void setPrimaryPhysicianID(int primaryPhysicianID) {
      this.primaryPhysicianID = primaryPhysicianID;
   }
   public int getAge() {
      return age;
   }
   public void setAge(int age) {
      this.age = age;
   }
   public String getAddress() {
      return address;
   }
   public void setAddress(String address) {
      this.address = address;
   }
   public String getCity() {
      return city;
   }
   public void setCity(String city) {
      this.city = city;
   }
   public String getState() {
      return state;
   }
   public void setState(String state) {
      this.state = state;
   }
   public String getZipCode() {
      return zipCode;
   }
   public void setZipCode(String zipCode) {
      this.zipCode = zipCode;
   }
   
}
