package com.example.DeluxeRps.Controllers;


import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Random;


/**
 *
 * play versus computer
 * @author Heidi & Hampus
 *
 */
public class StartGameCom {


   private RPS player1;
   private static RPS player2;
   private static Random generator = new Random();

  /**
   * Player chooses Rock (Covid), RPS checks who won -> new scene
   * @param mouseEvent
   * @throws IOException
   */
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


  /**
   * Player chooses Paper (Paper), RPS checks who won -> new scene
   * @param mouseEvent
   * @throws IOException
   */
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

  /**
   * Player chooses Hand (Hand), RPS checks who won -> new scene
   * @param mouseEvent
   * @throws IOException
   */
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

  public void exitButtonClicked(MouseEvent mouseEvent) {
    Login.exitButtonClicked(mouseEvent);
  }


  /**
   * Creates enum with boolean beats
   * @author heidiguneriussen
   */
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

    /**
     * Generates random number between 1 and 3
     * @author heidiguneriussen
     */
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

