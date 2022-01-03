package net.com.gopal.vyapar.invoice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.database.entity.Customer;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> implements Filterable {

    private ArrayList<Customer> customers;
    private ArrayList<Customer> filteredList;
    private ClickCallBack clickCallBack;

    public CustomerAdapter(ArrayList<Customer> customers, ClickCallBack clickCallBack) {
        this.customers = customers;
        this.filteredList=customers;
        this.clickCallBack = clickCallBack;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = filteredList.get(position);
        holder.mccName.setText(customer.getName());
        holder.mccLayout.setOnClickListener(v -> {
            clickCallBack.onClick(customer);
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
                ArrayList<Customer> filterableList = new ArrayList<>();
                if (charList.isEmpty()) {
                    filterableList = customers;
                } else {
                    for (Customer district : customers) {
                        if (district.getName().toUpperCase().contains(charList.toUpperCase())) {
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
                filteredList = (ArrayList<Customer>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mccLayout;
        private TextView mccName;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            mccLayout = itemView.findViewById(R.id.districtLayout);
            mccName = itemView.findViewById(R.id.districtName);
        }
    }

    public interface ClickCallBack {
        void onClick(Customer customer);
    }
}
