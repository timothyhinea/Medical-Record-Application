package com.cst363.demo;

public class Prescription
{
	private int prescriptionID;
    private int doctorSSN;
    private int patientSSN;
    private String drugName;
    private int quantity;
    private int refills;
    
    public Prescription()
    {
       
    }
    
    public Prescription(int patientSSN, int prescriptionID)
    {
        this.prescriptionID = prescriptionID;
        this.patientSSN = patientSSN;
    }
    
   public int getDoctorSSN() {
      return doctorSSN;
   }
   public void setDoctorSSN(int doctorSSN) {
      this.doctorSSN = doctorSSN;
   }
   public int getPatientSSN() {
      return patientSSN;
   }
   public void setPatientSSN(int patientSSN) {
      this.patientSSN = patientSSN;
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
   public int getRefills() {
      return refills;
   }
   public void setRefills(int refils) {
      this.refills = refils;
   }
   public int getprescriptionID() {
	      return prescriptionID;
	   }
	   public void prescriptionID(int prescriptionID) {
	      this.prescriptionID = prescriptionID;
   
}
}