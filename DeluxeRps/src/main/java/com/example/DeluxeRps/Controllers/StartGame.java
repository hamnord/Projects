package com.example.DeluxeRps.Controllers;

import java.util.Random;

public class StartGame {

    RPS player1, player2;
    Random generator = new Random();


    //Koblas till vyer osvosv.
    public void gameResult(){
        setPlayer1();
        cpGenerator();

        if (player1.beats(player2)){

            System.out.println("You win");
        }

        else if (player2.beats(player1)){

            System.out.println("You Loose");
        }

        else if (player1.equals(player2)){

            System.out.println("TIE");

        }

        return;

    }



    //Koblas til XML-objekt och mouse-event
    public void setPlayer1() {
        player1 = RPS.ROCK;

    }

    //player2-choices if computer
    public void cpGenerator(){

        int cp = generator.nextInt(3)+1;

        if (cp == 1){
            player2 = RPS.ROCK;
        }

        else if (cp == 2){
            player2 = RPS.PAPER;
        }

        else if (cp == 3){
            player2 = RPS.SCISSORS;
        }


    }


    //Lagar Objekt och bestämmer vad som slår vad
    public enum RPS {

        ROCK, PAPER, SCISSORS;

        boolean beats(RPS other) {

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


    }
}