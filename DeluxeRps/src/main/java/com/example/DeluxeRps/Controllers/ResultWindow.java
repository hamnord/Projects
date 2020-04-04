package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class ResultWindow {

  public void backButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
    Login.deleteMatch();
    Helper.replaceScene(Helper.startGameComFXML, Helper.startGameComTitle, mouseEvent);
  }

  public void exitButtonClicked(MouseEvent mouseEvent) {
    Login.exitButtonClicked(mouseEvent);
  }
}
