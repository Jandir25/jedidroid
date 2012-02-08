package org.jedi.Activity;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/* Ejemplo de como utilizar el sensor magnético. 
 * Teoricamente, es un detector de metales 
 */

public class SensorsActivity extends Activity {
	private SensorManager mySensorManager = null;
	private Sensor mySensorMagnetic = null;
	private ProgressBar pB = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* Definimos un botón para el GPS */
        MyListenerOnClick myListenerOnClick = new MyListenerOnClick();
        Button gps = (Button) findViewById(R.id.buttonGps);
        gps.setOnClickListener(myListenerOnClick);
        
        /* Creamos una instancia del manager sensor */
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        
        /* Comprobamos si el movil tiene Sensor magnetico */
        if (mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
        	mySensorMagnetic = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
        	
    }
    
    protected  void onResume() {
    	super.onResume();
    	/* Registramos el sensor al listener */
    	/* La variable SensorManager.SENSOR_DELAY_UI defina la frecuencia con la se testea el sensor.
    	 * Hay diferentes constantes, la SensorManager.SENSOR_DELAY_UI es la que recomienda google para las interfaces.
    	 */
    	if (mySensorMagnetic != null) {
    		mySensorManager.registerListener(mySensorListener, mySensorMagnetic, SensorManager.SENSOR_DELAY_UI);
    	}
    }
    
    protected void onPause() {
    	super.onPause();
    	
    	/* Desregistramos el sensor del listener */
    	if (mySensorMagnetic != null) {
    		mySensorManager.unregisterListener(mySensorListener);
    	}

    }
    
    
    
    /* Listener de los sensores */
    SensorEventListener mySensorListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			/* Comprobamos que el sensor que ha creado el evento sea el magnetico */
			if (event.sensor.getType() == mySensorMagnetic.getType()) {
				
				/* El decimalFormat es para concretar la precision con la que queremos obtener los valores */
				DecimalFormat d = new DecimalFormat("000");
				Double val1 = Double.valueOf(d.format((double) event.values[0]));
				Double val2 = Double.valueOf(d.format((double) event.values[1]));
				Double val3 = Double.valueOf(d.format((double) event.values[2]));
			
				Double modulo = Math.sqrt(event.values[0] * event.values[0]
						+ event.values[1] * event.values[1] + event.values[2] * event.values[2]);
				
				pB = (ProgressBar) findViewById(R.id.progressBar1);				
		        TextView textX = (TextView) findViewById(R.id.textViewX);
		        TextView textY = (TextView) findViewById(R.id.textViewY);
		        TextView textZ = (TextView) findViewById(R.id.textViewZ);
		        
		        pB.setProgress(modulo.intValue());
				textX.setText("X: " + val1.toString());
				textY.setText("Y: " + val2.toString());
				textZ.setText("Z: " + val3.toString());
				
				/* Vibramos el movil si tiene el progresBar es mayor que 75% */
				if (modulo > 75) {
					Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					/* Si la variable v es null, vol dir que el movil no te vibracio */
					if (v != null) {
						v.vibrate(20);
					}
				}
			}
			
		}
		
		/* Para los sensores que permiten tener un listener para la variacion de precision.
		 * 
		 *  Para hacer un simil (aunque este caso no funciona). En el googleMaps, cada vez que varia el radio de la precision, que sale
		 *  en 'Mi ubicacion', entraria en esta funcion.
		 *  
		 *  Esta funcion nos puede servir si, por ejemplo, tenemos un sensor que con una precision baja no nos sirve.  
		 *  */
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
		
		
	};
	
	/* Listener del button */
	
	protected class MyListenerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.buttonGps) {
				startActivity(new Intent(getApplicationContext(), GpsActivity.class));
			}
			
		}
		
	}
}