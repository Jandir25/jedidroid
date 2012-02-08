package org.sr.projecte;

import java.io.IOException;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class GpsActivity extends Activity  {
    private String GPS = "GPS";
    List <Address> adress;
	public static final String PREF_NAME = "MYPREFFILE";
	private String CONSUMER_KEY = "MGW3iFIWES96mRIdHq7Apw";
	private String CONSUMER_SECRET = "N9sXUF3wpVJ3ePGwnqzPRBmdOpMPpYJexgLhJ8jnKM";
    private String LastPos;
    LocationManager myLocationManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gps);
            
            
            myLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    
            /* Definimos los listeners tanto para loa ubicacion mediante red como mediante GPS */
            myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            ImageView Twitt = (ImageView) findViewById(R.id.TweetGps);
            Twitt.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					tweet_gps("Tweeting from "+LastPos+" using SrProject.");
					show_toast("Checked in!");
					return false;
				}
			});
            adress = null;
            
    }
    void show_toast(CharSequence text)
    {
 		Context context = getApplicationContext();
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	myLocationManager.removeUpdates(locationListener);
    	
    };
    @Override
    protected void onResume() {
    	super.onResume();
        myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    };
    
    
    
    
    
	private void tweet_gps(final String text)
	{
		
		Runnable run = new Runnable() {
			
			@Override
			public void run() {


				// Setejem l'estatus
				SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_WORLD_WRITEABLE);
				String ACCES_TOKEN = prefs.getString("clau_user", null);
				String ACCES_TOKEN_SECRET = prefs.getString("clau_secret", null);

		    	AccessToken a = new AccessToken(ACCES_TOKEN, ACCES_TOKEN_SECRET);
		    	
		        Twitter twitter = new TwitterFactory().getInstance();
		        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		        twitter.setOAuthAccessToken(a);
		        
		        
		        try {
		        	Log.v("LOG",twitter.getScreenName());
					twitter4j.Status status = twitter.updateStatus(text);
					Log.v("LOG",twitter.getScreenName());
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		
		Thread t = new Thread(run);
		t.start();
		
	}
    
    
    LocationListener locationListener = new LocationListener() {
            
            /* Por si cambia el estado, por ejemplo, pierde la localicacion */
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                    
                    
            }
            
            /* Si activa el elemento de localizacion (GPS o Red) */
            @Override
            public void onProviderEnabled(String provider) {
                    // TODO Auto-generated method stub
                    
            }
            
            /* Si desactiva el elemento de localizacion (GPS o Red) */
            @Override
            public void onProviderDisabled(String provider) {
                    // TODO Auto-generated method stub
                    
            }
            
            
            
            /* Si la localizacion cambia segun los parametros que le hemos puesto en el listener */
            @Override
            public void onLocationChanged(Location location) {
            		String lat = (Double)location.getLatitude()+"";
            		String lon = (Double)location.getLatitude()+"";
                    Log.v(GPS, "Latitud: " + ((Double)location.getLatitude()).toString());
                    Log.v(GPS, "Longitud: " + ((Double)location.getLongitude()).toString());
                    TextView latG = (TextView) findViewById(R.id.LatitudeGPS);
                    latG.setText(lat);
                    TextView lonG = (TextView) findViewById(R.id.LongitudeGPS);
                    lonG.setText(lon);

                    
                    Geocoder gc = new Geocoder(getApplicationContext());
                    try {
                            adress = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
                    } catch (IOException e) {
                            e.printStackTrace();
                    }
                    
                    for (int i = 0; i < 1; ++i) {
                            String direccion = adress.get(i).getAddressLine(0).toString();
                            Log.v(GPS, direccion);
                            
                            TextView localizacion = (TextView) findViewById(R.id.GeoGPS);
                            localizacion.setText(direccion);
                            LastPos = direccion;
                            
                    }
                    
                   // 
                    
            }
    };
}
