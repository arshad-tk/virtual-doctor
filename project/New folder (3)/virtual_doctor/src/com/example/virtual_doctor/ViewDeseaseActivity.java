
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewDeseaseActivity extends Activity implements OnItemSelectedListener {

	ListView l1;
	String[]type={"normal","high"};
	Spinner s;
	SharedPreferences sp;
	ArrayList<String> disease,symptom;
	String ty;
    String url="",ip="";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_desease);
		l1=(ListView)findViewById(R.id.listView1);
		s=(Spinner)findViewById(R.id.spinner1);
		sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		ArrayAdapter<String> ad=new ArrayAdapter<String>(ViewDeseaseActivity.this,android.R.layout.simple_spinner_item,type);
         s.setAdapter(ad);
         s.setOnItemSelectedListener(ViewDeseaseActivity.this);
        

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_desease, menu);
		return true;
	}



@Override
public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
	// TODO Auto-generated method stub
	
		url ="http://"+sp.getString("ip", "")+":5000/viewsym";
	
		RequestQueue queue = Volley.newRequestQueue(ViewDeseaseActivity.this);

	    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
	        @Override
	        public void onResponse(String response) {
	            // Display the response string.
	            Log.d("+++++++++++++++++",response);
	            try {
	
	                JSONArray ar=new JSONArray(response);
	                
	                disease=new ArrayList<String>();
	
	                symptom=new ArrayList<String>();
	
	                for(int i=0;i<ar.length();i++)
	                {
	                    JSONObject jo=ar.getJSONObject(i);
	                   
	                    disease.add(jo.getString("disease"));
	                    symptom.add(jo.getString("symptom"));
	                }
	
						
						 l1.setAdapter(new Custom2(getApplicationContext(), disease, symptom));
	
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
	
	
	            params.put("ty", type[arg2]);
	            
	
	            return params;
	        }
	    };
	   // Add the request to the RequestQueue.
	    queue.add(stringRequest);
}

@Override
public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
	
}




}
