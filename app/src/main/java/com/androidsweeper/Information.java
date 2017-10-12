package com.androidsweeper;

import android.app.LauncherActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/3/2017.
 */
public class Information {
    private static final String[] titles = {
            "Nothing is found here",
            "i am a beginner",
            "I want to build my confidence",
            "God is my strength"};
    private static final String[] subtitles = {
            "Nothing",
            "beginner",
            "confidence",
            "strength"};
    private static final int[] icons = {
            android.R.drawable.ic_btn_speak_now,
            android.R.drawable.ic_dialog_info,
            android.R.drawable.ic_delete,
            android.R.drawable.ic_input_add };


    public static List<ListItem> getData(){
        List<ListItem> data = new ArrayList<>();
        for(int i = 0 ; i<10 ; i++){
            for(int j = 0; j < titles.length && j< icons.length ; j++){
                ListItem item = new ListItem();
                item.setTitle(titles[j]);
                item.setIconid(icons[j]);
                data.add(item);
            }
        }
        return  data;
    }
}
