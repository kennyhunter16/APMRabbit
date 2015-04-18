package com.hunterit.APMRabbit;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GoPro extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    ImageButton videoMode, photoMode, burstMode, timelapseMode;
    Button startrec, stoprec;
    VideoView stream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gopro, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        //Declare the Buttons
        videoMode = (ImageButton) rootView.findViewById(R.id.videoMode);
        photoMode = (ImageButton) rootView.findViewById(R.id.photoMode);
        burstMode = (ImageButton) rootView.findViewById(R.id.burstMode);
        timelapseMode = (ImageButton) rootView.findViewById(R.id.timelapseMode);
        startrec = (Button) rootView.findViewById(R.id.startrec);
        stoprec = (Button) rootView.findViewById(R.id.stoprec);
        stream = (VideoView) rootView.findViewById(R.id.videoView1);

        //Declare Listeners
        videoMode.setOnClickListener(this);
        photoMode.setOnClickListener(this);
        burstMode.setOnClickListener(this);
        timelapseMode.setOnClickListener(this);
        startrec.setOnClickListener(this);
        stoprec.setOnClickListener(this);

        String streamURL = "http://10.5.5.9:8080/live/amba.m3u8";
        //Start the Stream
        stream.setVideoPath(streamURL);
        stream.start();

        return rootView;
    }

    	/* Find correct mode based on button pressed by user */

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.videoMode:  //Change Mode to Video
                goProAction("camera", "CM", "00");
                break;
            case R.id.photoMode:  //Change Mode to Photo
                goProAction("camera", "CM", "01");
                break;
            case R.id.burstMode:  //Change Mode to Burst
                goProAction("camera", "CM", "02");
                break;
            case R.id.timelapseMode:  //Change Mode to Time Lapse
                goProAction("camera", "CM", "03");
                break;
            case R.id.startrec:  //Take a Picture / Start Recording
                goProAction("bacpac", "SH", "01");
                break;
            case R.id.stoprec:  //Stop Recording / End Timelapse
                goProAction("bacpac", "SH", "00");
                break;
        }


    }


    /**
     * goProAction
     * <p/>
     * - Creates a link for the action based on the password
     * and link requirements
     *
     * @param function - get the first mode of the camera
     * @param mode     - the short form URL link
     * @param action   - the real action. read GoPro URL for more detail
     */

    public void goProAction(String function, String mode, String action) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String password = preferences.getString("goProPassword", "NULL");

        StringBuilder url = new StringBuilder();
        url.append("http://10.5.5.9/");
        url.append(function + "/");
        url.append(mode + "?t=");
        url.append(password + "&p=%");
        url.append(action);
        String urlLink = url.toString();

        new HttpAsyncTask().execute(urlLink);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {

        int value;

        if (key.equals("goProPassword") || key.equals("Location")) value = 25;
        else value = Integer.parseInt(key);

        String result = sharedPreferences.getString(key, "NULL");

        switch (value) {
            case 0:  //Video Resolutions
                goProAction("camera", "VV", result);
                break;
            case 1:  //Auto Low Light
                goProAction("camera", "LW", result);
                break;
            case 2: //ISO Gain
                goProAction("camera", "GA", result);
                break;
            case 3: //Colour Setting
                goProAction("camera", "CO", result);
                break;
            case 4: //Sharpness
                goProAction("camera", "SP", result);
                break;
            case 5: //Exposure
                goProAction("camera", "EV", result);
                break;
            case 6: //White Balance
                goProAction("camera", "WB", result);
                break;
            case 7: //Orientation
                goProAction("camera", "UP", result);
                break;
            case 8: //FOV
                goProAction("camera", "FV", result);
                break;
            case 9: //Photo Def
                goProAction("camera", "PR", result);
                break;
            case 10: //Timelapse
                goProAction("camera", "TI", result);
                break;
            case 11: //Volume
                goProAction("camera", "BS", result);
                break;
            case 12: //Loop Video
                goProAction("camera", "LO", result);
                break;
            case 13: //Protune
                goProAction("camera", "PT", result);
                break;
            case 14: //LEDS
                goProAction("camera", "LB", result);
                break;
            case 15: //Spot Meter
                goProAction("camera", "EX", result);
                break;
            case 16: //One Button
                goProAction("camera", "OB", result);
                break;
            case 17: //Power Off
                goProAction("camera", "AO", result);
                break;
            case 18: //Default Mode
                goProAction("camera", "DM", result);
                break;
            case 19: //On Screen Display
                goProAction("camera", "OS", result);
                break;
            case 20: //locate
                goProAction("camera", "LL", result);
                break;
            case 21: //Video Mode HDMI
                goProAction("camera", "VM", result);
                break;
            case 22: //Continuous Shot
                goProAction("camera", "CS", result);
                break;
            case 23: //Continuous Shot
                goProAction("camera", "BU", result);
                break;
            case 24: //Delete Files
                goProAction("camera", "DL", result);
                break;
            case 25: //GoProPassword
                break;
        }

    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    /**
     * HttpAsyncTask
     * <p/>
     * - Send the URL to the GoPro for the Command Action
     */

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
    }

    public Boolean isOnline(String url) {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + url);
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}