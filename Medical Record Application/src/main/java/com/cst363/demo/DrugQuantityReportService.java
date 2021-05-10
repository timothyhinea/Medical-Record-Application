package com.cst363.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.cst363.demo.DrugQuantity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class DrugQuantityReportService 
{
   @Autowired
   JdbcTemplate template;
   
   public List<DrugQuantity> findAll() {
      String sql = 
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
            "        AND p.prescribed_date < ?;";
      RowMapper<DrugQuantity> rm = new RowMapper<DrugQuantity>() {
          @Override
          public DrugQuantity mapRow(ResultSet resultSet, int i) throws SQLException {
             DrugQuantity dq = new DrugQuantity(resultSet.getString("drug_name"),
                      resultSet.getInt(2));
              return dq;
          }
      };

      return template.query(sql, rm);
  }
}
