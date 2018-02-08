package com.ups.kafka;

/* JDBC_Connection_Demo.java */
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class TestConn
{
  /* static block is executed when a class is loaded into memory 
   * this block loads MySQL's JDBC driver
   */
  static
  {
    try
    {
      // loads com.mysql.jdbc.Driver into memory
      Class.forName("com.mysql.jdbc.Driver");
    } 
    catch (ClassNotFoundException cnf) 
    {
      System.out.println("Driver could not be loaded: " + cnf);
    }
  }
 
  public static void main(String[] args)
  {
    String connectionUrl = "jdbc:mysql://localhost:3306/zebra";
    //String connectionUrl = "jdbc:mariadb://localhost:3306/zebra";
    String dbUser = "prem";
    String dbPwd = "adminUPS";
    Connection conn;
    ResultSet rs;
    String queryString = "select * from locations";
 
    try
    {
      conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
      Statement stmt = conn.createStatement();
      java.sql.Timestamp ts = new java.sql.Timestamp(new java.util.Date().getTime());
      String value = "1Z12345";
      long TID = 123456787;
 
      
  
      // INSERT A RECORD
      stmt.executeUpdate("INSERT INTO locations (coordinates,time_stamp,znum) VALUES ('"+value+"','"+ts+"',"+TID+")");
 
      // SELECT ALL RECORDS FROM EXPTABLE
      rs = stmt.executeQuery(queryString);
 
      System.out.println("id \tValue");
      System.out.println("============");
      while(rs.next())
      {
        System.out.print(rs.getInt("id") + ".\t" + rs.getString("coordinates"));
        //System.out.println(rs.getString("Tables_in_zebra"));
      }
      if (conn != null)
      {
        conn.close();
        conn = null;
      }
    }
    catch (SQLException sqle) 
    {
      System.out.println("SQL Exception thrown: " + sqle);
    }
  }
} 