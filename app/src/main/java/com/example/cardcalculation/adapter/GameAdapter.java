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

        // Set game team
        if (game.getTeam()) {
            holder.gameTeam.setText("Team");
        } else {
            holder.gameTeam.setText("Single");
        }

        // Format timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String date = sdf.format(new Date(game.getCreatedAt()));
        holder.gameDate.setText(date);

        holder.itemView.setOnClickListener(v -> listener.onGameClick(game));
//        holder.itemView.setOnLongClickListener(v -> {
//            listener.onNoteLongClick(note);
//            return true;
//        });
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
