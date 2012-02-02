package org.jediDroid.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class JediDroidActivity extends TabActivity {
	static private TabHost mTabHost;
	private Resources mResources;
	static private Activity gActivity = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		mTabHost = getTabHost();       
       mResources = getResources();
       addTabCalculadora();
       addTabMusic();    
       addTabMemory();
       mTabHost.setCurrentTab(0);   
    }	
	
	private void addTabCalculadora() {
		Intent intent = new Intent(this, CalculadoraActivity.class);
		TabSpec spec = mTabHost.newTabSpec("Calculadora");
		spec.setIndicator("Calculadora", mResources.getDrawable(android.R.drawable.ic_menu_agenda));
		spec.setContent(intent);
		mTabHost.addTab(spec);
	}

	private void addTabMusic() {
		Intent intent = new Intent(this, ReproductorActivity.class);
		TabSpec spec = mTabHost.newTabSpec("Music");
		spec.setIndicator("Music", mResources.getDrawable(android.R.drawable.ic_menu_gallery));
		spec.setContent(intent);
		mTabHost.addTab(spec);
	}
	
	private void addTabMemory() {
		Intent intent = new Intent(this, MemoryActivity.class);
		TabSpec spec = mTabHost.newTabSpec("Memory");
		spec.setIndicator("Memory", mResources.getDrawable(android.R.drawable.ic_menu_gallery));
		spec.setContent(intent);
		mTabHost.addTab(spec);
	}
}

// public class JediDroidActivity extends Activity {
// /** Called when the activity is first created. */
// @Override
// public void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.main);
//
// startActivity(new Intent(getApplicationContext(),
// ReproductorActivity.class));
// }
// }