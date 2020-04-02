package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Models.GameEngine;
import com.example.DeluxeRps.Models.Match;
import com.example.DeluxeRps.Models.Move;
import com.example.DeluxeRps.Models.Player;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class StartGamePlayer{


    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;

    Player player1, player2;
    Match newMatch;
    Move player1Move,player2Move;

    PreparedStatement getUserId, getMove, sendMove, getMatch;
    Connection con;
    String username = Login.username;
    String matchstatus;

    public static  final int DRAW = 0;
    public static  final int ROCK = 1;
    public static  final int SCISSORS = 2;
    public static  final int PAPER = 3;


    //FXML-OBJECTS

    // insert in inviteplayer ?
    public void startGameButtonClicked(MouseEvent mouseEvent ) throws SQLException, IOException {
        GameEngine game = new GameEngine(player1,player2, newMatch, player1Move,player2Move);
        Helper.replaceScene(Helper.startGamePlayerFXML, Helper.startGamePlayerTitle, mouseEvent);
    }


    public void RockButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        GameEngine game = new GameEngine(player1,player2, newMatch, player1Move,player2Move);
        con = ConDB.getConnection();
        con.setAutoCommit(false);

        Move.setMoveId(ROCK);
        player2Move.getMoveId();


        if () {
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

    public void PaperButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        con = ConDB.getConnection();
        con.setAutoCommit(false);

        Move.setMoveId(PAPER);

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

    public void ScissorButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
        con = ConDB.getConnection();
        con.setAutoCommit(false);

        Move.setMoveId(SCISSORS);

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

        boolean beats() {

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
