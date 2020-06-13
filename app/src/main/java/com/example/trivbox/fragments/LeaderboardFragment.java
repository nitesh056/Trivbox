package com.example.trivbox.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivbox.adapters.LeaderboardAdapter;
import com.example.trivbox.R;
import com.example.trivbox.models.Leaderboard;
import com.example.trivbox.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.internal.Util;

public class LeaderboardFragment extends Fragment {
    private DatabaseReference dbRef;
    private List<Leaderboard> leaderboardList = new ArrayList<Leaderboard>();;
    private TextView scoreboardTitle;
    private RecyclerView scoreboard_rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false);

        scoreboardTitle = rootView.findViewById(R.id.scoreboardTitle_id);
        scoreboardTitle.setText("Leaderboard");

        scoreboard_rv = rootView.findViewById(R.id.scoreboard_rv);
        scoreboard_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbRef = FirebaseDatabase.getInstance().getReference("Leaderboard");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                leaderboardList.clear();
                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
                    leaderboardList.add(dataSnapshot.getValue(Leaderboard.class));
                }
                if (leaderboardList.size()>0){
                    Collections.sort(leaderboardList, Collections.reverseOrder());
                    scoreboard_rv.setAdapter(new LeaderboardAdapter(leaderboardList));
                } else {
                    Utils.showToast(getActivity(), "List not found", true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Utils.showToast(getActivity(), "Firebase db not accessible", true);
            }
        });
    }
}
