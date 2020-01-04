package com.example.submission3fix;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private ArrayList<ItemsData> datas = new ArrayList<>();
    private onItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(ItemsAdapter.onItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<ItemsData> datas){
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.bind(datas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(datas.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profil;
        TextView title, desc, datePublish;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profil      = itemView.findViewById(R.id.iv_item_list);
            title       = itemView.findViewById(R.id.tv_title_list);
            desc        = itemView.findViewById(R.id.tv_desc_list);
            datePublish = itemView.findViewById(R.id.tv_date_list);
        }

        void bind(ItemsData itemsData){

            Glide.with(itemView.getContext())
                    .load(itemsData.getProfile())
                    .apply(new RequestOptions())
                    .into(profil);

            title.setText(itemsData.getTitle());
            datePublish.setText(itemsData.getDatePublish());
            //check apakah disediakan overview
            desc.setText(itemsData.getDesc().isEmpty() ? itemView.getResources().getString(R.string.not_supported) : itemsData.getDesc());
        }

    }

    interface onItemClickCallback{
        void onItemClicked(int id);
    }
}
