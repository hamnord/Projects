package com.example.DeluxeRps.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;



public class Login {

  public static final SecureRandom secure = new SecureRandom();
  public static final Base64.Encoder b64Encode = Base64.getUrlEncoder();
  public Connection connection;
  public Connection connection1;
  public PreparedStatement loginStmt;
  public PreparedStatement tokenStmnt;
  public PreparedStatement userIDtest;
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
    String token = Base64.getEncoder().encodeToString(randomBytes);
    return token;
  }

  //WONT WORK
  public void insertToken (String token, String userName){

    try {
      Class.forName("org.postgresql.Driver");
      connection1 = DriverManager.getConnection("jdbc:postgresql://ec2-176-34-97-213.eu-west-1.compute.amazonaws.com:5432/d2621gbprb812i", "igblmsacvvtqrc", "8aa6d775c64cc09d4e2aee35743c2ed90290530663b15d687f0e4bfff5542a68");
      connection1.setAutoCommit(false);
    } catch (Exception e) {e.printStackTrace();}


    try {
      userIDtest = connection1.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
      userIDtest.setString(1, username);
      ResultSet rs1 = userIDtest.executeQuery();
      int userId = rs1.getInt("userid");

      tokenStmnt = connection1.prepareStatement("INSERT INTO gamedb.tokens (tokenid, userid) VALUES (?, ?)");
      tokenStmnt.setString(1, token);
      tokenStmnt.setInt(2, userId);
      tokenStmnt.executeUpdate();

      connection1.commit();

    } catch (SQLException e){e.getStackTrace();}
  }



  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException, NoSuchAlgorithmException {

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
      loginStmt = connection.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
      loginStmt.setString(1, username);
      ResultSet validUser = loginStmt.executeQuery();

      while(validUser.next()){

        //Checking if pw matches hashed pw
        if (BCrypt.checkpw(password, validUser.getString("password"))){

          System.out.println("Authentication successful");
          generateToken();

          //WONT WORK
          insertToken(generateToken(), username);
          Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);

        }

        else if (!BCrypt.checkpw(password, validUser.getString("password"))){

          Alert alert = new Alert(AlertType.NONE, " Error! Credentials does not match", ButtonType.OK);
          alert.setTitle("Error in authentication");
          alert.show();

        }


      }

        //If no input
        if (validUser == null){
        Alert alert = new Alert(AlertType.NONE, " Error! Credential fields are empty", ButtonType.OK);
        alert.setTitle("Error in authentication");
        alert.show();
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
