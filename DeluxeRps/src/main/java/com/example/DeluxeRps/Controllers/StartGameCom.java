package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class StartGameCom {

    RPS player1, player2;
    Random generator = new Random();

    public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
        Login.exitButtonClicked(mouseEvent);
    }


    public void covidButtonClicked (MouseEvent mouseEvent) throws IOException {
        player1 = RPS.ROCK;
        boolean winner = false;
       if(gameResult(winner)){
           Helper.replaceScene(Helper.covidWinnerFXML, Helper.covidWinnerTitle, mouseEvent);
       } else {
           Helper.replaceScene(Helper.paperWinnerFXML, Helper.paperWinnerTitle, mouseEvent);
       }
    }

    public void paperButtonClicked (MouseEvent mouseEvent) throws IOException {
        player1 = RPS.PAPER;
        boolean winner = false;

        if(gameResult(winner)){
            Helper.replaceScene(Helper.paperWinnerFXML, Helper.paperWinnerTitle, mouseEvent);
        } else {
            Helper.replaceScene(Helper.handWinnerFXML, Helper.handWinnerTitle, mouseEvent);
        }
    }

    public void handButtonClicked (MouseEvent mouseEvent) throws IOException {
        player1 = RPS.SCISSORS;
        boolean winner = false;

        if (gameResult(winner)){
            Helper.replaceScene(Helper.handWinnerFXML, Helper.handWinnerTitle, mouseEvent);
        } else {
            Helper.replaceScene(Helper.covidWinnerFXML, Helper.covidWinnerTitle, mouseEvent);
        }
    }

    //Koblas till vyer
    public boolean gameResult(boolean winner){

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

        return winner;

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