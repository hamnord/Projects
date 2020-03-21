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
import java.sql.*;
import java.util.Base64;

public class CreatePlayer {

  

  public static final SecureRandom secure = new SecureRandom();
  public static final Base64.Encoder b64Encode = Base64.getUrlEncoder();
  final FileChooser fileChooser = new FileChooser();
  public Connection connection = null;
  public PreparedStatement prepStmnt;
  public PreparedStatement userIDStmnt;
  public PreparedStatement existingUserStmnt;
  public PreparedStatement tokenStmnt;


  public static String generateToken (){
    byte[] randomBytes = new byte[24];
    secure.nextBytes(randomBytes);
    return b64Encode.encodeToString(randomBytes);
  }

  //FXML-Objects
  @FXML
  private TextField newUserName;
  @FXML
  private PasswordField newPassword;
  @FXML
  private PasswordField repeatPassword;
  @FXML
  private TextField newEmail;

  //Other useful objects
  String username, password, repeatPass, email;



  public void confirmButtonClicked(MouseEvent mouseEvent) throws IOException, SQLException {

    //Getting input
    username = newUserName.getText();
    password = newPassword.getText();
    repeatPass = repeatPassword.getText();
    email = newEmail.getText();


    //Get Connection
    try {
          Class.forName("org.postgresql.Driver");
          connection = DriverManager.getConnection("jdbc:postgresql://ec2-176-34-97-213.eu-west-1.compute.amazonaws.com:5432/d2621gbprb812i", "igblmsacvvtqrc", "8aa6d775c64cc09d4e2aee35743c2ed90290530663b15d687f0e4bfff5542a68");
        } catch (Exception e) {e.printStackTrace();}

    //Prep statement
    try {
      existingUserStmnt = connection.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
      existingUserStmnt.setString(1, username);
      ResultSet storedUser = existingUserStmnt.executeQuery();

     //If user exists in users-table
     if (storedUser.next()){
        Alert alert = new Alert(AlertType.NONE, "Error! this user already exists", ButtonType.OK);
        alert.setTitle("Error in creating user");
        alert.show();
      }

      else {

          //Exceptions for input
          if (username.equals("")) {
          Alert alert = new Alert(AlertType.NONE, "Error! Username field are empty", ButtonType.OK);
          alert.setTitle("Error in creating user");
          alert.show();
          }
          else if (password.equals("")) {
          Alert alert = new Alert(AlertType.NONE, "Error! Password field is empty", ButtonType.OK);
          alert.setTitle("Error in creating user");
          alert.show();
          }
          else if (!password.equals(repeatPass)) {
          Alert alert = new Alert(AlertType.NONE, "Error! password fields does not match", ButtonType.OK);
          alert.setTitle("Error in creating user");
          alert.show();
          }
          else if (email.equals("")) {
          Alert alert = new Alert(AlertType.NONE, "Error! Email field is empty", ButtonType.OK);
          alert.setTitle("Error in creating user");
          alert.show();
          }

          //Adding user to DB
          else {
          prepStmnt = connection.prepareStatement("INSERT INTO gamedb.users (username, password, email) VALUES (?,?,?)");
          prepStmnt.setString(1, username);
          prepStmnt.setString(2, password);
          prepStmnt.setString(3, email);
          prepStmnt.executeUpdate();
          }



        //Not sure whats happening here, cant get tokens to work/not sure how
        String userToken = generateToken();

        userIDStmnt = connection.prepareStatement("SELECT userid FROM gamedb.users WHERE username = ? AND password = ?");
        userIDStmnt.setString(1, username);
        userIDStmnt.setString(2, password);
        ResultSet userid = userIDStmnt.executeQuery();
        int useridInt = userid.getInt("userid");

        tokenStmnt = connection.prepareStatement("INSERT INTO gamedb.tokens (tokenid, userid) VALUES (?, ?)");
        tokenStmnt.setString(1, userToken);
        tokenStmnt.setInt(2, useridInt);
        tokenStmnt.executeUpdate();

        Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);


      }
    }

    //Exceptions
    catch (Exception e) {
        e.printStackTrace();
      } finally {
      if(prepStmnt != null){
        prepStmnt.close();
      }
      if(userIDStmnt != null){
        userIDStmnt.close();
      }

    }
    }


  //Back-Button, works great
  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
  }


  // TODO just fix this shit when there is time bruh
  //Youll fix it bruh, I believe in ya
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
