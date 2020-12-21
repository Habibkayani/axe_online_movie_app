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

import Activities.SeeMore;
import Model.MovieAllCategory;
import Model.MovieCategoryItem;

public class MoviesMainRecylerAdapter extends RecyclerView.Adapter<MoviesMainRecylerAdapter.MainViewHolder> {
    Context context;
    List<MovieAllCategory> movieAllCategoryList;

    public MoviesMainRecylerAdapter(Context context, List<MovieAllCategory> homeAllCategoryList) {
        this.context = context;
        this.movieAllCategoryList = homeAllCategoryList;
    }

    @NonNull
    @Override
    public MoviesMainRecylerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recyler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesMainRecylerAdapter.MainViewHolder holder, int position) {
        holder.CategoryName.setText( movieAllCategoryList.get(position).getCatagoryTitle());
        setItemRecyler(holder.itemRecyler,  movieAllCategoryList.get(position).getMovieCategoryItemList());
        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, SeeMore.class);
                i.putExtra("cata",  movieAllCategoryList.get(position).getCatagoryTitle());

//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("StudentDetails", (ArrayList<? extends Parcelable>) allCategoryList.get(position).getCategoryItemList());
//                i.putExtras(bundle);

                //i.putExtra("PLACE", AllCategory);

                context.startActivity(i);
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
            CategoryName = itemView.findViewById(R.id.categoryname);
            itemRecyler = itemView.findViewById(R.id.item_recyler);
            seeMore=itemView.findViewById(R.id.seemore);


        }
    }

    private void setItemRecyler(RecyclerView recyclerView, List<MovieCategoryItem> homeCategoryItemList) {
       MovieItemRecylerViewAdapter homeItemRecylerViewAdapter = new  MovieItemRecylerViewAdapter(context, homeCategoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(homeItemRecylerViewAdapter);


    }
}
