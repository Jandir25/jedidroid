package org.paquete;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/* Ejemplo de Dialogs */

public class DialogActivity extends Activity {
	/* Creamos 3 variables para definir 3 posibles dialogos diferentes */
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	static final int DIALOG_CHECKS_ID = 2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* Definimos los listener para que cada boton muestre un dialog diferente */
        Button boton = (Button) findViewById(R.id.button1);
        boton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DIALOG_PAUSED_ID);
				
			}
		});
        
        Button boton2 = (Button) findViewById(R.id.button2);
        boton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DIALOG_GAMEOVER_ID);
			}
		});
        
        Button boton3 = (Button) findViewById(R.id.button3);
        boton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DIALOG_CHECKS_ID);
				
			}
		});
    }
    
    /* Funcion que se ejecuta al pulsar la tecla de 'Atras' */
    public void onBackPressed() {
    	showDialog(DIALOG_PAUSED_ID);
    }
    
    /* Selectora del dialogo, esta funcion es llamada al ejecutar 'showDialog' */
    protected Dialog onCreateDialog(int id) {
    	Dialog dialog = null;
    	switch (id) {
    	case DIALOG_PAUSED_ID:
    		dialog = createDialog();
    		break;
    		
    	case DIALOG_GAMEOVER_ID:
    		dialog = createDialogLista();
    		break;
    		
    	case DIALOG_CHECKS_ID:
    		dialog = createDialogListaChecks();
    		break;
    	}
    	return dialog;
    }
    
    /* Creadora de un dialogo */
    public Dialog createDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Salir?");
    	builder.setMessage("Estas seguro de que quieres salir?");
    	builder.setCancelable(false);
    	builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			DialogActivity.this.finish();
    		}
    	});
    	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			dialog.cancel();
    		}
    	});
    	
    	AlertDialog alert = builder.create();
    	return alert;
    }   
    
    /* Creadora de un dialogo con lista */
    public Dialog createDialogLista() {
    	final CharSequence[] items = {"Red", "Green", "Blue"};
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Pick a color");
    	builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			}
		});
    	
    	    	
    	AlertDialog alert = builder.create();
    	return alert;
    }   
    
    /* Creadora de un dialogo con lista de checks */
    public Dialog createDialogListaChecks() {
    	final CharSequence[] items = {"Red", "Green", "Blue"};
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Pick a color");
    	builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			}
		});
    	
    	    	
    	AlertDialog alert = builder.create();
    	return alert;
    }    
}

