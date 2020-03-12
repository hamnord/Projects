package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SelectPlayerMode {

  public void startGameButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGameFXML, Helper.startGameTitle, mouseEvent);
  }

}
