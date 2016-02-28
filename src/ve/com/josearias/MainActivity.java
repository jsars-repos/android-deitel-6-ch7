package ve.com.josearias;

import android.support.v7.app.AppCompatActivity;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private List<Weather> weatherList = new ArrayList<>();
	
	private WeatherArrayAdapter weatherArrayAdapter;
	private ListView weatherListView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		weatherListView = (ListView) findViewById(R.id.weatherListView);
		weatherArrayAdapter = new WeatherArrayAdapter(this, weatherList);
		weatherListView.setAdapter(weatherArrayAdapter);
		
		Button forecastButton = (Button) findViewById(R.id.forecastButton);
		
		forecastButton.setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View view) 
			{
				EditText locationEditText = 
						(EditText) findViewById(R.id.locationEditText);
				
				URL url = createURL(locationEditText.getText().toString());
				
				if (url != null)
				{
					dismissKeyboard(locationEditText);
					/*
					GetWeatherTask getLocalWeatherTask = new GetWeatherTask();
					getLocalWeatherTask.execute(url);
					*/
				}
				else
				{
					Toast.makeText(MainActivity.this, 
							R.string.invalid_url,
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void dismissKeyboard(View view)
	{
		InputMethodManager imm = 
				(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);		
	}
	
	private URL createURL(String city)
	{
		String apiKey = getString(R.string.api_key);
		String baseUrl = getString(R.string.web_service_url);
		
		try
		{
			String urlString = baseUrl + URLEncoder.encode(city, "UTF-8") + 
					"&units=metric&cnt=16&APPID="  + apiKey;
			return new URL(urlString);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
}
