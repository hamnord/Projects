package com.example.DeluxeRps.Models;

import com.example.DeluxeRps.Controllers.ConDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Player {


  private static Connection conn;
  private String userName;
  private static int userId;
  static PreparedStatement usernameSTMNT, userIdSTMNT;

  public Player (String userName, int userId) throws SQLException {
    this.userName = userName;
    this.userId = userId;

  }


  public static int getUserId() throws SQLException {

    conn = ConDB.getConnection();
    conn.setAutoCommit(false);

    userIdSTMNT = conn.prepareStatement("SELECT * FROM gamedb.users WHERE userid = ?");
    userIdSTMNT.setInt(1,userId);
    userIdSTMNT.executeQuery();
    conn.commit();
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserName() throws SQLException {
    usernameSTMNT = conn.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
    usernameSTMNT.setString(1,userName);
    usernameSTMNT.executeQuery();
    conn.commit();
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
