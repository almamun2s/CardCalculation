package com.example.cardcalculation.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cardcalculation.data.model.Bid;

import java.util.List;

@Dao
public interface BidDao {
    @Insert
    void insert(Bid bid);

    @Update
    void update(Bid bid);

    @Delete
    void delete(Bid bid);

    @Query("SELECT * FROM bids WHERE gameId = :gameId ORDER BY createdAt DESC")
    LiveData<List<Bid>> getAllBids(int gameId);

    @Query("SELECT * FROM bids WHERE id = :bidId")
    LiveData<Bid> getBidById(int bidId);
}
