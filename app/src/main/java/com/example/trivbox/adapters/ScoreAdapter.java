package com.example.trivbox.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trivbox.R;
import com.example.trivbox.models.Score;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.MyViewHolder> {

    private List<Score> scoreList;

    public ScoreAdapter(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public ScoreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.MyViewHolder holder, int position) {
        int number = position + 1;
        String category = scoreList.get(position).getCategory();
        String difficulty = scoreList.get(position).getDifficulty();
        String type = scoreList.get(position).getType();
        int score = scoreList.get(position).getPoint();
        holder.setData(number, category, difficulty, type, score);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numberText, catText, diffText, typeText, scoreText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            numberText = itemView.findViewById(R.id.scorelistnumber_id);
            catText = itemView.findViewById(R.id.catlist_id);
            diffText = itemView.findViewById(R.id.difflist_id);
            typeText = itemView.findViewById(R.id.typelist_id);
            scoreText = itemView.findViewById(R.id.scorelist_id);
        }

        public void setData(int number, String category, String difficulty, String type, int score) {
            numberText.setText(Integer.toString(number));
            catText.setText(category);
            diffText.setText(difficulty);
            typeText.setText(type);
            scoreText.setText(""+score);
        }
    }
}
