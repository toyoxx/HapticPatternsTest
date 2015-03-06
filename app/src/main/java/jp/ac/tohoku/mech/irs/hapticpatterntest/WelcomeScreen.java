package jp.ac.tohoku.mech.irs.hapticpatterntest;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;


public class WelcomeScreen extends Activity{
    public final static String COME_FROM_WELCOME_SCREEN = "jp.ac.tohoku.mech.irs.hapticpatterntest.COME_FROM_WELCOME_SCREEN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_screen, menu);
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

    public void nextScreen(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CircularCountdownTest.class);
        intent.putExtra(COME_FROM_WELCOME_SCREEN, true);
        startActivity(intent);

    }
}
