package com.example.cardcalculation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardcalculation.R;
import com.example.cardcalculation.adapter.GameAdapter;
import com.example.cardcalculation.data.model.Game;
import com.example.cardcalculation.view.GameViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.OnGameClickListener {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter(this);
        recyclerView.setAdapter(gameAdapter);

        // Observe LiveData - this automatically updates UI when data changes
        gameViewModel.getAllGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                gameAdapter.setGames(games);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(MainActivity.this, ShowGameActivity.class);
        intent.putExtra("game_id", game.getId());
        startActivity(intent);
    }
}