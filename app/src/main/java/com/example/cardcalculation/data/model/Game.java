package com.example.cardcalculation.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "games")
public class Game {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "player1")
    private String player1;

    @ColumnInfo(name = "player2")
    private String player2;

    @ColumnInfo(name = "player3")
    private String player3;

    @ColumnInfo(name = "player4")
    private String player4;

    @ColumnInfo(name = "isTeam")
    private Boolean isTeam;

    @ColumnInfo(name = "team1points", defaultValue = "0")
    private int team1points = 0;

    @ColumnInfo(name = "team2points", defaultValue = "0")
    private int team2points = 0;

    @ColumnInfo(name = "createdAt")
    private long createdAt;

    // Constructor
    public Game() {
        this.createdAt = System.currentTimeMillis();
    }


    public int getId() { return id;}
    public void setId(int id) {this.id = id;}

    public String getPlayer1() {return player1;}
    public void setPlayer1(String player1) {this.player1 = player1;}

    public String getPlayer2() {return player2;}
    public void setPlayer2(String player2) {this.player2 = player2;}

    public String getPlayer3() {return player3;}
    public void setPlayer3(String player3) {this.player3 = player3;}

    public String getPlayer4() {return player4;}
    public void setPlayer4(String player4) {this.player4 = player4;}

    public Boolean getTeam() {return isTeam;}

    public void setTeam(Boolean team) {isTeam = team;}

    public int getTeam1points() {return team1points;}
    public void setTeam1points(int team1points) {this.team1points = team1points;}

    public int getTeam2points() {return team2points;}
    public void setTeam2points(int team2points) {this.team2points = team2points;}

    public long getCreatedAt() {return createdAt;}
    public void setCreatedAt(long createdAt) {this.createdAt = createdAt;}
}
