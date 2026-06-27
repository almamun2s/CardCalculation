package com.example.cardcalculation.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cardcalculation.data.AppDatabase;
import com.example.cardcalculation.data.dao.GameDao;
import com.example.cardcalculation.data.model.Game;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRepository {
    private GameDao gameDao;
    private LiveData<List<Game>> allGames;
    private ExecutorService executorService;

    public GameRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        gameDao = database.gameDao();
        allGames = gameDao.getAllGames();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Game>> getAllGames(){ return allGames;}
    public LiveData<Game> getGameById(int gameId){ return gameDao.getGameById(gameId);}

    public void insert(Game game){
        executorService.execute(() -> gameDao.insert(game));
    }

    public void update(Game game){
        executorService.execute(() -> gameDao.update(game));
    }

    public void delete(Game game){
        executorService.execute(() -> gameDao.delete(game));
    }
}
