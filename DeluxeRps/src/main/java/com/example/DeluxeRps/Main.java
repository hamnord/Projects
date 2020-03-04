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

public class Main extends Application {

  public static void main(String[] args) {
    // Start the JavaFX application by calling launch().
    launch(args);
  }

  @Override
  public void start(Stage mainMenu) throws IOException {

    FXMLLoader loader = Helper.getLoader(Helper.mainMenuFXML);
    Parent root = loader.load();
    Scene scene = new Scene(root);

    mainMenu.setTitle(Helper.mainMenuTitle);
    mainMenu.setScene(scene);
    mainMenu.show();
    mainMenu.toFront();
  }
}
