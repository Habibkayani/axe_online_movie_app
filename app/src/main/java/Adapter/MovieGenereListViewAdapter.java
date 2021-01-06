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

import java.io.Serializable;
import java.util.List;

import Activities.VideoDetails;
import Model.MovieGenere.Datum2;
import Model.TvShowGenere.Datum;


public class MovieGenereListViewAdapter extends RecyclerView.Adapter<MovieGenereListViewAdapter.ItemViewHolder> {
    Context context;
    List<Datum2> datum2List;

    public MovieGenereListViewAdapter(Context context, List<Datum2> homeCategoryItemList) {
        this.context = context;
        this.datum2List = homeCategoryItemList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.moviegenererow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(datum2List.get(position).getAvatar()).into(holder.itemimage);
        holder.tvgenereName.setText(datum2List.get(position).getTitle());
        holder.tvgenererating.setText(datum2List.get(position).getRating());
        holder.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //Intent i=new Intent(context, TvShowDetail.class);
//                i.putExtra("movieId", tvShowCategoryItems.get(position).getId());
//                i.putExtra("movieName", tvShowCategoryItems.get(position).getTitle());
//                i.putExtra("movieImageUrl", tvShowCategoryItems.get(position).getAvatar());
//                i.putExtra("movieRating", tvShowCategoryItems.get(position).getRating());
//
//
//
//
//                context.startActivity(i);

                Datum2 userItem = datum2List.get(position);
                Intent intent = new Intent(context, VideoDetails.class);
                intent.putExtra("Iddd", userItem.getArticleId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datum2List.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimage;
        TextView tvgenereName, tvgenererating;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemimage = itemView.findViewById(R.id.moviegenereimage);
            tvgenereName = itemView.findViewById(R.id.moviegeneretitle);
            tvgenererating = itemView.findViewById(R.id.moviegenereratingrating);
        }
    }
}