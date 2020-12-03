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
import Model.HomeAllCategory;
import Model.HomeCategoryItem;

public class HomeMainRecylerAdapter extends RecyclerView.Adapter<HomeMainRecylerAdapter.MainViewHolder> {
    Context context;
    List<HomeAllCategory> homeAllCategoryList;

    public HomeMainRecylerAdapter(Context context, List<HomeAllCategory> homeAllCategoryList) {
        this.context = context;
        this.homeAllCategoryList = homeAllCategoryList;
    }

    @NonNull
    @Override
    public HomeMainRecylerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recyler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMainRecylerAdapter.MainViewHolder holder, int position) {
        holder.CategoryName.setText(homeAllCategoryList.get(position).getCatagoryTitle());
        setItemRecyler(holder.itemRecyler, homeAllCategoryList.get(position).getHomeCategoryItemList());
        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, SeeMore.class);
                i.putExtra("cata", homeAllCategoryList.get(position).getCatagoryTitle());

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
        return homeAllCategoryList.size();
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

    private void setItemRecyler(RecyclerView recyclerView, List<HomeCategoryItem> homeCategoryItemList) {
        HomeItemRecylerViewAdapter homeItemRecylerViewAdapter = new HomeItemRecylerViewAdapter(context, homeCategoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(homeItemRecylerViewAdapter);

    }
}
