package com.example.DeluxeRps.Controllers;


import com.example.DeluxeRps.Models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteFriend extends GenericController{

  Connection con;
  PreparedStatement checkFriendsList, gameRequestSTMNT, newGameSTMNT, getUserIDStmt, changeMatchStmt, whoIsOnlineStmt, getUsernameStmt;
  String username = Login.username;
  int useridplayer1;
  static String matchstatus;



  //FXML-Objects
  @FXML
  private ListView<String> friendsList = new ListView<>();

  @FXML
  private ListView<String> requestsList = new ListView<>();



  @Override
  public void postInitialize() throws SQLException {

    //Searching for info in DB + listing them in ListView
    friendsList.getItems().clear();
    requestsList.getItems().clear();

    con = ConDB.getConnection();
    con.setAutoCommit(false);

    useridplayer1 = getUserId(username);

    while (useridplayer1 != 0) {

      try {

        System.out.println("Looking for friends");

        ResultSet c = getOnlineFriends(useridplayer1);

        while (c.next()) {
          String friendName = c.getString("friendname");
          friendsList.getItems().add(friendName);
        }


        ResultSet d = gameRequests(useridplayer1);

        while (d.next()) {
          int player2userID = d.getInt("useridplayer1");
          String friendName = getUsername(player2userID);
          requestsList.getItems().add(friendName);
        }

        return;

      } catch (SQLException e) {
        e.printStackTrace();
      }


    }

  }


  //REMOVE THIS ONE
  public void startGameButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGamePlayerFXML, Helper.startGamePlayerTitle, mouseEvent);
  }

  //Buttons
  public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.pvpMenuFXML, Helper.pvpMenuTitle, mouseEvent);
  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }

  public void checkforfriends(MouseEvent mouseEvent) throws SQLException {
    postInitialize();
  }



  //INVITE FRIEND TO GAME BY CLICKING ON ITEM
  public void clickInviteFriend(MouseEvent arg0) {

    System.out.println("clicked on " + friendsList.getSelectionModel().getSelectedItem());

    try {
      newGameRequest(useridplayer1, getUserId(friendsList.getSelectionModel().getSelectedItem()));
      Helper.replaceScene(Helper.startGamePlayerFXML, Helper.startGamePlayerTitle, arg0);
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }

  }

  //ACCEPT REQUEST BY CLICKING ON ITEM
  public void clickAcceptRequest(MouseEvent arg0) {
    System.out.println("clicked on " + requestsList.getSelectionModel().getSelectedItem());

    try {
      changeMatchStatus(getUserId(requestsList.getSelectionModel().getSelectedItem()));
      Helper.replaceScene(Helper.startGamePlayerFXML, Helper.startGamePlayerTitle, arg0);
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }


  }




  //PREPARED STATEMENTS

  private ResultSet gameRequests(int userid) throws SQLException {

    matchstatus = "PENDING";
    gameRequestSTMNT = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer2 = ? AND matchstatus = ?");
    gameRequestSTMNT.setInt(1, userid);
    gameRequestSTMNT.setString(2, matchstatus);
    ResultSet requests = gameRequestSTMNT.executeQuery();
    return requests;

  }


  private void newGameRequest(int player1, int player2) throws SQLException {

    matchstatus = "PENDING";
    newGameSTMNT = con.prepareStatement("INSERT INTO gamedb.newgame (useridplayer1, useridplayer2, matchstatus) VALUES (?,?,?)");
    newGameSTMNT.setInt(1, player1);
    newGameSTMNT.setInt(2,player2);
    newGameSTMNT.setString(3, matchstatus);
    newGameSTMNT.executeUpdate();
    con.commit();

  }

  private int getUserId(String username) throws SQLException{

    getUserIDStmt = con.prepareStatement("SELECT * FROM gamedb.users where username = ?");
    getUserIDStmt.setString(1, username);
    ResultSet checkUser = getUserIDStmt.executeQuery();

    if(checkUser.next()) {

      useridplayer1 = checkUser.getInt("userid");

      return useridplayer1;
    }

    return 0;

  }

  private String getUsername(int playerID) throws SQLException {

    getUsernameStmt = con.prepareStatement("SELECT * FROM gamedb.users WHERE userid = ?");
    getUsernameStmt.setInt(1, playerID);
    ResultSet a = getUsernameStmt.executeQuery();

    while (a.next()) {
      String username = a.getString("username");

      return username;
    }

    return null;

  }


  private void changeMatchStatus(int useridplayer1) throws SQLException {

    matchstatus = "ONGOING";
    changeMatchStmt = con.prepareStatement("UPDATE gamedb.newgame SET matchstatus = ? where useridplayer1 = ?");
    changeMatchStmt.setString(1, matchstatus);
    changeMatchStmt.setInt(2, useridplayer1);
    changeMatchStmt.executeUpdate();
    con.commit();

  }

  private ResultSet getOnlineFriends(int userid) throws SQLException{

    whoIsOnlineStmt = con.prepareStatement("select * from gamedb.users inner join gamedb.friendslist on friendslist.friendid = users.userid where friendslist.userid in (select users.userid from gamedb.users inner join gamedb.tokens on tokens.userid = users.userid where users.userid = ?)");
    whoIsOnlineStmt.setInt(1, userid);

    return whoIsOnlineStmt.executeQuery();

  }





}
