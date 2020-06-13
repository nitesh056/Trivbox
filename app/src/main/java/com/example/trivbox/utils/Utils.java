package com.example.trivbox.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trivbox.R;

public class Utils {
    public static void showToast(Context context, String message, Boolean isShort){
        if (isShort) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void spinnerAdapter(Context context, Spinner spinner, String[] stringArray){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, stringArray);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(arrayAdapter);
    }
}
