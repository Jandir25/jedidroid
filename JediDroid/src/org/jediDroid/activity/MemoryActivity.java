package org.jediDroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MemoryActivity extends Activity {
	protected static final String LOG = "JediDroid - MemoryActivity";
	boolean[] destapades;
	
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.memory);


		/* Defino los listeners */
		MyListenerOnClick listener = new MyListenerOnClick();

		ImageView carta00 = (ImageView) findViewById(R.id.carta00);
		ImageView carta01 = (ImageView) findViewById(R.id.carta01);
		ImageView carta02 = (ImageView) findViewById(R.id.carta02);
		ImageView carta10 = (ImageView) findViewById(R.id.carta10);
		ImageView carta11 = (ImageView) findViewById(R.id.carta11);
		ImageView carta12 = (ImageView) findViewById(R.id.carta12);
		ImageView carta20 = (ImageView) findViewById(R.id.carta20);
		ImageView carta21 = (ImageView) findViewById(R.id.carta21);
		ImageView carta22 = (ImageView) findViewById(R.id.carta22);
		ImageView carta30 = (ImageView) findViewById(R.id.carta30);
		ImageView carta31 = (ImageView) findViewById(R.id.carta31);
		ImageView carta32 = (ImageView) findViewById(R.id.carta32);
		
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
		
		destapades = new boolean[12];
		
		inicializaValors();
	}
	
	/* MyListeners */

	protected class MyListenerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.carta00:
					destapades[0] = true;
					break;
					
				case R.id.carta01:
					destapades[1] = true;
					break;
									
					
				case R.id.carta02:
					destapades[2] = true;	
					break;		
									
				case R.id.carta10:
					destapades[3] = true;
					break;
					
				case R.id.carta11:
					destapades[4] = true;
					break;
					
				case R.id.carta12:
					destapades[5] = true;
					break;
					
				case R.id.carta20:
					destapades[6] = true;
					break;
					
				case R.id.carta21:
					destapades[7] = true;
					break;
					
				case R.id.carta22:
					destapades[8] = true;
					break;
					
				case R.id.carta30:
					destapades[9] = true;
					break;
					
				case R.id.carta31:
					destapades[10] = true;
					break;

				case R.id.carta32:
					destapades[11] = true;
					break;

				default:
					break;
			}

		}

	}
	
	/* Privades */
	
	private void inicializaValors() {
		for (int i = 0; i < destapades.length; ++i) {
			destapades[i] = false;
		}
	}

}
