package com.example.assignmentapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class ShopActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        Button userInfoButton = findViewById(R.id.btn_userInfo);
        String uid = intent.getStringExtra("id");

        SharedPreferences userInfo = getSharedPreferences(AppUtility.USER_INFO_ID, MODE_PRIVATE);
        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uid.equals(AppUtility.UID_GUEST)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
                    builder.setTitle("Register");
                    builder.setMessage("회원만 사용할 수 있는 기능입니다. 회원가입 페이지로 넘어가시겠습니까?");
                    builder.setNegativeButton("아니오", null);
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
                            startActivity(signupIntent);
                        }
                    });
                    builder.show();
                }
                else {
                    String rawInfoStr = userInfo.getString(AppUtility.USER_INFO_ID + "_" + uid, null);
                    String[] userInfo = rawInfoStr.split("/");
                    String message = "이름: " + userInfo[0] + "\n";
                    message += "전화번호: " + userInfo[1] + "\n";
                    message += "주소: " + userInfo[2] + "\n";

                    AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
                    builder.setTitle(AppUtility.USER_INFO_ID);
                    builder.setMessage(message);
                    builder.setPositiveButton("확인", null);
                    builder.show();
                }
            }
        });
    }
}
