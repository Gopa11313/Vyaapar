package net.com.gopal.vyapar.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.com.gopal.vyapar.R;

import java.util.Objects;

public class CustomerActivity extends AppCompatActivity {
    Toolbar toolbaruni;
    public TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        toolbaruni=findViewById(R.id.toolbar);
        setSupportActionBar(toolbaruni);
        setTitleToolbar("Customer Register");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbaruni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public Toolbar getToolbar() {
        return toolbaruni;
    }
    public void setTitleToolbar(String string){

        TextView toolbar_text=(TextView)toolbaruni.findViewById(R.id.title);
        toolbar_text.setText(string);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(TopUpToWalletFromBankAccountActivity.this, AccountActivity.class);
//        startActivity(intent);
        finish();
    }

}