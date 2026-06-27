package com.example.cardcalculation.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cardcalculation.data.model.Game;
import com.example.cardcalculation.repository.GameRepository;

import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private GameRepository repository;
    private LiveData<List<Game>> allGames;

    public GameViewModel(Application application) {
        super(application);
        repository = new GameRepository(application);
        allGames = repository.getAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return allGames;
    }

    public LiveData<Game> getGameById(int gameId) {
        return repository.getGameById(gameId);
    }

    public void insert(Game game) {
        repository.insert(game);
    }

    public void update(Game game) {
        repository.update(game);
    }

    public void delete(Game game) {
        repository.delete(game);
    }
}
