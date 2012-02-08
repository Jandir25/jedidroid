package org.jedi.jediDroid.activitys;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* Diferentes ejemplos:
 * 		- Guardar el estado en memoria
 * 		- Guardar el estado en preferencias
 *		- Menu
 *		- Context menu
 *		- Intent marcando un número de telefono
 *		- Proveedor de contactos
 *		- Notificación
 *		- Leer, escribir fichero
 *		- Toast
 */

public class JediDroidActivity extends Activity {
	protected static final String LOG = "EjerciciosJediDroid";
	protected static int NOTIFICATION_ID = 1;
	protected static final String PREFS_NAME = "Preferencias";
	protected static final String FILENAME = "file1";

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(LOG, "onCreate");
		
		super.onCreate(savedInstanceState);
		/* Mostramos el layout correspondiente */
		setContentView(R.layout.calculadora);
		
		/* Para ejecutar otra activity */
		//startActivity(new Intent(getApplicationContext(), NotificationTestHandler.class));
		
		/* Defino los listeners */
		MyListenerOnClick listener = new MyListenerOnClick();
		Button suma = (Button) findViewById(R.id.buttonSuma);
		Button resta = (Button) findViewById(R.id.buttonResta);
		Button multiplicacio = (Button) findViewById(R.id.buttonMultiplicacio);
		Button divisio = (Button) findViewById(R.id.buttonDivisio);
		ImageView llamar = (ImageView) findViewById(R.id.buttonLlamar);
		ImageView contactos = (ImageView) findViewById(R.id.buttonContacts);
		Button notificacion = (Button) findViewById(R.id.buttonNotificacion);
		Button startService = (Button) findViewById(R.id.buttonNewService);
		Button stopService = (Button) findViewById(R.id.buttonStopService);
		Button fichero = (Button) findViewById(R.id.buttonFichero);
		
		suma.setOnClickListener(listener);
		resta.setOnClickListener(listener);
		multiplicacio.setOnClickListener(listener);
		divisio.setOnClickListener(listener);
		llamar.setOnClickListener(listener);
		contactos.setOnClickListener(listener);
		notificacion.setOnClickListener(listener);
		startService.setOnClickListener(listener);
		stopService.setOnClickListener(listener);
		fichero.setOnClickListener(listener);

		/* Definimos que la imagen 'contactos' tendrá context menu */
		registerForContextMenu(contactos);
		registerForContextMenu(llamar);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(LOG, "onStart");
		
		/* Recuperar las preferencias para mantener el estado aunque la aplicación sea matada */ 
		TextView inputResult = (TextView) findViewById(R.id.inputResult);
		TextView inputX = (TextView) findViewById(R.id.inputX);
		TextView inputY = (TextView) findViewById(R.id.inputY);

		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String inputResultValor = prefs.getString("inputResult", "");
		String inputXValor = prefs.getString("inputX", "");
		String inputYValor = prefs.getString("inputY", "");
		
		inputResult.setText(inputResultValor);
		inputX.setText(inputXValor);
		inputY.setText(inputYValor);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(LOG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(LOG, "onPause");
		TextView inputResult = (TextView) findViewById(R.id.inputResult);
		TextView inputX = (TextView) findViewById(R.id.inputX);
		TextView inputY = (TextView) findViewById(R.id.inputY);
		
		/* Guardar el estado en el fichero de preferencias, para que se mantenga el estado aunque se salga de la aplicacion */
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		
		prefs.edit().putString("inputResult", inputResult.getText().toString()).commit();
		prefs.edit().putString("inputX", inputX.getText().toString()).commit();
		prefs.edit().putString("inputY", inputY.getText().toString()).commit();
		prefs.edit().commit();
		
		/* Otra forma de guardar el estado en el fichero de preferencias de forma más limpia */
//		SharedPreferences.Editor editor = prefs.edit();
//		editor.putString("inputResult", inputResult.getText().toString());
//		editor.putString("inputX", inputX.getText().toString());
//		editor.putString("inputY", inputY.getText().toString());
		
//		editor.commit();		
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.v(LOG, "onStop");
	}

	@Override
	protected void onDestroy() {
		Log.v(LOG, "onDestroy");		
		super.onDestroy();
	
	}

	/* Salvar el estado en memoria */
	/* Guardar estado en memoria, no sirve para guardar el estado al hacer el giro de pantalla.
	 * No sirve en caso de que la aplicación sea matada, para ello tenemos que guardarlo en el fichero de preferencias */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.v(LOG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
		TextView inputResultat = (TextView) findViewById(R.id.inputResult);
		outState.putString("resultado", inputResultat.getText().toString());
	}
	
	/* Restauramos el estado que hemos guardado en memoria */
	@Override
	protected void onRestoreInstanceState(Bundle outState) {
		Log.v(LOG, "onRestoreInstanceState");
		super.onRestoreInstanceState(outState);
		TextView inputResultat = (TextView) findViewById(R.id.inputResult);
		inputResultat.setText(outState.getString("resultado"));
	}
	
	
	/* Menu de la aplicacion */
	/* Funcion que se llama al pulsar por primera vez el boton menu */
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.v(LOG, "onCreateOptionsMenu");
		menu.add("");
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	/* Funcion que se llama cada vez que pulsamos el boton menu */
	public boolean onPrepareOptionsMenu(Menu menu) {
		Log.v(LOG, "onPrepareOptionsMenu");		
		return true;
	}
	
	/* Listener del context menu, funcion que se llama cada vez que se ejecuta un context menu */
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		Log.v(LOG, "onCreateContextMenu");
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getMenuInflater();
		if (v.getId() == R.id.buttonContacts) {
			inflater.inflate(R.menu.contextmenu, menu);
		}
		else if (v.getId() == R.id.buttonLlamar) {
			inflater.inflate(R.menu.contextmenu2, menu);
		}
	}
	
	

	/* Listeners */
	protected class MyListenerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.buttonLlamar) {
				/* Intent de marcar un número de teléfono */
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:934741150"));
				startActivity(intent);
			}
			
			if(v.getId() == R.id.buttonContacts) {
				/* Utilizando el proveedor de contactos */
				startActivity(new Intent(getApplicationContext(), ContactProviderActivity.class));
			}
						
			/* Ejemplo de como ejecutar una notificación cada vez que pulsemos el botón 'notificación' */
			if(v.getId() == R.id.buttonNotificacion) {
				Log.v(LOG, "Button Notificacion");
				
				NotificationManager myNManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				/* Definimos el icono de la notificación */
				int icon = R.drawable.contact;
				/* Definimos el texto que sale al generar la notificación */
				CharSequence texto1 = "En movimiento";
				/* Definimos la base de tiempo para la hora que muestra la notificación. */
				long when = System.currentTimeMillis();
				/* Creamos la notificación */
				Notification notification = new Notification(icon, texto1, when);
				
				Context context = getApplicationContext();
				/* Definimos los dos textos que salen al desplegar la barra de notificaciones. ContentTitle será el título y contentText será el texto */
				CharSequence contentTitle = "My notificaciton";
				CharSequence contentText = "Hello World!";
				/* Definimos a que Activity debe de ir cuando cliquen en nuestra notificación */
				Intent notificaIntent = new Intent(context, ContactProviderActivity.class);
				PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificaIntent, 0);
				notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
				
				/* Flag para hacer desaparecer la notificacion cuando el usuario clique en ella. */
				notification.flags |= Notification.FLAG_AUTO_CANCEL;
				
				/* Flag que indica que la notificacion no desaparezca nunca. */
				//notification.flags |= Notification.FLAG_NO_CLEAR;
				
				/* Flag que hace SPAM hasta que el usuario clique la notificacion. */
				//notification.flags |= Notification.FLAG_INSISTENT;
				
				/* Flag para la vibracion */
				//notification.defaults |= Notification.DEFAULT_VIBRATE;
				
				/* Flag para la vibracion, con vibracion personalizada. */
				long[] vibrate = {(long) 0, (long) 100, (long) 200, (long) 300};
				notification.vibrate = vibrate;
				
				/* Flag para que suene la notificacion */
				notification.defaults |= Notification.DEFAULT_SOUND;
				
				/* Ejecutamos la notificación y le definimos un identificador */
				Log.v(LOG, "Ejecutar notificacion");
				myNManager.notify(NOTIFICATION_ID, notification);
				
				/* Para cancelar la notificacion manualmente */
				//myNManager.cancel(HELLO_ID);
			}
			
			if (v.getId() == R.id.buttonNewService) {
				Log.v(LOG, "Button Start Service");
				/* Arrancamos el servicio */
				Intent intent = new Intent(getApplicationContext(), MyService.class);
				startService(intent);
			}
			
			if (v.getId() == R.id.buttonStopService) {
				Log.v(LOG, "Button Stop Service");
				/* Paramos al servicio */
				Intent intent = new Intent(getApplicationContext(), MyService.class);
				stopService(intent);
			}
			
			/* Ejemplo de crear, leer de un fichero y mostrarlo el resultado en un toast. Pero lo de crear/leer el fichero no acaba de funcionar */ 
			if (v.getId() == R.id.buttonFichero) {
				
				TextView result = (TextView)findViewById(R.id.inputResult);
				String resultValor = result.getText().toString();
				
				/* Abrimos y escribimos el fichero */
				try {
					FileOutputStream fosEscribir = openFileOutput(FILENAME, Context.MODE_PRIVATE);
					fosEscribir.write(resultValor.getBytes());
					fosEscribir.close();
				} catch (Exception e) {

				}
				
				/* Leemos el fichero y lo escribimos en un toast */
				String valorLeido = null;
				try {
					FileInputStream fosLeer = openFileInput(FILENAME);
					fosLeer.read(valorLeido.getBytes());
					fosLeer.close();
				} catch (Exception e) {
				}
				
				/* Mostramos un toat */
				Toast t = Toast.makeText(getApplicationContext(), valorLeido, Toast.LENGTH_SHORT);
				t.show();
				
			}
			
			/* Listeners para los operadores matemáticos */
			else {
			
				EditText inputX = (EditText) findViewById(R.id.inputX);
				EditText inputY = (EditText) findViewById(R.id.inputY);
					
				Double result = null;
							
				if (comprobarCampos(inputX, inputY)) {
					Double valorX = Double.valueOf(((inputX.getText()).toString()));
					Double valorY = Double.valueOf(((inputY.getText()).toString()));
				
					switch (v.getId()) {
						case R.id.buttonSuma:
							result = valorX + valorY;			
							break;
							
						case R.id.buttonResta:
							result = valorX - valorY;				
							break;
							
						case R.id.buttonMultiplicacio:
							result = valorX*valorY;				
							break;
							
						case R.id.buttonDivisio:
							if (valorY != 0) {
								result = valorX/valorY;
							}
							break;
							
					
					}
				}
				
				/* Metemos el resultado de la operación matemática en el TextView */
				TextView inputResultat = (TextView) findViewById(R.id.inputResult);
				inputResultat.setText(String.valueOf(result));			
			}
		}
		
		/* Funciones privadas */
		private boolean comprobarCampos(EditText inputX, EditText inputY) {
			if (inputX.getText().toString().equals("") || inputY.getText().toString().equals("")) {
				return false;
			}
			return true;			
		}
	}
}
