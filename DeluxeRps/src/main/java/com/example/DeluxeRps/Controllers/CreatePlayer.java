package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Connection;

/**
 * Class creates player in DB
 */

public class CreatePlayer {


  private String username, password, repeatPass, email, securePW;
  private Connection con;
  private PreparedStatement existingUser, regPlayerStmt;


  @FXML
  private TextField newUserName;
  @FXML
  private PasswordField newPassword;
  @FXML
  private PasswordField repeatPassword;
  @FXML
  private TextField newEmail;


  /**
   * Throws Alerts if some input is missing or the user already exists, if not user is created
   * with hashed and salted password in DB
   *
   * @param mouseEvent
   * @throws IOException
   * @throws SQLException
   * @throws NoSuchAlgorithmException
   */
  public void confirmButtonClicked(MouseEvent mouseEvent) throws SQLException{


    username = newUserName.getText();
    password = newPassword.getText();
    repeatPass = repeatPassword.getText();
    email = newEmail.getText();



    securePW = BCryptic.hashPassword(password);



    con = ConDB.getConnection();
    con.setAutoCommit(false);



    try {

      ResultSet storedUser = existingUserMt(username);


      if (storedUser.next()) {
        Alert alert = new Alert(AlertType.NONE, "Error! this user already exists", ButtonType.OK);
        alert.setTitle("Error in creating user");
        alert.show();
      } else {


        if (username.equals("")) {
          Alert alert = new Alert(AlertType.NONE, "Error! Username field are empty", ButtonType.OK);
          alert.setTitle("Error in creating user");
          alert.show();
        } else if (password.equals("")) {
          Alert alert = new Alert(AlertType.NONE, "Error! Password field is empty", ButtonType.OK);
          alert.setTitle("Error in creating user");
          alert.show();
        } else if (!password.equals(repeatPass)) {
          Alert alert = new Alert(AlertType.NONE, "Error! password fields does not match", ButtonType.OK);
          alert.setTitle("Error in creating user");
          alert.show();
        } else if (email.equals("")) {
          Alert alert = new Alert(AlertType.NONE, "Error! Email field is empty", ButtonType.OK);
          alert.setTitle("Error in creating user");
          alert.show();
        }


        else {

          addPlayerToDB(username, securePW, email);

        }

      }

      Helper.replaceScene(Helper.loginFXML, Helper.loginTitle, mouseEvent);;

    }


    catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (regPlayerStmt != null) {
        regPlayerStmt.close();
      }
      if (existingUser != null) {
        existingUser.close();
      }

    }
  }



  public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
  }



  /**
   * Checks whether or not the input is already stored in DB
   * @param username
   * @return ResultSet storedUser
   * @throws SQLException
   */
  public ResultSet existingUserMt(String username) throws SQLException {

    existingUser = con.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
    existingUser.setString(1, username);
    ResultSet storedUser = existingUser.executeQuery();
    con.commit();

    return storedUser;

  }

  /**
   * Creates player in DB using input
   * @param username
   * @param pw
   * @param email
   * @throws SQLException
   */
  public void addPlayerToDB(String username, String pw, String email) throws SQLException {

    regPlayerStmt = con.prepareStatement("INSERT INTO gamedb.users (username, password, email) VALUES (?,?,?)");
    regPlayerStmt.setString(1, username);
    regPlayerStmt.setString(2, pw);
    regPlayerStmt.setString(3, email);
    regPlayerStmt.executeUpdate();
    con.commit();

  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }
}
