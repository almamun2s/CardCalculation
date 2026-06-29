package com.example.cardcalculation.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.cardcalculation.R;
import com.example.cardcalculation.data.model.Bid;
import com.example.cardcalculation.data.model.Game;
import com.example.cardcalculation.view.BidViewModel;
import com.example.cardcalculation.view.GameViewModel;
import com.google.android.material.button.MaterialButton;

public class AddBidActivity extends AppCompatActivity {

    EditText etTeam1bid, etTeam2bid;
    TextView team1info, team2info;
    Integer gameId;
    MaterialButton btnSave;
    private BidViewModel bidViewModel;
    private GameViewModel gameViewModel;
    Integer bidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_add_game);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize view
        etTeam1bid = findViewById(R.id.et_team1bid);
        etTeam2bid = findViewById(R.id.et_team2bid);
        team1info = findViewById(R.id.team1info);
        team2info = findViewById(R.id.team2info);
        btnSave = findViewById(R.id.btn_save);


        if (getIntent().hasExtra("game_id")) {
            gameId = getIntent().getIntExtra("game_id", -1);

            gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

            gameViewModel.getGameById(gameId).observe(this, game -> {
                if (game != null) {
                    team1info.setText("Bid of " + game.getPlayer1() + " and " + game.getPlayer2());
                    team2info.setText("Bid of " + game.getPlayer3() + " and " + game.getPlayer4());
                }
            });

            bidViewModel = new ViewModelProvider(this).get(BidViewModel.class);
            btnSave.setOnClickListener(v -> saveBid());
        } else if (getIntent().hasExtra("bid_id")) {
            bidId = getIntent().getIntExtra("bid_id", -1);
            bidViewModel = new ViewModelProvider(this).get(BidViewModel.class);

            bidViewModel.getBidById(bidId).observe(this, bid -> {
                if (bid != null) {
                    gameId = bid.getGameId();
                    etTeam1bid.setText(String.valueOf(bid.getTeam1bid()));
                    etTeam2bid.setText(String.valueOf(bid.getTeam2bid()));

                    gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
                    gameViewModel.getGameById(gameId).observe(this, game -> {
                        if (game != null) {
                            team1info.setText("Bid of " + game.getPlayer1() + " and " + game.getPlayer2());
                            team2info.setText("Bid of " + game.getPlayer3() + " and " + game.getPlayer4());
                        }
                    });
                }
            });

        }else{
            finish();
        }
    }

    private void saveBid() {
        String team1bid = etTeam1bid.getText().toString().trim();
        String team2bid = etTeam2bid.getText().toString().trim();

        if (team1bid.isEmpty() || team2bid.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Bid bid = new Bid();
        bid.setTeam1bid(Integer.parseInt(team1bid));
        bid.setTeam2bid(Integer.parseInt(team2bid));
        bid.setGameId(gameId);
        bid.setResolved(false);
        bidViewModel.insert(bid);
        Toast.makeText(this, "Bid saved", Toast.LENGTH_SHORT).show();
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