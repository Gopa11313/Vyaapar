package net.com.gopal.vyapar.folder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.com.gopal.vyapar.R;

import java.util.ArrayList;

public class LoadFoldersAdapter extends RecyclerView.Adapter<LoadFoldersAdapter.LoadFolderViewHolder> {

    private Context context;
    private ArrayList<Folder> products;
    private LoadFundOnClickListener listener;

    public LoadFoldersAdapter(Context context, ArrayList<Folder> products, LoadFundOnClickListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LoadFolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);
        return new LoadFolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadFolderViewHolder holder, final int position) {

        holder.icon.setImageResource(products.get(position).getIcon());
        holder.title.setText(products.get(position).getName());
        holder.description.setText(products.get(position).getDescription());
        holder.loadFundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadFundlistener(Integer.parseInt(products.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class LoadFolderViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView title, description;
        private RelativeLayout loadFundLayout;

        public LoadFolderViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.loadFundImageView);
            title = itemView.findViewById(R.id.loadFundTitle);
            loadFundLayout = itemView.findViewById(R.id.loadFundLayout);
            description = itemView.findViewById(R.id.loadFundDescription);
        }
    }

    public interface LoadFundOnClickListener {
        void loadFundlistener(int position);
    }
}
