package com.example.virtual_doctor;

import java.util.HashMap;
import java.util.Map;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class BookingActivity extends Activity {

	EditText e1;
	
	Button b1;
	SharedPreferences sp;
	String did,url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking);
		
		  e1=(EditText)findViewById(R.id.editText5);

	      b1=(Button)findViewById(R.id.button1); 
	      sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	      did=getIntent().getStringExtra("did");  
	      
          url ="http://"+sp.getString("ip","")+":5000/booking";

	      b1.setOnClickListener(new View.OnClickListener(){
				
				@Override
				public void onClick(View arg0) {
					
					final	String date=e1.getText().toString();
               	 Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();	

					 // Instantiate the RequestQueue.
	                RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);


	                // Request a string response from the provided URL.
	                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
	                            @Override
	                            public void onResponse(String response) {
	                                // Display the response string.
	                                Log.d("+++++++++++++++++",response);
	                                try {
	                                    JSONObject json=new JSONObject(response);
	                                    String res=json.getString("task");
	                                    
	                                    if(res.equals("success"))
	                                    {
	                                    	  
	                                        Toast.makeText(getApplicationContext(),"booking succes",Toast.LENGTH_LONG).show();
	                                        startActivity(new Intent(getApplicationContext(),Home.class));
	                                    }
	                                    else
	                                    {
	                                    	 Toast.makeText(getApplicationContext(),"invalid",Toast.LENGTH_LONG).show();	
	                                    }
	                                  
	                                } catch (JSONException e) {
	                                    e.printStackTrace();
	                                  	 Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();	

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
	                        
	                        params.put("date", date);
	                        params.put("doctor_id", did);
	                        params.put("user_id", sp.getString("lid", ""));

	                        
                            return params;
                        }
                    };
                   // Add the request to the RequestQueue.
                    queue.add(stringRequest);

           
		}
	}); 
	}
}
