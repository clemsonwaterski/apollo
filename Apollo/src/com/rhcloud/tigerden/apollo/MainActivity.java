package com.rhcloud.tigerden.apollo;

import com.rhcloud.tigerden.apollo.drawing.GameBoard;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	private Handler frame = new Handler();
	
	private static final int FRAME_RATE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Handler h = new Handler();
		((Button)findViewById(R.id.main_button)).setOnClickListener(this);
		
		h.postDelayed(new Runnable(){

			@Override
			public void run() {
				initGfx();
			}}, 1000);
		
	}

	synchronized public void initGfx() {
		((GameBoard)findViewById(R.id.canvas)).resetStarField();
		((Button)findViewById(R.id.main_button)).setEnabled(true);
		frame.removeCallbacks(frameUpdate);
		frame.postDelayed(frameUpdate, FRAME_RATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		initGfx();
	}
	
	private Runnable frameUpdate = new Runnable() {

		@Override
		public void run() {
			frame.removeCallbacks(frameUpdate);
			((GameBoard)findViewById(R.id.canvas)).invalidate();
			frame.postDelayed(frameUpdate, FRAME_RATE);
		}
		
	};

}
