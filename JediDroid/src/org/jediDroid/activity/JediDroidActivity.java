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
       addTabUno();
       addTabDos();        
       mTabHost.setCurrentTab(0);   
    }	private void addTabUno() {
		Intent intent = new Intent(this, CalculadoraActivity.class);
		TabSpec spec = mTabHost.newTabSpec("Texto Tab1");
		spec.setIndicator("Calculadora", mResources.getDrawable(android.R.drawable.ic_menu_agenda));
		spec.setContent(intent);
		mTabHost.addTab(spec);
	}

	private void addTabDos() {
		Intent intent = new Intent(this, ReproductorActivity.class);
		TabSpec spec = mTabHost.newTabSpec("Texto Tab2");
		spec.setIndicator("Music", mResources.getDrawable(android.R.drawable.ic_menu_gallery));
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