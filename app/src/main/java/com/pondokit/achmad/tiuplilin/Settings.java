package com.pondokit.achmad.tiuplilin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class Settings extends AppCompatActivity implements View.OnClickListener, OnCheckedChangeListener {
	
	View vFlame, vCandle;
	TextView tvFlame, tvCandle,tvTransparency;
	CheckBox cbEnableCustomization,cbEnableTransparency;
	
	public static final String CHOOSE_CANDLE_COLOR="com.pondokit.achmad.tiuplilin.CHOOSE_CANDLE_COLOR";
	public static final String CHOOSE_FLAME_COLOR="com.pondokit.achmad.tiuplilin.CHOOSE_FLAME_COLOR";
	
	public static final String CANDLE_ALPHA="candle_alpha";
	public static final String CANDLE_RED="candle_red";
	public static final String CANDLE_GREEN="candle_green";
	public static final String CANDLE_BLUE="candle_blue";
	
	public static final String FLAME_ALPHA="flame_alpha";
	public static final String FLAME_RED="flame_red";
	public static final String FLAME_GREEN="flame_green";
	public static final String FLAME_BLUE="flame_blue";
	
	SharedPreferences prefs;
	Editor editor;
	
	public static final String ENABLE_TRANSPARENCY="transparency";
	public static final String ENABLE_CUSTOMIZATIONS="customizations";
	
	boolean enabled;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		init();
	}

	private void
	init() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		editor = prefs.edit();
		
		cbEnableCustomization = (CheckBox)findViewById(R.id.settings_checkBox_candle_enable_customizations);
		cbEnableCustomization.setChecked(prefs.getBoolean(Settings.ENABLE_CUSTOMIZATIONS, false));
		cbEnableCustomization.setOnCheckedChangeListener(this);
		
		cbEnableTransparency = (CheckBox)findViewById(R.id.settings_checkBox_candle_enable_transparency);
		cbEnableTransparency.setChecked(prefs.getBoolean(Settings.ENABLE_TRANSPARENCY, false));
		cbEnableTransparency.setOnCheckedChangeListener(this);
		
		tvFlame = (TextView) findViewById(R.id.settings_textView_flame_color);
		tvCandle = (TextView) findViewById(R.id.settings_textView_candle_color);
		tvTransparency = (TextView) findViewById(R.id.settings_textView_enable_transparency);
		
		vFlame = (View) findViewById(R.id.settings_view_flame_color);
		vFlame.setOnClickListener(this);
		
		if(prefs.getBoolean(Settings.ENABLE_TRANSPARENCY, false)){
			vFlame.setBackgroundColor(Color.argb(prefs.getInt(Settings.FLAME_ALPHA, 0),
					prefs.getInt(Settings.FLAME_RED, 0), 
					prefs.getInt(Settings.FLAME_GREEN, 0), 
					prefs.getInt(Settings.FLAME_BLUE, 0)));
		}else{
			vFlame.setBackgroundColor(Color.rgb(prefs.getInt(Settings.FLAME_RED, 0), 
					prefs.getInt(Settings.FLAME_GREEN, 0), 
					prefs.getInt(Settings.FLAME_BLUE, 0)));
		}

		vCandle = (View) findViewById(R.id.settings_view_candle_color);
		vCandle.setOnClickListener(this);
		
		if(prefs.getBoolean(Settings.ENABLE_TRANSPARENCY, false)){
			vCandle.setBackgroundColor(Color.argb(prefs.getInt(Settings.CANDLE_ALPHA, 0),
					prefs.getInt(Settings.CANDLE_RED, 0), 
					prefs.getInt(Settings.CANDLE_GREEN, 0), 
					prefs.getInt(Settings.CANDLE_BLUE, 0)));
		}else{
			vCandle.setBackgroundColor(Color.rgb(prefs.getInt(Settings.CANDLE_RED, 0), 
					prefs.getInt(Settings.CANDLE_GREEN, 0), 
					prefs.getInt(Settings.CANDLE_BLUE, 0)));
		}
		
		//Read Prefs
		enabled = prefs.getBoolean(Settings.ENABLE_CUSTOMIZATIONS, false);
		updateViews(prefs.getBoolean(Settings.ENABLE_CUSTOMIZATIONS, false));
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.settings_view_flame_color)
			chooseFlameColor();
		if (v.getId() == R.id.settings_view_candle_color)
			chooseCandleColor();
	}

	private void chooseFlameColor() {
		Intent intent = new Intent(getApplicationContext(),ColorPickerDialog.class);
		intent.setAction(Settings.CHOOSE_FLAME_COLOR);
		startActivity(intent);
		finish();
	}

	private void chooseCandleColor() {
		Intent intent = new Intent(getApplicationContext(),ColorPickerDialog.class);
		intent.setAction(Settings.CHOOSE_CANDLE_COLOR);
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(),MainActivity.class));
		finish();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(buttonView==cbEnableCustomization){
			updateViews(isChecked);
			editor.putBoolean(Settings.ENABLE_CUSTOMIZATIONS, isChecked);
			editor.commit();
		}
		if(buttonView==cbEnableTransparency){
			editor.putBoolean(Settings.ENABLE_TRANSPARENCY, isChecked);
			editor.commit();
		}
		
		Log.i("isChecked", Boolean.toString(isChecked));
	}

	private void updateViews(boolean enabled) {
		// TODO Auto-generated method stub
		vFlame.setEnabled(enabled);
		vCandle.setEnabled(enabled);
		tvFlame.setEnabled(enabled);
		tvCandle.setEnabled(enabled);
		tvTransparency.setEnabled(enabled);
		cbEnableTransparency.setEnabled(enabled);
	}
	
}
