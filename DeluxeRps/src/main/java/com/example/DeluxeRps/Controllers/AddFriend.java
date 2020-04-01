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

    Connection con;
    PreparedStatement checkFriendStmt, addFriendStmt, checkOnlineSTMNT;
    String usernameFriend, username, activefriendsList, friendname;
    int userInt, userFriendInt, userid;
    List<FriendList> bruhList;


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
          Alert alert = new Alert(Alert.AlertType.NONE, " Friend added!", ButtonType.OK);
          alert.setTitle("FRIEND ADDED");
          alert.show();
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

       try {

         con = ConDB.getConnection();
         con.setAutoCommit(false);

         username = Login.username;
         ResultSet a = checkUserId(username);
         ResultSet b = checkFriendList();

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
           friendListVBOX.getChildren().add(friendsLabel);

         }
         }catch(Exception e){
           e.printStackTrace();
           System.out.println("fuck u");
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

  public ResultSet checkFriendList() throws SQLException {

    checkOnlineSTMNT = con.prepareStatement("SELECT * FROM gamedb.friendslist WHERE friendname = ?");
    checkOnlineSTMNT.setString(1,usernameFriend);
    ResultSet checkfriend = checkOnlineSTMNT.executeQuery();
    con.commit();

    return checkfriend;
  }






}
