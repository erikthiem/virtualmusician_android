package com.erikthiem.virtualmusician;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int sensorSpeed;

    private long lastUpdate = 0;
    private float x, y, z;
    private static final int SHAKE_THRESHOLD = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sensorSpeed = SensorManager.SENSOR_DELAY_GAME;

        // Initialize the accelerometer sensor
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, this.sensorSpeed);
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

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, this.sensorSpeed);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            this.x = sensorEvent.values[0];
            this.y = sensorEvent.values[1];
            this.z = sensorEvent.values[2];

            long currentTime = System.currentTimeMillis();

            // Only check every 100 miliseconds
            int checkEveryMilliseconds = 100;
            if ((currentTime - lastUpdate) > checkEveryMilliseconds) {
                long differenceInTime = (currentTime - lastUpdate);
                lastUpdate = currentTime;
            }

            // Update onscreen values
            TextView xValue = (TextView) findViewById(R.id.xValue);
            TextView yValue = (TextView) findViewById(R.id.yValue);
            TextView zValue = (TextView) findViewById(R.id.zValue);
            xValue.setText("x: " + this.x);
            yValue.setText("y: " + this.y);
            zValue.setText("z: " + this.z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
