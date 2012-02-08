package org.sr.projecte;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends Activity 
{
	private String name = "";
	private static final int TAKE_PICTURE = 1;
	private static final int SELECT_PICTURE = 2;

	public static final String PREF_NAME = "MYPREFFILE";
	private String username;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
     	
	
        Button bCam = (Button) findViewById(R.id.getfromcam);
        Button bGal = (Button) findViewById(R.id.getfromgalery);
        MyOnClickListener mOCL = new MyOnClickListener();
        bCam.setOnClickListener(mOCL);
        bGal.setOnClickListener(mOCL);
        
     	SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_WORLD_WRITEABLE);
    	username = prefs.getString("nom_user", null);
    	
        TextView tLog = (TextView) findViewById(R.id.textView2);
        tLog.setText(username);
    	
     
        name = Environment.getExternalStorageDirectory()+ "/media/srproject/" +username+".jpg";
        File f = new File(name);
        if (f.exists())
        {
        	ImageView profile = (ImageView) findViewById(R.id.profileimage);
        	Bitmap avatar = BitmapFactory.decodeFile(name);
			profile.setImageBitmap(Bitmap.createScaledBitmap(avatar, 250, 250, true));
        }
        else
        {
        	ImageView profile = (ImageView) findViewById(R.id.profileimage);
        	profile.setImageResource(R.drawable.defaultprofile);
        }
    }
	

    
    
    
     public class MyOnClickListener implements OnClickListener
     {
			
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				int code = 0;
				
				switch (v.getId())
				{
					case R.id.getfromcam:
						Uri output = Uri.fromFile(new File(name));
						intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
						code = TAKE_PICTURE;
						break;	
						
					case R.id.getfromgalery:
						intent = new Intent(Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
						code = SELECT_PICTURE;
						break;
				}
				startActivityForResult(intent, code);	
			}
			
     }
			
     public String getRealPathFromURI(Uri contentUri) {
    	// can post image
    	String [] proj={MediaStore.Images.Media.DATA};
    	Cursor cursor = managedQuery( contentUri,
    	proj, // Which columns to return
    	null, // WHERE clause; which rows to return (all rows)
    	null, // WHERE clause selection arguments (none)
    	null); // Order-by clause (ascending by name)
    	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    	cursor.moveToFirst();
    	return cursor.getString(column_index);
    }
     
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
			if (requestCode == TAKE_PICTURE)
			{
				if (data != null)
				{
						if (data.hasExtra("data")) 
						{
								ImageView profile = (ImageView) findViewById(R.id.profileimage);
								profile.setImageBitmap(Bitmap.createScaledBitmap(
										(Bitmap) data.getParcelableExtra("data"), 250, 250,
										true));
						}
				}	
			}
			else if (requestCode == SELECT_PICTURE)
			{
				Uri selectedImage = data.getData();
				String PathtoImage = getRealPathFromURI(selectedImage);
				InputStream is;

				try 
				{
					is = getContentResolver().openInputStream(selectedImage);
					BufferedInputStream bis = new BufferedInputStream(is);
					Bitmap bitmap = BitmapFactory.decodeStream(bis);
					bitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
					ImageView profile = (ImageView) findViewById(R.id.profileimage);
					profile.setImageBitmap(bitmap);

					try {
					    File sd = Environment.getExternalStorageDirectory();
					    File datta = Environment.getDataDirectory();

					    if (sd.canWrite()) {
					        String sourceImagePath= PathtoImage;
	
					        String destinationImagePath= Environment.getExternalStorageDirectory()+ "/media/srproject/" +username+".jpg";
					        File source= new File(sourceImagePath);
					        File destination= new File(destinationImagePath);
					        if (source.exists()) {

					            FileChannel src = new FileInputStream(source).getChannel();
					            FileChannel dst = new FileOutputStream(destination).getChannel();
					            dst.transferFrom(src, 0, src.size());
					            src.close();
					            dst.close();
					        }
					    }
					} catch (Exception e) {}
					
					
				} catch (Exception e) {}		
			}
		 }	
			
     }
	
     
     
     
     
     
     
     
     
	