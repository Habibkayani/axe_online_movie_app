package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.axe.R;

import java.util.List;

import Activities.MovieSeeMore;
import Activities.SeeMore;
import Model.AllMovies.Movie2;
import Model.AllMovies.PostMovies;
import Model.AllTvshows.PostTVShows;

public class MoviesMainRecylerAdapter extends RecyclerView.Adapter<MoviesMainRecylerAdapter.MainViewHolder> {
    Context context;
    List<PostMovies> movieAllCategoryList;



    public MoviesMainRecylerAdapter(Context context, List<PostMovies> listofcatgeory) {
        this.context = context;
        this.movieAllCategoryList = listofcatgeory;
    }

    @NonNull
    @Override
    public MoviesMainRecylerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_movie_recyler_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesMainRecylerAdapter.MainViewHolder holder, int position) {

        holder.CategoryName.setText( movieAllCategoryList.get(position).getCategory());
        setItemRecyler(holder.itemRecyler,  movieAllCategoryList.get(position).getMovie2());






        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //i.putExtra("cata", tvShowAllCategories.get(position).getCatagoryTitle());

                PostMovies userItem = movieAllCategoryList.get(position);
                Intent yourIntent = new Intent(context,MovieSeeMore.class);
                yourIntent.putExtra("item2",userItem.getCategory());
                context.startActivity(yourIntent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return  movieAllCategoryList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView CategoryName,seeMore;
        RecyclerView itemRecyler;


        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryName = itemView.findViewById(R.id.moviecategoryname);
            itemRecyler = itemView.findViewById(R.id.movie_item_recyler);
            seeMore=itemView.findViewById(R.id.movieseemore);


        }
    }

    private void setItemRecyler(RecyclerView recyclerView, List<Movie2> homeCategoryItemList) {
        MovieItemRecylerViewAdapter homeItemRecylerViewAdapter = new  MovieItemRecylerViewAdapter(context, homeCategoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(homeItemRecylerViewAdapter);


    }

}
