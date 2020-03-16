package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Models.ConnectionMaster;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class Login {

  public static final SecureRandom secure = new SecureRandom();
  public static final Base64.Encoder b64Encode = Base64.getUrlEncoder();
  public Connection connection;
  public PreparedStatement loginStmt;
  private String username;
  private String password;


  public static String generateToken (){
    byte[] randomBytes = new byte[24];
    secure.nextBytes(randomBytes);
    return b64Encode.encodeToString(randomBytes);
  }

  // osäker men inte lika mycket kaos
  public static void insertToken (String token, String userName, String passWord){
    String input = String.format("INSERT INTO \"token\" (\"token_id\") VALUES (" + token + " ) " +
        "INNER JOIN user WHERE \"user_name\"='%s' and \"password\"='%s'", userName, passWord);
  }


/*
  // säker men skapar kaos i koden
  public static void insertToken2 (String token, String username, String password) throws SQLException {
    PreparedStatement state;

    Connection connection;
    state = connection.prepareStatement("INSERT INTO \"token\" (\"token_id\") VALUES (" + token + " ) " +
        "INNER JOIN user WHERE \"user_name\"=? and \"password\"= ?");
    state.setString(1,username);
    state.setString(2,password);
    state.executeQuery();
  }*/

  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

    try {

      loginStmt = connection.prepareStatement("Select * from \"users\" where \"username\" = ? and \"password\" = ?;");
      loginStmt.setString(1, username);
      loginStmt.setString(2, password);
      ResultSet validUser = loginStmt.executeQuery();


      if (validUser == null){
        Alert alert = new Alert(AlertType.NONE, " Error! Credential fields are empty", ButtonType.OK);
        alert.setTitle("Error in authentication");
        alert.show();
      }
      else if (!validUser.next()){
        Alert alert = new Alert(AlertType.NONE, " Error! Credentials does not match", ButtonType.OK);
        alert.setTitle("Error in authentication");
        alert.show();
      }
      else{
        System.out.println("Authentication successful");
        generateToken();
        insertToken(generateToken(), username, password);
        Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);
      }

    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    }


  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
  }


}
