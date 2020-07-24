package com.example.virtual_doctor;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom2 extends BaseAdapter{

    private Context Context;

    ArrayList<String> a;
    ArrayList<String> b;



    public Custom2(Context applicationContext, ArrayList<String> a,ArrayList<String> b  ) {
        this.Context=applicationContext;
        this.a=a;
        this.b=b;
      
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertView==null)
        {
            gridView=new View(Context);
            gridView=inflator.inflate(R.layout.activity_custom2, null);

        }
        else
        {
            gridView=(View)convertView;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.textView1);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView2);





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);





        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(Context);
        tv1.setText(a.get(position));
        tv2.setText(b.get(position));




//        String url="http://"+sp.getString("ip", "")+":5000/static/upload/"+a.get(position);





        return gridView;

    }



}

