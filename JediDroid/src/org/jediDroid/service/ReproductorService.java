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

/* Definimos el Service del reproductor */

/* De service hay de dos tipos:
 * 		- Started: Este servicio es inciado y parado por la aplicaci�n, aunque no hace falta que la aplicaci�n
 * este permanentemente activa para que el servicio funcione (puede morir la aplicaci�n y el servicio seguira funcionando).
 * Con este tipo de servicio no se puede interactuar, unicamente se pueden utilzar las operaciones ya definidas (entre ellas 
 * encender y parar el servicio).
 * 
 * 		- Bound: Este servicio se inicia cuando una aplicaci�n se asocia a ella y muere cuando no hay ninguna aplicaci�n asociada al servicio.
 * Se le pueden definir operaciones (funciones) para crear un servicio personalizado.
 * Tambi�n, este tipo de servicio puede ofrecerse para que otras aplicaciones lo utilicen, es decir, yo puedo crear una aplicaci�n que ofrezca
 * un servicio (por ejemplo que me de la hora) y as� otras aplicaciones podr�n nutrirse del servicio. 
 * 
 * 		- Mixto: Se puede crear un servicio mixto (como el de este ejemplo) y as� tendr� las propiedades de los dos servicios. Para ello
 * tenemos que definir las operaciones (funciones) de ambos servicios.
 */

public class ReproductorService extends Service {
	protected static final String LOG = "JediDroid - ReproductorService";
	MediaPlayer mediaPlayer;
	private final IBinder binder = new MyBinder();

	public void onCreate() {
		Log.v(LOG, "onCreateService");
		
		/* Definimos y configuramos el MediaPlayer */ 
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
		/* Con el START_STICKY estamos diciendo al Android que no puede matar el servicio */
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
	
	/* Creamos el binder, que le retorna una instancia de nuestro servicio */
	public class MyBinder extends Binder {
		public ReproductorService getService() {
			return ReproductorService.this;
		}
	}
	
	/* Funciones personalizadas del servicio */
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
			/* Despu�s del "stop" hay que hacer un prepare, en el destroy no se ha hecho porque, al destruir el servicio la pr�xima vez que 
			 * se haga "play" se crear� el servicio de cero y por lo tanto ya har� el "prepare" en el "onCreate()".
			 */
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
