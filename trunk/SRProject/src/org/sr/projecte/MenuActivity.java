package org.sr.projecte;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class MenuActivity extends Activity {
	

	
	private final String LOG = "Menu";

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	/* Enable full screen mode */
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.menu);
 
        ImageView menuCalc = (ImageView) findViewById(R.id.menu_calc);
        ImageView menuGame = (ImageView) findViewById(R.id.menu_game);
        ImageView menuMusic = (ImageView) findViewById(R.id.menu_music);
        ImageView menuProfile = (ImageView) findViewById(R.id.menu_profile);

        MyImageClick mIC = new MyImageClick();
        menuCalc.setOnTouchListener(mIC);
        menuGame.setOnTouchListener(mIC);
        menuMusic.setOnTouchListener(mIC);
        menuProfile.setOnTouchListener(mIC);


    }
    
    final Activity parentActivity = this; 

    public class MyImageClick implements OnTouchListener
    {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
				case R.id.menu_calc: 
					Log.v(LOG, "Calculadora");
	                startActivity(new Intent(parentActivity, CalculadoraActivity.class));
					break;
				case R.id.menu_game: 
					Log.v(LOG, "Game");
	                startActivity(new Intent(parentActivity, MemoryActivity.class)); 
					break;
				case R.id.menu_music: 
					Log.v(LOG, "Music");
	                startActivity(new Intent(parentActivity, ReproductorActivity.class)); 
					break;
				case R.id.menu_profile: 
					Log.v(LOG, "Profile");
	                startActivity(new Intent(parentActivity, ProfileActivity.class)); 

				default:
			}
			return false;
		}
    }
}