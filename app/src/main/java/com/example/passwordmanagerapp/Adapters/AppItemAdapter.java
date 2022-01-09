package com.example.passwordmanagerapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanagerapp.Models.AppItem;
import com.example.passwordmanagerapp.R;

import java.util.List;

public class AppItemAdapter extends RecyclerView.Adapter<AppItemAdapter.ViewHolder> {

    private Context context;
    List<AppItem> itemsList;
    private OnItemListener monItemListener;

    public AppItemAdapter(Context context, List<AppItem> itemsList, OnItemListener onItemListener){
        this.context = context;
        this.itemsList = itemsList;
        this.monItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_design, parent, false);

        return new AppItemAdapter.ViewHolder(view,monItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AppItem item = itemsList.get(position);
        holder.appNameTextView.setText(item.getAppName());
        holder.accountTextView.setText(item.getAccount());
        holder.imageViewAppIcons.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_missing_image));

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView appNameTextView;
        public TextView accountTextView;
        public ImageView imageViewAppIcons;
        OnItemListener onItemListener;


        public ViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);

            appNameTextView = itemView.findViewById(R.id.appNameTextView);
            accountTextView = itemView.findViewById(R.id.accountTextView);
            imageViewAppIcons = itemView.findViewById(R.id.imageViewAppIcons);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onNoteClick(int position);
    }

}
