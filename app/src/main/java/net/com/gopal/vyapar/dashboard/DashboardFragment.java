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
import net.com.gopal.vyapar.product.ProductActivity;

import org.jetbrains.annotations.NotNull;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    private LinearLayout customerLayout, productLayout,companyLayout;

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
        companyLayout = view.findViewById(R.id.companyLayout);
        customerLayout.setOnClickListener(this);
        productLayout.setOnClickListener(this);
        companyLayout.setOnClickListener(this);


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
            case R.id.companyLayout:
                Intent intent2=new Intent(requireActivity(), CompanyActivity.class);
                startActivity(intent2);
                break;
        }
    }
}