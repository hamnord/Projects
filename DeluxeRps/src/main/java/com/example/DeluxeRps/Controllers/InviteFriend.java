package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteFriend {

  Connection con;
  PreparedStatement checkOnlineSTMNT, gameRequestSTMNT, newGameSTMNT, getUserIDStmt, changeMatchStmt;
  static String username = Login.username;
  static int userid, useridplayer1,useridplayer2;
  static String matchstatus;


  //FXML-Objects
  TextField friend;




  //TODO implement GameRequest with friend
  public void inviteFriendButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

    con = ConDB.getConnection();

    ResultSet a = getUserId(username);
    useridplayer1 = a.getInt("userid");
    //set useridplayer2 via någon lista?


    newGameRequest(useridplayer1, useridplayer2);


  }

  public void seeRequestsButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

    con = ConDB.getConnection();

    ResultSet a = getUserId(username);
    userid = a.getInt("userid");


    if (gameRequests(userid).next()) {

      // Någon form for lista av requests + button for confirm -> to Game
      changeMatchStatus();



    } else {

      System.out.println("No active requests");
      //Button til invite-friend?

    }


  }


  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }




  //PREPARED STATEMENTS

  //TODO implement gameInvite
  private ResultSet gameRequests(int userid) throws SQLException {

    matchstatus = "PENDING";
    gameRequestSTMNT = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE userid = ? AND matchstatus = ?");
    gameRequestSTMNT.setInt(1, userid);
    gameRequestSTMNT.setString(2, matchstatus);
    ResultSet requests = gameRequestSTMNT.executeQuery();
    return requests;

  }

  //TODO Check online USERS/Friends
  public ResultSet checkOnlineUsers(int userid) throws SQLException {

    checkOnlineSTMNT = con.prepareStatement("SELECT * FROM gamedb.logedinusers WHERE userid = ?");
    checkOnlineSTMNT.setInt(1,userid);
    ResultSet onlineUsers = checkOnlineSTMNT.executeQuery();
    con.commit();
    return onlineUsers;
  }



  private void newGameRequest(int player1, int player2) throws SQLException {

    matchstatus = "PENDING";
    newGameSTMNT = con.prepareStatement("INSERT INTO gamedb.newgame (useridplayer1, useridplayer2, matchstatus) VALUES (?,?,?)");
    newGameSTMNT.setInt(2, player1);
    newGameSTMNT.setInt(3,player2);
    newGameSTMNT.setString(4,matchstatus);
    newGameSTMNT.executeUpdate();
    con.commit();

  }

  private ResultSet getUserId(String username) throws SQLException{

    getUserIDStmt = con.prepareStatement("SELECT * FROM gamedb.users where username = ?");
    getUserIDStmt.setString(1, username);
    ResultSet checkUser = getUserIDStmt.executeQuery();
    con.commit();

    return checkUser;


  }

  private void changeMatchStatus() throws SQLException {

    matchstatus = "ONGOING";
    changeMatchStmt = con.prepareStatement("UPDATE gamedb.newgame SET (matchstatus) = ?");
    changeMatchStmt.setString(1, matchstatus);
    changeMatchStmt.executeUpdate();
    con.commit();

  }




}
