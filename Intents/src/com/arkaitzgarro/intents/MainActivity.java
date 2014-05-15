package com.arkaitzgarro.intents;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final static int CAMERA_CODE = 1;
	private final static int FRM_CODE = 2;
	private final static int PICK_CONTACT = 3;

	private Button btnCamera, btnFrm, btnContact;
	private Bitmap mImageBitmap;
	private ImageView mImageView;
	private EditText editText;
	private TextView textView;
	
	private String mCurrentPhotoPath;
	
	private File image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnCamera = (Button) findViewById(R.id.btnCamera);
		btnFrm = (Button) findViewById(R.id.btnFrm);
		btnContact = (Button) findViewById(R.id.btnContact);
		mImageView = (ImageView) findViewById(R.id.imageView);
		editText = (EditText) findViewById(R.id.mainEditText);
		textView = (TextView) findViewById(R.id.mainTextView);

		btnCamera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				// Get a thumbnail
//				if(getPackageManager().hasSystemFeature(getPackageManager().FEATURE_CAMERA)) {
//					if (camera.resolveActivity(getPackageManager()) != null) {
//				        startActivityForResult(camera, CAMERA_CODE);
//				    }
//				}
				
				File photoFile = null;
		        try {
		            photoFile = createImageFile();
		        } catch (IOException ex) {
		            Log.d("INTENT", "No se ha podido crear el fichero.");
		            Log.d("INTENT", ex.toString());
		        }
		        
		        // Continue only if the File was successfully created
		        if (photoFile != null) {
		            camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
		            startActivityForResult(camera, CAMERA_CODE);
		        }
			}
		});

		btnFrm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent frm = new Intent(MainActivity.this, FormActivity.class);
				frm.putExtra(FormActivity.EMAIL_FIELD, editText.getText().toString());
				startActivityForResult(frm, FRM_CODE);
			}
		});

		btnContact.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == CAMERA_CODE) {
				// Show bitmap
				showBitmap(data);
				
				// Show scaled image
				// setPic();
			} else if (requestCode == FRM_CODE) {
				String email = data.getStringExtra(FormActivity.EMAIL_FIELD);
				textView.setText(email);
			} else if (requestCode == PICK_CONTACT) {
				showContact(data);
			}
		} else {		
			textView.setText(R.string.canceled);
		}
	}
	
	private void showBitmap(Intent data) {
		Bundle extras = data.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");
		mImageView.setImageBitmap(mImageBitmap);
	}
	
	private void showContact(Intent data) {
		Cursor cursor = null;
		String email = "";
		try {
			Uri result = data.getData();
			Log.i("INTENT",
					"Got a contact result: " + result.toString());

			// Get the contact id from the Uri
			String id = result.getLastPathSegment();

			// query for everything email
			cursor = getContentResolver().query(Email.CONTENT_URI,
					null, Email.CONTACT_ID + "=?", new String[] { id },
					null);

			int emailIdx = cursor.getColumnIndex(Email.DATA);

			// let's just get the first email
			if (cursor.moveToFirst()) {
				email = cursor.getString(emailIdx);
				Toast t = Toast.makeText(this, email,
						Toast.LENGTH_SHORT);
				t.show();
			} else {
				Toast t = Toast.makeText(this, "No email.",
						Toast.LENGTH_SHORT);
				t.show();
			}
		} catch (Exception e) {
			Log.e("INTENT", "Failed to get email data", e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStorageDirectory();
	    image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
	
	private void setPic() {
	    // Get the dimensions of the View
	    int targetW = mImageView.getWidth();
	    int targetH = mImageView.getHeight();

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(image.getPath(), bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), bmOptions);
	    mImageView.setImageBitmap(bitmap);
	}
}
