package com.example.DeluxeRps.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    Helper.replaceScene(Helper.selectPlayerModeFXML,Helper.selectPlayerModeTitle, mouseEvent);
  }


}
