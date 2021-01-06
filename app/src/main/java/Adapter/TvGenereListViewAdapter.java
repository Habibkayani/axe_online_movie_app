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
import Model.AllTvshows.Movie;
import Model.MovieGenere.Datum2;
import Model.TvShowGenere.Datum;

public class TvGenereListViewAdapter extends RecyclerView.Adapter<TvGenereListViewAdapter.ItemViewHolder> {
    Context context;
    List<Datum> datumList;

    public TvGenereListViewAdapter(Context context, List<Datum> homeCategoryItemList) {
        this.context = context;
        this.datumList = homeCategoryItemList;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.tvgenererow,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(datumList.get(position).getAvatar()).into(holder.itemimage);
        holder.tvgenereName.setText(datumList.get(position).getTitle());
        holder.tvgenererating.setText(datumList.get(position).getRating());
        holder.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Datum userItem = datumList.get(position);
                Intent intent = new Intent(context, TvShowDetail.class);
                intent.putExtra("Idddd",userItem.getId());
                context.startActivity(intent);

//                yourIntent.putExtra("TvshowGenre",TvGenere.getId());
                //pass bundle to your intent


            }
        });
    }

    @Override
    public int getItemCount() {
        return datumList.size();
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