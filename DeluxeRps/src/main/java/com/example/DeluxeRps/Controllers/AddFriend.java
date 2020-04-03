package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Models.FriendList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AddFriend extends GenericController{

    private Connection con;
    private PreparedStatement checkFriendStmt, addFriendStmt, checkOnlineStmt;
    private String usernameFriend, username;
    private int userInt, userFriendInt;



    @FXML
    private TextField addFriendToList;

    @FXML
    private VBox friendListVBOX;



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
          postInitialize();

        } catch (Exception e) {
          e.printStackTrace();
          Alert alert = new Alert(Alert.AlertType.NONE, " Error! friend already exist in friend list", ButtonType.OK);
          alert.setTitle("Error adding friend");
          alert.show();
          postInitialize();
        }
      }
      else {
        Alert alert = new Alert(Alert.AlertType.NONE, " Error! friend already exist in friend list", ButtonType.OK);
        alert.setTitle("Error adding friend");
        alert.show();

      }
    }

    //FRiendsList method
   public void postInitialize() throws SQLException {

         con = ConDB.getConnection();
         con.setAutoCommit(false);

     try {
         username = Login.username;
         ResultSet a = checkUserId(username);
           ResultSet b = checkFriendList();

           checkFriendList();

       while (b.next()) {

         System.out.println("loopRunning");

             FriendList friendListDeluxe = new FriendList(
                 b.getInt("userid"),
                 b.getInt("friendid"),
                 b.getString("friendname"));

               Label friendsLabel = new Label(friendListDeluxe.getFriendName());
               System.out.println("Label added");

               Font font = new Font("Arial Black", 20);
               friendsLabel.setFont(font);
               friendListVBOX.getChildren().addAll(friendsLabel);
             }
           }catch (Exception e){
       e.printStackTrace();
       System.out.println("Fuck this spaghetti code");
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

    private void addFriend(int user, int friend, String friendName) throws SQLException{

        addFriendStmt = con.prepareStatement("INSERT INTO gamedb.friendslist VALUES (?, ?, ?)");
        addFriendStmt.setInt(1, user);
        addFriendStmt.setInt(2, friend);
        addFriendStmt.setString(3,friendName);
        addFriendStmt.executeUpdate();
        con.commit();

    }

    private ResultSet checkFriendList() throws SQLException {

        checkOnlineStmt = con.prepareStatement("SELECT * FROM gamedb.friendslist WHERE friendname = ?");
        checkOnlineStmt.setString(1,usernameFriend);
        ResultSet checkFriend = checkOnlineStmt.executeQuery();
        con.commit();

        return checkFriend;
  }






}
