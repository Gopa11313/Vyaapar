package net.com.gopal.vyapar.folder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.com.gopal.vyapar.MainActivity;
import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.dashboard.DashBoardActivity;
import net.com.gopal.vyapar.product.ProductActivity;

import java.util.ArrayList;
import java.util.Objects;

public class FolderActivity extends AppCompatActivity {
    Toolbar toolbaruni;
    public TextView title;
    private LoadFoldersAdapter mAdapter;
    private ArrayList<Folder> loadFundList=new ArrayList<>();
    private RecyclerView folderRecyclerView;
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
        toolbaruni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadFolders();
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
        Intent intent=new Intent(FolderActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }
    private void loadFolders(){
        Folder productModel = new Folder("1", R.drawable.ic_invoices, "Invoices", "History of invoices");
        loadFundList.add(productModel);

        Folder linkAccount = new Folder("6", R.drawable.ic_cart, "Product", "All the product in system");
        loadFundList.add(linkAccount);

        Folder modelEbanking = new Folder("2", R.drawable. ic_customer, "Customer", "All registered customer");
        loadFundList.add(modelEbanking);


        Folder mobileBanking = new Folder("3", R.drawable.ic_provider, "Supplier", "All registered supplier");
        loadFundList.add(mobileBanking);
        mAdapter = new LoadFoldersAdapter(this, loadFundList, new LoadFoldersAdapter.LoadFundOnClickListener() {
            @Override
            public void loadFundlistener(int position) {

            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setHasFixedSize(true);
        folderRecyclerView.setNestedScrollingEnabled(false);
        folderRecyclerView.setLayoutManager(mLayoutManager);
        folderRecyclerView.setAdapter(mAdapter);




        mAdapter.notifyDataSetChanged();
    }
}