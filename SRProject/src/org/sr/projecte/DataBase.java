package org.sr.projecte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
	
	private static final String MEMORY_TABLE_NAME = "Memory";
	private static int DATABASE_VERSION = 1;
	private static final String MEMORY_TABLE = "CREATE TABLE " + MEMORY_TABLE_NAME + " (user TEXT, tries INTEGER, time REAL)";

	
	public DataBase(Context context) {
		super(context,MEMORY_TABLE_NAME,null,DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MEMORY_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	

}
