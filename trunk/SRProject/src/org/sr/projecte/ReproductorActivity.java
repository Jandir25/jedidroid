 package org.sr.projecte;

import org.sr.projecte.ReproductorService.MyBinder;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ReproductorActivity extends Activity  {

	   /** Called when the activity is first created. */
	protected final String LOG = "Reproductor";
	ReproductorService rSer;
	boolean bound = false;
	boolean paused = false;
	public static final String PREF_NAME = "MYPREFFILE";
	private String CONSUMER_KEY = "MGW3iFIWES96mRIdHq7Apw";
	private String CONSUMER_SECRET = "N9sXUF3wpVJ3ePGwnqzPRBmdOpMPpYJexgLhJ8jnKM";

	private void tweet_song(final String text)
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
    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            MyBinder binder = (MyBinder) service;
            rSer = binder.getService();
            bound = true;
        }
    	
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
    	/* Enable full screen mode */
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor);

        // Servei
//        rSer = new ReproductorService();
    	Intent intent = new Intent(ReproductorActivity.this, ReproductorService.class);
    	bindService(intent, connection, Context.BIND_AUTO_CREATE);
    	startService(intent);
        final Activity Pa = this;
//    	android.os.SystemClock.sleep(2000);
		ImageView invi = (ImageView) findViewById(R.id.imageView1);
		invi.postDelayed(new Runnable() {
	        public void run() {
	        	//  Declarem les imatges
	        	ImageView mPlay = (ImageView) findViewById(R.id.PlayMusic);
	        	ImageView mPause = (ImageView) findViewById(R.id.PauseMusic);
	        	ImageView mNext= (ImageView) findViewById(R.id.NextMusic);
	        	ImageView mBack = (ImageView) findViewById(R.id.PrevMusic);
	        	ImageView mTwit = (ImageView) findViewById(R.id.twiticon);
	        	// Afegim els listeners
	        	MyImageClick mIC = new MyImageClick();
	        	mPlay.setOnTouchListener(mIC);
	        	mPause.setOnTouchListener(mIC);
	        	mNext.setOnTouchListener(mIC);
	        	mBack.setOnTouchListener(mIC);
	        	mTwit.setOnTouchListener(mIC);
	        	// Declarem la seekbar i li afegim el listener
	        	SeekBar sb = (SeekBar) findViewById(R.id.ProgressMusic);
	        	MyProgressBar mPB = new MyProgressBar();
	        	sb.setOnSeekBarChangeListener(mPB);
	        	// Llista dels elements
	        	final ListView lSong = (ListView) findViewById(R.id.list);
	        	lSong.clearAnimation();
	        	lSong.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						
						return false;
					}
				});
	        	lSong.setScrollbarFadingEnabled(false);
	        	ArrayAdapter<String> adaptador =  new ArrayAdapter<String>(Pa,R.layout.row,rSer.get_songs());
	        	
	        	lSong.setAdapter(adaptador);
	        	// Creem el listener per clicar
	        	
	        	lSong.setOnItemClickListener(new OnItemClickListener()
	        	{
	        		
	        		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	        				long arg3)
	        		{
	        			
	        			rSer.reset_reproductor();
	        			rSer.set_song(arg2,arg2);
	        			rSer.play_reproductor();
	        			play_song();
	        		}
	        	});
	        	
	        	
	        	
	        	// Programem un thread per mantenir la seek actualitzada
	        	Runnable progressUpdater = new Runnable() {
	        		@Override
	        		public void run() 
	        		{
	        			int total = rSer.get_duration_actual();
	        			int act = 0;
	        			SeekBar sb = (SeekBar) findViewById(R.id.ProgressMusic);
	        			sb.setMax(total);
	        			while (rSer.not_null_mp()  || act < total) 
	        			{
	        				total = rSer.get_duration_actual();
	        				sb.setMax(total);
	        				if (!rSer.mp_playing() && !paused)
	        				{
	        					sb.setProgress(0);
	        				}
	        				
	        				try 
	        				{
	        					Thread.sleep(1000);
	        					act = rSer.mp_current_position();
	        				} catch (Exception e) {}
	        				
	        				sb.setProgress(act);
	        			}   		
	        		}
	        	};
	        	Thread thread = new Thread(progressUpdater);
	        	thread.start();
	        	

	        }}, 500);
          }
    
  
   public void play_song()
   {
	   
		TextView txtMusic = (TextView) findViewById(R.id.PlaySong);
    	txtMusic.setText(rSer.get_actual());
   }
 
   void show_toast(CharSequence text)
   {
		Context context = getApplicationContext();
   	int duration = Toast.LENGTH_SHORT;
   	Toast toast = Toast.makeText(context, text, duration);
   	toast.show();
   }
    
    
    
    

    
    public class MyProgressBar implements OnSeekBarChangeListener,OnSeekCompleteListener
    {

		@Override
		public void onSeekComplete(MediaPlayer arg0) 
		{		
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
		{
		
				  if (fromUser) 
				  {
			         rSer.seek_mp(progress);
			       }
			 }
		

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
    	
    }
    
    public class MyImageClick implements OnTouchListener
    {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			switch (v.getId())
			{
				case R.id.PlayMusic:
					
					rSer.play_reproductor();
					play_song();
					break;
				case R.id.PauseMusic:
					if (rSer.mp_playing())
					{
						rSer.pause_reproductor();
						paused = true;
					}
					else paused = false;
					break;
				case R.id.twiticon:
					if (rSer.mp_playing())
					{
						show_toast("Song Twitted");
						tweet_song("Playing "+rSer.get_actual()+" using SrProject.");
					}
					break;
				case R.id.NextMusic:
					rSer.next_song();
					play_song();
					break;
				case R.id.PrevMusic:
					rSer.prev_song();
					play_song();
					break;
					
				default:
					
			}	
			return false;
		}
    }


	
}
