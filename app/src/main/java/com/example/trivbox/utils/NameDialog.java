package com.example.trivbox.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.trivbox.R;

public class NameDialog {
    private Context context;
    private SendNameInterface sendNameInterface;

    public NameDialog(Context context) {
        this.context = context;
    }

    public void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View mview = LayoutInflater.from(context).inflate(R.layout.name_dialog, null);

        final EditText editName = (EditText) mview.findViewById(R.id.nametext_id);
        Button btn_cancel = (Button) mview.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) mview.findViewById(R.id.btn_ok);
        alert.setView(mview);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Utils.showToast(context,"Canceled", true);
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();

                if (name.isEmpty()){
                    Utils.showToast(context,"Please enter your name or cancel the dialog box", false);
                } else {
                    sendNameInterface = (SendNameInterface) context;
                    sendNameInterface.sendName(name);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    public interface SendNameInterface {
        void sendName(String name);
    }
}
