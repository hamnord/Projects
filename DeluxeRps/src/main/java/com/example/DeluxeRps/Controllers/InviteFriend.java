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
  PreparedStatement checkFriendsList, gameRequestSTMNT, newGameSTMNT, getUserIDStmt, changeMatchStmt, whoIsOnlineStmt;
  String username = Login.username;
  String friendUsername;
  int useridplayer1,useridplayer2;
  static String matchstatus;
  ResultSet c, d;





  //FXML-Objects

  @FXML
  private ListView<Player> friendsList = new ListView<>();


  @FXML
  private ListView<Player> requestsList = new ListView<>();



  @Override
  public void postInitialize() throws SQLException {

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
          String friendname = c.getString("friendname");
          int friendid = c.getInt("friendid");
          Player player2 = new Player(friendname, friendid);
          friendsList.getItems().add(player2);
        }


        ResultSet d = gameRequests(useridplayer1);

        while (d.next()) {
          int player2userID = d.getInt("useridplayer1");
          Player player2 = new Player(Player.getUserName(player2userID), player2userID);
          requestsList.getItems().add(player2);
        }

        return;

      } catch (SQLException e) {
        e.printStackTrace();
      }


    }

  }



  public void startGameButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGamePlayerFXML, Helper.startGamePlayerTitle, mouseEvent);
  }


  public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.pvpMenuFXML, Helper.pvpMenuTitle, mouseEvent);
  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }

  public void checkforfriends(MouseEvent mouseEvent) throws SQLException {
    postInitialize();
  }

  public void clickInviteFriend(MouseEvent arg0) {
    System.out.println("clicked on " + friendsList.getSelectionModel().getSelectedItem());
    try {
      newGameRequest(useridplayer1, Player.getUserId(friendsList.getSelectionModel().getSelectedItem().toString()));
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public void clickAcceptRequest(MouseEvent arg0) {
    System.out.println("clicked on " + requestsList.getSelectionModel().getSelectedItem());
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
    while (checkUser.next()) {
      useridplayer1 = checkUser.getInt("userid");

      return useridplayer1;
    }

    return 0;
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

    return whoIsOnlineStmt.executeQuery();

  }



}
