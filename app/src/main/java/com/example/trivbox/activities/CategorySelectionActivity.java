package com.example.trivbox.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.example.trivbox.R;
import com.example.trivbox.models.ApiResponse;
import com.example.trivbox.models.Question;
import com.example.trivbox.models.Score;
import com.example.trivbox.network.API;
import com.example.trivbox.utils.Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import static com.example.trivbox.utils.Utils.spinnerAdapter;

public class CategorySelectionActivity extends AppCompatActivity implements API.ApiResponseInterface {

    private Spinner catSpinner,diffSpinner,typeSpinner;
    private HashMap<String, String> selections = new HashMap<String, String>();
    private List<Question> responseQuestions;
    private Score scoreObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        catSpinner = (Spinner) findViewById(R.id.cat_spinner);
        diffSpinner = (Spinner) findViewById(R.id.diff_spinner);
        typeSpinner = (Spinner) findViewById(R.id.type_spinner);

        spinnerAdapter(this, catSpinner, getResources().getStringArray(R.array.categories));
        spinnerAdapter(this, diffSpinner, getResources().getStringArray(R.array.difficulty));
        spinnerAdapter(this, typeSpinner, getResources().getStringArray(R.array.type));
    }

    public void start_game(View view) {
        String catIdText = catSpinner.getSelectedItem().toString();
        String difficultyText = diffSpinner.getSelectedItem().toString();
        String typeText = typeSpinner.getSelectedItem().toString();

        selections.put("cat_id", getCategoryId(catIdText));
        selections.put("difficulty", getDifficulty(difficultyText));
        selections.put("type", getType(typeText));

        scoreObj = new Score(catIdText, difficultyText, typeText);

        Utils.showToast(this, "Fetching data!!!", true);

        API api = new API(CategorySelectionActivity.this);
        responseQuestions = api.getQuestions(selections);
    }

    @Override
    public void changeActivity(ApiResponse apiResponse){
        List<Question> res = apiResponse.getQuestions();
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("response", (Serializable) res);
        intent.putExtra("scoreObj", (Serializable) scoreObj);
        startActivity(intent);
    }

    private String getCategoryId(String selectedCategory){
        String[] categories = getResources().getStringArray(R.array.categories);
        if(!selectedCategory.equals("Any Category")){
            for (int i=1;i<categories.length;i++) {
                if (categories[i].equals(selectedCategory)){
                    return Integer.toString(i+8);
                }
            }
        }
        return "";
    }

    private String getDifficulty(String selectedDifficulty){
        if (!selectedDifficulty.equals("Any Difficulty")){
            return selectedDifficulty.substring(0, 1).toLowerCase() + selectedDifficulty.substring(1);
        }
        return "";
    }

    private String getType(String selectedType) {
        switch (selectedType){
            case "Multiple Choice":
                return "multiple";
            case "True/False":
                return "boolean";
            default:
                return "";
        }
    }
}
