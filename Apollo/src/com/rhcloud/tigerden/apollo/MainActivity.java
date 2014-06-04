package com.rhcloud.tigerden.apollo;

import java.util.Random;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.rhcloud.tigerden.apollo.drawing.GameBoard;

public class MainActivity extends Activity implements OnClickListener {
	
	private Handler frame = new Handler();
	
	private Point sprite1Velocity;
	private Point sprite2Velocity;
	private int sprite1MaxX;
	private int sprite1MaxY;
	private int sprite2MaxX;
	private int sprite2MaxY;
	
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
		
		Point p1,p2;
		
		do{
			p1 = getRandomPoint();
			p2 = getRandomPoint();
		} while (Math.abs(p1.x - p2.x) < ((GameBoard)findViewById(R.id.canvas)).getSprite1Width());
		
		
		((GameBoard)findViewById(R.id.canvas)).setSprite1(p1.x, p1.y);
		((GameBoard)findViewById(R.id.canvas)).setSprite2(p2.x, p2.y);
		
		sprite1Velocity = getRandomVelocity();
		sprite2Velocity = new Point(1,1);
		
		sprite1MaxX = ((GameBoard)findViewById(R.id.canvas)).getWidth() - ((GameBoard)findViewById(R.id.canvas)).getSprite1Width();
		sprite1MaxY = ((GameBoard)findViewById(R.id.canvas)).getHeight() - ((GameBoard)findViewById(R.id.canvas)).getSprite1Height();
		sprite2MaxX = ((GameBoard)findViewById(R.id.canvas)).getWidth() - ((GameBoard)findViewById(R.id.canvas)).getSprite2Width();
		sprite2MaxY = ((GameBoard)findViewById(R.id.canvas)).getWidth() - ((GameBoard)findViewById(R.id.canvas)).getSprite2Height();
		
		((Button)findViewById(R.id.main_button)).setEnabled(true);
		frame.removeCallbacks(frameUpdate);
		frame.postDelayed(frameUpdate, FRAME_RATE);
	}

	private Point getRandomPoint() {
		Random r = new Random();
		int minX = 0;
		int maxX = findViewById(R.id.canvas).getWidth() - ((GameBoard)findViewById(R.id.canvas)).getSprite1Width();
		int x = 0;
		int minY = 0;
		int maxY = findViewById(R.id.canvas).getHeight() - ((GameBoard)findViewById(R.id.canvas)).getSprite1Height();
		int y = 0;
		
		x = r.nextInt(maxX - minX + 1) + minX;
		y = r.nextInt(maxY - minY + 1) + minY;

		return new Point(x,y);
	}
	
	private Point getRandomVelocity(){
		Random r = new Random();
		int min = 1;
		int max = 5;
		int x = r.nextInt(max-min+1) + min;
		int y = r.nextInt(max-min+1) + min;
		return new Point(x,y);
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
		synchronized public void run() {
			frame.removeCallbacks(frameUpdate);
			Point sprite1 = new Point(((GameBoard)findViewById(R.id.canvas)).getSprite1X(), ((GameBoard)findViewById(R.id.canvas)).getSprite1Y());
			Point sprite2 = new Point(((GameBoard)findViewById(R.id.canvas)).getSprite2X(), ((GameBoard)findViewById(R.id.canvas)).getSprite2Y());
			sprite1.x = sprite1.x + sprite1Velocity.x;
			if(sprite1.x > sprite1MaxX || sprite1.x < 5){
				sprite1Velocity.x *= -1;
			}
			sprite1.y = sprite1.y + sprite1Velocity.y;
			if(sprite1.y > sprite1MaxY || sprite1.y < 5){
				sprite1Velocity.y *= -1;
			}
			sprite2.x = sprite2.x + sprite2Velocity.x;
			if(sprite2.x > sprite2MaxX || sprite2.x < 5){
				sprite2Velocity.x *= -1;
			}
			sprite2.y = sprite2.y + sprite2Velocity.y;
			if(sprite2.y > sprite2MaxY || sprite2.y < 5){
				sprite2Velocity.y *= -1;
			}
			
			((GameBoard)findViewById(R.id.canvas)).setSprite1(sprite1.x, sprite1.y);
			((GameBoard)findViewById(R.id.canvas)).setSprite2(sprite2.x, sprite2.y);
			
			((GameBoard)findViewById(R.id.canvas)).invalidate();
			frame.postDelayed(frameUpdate, FRAME_RATE);
		}
		
	};

}
