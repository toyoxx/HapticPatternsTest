package jp.ac.tohoku.mech.irs.hapticpatterntest.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import jp.ac.tohoku.mech.irs.hapticpatterntest.HapticPatternsController;
import jp.ac.tohoku.mech.irs.hapticpatterntest.PatternQuestion;
import jp.ac.tohoku.mech.irs.hapticpatterntest.R;

public class QuestionScreen extends Activity{
    static int[] botIds = {R.id.ansButton1, R.id.ansButton2, R.id.ansButton3, R.id.ansButton4};
    static int[] patIds = {R.drawable.pattern1,
            R.drawable.pattern2,
            R.drawable.pattern3,
            R.drawable.pattern4,
            R.drawable.pattern5,
            R.drawable.pattern6,
            R.drawable.pattern7,
            R.drawable.pattern8,
            R.drawable.pattern9,
            R.drawable.pattern10,
            R.drawable.pattern11,
            R.drawable.pattern12,
            R.drawable.pattern13,
            R.drawable.pattern14,
            R.drawable.pattern15,
            R.drawable.pattern16,
            R.drawable.pattern17,
            R.drawable.pattern18,
            R.drawable.pattern19,
            R.drawable.pattern20,
            R.drawable.pattern21,
            R.drawable.pattern22,
            R.drawable.pattern23,
            R.drawable.pattern24
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);
        TextView tv = (TextView)findViewById(R.id.questionNumber);
        tv.setText("Vibration Test Number "+(HapticPatternsController.getInstance().getCurrentQuestion()+1));


        ArrayList<Integer> options = new ArrayList<Integer>();
        options.add(HapticPatternsController.getInstance().getQuestions()[HapticPatternsController.getInstance().getCurrentQuestion()].getRightAnswer());
        for(int i=0; i< PatternQuestion.numOptions; i++ ){
            options.add(HapticPatternsController.getInstance().getQuestions()[HapticPatternsController.getInstance().getCurrentQuestion()].getOptions()[i]);
        }

        Collections.shuffle(options);
        for(int i = 0;i < 4; i++){
            ImageButton img = (ImageButton)findViewById(botIds[i]);
            int wi = img.getWidth();
            Resources res = getResources();
            Drawable dr = res.getDrawable(patIds[options.get(i)]);
            img.setImageDrawable(dr);
            img.getLayoutParams().width = wi;
        }
    }
    public void answerPressed(View view){
        ImageButton b = (ImageButton) view;
        b.setEnabled(false);

        if (HapticPatternsController.getInstance().getCurrentQuestion()==10){

        }else{
            HapticPatternsController.getInstance().advanceQuestion();

            Intent intent = new Intent(this, CountDownScreen.class);

            intent.putExtra(WelcomeScreen.COME_FROM_WELCOME_SCREEN, false);
            //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }

    }


}
