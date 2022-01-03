package net.com.gopal.vyapar.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.database.entity.Customer;
import net.com.gopal.vyapar.database.entity.Product;
import net.com.gopal.vyapar.invoice.adapter.CustomerAdapter;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {

    private ArrayList<Product> products;
    private ArrayList<Product> filteredList;
    private ClickCallBack clickCallBack;

    public ProductAdapter(ArrayList<Product> products, ClickCallBack clickCallBack) {
        this.products = products;
        this.filteredList=products;
        this.clickCallBack = clickCallBack;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAdapter.ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = filteredList.get(position);
        holder.mccName.setText(product.getDescription());
        holder.mccLayout.setOnClickListener(v -> {
            clickCallBack.onClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charList = constraint.toString();
                ArrayList<Product> filterableList = new ArrayList<>();
                if (charList.isEmpty()) {
                    filterableList = products;
                } else {
                    for (Product district : products) {
                        if (district.getDescription().toUpperCase().contains(charList.toUpperCase())) {
                            filterableList.add(district);
                        }
                    }
                    filteredList = filterableList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mccLayout;
        private TextView mccName;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mccLayout = itemView.findViewById(R.id.districtLayout);
            mccName = itemView.findViewById(R.id.districtName);
        }
    }

    public interface ClickCallBack {
        void onClick(Product products);
    }
}
