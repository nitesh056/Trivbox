package com.example.trivbox.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivbox.R;
import com.example.trivbox.models.Question;
import com.example.trivbox.models.Score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    private TextView questionNoText, questionText;
    private Button option1, option2, option3, option4;
    private Question currentQuestion;
    int questionNo;
    List<Question> apiResponse;
    Handler handler;
    int score;
    private Score scoreObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        getAllId();

        handler = new Handler();

        Intent i = getIntent();
        apiResponse = (List<Question>) i.getExtras().getSerializable("response");
        scoreObj = (Score) i.getExtras().getSerializable("scoreObj");
        Toast.makeText(this, scoreObj.getCategory()+scoreObj.getDifficulty()+scoreObj.getType(), Toast.LENGTH_SHORT).show();
        questionNo = 1;
        score = 0;
        currentQuestion = apiResponse.get(questionNo-1);

        setData();

        option1.setOnClickListener(checkAnswer);
        option2.setOnClickListener(checkAnswer);
        option3.setOnClickListener(checkAnswer);
        option4.setOnClickListener(checkAnswer);

    }

    private View.OnClickListener checkAnswer = new View.OnClickListener() {
        public void onClick(View v) {
            Button clickedButton = ((Button) v);
            String clickedOption = (String) clickedButton.getText();
            if (clickedOption.equals(currentQuestion.getCorrectAnswer())) {
                if (currentQuestion.getDifficulty().equals("easy")){
                    score += 10;
                } else if (currentQuestion.getDifficulty().equals("medium")){
                    score += 30;
                } else if (currentQuestion.getDifficulty().equals("hard")){
                    score += 60;
                }
                ((Button) v).setBackgroundColor(Color.parseColor("#00FF00"));
//                Toast.makeText(QuestionsActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
            }else{
                ((Button) v).setBackgroundColor(Color.parseColor("#FF0000"));
//                Toast.makeText(QuestionsActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
            }

            questionNo = questionNo + 1;
            if (questionNo <= 10) {
                currentQuestion = apiResponse.get(questionNo - 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setData();
                    }
                }, 100);
            }else{
                changeActivity();
            }
        }
    };

    private void changeActivity(){
        Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("scoreObj", (Serializable) scoreObj);
        startActivity(intent);
    }

    private void getAllId(){
        questionNoText = findViewById(R.id.question_no);
        questionText = findViewById(R.id.question_id);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
    }

    public void setData() {
        option1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        option2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        option3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        option4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        String question = currentQuestion.getQuestion();
        String correctAnswer = currentQuestion.getCorrectAnswer();
        List<String> wrongAnswer = currentQuestion.getIncorrectAnswers();
        String questionType = currentQuestion.getType();

        questionNoText.setText("Question No. " + questionNo);
        questionText.setText(question);

        ArrayList<String> allOptions = new ArrayList<String>();
        allOptions.add(correctAnswer);
        allOptions.addAll(wrongAnswer);

        Collections.shuffle(allOptions);

        option1.setText(allOptions.get(0));
        option2.setText(allOptions.get(1));
        if (questionType.equals("multiple")){
            option3.setVisibility(View.VISIBLE);
            option4.setVisibility(View.VISIBLE);

            option3.setText(allOptions.get(2));
            option4.setText(allOptions.get(3));
        }else{
            option3.setVisibility(View.GONE);
            option4.setVisibility(View.GONE);
        }
    }
}
