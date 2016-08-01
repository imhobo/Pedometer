package com.stepcount;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class First extends Activity implements OnClickListener {
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	 
		int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.welcome);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		final int width=dm.widthPixels;
		final int height=dm.heightPixels;
		
		Log.i("WIDTH", width+"");
		Log.i("HEIGHT", height+"");
		
		Log.i("Level","1" );
		ImageView logo=(ImageView) findViewById(R.id.imageView1);
		Bitmap icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.msit_logo);
		icon=getResizedBitmap(icon,(int)(1.18f*width/3),width/3);
		logo.setTranslationX(width/2-width/6);
		logo.setTranslationY(height/12);
		logo.setImageBitmap(icon);
		
		Log.i("Level","2" );
		TextView created=(TextView) findViewById(R.id.textView1);
		TextView name1=(TextView) findViewById(R.id.textView2);
		TextView name2=(TextView) findViewById(R.id.textView3);
		
		Log.i("Level","3" );
		created.setX(width/16);
		created.setY(0.2f*height);
		name1.setX(width/16);
		name1.setY(0.25f*height);
		name2.setX(width/16);
		name2.setY(0.25f*height);
		

		Log.i("Level","4" );
		Button enter=(Button) findViewById(R.id.first);
		enter.setOnClickListener(this);
		enter.setWidth(width/4);
		enter.setX((width/2)-(width/8));
		enter.setY(0.4f*height);
		
		Log.i("Level","5" );
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent intent=new Intent(this,Second.class);
		startActivity(intent);
		finish();
	}

}
