package com.example.virtual_doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewPrescriptionActivity extends Activity {
	
	ListView l1;
	SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> disease_name,priscription;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_prescription);
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		l1=(ListView)findViewById(R.id.listView1);
		
		
		 url ="http://"+sp.getString("ip", "")+":5000/viewpres";
		 
		 // Instantiate the RequestQueue.
	        RequestQueue queue = Volley.newRequestQueue(ViewPrescriptionActivity.this);


	        // Request a string response from the provided URL.
	        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
	            @Override
	            public void onResponse(String response) {
	                // Display the response string.
	                Log.d("+++++++++++++++++",response);
	                try {

	                    JSONArray ar=new JSONArray(response);

	                    disease_name=new ArrayList<String>();

	                    priscription=new ArrayList<String>();

	                    for(int i=0;i<ar.length();i++)
	                    {
	                        JSONObject jo=ar.getJSONObject(i);
	                        disease_name.add(jo.getString("disease"));
	                        priscription.add(jo.getString("priscriptn"));
	                    }

//						ArrayAdapter<String> ad=new ArrayAdapter<String>(ViewPrescriptionActivity.this,android.R.layout.simple_spinner_item,disease_name);
	                     l1.setAdapter(new Custom2(getApplicationContext(), disease_name, priscription));

	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }


	            }
	        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();


                    params.put("pid", sp.getString("lid", ""));
                    

                    return params;
                }
            };
           // Add the request to the RequestQueue.
            queue.add(stringRequest);
			

	}

	public boolean onCreateOptionsMenu1(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_view, menu);
		return true;
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_prescription, menu);
		return true;
	}

}
