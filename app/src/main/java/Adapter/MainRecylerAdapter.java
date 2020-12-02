package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.axe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Activities.SeeMore;
import Model.AllCategory;
import Model.CategoryItem;

public class MainRecylerAdapter extends RecyclerView.Adapter<MainRecylerAdapter.MainViewHolder> {
    Context context;
    List<AllCategory> allCategoryList;

    public MainRecylerAdapter(Context context, List<AllCategory> allCategoryList) {
        this.context = context;
        this.allCategoryList = allCategoryList;
    }

    @NonNull
    @Override
    public MainRecylerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recyler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecylerAdapter.MainViewHolder holder, int position) {
        holder.CategoryName.setText(allCategoryList.get(position).getCatagoryTitle());
        setItemRecyler(holder.itemRecyler, allCategoryList.get(position).getCategoryItemList());
        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, SeeMore.class);
                i.putExtra("cata",allCategoryList.get(position).getCatagoryTitle());
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("StudentDetails", (ArrayList<? extends Parcelable>) allCategoryList.get(position).getCategoryItemList());
//                i.putExtras(bundle);

                i.putExtra("PLACE", AllCategory);
                startActivity(intent);


                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
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

    private void setItemRecyler(RecyclerView recyclerView, List<CategoryItem> categoryItemList) {
        ItemRecylerViewAdapter itemRecylerViewAdapter = new ItemRecylerViewAdapter(context, categoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(itemRecylerViewAdapter);

    }
}
