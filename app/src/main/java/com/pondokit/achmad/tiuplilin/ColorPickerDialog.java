package com.pondokit.achmad.tiuplilin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ColorPickerDialog extends Activity implements
		OnSeekBarChangeListener {
	SeekBar sbRed, sbBlue, sbGreen, sbAlpha;
	TextView tvRed, tvBlue, tvGreen, tvAlpha;
	ImageView vColorDish;
	int green, blue, red, alpha;
	int preference=0;
	
	SharedPreferences prefs;
	Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_picker);
		init();
		
		Intent prefType = getIntent();
		if(prefType.getAction().equals(Settings.CHOOSE_CANDLE_COLOR)){
			preference=1;
			
		}
		if(prefType.getAction().equals(Settings.CHOOSE_FLAME_COLOR)){
			preference=2;
			
		}
		updateUI(preference);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		savePrefs();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		readPrefs();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		savePrefs();
		startActivity(new Intent(getApplicationContext(),Settings.class));
		finish();
	}
	private void init() {
		// TODO Auto-generated method stub
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		editor = prefs.edit();
		
		tvAlpha= (TextView) findViewById(R.id.color_picker_textView_alpha);
		tvRed= (TextView) findViewById(R.id.color_picker_textView_red);
		tvGreen= (TextView) findViewById(R.id.color_picker_textView_green);
		tvBlue = (TextView) findViewById(R.id.color_picker_textView_blue);
		
		sbRed = (SeekBar) findViewById(R.id.color_picker_seekBar_red);
		sbRed.setOnSeekBarChangeListener(this);

		sbBlue = (SeekBar) findViewById(R.id.color_picker_seekBar_blue);
		sbBlue.setOnSeekBarChangeListener(this);

		sbGreen = (SeekBar) findViewById(R.id.color_picker_seekBar_green);
		sbGreen.setOnSeekBarChangeListener(this);

		sbAlpha = (SeekBar) findViewById(R.id.color_picker_seekBar_alpha);
		sbAlpha.setOnSeekBarChangeListener(this);

		if(prefs.getBoolean(Settings.ENABLE_TRANSPARENCY, false)){
			sbAlpha.setVisibility(SeekBar.VISIBLE);
			tvAlpha.setVisibility(TextView.VISIBLE);
		}else{
			sbAlpha.setVisibility(SeekBar.GONE);
			tvAlpha.setVisibility(TextView.GONE);
		}
		
		vColorDish = (ImageView) findViewById(R.id.color_picker_imageView_color);
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
		if (seekBar == sbAlpha) {
			alpha = progress;
			Log.i("Alpha", "Level " +progress);
			
			switch(preference){
			case 1:
				editor.putInt(Settings.CANDLE_ALPHA, alpha);
				break;
			case 2:
				editor.putInt(Settings.FLAME_ALPHA, alpha);
				break;
			}
			
		}
		if (seekBar == sbRed) {
			red = progress;
			Log.i("Red", "Level " +progress);
			
			switch(preference){
			case 1:
				editor.putInt(Settings.CANDLE_RED, red);
				break;
			case 2:
				editor.putInt(Settings.FLAME_RED, red);
				break;
			}
		}
		if (seekBar == sbGreen) {
			green = progress;
			Log.i("Green", "Level " +progress);
			
			switch(preference){
			case 1:
				editor.putInt(Settings.CANDLE_GREEN, green);
				break;
			case 2:
				editor.putInt(Settings.FLAME_GREEN, green);
				break;
			}
		}
		if (seekBar == sbBlue) {
			blue = progress;
			Log.i("BLue", "Level " +progress);
			
			switch(preference){
			case 1:
				editor.putInt(Settings.CANDLE_BLUE, blue);
				break;
			case 2:
				editor.putInt(Settings.FLAME_BLUE, blue);
				break;
			}
		}
		
		editor.commit();
		
		switch(preference){
		case 1:
			if(prefs.getBoolean(Settings.ENABLE_TRANSPARENCY, false)){
				
				vColorDish.setBackgroundColor(Color.argb(prefs.getInt(Settings.CANDLE_ALPHA, 0),
						prefs.getInt(Settings.CANDLE_RED, 0), 
						prefs.getInt(Settings.CANDLE_GREEN, 0), 
						prefs.getInt(Settings.CANDLE_BLUE, 0)));
			}else{
				vColorDish.setBackgroundColor(Color.rgb(prefs.getInt(Settings.CANDLE_RED, 0), 
						prefs.getInt(Settings.CANDLE_GREEN, 0), 
						prefs.getInt(Settings.CANDLE_BLUE, 0)));
			}
			break;
		case 2:
			if(prefs.getBoolean(Settings.ENABLE_TRANSPARENCY, false)){
				vColorDish.setBackgroundColor(Color.argb(prefs.getInt(Settings.FLAME_ALPHA, 0),
						prefs.getInt(Settings.FLAME_RED, 0), 
						prefs.getInt(Settings.FLAME_GREEN, 0), 
						prefs.getInt(Settings.FLAME_BLUE, 0)));
			}else{
				vColorDish.setBackgroundColor(Color.rgb(prefs.getInt(Settings.FLAME_RED, 0), 
						prefs.getInt(Settings.FLAME_GREEN, 0), 
						prefs.getInt(Settings.FLAME_BLUE, 0)));
			}

			break;
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
	
	private void savePrefs(){}
	
	private void readPrefs(){
		switch(preference){
		case 1:
			if(prefs.getBoolean(Settings.ENABLE_TRANSPARENCY, false)){
				vColorDish.setBackgroundColor(Color.argb(prefs.getInt(Settings.CANDLE_ALPHA, 0),
						prefs.getInt(Settings.CANDLE_RED, 0), 
						prefs.getInt(Settings.CANDLE_GREEN, 0), 
						prefs.getInt(Settings.CANDLE_BLUE, 0)));
			}else{
				vColorDish.setBackgroundColor(Color.rgb(prefs.getInt(Settings.CANDLE_RED, 0), 
						prefs.getInt(Settings.CANDLE_GREEN, 0), 
						prefs.getInt(Settings.CANDLE_BLUE, 0)));
			}
			break;
		case 2:
			if(prefs.getBoolean(Settings.ENABLE_TRANSPARENCY, false)){
				vColorDish.setBackgroundColor(Color.argb(prefs.getInt(Settings.FLAME_ALPHA, 0),
						prefs.getInt(Settings.FLAME_RED, 0), 
						prefs.getInt(Settings.FLAME_GREEN, 0), 
						prefs.getInt(Settings.FLAME_BLUE, 0)));
			}else{
				vColorDish.setBackgroundColor(Color.rgb(prefs.getInt(Settings.FLAME_RED, 0), 
						prefs.getInt(Settings.FLAME_GREEN, 0), 
						prefs.getInt(Settings.FLAME_BLUE, 0)));
			}

			break;
		}
	}

	private void updateUI(int preference) {
		switch (preference) {
		case 1:
			sbAlpha.setProgress(prefs.getInt(Settings.CANDLE_ALPHA, 0));
			sbRed.setProgress(prefs.getInt(Settings.CANDLE_RED, 0));
			sbGreen.setProgress(prefs.getInt(Settings.CANDLE_GREEN, 0));
			sbBlue.setProgress(prefs.getInt(Settings.CANDLE_BLUE, 0));
			break;
		case 2:
			sbAlpha.setProgress(prefs.getInt(Settings.FLAME_ALPHA, 0));
			sbRed.setProgress(prefs.getInt(Settings.FLAME_RED, 0));
			sbGreen.setProgress(prefs.getInt(Settings.FLAME_GREEN, 0));
			sbBlue.setProgress(prefs.getInt(Settings.FLAME_BLUE, 0));
			break;
		}
	}
}
