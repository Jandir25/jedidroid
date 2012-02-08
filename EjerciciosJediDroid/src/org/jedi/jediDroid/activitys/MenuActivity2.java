package org.jedi.jediDroid.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/* Ejemplo básico de un botón */

public class MenuActivity2 extends Activity {
	private static final String debug = "MenuActivity2";
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(debug, "onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.barracolores);
		Button b1 = (Button) findViewById(R.id.button2);
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
			startActivity(new Intent(getApplicationContext(), JediDroidActivity.class));
		}
	}
}
