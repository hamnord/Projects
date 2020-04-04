package com.example.DeluxeRps.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This Class adds users to friendslist in DB
 */


public class AddFriend extends GenericController{

    private Connection con;
    private PreparedStatement checkFriendStmt, addFriendStmt, checkOnlineStmt;
    private String usernameFriend, username;
    private int userInt, userFriendInt;



    @FXML
    private TextField addFriendToList;


    /**
     * Button checks for users in gamedb.friendslist and gamedb.users and add friend to friendlist if
     * it is possible, if not Alert is initiated.
     *
     * @param mouseEvent
     * @throws SQLException
     */

  public void confirmButtonClicked(MouseEvent mouseEvent) throws SQLException {

      con = ConDB.getConnection();
      con.setAutoCommit(false);

      username = Login.username;
      usernameFriend = addFriendToList.getText();

      ResultSet a = checkUserId(username);
      ResultSet b = checkUserId(usernameFriend);

      if (b.next() && a.next()) {

        userInt = a.getInt("userid");
        userFriendInt = b.getInt("userid");

        try {
          addFriend(userInt, userFriendInt, usernameFriend);
          System.out.println("Friend added to FriendList");
          Alert alert = new Alert(Alert.AlertType.NONE, "Friend: " + usernameFriend + ", added!", ButtonType.OK);
          alert.setTitle("FriendList");
          alert.show();

        } catch (Exception e) {
          e.printStackTrace();
          Alert alert = new Alert(Alert.AlertType.NONE, " Error! friend already exist in friend list", ButtonType.OK);
          alert.setTitle("Error adding friend");
          alert.show();
        }
      }
      else {
        Alert alert = new Alert(Alert.AlertType.NONE, " Error! friend already exist in friend list", ButtonType.OK);
        alert.setTitle("Error adding friend");
        alert.show();

      }
    }


    //Rest of Buttons in Scene
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        Helper.replaceScene(Helper.pvpMenuFXML, Helper.pvpMenuTitle, mouseEvent);
    }

    public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
        Login.exitButtonClicked(mouseEvent);
    }




    //PREPARED STATEMENTS
    /**
     * Checks UserID
     * @param username
     * @return ResultSet checkUser
     * @throws SQLException
     */

    private ResultSet checkUserId(String username) throws SQLException{

        checkFriendStmt = con.prepareStatement("SELECT * FROM gamedb.users where username = ?");
        checkFriendStmt.setString(1, username);
        ResultSet checkUser = checkFriendStmt.executeQuery();
        con.commit();

        return checkUser;


    }

    /**
     * Creates friendid in friendslist
     * @param user
     * @param friend
     * @param friendName
     * @throws SQLException
     */

    private void addFriend(int user, int friend, String friendName) throws SQLException{

        addFriendStmt = con.prepareStatement("INSERT INTO gamedb.friendslist VALUES (?, ?, ?)");
        addFriendStmt.setInt(1, user);
        addFriendStmt.setInt(2, friend);
        addFriendStmt.setString(3,friendName);
        addFriendStmt.executeUpdate();
        con.commit();

    }




}
