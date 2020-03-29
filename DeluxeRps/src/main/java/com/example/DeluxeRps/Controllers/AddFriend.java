package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
    private TextField addFriend;


    public void confirmButtonClicked(MouseEvent mouseEvent) throws IOException, SQLException, NoSuchAlgorithmException {


            con = ConDB.getConnection();
            username = Login.username;
            usernameFriend = addFriend.getText();

            if(checkUserId(usernameFriend).next()){

               ResultSet a = checkUserId(username);
               ResultSet b = checkUserId(usernameFriend);
               userInt = a.getInt("userid");
               userFriendInt = b.getInt("userid");

               addFriend(userInt, userFriendInt);

            }
            else {

                Alert alert = new Alert(Alert.AlertType.NONE, "Error! this user don't exists", ButtonType.OK);
                alert.setTitle("Error in adding friend");
                alert.show();

            }


    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
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
