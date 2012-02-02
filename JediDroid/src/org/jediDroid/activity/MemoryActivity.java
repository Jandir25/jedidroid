package org.jediDroid.activity;

import org.jediDroid.activity.CalculadoraActivity.MyListenerOnClick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MemoryActivity extends Activity {
	protected static final String LOG = "JediDroid - MemoryActivity";
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.memory);


		/* Defino los listeners */
		MyListenerOnClick listener = new MyListenerOnClick();

		TextView carta00 = (TextView) findViewById(R.id.carta00);
		TextView carta01 = (TextView) findViewById(R.id.carta01);
		TextView carta02 = (TextView) findViewById(R.id.carta02);
		TextView carta10 = (TextView) findViewById(R.id.carta10);
		TextView carta11 = (TextView) findViewById(R.id.carta11);
		TextView carta12 = (TextView) findViewById(R.id.carta12);
		TextView carta20 = (TextView) findViewById(R.id.carta20);
		TextView carta21 = (TextView) findViewById(R.id.carta21);
		TextView carta22 = (TextView) findViewById(R.id.carta22);
		TextView carta30 = (TextView) findViewById(R.id.carta30);
		TextView carta31 = (TextView) findViewById(R.id.carta31);
		TextView carta32 = (TextView) findViewById(R.id.carta32);
		
		carta00.setOnClickListener(listener);
		carta01.setOnClickListener(listener);
		carta02.setOnClickListener(listener);
		carta10.setOnClickListener(listener);
		carta11.setOnClickListener(listener);
		carta12.setOnClickListener(listener);
		carta20.setOnClickListener(listener);
		carta21.setOnClickListener(listener);
		carta22.setOnClickListener(listener);
		carta30.setOnClickListener(listener);
		carta31.setOnClickListener(listener);
		carta32.setOnClickListener(listener);
	}
	
	/* MyListeners */

	protected class MyListenerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			TextView inputResult = (TextView) findViewById(R.id.inputResult);

		}

	}

}
