package com.example.virtual_doctor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Home extends Activity {
	
	Button b1,b2,b3,b4,b5,b6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	
		b1=(Button)findViewById(R.id.button1);  
		b2=(Button)findViewById(R.id.button2);  
		b3=(Button)findViewById(R.id.button3);  
		b4=(Button)findViewById(R.id.button4);  
		b5=(Button)findViewById(R.id.button5);  
		b6=(Button)findViewById(R.id.button6);  
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ViewSpecificationdepartmentActivity.class);
				startActivity(i);
			}
		});
		
        
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),DoctorViewActivity.class);
				startActivity(i);
			}
		});
		
		
	    b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),BookingActivity.class);
				startActivity(i);
			}
		});
	    

		
        b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ViewPrescriptionActivity.class);
				startActivity(i);
			}
		});
		
        b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ViewDeseaseActivity.class);
				startActivity(i);
			}
		});
		
        b6.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getApplicationContext(),FeedbackActivity.class);
			startActivity(i);
		}
	});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
