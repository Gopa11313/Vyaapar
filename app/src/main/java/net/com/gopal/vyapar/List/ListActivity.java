package net.com.gopal.vyapar.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.folder.FolderActivity;

import org.json.JSONObject;

public class ListActivity extends AppCompatActivity {
    Toolbar toolbaruni;
    public TextView title;
    private AppCompatTextView address, branch,city,customerType,email,landLine,name,note,tinNumber,zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbaruni = findViewById(R.id.toolbar);
        setSupportActionBar(toolbaruni);
        setTitleToolbar("Lists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbaruni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        System.out.println(data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            String adr = jsonObject.getString("address");
            String bnch = jsonObject.getString("branch");
            String cid = jsonObject.getString("cid");
            String cty = jsonObject.getString("city");
            String custType = jsonObject.getString("customerType");
            String eml = jsonObject.getString("email");
            String lL = jsonObject.getString("landLine");
            String nm = jsonObject.getString("name");
            String nt = jsonObject.getString("note");
            String tinNo = jsonObject.getString("tinNumber");
            String zp = jsonObject.getString("zip");
            address=findViewById(R.id.address);
            address.setText(adr);
            branch=findViewById(R.id.branch);
            branch.setText(bnch);
            city=findViewById(R.id.city);
            city.setText(cty);
            customerType=findViewById(R.id.customerType);
            customerType.setText(custType);
            email=findViewById(R.id.email);
            email.setText(eml);
            landLine=findViewById(R.id.landLine);
            landLine.setText(lL);
            name=findViewById(R.id.name);
            name.setText(nm);
            note=findViewById(R.id.note);
            note.setText(nt);
            tinNumber=findViewById(R.id.tinNumber);
            tinNumber.setText(tinNo);
            zip=findViewById(R.id.zip);
            zip.setText(zp);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Intent intent = new Intent(ListActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }
}