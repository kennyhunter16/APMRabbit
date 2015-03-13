package com.hunterit.apmrabbit;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity  {

	public static final String KEY_PREF_SYNC_CONN = "videoMode";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("APMRabbit GoPro Settings");
		addPreferencesFromResource(R.xml.preferences);
	}

	
	
	}

