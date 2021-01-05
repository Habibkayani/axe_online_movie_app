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
import Model.AllTvshows.Movie;
import Model.TvShowGenere.Datum;

public class TvGenereListViewAdapter extends RecyclerView.Adapter<TvGenereListViewAdapter.ItemViewHolder> {
    Context context;
    List<Datum> tvShowCategoryItems;

    public TvGenereListViewAdapter(Context context, List<Datum> homeCategoryItemList) {
        this.context = context;
        this.tvShowCategoryItems = homeCategoryItemList;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.tvgenererow,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(tvShowCategoryItems.get(position).getAvatar()).into(holder.itemimage);
        holder.tvgenereName.setText(tvShowCategoryItems.get(position).getTitle());
        holder.tvgenererating.setText(tvShowCategoryItems.get(position).getRating());
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

//                Movie userItem = tvShowCategoryItems.get(position);
//                Intent yourIntent = new Intent(context, TvShowDetail.class);
//                Bundle b = new Bundle();
//                b.putSerializable("user", userItem);
//                yourIntent.putExtras(b); //pass bundle to your intent
//                context.startActivity(yourIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowCategoryItems.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimage;
        TextView tvgenereName,tvgenererating;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

           itemimage=itemView.findViewById(R.id.tvgenereimage);
            tvgenereName=itemView.findViewById(R.id.tvgeneretitle);
            tvgenererating=itemView.findViewById(R.id.tvgenereratingrating);
        }
    }
}