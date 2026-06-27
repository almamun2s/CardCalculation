package com.example.cardcalculation.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cardcalculation.R;
import com.example.cardcalculation.data.model.Game;
import com.example.cardcalculation.view.GameViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class AddGameActivity extends AppCompatActivity {
    private EditText etPlayer1, etPlayer2, etPlayer3, etPlayer4;
    private Button btnSave;
    private SwitchMaterial switchTeam;
    private Boolean isTeam;

    private GameViewModel gameViewModel;
    private int gameId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_add_game);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        etPlayer1 = findViewById(R.id.et_player1);
        etPlayer2 = findViewById(R.id.et_player2);
        etPlayer3 = findViewById(R.id.et_player3);
        etPlayer4 = findViewById(R.id.et_player4);
        switchTeam = findViewById(R.id.is_team);
        isTeam = switchTeam.isChecked();
        btnSave = findViewById(R.id.btn_save);

        // Check if editing existing note
        if (getIntent().hasExtra("game_id")) {
            gameId = getIntent().getIntExtra("game_id", -1);
            getSupportActionBar().setTitle("Edit Game");
            btnSave.setText("Update Game");

            // Load note data using LiveData
            gameViewModel.getAllGames().observe(this, new Observer<List<Game>>() {
                @Override
                public void onChanged(List<Game> games) {
                    if (games != null) {
                        for (Game game : games) {
                            if (game.getId() == gameId) {
                                etPlayer1.setText(game.getPlayer1());
                                etPlayer2.setText(game.getPlayer2());
                                etPlayer3.setText(game.getPlayer3());
                                etPlayer4.setText(game.getPlayer4());
                                isTeam = game.getTeam();
                                break;
                            }
                        }
                    }
                    // Remove observer after loading once
                    gameViewModel.getAllGames().removeObserver(this);
                }
            });
        } else {
            getSupportActionBar().setTitle("Add Game");
            btnSave.setText("Save Game");
        }

        btnSave.setOnClickListener(v -> saveGame());
    }

    private void saveGame() {
        String player1 = etPlayer1.getText().toString().trim();
        String player2 = etPlayer2.getText().toString().trim();
        String player3 = etPlayer3.getText().toString().trim();
        String player4 = etPlayer4.getText().toString().trim();

        if (player1.isEmpty() || player2.isEmpty() || player3.isEmpty() || player4.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        if (gameId == -1) {
            // Create new game
            Game game = new Game();
            game.setPlayer1(player1);
            game.setPlayer2(player2);
            game.setPlayer3(player3);
            game.setPlayer4(player4);
            game.setTeam(isTeam);
            gameViewModel.insert(game);
            Toast.makeText(this, "Game saved", Toast.LENGTH_SHORT).show();
        } else {
            LiveData gameLiveData = gameViewModel.getGameById(gameId);
            gameLiveData.observe(this, new Observer<Game>() {
                @Override
                public void onChanged(Game game) {
                    game.setPlayer1(player1);
                    game.setPlayer2(player2);
                    game.setPlayer3(player3);
                    game.setPlayer4(player4);
                    game.setTeam(isTeam);
                    gameViewModel.update(game);
                    gameLiveData.removeObserver(this);
                }
            });
            Toast.makeText(this, "Game updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}