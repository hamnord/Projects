package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.graalvm.compiler.phases.common.NodeCounterPhase;

import java.io.IOException;
import java.net.URL;

public class Helper {

  //TITLE
  public static final String createPlayerTitle = "Create Player!";
  public static final String loginTitle = "Login!";
  public static final String mainMenuTitle = "Main Menu!";
  public static final String selectPlayerModeTitle = "Select Player!";
  public static final String startGameTitle = "Start Game!";
  public static final String announceWinnerTitle = "You Won!";
  public static final String announceLoserTitle = "You Lost!";

  //FXML
  public static final String createPlayerFXML = "Fxml/CreatePlayer.fxml";
  public static final String loginFXML = "Fxml/Login.fxml";
  public static final String mainMenuFXML = "Fxml/MainMenu.fxml";
  public static final String selectPlayerModeFXML = "Fxml/SelectPlayerMode.fxml";
  public static final String startGameFXML = "Fxml/StartGame.fxml";
  public static final String announceWinnerFXML = "Fxml/AnnounceWinner.fxml";
  public static final String announceLoserFXML = "Fxml/AnnounceLoser.fxml";

  public static URL getRes(String fileName){
    return Thread.currentThread().getContextClassLoader().getResource(fileName);
  }

  public static FXMLLoader getLoader(String fxmlpath){
    return new FXMLLoader(getRes(fxmlpath));
  }

  static void replaceScene(String fxmlPath, String windowTitle) throws IOException {
    Stage stage = new Stage();
    stage.setTitle(windowTitle);
    FXMLLoader loader = getLoader(fxmlPath);
    Parent root = loader.load();
    Scene scene = new Scene(root);

    try{
    stage.setScene(scene);
    stage.show();
    stage.toFront();
    }
    catch (Exception e){
      e.printStackTrace();
    }

  }

}