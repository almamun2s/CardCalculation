package com.example.cardcalculation.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cardcalculation.data.dao.BidDao;
import com.example.cardcalculation.data.dao.GameDao;
import com.example.cardcalculation.data.model.Bid;
import com.example.cardcalculation.data.model.Game;

@Database(entities = {Game.class, Bid.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract GameDao gameDao();
    public abstract BidDao bidDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "game_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
