package com.example.DeluxeRps.Controllers;

import java.sql.*;

public class ConDB {

  private static Connection con;


  public static Connection getConnection() throws SQLException {

    String db = "jdbc:postgresql://ec2-176-34-97-213.eu-west-1.compute.amazonaws.com:5432/d2621gbprb812i";
    String user = "igblmsacvvtqrc";
    String pass = "8aa6d775c64cc09d4e2aee35743c2ed90290530663b15d687f0e4bfff5542a68";


    try
    {
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection(""+db+"", ""+user+"", ""+pass+"");

    }
    catch(Exception e) {
      e.printStackTrace();
      con.close();
    }
    return con;
  }





}
