package com.example.DeluxeRps;

import com.example.DeluxeRps.Controllers.InviteFriend;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    }
}

