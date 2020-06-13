package com.example.trivbox.network;

import android.content.Context;

import com.example.trivbox.models.ApiResponse;
import com.example.trivbox.interfaces.RequestInterface;
import com.example.trivbox.models.Question;
import com.example.trivbox.utils.Utils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private RequestInterface requestInterface;
    private Context context;
    private HashMap<String, String> selections = new HashMap<String, String>();
    private ApiResponseInterface apiResponseInterface;

    public API(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestInterface = retrofit.create(RequestInterface.class);
        this.context = context;
    }

    public List<Question> getQuestions(HashMap<String, String> selections){
        this.selections = selections;
        Call<ApiResponse> myCall = requestInterface.getQuestions(selections.get("cat_id"), selections.get("difficulty"), selections.get("type"));

        myCall.enqueue(new Callback<ApiResponse>() {
            private ApiResponse apiResponseBody;
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                apiResponseBody = response.body();

                List<Question> responseQuestions = checkResponseCode(apiResponseBody);

                if (responseQuestions != null){
                    apiResponseInterface = (ApiResponseInterface) context;
                    apiResponseInterface.changeActivity(apiResponseBody);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Utils.showToast(context, "Error: No internet connection", false);
            }
        });
        return null;
    }

    public List<Question> checkResponseCode(ApiResponse apiResponse){
        switch(apiResponse.getResponseCode()) {
            case 0:
                return apiResponse.getQuestions();
            case 1:
                Utils.showToast(context, "Error: No questions found for that combination", false);
                break;
            default:
                Utils.showToast(context, "Error: Try another category", false);
        }
        return null;
    }

    public interface ApiResponseInterface {
        void changeActivity(ApiResponse apiResponse);
    }
}