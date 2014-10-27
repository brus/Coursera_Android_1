package com.brus.modernartui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String[] PURPLE = { "#f3e5f5", "#e1bee7", "#ce93d8", "#ba68c8", "#ab47bc", "#9c27b0", "#8e24aa", "#7b1fa2", "#6a1b9a", "#4a148c" };
	private static final String[] TEAL = { "#e0f2f1", "#b2dfdb", "#80cbc4", "#4db6ac", "#26a69a", "#009688", "#00897b", "#00796b", "#00695c", "#004d40" };
	private static final String[] LIME = { "#f9fbe7", "#f0f4c3", "#e6ee9c", "#dce775", "#d4e157", "#cddc39", "#c0ca33", "#afb42b", "#9e9d24", "#827717" };
	private static final String[] INDIGO = { "#e8eaf6", "#c5cae9", "#9fa8da", "#7986cb", "#5c6bc0", "#3f51b5", "#3949ab", "#303f9f", "#283593", "#1a237e" };

	private List<ColorComponent> colorComponents = new ArrayList<ColorComponent>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		colorComponents.add(new ColorComponent(TEAL, true));
		colorComponents.add(new ColorComponent(PURPLE, false));
		colorComponents.add(new ColorComponent(LIME, false));
		colorComponents.add(new ColorComponent(INDIGO, false));

		updateColors(0);

		SeekBar seekBar = (SeekBar) findViewById(R.id.changeColor);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
				updateColors(progresValue);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
	}

	private void updateColors(int sliderValue) {
		findViewById(R.id.button1Left).setBackgroundColor(colorComponents.get(0).getColor(sliderValue / 10));
		findViewById(R.id.button2Left).setBackgroundColor(colorComponents.get(1).getColor(sliderValue / 10));
		findViewById(R.id.button1Right).setBackgroundColor(colorComponents.get(2).getColor(sliderValue / 10));
		findViewById(R.id.button3Right).setBackgroundColor(colorComponents.get(3).getColor(sliderValue / 10));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		if (id == R.id.action_more_info) {
			showDialog();
		}
		return super.onOptionsItemSelected(item);
	}

	private void showDialog() {
		View dialogView = this.getLayoutInflater().inflate(R.layout.dialog, null);
		final AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

		TextView visitMoma = (TextView) dialogView.findViewById(R.id.visit_moma);
		visitMoma.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://www.moma.org/m#home"));
				startActivity(i);
			}
		});

		TextView notNow = (TextView) dialogView.findViewById(R.id.not_now);
		notNow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
}
