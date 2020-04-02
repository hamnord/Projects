package com.example.DeluxeRps.Models;

import com.example.DeluxeRps.Controllers.ConDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Move {


  private int moveId, move, userId;
   Connection conn = ConDB.getConnection();
  static PreparedStatement sendMoveSTMNT,moveIdSTMNT,getMoveSTMNT;

  public Move (int moveId, int move, int userId) throws SQLException {
    this.moveId = moveId;
    this.move = move;
    this.userId = userId;
  }


  //player 1 or player 2
  public int getMoveId() throws SQLException {
    moveIdSTMNT = conn.prepareStatement("SELECT * FROM gamedb.match WHERE moveid= ?");
    moveIdSTMNT.setInt(1,moveId);
    moveIdSTMNT.executeQuery();
    conn.commit();
    return moveId;
  }

  // sets moveId to 1 or 2 to players.
  public static void setMoveId(int moveId) throws SQLException {
    Connection conn = ConDB.getConnection();
    conn.setAutoCommit(false);

    moveIdSTMNT = conn.prepareStatement("INSERT * FROM gamedb.match WHERE moveid= ?");
    moveIdSTMNT.setInt(1,moveId);
    moveIdSTMNT.executeQuery();
    conn.commit();
  }

  // send move/choice to db
  public void sendMove(int userId, int move) throws SQLException {
    sendMoveSTMNT = conn.prepareStatement("INSERT INTO gamedb.match VALUES (?, ?)");
   sendMoveSTMNT.setInt(1,userId);
   sendMoveSTMNT.setInt(2,move);
    sendMoveSTMNT.executeQuery();
    conn.commit();
  }

  // get your opponents move
  public static int getMove(int userId, int move) throws SQLException {
    Connection conn = ConDB.getConnection();
    conn.setAutoCommit(false);

    getMoveSTMNT = conn.prepareStatement("SELECT * FROM gamedb.match WHERE userid = ? AND move = ?");
    getMoveSTMNT.setInt(1,userId);
    getMoveSTMNT.setInt(2,move);
    getMoveSTMNT.executeQuery();
    conn.commit();
    return move;
  }

  public void setMove(int move) {
    this.move = move;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

}
