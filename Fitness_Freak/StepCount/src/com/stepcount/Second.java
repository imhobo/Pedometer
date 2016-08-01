package com.stepcount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Second extends Activity implements OnClickListener{
	
	
	EditText weight;
	
	private TextWatcher mTextWatcher = new TextWatcher() {

		@Override
	    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
	    }
	
	    @Override
	    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
	    }
	
	    @Override
	    public void afterTextChanged(Editable editable) {
	        // check Fields For Empty Values
	        checkFieldsForEmptyValues();
	    }
	};
	
	void checkFieldsForEmptyValues(){
		
		Button begin=(Button) findViewById(R.id.second);

	    String s1 = weight.getText().toString();
	    
	    if(s1.equals("")){
	        begin.setEnabled(false);
	    } else {
	        begin.setEnabled(true);
	        begin.setOnClickListener(this);
	    }
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		if (getIntent().getBooleanExtra("EXIT",false)) {
		    finish();
		}
		
		weight=(EditText) findViewById(R.id.editText2);
		weight.addTextChangedListener(mTextWatcher);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		EditText name=(EditText) findViewById(R.id.editText1);
		weight=(EditText) findViewById(R.id.editText2);
		String username=name.getText().toString();
		String userweight=weight.getText().toString();
		
		RadioGroup rg=(RadioGroup) findViewById(R.id.radioGroup1);
		RadioButton selectRadio = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        String gender = selectRadio.getText().toString();
		
		
		Intent intent=new Intent(this,MainActivity.class);
		intent.putExtra("Username", username);
		intent.putExtra("Userweight", userweight);
		intent.putExtra("Gender", gender);
		startActivity(intent);
		
	}

}
