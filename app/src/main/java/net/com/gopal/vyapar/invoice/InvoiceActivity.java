package net.com.gopal.vyapar.invoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.company.CompanyActivity;
import net.com.gopal.vyapar.customer.CustomerActivity;
import net.com.gopal.vyapar.customer.adapter.SpineerAdapter;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.database.AppDatabase;
import net.com.gopal.vyapar.database.entity.Customer;
import net.com.gopal.vyapar.database.entity.Product;
import net.com.gopal.vyapar.invoice.adapter.CustomerAdapter;
import net.com.gopal.vyapar.invoice.adapter.InvoiceAdapter;
import net.com.gopal.vyapar.invoice.model.Invoice;
import net.com.gopal.vyapar.invoice.model.InvoiceItem;
import net.com.gopal.vyapar.product.adapter.ProductAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView invoices;
    Toolbar toolbaruni;
    public TextView title;
    private AppCompatEditText invoiceCode, pick_date, tin_Number, customername;
    private Spinner invoice_type;
    private AppCompatButton createCustomer, addItem;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String futureDate;
    private Dialog dialog;
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private ProductAdapter adapter1;
    private ArrayList<Customer> customr = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    LinearLayout selectCustomer;
    AppCompatEditText selectItem;
    AppCompatEditText totoal;
    AppCompatEditText rate;
    AppCompatEditText quantity;
    AppCompatEditText discount;
    AppCompatTextView total;
    AppCompatEditText tax;
    AppCompatButton proceed;
    ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();
    InvoiceAdapter invoiceAdapter;
    Double totalAmount=0.00;
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

    private void init() {
        invoices = findViewById(R.id.invoices);
        invoiceCode = findViewById(R.id.invoiceCode);
        pick_date = findViewById(R.id.pick_date);
        tin_Number = findViewById(R.id.tin_Number);
        invoice_type = findViewById(R.id.invoice_type);
        selectCustomer = findViewById(R.id.selectCustomer);
        createCustomer = findViewById(R.id.createCustomer);
        addItem = findViewById(R.id.addItem);
        customername = findViewById(R.id.customername);
        total = findViewById(R.id.totals);
        proceed = findViewById(R.id.proceed);
        createCustomer.setOnClickListener(this);
        selectCustomer.setOnClickListener(this);
        pick_date.setOnClickListener(this);
        addItem.setOnClickListener(this);
        ArrayList<Customer> customerTypes = new ArrayList<>();
        Customer customer = new Customer();
        customer.setCustomerType("Credit");
        customerTypes.add(customer);
        Customer customer1 = new Customer();
        customer1.setCustomerType("cash");
        customerTypes.add(customer1);
        setIssueFrom(customerTypes);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getDatabase(InvoiceActivity.this);
                List<Customer> cus = db.customerDao().getAll();
                customr = new ArrayList<>(cus);
                List<Product> pros = db.productDao().getAll();
                products = new ArrayList<>(pros);
            }
        });
//        setData();
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
        switch (view.getId()) {
            case R.id.createCustomer:
                Intent intent = new Intent(InvoiceActivity.this, CustomerActivity.class);
                intent.putExtra("from", "invoice");
                startActivity(intent);
                break;
            case R.id.addItem:
                setAddItem();
                break;
            case R.id.pick_date:
                showCalendar();
                break;
            case R.id.selectCustomer:
                showCustomerDialog();
                break;
            case R.id.proceed:
                break;
        }

    }
    private void setData(){
        if(!invoiceItems.isEmpty()) {
            total.setText(totalAmount.toString());
             invoiceAdapter = new InvoiceAdapter(invoiceItems, new InvoiceAdapter.ClickCallBack() {
                @Override
                public void onClick(Invoice invoice) {

                }
            });
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            invoices.setLayoutManager(layoutManager);
            invoices.setAdapter(invoiceAdapter);
            invoiceAdapter.notifyDataSetChanged();
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

    private void setAddItem() {
        Dialog dialog1 = new Dialog(this, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.setContentView(R.layout.add_item_layout);
        selectItem = dialog1.findViewById(R.id.selectItem);
        rate = dialog1.findViewById(R.id.rate);
        totoal = dialog1.findViewById(R.id.totoal);
        quantity = dialog1.findViewById(R.id.quantity);
        discount = dialog1.findViewById(R.id.discount);
        tax = dialog1.findViewById(R.id.tax);
        AppCompatButton cancel = dialog1.findViewById(R.id.cancel);
        AppCompatButton proceed = dialog1.findViewById(R.id.proceed);
        selectItem.setOnClickListener(v -> {
            showProductDialog();
        });
        final Handler handler = new Handler();

        discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        GetTotal();
                    }
                }, 1000);

            }
        });
        tax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        GetTotal();
                    }
                }, 1000);
            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        GetTotal();
                    }
                }, 1000);
            }
        });
        cancel.setOnClickListener(v -> {
            dialog1.dismiss();
        });
        proceed.setOnClickListener(v -> {
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setDiscount(discount.getText().toString());
            invoiceItem.setItemName(selectItem.getText().toString());
            invoiceItem.setQuantity(quantity.getText().toString());
            invoiceItem.setRate(rate.getText().toString());
            invoiceItem.setTotal(totoal.getText().toString());
            invoiceItems.add(invoiceItem);
            totalAmount=totalAmount+Double.parseDouble(totoal.getText().toString());
            dialog1.dismiss();
            setData();
        });
        dialog1.show();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        float density = getResources().getDisplayMetrics().density;
        lp.gravity = Gravity.CENTER;
        dialog1.getWindow().setAttributes(lp);
    }

    private void setIssueFrom(final ArrayList<Customer> customerTypes) {

        SpineerAdapter spineerAdapter = new SpineerAdapter(InvoiceActivity.this, customerTypes);
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

    private void showCustomerDialog() {
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
        if (customr.size() > 0) {
            adapter = new CustomerAdapter(customr, new CustomerAdapter.ClickCallBack() {
                @Override
                public void onClick(Customer customer) {
//                   districtId = district.getId();
//                   selectedDistrict=district;
//                   AppCache.setCity(districtId);
                    customername.setText(customer.getName());
//                   issuefromLayout.setError(null);
                    dialog.dismiss();
                }
            });
            RecyclerView.LayoutManager mLayoutManagerTo = new LinearLayoutManager(InvoiceActivity.this);
            recyclerView.setLayoutManager(mLayoutManagerTo);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
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

    private void showProductDialog() {
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
        if (products.size() > 0) {
            adapter1 = new ProductAdapter(products, new ProductAdapter.ClickCallBack() {
                @Override
                public void onClick(Product products) {
//                   districtId = district.getId();
//                   selectedDistrict=district;
//                   AppCache.setCity(districtId);
//                    customername.setText(products.getDescription());
                    setTextInCustomer(products);
                    GetTotal();
//                   issuefromLayout.setError(null);
                    dialog.dismiss();
                }
            });
            RecyclerView.LayoutManager mLayoutManagerTo = new LinearLayoutManager(InvoiceActivity.this);
            recyclerView.setLayoutManager(mLayoutManagerTo);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter1);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        float density = getResources().getDisplayMetrics().density;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);


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
                adapter1.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter1.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void setTextInCustomer(Product product) {
        selectItem.setText(product.getDescription());
        rate.setText(product.getRate());
    }

    private void GetTotal() {

        Double rates = Double.parseDouble(rate.getText().toString());
        Double totalAmount = rates;

//        if(!quantity.getText().toString().isEmpty()){
//            Double quantitys = Double.parseDouble(quantity.getText().toString());
//            totalAmount = rates * quantitys;
//        }
        if (!discount.getText().toString().isEmpty() && !tax.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty()) {
            Double discounts = Double.parseDouble(discount.getText().toString());
            Double taxs = Double.parseDouble(tax.getText().toString());
            Double quantitys = Double.parseDouble(quantity.getText().toString());
            totalAmount = (totalAmount * quantitys) - (totalAmount * discounts / 100) - (totalAmount * taxs / 100);
        }

        totoal.setText(totalAmount.toString());
    }

}