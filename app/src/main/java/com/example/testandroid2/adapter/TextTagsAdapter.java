package com.example.testandroid2.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.List;

/**
 * Created by Guge on 16/2/22.
 */
public class TextTagsAdapter extends TagsAdapter {

    private List<String> list;
    private Context context;

    public  TextTagsAdapter(Context context,List<String> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(100, 100);
        tv.setLayoutParams(lp);
        tv.setText("No." + position);
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("Click","Tag " + position + " clicked.");
            }
        });
        return tv;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getPopularity(int position) {
        return 0;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}
