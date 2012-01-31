package org.jediDroid.activity;


import org.jediDroid.service.ReproductorService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;




public class ReproductorActivity extends Activity {
	protected static final String LOG = "JediDroid - ReproductorActivity";
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reproductor);
		
		/* Defino los listeners */
		MyListenerOnClick listener = new MyListenerOnClick();
		ImageView play = (ImageView) findViewById(R.id.buttonPlay);
		ImageView stop = (ImageView) findViewById(R.id.buttonStop);
		
		play.setOnClickListener(listener);
		stop.setOnClickListener(listener);
		
	}
	
	/* Listeners */
	protected class MyListenerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.buttonPlay) {
				Log.v(LOG, "Play");
				Intent intent = new Intent(getApplicationContext(), ReproductorService.class);
				startService(intent);
			}
			
			else if (v.getId() == R.id.buttonStop) {
				Log.v(LOG, "Stop");
				Intent intent = new Intent(getApplicationContext(), ReproductorService.class);
				stopService(intent);
			}
			
			
		}
		
	}
}
