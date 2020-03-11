package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends Main {


  private Connection connection;
  private String username;
  private String password;

  public Login(Connection connection, String username, String password) {
   this.connection = connection;
    this.username = username;
    this.password = password;

  }

  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

    PreparedStatement loginStmt = null;

    try {
      loginStmt = connection.prepareStatement("Select * from \"users\" where \"username\" = ? and \"password\" = ?;");
      loginStmt.setString(1, username);
      loginStmt.setString(2,password);
      ResultSet validUser = loginStmt.executeQuery();


      if (validUser == null){
        Alert alert = new Alert(AlertType.NONE, " Error! Credential fields are empty", ButtonType.OK);
        alert.setTitle("Error in authentication");
        alert.show();
      } else if (!validUser.next()){
        Alert alert = new Alert(AlertType.NONE, " Error! Credentials does not match", ButtonType.OK);
        alert.setTitle("Error in authentication");
        alert.show();
      }else{
        System.out.println("Authentication successful");
        Helper.replaceScene(Helper.selectPlayerModeFXML,Helper.selectPlayerModeTitle, mouseEvent);
      }

    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      if (loginStmt != null){
        loginStmt.close();
      }
    }

  }

  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML,Helper.mainMenuTitle, mouseEvent);
  }


}
