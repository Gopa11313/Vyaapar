package net.com.gopal.vyapar.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.utils.GeneralPref;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    AppCompatEditText code_first;
    AppCompatButton submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init(){
        code_first=findViewById(R.id.code_first);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                if(code_first.getText().toString().equals("123498")){
                    try {
                        GeneralPref generalPref=new GeneralPref(LoginActivity.this);
                        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                        generalPref.setIsFirst(true);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}