package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class SelectPlayerMode {

  public void playvscomButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.startGameComFXML, Helper.startGameComTitle, mouseEvent);
  }

  public void playvsplayerButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.pvpMenuFXML, Helper.pvpMenuTitle, mouseEvent);
  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }

}
