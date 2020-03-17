package com.example.DeluxeRps;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.example.DeluxeRps.Controllers.Helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

  public static void main(String[] args) {
    // Start the JavaFX application by calling launch().
    launch(args);
  }

  public Connection connection;

  @Override
  public void start(Stage mainMenu) throws IOException, SQLException {

    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection("jdbc:postgresql://ec2-176-34-97-213.eu-west-1.compute.amazonaws.com:5432/d2621gbprb812i", "igblmsacvvtqrc", "8aa6d775c64cc09d4e2aee35743c2ed90290530663b15d687f0e4bfff5542a68");
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
