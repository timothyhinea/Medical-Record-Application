package cst363.controllers;

import com.cst363.demo.FillRequest;
import com.cst363.demo.Prescription;
import com.cst363.demo.DateRange;
import com.cst363.demo.DrugQuantity;
import com.cst363.demo.DrugQuantityByDoctor;
import com.cst363.demo.DrugQuantityReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
public class MyController 
{
   Prescription prescription;
   FillRequest fillRequest;
   DateRange dateRange;
   DrugQuantity drugQuantity;
   DrugQuantityReportService dqrService;
   
   @Autowired
   JdbcTemplate jdbcTemplate;
   
   // invoke this method using URL path "/dynamic?name=XXXXX"
   @GetMapping("/dynamic")
   public String getCurrentTime(@RequestParam("name") String name, Model model) 
   {
      String dt = new java.util.Date().toString();
      model.addAttribute("time", dt);
      model.addAttribute("name", name);
      return "dynamic";
   }
   
   @GetMapping("/requestdqbdreport")
   public String getDQBDRequest(Model model) 
   {
      model.addAttribute("daterange", new DateRange());
       return "requestdqbdreport";
   }
   
   @PostMapping("/requestdqbdreport")
   public String processDQBDRequest(@Valid DateRange dateRange, BindingResult result, Model model)
   {
      try 
      {
          Connection conn = jdbcTemplate.getDataSource().getConnection();
          PreparedStatement ps = conn.prepareStatement(
                  "SELECT " + 
                  "    d.trade_name, p.Quantity, dr.last_name " + 
                  "FROM " + 
                  "    drug d " + 
                  "        LEFT JOIN " + 
                  "    prescription p ON p.drug_id = d.drug_id " + 
                  "        LEFT JOIN " + 
                  "    doctor dr ON dr.doctor_id = p.doctor_id " + 
                  "WHERE " + 
                  "        p.prescribed_date > ? " + 
                  "        AND p.prescribed_date < ?;"
          );
          
          ps.setString(1, dateRange.getStartDate());
          ps.setString(2, dateRange.getEndDate());
          
          ResultSet rs = ps.executeQuery();
          int cost = 0;
          int rowCount = 0;
          List<DrugQuantityByDoctor> dqbdList = new ArrayList<DrugQuantityByDoctor>();
          while(rs.next())
          {
             dqbdList.add(new DrugQuantityByDoctor(rs.getString(1), rs.getInt(2), rs.getString(3)));
             // cost = rs.getInt(1);
             System.out.println("Drug Name: " + rs.getString(1) + " Quantity: " + rs.getInt(2) + " Doctor: " + rs.getString(3));
             rowCount++;
          }
          
          // if you want to go to updatesuccess
          // model.addAttribute("count", rowCount);
          // if you want to post the cost to the 2nd part of the form.
          model.addAttribute("dqbdresults", dqbdList);
          
          
          conn.close();
          
          return "dqbd_report"; 
      } catch (SQLException se) {
          System.out.println("Error:  cst363#dqbdreportrequest SQLException " + se.getMessage() );
          model.addAttribute("msg",se.getMessage());
          return "error";
      }  
   }
   
   @GetMapping("/dqbd_report")
   public String getDQBDReport(@ModelAttribute("dqbdresults") ArrayList<String> dqResults, Model model) 
   {
      return "dqbd_report";
   }
   
   // our form on the report page does nothing but if a submit button is to be clicked it will cause an error.
   // this handler is empty for now.
   @PostMapping("/dqbd_report")
   public String processDQBDReport(Model model)
   {
      return "dqbd_report";
   }

   // ////////////////////////////////// //
   // I belong to David. Don't touch me! //
   // ////////////////////////////////// //
   @GetMapping("/requestdrugreport")
   public String getDrugReportRequest(Model model) 
   {
      model.addAttribute("daterange", new DateRange());
       return "requestdrugreport";
   }
   
// ///////////////////////////////////// //
   // I belong to David. Don't touch me! //
   // ////////////////////////////////// //
   @PostMapping("/requestdrugreport")
   public String processDrugReportRequest(@Valid DateRange dateRange, BindingResult result, Model model)
   {
      try 
      {
          Connection conn = jdbcTemplate.getDataSource().getConnection();
          PreparedStatement ps = conn.prepareStatement(
                  "SELECT " + 
                  "    d.trade_name, SUM(p.Quantity) " + 
                  "FROM " + 
                  "    drug d " + 
                  "        LEFT JOIN " + 
                  "    prescription p ON p.drug_id = d.drug_id " + 
                  "        LEFT JOIN " + 
                  "    pharmacy_drug pd ON pd.drug_id = p.drug_id " + 
                  "WHERE " + 
                  "    pd.pharmacy_id = 3 " + 
                  "        AND p.prescribed_date > ? " + 
                  "        AND p.prescribed_date < ?;"
          );
          
          ps.setString(1, dateRange.getStartDate());
          ps.setString(2, dateRange.getEndDate());
          
          ResultSet rs = ps.executeQuery();
          int cost = 0;
          int rowCount = 0;
          List<DrugQuantity> dqList = new ArrayList<DrugQuantity>();
          while(rs.next())
          {
             dqList.add(new DrugQuantity(rs.getString(1), rs.getInt(2)));
             // cost = rs.getInt(1);
             System.out.println("Name: " + rs.getString(1) + " Quantity: " + rs.getInt(2));
             rowCount++;
          }
          
          // if you want to go to updatesuccess
          // model.addAttribute("count", rowCount);
          // if you want to post the cost to the 2nd part of the form.
          model.addAttribute("dqresults", dqList);
          
          
          conn.close();
          
          return "drugreport"; 
      } catch (SQLException se) {
          System.out.println("Error:  cst363#prescriptioninputform SQLException " + se.getMessage() );
          model.addAttribute("msg",se.getMessage());
          return "error";
      }  
   }
   
   
   
   // this is where the page is loaded with a query driven html table
   @GetMapping("/drugreport")
   public String getDrugReport(@ModelAttribute("dqresults") ArrayList<String> dqResults, Model model) 
   {
      return "drugreport";
   }
   
   // our form on the report page does nothing but if a submit button is to be clicked it will cause an error.
   // this handler is empty for now.
   @PostMapping("/drugreport")
   public String processDrugReport(Model model)
   {
      return "drugreport";
   }
   
   /*
    * GET FILL REQUEST (Confirmation)
    */
   @GetMapping("/fillrequest_confirmation")
   public String getFillRequestConfirmation(Model model) 
   {
       return "fillrequest_confirmation";
   }
   
   /*
    * POST FILL REQUEST (Confirmation)
    */
   @PostMapping("/fillrequest_confirmation")
   public String fillRequestConfirmed(Model model)
   {
      return "fillrequest_confirmed";
   }
   
   @GetMapping("/fillrequest")
   public String getFillRequest(Model model) 
   {
       model.addAttribute("fillrequest", new FillRequest());
       return "fillrequest";
   }
   
   @PostMapping("/fillrequest")
   public String processFillRequest(@Valid FillRequest fillReq, BindingResult result, Model model)
   {
      // enter 0 and 444444444 as the id and ssn
      
      try 
      {
          Connection conn = jdbcTemplate.getDataSource().getConnection();
          PreparedStatement ps = conn.prepareStatement(
                  "SELECT " + 
                  "    pd.price * p.Quantity AS cost " + 
                  "FROM " + 
                  "    prescription p " + 
                  "        LEFT JOIN " + 
                  "    pharmacy_drug pd ON p.drug_id = pd.drug_id " + 
                  "        LEFT JOIN " + 
                  "    patient pa ON p.patient_id = pa.patient_id " + 
                  "WHERE " + 
                  "    pa.SSN = ? AND p.prescription_id = ?;"
          );
          
          ps.setInt(1, fillReq.getPatientSSN());
          ps.setInt(2, fillReq.getPrescriptionID());
          
          
          ResultSet rs = ps.executeQuery();
          int cost = 0;
          int rowCount = 0;
          while(rs.next())
          {
             cost = rs.getInt(1);
             rowCount++;
          }
          
          // if you want to go to updatesuccess
          // model.addAttribute("count", rowCount);
          // if you want to post the cost to the 2nd part of the form.
          model.addAttribute("price", cost);
          
          System.out.println("Cost: " + cost);
          
          conn.close();
          
          return "fillrequest_confirmation"; 
      } catch (SQLException se) {
          System.out.println("Error:  cst363#prescriptioninputform SQLException " + se.getMessage() );
          model.addAttribute("msg",se.getMessage());
          return "error";
      }  
   }
   
   /*
    * download the form page
    */
   @GetMapping("/prescriptioninputform")
   public String getPrescriptionForm(Model model) {
       model.addAttribute("prescription", new Prescription());
       return "prescriptioninputform";
   }
   
   /*
    * process the submitted form
    */
   @PostMapping("/prescriptioninputform")
   public String processPrescriptionForm(@Valid Prescription prescription, BindingResult result, Model model) 
   {
       try 
       {
           Connection conn = jdbcTemplate.getDataSource().getConnection();
           PreparedStatement ps = conn.prepareStatement(
                 "insert into prescription (prescription_id, Refills, prescribed_date, Quantity, drug_id, patient_id, doctor_id) "
                 + "VALUES(last_insert_id(), ?, CURDATE(), ?,"
                 + "(SELECT drug_id FROM drug WHERE trade_name = ?),"
                 + "(SELECT patient_id from patient WHERE SSN = ?),"
                 + "(SELECT doctor_id from doctor WHERE SSN = ?))");
           
           ps.setInt(1, prescription.getRefills());
           ps.setInt(2, prescription.getQuantity());
           ps.setString(3, prescription.getDrugName());
           ps.setInt(4, prescription.getPatientSSN());
           ps.setInt(5, prescription.getDoctorSSN());
           int count = ps.executeUpdate();
           conn.close();
           model.addAttribute("count", count);
           System.out.println(count + " rows added");
           return "updatesuccess"; 
       } catch (SQLException se) {
           System.out.println("Error:  cst363#prescriptioninputform SQLException " + se.getMessage() );
           model.addAttribute("msg",se.getMessage());
           return "error";
       }  
   }
   
   @GetMapping("/displayprescriptions")
   public String displayPrescriptions(Model model) {
       model.addAttribute("prescription", new Prescription());
       return "displayprescriptions";
   }
   /*
    * process the submitted form
    */
   @PostMapping("/displayprescriptions")
   public String pr(@Valid Prescription prescription, BindingResult result, Model model)
   {
       ArrayList<Prescription> prescriptions = new ArrayList<>();
       try
       {
           Connection conn = jdbcTemplate.getDataSource().getConnection();
           PreparedStatement ps = conn.prepareStatement(
                 "SELECT \r\n" +
                 "    prescription_id, Refills\r\n" +
                 "FROM\r\n" +
                 "    prescription\r\n" +
                 "        JOIN\r\n" +
                 "    patient ON patient.patient_id = prescription.patient_id\r\n" +
                 "WHERE\r\n" +
                 "    patient.SSN = ?;");
           ps.setInt(1, prescription.getPatientSSN());
           System.out.println(prescription.getPatientSSN());
           ResultSet rs = ps.executeQuery();
           //Loop to check weather to refill prescriptions
           while (rs.next()) {
               System.out.println(prescription.getprescriptionID());
               if(prescription.getprescriptionID() == rs.getInt(1))
                  return "refillsent";
               Prescription r = new Prescription(rs.getInt(2), rs.getInt(1));
               prescriptions.add(r);
               }
           model.addAttribute("prescriptions", prescriptions);
           conn.close();
           return "displayprescriptions";
       } catch (SQLException se) {
           System.out.println("Error:  cst363#prescriptioninputform SQLException " + se.getMessage() );
           model.addAttribute("msg",se.getMessage());
           return "error";
       }
   }
   
}
   