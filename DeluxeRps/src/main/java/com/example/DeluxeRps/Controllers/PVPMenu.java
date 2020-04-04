package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * pvpmenu
 *
 *  calls different views
 */

public class PVPMenu {

  /**
   * addfriend
   * @param mouseEvent
   * @throws IOException
   */
  public void addFriendButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.addFriendFXML, Helper.addFriendTitle, mouseEvent);
  }

  /**
   * inviteFriend/Send friendRequest
   * @param mouseEvent
   * @throws IOException
   */

  public void inviteFriendButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.inviteFriendFXML, Helper.inviteFriendTitle, mouseEvent);
  }

  /**
   * backbutton
   * @param mouseEvent
   * @throws IOException
   */

  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);
  }

  /**
   * exitbutton
   * @param mouseEvent
   */

  public void exitButtonClicked(MouseEvent mouseEvent){
    Login.exitButtonClicked(mouseEvent);
  }


}
