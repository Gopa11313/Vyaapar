package net.com.gopal.vyapar.folder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import net.com.gopal.vyapar.List.ListActivity;
import net.com.gopal.vyapar.MainActivity;
import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.database.AppDatabase;
import net.com.gopal.vyapar.database.entity.Customer;
import net.com.gopal.vyapar.database.entity.Product;
import net.com.gopal.vyapar.invoice.InvoiceActivity;
import net.com.gopal.vyapar.invoice.adapter.CustomerAdapter;
import net.com.gopal.vyapar.product.ProductActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FolderActivity extends AppCompatActivity {
    Toolbar toolbaruni;
    public TextView title;
    private LoadFoldersAdapter mAdapter;
    private ArrayList<Folder> loadFundList = new ArrayList<>();
    private RecyclerView folderRecyclerView;
    private Dialog dialog;
    private RecyclerView recyclerView;
    private ArrayList<Customer> customr = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private CustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        toolbaruni = findViewById(R.id.toolbar);
        folderRecyclerView = findViewById(R.id.folderRecyclerView);
        setSupportActionBar(toolbaruni);
        setTitleToolbar("Folders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbaruni.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
        loadFolders();
    }

    private void init() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getDatabase(FolderActivity.this);
                List<Customer> cus = db.customerDao().getAll();
                customr = new ArrayList<>(cus);
                List<Product> pros = db.productDao().getAll();
                products = new ArrayList<>(pros);
            }
        });
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
        Intent intent = new Intent(FolderActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadFolders() {
        Folder productModel = new Folder("1", R.drawable.ic_invoices, "Invoices", "History of invoices");
        loadFundList.add(productModel);

        Folder linkAccount = new Folder("4", R.drawable.ic_cart, "Product", "All the product in system");
        loadFundList.add(linkAccount);

        Folder modelEbanking = new Folder("2", R.drawable.ic_customer, "Customer", "All registered customer");
        loadFundList.add(modelEbanking);


        Folder mobileBanking = new Folder("3", R.drawable.ic_provider, "Supplier", "All registered supplier");
        loadFundList.add(mobileBanking);
        mAdapter = new LoadFoldersAdapter(this, loadFundList, new LoadFoldersAdapter.LoadFundOnClickListener() {
            @Override
            public void loadFundlistener(int position) {
                switch (position) {
                    case 1:
                        break;
                    case 2:
                        showCustomerDialog();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }

            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setHasFixedSize(true);
        folderRecyclerView.setNestedScrollingEnabled(false);
        folderRecyclerView.setLayoutManager(mLayoutManager);
        folderRecyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();
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
                    Gson gson = new Gson();
                    String data = gson.toJson(customer);
                    Intent intent = new Intent(FolderActivity.this, ListActivity.class);
                    intent.putExtra("data", data);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            RecyclerView.LayoutManager mLayoutManagerTo = new LinearLayoutManager(FolderActivity.this);
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
}