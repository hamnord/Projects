package com.example.DeluxeRps.Controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class StartGamePlayer extends GenericController{


    PreparedStatement opponentStmt, sendMoveStmt, getMatchStmt, getUserIDStmt, endMatchStmt;
    Connection con;
    String matchstatus;
    private String username = Login.username;
    private int userIDPlayer1;
    int player2Move;
    private int player2Id;
    public int matchId;
    boolean hasplayermoved;


    public static  final int ROCK = 1;
    public static  final int SCISSORS = 2;
    public static  final int PAPER = 3;

    public void RockButtonClicked (MouseEvent mouseEvent) throws SQLException {

        con = ConDB.getConnection();
        con.setAutoCommit(false);
        userIDPlayer1 = getUserId(username);
        player2Id = getOpponentId(userIDPlayer1);
        matchId = getMatchId(userIDPlayer1);
        sendMove(userIDPlayer1, matchId, ROCK);
        hasplayermoved = false;

        while (!hasplayermoved) {
            try {
                getMove(player2Id, matchId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (getMove(player2Id, matchId) != 0) {
            try {

                player2Move = getMove(player2Id, matchId);
                System.out.println(player2Move);

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

                endMatch(matchId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void PaperButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException, InterruptedException {

        con = ConDB.getConnection();
        con.setAutoCommit(false);
        userIDPlayer1 = getUserId(username);
        player2Id = getOpponentId(userIDPlayer1);
        matchId = getMatchId(userIDPlayer1);
        sendMove(userIDPlayer1, matchId, PAPER);
        hasplayermoved = false;

        while (!hasplayermoved) {
            try {
                getMove(player2Id, matchId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (getMove(player2Id, matchId) != 0) {
            try {

                player2Move = getMove(player2Id, matchId);
                System.out.println(player2Move);

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

                endMatch(matchId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void ScissorButtonClicked (MouseEvent mouseEvent) throws IOException, SQLException, InterruptedException {

        con = ConDB.getConnection();
        con.setAutoCommit(false);
        userIDPlayer1 = getUserId(username);
        player2Id = getOpponentId(userIDPlayer1);
        matchId = getMatchId(userIDPlayer1);
        sendMove(userIDPlayer1, matchId, SCISSORS);
        hasplayermoved = false;

        while (!hasplayermoved) {
            try {
                getMove(player2Id, matchId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (getMove(player2Id, matchId) != 0) {
            try {

                player2Move = getMove(player2Id, matchId);
                System.out.println(player2Move);


                if (player2Move == PAPER) {
                    System.out.println("You win");
                    Helper.replaceScene(Helper.handWinnerFXML, Helper.handWinnerTitle, mouseEvent);
                } else if (player2Move == ROCK) {
                    System.out.println("You Loose");
                    Helper.replaceScene(Helper.handLoserFXML, Helper.handLoserTitle, mouseEvent);
                } else if (player2Move == SCISSORS) {
                    System.out.println("TIE");
                    Helper.replaceScene(Helper.handTIEFXML, Helper.handTIETitle, mouseEvent);
                }

                endMatch(matchId);

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

  private int getOpponentId (int playerid) throws SQLException {
      matchstatus = "ONGOING";
      opponentStmt = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer2 = ? or useridplayer1 = ? and matchstatus = ?");
      opponentStmt.setInt(1, playerid);
      opponentStmt.setInt(2, playerid);
      opponentStmt.setString(3, matchstatus);
      opponentStmt.executeQuery();
      ResultSet a = opponentStmt.executeQuery();
      con.commit();

      if(a.next()){
          int player1 = a.getInt("useridplayer1");
          int player2 = a.getInt("useridplayer2");

          if(playerid == player1){
              return player2;
          }

          if(playerid == player2){
              return player1;
          }


      }

      return 0;

  }

  private int getMatchId (int playerid) throws SQLException {
    matchstatus = "ONGOING";
    getMatchStmt = con.prepareStatement("SELECT * FROM gamedb.newgame WHERE useridplayer1 = ? or useridplayer2 = ? and matchstatus = ?");
    getMatchStmt.setInt(1, playerid);
    getMatchStmt.setInt(2, playerid);
    getMatchStmt.setString(3, matchstatus);
    ResultSet a  = getMatchStmt.executeQuery();
    con.commit();

    if(a.next()){
        return a.getInt("matchid");
    }

    return 0;
  }

  private int getMove(int userId, int matchid) throws SQLException{

    getUserIDStmt = con.prepareStatement("SELECT * FROM gamedb.match where userid  = ? and matchid = ?");
    getUserIDStmt.setInt(1, userId);
    getUserIDStmt.setInt(2, matchid);
    ResultSet rs = getUserIDStmt.executeQuery();

    if(rs.next()) {

        hasplayermoved = true;
        return rs.getInt("move");

    }

    return 0;

  }


  private void sendMove(int userid, int matchid, int move) throws SQLException {

        sendMoveStmt = con.prepareStatement("INSERT INTO gamedb.match VALUES (?, ?, ?)");
        sendMoveStmt.setInt(1, userid);
        sendMoveStmt.setInt(2, matchid);
        sendMoveStmt.setInt(3, move);
        sendMoveStmt.executeUpdate();
        con.commit();

    }


    private void endMatch(int matchId) throws SQLException{

        matchstatus = "ENDED";
        endMatchStmt = con.prepareStatement("UPDATE gamedb.newgame SET matchstatus = ? WHERE matchid = ?");
        endMatchStmt.setString(1, matchstatus);
        endMatchStmt.setInt(2, matchId);
        endMatchStmt.executeUpdate();
        con.commit();


    }

}
