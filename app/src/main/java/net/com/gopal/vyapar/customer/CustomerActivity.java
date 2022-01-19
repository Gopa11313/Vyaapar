package net.com.gopal.vyapar.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import net.com.gopal.vyapar.MainActivity;
import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.customer.adapter.SpineerAdapter;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.database.AppDatabase;
import net.com.gopal.vyapar.database.dao.CustomerDao;
import net.com.gopal.vyapar.database.entity.Customer;
import net.com.gopal.vyapar.invoice.InvoiceActivity;
import net.com.gopal.vyapar.product.ProductActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbaruni;
    public TextView title;
    private AppCompatEditText branchName, customerNAme, emailAddress, tin_number, address, city, zip, mobile_number, land_line, notes;
    private Spinner customerType;
    private AppCompatButton submitButton;
    private String from = null;
    private Boolean success = false;
    private LinearLayout snackbar_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        toolbaruni = findViewById(R.id.toolbar);
        setSupportActionBar(toolbaruni);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitleToolbar("Customer Register");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbaruni.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
    }

    private void init() {
        branchName = findViewById(R.id.branchName);
        customerNAme = findViewById(R.id.customerNAme);
        emailAddress = findViewById(R.id.emailAddress);
        tin_number = findViewById(R.id.tin_number);
        address = findViewById(R.id.address);
        snackbar_action = findViewById(R.id.snackbar_action);
        city = findViewById(R.id.city);
        zip = findViewById(R.id.zip);
        mobile_number = findViewById(R.id.mobile_number);
        land_line = findViewById(R.id.land_line);
        notes = findViewById(R.id.notes);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        customerType = findViewById(R.id.customerType);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
        ArrayList<Customer> customerType = new ArrayList<>();
        Customer customer = new Customer();
        customer.setCustomerType("Credit");
        customerType.add(customer);
        Customer customer1 = new Customer();
        customer1.setCustomerType("cash");
        customerType.add(customer1);
        setIssueFrom(customerType);
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
        Intent intent = new Intent(CustomerActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    private void setIssueFrom(final ArrayList<Customer> customerTypes) {

        SpineerAdapter spineerAdapter = new SpineerAdapter(getApplicationContext(), customerTypes);
        customerType.setAdapter(spineerAdapter);
        customerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                issueFromId = issueFrom.get(i).getId();
//                issuefromLayout.setError(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton:
                try {
                    String addr = address.getText().toString();
                    String brnch = branchName.getText().toString();
                    String cty = city.getText().toString();
                    String email = emailAddress.getText().toString();
                    String ll = land_line.getText().toString();
                    String cName = customerNAme.getText().toString();
                    String tinNo = tin_number.getText().toString();
                    String nt = notes.getText().toString();
                    String zp = zip.getText().toString();
                    if (!addr.isEmpty() && !brnch.isEmpty() && !cty.isEmpty() && !email.isEmpty() && !ll.isEmpty() && !cName.isEmpty() && !tinNo.isEmpty() && !nt.isEmpty() && !zp.isEmpty()) {
                        success = false;
                        Customer customer = new Customer();
                        customer.setAddress(addr);
                        customer.setBranch(brnch);
                        customer.setCity(cty);
//               Int a=customerType.getSelectedItemPosition();
                        customer.setCustomerType("Credit");
                        customer.setEmail(email);
                        customer.setLandLine(ll);
                        customer.setName(cName);
                        customer.setTinNumber(tinNo);
                        customer.setNote(nt);
                        customer.setZip(zp);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                                db.customerDao().insertAll(customer);
                                if (from == null) {
                                    success = true;
                                } else {
                                    finish();
                                    startActivity(new Intent(CustomerActivity.this, InvoiceActivity.class));
                                }

                            }
                        });
                        Snackbar snackbar = Snackbar
                                .make(snackbar_action, "Customer Register Successfully!!", Snackbar.LENGTH_LONG)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

//                                    Snackbar snackbar1 = Snackbar.make(snackbar_action, "Message is restored!", Snackbar.LENGTH_SHORT);
//                                    snackbar1.show();
                                    }
                                });

                        snackbar.show();
                        clear();
                    } else {
                        if (addr.isEmpty()) {
                            address.setError("Please enter address");
                            address.requestFocus();
                        }
                        if (brnch.isEmpty()) {
                            branchName.setError("Please enter branch");
                            branchName.requestFocus();
                        }

                        if (cty.isEmpty()) {
                            city.setError("Please enter city");
                            city.requestFocus();
                        }
                        if (email.isEmpty()) {
                            emailAddress.setError("Please enter your email address");
                            emailAddress.requestFocus();
                        }
                        if (ll.isEmpty()) {
                            land_line.setError("Please enter your land line");
                        }
                        if (cName.isEmpty()) {
                            customerNAme.setError("Please enter customer name");
                        }
                        if (tinNo.isEmpty()) {
                            tin_number.setError("Please enter tin no.");
                        }
                        if (nt.isEmpty()) {
                            notes.setError("Please enter some notes");
                        }
                        if (zp.isEmpty()) {
                            zip.setError("Please enter your country zip code");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void clear() {
        branchName.setText("");
        customerNAme.setText("");
        emailAddress.setText("");
        tin_number.setText("");
        address.setText("");
        city.setText("");
        zip.setText("");
        mobile_number.setText("");
        land_line.setText("");
        notes.setText("");
    }
}