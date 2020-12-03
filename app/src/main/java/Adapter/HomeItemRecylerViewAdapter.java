package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.axe.R;


import java.util.List;

import Activities.VideoDetails;
import Model.HomeCategoryItem;

public class HomeItemRecylerViewAdapter extends RecyclerView.Adapter<HomeItemRecylerViewAdapter.ItemViewHolder> {
    Context context;
    List<HomeCategoryItem> homeCategoryItemList;

    public HomeItemRecylerViewAdapter(Context context, List<HomeCategoryItem> homeCategoryItemList) {
        this.context = context;
        this.homeCategoryItemList = homeCategoryItemList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recyler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(homeCategoryItemList.get(position).getImageUrl()).into(holder.itemimage);
        holder.MovieName.setText(homeCategoryItemList.get(position).getMovieName());
        holder.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, VideoDetails.class);
                i.putExtra("movieId", homeCategoryItemList.get(position).getId());
                i.putExtra("movieName", homeCategoryItemList.get(position).getMovieName());
                i.putExtra("movieImageUrl", homeCategoryItemList.get(position).getImageUrl());
                i.putExtra("movieFile", homeCategoryItemList.get(position).getFileUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCategoryItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimage;
        TextView MovieName;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemimage=itemView.findViewById(R.id.item_image);
            MovieName=itemView.findViewById(R.id.movieimagename);
        }
    }
}
