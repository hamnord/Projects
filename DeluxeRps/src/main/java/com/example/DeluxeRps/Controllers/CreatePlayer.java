package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Connection;


public class CreatePlayer {

  final FileChooser fileChooser = new FileChooser();

  String username, password, repeatPass, email, securePW;
  Connection con;
  PreparedStatement existingUser, regPlayerStmnt, tokenStmnt, logIn;


  //FXML-Objects
  @FXML
  private TextField newUserName;
  @FXML
  private PasswordField newPassword;
  @FXML
  private PasswordField repeatPassword;
  @FXML
  private TextField newEmail;



  public void confirmButtonClicked(MouseEvent mouseEvent) throws IOException, SQLException, NoSuchAlgorithmException {

    //Getting input
    username = newUserName.getText();
    password = newPassword.getText();
    repeatPass = repeatPassword.getText();
    email = newEmail.getText();


    //Crypting pw with BCrypt
    securePW = BCryptify.hashPassword(password);


    //Get Connection
    con = ConDB.getConnection();
    con.setAutoCommit(false);


    //Prep statement
    try {

      ResultSet storedUser = existingUserMt(username);

      //If user exists in users-table
      if (storedUser.next()) {
        Alert alert = new Alert(AlertType.NONE, "Error! this user already exists", ButtonType.OK);
        alert.setTitle("Error in creating user");
        alert.show();
      } else {

        //Exceptions for input
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

        //Adding user to DB
        else {

          addPlayerToDB(username, securePW, email);

        }

      }

      Helper.replaceScene(Helper.loginFXML, Helper.loginTitle, mouseEvent);;

    }

    //Exceptions
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (regPlayerStmnt != null) {
        regPlayerStmnt.close();
      }
      if (existingUser != null) {
        existingUser.close();
      }

    }
  }


  //Back-Button, works great
  public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
  }


  ///////////////////////////////

  // TODO just fix this shit when there is time bruh
  //Youll fix it bruh, I believe in ya
  @FXML
  public void addProfilePicture(MouseEvent mouseEvent) throws MalformedURLException {
    Stage stage = new Stage();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {

      Image img = new Image(file.toURI().toURL().toString());
      ImageView imageView = new ImageView();
      imageView.setImage(img);

    }

    ////////////////////

  }

  //SQL STATEMENTS:
  public ResultSet existingUserMt(String username) throws SQLException {

    existingUser = con.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
    existingUser.setString(1, username);
    ResultSet storedUser = existingUser.executeQuery();
    con.commit();

    return storedUser;

  }

  public void addPlayerToDB(String username, String pw, String email) throws SQLException {

    regPlayerStmnt = con.prepareStatement("INSERT INTO gamedb.users (username, password, email) VALUES (?,?,?)");
    regPlayerStmnt.setString(1, username);
    regPlayerStmnt.setString(2, pw);
    regPlayerStmnt.setString(3, email);
    regPlayerStmnt.executeUpdate();
    con.commit();

  }




}
