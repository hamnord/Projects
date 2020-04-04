package com.example.DeluxeRps.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 *
 */

public class Login {

  private static final SecureRandom secure = new SecureRandom();
  private PreparedStatement logIn, loginStmt, tokenStmt;
  static String username;
  private String password;
  private static int userid;
  private Connection con;
  private static Connection connection;


  //FXML-Objects
  @FXML
  TextField userNameInput;
  @FXML
  PasswordField passwordField;

    /**
     *
     * @return token
     */
  private static String generateToken() {
      byte[] randomBytes = new byte[24];
      secure.nextBytes(randomBytes);
      String token = Base64.getEncoder().encodeToString(randomBytes);
      return token;
  }

 //ON MOUSE CLICKED

    /**
     * Sets of Alerts if something is wrongly entered or missing.
     * Checks if password is correctly entered via BCrypt.
     * @param mouseEvent
     * @throws IOException
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     */
  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException, NoSuchAlgorithmException {

    //Getting input
    username = userNameInput.getText();
    password = passwordField.getText();

    //Get Connection
    con = ConDB.getConnection();
    con.setAutoCommit(false);

    //Check for user in DB
    try {

      ResultSet validUser = checkUser();

      while(validUser.next()){

        //Checking if pw matches hashed pw
        if (BCrypt.checkpw(password, validUser.getString("password"))){

          System.out.println("Authentication successful");
          userid = validUser.getInt("userid");

          loggedIn();
          String token = generateToken();
          insertToken(token);

          Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);

        }

        else if (!BCrypt.checkpw(password, validUser.getString("password"))){

          Alert alert = new Alert(AlertType.NONE, " Error! Credentials does not match", ButtonType.OK);
          alert.setTitle("Error in authentication");
          alert.show();

        }
      }
        //If no input
        if (userNameInput == null && passwordField == null){
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

  static void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
      try {
          logOut();
          removeToken();
          deleteMatch();
          System.out.println("exiting fucking program");
          System.exit(0);
          Platform.exit();
      } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeButtonClicked(MouseEvent mouseEvent) throws SQLException {
        exitButtonClicked(mouseEvent);
    }



  //STATEMENTS
    /**
     * Checks for users in DB
     * @return ResultSet
     * @throws SQLException
     */
  private ResultSet checkUser() throws SQLException{

    loginStmt = con.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
    loginStmt.setString(1, username);
    return loginStmt.executeQuery();

  }


    /**
     * Creates logedinuser in DB
     * @throws SQLException
     */
  private void loggedIn() throws SQLException {

    logIn = con.prepareStatement("INSERT INTO gamedb.logedinusers VALUES (?,?)");
    logIn.setInt(1,userid);
    logIn.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
    logIn.executeUpdate();
    con.commit();

  }

    /**
     * Removes user from logedinusers in DB
     * @throws SQLException
     */
  static void logOut() throws SQLException {

    connection = ConDB.getConnection();
    connection.setAutoCommit(false);

    PreparedStatement logout = connection.prepareStatement("DELETE FROM gamedb.logedinusers WHERE \"userid\" = ?; ");
    logout.setInt(1, userid);
    logout.executeUpdate();
    connection.commit();

    }


    /**
     * Creates token in DB
     * @param token
     * @throws SQLException
     */
  private void insertToken(String token) throws SQLException{

      tokenStmt = con.prepareStatement("INSERT INTO gamedb.tokens VALUES (?, ?, ?);");
      tokenStmt.setString(1, token);
      tokenStmt.setInt(2, userid);
      tokenStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
      tokenStmt.executeUpdate();
      con.commit();

  }

    /**
     * Removes token from DB
     * @throws SQLException
     */
  static void removeToken() throws SQLException {

    connection = ConDB.getConnection();
    connection.setAutoCommit(false);

    PreparedStatement removeTokenStmnt = connection.prepareStatement("DELETE FROM gamedb.tokens WHERE \"userid\" = ?;");
    removeTokenStmnt.setInt(1, userid);
    removeTokenStmnt.executeUpdate();
    connection.commit();

  }

  static void deleteMatch() throws SQLException {

    connection = ConDB.getConnection();
    connection.setAutoCommit(false);

    PreparedStatement delete = connection.prepareStatement("DELETE FROM gamedb.match WHERE \"userid\" = ?; ");
    delete.setInt(1, userid);
    delete.executeUpdate();
    connection.commit();

  }



}
