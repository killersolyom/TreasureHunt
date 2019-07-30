package com.threess.summership.treasurehunt.util;

import android.content.Context;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


/**
 * This class contains static methods to create simple animations.
 *
 * @author MilleJanos
 */
public class Animator {

    public static long ANIMATION_DURATION = 500; // milliseconds
    private View mView = null;
    private AnimationSet mAnimationSet = null;
    private Context mContext = null;
    private boolean mInvisibleBeforeAnimation = false;

    // Directions
    public static final int FROM_LEFT = 0;
    public static final int FROM_TOP = 1;
    public static final int FROM_RIGHT = 2;
    public static final int FROM_BOTTOM = 3;
    public static final int TO_LEFT = 4;
    public static final int TO_TOP = 5;
    public static final int TO_RIGHT = 6;
    public static final int TO_BOTTOM = 7;


    public Animator(Context context, View view){
        mContext = context;
        mView = view;
        mAnimationSet = new AnimationSet(false);
    }


    public Animator(Context context, View view, boolean invisibleBeforeAnimation){
        mContext = context;
        mView = view;
        mAnimationSet = new AnimationSet(false);
        mInvisibleBeforeAnimation = invisibleBeforeAnimation;
        if(mInvisibleBeforeAnimation) {
            mView.setVisibility(View.INVISIBLE);
        }
    }


    public Animator(View view, boolean shareInterpolator){
        mView = view;
        mAnimationSet = new AnimationSet(shareInterpolator);
    }


    /**
     * This method adds a simple slide animation to the view_item, 'from' and 'to' the given parameter.
     * Animation duration = {@value ANIMATION_DURATION}
     * @param fromXDelta from X relative coordinate
     * @param toXDelta to X relative coordinate
     * @param fromYDelta from Y relative coordinate
     * @param toYDelta to Y relative coordinate
     */
    public void AddSlide(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta ){
        AddSlide(fromXDelta,  toXDelta,  fromYDelta,  toYDelta, ANIMATION_DURATION);
    }

    /**
     * This method adds a simple slide animation to the view_item, 'from' and 'to' the given parameter.
     * Duration of the animation can be set.
     * @param fromXDelta from X relative coordinate
     * @param toXDelta to X relative coordinate
     * @param fromYDelta from Y relative coordinate
     * @param toYDelta to Y relative coordinate
     * @param duration animation duration (ms)
     */
    public void AddSlide(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long duration){

        // Convert DPI to Pixels
        float fromXDeltaInPixel = DpiToPixelConverter(fromXDelta);
        float toXDeltaInPixel   = DpiToPixelConverter(toXDelta);
        float fromYDeltaInPixel = DpiToPixelConverter(fromYDelta);
        float toYDeltaInPixel   = DpiToPixelConverter(toYDelta);

        // Translate:
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDeltaInPixel, toXDeltaInPixel, fromYDeltaInPixel, toYDeltaInPixel);
        translateAnimation.setDuration( duration );

        //AnimationSet as = new AnimationSet(false);

        // Animation Set: (Translate+Alpha)
        mAnimationSet.addAnimation(translateAnimation);
    }


    public void AddScale(float fromX, float toX, float fromY, float pivotX, float pivotY, float toY){
        AddScale(fromX, toX,  fromY, toY, pivotX, pivotY, ANIMATION_DURATION);
    }

    public void AddScale(float fromX, float toX, float fromY, float toY, float pivotX, float pivotY, long duration){
        Animation scaleAnimation = new ScaleAnimation(
                fromX, toX,                                 // Start and end values for the X axis scaling
                fromY, toY,                                 // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, pivotX,       // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, pivotY);      // Pivot point of Y scaling
        scaleAnimation.setFillAfter(true);                         // Needed to keep the result of the animation
        scaleAnimation.setDuration(duration);

        mAnimationSet.addAnimation(scaleAnimation);
    }

    public void AddAlpha(float fromAlpha, float toAlpha, long startOffset, boolean fillAfter){
        AddAlpha(fromAlpha, toAlpha, startOffset, fillAfter, ANIMATION_DURATION);
    }

    public void AddAlpha(float fromAlpha, float toAlpha, long startOffset, boolean fillAfter, long duration){
        AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
        animation.setDuration(duration);
        animation.setStartOffset(startOffset);
        animation.setFillAfter(fillAfter);
        mAnimationSet.addAnimation( animation );
    }


    /**
     * This method adds logo intro animation to view_item.
     */
    public void AddIntroSet(){
        float distanceY = TypedValue.applyDimension(         // dip to pixels
                TypedValue.COMPLEX_UNIT_DIP, 45,
                mContext.getResources().getDisplayMetrics()
        );
        // Translate:
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, distanceY, 0);
        translateAnimation.setDuration(ANIMATION_DURATION);

        // Scale
        Animation scaleAnimation = new ScaleAnimation(
                2f, 1f, // Start and end values for the X axis scaling
                2f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        scaleAnimation.setFillAfter(true); // Needed to keep the result of the animation
        scaleAnimation.setDuration(ANIMATION_DURATION);

        // Animation Set: (Translate+Alpha)
        //AnimationSet as = new AnimationSet(false);
        mAnimationSet.addAnimation(translateAnimation);
        mAnimationSet.addAnimation(scaleAnimation);

        // Start Animations:
        //view_item.setAnimation(as);
    }

    public void Start(){
        // Start Animations:
        mView.setAnimation(mAnimationSet);
        if(mInvisibleBeforeAnimation) {
            mView.setVisibility(View.VISIBLE);
        }
    }

    public void Start(int delayMs){
        // Start Animations:
        new Handler().postDelayed(this::Start, delayMs);
    }

    /**
     * This method converts DPI value to pixels.
     * @param dpi_value in DPI
     * @return DPI value in pixels
     */
    public float DpiToPixelConverter(float dpi_value){

        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dpi_value,
                mContext.getResources().getDisplayMetrics()
        );
    }

}
