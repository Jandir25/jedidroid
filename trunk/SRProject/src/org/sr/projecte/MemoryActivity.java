package org.sr.projecte;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.sr.projecte.R.drawable;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MemoryActivity extends Activity  {
	
	
	
	
    /** Called when the activity is first created. */
	
	private final String LOG = "Memory";
	private ArrayList<Integer> vOcells;
	private ArrayList<Integer> vCheck;
	private Integer Checked;
	private Integer Blocked;
	private Integer Errors;
	private Double StartTime;
	private  AlertDialog alert;
	private AlertDialog alert2;
	private Double milis;
	private Activity parentAct = this;
	private MediaPlayer mp;
	public static final String PREF_NAME = "MYPREFFILE";
	private String CONSUMER_KEY = "MGW3iFIWES96mRIdHq7Apw";
	private String CONSUMER_SECRET = "N9sXUF3wpVJ3ePGwnqzPRBmdOpMPpYJexgLhJ8jnKM";

//	private String CONSUMER_KEY = "ytMLAwaveQlmqwK4NwTkg";
//	private String CONSUMER_SECRET = "49XB4FHujFfVcL46rLLwDfJTTf1uvBUzdxSME5vJ4";
//	private String ACCES_TOKEN = "178286820-PVWA1iRqeaYhoJa2wzoaytit9ScGOsprWutcXn0G";
//	private String ACCES_TOKEN_SECRET = "*nKSsj7rnMNtjwyrItEn5URWB2o6IxaAtunirf440";
	
	protected Dialog onCreateDialog(int idDialog) {
		
		return null;
	}
	
	private void iniciar_ocellots()
	{
		vOcells = new ArrayList<Integer>();
		vCheck = new ArrayList<Integer>();
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		
		ArrayList<Integer> cont = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) cont.add(0);
		
		for (int i = 0; i < 12; ++i)
		{	
			boolean b = false;
			while (!b)
			{
				int e = r.nextInt(6);
				if (!vOcells.contains(e) || (vOcells.contains(e) && cont.get(e) < 2))
				{
					vCheck.add(0);
					vOcells.add(e);
					int n = cont.get(e);
					cont.set(e, ++n);
					b = true;
				}
			}
		}
		Log.v(LOG,""+vOcells);
	}
	
	
	
	private void tweet_missage(final String text)
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
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
  
        
    	/* Enable full screen mode */
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Creacio del dialog final    
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("Win!")
	    .setCancelable(false)
	    .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) 
	    {
	    	// Funcio reset
	    	Intent intent = getIntent();
	    	finish();
	    	startActivity(intent);
	       
	    }}).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
	            public void onClick(DialogInterface dialog, int which) 
                {
	                 // TODO Auto-generated method stub
                	MemoryActivity.this.finish();
	            }
	  }).setNeutralButton("Tweet",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			tweet_missage("I did " + Errors + " error in " + milis/1000 + " seconds on SrProject");
        	
		}
	});
	  alert = builder.create();
	      
	      
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory);



       
        Checked = 0;
        Blocked = 0;
        Errors = 0;
        iniciar_ocellots();
        StartTime= (double) System.currentTimeMillis();
        ImageView mem00 = (ImageView) findViewById(R.id.mem00);
        ImageView mem01 = (ImageView) findViewById(R.id.mem01);
        ImageView mem02 = (ImageView) findViewById(R.id.mem02);
        ImageView mem03 = (ImageView) findViewById(R.id.mem03);
        ImageView mem10 = (ImageView) findViewById(R.id.mem10);
        ImageView mem11 = (ImageView) findViewById(R.id.mem11);
        ImageView mem12 = (ImageView) findViewById(R.id.mem12);
        ImageView mem13 = (ImageView) findViewById(R.id.mem13);
        ImageView mem20 = (ImageView) findViewById(R.id.mem20);
        ImageView mem21 = (ImageView) findViewById(R.id.mem21);
        ImageView mem22 = (ImageView) findViewById(R.id.mem22);
        ImageView mem23 = (ImageView) findViewById(R.id.mem23);
        MyImageClick mIC = new MyImageClick();
        mem00.setOnTouchListener(mIC);
        mem01.setOnTouchListener(mIC);
        mem02.setOnTouchListener(mIC);
        mem03.setOnTouchListener(mIC);
        mem10.setOnTouchListener(mIC);
        mem11.setOnTouchListener(mIC);
        mem12.setOnTouchListener(mIC);
        mem13.setOnTouchListener(mIC);
        mem20.setOnTouchListener(mIC);
        mem21.setOnTouchListener(mIC);
        mem22.setOnTouchListener(mIC);
        mem23.setOnTouchListener(mIC);
    }
    
    // CREACIO DEL MENUUUUUUUUUUU (CASETA)	
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch (item.getItemId()) {
    		case R.id.item1:
    			Intent intentt = new Intent(parentAct, RankingALLActivity.class);
    			startActivity(intentt);
    			return true;
    		case R.id.item2:
    			Intent intent = getIntent();
    	    	finish();
    	    	startActivity(intent);
    			return true;
    			
    		case R.id.item3:
    			milis=System.currentTimeMillis()-StartTime; 
    	    	CharSequence text = "You have "+Errors+" errors in "+(milis/1000)+" seconds.";
    	    	show_toast(text);	
    			return true;
    		default:
    			break;		
    	}
    	return false;
    }
    
    void show_toast(CharSequence text)
    {
		Context context = getApplicationContext();
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }

    private int get_ocell(int n)
    {
    	if (n == 0) return drawable.ocellgroc;
    	if (n == 1) return drawable.ocellverd;
    	if (n == 2) return drawable.ocellblau;
    	if (n == 3) return drawable.ocellvermell;
    	if (n == 4) return drawable.ocellbomba;
    	if (n == 5) return drawable.porquetverd;

    	else return drawable.porquetverd;
    }
   
    
    
    public class MyImageClick implements OnTouchListener
    {
    	private void translatePosition(Integer act) {
    		// TODO Auto-generated method stub
    		switch(act) {
    			case 0:
    				ImageView mem00 = (ImageView) findViewById(R.id.mem00);
    				mem00.setImageResource(drawable.nogirada);
    				break;
    			case 1:
    				ImageView mem01 = (ImageView) findViewById(R.id.mem01);
    				mem01.setImageResource(drawable.nogirada);
    				break;
    			case 2:
    				ImageView mem02 = (ImageView) findViewById(R.id.mem02);
    				mem02.setImageResource(drawable.nogirada);
    				break;
    			case 3:
    				ImageView mem03 = (ImageView) findViewById(R.id.mem03);
    				mem03.setImageResource(drawable.nogirada);
    				break;
    			case 4:
    				ImageView mem10 = (ImageView) findViewById(R.id.mem10);
    				mem10.setImageResource(drawable.nogirada);
    				break;
    			case 5:
    				ImageView mem11 = (ImageView) findViewById(R.id.mem11);
    				mem11.setImageResource(drawable.nogirada);
    				break;
    			case 6:
    				ImageView mem12 = (ImageView) findViewById(R.id.mem12);
    				mem12.setImageResource(drawable.nogirada);
    				break;
    			case 7:
    				ImageView mem13 = (ImageView) findViewById(R.id.mem13);
    				mem13.setImageResource(drawable.nogirada);
    				break;
    			case 8:
    				ImageView mem20 = (ImageView) findViewById(R.id.mem20);
    				mem20.setImageResource(drawable.nogirada);
    				break;
    			case 9:
    				ImageView mem21 = (ImageView) findViewById(R.id.mem21);
    				mem21.setImageResource(drawable.nogirada);
    				break;
    			case 10:
    				ImageView mem22 = (ImageView) findViewById(R.id.mem22);
    				mem22.setImageResource(drawable.nogirada);
    				break;	
    			case 11:
    				ImageView mem23 = (ImageView) findViewById(R.id.mem23);
    				mem23.setImageResource(drawable.nogirada);
    				break;

    		}
    		
    		
    		
    	}
    	
    	private Integer search_destapada(Integer act) {
    		// TODO Auto-generated method stub
    		for (int i = 0; i < vCheck.size();++i) 
    		{
    			if (vCheck.get(i) == 1 && i != act) return i;
    		}
    		//Mai aribarem aqui derp. Si arribem som uns desgraciats
    		return null;
    	}
    	
    	private void CheckStatus (final Integer act) {
    		
    		// MIRAR DESTAPADA
    		// Estic 100% segur que hi ha una altre carta amb destapada (vCheck(X) == 1);
    		final Integer d = search_destapada(act);
    		Log.v("Destapada",d+"");
    		if (vOcells.get(d) == vOcells.get(act)) {
    			// HEM TRIUNFAT AMICS
    			Log.v("HE TRIUNFAT",act+" = "+d);
    			vCheck.set(d, 2);
    			vCheck.set(act,2);
    			Log.v("FUCK",vCheck+"");
    		} else {
    			// LES TAPEM COM A BONS PERDEDORS
    			vCheck.set(d, 0);
    			vCheck.set(act,0);
    			Blocked = 1;
    			Errors += 1;
				// S'ajunta el thread a qualsevol element de la vista...
    			ImageView mem23 = (ImageView) findViewById(R.id.mem23);
				mem23.postDelayed(new Runnable() {
    		        public void run() {
//    	    			android.os.SystemClock.sleep(2000);
    	    			translatePosition(d);
    	    			translatePosition(act);
    	    			Blocked = 0;
    		        }}, 1000);
    			    			
    		}
    		Checked = 0;
    	}

		@Override
		public boolean onTouch(View v, MotionEvent event) 
		{
			// TODO Auto-generated method stub
			// Vocells { 00, 01, 02, 03, 10, 11, 12, 13, 20, 21, 22, 23 }
			Log.v(LOG, "imatge");
			int e;
			
			RotateAnimation anim = new RotateAnimation(350f,0f , 15f, 15f);
			anim.setInterpolator(new LinearInterpolator());
			anim.setRepeatCount(0);
			anim.setDuration(400);

	
			switch (v.getId())
			{
			case R.id.mem00:
				if (vCheck.get(0) == 0 && Blocked == 0) {
					Integer act = 0;
					ImageView mem00 = (ImageView) findViewById(R.id.mem00);
					mem00.startAnimation(anim);
		        	e = get_ocell(vOcells.get(act));
					mem00.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
				
			case R.id.mem01:
				if (vCheck.get(1) == 0  && Blocked == 0) {
					Integer act = 1;
					ImageView mem01 = (ImageView) findViewById(R.id.mem01);
					mem01.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem01.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors

				}
				break;
				
			case R.id.mem02:
				if (vCheck.get(2) == 0  && Blocked == 0) {
					Integer act = 2;
					ImageView mem02 = (ImageView) findViewById(R.id.mem02);
					mem02.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem02.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
				
			case R.id.mem03:
				if (vCheck.get(3) == 0  && Blocked == 0) {
					Integer act = 3;
					ImageView mem03 = (ImageView) findViewById(R.id.mem03);
					mem03.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem03.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
				
			case R.id.mem10:
				if (vCheck.get(4) == 0 && Blocked == 0) {
					Integer act = 4;
					ImageView mem10 = (ImageView) findViewById(R.id.mem10);
					mem10.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem10.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
				
			case R.id.mem11:
				if (vCheck.get(5) == 0 && Blocked == 0) {
					Integer act = 5;
					ImageView mem11 = (ImageView) findViewById(R.id.mem11);
					mem11.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem11.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
			
			case R.id.mem12:
				if (vCheck.get(6) == 0 && Blocked == 0) {
					Integer act = 6;
					ImageView mem12 = (ImageView) findViewById(R.id.mem12);
					mem12.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem12.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
			
			case R.id.mem13:
				if (vCheck.get(7) == 0 && Blocked == 0) {
					Integer act = 7;
					ImageView mem13 = (ImageView) findViewById(R.id.mem13);
					mem13.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem13.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
				
			case R.id.mem20:
				if (vCheck.get(8) == 0 && Blocked == 0) {
					Integer act = 8;
					ImageView mem20 = (ImageView) findViewById(R.id.mem20);
					mem20.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem20.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
				
			case R.id.mem21:
				if (vCheck.get(9) == 0 && Blocked == 0) {
					Integer act = 9;
					ImageView mem21 = (ImageView) findViewById(R.id.mem21);
					mem21.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem21.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
				
			case R.id.mem22:
				if (vCheck.get(10) == 0 && Blocked == 0) {
					Integer act =10;
					ImageView mem22 = (ImageView) findViewById(R.id.mem22);
					mem22.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem22.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				break;
				
			case R.id.mem23:
				if (vCheck.get(11) == 0 && Blocked == 0) {
					Integer act = 11;
					ImageView mem23 = (ImageView) findViewById(R.id.mem23);
					mem23.startAnimation(anim);
				    e = get_ocell(vOcells.get(act));
					mem23.setImageResource(e);
					vCheck.set(act, 1);
					if (Checked == 1) {
						CheckStatus(act);
					} else Checked = 1;
//				Comprovar si som uns triunfadors
				}
				
				break;
				
			}
			win();
			Log.v(LOG,Checked+"");
			Log.v(LOG,vCheck+"");
			return false;
		}

		private void win() 
		{
			// TODO Auto-generated method stub
    		for (int i = 0; i < vCheck.size();++i) {
    			if (vCheck.get(i) != 2) return;
    		}
    		
    		 mp = MediaPlayer.create(parentAct, R.raw.winlevel);
    		try 
    		{
    			mp.prepare();
    		} catch (Exception e){}
    		mp.start();
    		
    		
        	milis=System.currentTimeMillis()-StartTime; 
        	alert.setMessage("You did "+Errors+" errors in "+(milis/1000)+" seconds.");
        	alert.show();
			// Cal guardar a la base de dades
        	DataBase db = new DataBase(parentAct);
        	SQLiteDatabase sdb = db.getWritableDatabase();
        	double time =  (milis/10000);
        	    	
        	if (sdb != null) {
            	SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_WORLD_WRITEABLE);
            	String NAME_USER = prefs.getString("nom_user", null);
        	    sdb.execSQL("INSERT INTO Memory (user, tries, time) VALUES ('" + NAME_USER + "'," + Errors + "," + time  +")");
        	    sdb.close();
        	}
        	db.close();   
		}
    }
}