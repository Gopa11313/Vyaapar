package net.com.gopal.vyapar.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.database.entity.Customer;

import java.util.ArrayList;

public class SpineerAdapter extends BaseAdapter {
    Context context;
    ArrayList<Customer> arrayList;
    LayoutInflater inflter;

    public SpineerAdapter(Context applicationContext, ArrayList<Customer> countryNames) {
        this.context = applicationContext;
        this.arrayList = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_spineer, null);
        TextView names = (TextView) view.findViewById(R.id.itemName);
        names.setText(arrayList.get(i).getCustomerType());
        return view;
    }
}