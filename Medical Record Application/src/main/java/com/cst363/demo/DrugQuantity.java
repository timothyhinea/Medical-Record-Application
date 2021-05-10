package com.cst363.demo;

public class DrugQuantity 
{
   private String name;
   private int quantity;
   
   public DrugQuantity(String name, int quantity)
   {
      this.name = name;
      this.quantity = quantity;
   }
   
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public int getQuantity() {
      return quantity;
   }
   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }
   
   
}
