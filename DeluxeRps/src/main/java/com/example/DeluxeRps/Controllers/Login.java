package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Login {

  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.selectPlayerModeFXML,Helper.selectPlayerModeTitle, mouseEvent);
  }


}
