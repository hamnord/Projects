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

            if(checkUserId(usernameFriend).next()){

               ResultSet a = checkUserId(username);
              userInt = a.getInt("userid");
               ResultSet b = checkUserId(usernameFriend);
               userFriendInt = b.getInt("userid");

               addFriend(userInt, userFriendInt);

            }
            else {

                Alert alert = new Alert(Alert.AlertType.NONE, "Error! this user does not exist", ButtonType.OK);
                alert.setTitle("Error in adding friend");
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
