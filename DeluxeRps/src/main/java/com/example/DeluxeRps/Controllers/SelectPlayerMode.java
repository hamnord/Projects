package com.example.DeluxeRps.Controllers;


import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class SelectPlayerMode {

  public void playVSComButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGameComFXML, Helper.startGameComTitle, mouseEvent);
  }

  public void playVSPlayerButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.pvpMenuFXML, Helper.pvpMenuTitle, mouseEvent);
  }

  public void instructionsButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.instructionsFXML, Helper.instructionsTitle, mouseEvent);
  }


  public void backButtonClicked (MouseEvent mouseEvent) {
    try {
      Login.logOut();
      Login.removeToken();
      System.out.println("Login fucking out");
      Helper.replaceScene(Helper.loginFXML, Helper.loginTitle, mouseEvent);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public void exitButtonClicked(MouseEvent mouseEvent) {
    Login.exitButtonClicked(mouseEvent);
  }

}
