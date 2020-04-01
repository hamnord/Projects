package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class StartGamePlayer{


    StartGamePlayer.RPS player1, player2;
    PreparedStatement getUserId, getMove, sendMove, getMatch;
    Connection con;
    String username = Login.username;
    String matchstatus;


    //FXML-OBJECTS




    public void RockButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        con = ConDB.getConnection();

        ResultSet match = getMatchDetails(getUserId(username));
        int idplayer1 = match.getInt("useridplayer1");
        int idmatch = match.getInt("matchid");


        player1 = StartGamePlayer.RPS.ROCK;
        sendMove(idplayer1, idmatch, 1);

        gameResult();
    }

    public void PaperButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        con = ConDB.getConnection();

        ResultSet match = getMatchDetails(getUserId(username));
        int idplayer1 = match.getInt("useridplayer1");
        int idmatch = match.getInt("matchid");

        player1 = StartGamePlayer.RPS.PAPER;
        sendMove(idplayer1, idmatch, 2);

        gameResult();
    }

    public void ScissorButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        con = ConDB.getConnection();

        ResultSet match = getMatchDetails(getUserId(username));
        int idplayer1 = match.getInt("useridplayer1");
        int idmatch = match.getInt("matchid");


        player1 = StartGamePlayer.RPS.SCISSORS;
        sendMove(idplayer1, idmatch, 3);

        gameResult();
    }

    public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
        Login.exitButtonClicked(mouseEvent);
    }





    //Koblas till vyer
    private void gameResult() throws SQLException {

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



    }




    //player2-choices if computer
    private void getPlayer2() throws SQLException {

        ResultSet match = getMatchDetails(getUserId(username));
        int idplayer2 = match.getInt("useridplayer2");
        int idmatch = match.getInt("matchid");


        int playerMove = getMove(idplayer2, idmatch);


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

    private int getUserId(String username) throws SQLException{

       getUserId = con.prepareStatement("SELECT * FROM gamedb.users WHERE username = ?");
       getUserId.setString(1, username);
       ResultSet validUser = getUserId.executeQuery();
       int userid = validUser.getInt("userid");
       return userid;

    }

    private int getMove(int userid, int matchid) throws SQLException{

        getMove = con.prepareStatement("SELECT * FROM gamedb.match WHERE userid = ? AND matchid = ?");
        getMove.setInt(1, userid);
        getMove.setInt(2, matchid);
        ResultSet movRs = getMove.executeQuery();
        int player2Move = movRs.getInt("move");
        return player2Move;

    }

    private void sendMove(int userid, int matchid, int move) throws SQLException {

        sendMove = con.prepareStatement("INSERT INTO gamedb.match VALUES (?, ?, ?)");
        sendMove.setInt(1, userid);
        sendMove.setInt(2, matchid);
        sendMove.setInt(3, move);
        sendMove.executeUpdate();
        con.commit();

    }

    private ResultSet getMatchDetails(int userid) throws SQLException{

        matchstatus = "ONGOING";
        getMatch = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE userid = ? AND matchstatus = ?");
        getMatch.setInt(1, userid);
        getMatch.setString(2, matchstatus);
        ResultSet a = getMatch.executeQuery();
        con.commit();

        return a;

    }



}
