package com.example.widjet.main.imagecreator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class ImageCreater {
    public static Bitmap createUpdate(String textTime, int size, int color, Paint.Align align, Context context) {
        Paint paint = new Paint();
        paint.setTextSize(size);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf");

      //  paint.setTypeface(typeface);
        paint.setColor(color);
        paint.setTextAlign(align);
        paint.setSubpixelText(true);

        float baseLine =  - paint.ascent();

        int width = (int) (paint.measureText(textTime) + 0.5f);
        int height = (int) (baseLine + paint.descent() + 0.5f);
        System.out.println(baseLine);
        System.out.println(width + "  " + height);

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);

        Canvas canvas = new Canvas(image);
        canvas.drawText(textTime, 0, baseLine, paint);
        System.out.println(image.getHeight() + " " + image.getWidth());
        return image;
    }
}
