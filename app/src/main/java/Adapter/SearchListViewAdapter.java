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
import Model.Search.Body;


public class SearchListViewAdapter extends RecyclerView.Adapter<SearchListViewAdapter .ViewHolder> {

    Context context;
    List<Body> moviesCastList;

    public SearchListViewAdapter (Context context, List<Body> moviesCastList) {
        this.context = context;
        this.moviesCastList = moviesCastList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchListViewAdapter .ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_list_row ,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(moviesCastList.get(position).getImage()).placeholder(R.drawable.loading).into(holder.image);
        holder.name.setText(moviesCastList.get(position).getName());
        holder.rating.setText(moviesCastList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return moviesCastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,rating;
        public ViewHolder(@NonNull View view) {
            super(view);
            image= (ImageView) view.findViewById(R.id.searchimage);
            rating = (TextView) view.findViewById(R.id.searchratingrating);

            name= (TextView) view.findViewById(R.id.searchtitle);
        }
    }
}
