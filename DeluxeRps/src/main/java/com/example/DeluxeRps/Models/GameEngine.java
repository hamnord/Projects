package com.example.DeluxeRps.Models;

import java.sql.SQLException;
import java.util.Random;

public class GameEngine {


  private Player player1, player2;
  private Match match;
  private Move player1Move, player2Move;
  private int player1Score, player2Score;
  public int  userId, move, matchId, moveId;
  static String userName;

  //players
  public static final int PLAYER1 = 1;
  public static final int PLAYER2 = 2;




  public GameEngine (Player player1, Player player2, Match match, Move player1Move, Move player2Move) throws SQLException {
    this.player1 = player1;
    this.player2 = player2;
    this.match = match;
    this.player1Move = player1Move;
    this.player2Move = player2Move;
    player1Score = 0;
    player2Score = 0;
  }


  public Player getPlayer1() throws SQLException {
    Player player1 = new Player(userName, userId);
    player1.getUserId(userName);
    player1.getUserName(userId);
    return player1;
  }

  public Player getPlayer2() throws SQLException {
    Player player2 = new Player(userName,userId);
    player2.getUserId(userName);
    player2.getUserName(userId);
    return player2;
  }

  public Match getMatch() throws SQLException {
    Match newMatch = new Match(matchId,userId);
    newMatch.getMatchId();
    newMatch.getUserId(userId);
    return newMatch;
  }

  public Move getPlayer2Move() throws SQLException {
    Move player2Move = new Move(moveId,move,userId);
    player2Move.getMoveId();
    player2Move.getMove(userId,move);
    return player2Move;
  }

  // behövs nog inte
  public Move getPlayer1Move() throws SQLException {
    Move player1Move = new Move(moveId,move,userId);
    player1Move.getMoveId();
    player1Move.getMove(userId,move);
    return player1Move;
  }

  public int getPlayer1Score() {
    return player1Score;
  }

  public int getPlayer2Score() {
    return player2Score;
  }
}
