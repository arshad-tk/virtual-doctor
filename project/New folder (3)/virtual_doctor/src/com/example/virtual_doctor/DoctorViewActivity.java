package com.example.virtual_doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;

public class DoctorViewActivity extends Activity implements OnItemSelectedListener,OnItemClickListener {

	ListView l1;
	SharedPreferences sp;
    String url="",ip="",docid;
    Spinner s1;
    ArrayList<String> dname,did, doc_name,phno,doc_id;
	String dptid="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_view);
		 l1=(ListView)findViewById(R.id.listView1);
	        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	        s1= (Spinner)findViewById(R.id.spinner1);
	        l1.setOnItemClickListener(DoctorViewActivity.this);
	        ip=sp.getString("ip","");

	        url ="http://"+ip+":5000/viewdep";


	        // Instantiate the RequestQueue.
	        RequestQueue queue = Volley.newRequestQueue(DoctorViewActivity.this);


	        // Request a string response from the provided URL.
	        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
	            @Override
	            public void onResponse(String response) {
	                // Display the response string.
	                Log.d("+++++++++++++++++",response);
	                try {

	                    JSONArray ar=new JSONArray(response);

	                    dname=new ArrayList<String>();
	                    did=new ArrayList<String>();

	                    for(int i=0;i<ar.length();i++)
	                    {
	                        JSONObject jo=ar.getJSONObject(i);
	                        dname.add(jo.getString("Dep_Name"));
	                        did.add(jo.getString("id"));
	                    }

		            	ArrayAdapter<String> ad=new ArrayAdapter<String>(DoctorViewActivity.this,android.R.layout.simple_spinner_item,dname);
		                s1.setAdapter(ad);
	                    s1.setOnItemSelectedListener(DoctorViewActivity.this);

	                } catch (JSONException e) {
	                    e.printStackTrace();

	                }


	            }
	        }, new Response.ErrorListener() {
	            @Override
	            public void onErrorResponse(VolleyError error) {

	                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
	            }
	        });
	        // Add the request to the RequestQueue.
	        queue.add(stringRequest);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_view, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		dptid=dname.get(arg2);

        String url2 = "http://"+ip+":5000/dr_view";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(DoctorViewActivity.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    
                   doc_id=new ArrayList<String>();
                   doc_name=new ArrayList<String>();
                   phno=new ArrayList<String>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        doc_id.add(jo.getString("did"));
                        doc_name.add(jo.getString("doctor_name"));
                        phno.add(jo.getString("contact_number"));
                    }
                    l1.setAdapter(new Custom2(DoctorViewActivity.this, doc_name, phno));
                    	l1.setOnItemClickListener(DoctorViewActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Eeeeeee"+e,Toast.LENGTH_LONG).show();
                    
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                
                params.put("deptid", dptid);
               

                return params;
            }
        };
        queue.add(stringRequest);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		docid=doc_id.get(arg2);
		Intent i=new Intent(getApplicationContext(),BookingActivity.class);
		i.putExtra("did", docid);
		startActivity(i);
	}

}
