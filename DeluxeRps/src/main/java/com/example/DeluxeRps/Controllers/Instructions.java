package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class Instructions {


  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }
}
