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
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class CreatePlayer {

  public static final SecureRandom secure = new SecureRandom();
  public static final Base64.Encoder b64Encode = Base64.getUrlEncoder();
  final FileChooser fileChooser = new FileChooser();
  public Connection connection;
  public PreparedStatement prepStmnt;


  public static String generateToken (){
    byte[] randomBytes = new byte[24];
    secure.nextBytes(randomBytes);
    return b64Encode.encodeToString(randomBytes);
  }

  // osäker men inte lika mycket kaos
  public static void insertToken (String token, String userName, String passWord){
    String input = String.format("INSERT INTO \"tokens\" (\"tokenid\") VALUES (" + token + " ) " +
        "INNER JOIN user WHERE \"user_name\"='%s' and \"password\"='%s'", userName, passWord);
  }


  @FXML
  public void confirmButtonClicked(MouseEvent mouseEvent) throws IOException, SQLException {

    String username = newUserName.getText();
    String password = newPassword.getText();
    String email = newEmail.getText();

    try {
      PreparedStatement existingUserStmnt;
      existingUserStmnt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
      existingUserStmnt.setString(1, username);
      ResultSet storedUser = existingUserStmnt.executeQuery();


      if (storedUser.next()) {
        Alert alert = new Alert(AlertType.NONE, "Error! This user already exist", ButtonType.OK);
        alert.setTitle("Error in creating user");
        alert.show();

      } else if (username.equals("")) {
        Alert alert = new Alert(AlertType.NONE, "Error! Username field are empty", ButtonType.OK);
        alert.setTitle("Error in creating user");
        alert.show();

      } else if (password.equals("")) {
        Alert alert = new Alert(AlertType.NONE, "Error! Password field is empty", ButtonType.OK);
        alert.setTitle("Error in creating user");
        alert.show();

      } else if (email.equals("")) {
        Alert alert = new Alert(AlertType.NONE, "Error! Email field is empty", ButtonType.OK);
        alert.setTitle("Error in creating user");
        alert.show();

      } else {
        prepStmnt = connection.prepareStatement("INSERT INTO users (username,password,email) VALUES (?,?,?) ");
        prepStmnt.setString(1, username);
        prepStmnt.setString(2, password);
        prepStmnt.setString(3, email);
        prepStmnt.executeUpdate();

        generateToken();
        insertToken(generateToken(), username, password);
        Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }


  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
  }



  // TODO just fix this shit when there is time bruh
  @FXML
  public void addProfilePicture (MouseEvent mouseEvent) throws MalformedURLException {
    Stage stage = new Stage();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {

      Image img = new Image(file.toURI().toURL().toString());
      ImageView imageView = new ImageView();
      imageView.setImage(img);

    }
  }


}
