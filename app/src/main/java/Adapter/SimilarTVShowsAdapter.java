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

import Model.SimilarMovie;
import Model.SimilarTvShows;


public class SimilarTVShowsAdapter extends RecyclerView.Adapter<SimilarTVShowsAdapter.ViewHolder> {

    Context context;
    List<SimilarTvShows> moviesCastList;

    public SimilarTVShowsAdapter(Context context, List<SimilarTvShows> moviesCastList) {
        this.context = context;
        this.moviesCastList = moviesCastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarTVShowsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.similarltvshows_recyler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(moviesCastList.get(position).getImageUrl()).into(holder.image);
        holder.name.setText(moviesCastList.get(position).getMovieName());
    }

    @Override
    public int getItemCount() {
        return moviesCastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
     ImageView image;
     TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image= itemView.findViewById(R.id.similarimage);
            name=itemView.findViewById(R.id.similarmovieimagename);
        }
    }
}
