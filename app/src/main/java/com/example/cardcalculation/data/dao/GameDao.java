package com.example.cardcalculation.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cardcalculation.data.model.Game;

import java.util.List;

@Dao
public interface GameDao {
    @Insert
    void insert(Game game);

    @Update
    void update(Game game);

    @Delete
    void delete(Game game);

    // Return LiveData for observing data changes
    @Query("SELECT * FROM games ORDER BY createdAt DESC")
    LiveData<List<Game>> getAllGames();

    @Query("SELECT * FROM games WHERE id = :gameId")
    LiveData<Game> getGameById(int gameId);
}
