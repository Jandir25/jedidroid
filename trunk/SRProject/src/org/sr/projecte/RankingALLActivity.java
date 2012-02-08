package org.sr.projecte;


import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class RankingALLActivity extends Activity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rankingrmem);
        
        Button bClose = (Button) findViewById(R.id.bcloseRank);
        bClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				RankingALLActivity.this.finish();			
			}
		});
        
        
        final ListView lRank= (ListView) findViewById(R.id.listrank);

        DataBase db = new DataBase(this);
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor;
        if (sdb != null) 
        {
        	cursor = sdb.rawQuery("SELECT user, tries, time FROM Memory  ORDER BY tries DESC",null);
        	ArrayList<String> aRank = new ArrayList<String>();
        	
        	int i = 1;
        	while (cursor.moveToNext())
        	{
        		double time = cursor.getDouble(cursor.getColumnIndex("time"));
        		int error = cursor.getInt(cursor.getColumnIndex("tries"));
        		String user = String.valueOf(cursor.getString(cursor.getColumnIndex("user")));
                String from = i+ "  " + user + " " + error + " errors in " + time + " seconds";
                aRank.add(from);
                ++i;        
        	}
            ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getApplicationContext(), R.layout.rowrankg,aRank);
            lRank.setAdapter(adapter);
         }
        db.close();
        sdb.close();
    }
}
