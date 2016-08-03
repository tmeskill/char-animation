package com.tmeskill.charanimationtest;

import android.content.Context;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by tommy on 04 29, 2016.
 * to support BeerFly-MVP application
 */
public class PulseTextView extends TextView {
    private SpannableStringBuilder mText;
    private int mIndex;
    private long mDelay = 500; //Default 500ms delay
    private Context context;


    public PulseTextView(Context context) {
        super(context);
        this.context = context;
    }

    public PulseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private Handler mHandler = new Handler();
    private Runnable characterPulser = new Runnable() {
        @Override
        public void run() {
            mText.clearSpans();

            Animation startFadeOut = AnimationUtils.loadAnimation(context, android.R.anim.bounce_interpolator);

            if(mIndex <= mText.length()-1) {
                mText.setSpan(new AbsoluteSizeSpan(28, true), mIndex, mIndex + 1, 0);
                //mText.setSpan(new StrikethroughSpan(), mIndex, mIndex+1, 0);
            }
            setText(mText);
            startAnimation(startFadeOut);
            if(mIndex <= mText.length() -1 ) {
                //mText.replace(mIndex, mIndex, "X", mIndex, 1);
                //Log.d("TEST", "" + mText.getSpans(mIndex, mIndex, Object.class));
                mHandler.postDelayed(characterPulser, mDelay);
            }
            mIndex++;
        }
    };

    public void animateText(CharSequence text) {
        mText = new SpannableStringBuilder(text);
        mIndex = 0;

        setText(mText);
        mHandler.removeCallbacks(characterPulser);
        mHandler.postDelayed(characterPulser, mDelay);
    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }
}
