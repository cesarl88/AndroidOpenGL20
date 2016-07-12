package android.cesar.com.openglapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {


    private static final String TAG = "MainActivity";
    private MyGLSurfaceView mGLView;

    private TextView speed;
    private TextView label;

    private boolean isZooming = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        View layout = findViewById(R.id.test1);
        mGLView = (MyGLSurfaceView)layout.findViewById(R.id.mSurface);

        speed = (TextView) layout.findViewById(R.id.speed);
        label = (TextView) layout.findViewById(R.id.label);

        final SeekBar sk=(SeekBar) layout.findViewById(R.id.speed_bar);

        mGLView.setSpeed(sk.getProgress() / 100.0f);
        speed.setText("" + sk.getProgress());

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                if(progress == 0)
                    progress = 1;
                speed.setText("" + progress);

                Log.d(TAG, "onProgressChanged: " + ((float) progress / 100.0f));

                if(MainActivity.this.isZooming)
                    mGLView.setZoomLevel((float) (100 - progress) / 100.0f);
                else
                    mGLView.setSpeed((float) progress / 100.0f);

            }
        });
        Log.d(TAG, "onCreate: "+mGLView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.zoom:
                if (checked) {    // Pirates are the best
                    this.mGLView.setIsRotating(false);

                    this.label.setText("Zoom");
                    this.isZooming = true;
                }
                    break;
            case R.id.rotate:
                if (checked)
                    // Ninjas rule
                {
                    this.mGLView.setIsRotating(true);
                    this.label.setText("Speed");
                    this.mGLView.setZoomLevel(1.0f);
                    this.isZooming = false;
                }
                    break;
        }
    }
}
