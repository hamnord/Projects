package com.example.DeluxeRps.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Login {

  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException {

    //TODO implement database usage with credentials
    //TODO implement with access token

    String userCredentials = null;
    String storedCredentials = null;

    if (userCredentials == null){
      Alert alert = new Alert(AlertType.NONE, " Error! Credential fields are empty", ButtonType.OK);
      alert.setTitle("Error in authentication");
      alert.show();
    } else if (!userCredentials.equals(storedCredentials)){
      Alert alert = new Alert(AlertType.NONE, " Error! Credentials does not match", ButtonType.OK);
      alert.setTitle("Error in authentication");
      alert.show();
    }else{
      System.out.println("Authentication successful");
    Helper.replaceScene(Helper.selectPlayerModeFXML,Helper.selectPlayerModeTitle, mouseEvent);
    }
  }

  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML,Helper.mainMenuTitle, mouseEvent);
  }


}
