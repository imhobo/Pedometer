package com.stepcount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener, OnClickListener {
	

private SensorManager mSensorManager; 
private Sensor mAccelerometer; 

final private float STEP_MALE=0.76f;
final private float STEP_FEMALE=0.67f;

final private float A=0.0195f;
final private float B=0.00436f;
final private float C=0.00245f;
private float D;

private int count=0;
private float high=-1;
private float low=100;
private float distance=0;
private float speed=0;
private float time=0;
private float calories=0;
private boolean sex;
private String username;
private String userweight;
private static Context CONTEXT;
private float init_time=0;
private float cur_time=0;

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    CONTEXT = this;
    
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    
    Bundle extras=getIntent().getExtras();
    if(extras!=null)
    {
    	username=extras.getString("Username");
    	username+=" is a douchebag!";	//Only for fun purposes..try printing any name..let me do it for you
    	Log.i("Username",username);		//There you go :P
    	userweight=extras.getString("Userweight");
    	String gender=extras.getString("Gender");
    	if(gender.equals("Male"))sex=true;
    	else sex=false;
    	
    	float W=Float.parseFloat(userweight)*2.2f;	//Convert weight to float and in pounds
    	float SOME_RANDOM_CONSTANT= (float) Math.pow(W/154,0.454f);
    	D=(float)0.000801*SOME_RANDOM_CONSTANT/W;
    	
    }
    
    //UI for this activity
    
    DisplayMetrics dm = new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(dm);
	final int width=dm.widthPixels;
	//final int height=dm.heightPixels;
	
    Button reset=(Button) findViewById(R.id.third);
	reset.setOnClickListener(this);
	reset.setWidth(width/4);
	reset.setX((width/3)-(width/8));
	
	Button quit=(Button) findViewById(R.id.fourth);
	quit.setOnClickListener(this);
	quit.setWidth(width/4);
	quit.setX((width/3)-(width/8));
	

}

protected void onResume() {
    super.onResume();
    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
}

protected void onPause() {
super.onPause();
mSensorManager.unregisterListener(this);
}


public static Context getContext() {
    return CONTEXT;
}

/**
 * onShake callback
 */
public void onShake(float force) {
    Toast.makeText(this, "Phone shaked : " + force, Toast.LENGTH_SHORT).show();
}


@Override
public void onSensorChanged(SensorEvent event) {
	// TODO Auto-generated method stub
	
	TextView tvdis= (TextView)findViewById(R.id.dis);
	TextView tvspeed= (TextView)findViewById(R.id.textView1);
	TextView tvtime= (TextView)findViewById(R.id.textView2);
	TextView tvcal= (TextView)findViewById(R.id.textView3);
	TextView tvs= (TextView)findViewById(R.id.steps);
	
	float x = event.values[0];
	float y = event.values[1];
	float z = event.values[2];
	//float g = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	float l = (float) Math.sqrt(x*x + y*y + z*z);
	
	if(l>high){
		high=l;
	}
	if(l<low){
		low=l;
	}
	
	if(l>14f)count++;
	
	if(sex)distance=count*STEP_MALE;
	else distance=count*STEP_FEMALE;
	
	cur_time=SystemClock.elapsedRealtime();
	if(init_time!=0)time=(cur_time-init_time)/1000;
	else init_time=cur_time;
	
	speed=distance/time;
	
	float V=speed/1.6f;	//Convert speed to mph
	calories= A + B*V + C*V*V + D*V*V*V;
	calories*=2.2;		//Convert from calories/lbs-min to calories/kg-min
	
	if(V==0)calories=0;
	
	tvs.setText("Steps  : "+Float.toString(count));
	tvdis.setText(getResources().getString(R.string.Distance)+Float.toString(distance));
	tvspeed.setText(getResources().getString(R.string.Speed)+Float.toString(speed));
	tvtime.setText(getResources().getString(R.string.Time)+Float.toString(time));
	tvcal.setText(getResources().getString(R.string.Calories)+Float.toString(calories));
	
}

@Override
public void onAccuracyChanged(Sensor sensor, int accuracy) {
	// TODO Auto-generated method stub
	
}

void initialize()
{
	distance=0;
	speed=0;
	count=0;
	calories=0;
	time=0;
	init_time=0;
	cur_time=0;
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch(v.getId())
	{
		case R.id.third:
			
			initialize();
			break;
		
		case R.id.fourth:
			
			Intent intent = new Intent(this, Second.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("EXIT", true);
			startActivity(intent);
			
			break;
			
	
	
	
	}
}

 }