package com.example.DeluxeRps.Controllers;

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



public class AddFriend {

    Connection con;
    PreparedStatement checkFriendStmt, addFriendStmt, checkOnlineSTMNT;
    String usernameFriend, username, activefriendsList;
    int userInt, userFriendInt, userid;



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


    //FRiendsList Button
   public void refreshButton(MouseEvent mouseEvent) throws SQLException {

     con = ConDB.getConnection();
     con.setAutoCommit(false);

     username = Login.username;
     ResultSet a = checkUserId(username);

     while (a.next()){
       System.out.println("loop running");
       try {

         System.out.println("adding friend");

         Label friendsLabel = new Label(Integer.toString(checkFriendList()));
         Font font = new Font("Arial Black", 20);
         friendsLabel.setFont(font);
         friendListVBOX.getChildren().addAll(friendsLabel);

       }catch (Exception e){
         e.printStackTrace();
         System.out.println("fuck u");
       }
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

  public int checkFriendList() throws SQLException {

    checkOnlineSTMNT = con.prepareStatement("SELECT * FROM gamedb.friendslist WHERE userid = ?");
    checkOnlineSTMNT.setInt(1,userid);
    checkOnlineSTMNT.executeQuery();
    con.commit();
return userid;
  }






}
