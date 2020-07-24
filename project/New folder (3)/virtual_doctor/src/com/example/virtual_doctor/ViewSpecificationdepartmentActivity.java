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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewSpecificationdepartmentActivity extends Activity {

	ListView l1;
	SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> department_name,description;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_specificationdepartment);
		
		l1=(ListView)findViewById(R.id.listView1);
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		 url ="http://"+sp.getString("ip", "")+":5000/viewdep";
		 
		 RequestQueue queue = Volley.newRequestQueue(ViewSpecificationdepartmentActivity.this);


	        // Request a string response from the provided URL.
	        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
	            @Override
	            public void onResponse(String response) {
	                // Display the response string.
	                Log.d("+++++++++++++++++",response);
	                try {

	                    JSONArray ar=new JSONArray(response);

	                    department_name=new ArrayList<String>();

	                    description=new ArrayList<String>();

	                    for(int i=0;i<ar.length();i++)
	                    {
	                        JSONObject jo=ar.getJSONObject(i);
	                        department_name.add(jo.getString("Dep_Name"));
	                        description.add(jo.getString("Description"));
	                    }

					//	ArrayAdapter<String> ad=new ArrayAdapter<String>(ViewSpecificationdepartmentActivity.this,android.R.layout.simple_spinner_item,department_name);
	                    l1.setAdapter(new Custom2(getApplicationContext(), department_name, description));

	                } catch (JSONException e) {
	                    e.printStackTrace();
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


//                 params.put("pid", sp.getString("id", ""));

                 return params;
             }
         };
        // Add the request to the RequestQueue.
         queue.add(stringRequest);
			
		 
		 
		 
	}

	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_specificationdepartment, menu);
		return true;
	}

}
