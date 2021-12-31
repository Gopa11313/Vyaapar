package net.com.gopal.vyapar.custumclasses;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import net.com.gopal.vyapar.utils.CustomerApplication;


public class FontTextViewextrabold extends androidx.appcompat.widget.AppCompatTextView {
    static Typeface face=Typeface.createFromAsset(CustomerApplication.getContext().getAssets(), "Poppins-Bold.ttf");


    public FontTextViewextrabold(Context context) {
        super(context);
        this.setTypeface(face);
    }

    public FontTextViewextrabold(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
        this.setTypeface(face);
    }

    public FontTextViewextrabold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}