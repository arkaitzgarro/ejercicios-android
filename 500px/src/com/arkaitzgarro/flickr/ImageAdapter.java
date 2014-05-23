package com.arkaitzgarro.flickr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // references to our images
    private Image[] images;
    private String uri;
    private DrawableManager dm;

    public ImageAdapter(Context c, String uri) {
        mContext = c;
        this.uri = uri;
        this.dm = new DrawableManager();
        
        Thread t = new Thread(new Runnable() {
			public void run() {
				get500pxImages();
			}
		});
        
        try {
        	t.start();
        	t.wait();
        	this.notifyDataSetChanged();
        } catch(Exception e) {
        	Log.d("500PX", e.getMessage());
        }
		
    }

    public int getCount() {
    	if(images == null) {
    		return 0;
    	}
    	
        return images.length;
    	//return 0;
    }

    public Image getItem(int position) {
        return images[position];
    }

    public long getItemId(int position) {
        return images[position].getId();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        imageView.setImageDrawable(dm.fetchDrawable(images[position].getUrl()));
        return imageView;
    }
    
    public void get500pxImages() {
		JSONObject json;
		
		try {
		  URL url = new URL(uri);

		  // Create a new HTTP URL connection
		  URLConnection connection = url.openConnection();
		  HttpURLConnection httpConnection = (HttpURLConnection)connection;

		  int responseCode = httpConnection.getResponseCode();
		  if (responseCode == HttpURLConnection.HTTP_OK) {
			  try {
				  //JSONObject json = new JSONObject(httpConnection.getInputStream().toString());
				  BufferedReader streamReader =
						  new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8")); 
				  StringBuilder responseStrBuilder = new StringBuilder();

				  String inputStr;
				  while ((inputStr = streamReader.readLine()) != null)
				      responseStrBuilder.append(inputStr);
				  
				  json = new JSONObject(responseStrBuilder.toString());
				  
				  JSONArray imagesFields = json.getJSONArray("photos");
				  images = new Image[imagesFields.length()];
				  
				  for (int i = 0; i < imagesFields.length(); i++) {
                      JSONObject image = imagesFields.getJSONObject(i);

                      images[i] = new Image(image.getInt("id"), image.getString("name"), image.getString("image_url"));
                      
                      Log.d("DOWNLOADS", images[i].toString());
                  }
			  } catch(JSONException e) {
				  Log.e("500PX", "Error al leer el fichero JSON: "+e.getMessage());
			  }
		  }
		}
		catch (MalformedURLException e) {
		  Log.d("500PX", "Malformed URL Exception.", e);
		}
		catch (IOException e) {
		  Log.d("500PX", "IO Exception.", e);
		}
	}
}
