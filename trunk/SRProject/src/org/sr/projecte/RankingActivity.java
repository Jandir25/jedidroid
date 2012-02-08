package org.sr.projecte;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class RankingActivity extends Activity {
	
	
	public static final String PREF_NAME = "MYPREFFILE";

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	

        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rankingr);
        
        final ListView lRank= (ListView) findViewById(R.id.listrank);

        DataBase db = new DataBase(this);
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor;
        if (sdb != null) {
        	SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_WORLD_WRITEABLE);
        	String NAME_USER = prefs.getString("nom_user", null);
        	cursor = sdb.rawQuery("SELECT user, tries, time FROM Memory WHERE user ='" + NAME_USER + "' ORDER BY tries ASC",null);
        	ArrayList<String> aRank = new ArrayList<String>();
        	
        	int i = 1;

        	while (cursor.moveToNext())
        	{
        		
        		double time = cursor.getDouble(cursor.getColumnIndex("time"));
        		int error = cursor.getInt(cursor.getColumnIndex("tries"));
        		String user = String.valueOf(cursor.getString(cursor.getColumnIndex("user")));
                String from = i+ "  " + user + " " + error + " errors " + time + " seconds";
                aRank.add(from);
                ++i;
//                int[] to = { R.id.Rank,R.id.Name,R.id.Puntuation};
//                SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.rowrank,cursor,from,to);
                
        	}
            ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getApplicationContext(), R.layout.rowrankg,aRank);
            lRank.setAdapter(adapter);
         }
        db.close();
       sdb.close();
     
    }
}
