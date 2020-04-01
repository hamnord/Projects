package com.example.DeluxeRps.Models;

public class FriendList {

  private String friendName;
  private int friendId, userId;

  public FriendList (int userId, int friendId, String friendName) {
    this.userId = userId;
    this.friendId = friendId;
    this.friendName = friendName;
  }

  public String getFriendName() {
    return friendName;
  }

  public int getFriendId() {
    return friendId;
  }

  public int getUserId() {
    return userId;
  }

  public String toString (){
    return friendName + "(" + userId + ")";
  }
}

