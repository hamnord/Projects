package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteFriend {

  Connection con;
  PreparedStatement checkOnlineSTMNT, gameRequestSTMNT, newGameSTMNT;
  static String username;
  static int userid, matchid,useridplayer1,useridplayer2;
  static String matchstatus;

  //TODO implement gameInvite
  public void gameRequest() throws SQLException {



  }

  //TODO Check online USERS/Friends
  public ResultSet checkOnlineUsers() throws SQLException {

    checkOnlineSTMNT = con.prepareStatement("SELECT * FROM gamedb.logedinusers WHERE userid = ?");
    checkOnlineSTMNT.setInt(1,userid);
    ResultSet onlineUsers = checkOnlineSTMNT.executeQuery();
    con.commit();
    return onlineUsers;
  }



  public void newGameRequest () throws SQLException {

    newGameSTMNT = con.prepareStatement("INSERT INTO gamedb.newgame VALUES (?,?,?,?)");
    newGameSTMNT.setInt(1,matchid);
    newGameSTMNT.setInt(2,useridplayer1);
    newGameSTMNT.setInt(3,useridplayer2);
    newGameSTMNT.setString(4,matchstatus);
    newGameSTMNT.executeUpdate();
    con.commit();

  }


  //TODO implement GameRequest with friend
  public void inviteFriendButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
    con = ConDB.getConnection();





  }




  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }


}
