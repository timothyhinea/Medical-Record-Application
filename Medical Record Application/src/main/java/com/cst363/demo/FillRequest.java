package com.cst363.demo;

public class FillRequest 
{
   private int prescriptionID;
   private int patientSSN;
   
   public int getPrescriptionID() {
      return prescriptionID;
   }
   public void setPrescriptionID(int prescriptionID) {
      this.prescriptionID = prescriptionID;
   }
   public int getPatientSSN() {
      return patientSSN;
   }
   public void setPatientSSN(int patientSSN) {
      this.patientSSN = patientSSN;
   }
}
