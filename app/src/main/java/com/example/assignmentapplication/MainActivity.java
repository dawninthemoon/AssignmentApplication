package com.example.assignmentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText idInput = (EditText) findViewById(R.id.input_id);
        EditText pwInput = (EditText) findViewById(R.id.input_pw);

        Button signInBtn = (Button) findViewById(R.id.btn_signin);
        Button signUpBtn = (Button) findViewById(R.id.btn_signup);

        SharedPreferences infoPreference = getSharedPreferences(AppUtility.USER_INFO_ID, MODE_PRIVATE);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwStr = infoPreference.getString(String.valueOf(AppUtility.UID_ID + idInput.getText()), null);

                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                myAlertBuilder.setTitle("오류");
                myAlertBuilder.setPositiveButton("확인",null);

                if (pwStr == null) {
                    myAlertBuilder.setMessage("ID가 존재하지 않습니다.");
                    myAlertBuilder.show();
                }
                else if(!pwStr.equals(pwInput.getText().toString())){
                    myAlertBuilder.setMessage("비밀번호가 일치하지 않습니다");
                    myAlertBuilder.show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                    AppUtility.CUR_ID = idInput.getText().toString();
                    startActivity(intent);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        Button guestBtn = findViewById(R.id.btn_guest);
        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                AppUtility.CUR_ID = AppUtility.UID_GUEST;
                startActivity(intent);
            }
        });
    }
}