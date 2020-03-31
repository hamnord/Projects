package com.example.DeluxeRps.Controllers;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class SelectPlayerMode {

  public void playvscomButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGameComFXML, Helper.startGameComTitle, mouseEvent);
  }

  public void playvsplayerButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.pvpMenuFXML, Helper.pvpMenuTitle, mouseEvent);
  }

  public void instructionsButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.instructionsFXML, Helper.instructionsTitle, mouseEvent);
  }

  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    try {
      Login.logOut();
      Login.removeToken();
      System.out.println("Login fucking out");
      Helper.replaceScene(Helper.loginFXML, Helper.loginTitle, mouseEvent);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }

}
