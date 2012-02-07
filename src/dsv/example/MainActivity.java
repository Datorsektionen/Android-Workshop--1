package dsv.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ProgressBar progress;
	Button downloadButton;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        progress = (ProgressBar) findViewById(R.id.progress);
        downloadButton = (Button) findViewById(R.id.download_button);
        
        downloadButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				new DownloadGoogleTask().execute();
			}
        });
        
                
    }
    public class DownloadGoogleTask extends AsyncTask<Void, String, String> {

    	@Override
    	protected void onPreExecute() {
    		progress.setVisibility(View.VISIBLE);
    		downloadButton.setVisibility(View.GONE);
    	}
    	
		@Override
		protected String doInBackground(Void... params) {

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://google.com");
			
			String result = null;
			try {
				HttpResponse response = client.execute(get);
				result = convertStreamToString(response.getEntity().getContent());
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String res) {
	   		progress.setVisibility(View.GONE);
    		downloadButton.setVisibility(View.VISIBLE);
    		
    		if(res != null) {
    			Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
    			intent.putExtra("text", res);
    			startActivity(intent);
    		} else {
    			Toast.makeText(getApplicationContext(), "Download failed", Toast.LENGTH_SHORT).show();
    		}
		}
    	
    }
    
    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {}
        }
        return sb.toString();
    }
}