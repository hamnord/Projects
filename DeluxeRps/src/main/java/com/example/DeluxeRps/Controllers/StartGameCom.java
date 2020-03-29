package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Models.GameEngine;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class StartGameCom {


   RPS player1;
   static RPS player2;
  static Random generator = new Random();


  public void covidButtonClicked(MouseEvent mouseEvent) throws IOException {
    player1 = RPS.ROCK;

    RPS.cpGenerator();

    if (player1.beats(player2)) {
      System.out.println("You win");
      Helper.replaceScene(Helper.covidWinnerFXML, Helper.covidWinnerTitle, mouseEvent);
    } else if (player2.beats(player1)) {
      System.out.println("You Loose");
      Helper.replaceScene(Helper.covidLoserFXML, Helper.covidLoserTitle, mouseEvent);
    } else if (player1.equals(player2)){
      System.out.println("TIE");
      Helper.replaceScene(Helper.covidTIEFXML, Helper.covidTIETitle, mouseEvent);
    }
  }


  public void paperButtonClicked(MouseEvent mouseEvent) throws IOException {
    player1 = RPS.PAPER;

    RPS.cpGenerator();

    if (player1.beats(player2)) {
      System.out.println("You win");
      Helper.replaceScene(Helper.paperWinnerFXML, Helper.paperWinnerTitle, mouseEvent);
    } else if (player2.beats(player1)) {
      System.out.println("You Loose");
      Helper.replaceScene(Helper.paperLoserFXML, Helper.paperLoserTitle, mouseEvent);
    } else if (player1.equals(player2)){
      System.out.println("TIE");
      Helper.replaceScene(Helper.paperTIEFXML, Helper.paperTIETitle, mouseEvent);
    }
  }

  public void handButtonClicked(MouseEvent mouseEvent) throws IOException {
    player1 = RPS.SCISSORS;

    RPS.cpGenerator();

    if (player1.beats(player2)) {
      System.out.println("You win");
      Helper.replaceScene(Helper.handWinnerFXML, Helper.handWinnerTitle, mouseEvent);
    } else if (player2.beats(player1)) {
      System.out.println("You Loose");
      Helper.replaceScene(Helper.handLoserFXML, Helper.handLoserTitle, mouseEvent);
    } else if (player1.equals(player2)){
      System.out.println("TIE");
      Helper.replaceScene(Helper.handTIEFXML, Helper.handTIETitle, mouseEvent);
    }
  }

  public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
    Login.exitButtonClicked(mouseEvent);
  }

  public enum RPS {

    ROCK, PAPER, SCISSORS;

    public boolean beats(RPS other) {

      switch (this) {
        case ROCK:
          return other == SCISSORS;
        case PAPER:
          return other == ROCK;
        case SCISSORS:
          return other == PAPER;

        default:
          throw new IllegalStateException();

      }
    }

    public static void cpGenerator() {

      int cp = generator.nextInt(3) + 1;

      if (cp == 1) {
        player2 = RPS.ROCK;
      } else if (cp == 2) {
        player2 = RPS.PAPER;
      } else if (cp == 3) {
        player2 = RPS.SCISSORS;
      }

    }

  }
}

