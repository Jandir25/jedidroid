package org.jediDroid.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BBDD extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "jediDroid";
	private static final String DICTIONARY_TABLE_NAME = "ranking";
	private static final String DICTIONARY_TABLE_CREATE = 
			"CREATE TABLE " + DICTIONARY_TABLE_NAME +
			" (id INTEGER PRIMARY KEY AUTOINCREMENT,  usuari TEXT,  fallos INTEGER)";
	
	public BBDD(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DICTIONARY_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void insertNewPartida(Context context, String usuari, int fallos) {
		BBDD db = new BBDD(context);
		SQLiteDatabase instantDb = db.getReadableDatabase();
		/* Para ejecutar una query que no retorne valor */
		/* Insertamos una nueva partida en el ranking */
		instantDb.execSQL("INSERT INTO ranking(usuari, fallos) VALUES ('" + usuari + "', " + fallos + ");");
	}
	
	
	
}
