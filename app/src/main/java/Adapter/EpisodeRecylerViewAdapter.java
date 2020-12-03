package Adapter;

import android.content.Context;
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

import Model.EpisodeItem;

public class EpisodeRecylerViewAdapter extends RecyclerView.Adapter<EpisodeRecylerViewAdapter.ItemViewHolder> {
    Context context;
    List<EpisodeItem> categoryItemList;

    public EpisodeRecylerViewAdapter(Context context, List<EpisodeItem> categoryItemList) {
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
        Glide.with(context).load(categoryItemList.get(position).getImageUrl()).into(holder.itemimage);
        holder.EpisodeName.setText(categoryItemList.get(position).getEpisodeName());
        holder.description.setText(categoryItemList.get(position).getEpisode_description());
        holder.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(context, VideoDetails.class);
//                i.putExtra("movieId",categoryItemList.get(position).getId());
//                i.putExtra("movieName",categoryItemList.get(position).getMovieName());
//                i.putExtra("movieImageUrl",categoryItemList.get(position).getImageUrl());
//                i.putExtra("movieFile",categoryItemList.get(position).getFileUrl());
//                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimage;
        TextView EpisodeName,description;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemimage=itemView.findViewById(R.id.episodeimage);
            EpisodeName=itemView.findViewById(R.id.episodename);
            description=itemView.findViewById(R.id.episodedescription);
        }
    }
}
