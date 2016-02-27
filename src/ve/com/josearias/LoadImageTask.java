package ve.com.josearias;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

class LoadImageTask extends AsyncTask<String, Void, Bitmap>
{
	private ImageView imageView;
	private Map<String, Bitmap> bitmaps;

	public LoadImageTask(ImageView imageView, Map<String, Bitmap> bitmaps) 
	{
		 this.imageView = imageView;
		 this.bitmaps = bitmaps;
	}
		
		
	 @Override
	 protected Bitmap doInBackground(String... params)		 
	 {
		 Bitmap bitmap = null;
		 HttpURLConnection connection = null;
	
	 try 
	 {
		 URL url = new URL(params[0]); 
		
		 // open an HttpURLConnection, get its InputStream
		 // and download the image
		 connection = (HttpURLConnection) url.openConnection();
		
		 try (InputStream inputStream = connection.getInputStream())
		 {
				 bitmap = BitmapFactory.decodeStream(inputStream);
				 bitmaps.put(params[0], bitmap);		
		
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
	 }		 
	 catch (Exception e) 
	 {
		 e.printStackTrace();
	 }
	 finally 
	 {
		 connection.disconnect();
	 }
	
	 return bitmap;
	 }
	
	 // set weather condition image in list item
	 @Override
	 protected void onPostExecute(Bitmap bitmap)		 
	 {
		 imageView.setImageBitmap(bitmap);
	 }		
}

