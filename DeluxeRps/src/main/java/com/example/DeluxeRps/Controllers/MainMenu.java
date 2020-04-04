package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Main Menu
 * calls different views
 */

public class MainMenu{

  /**
   * login
   * @param mouseEvent
   * @throws IOException
   */

  public void loginButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.loginFXML, Helper.loginTitle, mouseEvent);
  }

  /**
   * creeate player
   * @param mouseEvent
   * @throws IOException
   */

  public void createPlayerButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.createPlayerFXML, Helper.createPlayerTitle, mouseEvent);
  }

  /**
   * exitbutton
   * @param mouseEvent
   */

  public void exitButtonClicked(MouseEvent mouseEvent){
    Login.exitButtonClicked(mouseEvent);
  }

}
