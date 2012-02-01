package org.jediDroid.activity;


import org.jediDroid.service.ReproductorService;
import org.jediDroid.service.ReproductorService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;




public class ReproductorActivity extends Activity {
	protected static final String LOG = "JediDroid - ReproductorActivity";
	protected ReproductorService bService;
	protected boolean bound;
	
	private ServiceConnection connection = new ServiceConnection() {
		
		/* Funcion por si el servicio se desconecta inesperadamente */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			bound = false;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MyBinder binder = (MyBinder) service;
			bService = binder.getService();
			bound = true;
		}
	};
	
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
		
		
		/* Tenemos que hacer el Start y la asociacion porque hemos hecho el servicio mixto */
		/* Start service */
		Intent intent = new Intent(getApplicationContext(), ReproductorService.class);
		startService(intent);
		
		/* Me asocio al servicio */
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
		
	}
	
	/* Listeners */
	protected class MyListenerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			ImageView play = (ImageView) findViewById(R.id.buttonPlay);
			if (v.getId() == R.id.buttonPlay) {
				Log.v(LOG, "buttonPlay");
				if(bService.isPlaying()) {
					bService.pause();
					play.setImageResource(R.drawable.ic_menu_play);
				}
				else {
					play.setImageResource(R.drawable.ic_menu_pause);
					bService.play();
				}				
			}
			
			else if (v.getId() == R.id.buttonStop) {
				Log.v(LOG, "buttonStop");
				play.setImageResource(R.drawable.ic_menu_play);
				bService.stop();
				/* Desasociarme */
//				if (bound) {
//					unbindService(connection);
//					bound = false;
//				}
//				
//				/* Ahora destruimos el servicio ya que lo hemos hecho mixto */
//				Intent intent = new Intent(getApplicationContext(), ReproductorService.class);
//				stopService(intent);
			}
			
			
		}
		
	}
}
