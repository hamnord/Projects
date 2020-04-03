package com.example.DeluxeRps.Controllers;

import com.example.DeluxeRps.Models.Match;
import com.example.DeluxeRps.Models.Move;
import com.example.DeluxeRps.Models.Player;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class StartGamePlayer extends GenericController{

    Player player1, player2;
    Match newMatch;
  //  Move player1Move,player2Move;

    public int moveId, move, userId;

    PreparedStatement getUserId, opponentSTMNT, getMove,getMatch, sendMove, getMatchSTMNT,getUserIDStmt, gameStmt;
    Connection con;
    String matchstatus;
    private String username = Login.username;
    private int userIDPlayer1;
    int player2Move;
    private int player2Id;
    public int matchId;


    public static  final int DRAW = 0;
    public static  final int ROCK = 1;
    public static  final int SCISSORS = 2;
    public static  final int PAPER = 3;

    public void RockButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
      con = ConDB.getConnection();
      con.setAutoCommit(false);

        try {
          userIDPlayer1 = getUserId(username);
          Move rockMove = new Move(ROCK, userIDPlayer1);
          rockMove.setMoveId(ROCK);
          rockMove.sendMove(userIDPlayer1, ROCK);
          System.out.println("ROCK SELECTED");

          player2Id = getOpponentId();

          Move opponentMove = new Move(player2Move, player2Id);
          player2Move = opponentMove.getMove(player2Id);


          if (player2Move == SCISSORS) {
            System.out.println("You win");
            Helper.replaceScene(Helper.covidWinnerFXML, Helper.covidWinnerTitle, mouseEvent);
          } else if (player2Move == PAPER) {
            System.out.println("You Loose");
            Helper.replaceScene(Helper.covidLoserFXML, Helper.covidLoserTitle, mouseEvent);
          } else if (player2Move == ROCK){
            System.out.println("TIE");
            Helper.replaceScene(Helper.covidTIEFXML, Helper.covidTIETitle, mouseEvent);
          }
      }catch (Exception e){
          e.printStackTrace();
        }
      }


    public void PaperButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

      con = ConDB.getConnection();
      con.setAutoCommit(false);
      ResultSet activeMatch = getMatch();

      while (activeMatch.next()) {


        try {
          userIDPlayer1 = getUserId(username);
          Move paperMove = new Move(PAPER, userIDPlayer1);
          paperMove.setMoveId(PAPER);
          paperMove.sendMove(userIDPlayer1, ROCK);


          if (player2Move == ROCK) {
            System.out.println("You win");
            Helper.replaceScene(Helper.paperWinnerFXML, Helper.paperWinnerTitle, mouseEvent);
          } else if (player2Move == SCISSORS) {
            System.out.println("You Loose");
            Helper.replaceScene(Helper.paperLoserFXML, Helper.paperLoserTitle, mouseEvent);
          } else if (player2Move == PAPER) {
            System.out.println("TIE");
            Helper.replaceScene(Helper.paperTIEFXML, Helper.paperTIETitle, mouseEvent);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    public void ScissorButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

      con = ConDB.getConnection();
      con.setAutoCommit(false);

      ResultSet activeMatch = getMatch();

      while (activeMatch.next()) {


        try {
          userIDPlayer1 = getUserId(username);
          Move scissorMove = new Move(SCISSORS, userIDPlayer1);
          scissorMove.setMoveId(SCISSORS);
          scissorMove.sendMove(userIDPlayer1, SCISSORS);

          if (player2Move == PAPER) {
            System.out.println("You win");
            Helper.replaceScene(Helper.paperWinnerFXML, Helper.paperWinnerTitle, mouseEvent);
          } else if (player2Move == ROCK) {
            System.out.println("You Loose");
            Helper.replaceScene(Helper.paperLoserFXML, Helper.paperLoserTitle, mouseEvent);
          } else if (player2Move == SCISSORS) {
            System.out.println("TIE");
            Helper.replaceScene(Helper.paperTIEFXML, Helper.paperTIETitle, mouseEvent);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    public void exitButtonClicked(MouseEvent mouseEvent) throws SQLException {
        Login.exitButtonClicked(mouseEvent);
    }


    //PREPARED STATEMENTS

  private int getUserId(String username) throws SQLException{

    getUserIDStmt = con.prepareStatement("SELECT * FROM gamedb.users where username = ?");
    getUserIDStmt.setString(1, username);
    ResultSet checkUser = getUserIDStmt.executeQuery();

    if(checkUser.next()) {

      userIDPlayer1 = checkUser.getInt("userid");

      return userIDPlayer1;
    }
    return 0;
  }

  private int getOpponentId () throws SQLException {
      opponentSTMNT = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer2 = ?");
      opponentSTMNT.setInt(1, player2Id);
      opponentSTMNT.executeQuery();
      con.commit();

      return player2Id;
  }

  private ResultSet getMatch () throws SQLException {
      getMatchSTMNT = con.prepareStatement("SELECT * FROM gamedb.match WHERE matchid = ? ");
      getMatchSTMNT.setInt(1, matchId);
      ResultSet rs = getMatchSTMNT.executeQuery();
      con.commit();
      return rs;
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

    private ResultSet getMatch(int matchid) throws SQLException{

        matchstatus = "ONGOING";
        getMatch = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE matchid = ?");
        getMatch.setInt(1, matchid);
        ResultSet a = getMatch.executeQuery();
        con.commit();

        return a;

    }
/*
    private int getOpponent() throws SQLException {

        gameStmt = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer2 = ?");
        gameStmt.setInt(1, userId);
        gameStmt.executeQuery();
        return userId;

    }

  public void getOpponentMove() throws SQLException, IOException {
    con = ConDB.getConnection();
    con.setAutoCommit(false);

    int userIDPlayer2 = getOpponent();

    ResultSet activeMatch = getMatch();

    while (activeMatch.next()) {

      try {
        Move opponentMove = new Move(player2Move, userIDPlayer2);

        switch (player2Move) {
          case ROCK:
            opponentMove.sendMove(userIDPlayer2, ROCK);
            break;
          case PAPER:
            opponentMove.sendMove(userIDPlayer2, PAPER);
            break;
          case SCISSORS:
            opponentMove.sendMove(userIDPlayer2, SCISSORS);
            break;

          default:
            throw new IllegalStateException();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
*/

}
