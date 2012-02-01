package org.jediDroid.service;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;


public class ReproductorService extends Service {
	protected static final String LOG = "JediDroid - ReproductorService";
	MediaPlayer mediaPlayer;
	private final IBinder binder = new MyBinder();

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
		Log.v(LOG, "onStartCommand");
		return START_STICKY;

	}
	
	
	public void onDestroy() {
		Log.v(LOG, "onDestroy");
		mediaPlayer.stop();
		//Para liberar los recursos
		Log.v(LOG, "Relase");
		mediaPlayer.release();
		
	}

	/* Cuando se asocie una actividad */
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	
	public class MyBinder extends Binder {
		public ReproductorService getService() {
			return ReproductorService.this;
		}
	}
	
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}
	
	public void play() {
		Log.v(LOG, "Play");
		mediaPlayer.start();
	}
	
	public void pause() {
		Log.v(LOG, "Pause");
		mediaPlayer.pause();
		
	}

	public void stop() {
		Log.v(LOG, "Stop");
		mediaPlayer.stop();
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
