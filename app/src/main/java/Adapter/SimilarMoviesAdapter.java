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

import Activities.VideoDetails;
import Model.AllMovies.SimilarMovie;


public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder> {

    Context context;
    List<SimilarMovie> moviesCastList;

    public SimilarMoviesAdapter(Context context, List<SimilarMovie> moviesCastList) {
        this.context = context;
        this.moviesCastList = moviesCastList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarMoviesAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.similarlmovies_recyler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(moviesCastList.get(position).getAvatar()).into(holder.image);
        holder.name.setText(moviesCastList.get(position).getTitle());
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
