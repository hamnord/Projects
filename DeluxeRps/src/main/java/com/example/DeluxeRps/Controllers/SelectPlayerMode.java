package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SelectPlayerMode {

  public void playvscomButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGameComFXML, Helper.startGameComTitle, mouseEvent);
  }

  public void playvsplayerButtonClicked (MouseEvent mouseEvent) throws IOException {

    //EXCEPT different scene + controller
    Helper.replaceScene(Helper.startGameComFXML, Helper.startGameComTitle, mouseEvent);
  }

}
