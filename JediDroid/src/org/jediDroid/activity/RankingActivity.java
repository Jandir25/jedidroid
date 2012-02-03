package org.jediDroid.activity;


import org.jediDroid.domain.BBDD;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class RankingActivity extends Activity{
	protected static final String LOG = "JediDroid - RankingActivity";
	
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(LOG,"onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
		
		BBDD db = new BBDD(getApplicationContext());
		SQLiteDatabase instantDb = db.getReadableDatabase();
		Cursor c = instantDb.rawQuery("SELECT id _id, usuari, fallos FROM ranking ORDER BY fallos ASC ", null);
		startManagingCursor(c);
		
		ListView listView = (ListView) findViewById(R.id.listRanking);
		String[] from = {"usuari", "fallos"};
		int[] to = { R.id.nomRankingElement, R.id.puntuacioRankingElement };
		SimpleCursorAdapter sca = new SimpleCursorAdapter(getApplicationContext(), R.layout.ranking_element, c, from, to);
		listView.setAdapter(sca);	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v(LOG, "onResume");
		/* Permitimos el giro de pantalla */
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	}

}
