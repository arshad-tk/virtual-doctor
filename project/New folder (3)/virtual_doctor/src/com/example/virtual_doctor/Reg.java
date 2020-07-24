package com.example.virtual_doctor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Reg extends Activity {
	
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
	RadioButton r1,r2,r3;
	Spinner l1;
	Button b1;
	String ip="";
	SharedPreferences sp;
	String dist[]={"kozhikode","malappurm","thrissur","palakkad","ernamkulam","wayanad","kottayam"};
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		  e1=(EditText)findViewById(R.id.editText1);
	      e2=(EditText)findViewById(R.id.editText2);
	      e3=(EditText)findViewById(R.id.editText3);
	      e4=(EditText)findViewById(R.id.editText4);
	      e5=(EditText)findViewById(R.id.editText5); 
	      e6=(EditText)findViewById(R.id.editText6);
	      e7=(EditText)findViewById(R.id.editText7);
	      e8=(EditText)findViewById(R.id.editText8);
	      e9=(EditText)findViewById(R.id.editText9);
	      r1=(RadioButton)findViewById(R.id.radioButton1);
	      r2=(RadioButton)findViewById(R.id.radioButton2);
	      r3=(RadioButton)findViewById(R.id.radioButton3);
	      l1=(Spinner)findViewById(R.id.spinner1);
	      b1=(Button)findViewById(R.id.button1);   
	      sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	      
	  		ArrayAdapter<String> ad=new ArrayAdapter<String>(Reg.this,android.R.layout.simple_spinner_item,dist);
           l1.setAdapter(ad);
	      b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
			final	String pname=e1.getText().toString();
			final	String page=e2.getText().toString();
			final	String pplace=e3.getText().toString();
			final	String ppost=e4.getText().toString();
			final	String ppin=e5.getText().toString();
			final	String pphone=e6.getText().toString();
			final	String pemail=e7.getText().toString();
			final	String pusername=e8.getText().toString();
			final	String ppassword=e9.getText().toString();
			final	String distr=l1.getSelectedItem().toString();
			final String gender;
			if (r1.isChecked())
			{
				gender=r1.getText().toString();
			}
			else if(r2.isChecked())
			{
				gender=r2.getText().toString();

			}
			else
			{
				gender=r3.getText().toString();

			}
			
		    if(pname.equalsIgnoreCase(""))
			{
				e1.setError("Enter Your Name");
			}
//		    else if(pname.matches("^[a-zA-Z]*$"))
//		    {
//		    	e1.setError("characters allowed");
//		    }
		    else if(page.equalsIgnoreCase(""))
			{
				e2.setError("Enter Your age");
			}
			
			else if(pplace.equalsIgnoreCase(""))
			{
				e3.setError("Enter Your Place");
			}
			else if(ppost.equalsIgnoreCase(""))
			{
				e4.setError("Enter Your post");
			}
			else if(ppin.equalsIgnoreCase(""))
			{
				e5.setError("Enter Your Pin");
			}
		    
			else if(ppin.length()!=6)
			{
				e5.setError("invalid pin");
				e5.requestFocus();
			

			}
			else if(pphone.equalsIgnoreCase(""))
			{
				e6.setError("Enter Your Phone No");
			}
			
			else if(pphone.length()<10)
			{
				e6.setError("Minimum 10 nos required");
				e6.requestFocus();
			

			}

			else if(pemail.equalsIgnoreCase(""))
			{
				e7.setError("Enter Your Email");
			}
			else if(!Patterns.EMAIL_ADDRESS.matcher(pemail).matches())
			{
				e7.setError("Enter Valid Email");
				e7.requestFocus();
			}
		    
			else if(pusername.equalsIgnoreCase(""))
			{
				e3.setError("Enter your username");
			}
			
			else if(ppassword.equalsIgnoreCase(""))
			{
				e9.setError("Enter Your Password");
			}
			
			else
			{




			

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Reg.this);
                String url ="http://"+sp.getString("ip", "")+":5000/reg";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++",response);
                                try {
                                    JSONObject json=new JSONObject(response);
                                    String res=json.getString("result");
                                    
                                    if(res.equals("ok"))
                                    {
                                    	  
                                        Toast.makeText(getApplicationContext(),"reg succes",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),Login.class));
                                    }
                                    else
                                    {
                                    	 Toast.makeText(getApplicationContext(),"reg fail",Toast.LENGTH_LONG).show();	
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
                        params.put("name", pname);
                        params.put("age", page);
                        params.put("gender", gender);
                        params.put("place", pplace);
                        params.put("post", ppost);
                        params.put("pin", ppin);
                        params.put("district", distr);
                        params.put("phone", pphone);
                        params.put("e_mail", pemail);
                        params.put("user_name", pusername);
                        params.put("password", ppassword);

                        return params;
                    }
                };
               // Add the request to the RequestQueue.
                queue.add(stringRequest);
				
				Intent i = new Intent(getApplicationContext(),Login.class);
				startActivity(i);
			}
			}
		});
	      
	      	  
	    		      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reg, menu);
		return true;
	}

}
