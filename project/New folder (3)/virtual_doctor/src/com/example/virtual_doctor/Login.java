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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

public class Login extends Activity {
	EditText e1,e2;
	Button b1,b2;
	String ip="192.168.43.58";
	SharedPreferences sp;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button1);   
        b2=(Button)findViewById(R.id.button2); 
        sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
        SharedPreferences.Editor ed=sp.edit();
        ed.putString("ip", ip);
        ed.commit();
	    b1.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				final	String pusername=e1.getText().toString();
				final	String ppassword=e2.getText().toString();
				
				if(pusername.equalsIgnoreCase(""))
				{
					e1.setError("Enter your username");
				}
				
				else if(ppassword.equalsIgnoreCase(""))
				{
					e2.setError("Enter Your Password");
				}
				else{
				
				 // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                String url ="http://"+ip+":5000/login";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++",response);
                                try {
                                    JSONObject json=new JSONObject(response);
                                    String res=json.getString("result");
                                    
                                    if(res.equals("invalid"))
                                    {
                                    	 Toast.makeText(getApplicationContext(),"invalid",Toast.LENGTH_LONG).show();	
                                      }
                                    else
                                    {
                                    	 SharedPreferences.Editor ed=sp.edit();
                                         ed.putString("lid", res);
                                         ed.commit();
                                        Toast.makeText(getApplicationContext(),"login succes",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),Home.class));
                                   
                                    	 }
                                  
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
                                params.put("uname", pusername);
                                params.put("pass", ppassword);
                               
                                return params;
                            }
                        };
                       // Add the request to the RequestQueue.
                        queue.add(stringRequest);
				}
               
			}
		}); 
      b2.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),Reg.class);
				startActivity(i);
			}
		}); 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
