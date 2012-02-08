package org.jedi.Activity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/* Ejemplo de GPS */

public class GpsActivity extends Activity {
	private String GPS = "GPS";
	List <Address> adress;
	ArrayList <String> direcciones;
	LocationManager myLocationManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps);
		
		/* Creamos una instancia del Manager de localizaci�n */
		myLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		adress = null;
		direcciones = new ArrayList<String>();
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		/* Definimos los listeners tanto para loa ubicacion mediante red como mediante GPS 
		 * Los dos parametros 'long minTime' y 'float minDistance' de la funci�n indica el
		 * intervalo de tiempo o la distancia en la que el GPS activar� el listener. */
		myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/* Desactivamos el listener para no consumir GPS cuando la aplicaci�n no est� en primer plano */
		myLocationManager.removeUpdates(locationListener);
	}
	
	
	LocationListener locationListener = new LocationListener() {
		
		/* Por si cambia el estado, por ejemplo, pierde la localicacion */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
			
		}
		
		/* Si el usuario activa el elemento de localizacion (GPS o Red) */
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		/* Si el usuario desactiva el elemento de localizacion (GPS o Red) */
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		/* Si la localizacion cambia segun los parametros que le hemos puesto en el listener */
		@Override
		public void onLocationChanged(Location location) {
			Log.v(GPS, "Latitud: " + ((Double)location.getLatitude()).toString());
			Log.v(GPS, "Longitud: " + ((Double)location.getLongitude()).toString());
			Geocoder gc = new Geocoder(getApplicationContext());
			
			try {
				/* Esta funci�n, nos da la direcci�n correspondiente a las coordenadas. El �ltimo Integer indica
				 * el n�mero m�ximos de resultados. */
				adress = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/* Comprobamos que no sea null, porque la funci�n getFromLocation puede retornar null si, por ejemplo, no tiene conexi�n a internet */
			if (adress != null) {
				
				/* Vaciamos la ArrayList */
				direcciones.clear();
				
				/* Ponemos las direcciones en un ArrayList */
				for (int i = 0; i < adress.size(); ++i) {
					String direccion = adress.get(i).getAddressLine(0).toString();
					Log.v(GPS, direccion);
					
					direcciones.add(direccion);					
				}
				
				/* Pasamas el ArrayList en un String[] */
				String[] direccionesArray = new String[direcciones.size()];
				direccionesArray = (String[]) direcciones.toArray(direccionesArray);

				ListView listView = (ListView) findViewById(R.id.listGps);
				
				/* Ponemos las direcciones en una especie de 'layoutLista' */
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, direccionesArray);
				/* Ejecutamos el adapter para que se muestre las direcciones */
				listView.setAdapter(adapter);
			}
		}
	};
}
