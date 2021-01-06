package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import Model.AllMovies.Movie2;
import Model.AllTvshows.ModelTvShowDetail;
import Model.AllTvshows.Movie;

public class MovieItemRecylerViewAdapter extends RecyclerView.Adapter<MovieItemRecylerViewAdapter.ItemViewHolder> {
    Context context;
    List<Movie2> movie2CategoryItems;
    Integer Favourite;

    public MovieItemRecylerViewAdapter(Context context, List<Movie2> homeCategoryItemList) {
        this.context = context;
        this.movie2CategoryItems = homeCategoryItemList;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recyler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(movie2CategoryItems.get(position).getAvatar()).into(holder.itemimage);
        holder.MovieName.setText(movie2CategoryItems.get(position).getTitle());




//        if(Favourite.equals(0))
//        {
//
//            holder.favourite.setVisibility(View.VISIBLE);
//        }
//        if (Favourite.equals(1)) {
//            holder.favourite.setVisibility(View.VISIBLE);
//        }

        holder.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(context, VideoDetails.class);
//                i.putExtra("movieId", movieCategoryItems.get(position).getId());
//                i.putExtra("movieName", movieCategoryItems.get(position).getMovieName());
//                i.putExtra("movieImageUrl", movieCategoryItems.get(position).getImageUrl());
//                i.putExtra("movieFile", movieCategoryItems.get(position).getFileUrl());
//                context.startActivity(i);
                Movie2 userItem = movie2CategoryItems.get(position);
                Intent yourIntent = new Intent(context, VideoDetails.class);
                Bundle b = new Bundle();
                b.putSerializable("user", userItem);
                yourIntent.putExtras(b); //pass bundle to your intent
                context.startActivity(yourIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movie2CategoryItems.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimage,Watch,favourite;
        TextView MovieName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemimage=itemView.findViewById(R.id.item_image);
            MovieName=itemView.findViewById(R.id.movieimagename);
            Watch=itemimage.findViewById(R.id.watch);
            favourite=itemimage.findViewById(R.id.heart);




        }

    }


    @Override
    public int getItemViewType(int position) {

//        Integer Favourite=movie2CategoryItems.get(position).getArticleFavourite();
//        if(Favourite==1){
//
//
//        }

        return super.getItemViewType(position);

    }
}
