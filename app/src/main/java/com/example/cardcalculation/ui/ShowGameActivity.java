package com.example.cardcalculation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.cardcalculation.R;
import com.example.cardcalculation.view.GameViewModel;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AlertDialog;

public class ShowGameActivity extends AppCompatActivity {
    private TextView txtTeam1Points, txtTeam2Points, txtPlayer1, txtPlayer2, txtPlayer3, txtPlayer4;
    private int gameId = -1;
    private GameViewModel gameViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game);

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_game);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize view
        txtPlayer1 = findViewById(R.id.txtPlayer1);
        txtPlayer2 = findViewById(R.id.txtPlayer2);
        txtPlayer3 = findViewById(R.id.txtPlayer3);
        txtPlayer4 = findViewById(R.id.txtPlayer4);

        if (getIntent().hasExtra("game_id")) {
            gameId = getIntent().getIntExtra("game_id", -1);

            gameViewModel.getGameById(gameId).observe(this, game -> {
                if (game != null) {
                    txtPlayer1.setText(game.getPlayer1());
                    txtPlayer2.setText(game.getPlayer2());
                    txtPlayer3.setText(game.getPlayer3());
                    txtPlayer4.setText(game.getPlayer4());
                }
            });
        }else{
            finish();
        }

        MaterialButton btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ShowGameActivity.this, AddGameActivity.class);
            intent.putExtra("game_id", gameId);
            startActivity(intent);
        });

        MaterialButton btnDelete = findViewById(R.id.btn_delete);

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Game")
                    .setMessage("Are you sure you want to delete this game?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        gameViewModel.getGameById(gameId).observe(this, game -> {
                            if (game != null) {
                                gameViewModel.delete(game);
                                finish();
                            }
                        });
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

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