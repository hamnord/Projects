package com.example.DeluxeRps.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class ConnectionMaster {

   Connection connection;
  private String username;
  private String password;


  public ConnectionMaster(Connection connection, String username, String password) {
    this.connection = connection;
    this.username = username;
    this.password = password;

  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}