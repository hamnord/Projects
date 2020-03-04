package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Main;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {

  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.loginFXML,Helper.loginTitle);
  }

  public void createPlayerButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.createPlayerFXML,Helper.createPlayerTitle);
  }

}
