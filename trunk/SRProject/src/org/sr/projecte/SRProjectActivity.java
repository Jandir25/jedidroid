package org.sr.projecte;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SRProjectActivity extends Activity {
    /** Called when the activity is first created. */
	private String CONSUMER_KEY = "MGW3iFIWES96mRIdHq7Apw";
	private String CONSUMER_SECRET = "N9sXUF3wpVJ3ePGwnqzPRBmdOpMPpYJexgLhJ8jnKM";
	public static final String PREF_NAME = "MYPREFFILE";
	OAuthConsumer consumer;
    OAuthProvider provider;
    private String ACCES_KEY;
    private String ACCES_SECRET;
    final Activity parentActivity = this; 

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	/* Enable full screen mode */
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     	
        Button bLog = (Button) findViewById(R.id.ButtonLog);
        // Afegim listener al button
        MyOnClickListener mOCL = new MyOnClickListener();
        bLog.setOnClickListener(mOCL);
        
      
    }
    
    
    void Oath_login()
    {

    	// Autentificacio
    	 consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET); 
         provider = new DefaultOAuthProvider(
        		  "https://api.twitter.com/oauth/request_token",
        		  "https://api.twitter.com/oauth/access_token",
        		  "https://api.twitter.com/oauth/authorize");
      
        String authUrl;
		try { 
			// Permet que la appi es conecti a twitter
			
			authUrl = provider.retrieveRequestToken(consumer, "srproject://twitter");
			// Utilitzem el intent per mostrar la finestra de login
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		
    }
//        
    @Override
    protected void onNewIntent(Intent intent) {
	// TODO Auto-generated method stub
	
    	super.onNewIntent(intent);
    	Uri uri = intent.getData();
    	Log.v("Twitter","DERP New INTENT");
    	if (uri != null && uri.toString().startsWith("srproject://twitter"))
    	{
    			// Cal obtenir els tokens que twitter ens asigna
    			String pinCode = uri.getQueryParameter("oauth_verifier");
    			Log.v("Twitter","The pincode"+ pinCode);
    			try 
    			{
    				provider.retrieveAccessToken(consumer,pinCode);
				} catch (Exception e) {}
    			
    			ACCES_KEY = consumer.getToken();
    			ACCES_SECRET = consumer.getTokenSecret();
    		   
    			// Necesito el nom de l'usuari!
    			
    			Log.v("Twitter",ACCES_KEY + "^_^" + ACCES_SECRET);
    			 
    			AccessToken a = new AccessToken(ACCES_KEY,ACCES_SECRET);
    			Log.v("Twitter","NEW ACCES");
    		 
    		 
		        Twitter twitter = new TwitterFactory().getInstance();
		        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		        twitter.setOAuthAccessToken(a);
		        Log.v("Twitter","NEW FACTORY");
		        
		        try {
					String user = twitter.getScreenName();
					
					SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_WORLD_WRITEABLE);
			        SharedPreferences.Editor prefsEditor = prefs.edit();

			        prefsEditor.putString("nom_user", user);
			        prefsEditor.putString("clau_user",ACCES_KEY);
			        prefsEditor.putString("clau_secret", ACCES_SECRET);
			        prefsEditor.commit();

					Log.v("Twitter","ALL COMPLETE");
					startActivity(new Intent(parentActivity, MenuActivity.class)); 
					
				} catch (Exception e) {}
    	} else {
			Log.v("Twitter","Digam que no entras aqui siusplau");

    		
    	}
    }
        
    
    class MyOnClickListener implements OnClickListener
    {

		@Override
		public void onClick(View v) 
		{
		
		        Oath_login();
	        	
	        }
			
		}
    	
    }
