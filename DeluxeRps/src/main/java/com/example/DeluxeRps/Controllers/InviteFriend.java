package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Models.Match;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Class checks for online friends, sends requests and starts a game
 */
public class InviteFriend extends GenericController{

  private Connection con;
  private PreparedStatement gameRequestStmt, newGameStmt, getUserIDStmt, changeMatchStmt, whoIsOnlineStmt, getUsernameStmt;
  private String username = Login.username;
  private int userIDPlayer1;
  private static String matchStatus;


  @FXML
  private ListView<String> friendsList = new ListView<>();

  @FXML
  private ListView<String> requestsList = new ListView<>();


  /**
   * Checks for online users who also exists in friendslist in DB.
   * Checks for newmatch with value "PENDING" in DB as requests.
   * @throws SQLException
   *
   * @author heidiguneriussen
   */
  @Override
  public void postInitialize() throws SQLException {

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



  /**
   * Checks for newmatch in DB with matchstatus "PENDING"
   * @param
   * @return ResultSet requests
   * @throws SQLException
   */

  private ResultSet gameRequests(int userid) throws SQLException {

    matchStatus = "PENDING";
    gameRequestStmt = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer2 = ? AND matchstatus = ?");
    gameRequestStmt.setInt(1, userid);
    gameRequestStmt.setString(2, matchStatus);
    ResultSet requests = gameRequestStmt.executeQuery();
    return requests;

  }

  /**
   * Creates newmatch in DB with params
   * @param player1
   * @param player2
   * @throws SQLException
   */
  private void newGameRequest(int player1, int player2) throws SQLException {

    matchStatus = "PENDING";
    newGameStmt = con.prepareStatement("INSERT INTO gamedb.newgame (useridplayer1, useridplayer2, matchstatus) VALUES (?,?,?)");
    newGameStmt.setInt(1, player1);
    newGameStmt.setInt(2,player2);
    newGameStmt.setString(3, matchStatus);
    newGameStmt.executeUpdate();
    con.commit();

  }

  /**
   * Getter for userid in DB
   * @param username
   * @return int userIDPlayer1 if possible
   * @throws SQLException
   */
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

  /**
   * Getter for username in DB
   * @param playerID
   * @return String username
   * @throws SQLException
   */
  private String getUsername(int playerID) throws SQLException {

    getUsernameStmt = con.prepareStatement("SELECT * FROM gamedb.users WHERE userid = ?");
    getUsernameStmt.setInt(1, playerID);
    ResultSet a = getUsernameStmt.executeQuery();

    if(a.next()) {

      return a.getString("username");
    }

    return null;

  }

  /**
   * Changes matchstatus in DB
   * @param useridplayer1
   * @throws SQLException
   */
  private void changeMatchStatus(int useridplayer1) throws SQLException {

    matchStatus = "ONGOING";
    changeMatchStmt = con.prepareStatement("UPDATE gamedb.newgame SET matchstatus = ? where useridplayer1 = ?");
    changeMatchStmt.setString(1, matchStatus);
    changeMatchStmt.setInt(2, useridplayer1);
    changeMatchStmt.executeUpdate();
    con.commit();

  }



  /**
   * Checks for online users in friendslist via tokens
   * @param userid
   * @return Resultset
   * @throws SQLException
   */
  private ResultSet getOnlineFriends(int userid) throws SQLException{

    whoIsOnlineStmt = con.prepareStatement("select * from gamedb.users inner join gamedb.friendslist on friendslist.friendid = users.userid where friendslist.userid in (select users.userid from gamedb.users inner join gamedb.tokens on tokens.userid = users.userid where users.userid = ?)");
    whoIsOnlineStmt.setInt(1, userid);

    return whoIsOnlineStmt.executeQuery();

  }






}
