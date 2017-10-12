package com.androidsweeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/4/2017.
 */

public class CardViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{

    public TextView countryName;
    public ImageView countryPhoto;
    public List<ItemObject> list;
    public Context context;
    RecyclerViewListener recyclerViewListener;

    public CardViewHolder2(View itemView, List<ItemObject> list, Context context) {
        super(itemView);
        this.list = list;
        this.context = context;
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
        countryName = (TextView)itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
    }
    public void removeAt(int position) {
        list.remove(position);
        //notifyItemRemoved(position);
        //notifyItemRangeChanged(position, list.size());

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.context, ImageFullScreen.class);
        intent.putExtra("image_id", this.list.get(getAdapterPosition()).getImageID());
        intent.putExtra("image_path", this.list.get(getAdapterPosition()).getPath());
        this.context.startActivity(intent);
        //Toast.makeText(view.getContext(), "Clicked Image Position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select Action");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 1, "Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String result = String.valueOf(item.getGroupId());
                Log.i("order", String.valueOf(item.getOrder()));
                Log.i("groupId", String.valueOf(item.getGroupId()));
                Log.i("menuInfo", String.valueOf(item.getMenuInfo()));
                Log.i("result2", String.valueOf(item.getItemId()));
                removeAt(getAdapterPosition());
                return true;
            }
        });

    }


}