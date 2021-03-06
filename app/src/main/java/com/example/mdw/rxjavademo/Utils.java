package com.example.mdw.rxjavademo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.FileOutputStream;
import java.io.IOException;

import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by mdw on 2016/3/3.
 */
public class Utils {


    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }



    public static void storeBitmap(final Context context, final Bitmap bitmap, final String fileName){
        Schedulers.io().createWorker().schedule(new Action0(){

            @Override
            public void call() {
                blockingStoreBitmap(context,bitmap,fileName);
            }
        });
    }

    public static void blockingStoreBitmap(Context context, Bitmap bitmap, String fileName) {
        FileOutputStream fOut = null;

        try {
            fOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fOut != null) {
                    fOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

