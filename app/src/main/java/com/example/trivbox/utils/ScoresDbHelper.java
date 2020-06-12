package com.example.trivbox.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.trivbox.activities.ScoreboardActivity;
import com.example.trivbox.models.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoresDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "TrivBoxDB";
    public static final String TABLE_NAME = "Scores";
    public static final String COL_ONE = "score_id";
    public static final String COL_TWO = "category";
    public static final String COL_THREE = "difficulty";
    public static final String COL_FOUR = "type";
    public static final String COL_FIVE = "point";

    public ScoresDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_ONE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TWO + " TEXT, " +
                COL_THREE + " TEXT, " +
                COL_FOUR + " TEXT, " +
                COL_FIVE + " INTEGER" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertScore(Score score){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TWO, score.getCategory());
        values.put(COL_THREE, score.getDifficulty());
        values.put(COL_FOUR, score.getType());
        values.put(COL_FIVE, score.getPoint());
        long l = db.insert(TABLE_NAME, null, values);
        db.close();
        if (l==-1){
            return false;
        } else {
            return true;
        }
    }

    public List<Score> getAllScores(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_FIVE + " DESC";
        Cursor c = db.rawQuery(query, null);

        List<Score> scoreList = new ArrayList<>();

        if(c.moveToFirst()){
            do {
                scoreList.add(new Score(
                        c.getString(c.getColumnIndex(COL_TWO)),
                        c.getString(c.getColumnIndex(COL_THREE)),
                        c.getString(c.getColumnIndex(COL_FOUR)),
                        c.getInt(c.getColumnIndex(COL_FIVE))));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return scoreList;
    }

    public boolean checkHighScore(int points){
        List<Score> allScores = this.getAllScores();
        int max;
        if (allScores.size()==0) {
            max = 0;
        }else if (allScores.size()==1) {
            max = allScores.get(0).getPoint();
        }else{
            max = allScores.get(0).getPoint();
            int num;
            for (int i=1; i<allScores.size();i++){
                num = allScores.get(i).getPoint();
                if (max < num){
                    max = num;
                }
            }
        }
        return points > max;
    }
}
