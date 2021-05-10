package com.cst363.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class SQLHelper 
{
	   @Autowired
	   JdbcTemplate jdbcTemplate;
   // this is used for creating connections to the database.
   private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   private final String DB_URL = "jdbc:mysql://localhost/database_name";
   
   // credentials
   private static final String USER = "username";
   private static final String PASS = "password";
   
   private Connection con = null;
   private Statement stmt = null;
   
   public SQLHelper()
   {
      // register the driver
      try
      {
         Class.forName(JDBC_DRIVER);
      } catch(Exception e)
      {
         e.printStackTrace();
      }
   }
   
   // Check the returned connection to make sure it isnt null.
   public Connection open()
   {
      try
      {
         con = DriverManager.getConnection(DB_URL, USER,PASS);
         stmt = con.createStatement();
      } catch(SQLException ex)
      {
         Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      return con;
   }
   
   public void close()
   {
      try
      {
         stmt.close();
         con.close();
      } catch (SQLException ex)
      {
         Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
}
