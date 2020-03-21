package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Models.ConnectionMaster;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

public class Login {

  public static final SecureRandom secure = new SecureRandom();
  public static final Base64.Encoder b64Encode = Base64.getUrlEncoder();
  public Connection connection;
  public PreparedStatement loginStmt;
  private String username;
  private String password;


  //FXML-Objects
  @FXML
  TextField userNameInput;
  @FXML
  PasswordField passwordField;


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



  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

    //Getting input
    username = userNameInput.getText();
    password = passwordField.getText();

    //Get Connection
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection("jdbc:postgresql://ec2-176-34-97-213.eu-west-1.compute.amazonaws.com:5432/d2621gbprb812i", "igblmsacvvtqrc", "8aa6d775c64cc09d4e2aee35743c2ed90290530663b15d687f0e4bfff5542a68");
    } catch (Exception e) {e.printStackTrace();}

    //Check for user in DB
    try {
      loginStmt = connection.prepareStatement("SELECT * FROM gamedb.users WHERE username = ? AND password = ?");
      loginStmt.setString(1, username);
      loginStmt.setString(2, password);
      ResultSet validUser = loginStmt.executeQuery();

        //Exception-alerts
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

        //Logged in! Tokens wont work + also maybe add user til listofloggedinusers
        else{
        System.out.println("Authentication successful");
        generateToken();
        insertToken(generateToken(), username, password);
        Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);
        }

    }

    //Exceptions
    catch (SQLException e) {
      e.printStackTrace();
    }
    }

  //Back-Button works great
  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
  }


}
