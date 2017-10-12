package com.androidsweeper;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/4/2017.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder>  {

    Context mycontext ;
    List<ItemObject> mylist;
    public CardViewAdapter(Context c , List<ItemObject> mylist){
        this.mycontext = c;
        this.mylist = mylist;

    }
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        CardViewHolder rcv = new CardViewHolder(layoutView,this.mylist,this.mycontext);
        return rcv;
    }

    public int countCheck(){
        int total = 0;
        for( int i = 0 ; i < mylist.size(); i ++){
            if(mylist.get(i).isChecked() == true){
                total = total+1;
            }
        }
        return total;

    }
    public List<String> getFilePaths(){
        List<String> pathList = new ArrayList<>();
        int total = 0;
        for( int i = 0 ; i < mylist.size(); i ++){
            if(mylist.get(i).isChecked() == true){
                pathList.add(mylist.get(i).getPath());
            }
        }
        return pathList;

    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        holder.countryPhoto.setImageBitmap(decodeSampledBitmapFromFile(mylist.get(position).getPath(), 100, 100));
       // holder.imagecheckbox.setVisibility(mylist.get(holder.getAdapterPosition()).isVisible() ? View.VISIBLE : View.GONE);

        //in some cases, it will prevent unwanted situations
        holder.imagecheckbox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        holder.imagecheckbox.setChecked(mylist.get(position).isChecked());

        holder.imagecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mylist.get(holder.getAdapterPosition()).setIsChecked(isChecked);
                notifyDataSetChanged();
                Toast.makeText(mycontext, String.valueOf(countCheck())+" total checked", Toast.LENGTH_LONG).show();

            }
        });

    }
    public static Bitmap decodeSampledBitmapFromFile(String path,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inScaled = false;
        return BitmapFactory.decodeFile(path, options);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView countryName;
        public ImageView countryPhoto;
        public List<ItemObject> list;
        public Context context;
        public CheckBox imagecheckbox;
        CardView cardview;
        RecyclerViewListener recyclerViewListener;

        public CardViewHolder(View itemView,List<ItemObject> list,Context context) {
            super(itemView);
            this.list = list;
            this.context = context;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            //itemView.setOnCreateContextMenuListener(this);
            cardview = (CardView)itemView.findViewById(R.id.card_view);
            countryName = (TextView)itemView.findViewById(R.id.country_name);
            countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
            imagecheckbox = (CheckBox)itemView.findViewById(R.id.imagecheckbox);
        }
        public void UpdateGallery(String filePath) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(filePath))));
        }
        public void removeAt(int position) {
            File file = new File(list.get(position).getPath());
            if(file.exists()){
               String path = file.getAbsolutePath();
                list.remove(position);
                notifyItemRemoved(position);
                deleteFileFromMediaStore(context.getContentResolver(), file);
                notifyItemRangeChanged(position, list.size());
                notifyDataSetChanged();
            }else{
                Log.i("FileError","File not Found");
            }
        }
        public  void deleteFileFromMediaStore(final ContentResolver contentResolver, File file) {
            String canonicalPath;
            try {
                canonicalPath = file.getCanonicalPath();
            } catch (IOException e) {
                canonicalPath = file.getAbsolutePath();
            }
            final Uri uri = MediaStore.Files.getContentUri("external");
            final int result = contentResolver.delete(uri,
                    MediaStore.Files.FileColumns.DATA + "=?", new String[] {canonicalPath});
            if (result == 0) {
                final String absolutePath = file.getAbsolutePath();
                if (!absolutePath.equals(canonicalPath)) {
                    contentResolver.delete(uri,
                            MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
                }
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(this.context, ImageFullScreen.class);
            intent.putExtra("image_id", this.list.get(getAdapterPosition()).getImageID());
            intent.putExtra("image_path", this.list.get(getAdapterPosition()).getPath());
            this.context.startActivity(intent);
            //Toast.makeText(view.getContext(), "Clicked Image Position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }


//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            imagecheckbox.setVisibility(View.VISIBLE);
//            menu.setHeaderTitle("Select Action");
//            menu.add(0, v.getId(), 0, "Edit");
//            menu.add(0, v.getId(), 1, "Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    removeAt(getAdapterPosition());
//                    return true;
//                }
//            });

        //}


        @Override
        public boolean onLongClick(View v) {
            imagecheckbox.setVisibility(View.VISIBLE);
            return false;
        }
    }

}
