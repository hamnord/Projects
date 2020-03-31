package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class AddFriend {

    Connection con;
    PreparedStatement checkFriendStmt, addFriendStmt;
    String usernameFriend, username;
    int userInt, userFriendInt;


    @FXML
    private TextField addFriendToList;


    public void confirmButtonClicked(MouseEvent mouseEvent) throws SQLException {

      con = ConDB.getConnection();
      con.setAutoCommit(false);

      username = Login.username;
      usernameFriend = addFriendToList.getText();

      ResultSet a = checkUserId(username);
      ResultSet b = checkUserId(usernameFriend);

      if (b.next() && a.next()) {

        System.out.println("loop running");
        userInt = a.getInt("userid");
        userFriendInt = b.getInt("userid");

        try {
          System.out.println("adding friend");
          addFriend(userInt, userFriendInt);
          Alert alert = new Alert(Alert.AlertType.NONE, " Friend added!", ButtonType.OK);
          alert.setTitle("FRIEND ADDED");
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


    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        Helper.replaceScene(Helper.pvpMenuFXML, Helper.pvpMenuTitle, mouseEvent);
    }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }




    //PREPARED STATEMENTS
    private ResultSet checkUserId(String username) throws SQLException{

        checkFriendStmt = con.prepareStatement("SELECT * FROM gamedb.users where username = ?");
        checkFriendStmt.setString(1, username);
        ResultSet checkUser = checkFriendStmt.executeQuery();
        con.commit();

        return checkUser;


    }

    private void addFriend(int user, int friend) throws SQLException{

        addFriendStmt = con.prepareStatement("INSERT INTO gamedb.friendslist VALUES (?, ?)");
        addFriendStmt.setInt(1, user);
        addFriendStmt.setInt(2, friend);
        addFriendStmt.executeUpdate();
        con.commit();

    }








}
