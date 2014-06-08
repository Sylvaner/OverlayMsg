package com.example.sampleproject;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	private int convertSpToPixel(float sp)
	{
		DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
	    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics);
	}
	
	public void Button1(View view)
	{
		OverlayMsg ovm = new OverlayMsg(this);
		// Change text size
		ovm.textSize = convertSpToPixel(40);
		ovm.showTextWithCircle(R.id.globalLayout, R.id.textView1, "First text view", OverlayMsg.POSITION_CENTER, null);
	}

	public void Button2(View view)
	{
		OverlayMsg ovm = new OverlayMsg(this);
		// Add background to text
		// Hide press to continue
		ovm.showTextBackground = true;
		ovm.textBackgroundColor = 0xFF000000;
		ovm.showPressToContinue = false;
		ovm.showTextWithRect(R.id.globalLayout, R.id.radiogroup, "A radio group", OverlayMsg.POSITION_TOP, null);
	}

	public void Button3(View view)
	{
		OverlayMsg ovm = new OverlayMsg(this);
		// Change border size and color
		// Change press to continue text size
		ovm.borderColor = 0xFF00FF00;
		ovm.borderSize = 50;
		ovm.pressToContinueTextSize = convertSpToPixel(10);
		ovm.showTextWithCircle(R.id.globalLayout, R.id.radio1, "This things", OverlayMsg.POSITION_BOTTOM, null);
	}
	public void Button4(View view)
	{
		OverlayMsg ovm = new OverlayMsg(this);
		// Around 2 items with special font
		// Font found here : http://www.dafont.com/fr/alex-toth.font?l[]=10
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/alex-toth.ttf");
		ovm.textFont = typeface;
		ovm.textSize = convertSpToPixel(50);
		ovm.showTextWithBigRect(R.id.globalLayout, R.id.checkbox1, R.id.checkbox2, "A big box", OverlayMsg.POSITION_TOP, null);
	}

	public void Button5(View view)
	{
		OverlayMsg ovm = new OverlayMsg(this);
		// 3 items with event
		int[] viewIdArray = new int[3];
		viewIdArray[0] = R.id.textView1;
		viewIdArray[1] = R.id.button2;
		viewIdArray[2] = R.id.button5;
		int[] shapeArray = new int[3];
		shapeArray[0] = OverlayMsg.SHAPE_CIRCLE;
		shapeArray[1] = OverlayMsg.SHAPE_RECTANGLE;
		shapeArray[2] = OverlayMsg.SHAPE_CIRCLE;
		
		ovm.showTextWithMultiple(
				R.id.globalLayout, 
				viewIdArray, 
				shapeArray, 
				"A lot of things", 
				OverlayMsg.POSITION_CENTER, 
				new OverlayMsg.Event() {
					public void event() {
						Toast.makeText(getApplicationContext(), "Final sample", Toast.LENGTH_LONG).show();
					}
				});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		OverlayMsg ovm = new OverlayMsg(this);
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (ovm.isCurrentlyShowed())
			{
				ovm.hideCurrentMessage();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
