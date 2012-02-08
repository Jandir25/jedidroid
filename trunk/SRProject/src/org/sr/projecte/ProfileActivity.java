package org.sr.projecte;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

public class ProfileActivity extends TabActivity 
{

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.profile);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    
	    intent = new Intent().setClass(this, UserActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("profile").setIndicator("Profile",
	                      res.getDrawable(R.drawable.kingicon))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    intent = new Intent().setClass(this, GpsActivity.class);

	    spec = tabHost.newTabSpec("gps").setIndicator("Gps",
                res.getDrawable(R.drawable.gpsicon))
            .setContent(intent);
	    tabHost.addTab(spec);
		intent = new Intent().setClass(this, RankingActivity.class);
		
		spec = tabHost.newTabSpec("ranking").setIndicator("Ranking",
		        res.getDrawable(R.drawable.rankingicon))
		    .setContent(intent);
		tabHost.addTab(spec);

	    tabHost.setCurrentTab(1);
	    
	    
	}
    

    	
    	
 
}
