package com.example.DeluxeRps.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Player {


  private static Connection conn;
  private String userName;
  private  int userId;
  static PreparedStatement usernameSTMNT, userIdSTMNT;

  public Player (String userName, int userId) throws SQLException {
    this.userName = userName;
    this.userId = userId;

  }


  public int getUserId(String username) throws SQLException {

    userIdSTMNT = conn.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
    userIdSTMNT.setString(1, username);
    ResultSet a = userIdSTMNT.executeQuery();
    int userID = a.getInt("userid");
    conn.commit();

    return userID;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public static String getUserName(int userID) throws SQLException {
    usernameSTMNT = conn.prepareStatement("SELECT * FROM gamedb.users WHERE userid = ?");
    usernameSTMNT.setInt(1,userID);
    ResultSet a = usernameSTMNT.executeQuery();
    String username = a.getString("username");
    conn.commit();

    return username;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
