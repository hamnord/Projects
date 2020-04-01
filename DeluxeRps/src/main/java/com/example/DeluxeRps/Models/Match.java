package com.example.DeluxeRps.Models;

import com.example.DeluxeRps.Controllers.ConDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Match {

  private int matchId, userId;
  PreparedStatement matchSTMNT;
  Connection conn = ConDB.getConnection();

  public Match(int matchId, int userId) throws SQLException {
    this.matchId = matchId;
    this.userId = userId;
  }

  public int getMatchId() throws SQLException {
    matchSTMNT = conn.prepareStatement("SELECT * FROM gamedb.match WHERE matchid = ?");
    matchSTMNT.setInt(1,matchId);
    matchSTMNT.executeQuery();
    conn.commit();
    return matchId;
  }

  public void setMatchId(int matchId) throws SQLException {
    matchSTMNT = conn.prepareStatement("INSERT INTO gamedb.match VALUES matchid = ?");
    matchSTMNT.setInt(1,matchId);
    matchSTMNT.executeUpdate();
    conn.commit();
    this.matchId = matchId;
  }


  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
