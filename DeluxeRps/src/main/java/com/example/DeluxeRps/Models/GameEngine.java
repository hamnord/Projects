package com.example.DeluxeRps.Models;

import java.sql.SQLException;
import java.util.Random;

public class GameEngine {


  private Player player1, player2;
  private Match match;
  private Move player1Move, player2Move;
  private int player1Score, player2Score, userId, move;

  //moves
  public static  final int DRAW = 0;
  public static  final int ROCK = 1;
  public static  final int SCISSORS = 2;
  public static  final int PAPER = 3;

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


  public Player getPlayer1() {
    return player1;
  }

  public Player getPlayer2() {
    return player2;
  }

  public Match getMatch() {
    return match;
  }

  public Move getPlayer1Move() {
    return player1Move;
  }

  public Move getPlayer2Move() {
    return player2Move;
  }

  public int getPlayer1Score() {
    return player1Score;
  }

  public int getPlayer2Score() {
    return player2Score;
  }
}
