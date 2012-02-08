package org.jediDroid.activity;


import org.jediDroid.service.ReproductorService;
import org.jediDroid.service.ReproductorService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/* Activity del reproductor */

public class ReproductorActivity extends Activity {
	
	protected static final String LOG = "JediDroid - ReproductorActivity";
	protected ReproductorService bService;
	/* Variable que nos indica si estamos conectados o no */
	protected boolean bound;
	
	private ServiceConnection connection = new ServiceConnection() {
		
		/* Funcion por si el servicio se desconecta inesperadamente */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			bound = false;
		}
		
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v(LOG, "onServiceConnected");
			MyBinder binder = (MyBinder) service;
			bService = binder.getService();
			bound = true;
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(LOG, "onCreate");
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
		/* Se tiene que poner 'getApplicationContext()' para que busque 
		 * el 'bindService' en todo el contexto de la aplicacion y no solo en el contexto de la activity.
		 * 
		 * Como tenemos TABS hay decirle al 'bindService' que 
		 * el servicio que busca esta en la aplicacion. Si no se lo pones, solo buscara en el contexto de la Activity
		 * y queremos que busque en todo el contexto de la aplicacion.
		 */
		getApplicationContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v(LOG, "onResume");
		/* Permitimos el giro de pantalla */
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
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
