package org.jedi.jediDroid.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/* Ejemplo básico de un botón */

public class MenuActivity extends Activity{
	protected static final String debug = "MenuActivity";
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(debug, "onCreate");
		/* Definimos que no queremos que salga el titulo de la aplicación en la pantalla */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* Definimos que la aplicación será FULL SCREEN */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boton);
		
		Button b1 = (Button) findViewById(R.id.stAct1);
		MyListener m = new MyListener();
		b1.setOnClickListener(m);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.v(debug, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(debug, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(debug, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.v(debug, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(debug, "onDestroy");
		
		
	}
	
	
	
	/* Listeners */
	private class MyListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(getApplicationContext(), MenuActivity2.class));
		}
	}

}
