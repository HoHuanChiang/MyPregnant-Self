package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.UpdatePassword;
import com.example.mypregnant.Function.ToolBarFunction;

public class BasicPasswordEdit extends AppCompatActivity {
    ImageButton saveBtn;
    EditText currentText,firstText,secondText;
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_password_edit);
        ToolBarFunction.setToolBarInit(this,"重設密碼");
        currentText=findViewById(R.id.basicPasswordCurrent);
        firstText=findViewById(R.id.basicPasswordNewFirst);
        secondText=findViewById(R.id.basicPasswordNewSecond);
        saveBtn=findViewById(R.id.basicPasswordSaveBtn);
        userID=getSharedPreferences("data",0).getInt("user",0);
        currentText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstText.getText().toString().trim().equals(secondText.getText().toString().trim())){
                    Toast.makeText(BasicPasswordEdit.this, "密碼與確認密碼不一樣", Toast.LENGTH_SHORT).show();
                }
                else{
                    UpdatePassword updatePassword=new UpdatePassword(String.valueOf(userID), currentText.getText().toString(), firstText.getText().toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("0")){
                                Toast.makeText(BasicPasswordEdit.this, "目前的密碼錯誤!", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.trim().equals("1")){
                                Toast.makeText(BasicPasswordEdit.this, "密碼更改完成!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(BasicPasswordEdit.this, "發生錯誤!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    RequestQueue q= Volley.newRequestQueue(BasicPasswordEdit.this);
                    q.add(updatePassword);
                }
            }
        });
    }
}
