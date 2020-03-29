package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
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
    String username;

    @FXML
    private TextField addFriend;


    public void confirmButtonClicked(MouseEvent mouseEvent) throws IOException, SQLException, NoSuchAlgorithmException {

            con = ConDB.getConnection();
            username = addFriend.getText();

            checkUserId(username);



    }

    //PREPARED STATEMENTS
    public ResultSet checkUserId(String username) throws SQLException{

        checkFriendStmt = con.prepareStatement("SELECT * FROM gamedb.users where username = ?");
        checkFriendStmt.setString(1, username);
        ResultSet checkUser = checkFriendStmt.executeQuery();
        con.commit();

        return checkUser;


    }







}
