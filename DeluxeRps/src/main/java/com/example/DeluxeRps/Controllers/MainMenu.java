package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenu{

  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.loginFXML, Helper.loginTitle, mouseEvent);
  }

  public void createPlayerButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.createPlayerFXML, Helper.createPlayerTitle, mouseEvent);
  }

  public void instructionsButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.instructionsFXML, Helper.instructionsTitle, mouseEvent);
  }

}
