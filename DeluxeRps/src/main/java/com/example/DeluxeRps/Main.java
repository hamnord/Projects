package com.example.DeluxeRps;

import com.example.DeluxeRps.Controllers.Login;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.example.DeluxeRps.Controllers.Helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

  public static void main(String[] args) {
    // Start the JavaFX application by calling launch().
    launch(args);


  }


  @Override
  public void start(Stage mainMenu) throws IOException, SQLException {


    FXMLLoader loader = Helper.getLoader(Helper.mainMenuFXML);
    Parent root = loader.load();
    Scene scene = new Scene(root);

    mainMenu.setTitle(Helper.mainMenuTitle);
    mainMenu.setScene(scene);
    mainMenu.show();
    mainMenu.toFront();

  /*  mainMenu.setOnCloseRequest(e -> {

      try {
        ResultSet user = Login.checkUser(username);
        Login.removeToken(userid);
        Login.logOut(userid);
        System.out.println("Everything closed");

      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    });*/


    }
}

