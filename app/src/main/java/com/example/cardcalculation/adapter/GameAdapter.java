package com.example.cardcalculation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardcalculation.R;
import com.example.cardcalculation.data.model.Game;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder>{
    private List<Game> games;
    private OnGameClickListener listener;
    public interface OnGameClickListener {
        void onGameClick(Game game);
//        void onNoteLongClick(Game game);
    }

    public GameAdapter(OnGameClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = games.get(position);

        String gameTeamText = String.format(
                "%s + %s  vs  %s + %s",
                game.getPlayer1(),
                game.getPlayer2(),
                game.getPlayer3(),
                game.getPlayer4()
        );
        holder.gameTeam.setText(gameTeamText);

        // Format timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String date = sdf.format(new Date(game.getCreatedAt()));
        holder.gameDate.setText(date);

        holder.itemView.setOnClickListener(v -> listener.onGameClick(game));
    }

    @Override
    public int getItemCount() {
        return games != null ? games.size() : 0;
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView gameTeam, gameDate;

        GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameTeam = itemView.findViewById(R.id.game_team);
            gameDate = itemView.findViewById(R.id.game_date);
        }
    }
}
