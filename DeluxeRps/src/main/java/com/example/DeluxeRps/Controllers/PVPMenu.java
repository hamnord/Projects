package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class PVPMenu {

  public void addFriendButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.addFriendFXML, Helper.addFriendTitle, mouseEvent);
  }

  public void inviteFriendButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.inviteFriendFXML, Helper.inviteFriendTitle, mouseEvent);
  }

  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);
  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }


}
