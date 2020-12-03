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
import Model.MovieCategoryItem;
import Model.MovieCategoryItem;

public class MovieItemRecylerViewAdapter extends RecyclerView.Adapter<MovieItemRecylerViewAdapter.ItemViewHolder> {
    Context context;
    List<MovieCategoryItem> movieCategoryItems;


    public MovieItemRecylerViewAdapter(Context context, List<MovieCategoryItem> homeCategoryItemList) {
        this.context = context;
        this.movieCategoryItems = homeCategoryItemList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recyler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(movieCategoryItems.get(position).getImageUrl()).into(holder.itemimage);
        holder.MovieName.setText(movieCategoryItems.get(position).getMovieName());
        holder.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, VideoDetails.class);
                i.putExtra("movieId", movieCategoryItems.get(position).getId());
                i.putExtra("movieName", movieCategoryItems.get(position).getMovieName());
                i.putExtra("movieImageUrl", movieCategoryItems.get(position).getImageUrl());
                i.putExtra("movieFile", movieCategoryItems.get(position).getFileUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieCategoryItems.size();
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
