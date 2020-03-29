package com.example.DeluxeRps.Models;

import java.util.Random;

public class GameEngine {

  static Random generator = new Random();
  static RPS player2;

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

      int cp = generator.nextInt(3)+1;

      if (cp == 1){
        player2 = GameEngine.RPS.ROCK;
      }

      else if (cp == 2){
        player2 = GameEngine.RPS.PAPER;
      }

      else if (cp == 3){
        player2 = GameEngine.RPS.SCISSORS;
      }

    }


  }
}
