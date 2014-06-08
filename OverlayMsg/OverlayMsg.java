/**
 * @file OverlayMsg.java
 * @brief Highlight an item and show a message over the current view.
 * @author Sylvain DANGIN
 * @version 1.0
 * @date 5/29/2014
 * @copyright Copyright (c) 2014, Sylvain DANGIN\n
 * @par
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * @par
1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the followingdisclaimer in the documentation and/or
other materials provided with the distribution.
 * @par
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE 
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @class OverlayMsg
 * @brief Highlight an item and show a message over the current view.
 */
public class OverlayMsg
{
	/**
	 * @interface Event
	 * @brief Interface used by OverlayMsg for events.
	 */
	public interface Event
	{
		/** @brief Called when the layout is closed. */
		public void event();
	}

	/** @brief Top position constant used for placement of text. */
	public final static int POSITION_TOP = 0;
	/** @brief Center position constant used for placement of text. */
	public final static int POSITION_CENTER = 1;
	/** @brief Bottom position constant used for placement of text. */
	public final static int POSITION_BOTTOM = 2;
	/** @brief Shape circle for multiple items. */
	public final static int SHAPE_CIRCLE = 0;
	/** @brief Shape rectangle for multiple items. */
	public final static int SHAPE_RECTANGLE = 1;
	/** @brief Application activity. */
	private Activity activity;
	/** 
	 * @brief Border size in pixel.
	 * @details The default value is defined by @b R.dimen.overlay_msg_default_border_size. 
	 */
	public int borderSize;
	/** 
	 * @brief Text size in pixel.
	 * @details The default value is defined by @b R.dimen.overlay_msg_default_text_size. 
	 */
	public int textSize;
	/** 
	 * @brief Text margin in pixel.
	 * @details The default value is defined by @b R.dimen.overlay_msg_default_text_margin. 
	 */
	public int textMargin;
	/**
	 * @brief Text color
	 * @details The default value is defined by @b R.color.overlay_msg_default_text_color.
	 */
	public int textColor;
	/**
	 * @brief Text background color
	 * @details The default value is defined by @b R.color.overlay_msg_default_text_background_color.
	 */
	public int textBackgroundColor;
	/**
	 * @brief Background color
	 * @details The default value is defined by @b R.color.overlay_msg_default_background_color.
	 */
	public int backgroundColor;
	/**
	 * @brief Border color
	 * @details The default value is defined by @b R.color.overlay_msg_default_border_color.
	 */
	public int borderColor;
	/**
	 * @brief Use anti alias.
	 * @details The default value is defined by @b R.bool.overlay_msg_default_use_antialias.
	 */
	public boolean antiAlias;
	/**
	 * @brief Show TextView background
	 * @details The default value is defined by @b R.bool.overlay_msg_default_show_text_background.
	 */
	public boolean showTextBackground;
	/**
	 * @brief TextView font
	 */
	public Typeface textFont;
	/**
	 * @brief Show a message under text.
	 */
	public boolean showPressToContinue;
	/**
	 * @brief Press to continue text size.
	 */
	public int pressToContinueTextSize;
	/**
	 * @brief State at true if message is currently showed.
	 */
	private boolean currentlyShowed;
	/**
	 * @brief Current message layout.
	 */
	private RelativeLayout currentLayout;
	
	/**
	 * @brief Class constructor.
	 * @param applicationActivity Application activity
	 * @details Initializes all default values.
	 */
	public OverlayMsg(Activity applicationActivity)
	{
		activity = applicationActivity;
		borderSize = activity.getResources().getDimensionPixelSize(R.dimen.overlay_msg_default_border_size);
		textSize = activity.getResources().getDimensionPixelSize(R.dimen.overlay_msg_default_text_size);
		textMargin = activity.getResources().getDimensionPixelSize(R.dimen.overlay_msg_default_text_margin);
		textColor = activity.getResources().getColor(R.color.overlay_msg_default_text_color);
		textBackgroundColor = activity.getResources().getColor(R.color.overlay_msg_default_text_background_color);
		backgroundColor = activity.getResources().getColor(R.color.overlay_msg_default_background_color);
		borderColor = activity.getResources().getColor(R.color.overlay_msg_default_border_color);
		antiAlias = activity.getResources().getBoolean(R.bool.overlay_msg_default_use_antialias);
		showTextBackground = activity.getResources().getBoolean(R.bool.overlay_msg_default_show_text_background);
		showPressToContinue = activity.getResources().getBoolean(R.bool.overlay_msg_default_press_to_continue);
		pressToContinueTextSize = activity.getResources().getDimensionPixelSize(R.dimen.overlay_msg_default_press_text_size);
		textFont = null;
	}
	
	/**
	 * @brief Displays a message with an item surrounded by a circle.
	 * @param globalLayoutId Id of the main layout.
	 * @param viewToSurround View that will be surrounded.
	 * @param msg Message to show.
	 * @param position Position of the text on the screen.
	 * @param overlayEvent Event called at the end of the display.
	 * @details 
	 * The @b globalLayoutId parameter must refer to a FrameLayout.\n
	 * The @b position parameter is defined by : 
	 * - OverlayMsg::POSITION_TOP,
	 * - OverlayMsg::POSITION_CENTER,
	 * - OverlayMsg::POSITION_BOTTOM.
	 */
	public void showTextWithCircle(int globalLayoutId, int viewToSurround, String msg, int position, final OverlayMsg.Event overlayEvent)
	{
		int tempViewId[] = new int[1];
		int tempShape[] = new int[1];
		tempViewId[0] = viewToSurround;
		tempShape[0] = SHAPE_CIRCLE;
		showTextWithMultiple(globalLayoutId, tempViewId, tempShape, msg, position, overlayEvent);
	}
	
	/**
	 * @brief Displays a message with an item surrounded by a rectangle.
	 * @param globalLayoutId Id of the main layout.
	 * @param viewToSurround View that will be surrounded.
	 * @param msg Message to show.
	 * @param position Position of the text on the screen.
	 * @param overlayEvent Event called at the end of the display.
	 * @details 
	 * The @b globalLayoutId parameter must refer to a FrameLayout.\n
	 * The @b position parameter is defined by : 
	 * - OverlayMsg::POSITION_TOP,
	 * - OverlayMsg::POSITION_CENTER,
	 * - OverlayMsg::POSITION_BOTTOM.
	 */
	public void showTextWithRect(int globalLayoutId, int viewToSurround, String msg, int position, final OverlayMsg.Event overlayEvent)
	{
		int tempViewId[] = new int[1];
		int tempShape[] = new int[1];
		tempViewId[0] = viewToSurround;
		tempShape[0] = SHAPE_RECTANGLE;
		showTextWithMultiple(globalLayoutId, tempViewId, tempShape, msg, position, overlayEvent);

	}
	
	/**
	 * @brief Displays a message with 2 items surrounded by a rectangle.
	 * @param globalLayoutId Id of the main layout.
	 * @param viewToSurround1 First view that will be surrounded.
	 * @param viewToSurround2 Second view that will be surrounded.
	 * @param msg Message to show.
	 * @param position Position of the text on the screen.
	 * @param overlayEvent Event called at the end of the display.
	 * @details 
	 * The @b globalLayoutId parameter must refer to a FrameLayout.\n
	 * The @b position parameter is defined by : 
	 * - OverlayMsg::POSITION_TOP,
	 * - OverlayMsg::POSITION_CENTER,
	 * - OverlayMsg::POSITION_BOTTOM.
	 */
	public void showTextWithBigRect(int globalLayoutId, int viewToSurround1, int viewToSurround2, String msg, int position, final OverlayMsg.Event overlayEvent)
	{
		final FrameLayout globalLayout = (FrameLayout)activity.findViewById(globalLayoutId);
		createOverlayLayout(globalLayout, overlayEvent);

		Bitmap bitmap = createBitmap(globalLayout);
		Canvas canvas = new Canvas(bitmap);
		
		int location1[] = new int[2];
		int location2[] = new int[2];
		activity.findViewById(viewToSurround1).getLocationInWindow(location1);
		View v2 = activity.findViewById(viewToSurround2);
		v2.getLocationInWindow(location2);
		int offset = getStartOffset(globalLayout);
		
		addBackground(canvas, globalLayout.getWidth(), globalLayout.getHeight());
		addRect(canvas, 
				location1[0], 
				location1[1] - offset, 
				location2[0] - location1[0] + v2.getWidth(), 
				location2[1] - location1[1] + v2.getHeight());
		currentLayout.addView(getMessageLayout(msg, position));

		setBackgroundToLayout(currentLayout, bitmap);
		currentlyShowed = true;
		globalLayout.addView(currentLayout);
	}

	
	/**
	 * @brief Displays multiple items surrounded by a choosen shape .
	 * @param globalLayoutId Id of the main layout.
	 * @param viewToSurroundArray Array of items id.
	 * @param shapeArray Array of corresponding shape.
	 * @param msg Message to show.
	 * @param position Position of the text on the screen.
	 * @param overlayEvent Event called at the end of the display.
	 * The @b globalLayoutId parameter must refer to a FrameLayout.\n
	 * The @b shapeArray parameter is defined by : 
	 *  - OverlayMsg::SHAPE_CIRCLE,
	 *  - OverlayMsg::SHAPE_RECTANGLE.
	 * The @b position parameter is defined by : 
	 * - OverlayMsg::POSITION_TOP,
	 * - OverlayMsg::POSITION_CENTER,
	 * - OverlayMsg::POSITION_BOTTOM.
	 */
	public void showTextWithMultiple(int globalLayoutId, int[] viewToSurroundArray, int[] shapeArray, String msg, int position, final OverlayMsg.Event overlayEvent)
	{
		int length = viewToSurroundArray.length;
		if (shapeArray.length == length)
		{
			final FrameLayout globalLayout = (FrameLayout)activity.findViewById(globalLayoutId);
			createOverlayLayout(globalLayout, overlayEvent);

			Bitmap bitmap = createBitmap(globalLayout);
			Canvas canvas = new Canvas(bitmap);
			
			addBackground(canvas, globalLayout.getWidth(), globalLayout.getHeight());

			int location[] = new int[2];
			// Display all shapes
			for (int i = 0; i < length; ++i)
			{
				View v = activity.findViewById(viewToSurroundArray[i]);
				v.getLocationInWindow(location);
				// Add a circle
				if (shapeArray[i] == SHAPE_CIRCLE)
				{
					int posX = v.getWidth() / 2 + location[0];
					int posY = v.getHeight() / 2 + location[1] - getStartOffset(globalLayout);
					int radius;
					if (v.getWidth() < v.getHeight())
						radius = v.getHeight() / 2;
					else
						radius = v.getWidth() / 2;
					addCircle(canvas, posX, posY, radius);
				}
				else
				{
					// Add a rectangle
					addRect(canvas, location[0], location[1] - getStartOffset(globalLayout), v.getWidth(), v.getHeight());
				}
			}
			currentLayout.addView(getMessageLayout(msg, position));
			setBackgroundToLayout(currentLayout, bitmap);
			currentlyShowed = true;
			globalLayout.addView(currentLayout);
		}
	}

	/**
	 * @brief Add a fade in animation to the overlay layout
	 */
	private void setFadeIn()
	{
		Animation fadeIn = new AlphaAnimation(0, 1);
	    fadeIn.setDuration(150);
	    currentLayout.setAnimation(fadeIn);
	}
	
	/**
	 * @brief Add a fade out animation to the overlay layout.
	 * @param overlayEvent Event called at the end of the display.
	 * @details The event is attached in this method and called at the end of the animation.
	 */
	private void setFadeOut(final OverlayMsg.Event overlayEvent)
	{
		currentLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Animation fadeOut = new AlphaAnimation(1, 0);
			    fadeOut.setDuration(150);
			    fadeOut.setAnimationListener(new AnimationListener() {
					public void onAnimationStart(Animation animation) {}
					public void onAnimationRepeat(Animation animation) {}
					public void onAnimationEnd(Animation animation) {
						// Remove the layout from the parent
						currentlyShowed = false;
						((ViewGroup)currentLayout.getParent()).removeView(currentLayout);
						if (overlayEvent != null)
							overlayEvent.event();
						}});
			    currentLayout.startAnimation(fadeOut);
			}});
	}
	
	/**
	 * @brief Return the offset of the action bar. 
	 * @param globalLayout Main layout.
	 */
	private int getStartOffset(FrameLayout globalLayout)
	{
		int[] location = new int[2];
		globalLayout.getLocationInWindow(location);
		return location[1];
	}
	
	/**
	 * @brief Create the base bitmap.
	 * @param globalLayout Main layout.
	 * @return Created bitmap
	 */
	private Bitmap createBitmap(FrameLayout globalLayout)
	{
		return Bitmap.createBitmap(globalLayout.getWidth(), globalLayout.getHeight(), Bitmap.Config.ARGB_8888);
	}
	
	/**
	 * @brief Create the overlay layout.
	 * @param globalLayout Main layout.
	 * @param overlayEvent Event called at the end of the display.
	 */
	private void createOverlayLayout(FrameLayout globalLayout, OverlayMsg.Event overlayEvent)
	{
		currentLayout = new RelativeLayout(activity);
		currentLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setFadeIn();
		setFadeOut(overlayEvent);
	}
	
	/**
	 * @brief Add a background to the canvas.
	 * @param canvas Destination canvas.
	 * @param width Width of the background.
	 * @param height Height of the background.
	 * @see drawRect
	 */
	private void addBackground(Canvas canvas, int width, int height)
	{
		drawRect(canvas, 0, 0, width, height, backgroundColor);
	}
	
	/**
	 * @brief Add a transparent circle with border to the canvas.
	 * @param canvas Destination canvas.
	 * @param x Circle horizontal position.
	 * @param y Circle vertical position.
	 * @param radius Circle radius.
	 * @see drawCircle
	 */
	private void addCircle(Canvas canvas, int x, int y, int radius)
	{
		drawCircle(canvas, x, y, radius + borderSize, borderColor);
		drawCircle(canvas, x, y, radius, 0x00FFFFFF);		
	}
	
	/**
	 * @brief Draw a circle in the canvas.
	 * @param canvas Destination canvas.
	 * @param x Circle horizontal position.
	 * @param y Circle vertical position.
	 * @param radius Circle radius.
	 * @param color Circle color.
	 * @details Use a color like 0x00xxxxxx to make a hole.
	 */
	private void drawCircle(Canvas canvas, int x, int y, int radius, int color)
	{
		Paint paint = new Paint();
		// Test for transparent color
		if ((color & 0x00FFFFFF) == color)
		{
			// Make a hole
			paint.setColor(activity.getResources().getColor(android.R.color.transparent));
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		}
		else
			paint.setColor(color);
		
		paint.setAntiAlias(antiAlias);
		canvas.drawCircle(x, y, radius, paint);
	}
	
	/**
	 * @brief Add a transparent rectangle with border to the canvas.
	 * @param canvas Destination canvas.
	 * @param x Rectangle horizontal position.
	 * @param y Rectangle vertical position.
	 * @param width Rectangle width.
	 * @param height Rectangle height.
	 * @see drawRect
	 */
	private void addRect(Canvas canvas, int x, int y, int width, int height)
	{
		
		drawRect(canvas, x - borderSize, y - borderSize, x + width + borderSize, y + height + borderSize, borderColor);
		drawRect(canvas, x, y, x + width, y + height, 0x00FFFFFF);		
	}
	
	/**
	 * @brief Draw a rectangle in the canvas.
	 * @param canvas Destination canvas.
	 * @param x Rectangle horizontal position.
	 * @param y Rectangle vertical position.
	 * @param width Rectangle width.
	 * @param height Rectangle height.
	 * @param color Rectangle color.
	 * @details Use a color like 0x00xxxxxx to make a hole.
	 */
	private void drawRect(Canvas canvas, int x, int y, int width, int height, int color)
	{
		Paint paint = new Paint();
		// Test for transparent color
		if ((color & 0x00FFFFFF) == color)
		{
			// Make a hole
			paint.setColor(activity.getResources().getColor(android.R.color.transparent));
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		}
		else
			paint.setColor(color);
		
		paint.setAntiAlias(antiAlias);
		canvas.drawRect(x, y, width, height, paint);
	}
	
	/**
	 * @brief Get the message TextView.
	 * @param msg Message of the TextView.
	 * @return Created TextView.
	 */
	private TextView getTextView(String msg)
	{
		TextView textView = new TextView(activity);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setTextColor(textColor);
		if (showTextBackground)
			textView.setBackgroundColor(textBackgroundColor);
		if (textFont != null)
			textView.setTypeface(textFont);
		textView.setTextSize(textSize);
		textView.setText(msg);
		return textView;
	}
	
	/**
	 * @brief Get the message "Press to continue" TextView.
	 * @return Created TextView.
	 */
	private TextView getPressToContinueTextView()
	{
		TextView textView = new TextView(activity);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setTextColor(textColor);
		textView.setTextSize(pressToContinueTextSize);
		textView.setText(activity.getResources().getString(R.string.overlay_msg_press_to_continue));
		return textView;
	}
	
	/**
	 * @brief Get the LayoutParams neccessary for the TextView position.
	 * @param position Position of the text on the screen.
	 * @return LayoutParams of the TextView.
	 * @details 
	 * The @b globalLayoutId parameter must refer to a FrameLayout.\n
	 * The @b position parameter is defined by : 
	 * - OverlayMsg::POSITION_TOP,
	 * - OverlayMsg::POSITION_CENTER,
	 * - OverlayMsg::POSITION_BOTTOM.
	 */
	private RelativeLayout.LayoutParams getLayoutParams(int position)
	{
		RelativeLayout.LayoutParams layoutParams;
		
		layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		switch (position)
		{
		case POSITION_TOP:
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			layoutParams.setMargins(0, textMargin, 0, 0);
			break;
		case POSITION_CENTER:
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			break;
		case POSITION_BOTTOM:
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			layoutParams.setMargins(0, 0, 0, textMargin);
			break;
		}
		return layoutParams;
	}
	
	/**
	 * @brief Create the message layout.
	 * @param msg Message to show.
	 * @param position Position in the screen.
	 */
	private LinearLayout getMessageLayout(String msg, int position)
	{
		LinearLayout layout = new LinearLayout(activity);
		layout.setLayoutParams(getLayoutParams(position));
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		if (showTextBackground)
			layout.setBackgroundColor(textBackgroundColor);
		layout.addView(getTextView(msg));
		if (showPressToContinue)
			layout.addView(getPressToContinueTextView());
		return layout;
	}
	
	/**
	 * @brief Apply background to overlay layout.
	 * @param layout Overlay layout.
	 * @param bitmap Background.
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void setBackgroundToLayout(RelativeLayout layout, Bitmap bitmap)
	{
		Drawable drawable = new BitmapDrawable(activity.getResources(), bitmap);
		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if (sdkVersion < android.os.Build.VERSION_CODES.JELLY_BEAN)
		    layout.setBackgroundDrawable(drawable);
		else
		    layout.setBackground(drawable);
	}
	
	/**
	 * @brief Method to know if a message is currently showed.
	 * @return True if message is currently showed.
	 */
	public boolean isCurrentlyShowed()
	{
		return currentlyShowed;
	}
	
	/**
	 * @brief Hide the current message.
	 */
	public void hideCurrentMessage()
	{
		if (currentLayout != null && currentlyShowed)
		{
			currentLayout.performClick();
		}
	}
}

