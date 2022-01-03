package net.com.gopal.vyapar.invoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.company.CompanyActivity;
import net.com.gopal.vyapar.customer.adapter.SpineerAdapter;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.database.entity.Customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView invoices;
    Toolbar toolbaruni;
    public TextView title;
    private AppCompatEditText invoiceCode,pick_date,tin_Number,selectCustomer;
    private Spinner invoice_type;
    private AppCompatButton createCustomer,addItem;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String futureDate;
    private Dialog dialog;
    private RecyclerView recyclerView;
    private DistrictAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        toolbaruni = findViewById(R.id.toolbar);
        setSupportActionBar(toolbaruni);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitleToolbar("Invoice");
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
        invoices = findViewById(R.id.invoices);
        invoiceCode = findViewById(R.id.invoiceCode);
        pick_date = findViewById(R.id.pick_date);
        tin_Number = findViewById(R.id.tin_Number);
        invoice_type = findViewById(R.id.invoice_type);
        selectCustomer = findViewById(R.id.selectCustomer);
        createCustomer = findViewById(R.id.createCustomer);
        addItem = findViewById(R.id.addItem);
        createCustomer.setOnClickListener(this);
        pick_date.setOnClickListener(this);
        addItem.setOnClickListener(this);
        ArrayList<Customer> customerTypes=new ArrayList<>();
        Customer customer=new Customer()    ;
        customer.setCustomerType("Credit");
        customerTypes.add(customer);
        Customer customer1=new Customer()    ;
        customer1.setCustomerType("cash");
        customerTypes.add(customer1);
        setIssueFrom(customerTypes);
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
        Intent intent = new Intent(InvoiceActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createCustomer:
                break;
            case R.id.addItem:
                break;
                case R.id.pick_date:
                    showCalendar();
                break;
        }

    }
    private void showCalendar() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, (view, year, month, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, dayOfMonth);
            pick_date.setText(format.format(newDate.getTime()));
            futureDate = format.format(newDate.getTime());
            runOnUiThread(() -> {
            });

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    private void setIssueFrom(final ArrayList<Customer> customerTypes) {

        SpineerAdapter spineerAdapter = new SpineerAdapter(getApplicationContext(), customerTypes);
        invoice_type.setAdapter(spineerAdapter);
        invoice_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    private void showDistrictDialog() {
        dialog = new Dialog(this, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.dialog_fullscreen_withdraw);
        recyclerView = dialog.findViewById(R.id.bankRecyclerView);
        SearchView searchView = dialog.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        EditText search = searchView.findViewById(R.id.search_src_text);
        search.setTextColor(getResources().getColor(R.color.white));
        search.setHintTextColor(getResources().getColor(android.R.color.white));
        ImageView imageView = searchView.findViewById(R.id.search_close_btn);
        imageView.setImageResource(R.drawable.ic_action_cancel);
        ImageView closeBranch = searchView.findViewById(R.id.search_button);
        closeBranch.setImageResource(R.drawable.ic_search_black_24dp);

        adapter = new DistrictAdapter(districts, new DistrictAdapter.ClickCallBack() {
            @Override
            public void onClick(District district) {
                districtId = district.getId();
                selectedDistrict=district;
                AppCache.setCity(districtId);
                issuedfromEdiText.setText(district.getDistrict());
                issuefromLayout.setError(null);
                dialog.dismiss();
            }
        });

        RecyclerView.LayoutManager mLayoutManagerTo = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManagerTo);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        dialog.show();


        ImageView backArrowImageViews = dialog.findViewById(R.id.backArrowImageView);
        backArrowImageViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}