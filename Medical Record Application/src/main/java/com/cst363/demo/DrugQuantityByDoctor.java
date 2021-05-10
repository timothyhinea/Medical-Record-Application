package com.cst363.demo;

public class DrugQuantityByDoctor 
{
   String doctorName;
   String drugName;
   int quantity;
   
   public DrugQuantityByDoctor(String drugName, int quantity, String doctorName )
   {
      this.doctorName = doctorName;
      this.drugName = drugName;
      this.quantity = quantity;
   }
   
   public String getDoctorName() {
      return doctorName;
   }
   public void setDoctorName(String doctorName) {
      this.doctorName = doctorName;
   }
   public String getDrugName() {
      return drugName;
   }
   public void setDrugName(String drugName) {
      this.drugName = drugName;
   }
   public int getQuantity() {
      return quantity;
   }
   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }
}
