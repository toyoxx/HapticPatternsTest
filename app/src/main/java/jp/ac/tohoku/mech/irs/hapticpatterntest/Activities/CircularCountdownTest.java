package jp.ac.tohoku.mech.irs.hapticpatterntest.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import com.sefford.circularprogressdrawable.CircularProgressDrawable;

import jp.ac.tohoku.mech.irs.hapticpatterntest.R;


public class CircularCountdownTest extends Activity {

    // Views
    ImageView ivDrawable;
    Button btStyle1;
    Button btStyle2;
    Button btStyle3;
    Button btStyle4;

    CircularProgressDrawable drawable;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentAnimation != null) {
                currentAnimation.cancel();
            }
            switch (v.getId()) {
                case R.id.bt_style_1:
                    currentAnimation = prepareStyle1Animation();
                    break;
                case R.id.bt_style_2:
                    currentAnimation = prepareStyle4Animation();
                    break;
                case R.id.bt_style_3:
                    currentAnimation = prepareStyle3Animation();
                    break;
                case R.id.bt_style_4:
                default:
                    currentAnimation = preparePulseAnimation();
                    break;

            }
            currentAnimation.start();
        }
    };

    Animator currentAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_countdown_test);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivDrawable = (ImageView) findViewById(R.id.iv_drawable);
        btStyle1 = (Button) findViewById(R.id.bt_style_1);
        btStyle2 = (Button) findViewById(R.id.bt_style_2);
        btStyle3 = (Button) findViewById(R.id.bt_style_3);
        btStyle4 = (Button) findViewById(R.id.bt_style_4);

        drawable = new CircularProgressDrawable.Builder()
                .setRingWidth(getResources().getDimensionPixelSize(R.dimen.drawable_ring_size))
                .setOutlineColor(getResources().getColor(android.R.color.darker_gray))
                .setRingColor(getResources().getColor(android.R.color.holo_green_light))
                .setCenterColor(getResources().getColor(android.R.color.holo_blue_dark))
                .create();
        ivDrawable.setImageDrawable(drawable);
        hookUpListeners();
    }

    private void hookUpListeners() {
        ivDrawable.setOnClickListener(listener);
        btStyle1.setOnClickListener(listener);
        btStyle2.setOnClickListener(listener);
        btStyle3.setOnClickListener(listener);
        btStyle4.setOnClickListener(listener);

    }

    /**
     * This animation was intended to keep a pressed state of the Drawable
     *
     * @return Animation
     */
    private Animator preparePressedAnimation() {
        Animator animation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                drawable.getCircleScale(), 0.65f);
        animation.setDuration(120);
        return animation;
    }

    /**
     * This animation will make a pulse effect to the inner circle
     *
     * @return Animation
     */
    private Animator preparePulseAnimation() {
        AnimatorSet animation = new AnimatorSet();

        Animator firstBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                drawable.getCircleScale(), 0.88f);
        firstBounce.setDuration(300);
        firstBounce.setInterpolator(new CycleInterpolator(1));
        Animator secondBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                0.75f, 0.83f);
        secondBounce.setDuration(300);
        secondBounce.setInterpolator(new CycleInterpolator(1));
        Animator thirdBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                0.75f, 0.80f);
        thirdBounce.setDuration(300);
        thirdBounce.setInterpolator(new CycleInterpolator(1));

        animation.playSequentially(firstBounce, secondBounce, thirdBounce);
        return animation;
    }

    /**
     * Style 1 animation will simulate a indeterminate loading while taking advantage of the inner
     * circle to provide a progress sense
     *
     * @return Animation
     */
    private Animator prepareStyle1Animation() {
        AnimatorSet animation = new AnimatorSet();

        final Animator indeterminateAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0, 3600);
        indeterminateAnimation.setDuration(3600);

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.75f);
        innerCircleAnimation.setDuration(3600);
        innerCircleAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                drawable.setIndeterminate(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                indeterminateAnimation.end();
                drawable.setIndeterminate(false);
                drawable.setProgress(0);
            }
        });

        animation.playTogether(innerCircleAnimation, indeterminateAnimation);
        return animation;
    }

    /**
     * Style 2 animation will fill the outer ring while applying a color effect from red to green
     *
     * @return Animation
     */
    private Animator prepareStyle2Animation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY,
                0f, 1f);
        progressAnimation.setDuration(1000);
        progressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(drawable, CircularProgressDrawable.RING_COLOR_PROPERTY,
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_light));
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(1000);
        colorAnimator.setRepeatCount(3);
        progressAnimation.setRepeatCount(3);


        animation.playTogether(progressAnimation, colorAnimator);
        return animation;
    }

    private Animator prepareStyle4Animation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY,
                1f, 0.66f);
        progressAnimation.setDuration(1000);
        progressAnimation.setInterpolator(new OvershootInterpolator());

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(drawable, CircularProgressDrawable.RING_COLOR_PROPERTY,
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_light));
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(1000);

        ObjectAnimator progressAnimation2 = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY,
                0.66f, 0.33f);
        progressAnimation2.setDuration(1000);
        progressAnimation2.setStartDelay(1000);
        progressAnimation2.setInterpolator(new OvershootInterpolator());

        ObjectAnimator progressAnimation3 = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY,
                0.33f, 0f);
        progressAnimation3.setDuration(1000);
        progressAnimation3.setStartDelay(2000);
        progressAnimation3.setInterpolator(new OvershootInterpolator());


        ObjectAnimator colorAnimator2 = ObjectAnimator.ofInt(drawable, CircularProgressDrawable.RING_COLOR_PROPERTY,
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_dark));
        colorAnimator2.setEvaluator(new ArgbEvaluator());
        colorAnimator2.setDuration(1000);
        colorAnimator2.setStartDelay(1000);


        ObjectAnimator colorAnimator3= ObjectAnimator.ofInt(drawable, CircularProgressDrawable.RING_COLOR_PROPERTY,
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_light));
        colorAnimator3.setEvaluator(new ArgbEvaluator());
        colorAnimator3.setDuration(1000);
        colorAnimator3.setStartDelay(2000);

        animation.playTogether(progressAnimation, progressAnimation2, progressAnimation3, colorAnimator, colorAnimator2, colorAnimator3);
        return animation;
    }



    /**
     * Style 3 animation will turn a 3/4 animation with Anticipate/Overshoot interpolation to a
     * blank waiting - like state, wait for 2 seconds then return to the original state
     *
     * @return Animation
     */
    private Animator prepareStyle3Animation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0.75f, 0f);
        progressAnimation.setDuration(1200);
        progressAnimation.setInterpolator(new AnticipateInterpolator());

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0.75f, 0f);
        innerCircleAnimation.setDuration(1200);
        innerCircleAnimation.setInterpolator(new AnticipateInterpolator());

        ObjectAnimator invertedProgress = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0f, 0.75f);
        invertedProgress.setDuration(1200);
        invertedProgress.setStartDelay(3200);
        invertedProgress.setInterpolator(new OvershootInterpolator());

        Animator invertedCircle = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.75f);
        invertedCircle.setDuration(1200);
        invertedCircle.setStartDelay(3200);
        invertedCircle.setInterpolator(new OvershootInterpolator());

        animation.playTogether(progressAnimation, innerCircleAnimation, invertedProgress, invertedCircle);
        return animation;
    }
}
