package com.example.trivbox.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trivbox.R;
import com.example.trivbox.models.Leaderboard;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {
    private List<Leaderboard> leaderboardList;

    public LeaderboardAdapter(List<Leaderboard> leaderboardList) {
        this.leaderboardList = leaderboardList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_list, parent, false);
        return new LeaderboardAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int number = position + 1;
        String name = leaderboardList.get(position).getName();
        String category = leaderboardList.get(position).getScore().getCategory();
        String difficulty = leaderboardList.get(position).getScore().getDifficulty();
        String type = leaderboardList.get(position).getScore().getType();
        int point = leaderboardList.get(position).getScore().getPoint();
        holder.setData(number, name, category, difficulty, type, point);
    }

    @Override
    public int getItemCount() {
        return leaderboardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numberText, nameText, catText, diffText, typeText, scoreText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numberText = itemView.findViewById(R.id.scorelistnumber_id);
            nameText = itemView.findViewById(R.id.namelist_id);
            catText = itemView.findViewById(R.id.catlist_id);
            diffText = itemView.findViewById(R.id.difflist_id);
            typeText = itemView.findViewById(R.id.typelist_id);
            scoreText = itemView.findViewById(R.id.scorelist_id);
        }

        public void setData(int number, String name, String category, String difficulty, String type, int score) {
            numberText.setText(Integer.toString(number));
            nameText.setText(name);
            catText.setText(category);
            diffText.setText(difficulty);
            typeText.setText(type);
            scoreText.setText(""+score);
        }
    }
}
