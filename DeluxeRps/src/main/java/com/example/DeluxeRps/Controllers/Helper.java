package com.example.DeluxeRps.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * handles all views and scenes
 *
 * @author Hampus
 *
 * shoutout to dogeDice-fam for help
 */

public class Helper {

  //TITLE
  public static final String createPlayerTitle = "Create Player!";
  public static final String loginTitle = "Login!";
  public static final String mainMenuTitle = "Main Menu!";
  public static final String selectPlayerModeTitle = "Select Player!";
  public static final String startGameComTitle = "Start Game!";
  public static final String startGamePlayerTitle = "Start Game!";
  public static final String pvpMenuTitle = "PVP MENU!";
  public static final String addFriendTitle = "ADD A FRIEND!";
  public static final String inviteFriendTitle = "INVITE A FRIEND!";

  //RESULT TITLES
  public static final String handWinnerTitle = "HAND Won!";
  public static final String handLoserTitle = "HAND Won!";
  public static final String handTIETitle = "TIE!";
  public static final String covidWinnerTitle = "COVID Won!";
  public static final String covidLoserTitle = "COVID LOST!";
  public static final String covidTIETitle = "TIE!";
  public static final String paperWinnerTitle = "TP Won!";
  public static final String paperLoserTitle = "TP LOST!";
  public static final String paperTIETitle = "TIE!";

  public static final String instructionsTitle = "Game rules";

  //FXML
  public static final String createPlayerFXML = "Fxml/CreatePlayer.fxml";
  public static final String loginFXML = "Fxml/Login.fxml";
  public static final String mainMenuFXML = "Fxml/MainMenu.fxml";
  public static final String selectPlayerModeFXML = "Fxml/SelectPlayerMode.fxml";
  public static final String startGameComFXML = "Fxml/StartGameCom.fxml";
  public static final String startGamePlayerFXML = "Fxml/StartGamePlayer.fxml";
  public static final String pvpMenuFXML = "Fxml/PVPMenu.fxml";
  public static final String addFriendFXML = "Fxml/AddFriendWindow.fxml";
  public static final String inviteFriendFXML = "Fxml/InviteFriendWindow.fxml";


  //RESULT FXML WINDOWS
  public static final String covidWinnerFXML = "Fxml/CovidWinnerWindow.fxml";
  public static final String covidLoserFXML = "Fxml/CovidLoserWindow.fxml";
  public static final String covidTIEFXML = "Fxml/CovidTIEWindow.fxml";
  public static final String paperWinnerFXML = "Fxml/PaperWinnerWindow.fxml";
  public static final String paperLoserFXML = "Fxml/PaperLoserWindow.fxml";
  public static final String paperTIEFXML = "Fxml/PaperTIEWindow.fxml";
  public static final String handWinnerFXML = "Fxml/HandWinnerWindow.fxml";
  public static final String handLoserFXML = "Fxml/HandLoserWindow.fxml";
  public static final String handTIEFXML = "Fxml/HandTIEWindow.fxml";

  public static final String instructionsFXML = "Fxml/Instructions.fxml";

  public static URL getRes(String fileName) {
    return Thread.currentThread().getContextClassLoader().getResource(fileName);
  }

  public static FXMLLoader getLoader(String fxmlpath) {
    return new FXMLLoader(getRes(fxmlpath));
  }

  /**
   * reeplaces scene
   * @param fxmlPath
   * @param windowTitle
   * @param mouseEvent
   * @throws IOException
   */
  static void replaceScene(String fxmlPath, String windowTitle, MouseEvent mouseEvent) throws IOException {

    Stage stage = (Stage) ((Node) mouseEvent.getSource())
        .getScene()
        .getWindow();

    FXMLLoader loader = getLoader(fxmlPath);
    Parent root = loader.load();
    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.setTitle(windowTitle);
    stage.show();
    stage.toFront();

  }
}


