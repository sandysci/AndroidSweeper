package com.androidsweeper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class VideoFragment extends Fragment {

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    static final int GALLERY_REQUEST = 2;
    static final int RESULT_OK= -1;
    public RecyclerView recyclerView;
    public DeepAdapter deepAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.recview);
        // set Layout Manager it can be Grid or Linear
        Log.i("ActivityInfo",getActivity().toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        deepAdapter = new DeepAdapter(Information.getData(),getActivity());
        recyclerView.setAdapter(deepAdapter);

         //Inflate the layout for this fragment
        return view ;
    }
    private void pickImage(){
        Environment.getExternalStorageDirectory();
        Intent photoIntent = new Intent(Intent.ACTION_PICK);
        photoIntent.setType("image/*");
        startActivityForResult(photoIntent, GALLERY_REQUEST);
    }
    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri dataURI;
        Log.i("Phone1", String.valueOf(requestCode));
        Log.i("Phone2",String.valueOf(resultCode));
        Log.i("Phone3",String.valueOf(data));
        if(resultCode == RESULT_OK){
            Log.i("info","i am here");
            if(requestCode == GALLERY_REQUEST){
                Log.i("info1","i am here");
                dataURI = data.getData();
                InputStream inputstream ;
                try {
                    inputstream = getActivity().getContentResolver().openInputStream(dataURI);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputstream);
                }catch (FileNotFoundException ex){
                    Toast.makeText(getActivity(),"Cant open image",Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

                Toast.makeText(getActivity(),dataURI.toString(),Toast.LENGTH_LONG).show();
            }
            if(requestCode == PICK_CONTACT_REQUEST){
                Log.i("info3","i am here");
                 dataURI = data.getData();
                Toast.makeText(getActivity(),dataURI.toString(),Toast.LENGTH_LONG).show();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)

                // Consider using CursorLoader to perform the query.
                Cursor cursor = getActivity().getContentResolver()
                        .query(dataURI, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
            }
        }

    }







}
