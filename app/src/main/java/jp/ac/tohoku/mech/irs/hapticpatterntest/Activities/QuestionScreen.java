package jp.ac.tohoku.mech.irs.hapticpatterntest.Activities;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import java.lang.reflect.Field;

import jp.ac.tohoku.mech.irs.hapticpatterntest.R;

public class QuestionScreen extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);
        int[] ids = {R.id.ansButton1, R.id.ansButton2, R.id.ansButton3, R.id.ansButton4};
        for(int i = 0;i < 4; i++){

            ImageButton img = (ImageButton)findViewById(ids[i]);
            int wi = img.getWidth();
            Resources res = getResources();
            Drawable dr = res.getDrawable(R.drawable.slide1);
            img.setImageDrawable(dr);
            img.getLayoutParams().width = wi;

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
