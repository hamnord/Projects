package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class WinnerWindow {

  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGameComFXML, Helper.startGameComTitle, mouseEvent);
  }
}
