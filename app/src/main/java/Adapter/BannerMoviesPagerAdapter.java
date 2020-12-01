package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.axe.R;


import java.util.List;

import Activities.VideoDetails;
import Model.BannerMovies;

public class BannerMoviesPagerAdapter extends PagerAdapter {
    private static final String TAG = "TAG";
    Context context;
    List<BannerMovies> bannerMoviesList;

    public BannerMoviesPagerAdapter(Context context, List<BannerMovies> bannerMoviesList) {
        this.context = context;
        this.bannerMoviesList = bannerMoviesList;
    }

    @Override
    public int getCount() {
        return bannerMoviesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
       return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie_layout, null);
        ImageView bannerimage = view.findViewById(R.id.banner_image);
//        RequestOptions options = new RequestOptions()
//                .format(DecodeFormat.PREFER_RGB_565)
//                .circleCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .priority(Priority.NORMAL)
//                .diskCacheStrategy(DiskCacheStrategy.NONE);
    Glide.with(context).load(bannerMoviesList.get(position).getImageUrl()).into(bannerimage);
   // Picasso.get().load(bannerMoviesList.get(position).getImageUrl()).into(bannerimage);

        //Glide.with(context).load(bannerMoviesList.get(position).getImageUrl()).thumbnail(0.05f).transition(DrawableTransitionOptions.withCrossFade()).fitCenter().centerInside().into(bannerimage);
        container.addView(view);
        bannerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, VideoDetails.class);
                i.putExtra("movieId",bannerMoviesList.get(position).getId());
                i.putExtra("movieName",bannerMoviesList.get(position).getMovieName());
                i.putExtra("movieImageUrl",bannerMoviesList.get(position).getImageUrl());
                i.putExtra("movieFile",bannerMoviesList.get(position).getFileUrl());
                context.startActivity(i);

            }
        });
        return view;

    }
}