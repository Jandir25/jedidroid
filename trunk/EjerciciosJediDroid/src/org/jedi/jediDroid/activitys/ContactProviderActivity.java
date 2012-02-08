package org.jedi.jediDroid.activitys;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/* Ejemplo de como mostrar una lista de los contactos del móvil */

public class ContactProviderActivity extends Activity {
		private static final String TAG = "ContentUserDemo";
		
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			/* Mostramos el layout correspondiente */
			setContentView(R.layout.contact_provider);
			
			/* Get content provider and cursor */
			ContentResolver cr = getContentResolver();
			Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "DISPLAY_NAME ASC");
			
			/* Let activity manage the cursor */
			startManagingCursor(cursor);
			Log.d(TAG, "cursor.getCount()=" + cursor.getCount());
			
			/* Get the list view */
			ListView listView = (ListView) findViewById(R.id.listContacts);
			/* Pedimos las columnas 'Nombre del contacto' y 'La última vez que contactemos' */
			String[] from = { ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.LAST_TIME_CONTACTED };
			/* Definimos en que TextView deben de ir */
			int[] to = { R.id.contactProviderElementName, R.id.contactProviderElementData };
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.contact_proider_element, cursor, from, to);
	        listView.setAdapter(adapter);
	        
	        /* Definimos el context menu que se le aplicará a cada elemento de la lista */
	        registerForContextMenu(listView);

		}
		
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Log.d(TAG, "onCreateContextMenu");
 			super.onCreateContextMenu(menu, v, menuInfo);
 			MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contextmenu, menu);
	}
}
