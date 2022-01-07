package net.com.gopal.vyapar.invoice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.invoice.model.Invoice;
import net.com.gopal.vyapar.invoice.model.InvoiceItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    ArrayList<InvoiceItem> invoiceItem;
    private ClickCallBack clickCallBack;

    public InvoiceAdapter(ArrayList<InvoiceItem> invoiceItem, ClickCallBack clickCallBack) {
        this.invoiceItem = invoiceItem;
        this.clickCallBack = clickCallBack;
    }

    @NonNull
    @NotNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new InvoiceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InvoiceViewHolder holder, int position) {
        InvoiceItem invoice = invoiceItem.get(position);
        holder.description.setText("");
        holder.discount.setText(invoice.getDiscount());
        holder.name.setText(invoice.getItemName());
        holder.qty.setText(invoice.getQuantity());
        holder.rate.setText(invoice.getRate());
        holder.total.setText(invoice.getTotal());
    }

    @Override
    public int getItemCount() {
        return invoiceItem.size();
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView name, description, discount, qty, rate, total;

        public InvoiceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            description=itemView.findViewById(R.id.description);
            discount=itemView.findViewById(R.id.discount);
            qty=itemView.findViewById(R.id.qty);
            rate=itemView.findViewById(R.id.rate);
            total=itemView.findViewById(R.id.total);
        }
    }

    public interface ClickCallBack {
        void onClick(Invoice invoice);
    }
}
