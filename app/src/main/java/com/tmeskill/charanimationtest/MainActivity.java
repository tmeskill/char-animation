package com.tmeskill.charanimationtest;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String testString = "SampleStringSampleString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Typewriter writer = (Typewriter)findViewById(R.id.typewriter);
//        //Add a character every 150ms
//        writer.setCharacterDelay(150);
//        writer.animateText(testString);

        TextView text = (TextView) findViewById(R.id.textView);

        SpannableString testText = new SpannableString(testString);
        testText.setSpan(new StrikethroughSpan(), 1, 2, 0);
        text.setText(testText);

        PulseTextView pulser = (PulseTextView) findViewById(R.id.pulseTextView);
        pulser.setCharacterDelay(250);
        pulser.animateText(testString);

//        Animator anim = fadeStringIntoViewGroup((ViewGroup)findViewById(R.id.fader), "Snoozing ", 3000);
//        //here you can add a another listener to anim if you want (a listener could manipulate the views by set ids).
//        anim.start();
    }

    private Animator fadeStringIntoViewGroup(ViewGroup faderTextContainer, final String vls, int duration){
        Context context = faderTextContainer.getContext();
        final FrameLayout textViewHolder = new FrameLayout(context);
        final TextView textView = new TextView(context);
        final TextView helper = new TextView(context);
        textViewHolder.addView(helper);
        textViewHolder.addView(textView);

        SpannableString helperString = new SpannableString(vls);
        helperString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 1, vls.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setAlpha(0);
        helper.setText(helperString);
        setupCreatedViews(faderTextContainer, textViewHolder, textView, helper);

        final int length = vls.length();
        final int stepDuration = duration/length;
        ObjectAnimator anim = ObjectAnimator.ofFloat(helper, "alpha", 0, 1).setDuration(stepDuration);
        anim.setRepeatCount(length);
        anim.addListener(new Animator.AnimatorListener() {
            int at = 1;
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (at < vls.length()) {
                    SpannableString textViewString = new SpannableString(vls);
                    textViewString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), at, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(textViewString);
                    helper.setAlpha(0);

                    SpannableString helperString = new SpannableString(vls);
                    helperString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, at, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    helperString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), at + 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    helper.setText(helperString);
                    at++;
//                    if (at >= vls.length()) {
//                        at = 1;
//                    }
                }
            }
        });
        return anim;
    }

    private void setupCreatedViews(ViewGroup containerForAnimatedText, FrameLayout subcontainer, TextView textView, TextView textViewFadeinLetter){
        containerForAnimatedText.addView(subcontainer, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //here you can set colors, font sizes, ids, padding etc...
    }
}
