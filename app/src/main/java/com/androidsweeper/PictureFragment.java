package com.androidsweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;


public class PictureFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        GridView gridview = (GridView)view.findViewById(R.id.picture_gridview);
        //gridview.setAdapter(new ImageAdapter(getActivity()));
        ListAdapter selectedAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(selectedAdapter);




        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position +"---"+id+"==="+parent,
                        Toast.LENGTH_LONG).show();
            }
        });
        // Inflate the layout for this fragment
        return  view;

    }
    private void showDialogBox(String title ,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setMessage(message);
        builder.setTitle(title);
        builder.show();
    }




}
