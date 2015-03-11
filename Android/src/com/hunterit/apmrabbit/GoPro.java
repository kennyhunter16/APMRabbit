package com.hunterit.apmrabbit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
 
public class GoPro extends Fragment implements OnClickListener{
	
	ImageButton videoMode, photoMode, burstMode, timelapseMode;
	Button startrec, stoprec;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_gopro, container, false);
        
        //Declare the Buttons
        videoMode =(ImageButton) rootView.findViewById(R.id.videoMode);
        photoMode =(ImageButton) rootView.findViewById(R.id.photoMode);
        burstMode =(ImageButton) rootView.findViewById(R.id.burstMode);
        timelapseMode =(ImageButton) rootView.findViewById(R.id.timelapseMode);
        startrec =(Button) rootView.findViewById(R.id.startrec);
        stoprec =(Button) rootView.findViewById(R.id.stoprec);
        
        //Declare Listeners
        videoMode.setOnClickListener(this);
        photoMode.setOnClickListener(this);
        burstMode.setOnClickListener(this);
        timelapseMode.setOnClickListener(this);
        startrec.setOnClickListener(this);
        stoprec.setOnClickListener(this);
 
        return rootView;
    }
        
       
    	public void onClick(View v) {
        	switch(v.getId()) {
            case R.id.videoMode:  //Change Mode to Video
          	  new HttpAsyncTask().execute("http://10.5.5.9/camera/CM?t=APMRover&p=%00");
              break;
            case R.id.photoMode:  //Change Mode to Photo
               new HttpAsyncTask().execute("http://10.5.5.9/camera/CM?t=APMRover&p=%01");
              break;
            case R.id.burstMode:  //Change Mode to Burst
            	 new HttpAsyncTask().execute("http://10.5.5.9/camera/CM?t=APMRover&p=%02");
                break;
            case R.id.timelapseMode:  //Change Mode to Time Lapse
            	 new HttpAsyncTask().execute("http://10.5.5.9/camera/CM?t=APMRover&p=%03");
                break;
            case R.id.startrec:  //Take a Picture / Start Recording
            	new HttpAsyncTask().execute("http://10.5.5.9/bacpac/SH?t=APMRover&p=%01");
              break;
            case R.id.stoprec:  //Stop Recording / End Timelapse
            	new HttpAsyncTask().execute("http://10.5.5.9/bacpac/SH?t=APMRover&p=%00");
                break;
        	}
    	

    }
    
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
        	HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
        	HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
        	inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
 
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
 
    @SuppressWarnings("unused")
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            return GET(urls[0]);
        }
       
}

}