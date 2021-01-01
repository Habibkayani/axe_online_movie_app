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

import Model.AllTvshows.Episode;
import player.PlayerActivity;

public class EpisodeRecylerViewAdapter extends RecyclerView.Adapter<EpisodeRecylerViewAdapter.ItemViewHolder> {
    Context context;
    List<Episode> categoryItemList;

    public EpisodeRecylerViewAdapter(Context context, List<Episode> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.episodes_recyler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(categoryItemList.get(position).getAvatar()).into(holder.itemimage);
        holder.EpisodeNumber.setText(String.valueOf(categoryItemList.get(position).getEpisodeNumber()));
        holder.EpisodeName.setText(categoryItemList.get(position).getName());
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent i=new Intent(context, PlayerActivity.class);
                i.putExtra("link",categoryItemList.get(position).getLink());
//                i.putExtra("movieName",categoryItemList.get(position).getMovieName());
//                i.putExtra("movieImageUrl",categoryItemList.get(position).getImageUrl());
//                i.putExtra("movieFile",categoryItemList.get(position).getFileUrl());
              context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimage,play;
        TextView EpisodeNumber,EpisodeName;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemimage=itemView.findViewById(R.id.episodeimage);
            EpisodeNumber=itemView.findViewById(R.id.episodename);
            EpisodeName=itemView.findViewById(R.id.episodedescription);
            play=itemView.findViewById(R.id.playyyyy);
        }
    }
}
