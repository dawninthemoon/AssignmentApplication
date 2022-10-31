package com.example.assignmentapplication;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class SignupActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SharedPreferences infoPreference = getSharedPreferences(AppUtility.USER_INFO_ID, MODE_PRIVATE);
        SharedPreferences.Editor infoEditor = infoPreference.edit();

        EditText inputID = (EditText)findViewById(R.id.input_signup_id);
        EditText inputPW = (EditText)findViewById(R.id.input_signup_pw);
        EditText inputName = (EditText)findViewById(R.id.input_signup_name);
        EditText inputPhone = (EditText)findViewById(R.id.input_signup_phone);
        EditText inputAddress = (EditText)findViewById(R.id.input_signup_address);

        RadioButton radio_disagree = (RadioButton)findViewById(R.id.radio_disagree);

        Button applyButton = (Button)findViewById(R.id.btn_signup_apply);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(SignupActivity.this);
                myAlertBuilder.setTitle("오류");
                myAlertBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    }
                });

                if (checkIDExists(infoPreference, inputID.getText().toString())) {
                    myAlertBuilder.setMessage("ID가 이미 존재합니다.");
                    myAlertBuilder.show();
                    return;
                }

                // 비밀번호 조건 검사(Regex)
                if (!checkPWValid(infoPreference, inputPW.getText().toString())) {
                    myAlertBuilder.setMessage("비밀번호는 영어, 숫자, 특수문자를 포함하고, 8자에서 20자 사이어야 합니다.");
                    myAlertBuilder.show();
                    return;
                }

                // 약관 비동의

                if (radio_disagree.isChecked()) {
                    myAlertBuilder.setMessage("약관에 동의해야 회원가입을 마칠 수 있습니다.");
                    myAlertBuilder.show();
                    return;
                }

                String idStr = inputID.getText().toString();
                String nameStr = inputName.getText().toString();
                String phoneStr = inputPhone.getText().toString();
                String addressStr = inputAddress.getText().toString();

                String infoStr = nameStr + "/" + phoneStr + "/" + addressStr;

                AppUtility.CUR_ID = idStr;

                infoEditor.putString(AppUtility.UID_ID + idStr, inputPW.getText().toString());
                infoEditor.putString(AppUtility.USER_INFO_ID + "_" + idStr, infoStr);
                infoEditor.commit();

                finish();
            }
        });
    }

    private boolean checkIDExists(SharedPreferences pref, String id) {
        String pwStr = pref.getString(String.valueOf(AppUtility.UID_ID + id), null);
        return pwStr != null;
    }

    private boolean checkPWValid(SharedPreferences pref, String pw) {
        Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher = passPattern.matcher(pw);

        return passMatcher.find();
    }
}
