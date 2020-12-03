package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.axe.R;

import java.util.List;

import Model.MoviesCast;
import Model.TvShowsCast;
import de.hdodenhof.circleimageview.CircleImageView;


public class TvShowCastAdapter extends RecyclerView.Adapter<TvShowCastAdapter.ViewHolder> {

    Context context;
    List<TvShowsCast> moviesCastList;

    public TvShowCastAdapter(Context context, List<TvShowsCast> moviesCastList) {
        this.context = context;
        this.moviesCastList = moviesCastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TvShowCastAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cast_recyler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(moviesCastList.get(position).getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return moviesCastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       CircleImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image= itemView.findViewById(R.id.profile_image);
        }
    }
}
