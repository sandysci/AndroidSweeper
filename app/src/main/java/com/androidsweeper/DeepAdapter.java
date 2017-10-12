package com.androidsweeper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by USER on 10/3/2017.
 */
public class DeepAdapter extends RecyclerView.Adapter<DeepAdapter.DeepHolder>{
    List<ListItem> myList ;
    LayoutInflater inflater ;
    Context c;

    public DeepAdapter(List<ListItem> listdata ,Context mcontext){
        this.c = mcontext;
        this.myList = listdata;
        inflater = LayoutInflater.from(mcontext);

    }

    @Override
    public DeepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new DeepHolder(view);
    }

    @Override
    public void onBindViewHolder(DeepHolder holder, int position) {
        ListItem item = myList.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getIconid());


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class DeepHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView icon;
        private View container;
        public DeepHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tv_item_title);
            icon = (ImageView)itemView.findViewById(R.id.im_item_icon);
            container = itemView.findViewById(R.id.cont_item);
        }
    }
}
