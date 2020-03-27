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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Helper {

  //TITLE
  public static final String createPlayerTitle = "Create Player!";
  public static final String loginTitle = "Login!";
  public static final String mainMenuTitle = "Main Menu!";
  public static final String selectPlayerModeTitle = "Select Player!";
  public static final String startGameComTitle = "Start Game!";
  public static final String handWinnerTitle = "Humans Won!";
  public static final String covidWinnerTitle = "Covid Won!";
  public static final String paperWinnerTitle = "TP Won!";
  public static final String announceLoserTitle = "You Lost!";
  public static final String instructionsTitle = "Game rules";

  //FXML
  public static final String createPlayerFXML = "Fxml/CreatePlayer.fxml";
  public static final String loginFXML = "Fxml/Login.fxml";
  public static final String mainMenuFXML = "Fxml/MainMenu.fxml";
  public static final String selectPlayerModeFXML = "Fxml/SelectPlayerMode.fxml";
  public static final String startGameComFXML = "Fxml/StartGameCom.fxml";
  public static final String covidWinnerFXML = "Fxml/CovidWinnerWindow.fxml";
  public static final String paperWinnerFXML = "Fxml/PaperWinnerWindow.fxml";
  public static final String handWinnerFXML = "Fxml/HandWinnerWindow.fxml";
  public static final String announceLoserFXML = "Fxml/AnnounceLoser.fxml";
  public static final String instructionsFXML = "Fxml/Instructions.fxml";

  public static int userid;
  static String username;


  static Connection con;

  public static URL getRes(String fileName) {
    return Thread.currentThread().getContextClassLoader().getResource(fileName);
  }

  public static FXMLLoader getLoader(String fxmlpath) {
    return new FXMLLoader(getRes(fxmlpath));
  }

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

   static void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {

    try {

     ResultSet user = Login.checkUser(username);
      user.next();

        int userid = user.getInt("userid");

        logOut(userid);
        System.out.println("exiting1 user id removed");
        removeToken(userid);
        System.out.println("exiting2 token removed");
        System.exit(0);
        Platform.exit();

    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void removeToken(int userid) throws SQLException {

    PreparedStatement removeTokenStmnt = con.prepareStatement("DELETE FROM gamedb.tokens VALUES (?)");
    removeTokenStmnt.setInt(1, userid);
    removeTokenStmnt.executeUpdate();
    con.commit();

  }

  public static void logOut(int userid) throws SQLException {

    PreparedStatement logout = con.prepareStatement("DELETE FROM gamedb.logedinusers VALUES (?)");
    logout.setInt(1, userid);
    logout.executeUpdate();
    con.commit();

    con.close();
    logout.close();

  }
}
