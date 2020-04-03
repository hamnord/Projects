package com.example.DeluxeRps.Models;

import com.example.DeluxeRps.Controllers.ConDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Move {


  private int moveId, userId;
   Connection conn = ConDB.getConnection();
  static PreparedStatement sendMoveSTMNT,moveIdSTMNT,getMoveSTMNT;

  public Move (int moveId, int userId) throws SQLException {
    this.moveId = moveId;

    this.userId = userId;
  }


  //player 1 or player 2
  public void getMoveId() throws SQLException {
    moveIdSTMNT = conn.prepareStatement("SELECT * FROM gamedb.match WHERE moveid= ?");
    moveIdSTMNT.setInt(1,moveId);
    moveIdSTMNT.executeQuery();
    conn.commit();
  }

  // sets moveId to 1 or 2 to players.
  public void setMoveId(int moveId) throws SQLException {
    moveIdSTMNT = conn.prepareStatement("INSERT INTO gamedb.match WHERE moveid= ? ");
    moveIdSTMNT.setInt(1,moveId);
    moveIdSTMNT.executeUpdate();
    conn.commit();
  }

  // send move/choice to db
  public void sendMove(int userId, int move) throws SQLException {
    sendMoveSTMNT = conn.prepareStatement("INSERT INTO gamedb.match VALUES (?, ?)");
   sendMoveSTMNT.setInt(1,userId);
   sendMoveSTMNT.setInt(2,move);
    sendMoveSTMNT.executeUpdate();
    conn.commit();
  }

  // get your opponents move
  public void getMove(int userId, int move) throws SQLException {

    getMoveSTMNT = conn.prepareStatement("SELECT move FROM gamedb.match WHERE userid = ?");
    getMoveSTMNT.setInt(1,userId);
    getMoveSTMNT.setInt(2,move);
    getMoveSTMNT.executeQuery();
    conn.commit();
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

}
