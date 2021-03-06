package com.example.DeluxeRps.Models;

import com.example.DeluxeRps.Controllers.ConDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Match {


  private int matchId, userId;
  static PreparedStatement matchSTMNT, matchUserSTMNT;
 Connection conn = ConDB.getConnection();

  public Match(int matchId, int userId) throws SQLException {
    this.matchId = matchId;
    this.userId = userId;
  }

  public void getMatchId() throws SQLException {
    matchSTMNT = conn.prepareStatement("SELECT * FROM gamedb.match WHERE matchid = ?");
    matchSTMNT.setInt(1,matchId);
    matchSTMNT.executeQuery();
    conn.commit();
  }

  public void setMatchId(int userId, int matchId) throws SQLException {
    matchSTMNT = conn.prepareStatement("INSERT INTO gamedb.match VALUES (?, ?)");
    matchSTMNT.setInt(1,userId);
    matchSTMNT.setInt(2,matchId);
    matchSTMNT.executeUpdate();
    conn.commit();

  }

  public void getUserId(int userId) throws SQLException {
    matchUserSTMNT = conn.prepareStatement("SELECT * FROM gamedb.match WHERE userid = ?");
    matchUserSTMNT.setInt(1, userId);
    matchUserSTMNT.executeQuery();
    conn.commit();


  }


  public void setUserId(int userId) {
    this.userId = userId;
  }
}
