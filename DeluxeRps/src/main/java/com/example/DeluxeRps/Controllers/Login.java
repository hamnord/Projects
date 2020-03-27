package com.example.DeluxeRps.Controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.graalvm.compiler.phases.common.NodeCounterPhase;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Base64;



public class Login {

  public static final SecureRandom secure = new SecureRandom();
  PreparedStatement logIn, logout, loginStmt, tokenStmt, removeTokenStmnt;
  static String username;
  private String password;
  Connection con;


  //FXML-Objects
  @FXML
  TextField userNameInput;
  @FXML
  PasswordField passwordField;

  public static String generateToken () {
      byte[] randomBytes = new byte[24];
      secure.nextBytes(randomBytes);
      String token = Base64.getEncoder().encodeToString(randomBytes);
      return token;
  }

 //ON MOUSE CLICKED
  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException, NoSuchAlgorithmException {

    //Getting input
    username = userNameInput.getText();
    password = passwordField.getText();

    //Get Connection
    con = ConDB.getConnection();
    con.setAutoCommit(false);

    //Check for user in DB
    try {

      ResultSet validUser = checkUser(username);

      while(validUser.next()){

        //Checking if pw matches hashed pw
        if (BCrypt.checkpw(password, validUser.getString("password"))){

          System.out.println("Authentication successful");
          int userid = validUser.getInt("userid");

          logedIn(userid);
          String token = generateToken();
          insertToken(token, userid);

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


  //SQL-STATEMENTS:
  public ResultSet checkUser(String username) throws SQLException{

    loginStmt = con.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
    loginStmt.setString(1, username);
    ResultSet validUser = loginStmt.executeQuery();
    return validUser;

  }


  // WORKS
  public void logedIn (int userid) throws SQLException {

    logIn = con.prepareStatement("INSERT INTO gamedb.logedinusers VALUES (?, ?)");
    logIn.setInt(1, userid);
    logIn.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
    logIn.executeUpdate();
    con.commit();

  }

  //NOT IN USE CURRENTLY BUT SHOULD BE CALLED WHEN SYSTEM.EXIT(0)
  public void logOut (int userid) throws SQLException {

      logout = con.prepareStatement("DELETE FROM gamedb.logedinusers VALUES (?)");
      logout.setInt(1, userid);
      logout.executeUpdate();
      con.commit();

      con.close();
      logout.close();

    }


  //WORKSSSSSS, NOT SURE HOW TO SET VALUE AND HOW TO MAKE TOKENS GO AWAY
  public void insertToken (String token, int userid) throws SQLException{

      tokenStmt = con.prepareStatement("INSERT INTO gamedb.tokens VALUES (?, ?, ?)");
      tokenStmt.setString(1, token);
      tokenStmt.setInt(2, userid);
      tokenStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
      tokenStmt.executeUpdate();
      con.commit();

  }

  public void removeToken (int userid) throws SQLException {

      removeTokenStmnt = con.prepareStatement("DELETE FROM gamedb.tokens VALUES (?)");
      removeTokenStmnt.setInt(1, userid);
      removeTokenStmnt.executeUpdate();
      con.commit();

  }


  /* private void closeButtonClicked(String token, int userid) throws SQLException {

    Stage stage = (Stage) .getScene().getWindow();
    stage.setOnCloseRequest(event -> {

      try {
        logOut(userid);
        removeToken(token,userid);
        con.close();
        System.exit(0);

      } catch (SQLException e) {
        e.printStackTrace();
      }

  });
  } */

}
