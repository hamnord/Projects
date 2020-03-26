package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class StartGamePlayer{


    StartGamePlayer.RPS player1, player2;
    PreparedStatement getUserId, getMove, sendMove;
    Connection con;
    String username = Login.username;





    //BUTTONS NOT MADE IN FXML YET
    public void RockButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        con = ConDB.getConnection();
        player1 = StartGamePlayer.RPS.ROCK;
        sendMove(1);
        gameResult();
    }

    public void PaperButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        con = ConDB.getConnection();
        player1 = StartGamePlayer.RPS.PAPER;
        sendMove(2);
        gameResult();
    }

    public void ScissorButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        con = ConDB.getConnection();
        player1 = StartGamePlayer.RPS.SCISSORS;
        sendMove(3);
        gameResult();
    }



    //Koblas till vyer
    public void gameResult() throws SQLException {

        getPlayer2();

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
    public void getPlayer2() throws SQLException {

        int playerMove = getMove(getUserId(username));


        if (playerMove == 1){
            player2 = StartGamePlayer.RPS.ROCK;
        }

        else if (playerMove == 2){
            player2 = StartGamePlayer.RPS.PAPER;
        }

        else if (playerMove == 3){
            player2 = StartGamePlayer.RPS.SCISSORS;
        }


    }


    //Lagar Objekt och bestämmer vad som slår vad
    public enum RPS {

        ROCK, PAPER, SCISSORS;

        boolean beats(StartGamePlayer.RPS other) {

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


    //PREPARED STATEMENTS

    //THESE ARE NOT DONE WHAT SO EVER, NEED TO ADJUST DB AND STUFF

    public int getUserId(String username) throws SQLException{

       getUserId = con.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
       getUserId.setString(1, username);
       ResultSet validUser = getUserId.executeQuery();
       int userid = validUser.getInt("userid");
       return userid;

    }

    public int getMove(int userid) throws SQLException{

        getMove = con.prepareStatement("SELECT * FROM gamedb(speltabell) WHERE userid = ?");
        getMove.setInt(1, userid);
        ResultSet movRs = getMove.executeQuery();
        int player2Move = movRs.getInt("Kollonne for player2 moves");
        return player2Move;

    }

    public void sendMove(int move) throws SQLException {

        sendMove = con.prepareStatement("INSERT INTO gamedb(speltabell) VALUES (?)");
        sendMove.setInt(1, move);
        sendMove.executeUpdate();
        con.commit();

    }



}
