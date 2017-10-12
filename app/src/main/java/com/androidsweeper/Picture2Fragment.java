package com.androidsweeper;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Picture2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Picture2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Picture2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Picture2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Picture2Fragment newInstance(String param1, String param2) {
        Picture2Fragment fragment = new Picture2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Picture2Fragment() {
        // Required empty public constructor
    }
    public LinearLayoutManager checkScreenSize(){
        LinearLayoutManager lLayout;
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            lLayout = new GridLayoutManager(getActivity(),4);
            return lLayout;
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            lLayout = new GridLayoutManager(getActivity(),2);
            return lLayout;
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_SMALL) {
            lLayout = new GridLayoutManager(getActivity(),2);
            return lLayout;
        }
        else {
            lLayout = new GridLayoutManager(getActivity(),4);
            return lLayout;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture2, container, false);
        final CheckBox imagecheckbox = (CheckBox)view.findViewById(R.id.imagecheckbox);

        LinearLayoutManager lLayout = checkScreenSize();

        RecyclerView rView = (RecyclerView)view.findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        CardViewAdapter rcAdapter = new CardViewAdapter(getActivity(), getAllShownImagesPath(getActivity()));
        rView.setAdapter(rcAdapter);
        // Inflate the layout for this fragment
        return view;
    }
    private ArrayList<ItemObject> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name,imageID;
        ArrayList<ItemObject> imageList =  new ArrayList<ItemObject>();
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        ArrayList<Integer> listOfAllImages2 = new ArrayList<Integer>();
        String absolutePathOfImage = null;
        int absolutePathOfImage2 ;
        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Thumbnails._ID };

        cursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, MediaStore.Images.Thumbnails._ID + " DESC");
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        imageID = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
        while (cursor.moveToNext()) {

            imageList.add(new ItemObject(cursor.getString(column_index_data),cursor.getInt(imageID),false,true));

        }


        return imageList;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
