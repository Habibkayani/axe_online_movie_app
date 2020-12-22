package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import Activities.TvShowDetail;
import Activities.VideoDetails;
import Model.Movie;
import Model.Movie;

public class TvShowsItemRecylerViewAdapter extends RecyclerView.Adapter<TvShowsItemRecylerViewAdapter.ItemViewHolder> {
    Context context;
    List<Movie> tvShowCategoryItems;

    public TvShowsItemRecylerViewAdapter(Context context, List<Movie> homeCategoryItemList) {
        this.context = context;
        this.tvShowCategoryItems = homeCategoryItemList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recyler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(tvShowCategoryItems.get(position).getAvatar()).into(holder.itemimage);
        holder.MovieName.setText(tvShowCategoryItems.get(position).getTitle());
        holder.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i=new Intent(context, TvShowDetail.class);
//                i.putExtra("movieId", tvShowCategoryItems.get(position).getId());
//                i.putExtra("movieName", tvShowCategoryItems.get(position).getTitle());
//                i.putExtra("movieImageUrl", tvShowCategoryItems.get(position).getAvatar());
//                i.putExtra("movieRating", tvShowCategoryItems.get(position).getRating());
//
//
//
//
//                context.startActivity(i);

                Movie userItem = tvShowCategoryItems.get(position);
                Intent yourIntent = new Intent(context,TvShowDetail.class);
                Bundle b = new Bundle();
                b.putSerializable("user", userItem);
                yourIntent.putExtras(b); //pass bundle to your intent
                context.startActivity(yourIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowCategoryItems.size();
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
