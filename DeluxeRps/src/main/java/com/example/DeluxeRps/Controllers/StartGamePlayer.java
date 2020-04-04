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

    PreparedStatement getUserId, opponentSTMNT, getMove,getMatch, sendMove, getMatchSTMNT,getUserIDStmt, setopponentMatchIdSTMNT;
    Connection con;
    String matchstatus;
    private String username = Login.username;
    private int userIDPlayer1;
    private int userIDPlayer2;
    int player2Move;
    private int player2Id;
    public int matchId, opponentMatchId;


    public static  final int DRAW = 0;
    public static  final int ROCK = 1;
    public static  final int SCISSORS = 2;
    public static  final int PAPER = 3;

    public void RockButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {
      con = ConDB.getConnection();
      con.setAutoCommit(false);
      userIDPlayer1 = getUserId(username);
      matchId = getMatchId();

        try {

          getOpponentId(player2Id);
          getMove(player2Id);

          sendMove(userIDPlayer1, matchId, ROCK);
          System.out.println("ROCK SELECTED");

            System.out.println("set körd");
           // getMove(player2Id);


            if (player2Move == SCISSORS) {
              System.out.println("You win");
              Helper.replaceScene(Helper.covidWinnerFXML, Helper.covidWinnerTitle, mouseEvent);
            } else if (player2Move == PAPER) {
              System.out.println("You Loose");
              Helper.replaceScene(Helper.covidLoserFXML, Helper.covidLoserTitle, mouseEvent);
            } else if (player2Move == ROCK) {
              System.out.println("TIE");
              Helper.replaceScene(Helper.covidTIEFXML, Helper.covidTIETitle, mouseEvent);
            }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }




    public void PaperButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

      con = ConDB.getConnection();
      con.setAutoCommit(false);
      userIDPlayer1 = getUserId(username);
      matchId = getMatchId();

        try {

          getMove(player2Id);

            sendMove(userIDPlayer1, matchId, PAPER);
            System.out.println("PAPER SELECTED");

          System.out.println("set körd");
          // getMove(player2Id);

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



    public void ScissorButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException {

      con = ConDB.getConnection();
      con.setAutoCommit(false);
      userIDPlayer1 = getUserId(username);
      matchId = getMatchId();
        try {

            sendMove(userIDPlayer1, matchId, SCISSORS);
            System.out.println("SCISSORS SELECTED");

          getOpponentId(player2Id);
          getMove(player2Id);
          System.out.println("set körd");
          // getMove(player2Id);

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

  private int getOpponentId (int userId) throws SQLException {
      opponentSTMNT = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer2 = ?");
      opponentSTMNT.setInt(1, userId);
      opponentSTMNT.executeQuery();
      con.commit();

      return userId;
  }

  private int getMatchId () throws SQLException {
    getMatchSTMNT = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer1 = ? ");
    getMatchSTMNT.setInt(1, userIDPlayer1);
    ResultSet rs = getMatchSTMNT.executeQuery();
    con.commit();

    if (rs.next()) {
      matchId = rs.getInt("matchid");
      return matchId;
    } else {
      System.out.println("couldn't fetch matchid");
      return -1;
    }
  }

  private int getMove(int userId) throws SQLException{

        getMove = con.prepareStatement("SELECT * FROM gamedb.match WHERE useridplayer2 =?");
        getMove.setInt(1, userId);
        ResultSet movRs = getMove.executeQuery();
        con.commit();

        if (movRs.next()) {
          player2Move = movRs.getInt("move");
          return player2Move;
        }
    System.out.println("bruh, couldn't fetch opponent move");
        return -1;
    }


    private void sendMove(int userid, int matchid, int move) throws SQLException {

        sendMove = con.prepareStatement("INSERT INTO gamedb.match VALUES (?, ?, ?)");
        sendMove.setInt(1, userid);
        sendMove.setInt(2, matchid);
        sendMove.setInt(3, move);
        sendMove.executeUpdate();
        con.commit();

    }

    private int getMatch(int matchid,int userId) throws SQLException{

        matchstatus = "ONGOING";
        getMatch = con.prepareStatement("SELECT matchid FROM gamedb.newgame WHERE useridplayer1 = ? or useridplayer2 =? ");
        getMatch.setInt(1, matchid);
      getMatch.setInt(2, userId);
        getMatch.executeQuery();
        con.commit();

        return matchid;

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
