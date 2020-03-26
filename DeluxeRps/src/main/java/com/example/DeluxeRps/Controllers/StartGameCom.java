package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Random;

public class StartGameCom {

    RPS player1, player2;
    Random generator = new Random();


    public void RockButtonClicked (MouseEvent mouseEvent) throws IOException {
        player1 = RPS.ROCK;
        gameResult();
    }

    public void PaperButtonClicked (MouseEvent mouseEvent) throws IOException {
        player1 = RPS.PAPER;
        gameResult();
    }

    public void ScissorButtonClicked (MouseEvent mouseEvent) throws IOException {
        player1 = RPS.SCISSORS;
        gameResult();
    }



    //Koblas till vyer
    public void gameResult(){

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