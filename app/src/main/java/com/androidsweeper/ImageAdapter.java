package com.androidsweeper;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;



/**
 * Created by USER on 9/26/2017.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Cursor cursor;
    private int columnIndex;
    public ImageAdapter(Context c) {

        mContext = c;
        // column to return
        final String[] projection = {MediaStore.Images.Thumbnails.IMAGE_ID,MediaStore.Images.Thumbnails._ID};

        cursor = mContext.getContentResolver().query( MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection, null, null, MediaStore.Images.Thumbnails._ID +" DESC");
//        showDialogBox("cusorobject", String.valueOf(cursor.getCount()));
//        Log.i("projection", String.valueOf(cursor.getCount()));
        // Array position for _ID in projection array
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
        //showDialogBox("column Index", String.valueOf(columnIndex));

    }
    public int getCount() {
        return cursor.getCount();
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }


            cursor.moveToPosition(position);

            // Get the current value for the requested column
            int imageID = cursor.getInt(columnIndex);
          //  showDialogBox("ImageIds +"+imageID, String.valueOf(imageID));
//            showDialogBox("UriImage",Uri.withAppendedPath(
//                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID).toString());
            imageView.setImageURI(Uri.withAppendedPath(
                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID));

        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };

    private void showDialogBox(String title ,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Photo!");
        builder.setMessage(message);
        builder.setTitle(title);
        builder.show();
    }

}
