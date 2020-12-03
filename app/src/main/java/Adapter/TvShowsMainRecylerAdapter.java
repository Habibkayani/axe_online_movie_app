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
import Model.MovieCategoryItem;
import Model.TvShowAllCategory;
import Model.TvShowCategoryItem;

public class TvShowsMainRecylerAdapter extends RecyclerView.Adapter<TvShowsMainRecylerAdapter.MainViewHolder> {
    Context context;
    List<TvShowAllCategory> tvShowAllCategories;

    public TvShowsMainRecylerAdapter(Context context, List<TvShowAllCategory> tvShowAllCategories) {
        this.context = context;
        this.tvShowAllCategories = tvShowAllCategories;
    }

    @NonNull
    @Override
    public TvShowsMainRecylerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recyler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowsMainRecylerAdapter.MainViewHolder holder, int position) {
        holder.CategoryName.setText(tvShowAllCategories.get(position).getCatagoryTitle());
        setItemRecyler(holder.itemRecyler, tvShowAllCategories.get(position).getTvShowCategoryItemList());
        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, SeeMore.class);
                i.putExtra("cata", tvShowAllCategories.get(position).getCatagoryTitle());

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
        return tvShowAllCategories.size();
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

    private void setItemRecyler(RecyclerView recyclerView, List<TvShowCategoryItem> homeCategoryItemList) {
        TvShowsItemRecylerViewAdapter homeItemRecylerViewAdapter = new TvShowsItemRecylerViewAdapter(context, homeCategoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(homeItemRecylerViewAdapter);

    }
}
