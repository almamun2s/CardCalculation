package com.example.cardcalculation.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "bids",
        foreignKeys = @ForeignKey(
        entity = Game.class,
        parentColumns = "id",
        childColumns = "gameId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("gameId")})
public class Bid {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "gameId")
    private int gameId;

    @ColumnInfo(name = "team1bid")
    private int team1bid;

    @ColumnInfo(name = "team2bid")
    private int team2bid;

    @ColumnInfo(name = "team1bidIsPositive", defaultValue = "0")
    private Boolean team1bidIsPositive = false;

    @ColumnInfo(name = "team2bidIsPositive", defaultValue = "0")
    private Boolean team2bidIsPositive = false;

    @ColumnInfo(name = "isResolved", defaultValue = "0")
    private Boolean isResolved = false;

    @ColumnInfo(name = "createdAt")
    private long createdAt;

    // Constructor
    public Bid() {
        this.createdAt = System.currentTimeMillis();
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getGameId() {return gameId;}
    public void setGameId(int gameId) {this.gameId = gameId;}

    public int getTeam1bid() {return team1bid;}
    public void setTeam1bid(int team1bid) {this.team1bid = team1bid;}

    public int getTeam2bid() {return team2bid;}
    public void setTeam2bid(int team2bid) {this.team2bid = team2bid;}

    public Boolean getResolved() {return isResolved;}
    public void setResolved(Boolean resolved) {isResolved = resolved;}

    public Boolean getTeam1bidIsPositive() {return team1bidIsPositive;}
    public void setTeam1bidIsPositive(Boolean team1bidIsPositive) {this.team1bidIsPositive = team1bidIsPositive;}

    public Boolean getTeam2bidIsPositive() {return team2bidIsPositive;}
    public void setTeam2bidIsPositive(Boolean team2bidIsPositive) {this.team2bidIsPositive = team2bidIsPositive;}

    public long getCreatedAt() {return createdAt;}
    public void setCreatedAt(long createdAt) {this.createdAt = createdAt;}
}
