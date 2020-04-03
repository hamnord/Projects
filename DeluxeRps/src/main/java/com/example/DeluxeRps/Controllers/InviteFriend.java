package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class InviteFriend extends GenericController{

  private Connection con;
  private PreparedStatement gameRequestStmt, newGameStmt, getUserIDStmt, changeMatchStmt, whoIsOnlineStmt, getUsernameStmt;
  private String username = Login.username;
  private int userIDPlayer1;
  private static String matchStatus;



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

    userIDPlayer1 = getUserId(username);

    while (userIDPlayer1 != 0) {

      try {

        System.out.println("Looking for friends");

        ResultSet c = getOnlineFriends(userIDPlayer1);

        while (c.next()) {
          String friendName = c.getString("friendname");
          friendsList.getItems().add(friendName);
        }


        ResultSet d = gameRequests(userIDPlayer1);

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

  public void checkForFriends(MouseEvent mouseEvent) throws SQLException {
    postInitialize();
  }



  //INVITE FRIEND TO GAME BY CLICKING ON ITEM
  public void clickInviteFriend(MouseEvent arg0) {

    System.out.println("clicked on " + friendsList.getSelectionModel().getSelectedItem());

    try {
      newGameRequest(userIDPlayer1, getUserId(friendsList.getSelectionModel().getSelectedItem()));
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

    matchStatus = "PENDING";
    gameRequestStmt = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer2 = ? AND matchstatus = ?");
    gameRequestStmt.setInt(1, userid);
    gameRequestStmt.setString(2, matchStatus);
    ResultSet requests = gameRequestStmt.executeQuery();
    return requests;

  }


  private void newGameRequest(int player1, int player2) throws SQLException {

    matchStatus = "PENDING";
    newGameStmt = con.prepareStatement("INSERT INTO gamedb.newgame (useridplayer1, useridplayer2, matchstatus) VALUES (?,?,?)");
    newGameStmt.setInt(1, player1);
    newGameStmt.setInt(2,player2);
    newGameStmt.setString(3, matchStatus);
    newGameStmt.executeUpdate();
    con.commit();

  }

  private int getUserId(String username) throws SQLException{

    getUserIDStmt = con.prepareStatement("SELECT * FROM gamedb.users where username = ?");
    getUserIDStmt.setString(1, username);
    ResultSet checkUser = getUserIDStmt.executeQuery();

    if(checkUser.next()) {

      userIDPlayer1 = checkUser.getInt("userid");

      return userIDPlayer1;
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

    matchStatus = "ONGOING";
    changeMatchStmt = con.prepareStatement("UPDATE gamedb.newgame SET matchstatus = ? where useridplayer1 = ?");
    changeMatchStmt.setString(1, matchStatus);
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
