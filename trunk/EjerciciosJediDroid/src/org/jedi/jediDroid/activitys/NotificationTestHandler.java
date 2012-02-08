package org.jedi.jediDroid.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/* Ejemplo hecho por el profesor de una progress bar */

/* Hacer una progresbar con threads */
public class NotificationTestHandler extends Activity {
	static final int PROGRESS_DIALOG = 0;
	Button button;
	ProgressThread progressThread;
	ProgressDialog progressDialog;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Setup the button that starts the progress dialog
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(PROGRESS_DIALOG);
			}
		});
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			progressDialog = new ProgressDialog(NotificationTestHandler.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMessage("Loading...");
			return progressDialog;
		default:
			return null;
		}
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.arg1;
			progressDialog.setProgress(total);
			if (total >= 100) {
				dismissDialog(PROGRESS_DIALOG);
				progressThread.setState(ProgressThread.STATE_DONE);
			}
		}
	};

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case PROGRESS_DIALOG:
			progressDialog.setProgress(0);
			progressThread = new ProgressThread(handler);
			progressThread.start();
		}

		// Define the Handler that receives messages from the thread and update
		// the progress

		/** Nested class that performs progress calculations (counting) */
	}
}

class ProgressThread extends Thread {
	Handler mHandler;
	final static int STATE_DONE = 0;
	final static int STATE_RUNNING = 1;
	int mState;
	int total;

	ProgressThread(Handler h) {
		mHandler = h;
	}

	public void run() {
		mState = STATE_RUNNING;
		total = 0;
		while (mState == STATE_RUNNING) {
			try {
				/* Es on es fa la accio */
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Log.e("ERROR", "Thread Interrupted");
			}
			Message msg = mHandler.obtainMessage();
			msg.arg1 = total;
			mHandler.sendMessage(msg);
			total++;
		}
	}

	/*
	 * sets the current state for the thread, used to stop the thread
	 */
	public void setState(int state) {
		mState = state;
	}
}
