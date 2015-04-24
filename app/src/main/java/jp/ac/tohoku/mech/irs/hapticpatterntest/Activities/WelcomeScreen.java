package jp.ac.tohoku.mech.irs.hapticpatterntest.Activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import jp.ac.tohoku.mech.irs.hapticpatterntest.Bluetooth;
import jp.ac.tohoku.mech.irs.hapticpatterntest.HapticPatternsController;
import jp.ac.tohoku.mech.irs.hapticpatterntest.R;


public class WelcomeScreen extends Activity{
    public final static String COME_FROM_WELCOME_SCREEN = "jp.ac.tohoku.mech.irs.hapticpatterntest.COME_FROM_WELCOME_SCREEN";
    private static final String TAG = WelcomeScreen.class.getSimpleName();
    private TextView debug;
    private TextView status;
    private String clave ;
    boolean authenticated = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:
                    Log.d(TAG, "WS State Change" + msg.arg1);
                    switch (msg.arg1){
                        case Bluetooth.STATE_CONNECTING:
                            status.setText("Authenticating...");
                            break;
                        case Bluetooth.STATE_CONNECTED:
                            clave = getSaltString();
                            Bluetooth.getInstance().sendMessage(clave);
                            break;
                        default:
                            break;
                    }

                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.d(TAG, "WS Message Sent");
                    break;
                case Bluetooth.MESSAGE_READ:
                    Log.d(TAG, "WS Message Received "+(String)msg.obj);
                    if(authenticated == false) {
                        if (((String) msg.obj).equals(clave)) {
                            status.setText("Connected");
                            authenticated = true;
                        } else {
                            Bluetooth.getInstance().sendMessage(clave);
                        }
                    }else{

                    }
                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "WS Mess Dev Name"+msg);
                    break;
                case Bluetooth.MESSAGE_TOAST:
                    Log.d(TAG, "WS Ms"+msg);
                    break;
            }
        }
    };

    protected void onResume() {
        super.onResume();
        Button button1 = (Button) findViewById(R.id.startButton);
        button1.setEnabled(true);
    }



    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        debug = (TextView) findViewById(R.id.debug);
        status = (TextView) findViewById(R.id.status);
        Bluetooth.getInstance().setmHandler(mHandler);
        HapticPatternsController.getInstance().initializeQuestions();
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
        Button b = (Button) view;
        b.setEnabled(false);
        Intent intent = new Intent(this, CountDownScreen.class);
        intent.putExtra(COME_FROM_WELCOME_SCREEN, true);
        startActivity(intent);

    }

    public void connectService(View view){
        try {
            status.setText("Connecting...");
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter.isEnabled()) {
                Bluetooth.getInstance().start();
                Bluetooth.getInstance().connectDevice("HC-06");
                Log.d(TAG, "Btservice started - listening");
                status.setText("Listening...");

            } else {
                Log.w(TAG, "Btservice started - bluetooth is not enabled");
                status.setText("Bluetooth Not enabled");
            }
        } catch(Exception e){
            Log.e(TAG, "Unable to start bt ",e);
            status.setText("Unable to connect " +e);
        }
    }

    public void sendMessage(View view){
        Bluetooth.getInstance().sendMessage("JHOJOJO");
    }
}
