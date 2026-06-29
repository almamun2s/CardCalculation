package com.example.cardcalculation.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.example.cardcalculation.data.model.Bid;
import com.example.cardcalculation.data.model.Game;
import com.example.cardcalculation.view.BidViewModel;
import com.example.cardcalculation.view.GameViewModel;
import com.google.android.material.button.MaterialButton;

public class ResolveBidActivity extends AppCompatActivity {

    EditText etTeam1bid, etTeam2bid;
    TextView team1info, team2info;
    Integer bidId, gameId, team1Point, team2Point;
    MaterialButton btnSave;
    private BidViewModel bidViewModel;
    private GameViewModel gameViewModel;
    private Bid currentBid;
    private Game currentGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_bid);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_resolve_bid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize view
        etTeam1bid = findViewById(R.id.et_team1bid);
        etTeam2bid = findViewById(R.id.et_team2bid);
        team1info = findViewById(R.id.team1info);
        team2info = findViewById(R.id.team2info);
        btnSave = findViewById(R.id.btn_save);

        if (getIntent().hasExtra("bid_id")) {
            bidId = getIntent().getIntExtra("bid_id", -1);
            bidViewModel = new ViewModelProvider(this).get(BidViewModel.class);

            bidViewModel.getBidById(bidId).observe(this, bid -> {
                if (bid != null) {
                    currentBid = bid;
                    gameId = bid.getGameId();
                    etTeam1bid.setText(String.valueOf(bid.getTeam1bid()));
                    etTeam2bid.setText(String.valueOf(bid.getTeam2bid()));
                    team1Point = bid.getTeam1bid();
                    team2Point = bid.getTeam2bid();

                    gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
                    gameViewModel.getGameById(gameId).observe(this, game -> {
                        if (game != null) {
                            currentGame = game;
                            team1info.setText("Bid of " + game.getPlayer1() + " and " + game.getPlayer2());
                            team2info.setText("Bid of " + game.getPlayer3() + " and " + game.getPlayer4());
                        }
                    });
                }else{
                    finish();
                }
            });

            btnSave.setOnClickListener(v -> resolveBid());
        }else{
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resolveBid() {
        RadioGroup radioGroupT1 = findViewById(R.id.rg_result_team1);
        RadioGroup radioGroupT2 = findViewById(R.id.rg_result_team2);

        int selectedIdT1 = radioGroupT1.getCheckedRadioButtonId();
        int selectedIdT2 = radioGroupT2.getCheckedRadioButtonId();

        boolean team1isPositive = selectedIdT1 == R.id.rb_success_team1;
        boolean team2isPositive = selectedIdT2 == R.id.rb_success_team2;

        currentBid.setResolved(true);
        if (team1isPositive) {
            currentGame.setTeam1points(currentGame.getTeam1points() + team1Point);
        } else {
            currentGame.setTeam1points(currentGame.getTeam1points() - team1Point);
        }
        if (team2isPositive){
            currentGame.setTeam2points(currentGame.getTeam2points() + team2Point);
        }else{
            currentGame.setTeam2points(currentGame.getTeam2points() - team2Point);
        }

        gameViewModel.update(currentGame);
        bidViewModel.update(currentBid);

        Toast.makeText(this, "Team 1 Point: " + team1Point + "\nTeam 2 Point: " + team2Point, Toast.LENGTH_SHORT).show();
        finish();
    }
}