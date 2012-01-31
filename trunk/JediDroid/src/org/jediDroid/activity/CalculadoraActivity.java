package org.jediDroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CalculadoraActivity extends Activity {
	protected static final String LOG = "JediDroid - CalculadoraActivity";
	
	String resultat = "0";
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(LOG, "onCreate");

		super.onCreate(savedInstanceState);
		
		TextView inputResult = (TextView) findViewById(R.id.inputResult);
		inputResult.setText(resultat);
		setContentView(R.layout.calculadora);
		
		/* Defino los listeners */
		MyListenerOnClick listener = new MyListenerOnClick();
		
		Button number0 = (Button) findViewById(R.id.buttonNumber0);
		Button number1 = (Button) findViewById(R.id.buttonNumber1);
		Button number2 = (Button) findViewById(R.id.buttonNumber2);
		Button number3 = (Button) findViewById(R.id.buttonNumber3);
		Button number4 = (Button) findViewById(R.id.buttonNumber4);
		Button number5 = (Button) findViewById(R.id.buttonNumber5);
		Button number6 = (Button) findViewById(R.id.buttonNumber6);
		Button number7 = (Button) findViewById(R.id.buttonNumber7);
		Button number8 = (Button) findViewById(R.id.buttonNumber8);
		Button number9 = (Button) findViewById(R.id.buttonNumber9);
		
		Button suma = (Button) findViewById(R.id.buttonSuma);
		Button resta = (Button) findViewById(R.id.buttonResta);
		Button multiplicacio = (Button) findViewById(R.id.buttonMultiplicacio);
		Button divisio = (Button) findViewById(R.id.buttonDivisio);
		Button clear = (Button) findViewById(R.id.buttonClearCalculator);
		Button igual = (Button) findViewById(R.id.buttonIgual);
		
		number0.setOnClickListener(listener);
		number1.setOnClickListener(listener);
		number2.setOnClickListener(listener);
		number3.setOnClickListener(listener);
		number4.setOnClickListener(listener);
		number5.setOnClickListener(listener);
		number6.setOnClickListener(listener);
		number7.setOnClickListener(listener);
		number8.setOnClickListener(listener);
		number9.setOnClickListener(listener);
		suma.setOnClickListener(listener);
		resta.setOnClickListener(listener);
		multiplicacio.setOnClickListener(listener);
		divisio.setOnClickListener(listener);
		igual.setOnClickListener(listener);
		clear.setOnClickListener(listener);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.v(LOG, "onStart");
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
	
	/* MyListeners */
	
	protected class MyListenerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			TextView inputResult = (TextView) findViewById(R.id.inputResult);
			
			if (!pulsacioNumero(v, inputResult)) {
				pulsacioOperador(v, inputResult);
			}
			
				
						
		
			
		}
		
	}
		
	/* Funciones privadas */
	
	/**
	 * Logica al pulsar un numero.
	 * @param v
	 * @param textView
	 * @return true: Si se ha pulsado un numero
	 */
	private boolean pulsacioNumero(View v, TextView textView) {
		switch (v.getId()) {
			case R.id.buttonNumber0:
				añadirNumero("0", textView);
				break;
				
			case R.id.buttonNumber1:
				añadirNumero("1", textView);
				break;
				
			case R.id.buttonNumber2:
				añadirNumero("2", textView);
				break;
				
			case R.id.buttonNumber3:
				añadirNumero("3", textView);
				break;
				
			case R.id.buttonNumber4:
				añadirNumero("4", textView);
				break;
				
			case R.id.buttonNumber5:
				añadirNumero("5", textView);
				break;
				
			case R.id.buttonNumber6:
				añadirNumero("6", textView);
				break;
				
			case R.id.buttonNumber7:
				añadirNumero("7", textView);
				break;
				
			case R.id.buttonNumber8:
				añadirNumero("8", textView);
				break;
				
			case R.id.buttonNumber9:
				añadirNumero("9", textView);
				break;
				
			default:
				return false;	
		}
		return true;
	}
	
	private boolean pulsacioOperador(View v, TextView textView) {
		if (comprobarCampos(textView)) {
			switch (v.getId()) {
			}
		}
		
		return true;
	}
	
	private boolean comprobarCampos(TextView input) {
		if (input.getText().toString().equals("")) {
			return false;
		}
		return true;			
	}
	
	/**
	 * Añade el parametro 'numero' al string de la 'vista'
	 * @param numero
	 * @param vista
	 */
	private void añadirNumero(String numero, TextView vista) {
		String aux = vista.getText().toString();
		aux = aux.concat(numero);
		vista.setText(aux);
	}

}
