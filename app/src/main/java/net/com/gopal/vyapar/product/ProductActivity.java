package net.com.gopal.vyapar.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.com.gopal.vyapar.MainActivity;
import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.database.AppDatabase;
import net.com.gopal.vyapar.database.entity.Product;

import java.util.List;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbaruni;
    public TextView title;
    private AppCompatEditText productCode, description, Supplier, rate;
    private ImageView image;
    private AppCompatButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        toolbaruni = findViewById(R.id.toolbar);
        setSupportActionBar(toolbaruni);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitleToolbar("Product Register");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
        productCode = findViewById(R.id.productCode);
        description = findViewById(R.id.description);
        Supplier = findViewById(R.id.Supplier);
        rate = findViewById(R.id.rate);
        image = findViewById(R.id.image);
        submitButton = findViewById(R.id.submitButton);
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
        Intent intent = new Intent(ProductActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton:
                try {
                    String dis = description.getText().toString();
                    String pCode = productCode.getText().toString();
                    String rt = rate.getText().toString();
                    String sp = Supplier.getText().toString();
                    if (!dis.isEmpty() && !pCode.isEmpty() && !rt.isEmpty() && !sp.isEmpty()) {
                        Product product = new Product();
                        product.setDescription(dis);
                        product.setImageUrl("");
                        product.setProductCode(pCode);
                        product.setRate(rt);
                        product.setSupplier(sp);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                                db.productDao().insertAll(product);
                                List<Product> p = db.productDao().getAll();
                                System.out.println(p);
                                clean();
                            }
                        });
                    }
                    else{
                        if(dis.isEmpty()){
                            description.setError("Please enter product description");
                            description.requestFocus();
                        }
                        if(pCode.isEmpty()){
                            productCode.setError("Please enter product code");
                            productCode.requestFocus();
                        }
                        if(rt.isEmpty()){
                            rate.setError("Please enter product rate");
                            rate.requestFocus();
                        }
                        if(sp.isEmpty()){
                            Supplier.setError("Please enter product Supplier");
                            Supplier.requestFocus();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void clean() {
        description.setText("");
        productCode.setText("");
        rate.setText("");
        Supplier.setText("");
    }
}