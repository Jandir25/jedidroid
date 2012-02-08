package org.sr.projecte;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;


public class ReproductorService extends Service
{
	
	protected final String LOG = "Service";

	private  MediaPlayer mp;
	private String[] music;
	private int cont = 0;
	private MyBinder binder = new MyBinder();
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.v("LOG","1");
	      mp = new MediaPlayer();
	      getFiles();
	        
			Log.v("LOG","2");

	        mp.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) 
				{
					nextSong();
					
				}
			});
		
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		
		return START_STICKY;
		
	}
	
	public class MyBinder extends Binder {
		ReproductorService getService() {
			return ReproductorService.this;
		}
	}

	private void nextSong()
	{
		mp.reset();
		++cont;
		cont = cont%music.length;
		playAudio(music[cont]);
		mp.start(); 
	}
	
	private void prevSong()
	{
		mp.reset();
		--cont;
		if (cont < 0) cont = music.length-1;
		playAudio(music[cont]);
		mp.start(); 
	}
	
	public void next_song()
	{
		nextSong();
	}
	
	public void prev_song()
	{
		prevSong();
	}
	
	public void seek_mp(int nSeek)
	{
		mp.seekTo(nSeek);
	}
	
	  private void getFiles() {
	        // TODO Auto-generated method stub
	         File sdCard =Environment.getExternalStorageDirectory();
	         File dir = new File(sdCard.getAbsolutePath()+"/Music/");
	         music = dir.list();
	         if (music.length == 0) return;
	         String filename= music[0];
	         playAudio(filename);
	   }
	  
	    private void playAudio(String filename)
	    {
	    
	    
	    	filename = Environment.getExternalStorageDirectory()+"/Music/"+ filename;
	    	if (!mp.isPlaying())
	    	{
	    		try {
					mp.setDataSource(filename);
					mp.prepare();			
				} catch (Exception e) {}
	    	}
	    }
	  
	  
	  public void reset_reproductor()
	  {
		  mp.reset();
	  }
	   
	  public void play_reproductor()
	  {
		  mp.start();
	  }
	  
	  public void pause_reproductor()
	  {
		  mp.pause();
	  }
	  
	  public void set_song(int nSong, int id)
	  {
		  cont = id;
		  Log.v(LOG,"Contador de la canso: "+ cont+"");
		  playAudio(music[nSong]);
		  
		  
	  }
	  
	  public String[] get_songs()
	  {
		  return music;
	  }
	
	  public int get_duration_actual()
	  {
		  return mp.getDuration();
	  }
	
	  public boolean not_null_mp()
	  {
		  return mp != null;
	  }

	  public boolean mp_playing()
	  {
		  return mp.isPlaying();
	  }
	  
	  public int mp_current_position()
	  {
		  return mp.getCurrentPosition();
	  }
	  
	  public String get_actual()
	  {
		  return music[cont];
	  }
		@Override
		public void onDestroy() {
			Log.v(LOG,"DESTROY");
			super.onDestroy();
			mp.release();
			
		}
}


