package net.com.gopal.vyapar.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.company.CompanyActivity;
import net.com.gopal.vyapar.customer.CustomerActivity;
import net.com.gopal.vyapar.invoice.InvoiceActivity;
import net.com.gopal.vyapar.product.ProductActivity;
import net.com.gopal.vyapar.supplier.SupplierActivity;

import org.jetbrains.annotations.NotNull;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    private LinearLayout customerLayout, productLayout, companyLayout, SupplierLayout, invoice, list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerLayout = view.findViewById(R.id.customerLayout);
        productLayout = view.findViewById(R.id.productLayout);
        SupplierLayout = view.findViewById(R.id.SupplierLayout);
        companyLayout = view.findViewById(R.id.companyLayout);
        invoice = view.findViewById(R.id.invoice);
        list = view.findViewById(R.id.list);
        customerLayout.setOnClickListener(this);
        productLayout.setOnClickListener(this);
        companyLayout.setOnClickListener(this);
        SupplierLayout.setOnClickListener(this);
        invoice.setOnClickListener(this);
        list.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customerLayout:
                Intent intent = new Intent(requireActivity(), CustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.productLayout:
                Intent intent1 = new Intent(requireActivity(), ProductActivity.class);
                startActivity(intent1);
                break;
            case R.id.companyLayout:
                Intent intent2 = new Intent(requireActivity(), CompanyActivity.class);
                startActivity(intent2);
                break;
            case R.id.SupplierLayout:
                Intent intent3 = new Intent(requireActivity(), SupplierActivity.class);
                startActivity(intent3);
                break;
            case R.id.invoice:
                Intent intent4 = new Intent(requireActivity(), InvoiceActivity.class);
                startActivity(intent4);
                break;
//            case R.id.list:
//                Intent intent5 = new Intent(requireActivity(), InvoiceActivity.class);
//                startActivity(intent5);
//                break;
        }
    }
}