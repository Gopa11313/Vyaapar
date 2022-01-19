package net.com.gopal.vyapar.company;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.com.gopal.vyapar.MainActivity;
import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.database.AppDatabase;
import net.com.gopal.vyapar.database.entity.Company;
import net.com.gopal.vyapar.product.ProductActivity;

import java.util.List;
import java.util.Objects;

public class CompanyActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbaruni;
    public TextView title;
    private AppCompatEditText companyName, address1, address2, address3, state, zip, sales, office, mobile, email, website;
    private AppCompatButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        toolbaruni = findViewById(R.id.toolbar);
        setSupportActionBar(toolbaruni);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitleToolbar("Company Register");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbaruni.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
    }
    private void init(){
        companyName=findViewById(R.id.companyName);
        address1=findViewById(R.id.address1);
        address2=findViewById(R.id.address2);
        address3=findViewById(R.id.address3);
        state=findViewById(R.id.state);
        zip=findViewById(R.id.zip);
        sales=findViewById(R.id.sales);
        office=findViewById(R.id.office);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        office=findViewById(R.id.office);
        website=findViewById(R.id.website);
        submitButton=findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

    }

    public Toolbar getToolbar() {
        return toolbaruni;
    }

    public void setTitleToolbar(String string) {

        TextView toolbar_text = (TextView) toolbaruni.findViewById(R.id.title);
        toolbar_text.setText(string);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CompanyActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitButton:
                String cName=companyName.getText().toString();
                String ad1=address1.getText().toString();
                String ad2=address2.getText().toString();
                String ad3=address3.getText().toString();
                String st=state.getText().toString();
                String zp=zip.getText().toString();
                String sale=sales.getText().toString();
                String ofc=office.getText().toString();
                String mbl=mobile.getText().toString();
                String em=email.getText().toString();
                String web=website.getText().toString();
                if(!cName.isEmpty()&&ad1.isEmpty()&&!st.isEmpty()&&!zp.isEmpty()&&!sale.isEmpty()&&!ofc.isEmpty()&&!mbl.isEmpty()&&!em.isEmpty()) {
                    Company company = new Company();
                    company.setName(cName);
                    company.setAaddress1(ad1);
                    company.setAddress2(ad2);
                    company.setAddress3(ad3);
                    company.setState(st);
                    company.setZip(zp);
                    company.setSales(sale);
                    company.setOffice(ofc);
                    company.setMobile(mbl);
                    company.setEmail(em);
                    company.setWebsite(web);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                            db.companyDao().insertAll(company);
                            List<Company> list = db.companyDao().getAll();
                            System.out.println("Gopal: " + list);
                        }
                    });
                }else{
                    if(cName.isEmpty()){
                        companyName.setError("Please enter company name");
                        companyName.requestFocus();
                    }
                    if(ad1.isEmpty()){
                        address1.setError("Please provide a address");
                        address1.requestFocus();
                    }
                    if(st.isEmpty()){
                        state.setError("Please enter company state");
                        state.requestFocus();
                    }
                    if(zp.isEmpty()){
                        zip.setError("Please enter zip code");
                    }
                    if(sale.isEmpty()){
                        sales.setError("Please enter company sales");
                        sales.requestFocus();
                    }
                    if(ofc.isEmpty()){
                        office.setError("Please enter office name");
                        office.requestFocus();
                    }
                    if(mbl.isEmpty()){
                        mobile.setError("Please enter a mobile number");
                    }
                    if(em.isEmpty()){
                        email.setError("Please enter a company email");
                        email.requestFocus();
                    }
                }
                break;
        }
    }
}