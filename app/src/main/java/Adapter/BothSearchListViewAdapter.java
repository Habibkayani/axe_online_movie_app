package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.axe.R;

import java.util.List;

import Activities.TvShowDetail;
import Activities.VideoDetails;
import Model.AllSearch.Body3;


public class BothSearchListViewAdapter extends RecyclerView.Adapter<BothSearchListViewAdapter .ViewHolder> {

    Context context;
    List<Body3>bothsearchlist;
    String flag;
    public BothSearchListViewAdapter(Context context, List<Body3> moviesCastList) {
        this.context = context;
        this.bothsearchlist = moviesCastList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BothSearchListViewAdapter .ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_list_row ,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(bothsearchlist.get(position).getImage()).placeholder(R.drawable.loading).into(holder.image);
        holder.name.setText(bothsearchlist.get(position).getName());
        holder.rating.setText(bothsearchlist.get(position).getRating());






        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Body3 body = bothsearchlist.get(position);

                String flag=body.getFlag();
                if(flag.equals("article")){
                   // Toast.makeText(context,body.getId().toString(),Toast.LENGTH_LONG).show();

//                    Intent yourIntent = new Intent(context, VideoDetails.class);
//                    yourIntent.putExtra("allsearchtvarticleid",body.getId());//pass bundle to your intent
//                    context.startActivity(yourIntent);

                    Intent yourIntent = new Intent(context, VideoDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("allsearchtvarticleid", body);
                    yourIntent.putExtras(bundle); //pass bundle to your intent
                    context.startActivity(yourIntent);
                }
                else if(flag.equals("tvshow")){

                   // Toast.makeText(context,body.getId().toString(),Toast.LENGTH_LONG).show();
                    //Intent yourIntent = new Intent(context, TvShowDetail.class);
                       //yourIntent.putExtra("allsearchtvid",body.getId());
                 //pass bundle to your intent
                    //context.startActivity(yourIntent);

                    Intent yourIntent = new Intent(context, TvShowDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("allsearchtvid", body);
                    yourIntent.putExtras(bundle); //pass bundle to your intent
                    context.startActivity(yourIntent);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return bothsearchlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,rating;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View view) {
            super(view);
            image= (ImageView) view.findViewById(R.id.searchimage);
            rating = (TextView) view.findViewById(R.id.searchratingrating);
            name= (TextView) view.findViewById(R.id.searchtitle);
            progressBar=view.findViewById(R.id.progressBarxxxx);
        }
    }
}
