package org.jediDroid.activity;

import java.util.ArrayList;
import java.util.Random;

import org.jediDroid.domain.BBDD;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/* Activity del juego memorys */

public class MemoryActivity extends Activity {
	protected static final String LOG = "JediDroid - MemoryActivity";
	ArrayList<Boolean> destapades = new ArrayList<Boolean>();
	ArrayList<Integer> cartes = new ArrayList<Integer>();
	Integer ultimaDestapada = null;
	Integer fallos = 0;
	Boolean touch = false;
	Integer ultimaDestapadaCarta = null;
	BBDD db = null;
	Integer DIALOG_WINNER = 0;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(LOG, "onCreate");
		touch = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memory);
		

		/* Defino los listeners */
		MyListenerOnClick listener = new MyListenerOnClick();

		ImageView carta00 = (ImageView) findViewById(R.id.carta00);
		ImageView carta01 = (ImageView) findViewById(R.id.carta01);
		ImageView carta02 = (ImageView) findViewById(R.id.carta02);
		ImageView carta10 = (ImageView) findViewById(R.id.carta10);
		ImageView carta11 = (ImageView) findViewById(R.id.carta11);
		ImageView carta12 = (ImageView) findViewById(R.id.carta12);
		ImageView carta20 = (ImageView) findViewById(R.id.carta20);
		ImageView carta21 = (ImageView) findViewById(R.id.carta21);
		ImageView carta22 = (ImageView) findViewById(R.id.carta22);
		ImageView carta30 = (ImageView) findViewById(R.id.carta30);
		ImageView carta31 = (ImageView) findViewById(R.id.carta31);
		ImageView carta32 = (ImageView) findViewById(R.id.carta32);
		
		carta00.setOnClickListener(listener);
		carta01.setOnClickListener(listener);
		carta02.setOnClickListener(listener);
		carta10.setOnClickListener(listener);
		carta11.setOnClickListener(listener);
		carta12.setOnClickListener(listener);
		carta20.setOnClickListener(listener);
		carta21.setOnClickListener(listener);
		carta22.setOnClickListener(listener);
		carta30.setOnClickListener(listener);
		carta31.setOnClickListener(listener);
		carta32.setOnClickListener(listener);
		
		inicializaValors();
		touch = true;		
	}
	
	@Override
	protected void onResume() {
		touch = false;
		super.onResume();
		Log.v(LOG, "onResume");
		/* Bloqueamos el giro de pantalla */
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		touch = true;
	}
	
	/* MyListeners */

	protected class MyListenerOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (touch) {
				ImageView destapada;
				switch (v.getId()) {
					case R.id.carta00:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta00) && (destapades.get(0) != true)) {
							ultimaDestapadaCarta = R.id.carta00;
							destapada = (ImageView) findViewById(R.id.carta00);
							/* Damos vuelta a la carta */
							destapada.setImageResource(cartes.get(0));
							comprobarAcierto(cartes.get(0));
						}
						break;
						
					case R.id.carta01:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta01) && (destapades.get(1) != true)) {
							ultimaDestapadaCarta = R.id.carta01;
							destapada = (ImageView) findViewById(R.id.carta01);
							destapada.setImageResource(cartes.get(1));
							comprobarAcierto(cartes.get(1));
						}
						break;									
						
					case R.id.carta02:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta02) && (destapades.get(2) != true)) {
							ultimaDestapadaCarta = R.id.carta02;
							destapada = (ImageView) findViewById(R.id.carta02);
							destapada.setImageResource(cartes.get(2));
							comprobarAcierto(cartes.get(2));
						}
						break;	
										
					case R.id.carta10:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta10) && (destapades.get(3) != true)) {
							ultimaDestapadaCarta = R.id.carta10;
							destapada = (ImageView) findViewById(R.id.carta10);
							destapada.setImageResource(cartes.get(3));
							comprobarAcierto(cartes.get(3));
						}
						break;
						
					case R.id.carta11:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta11) && (destapades.get(4) != true)) {
							ultimaDestapadaCarta = R.id.carta11;
							destapada = (ImageView) findViewById(R.id.carta11);
							destapada.setImageResource(cartes.get(4));
							comprobarAcierto(cartes.get(4));
						}
						break;
						
					case R.id.carta12:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta12) && (destapades.get(5) != true)) {
							ultimaDestapadaCarta = R.id.carta12;
							destapada = (ImageView) findViewById(R.id.carta12);
							destapada.setImageResource(cartes.get(5));
							comprobarAcierto(cartes.get(5));
						}
						break;
						
					case R.id.carta20:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta20) && (destapades.get(6) != true)) {
							ultimaDestapadaCarta = R.id.carta20;
							destapada = (ImageView) findViewById(R.id.carta20);
							destapada.setImageResource(cartes.get(6));
							comprobarAcierto(cartes.get(6));
						}
						break;
						
					case R.id.carta21:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta21) && (destapades.get(7) != true)) {
							ultimaDestapadaCarta = R.id.carta21;
							destapada = (ImageView) findViewById(R.id.carta21);
							destapada.setImageResource(cartes.get(7));
							comprobarAcierto(cartes.get(7));
						}
						break;
						
					case R.id.carta22:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta22) && (destapades.get(8) != true)) {
							ultimaDestapadaCarta = R.id.carta22;
							destapada = (ImageView) findViewById(R.id.carta22);
							destapada.setImageResource(cartes.get(8));
							comprobarAcierto(cartes.get(8));
						}
						break;
						
					case R.id.carta30:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta30) && (destapades.get(9) != true)) {
							ultimaDestapadaCarta = R.id.carta30;
							destapada = (ImageView) findViewById(R.id.carta30);
							destapada.setImageResource(cartes.get(9));
							comprobarAcierto(cartes.get(9));
						}
						break;
						
					case R.id.carta31:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta31) && (destapades.get(10) != true)) {
							ultimaDestapadaCarta = R.id.carta31;
							destapada = (ImageView) findViewById(R.id.carta31);
							destapada.setImageResource(cartes.get(10));
							comprobarAcierto(cartes.get(10));
						}
						break;
	
					case R.id.carta32:
						if ((ultimaDestapadaCarta == null || ultimaDestapadaCarta != R.id.carta32) && (destapades.get(11) != true)) {
							ultimaDestapadaCarta = R.id.carta32;
							destapada = (ImageView) findViewById(R.id.carta32);
							destapada.setImageResource(cartes.get(11));
							comprobarAcierto(cartes.get(11));
						}
						break;
	
				}
	
				Log.v(LOG, "Destapades: " + destapades.toString());
			}
		}

	}
	
	/* Menu */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ranking, menu);
		return true;
	}
	
	 public boolean onOptionsItemSelected(MenuItem item) {
		 if (item.getItemId() == R.id.ranking) {
			startActivity(new Intent(getApplicationContext(), RankingActivity.class));
			return true;
		 }
		 return false;
	 }
	 
	 /* Dialog */
	 
	 /* Funcion que se llama al crear el dialog */
	 protected Dialog onCreateDialog(int id) {
		Log.v(LOG, "onCreateDialog");
		return createDialog();
		
	}	
		
	
	 private Dialog createDialog() {
		 Log.v(LOG, "createDialog");
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("Winner");
		 builder.setMessage("Fallos: " + fallos);
		 builder.setCancelable(false);
		 builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				inicializaValors();
				dialog.cancel();
			}
		});
		
		AlertDialog alert = builder.create();
		return alert;
	}
	 
	 
	 /* Funcion que se llama cada vez que se muestra el dialog */
	 @Override
	 protected void onPrepareDialog(int id, Dialog dialog) {
		 Log.v(LOG, "onPrepareDialog");
		 AlertDialog alertDialog = (AlertDialog)dialog;
         alertDialog.setMessage("Fallos: " + fallos);
             
	 }

	
	/* Privades */
	
	private void inicializaValors() {
		destapades = new ArrayList<Boolean>();
		cartes = new ArrayList<Integer>();
		db = new BBDD(getApplicationContext());
		ultimaDestapada = null;
		fallos = 0;
		ultimaDestapadaCarta = null;

		
		for (int i = 0; i < 12; ++i) {
			destapades.add(false);
		}
		
		for (int i = 0; i < 12; ++i) {
			cartes.add(-1);
		}
		
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 2;) {
				int iterator = r.nextInt(12);
				if (cartes.get(iterator) == -1) {
					cartes.set(iterator, idCartes(i));
					++j;
				}
			}
		}
		Log.v(LOG, "Cartes: " + cartes.toString());
		limpiarPantalla();
		touch = true;
		
	}
	
	private Integer idCartes(Integer i) {
		switch (i) {
			case 0:
				return R.drawable.meme_ffffuuuuu;
				
			case 1:
				return R.drawable.meme_forever_alone_face;
				
			case 2:
				return R.drawable.meme_fuck_yea;
				
			case 3:
				return R.drawable.meme_okay;
				
			case 4:
				return R.drawable.meme_that_sospicious;
				
			case 5:
				return R.drawable.meme_trollface;
		}
		return null;
	}
	
	private void comprobarAcierto(Integer i) {
		if (ultimaDestapada == null) {
			ultimaDestapada = i;
			return;
		}
		
		if (ultimaDestapada.equals(i)) {
			guardarAcierto(i);
			/* Comprobamos si hemos ganado */
			winner();
		}
		else {
			++fallos;
		}
		ultimaDestapadaCarta = null;
		ultimaDestapada = null;
		touch = false;
		limpiarPantalla();
		return;
		
		
	}
	
	private void guardarAcierto(Integer acertada) {
		for (int i = 0; i < 12; ++i) {
			if (cartes.get(i).equals(acertada)) {
				destapades.set(i, true);
			}
		}
		
	}
	
	private void limpiarPantalla() {
		
		/* Asociamos el thread a cualquier elemento de la vista.
		 * El 500, indica que esperar medio segundo a iniciar el thread */
		ImageView idElementView = (ImageView) findViewById(R.id.carta00);
		idElementView.postDelayed(new Runnable() {
		
			@Override
			public void run() {
				if (destapades.get(0) != true) {
					ImageView carta00 = (ImageView) findViewById(R.id.carta00);
					carta00.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(1) != true) {
					ImageView carta01 = (ImageView) findViewById(R.id.carta01);
					carta01.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(2) != true) {
					ImageView carta02 = (ImageView) findViewById(R.id.carta02);
					carta02.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(3) != true) {
					ImageView carta10 = (ImageView) findViewById(R.id.carta10);
					carta10.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(4) != true) {
					ImageView carta11 = (ImageView) findViewById(R.id.carta11);
					carta11.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(5) != true) {
					ImageView carta12 = (ImageView) findViewById(R.id.carta12);
					carta12.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(6) != true) {
					ImageView carta20 = (ImageView) findViewById(R.id.carta20);
					carta20.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(7) != true) {
					ImageView carta21 = (ImageView) findViewById(R.id.carta21);
					carta21.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(8) != true) {
					ImageView carta22 = (ImageView) findViewById(R.id.carta22);
					carta22.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(9) != true) {
					ImageView carta30 = (ImageView) findViewById(R.id.carta30);
					carta30.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(10) != true) {
					ImageView carta31 = (ImageView) findViewById(R.id.carta31);
					carta31.setImageResource(R.drawable.interrogante);
				}
				
				if (destapades.get(11) != true) {
					ImageView carta32 = (ImageView) findViewById(R.id.carta32);
					carta32.setImageResource(R.drawable.interrogante);
				}
				
				touch = true;
			}
		},500);		
	}
	
	private void winner() {
		if (!destapades.contains(false)) {
			touch = false;
			db.insertNewPartida(getApplicationContext(), "user", fallos);
			showDialog(DIALOG_WINNER);
		}
	}
}
