package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class CreatePlayer {

  final FileChooser fileChooser = new FileChooser();

  @FXML
  public void addProfilePicture (MouseEvent mouseEvent) throws MalformedURLException {
    Stage stage = new Stage();
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {

      Image img = new Image(file.toURI().toURL().toString());
      ImageView imageView = new ImageView();
      imageView.setImage(img);

    }
  }

  public void confirmButtonClicked(MouseEvent mouseEvent) throws IOException {
    String storedUser = null;
    String newUser = null;

    if (newUser == null){
      Alert alert = new Alert(AlertType.NONE, "Error! Fields are empty", ButtonType.OK);
      alert.setTitle("Error in creating user");
      alert.show();
    }

    else if ( newUser == storedUser ) {
      Alert alert = new Alert(AlertType.NONE, "Error! This user already exist",ButtonType.OK);
      alert.setTitle("Error in creating user");
      alert.show();
    }
    else {
      //Todo place store user here

      System.out.println("User: " + newUser + " has been created.");
      Helper.replaceScene(Helper.selectPlayerModeFXML, Helper.selectPlayerModeTitle, mouseEvent);
    }
  }

  public void backButtonClicked (MouseEvent mouseEvent) throws IOException {
    Helper.replaceScene(Helper.mainMenuFXML, Helper.mainMenuTitle, mouseEvent);
  }


}
