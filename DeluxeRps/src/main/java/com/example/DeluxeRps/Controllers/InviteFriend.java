package com.example.DeluxeRps.Controllers;


import com.example.DeluxeRps.Models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteFriend extends GenericController{

  Connection con;
  PreparedStatement checkFriendsList, gameRequestSTMNT, newGameSTMNT, getUserIDStmt, changeMatchStmt, whoIsOnlineStmt;
  String username = Login.username;
  String friendUsername;
  int useridplayer1,useridplayer2;
  static String matchstatus;



  //FXML-Objects

  @FXML
  private ListView<Player> friendsList;

  @FXML
  private ListView<Player> requestsList;



  @Override
  public void postInitialize() throws SQLException{

    friendsList.getItems().clear();
    requestsList.getItems().clear();


    try{

       useridplayer1 = getUserId(username);
       ResultSet a = checkFriendList(useridplayer1);
       useridplayer2 = a.getInt("friendid");

        while(getOnlineFriends(useridplayer1).next()){
                ResultSet b = getOnlineFriends(useridplayer1);
                Player player2 = new Player(b.getString("username"), b.getInt("userid"));
                friendsList.getItems().add(player2);
        }



        while(gameRequests(useridplayer1).next()){
              ResultSet c = gameRequests(useridplayer1);
              int player2userID = c.getInt("useridplayer1");
              Player player2 = new Player(Player.getUserName(player2userID), player2userID);
              requestsList.getItems().add(player2);
        }




    } catch (SQLException e) {
      e.printStackTrace();
    }




  }



  //TODO implement GameRequest with friend
  /* public void inviteFriendButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

    con = ConDB.getConnection();
    con.setAutoCommit(false);

    friendId = inviteFriendFromList.getText();

    ResultSet a = getUserId(username);
    ResultSet b = getUserId(friendId);

    while (a.next() && b.next()) {

      System.out.println("loop running");

      useridplayer1 = a.getInt("userid");
      useridplayer2 = b.getInt("userid");

      if(checkFriendList(useridplayer1).next()) {

        try {

          newGameRequest(useridplayer1, useridplayer2);

        } catch (Exception e) {
          e.printStackTrace();
        }

      }

    }
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


  } */

  public void startGameButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGamePlayerFXML, Helper.startGamePlayerTitle, mouseEvent);
  }


  public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.pvpMenuFXML, Helper.pvpMenuTitle, mouseEvent);
  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }




  //PREPARED STATEMENTS

  //TODO implement gameInvite
  private ResultSet gameRequests(int userid) throws SQLException {

    matchstatus = "PENDING";
    gameRequestSTMNT = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer2 = ? AND matchstatus = ?");
    gameRequestSTMNT.setInt(1, userid);
    gameRequestSTMNT.setString(2, matchstatus);
    ResultSet requests = gameRequestSTMNT.executeQuery();
    return requests;

  }

  //TODO Check online Friends
  private ResultSet checkFriendList(int userID) throws SQLException {

    checkFriendsList = con.prepareStatement("SELECT * FROM gamedb.friendslist WHERE userid = ?");
    checkFriendsList.setInt(1,userID);
    ResultSet friendsList = checkFriendsList.executeQuery();
    con.commit();
    return friendsList;

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

  private int getUserId(String username) throws SQLException{

    getUserIDStmt = con.prepareStatement("SELECT * FROM gamedb.users where username = ?");
    getUserIDStmt.setString(1, username);
    ResultSet checkUser = getUserIDStmt.executeQuery();
    int a = checkUser.getInt("userid");
    con.commit();

    return a;

  }

  private void changeMatchStatus() throws SQLException {

    matchstatus = "ONGOING";
    changeMatchStmt = con.prepareStatement("UPDATE gamedb.newgame SET (matchstatus) = ?");
    changeMatchStmt.setString(1, matchstatus);
    changeMatchStmt.executeUpdate();
    con.commit();

  }

  private ResultSet getOnlineFriends(int userid) throws SQLException{

    whoIsOnlineStmt = con.prepareStatement("select * from gamedb.users inner join gamedb.friendslist on friendslist.friendid = users.userid where friendslist.userid in (select users.userid from gamedb.users inner join gamedb.tokens on tokens.userid = users.userid where users.userid = ?)");
    whoIsOnlineStmt.setInt(1, userid);
    ResultSet a = whoIsOnlineStmt.executeQuery();

    return a;

  }




}
