package com.hogg.catapps.ui;

import com.hogg.catapps.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/*public class PixelImageView extends ImageView {
	Paint p = new Paint();
	BitmapDrawable image;
	Bitmap toDraw;
	boolean scaled = false;
	int width;
    int height;
    int src;

	public PixelImageView(Context context) {
		super(context);
		Log.i("slerp", "derp?");
		width = getWidth();
		height = getHeight();
	}
	
	public PixelImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		src = Integer.parseInt(attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "src").substring(1));
		image = BitmapFactory.decodeResource(getResources(), src);
		width = getWidth();
		height = getHeight();
	}
	
	public PixelImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		src = Integer.parseInt(attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "src").substring(1));
		toDraw = BitmapFactory.decodeResource(getResources(), src);
		width = getWidth();
		height = getHeight();
	}
	
	@Override
	public void setImageDrawable(Drawable drawable) {
		image = ((BitmapDrawable)drawable);
		width = image.getBitmap().getWidth();
		height = image.getBitmap().getHeight();
		toDraw.
        toDraw = Bitmap.createScaledBitmap(image.getBitmap(), width*2, height*2, false).;
		//scaled = false;
		super.setImageDrawable(image);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	    if(!scaled) {
	    	width = getWidth();
			height = getHeight();
	        image = Bitmap.createScaledBitmap(image, width*10, height*10, false);
	        //invalidate();
	        //setImageBitmap(image);
	        scaled = true;
	    }
        canvas.drawBitmap(toDraw, 0f, 0f, p);
	}
}
*/