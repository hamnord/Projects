package com.example.DeluxeRps;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.example.DeluxeRps.Controllers.Helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main extends Application {

  public static void main(String[] args) {
    // Start the JavaFX application by calling launch().
    launch(args);
  }

  @Override
  public void start(Stage mainMenu) throws IOException {

    Connection connection;

    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testDB", "test", "123");
      connection.setAutoCommit(false);
    } catch (Exception e) {
      e.printStackTrace();
    }

    FXMLLoader loader = Helper.getLoader(Helper.mainMenuFXML);
    Parent root = loader.load();
    Scene scene = new Scene(root);

    mainMenu.setTitle(Helper.mainMenuTitle);
    mainMenu.setScene(scene);
    mainMenu.show();
    mainMenu.toFront();

  }
}
