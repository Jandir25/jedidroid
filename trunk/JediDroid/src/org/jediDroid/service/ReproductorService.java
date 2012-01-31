package org.jediDroid.service;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;


public class ReproductorService extends Service {
	protected static final String LOG = "JediDroid - ReproductorService";
	MediaPlayer mediaPlayer;
	Thread thread;

	public void onCreate() {
		Log.v(LOG, "onCreateService");
		mediaPlayer = new MediaPlayer();
		File sdCard = Environment.getExternalStorageDirectory();
		File song = new File(sdCard.getAbsolutePath() + "/Music/Carlos Jean - Lead the way.mp3");
		Log.v(LOG,song.toString());
		try {
			mediaPlayer.setDataSource(song.getAbsolutePath());
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public int onStartCommand(Intent intent, int flags, int startId){
			
		/* Play y pause con un thread */
		Log.v(LOG, "onStartCommand");
			new Thread(new Runnable() {
				public void run() {
					if (mediaPlayer.isPlaying()) {
						Log.v(LOG, "Pause");
						mediaPlayer.pause();
					}
					else {
						Log.v(LOG, "Start");
						mediaPlayer.start();
					}
				}
			}).start();
		return START_STICKY;

	}
	
	
	public void onDestroy() {
		Log.v(LOG, "onDestroy");
		mediaPlayer.stop();
		//Para liberar los recursos
		Log.v(LOG, "Relase");
		mediaPlayer.release();
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
