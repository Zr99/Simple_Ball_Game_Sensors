package com.example.assignmentsix;

import androidx.annotation.Dimension;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import java.lang.reflect.GenericArrayType;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManger;
    Sensor accelerometer;
    String TAG = "hello";

    Paint[] paint ={new Paint(),new Paint(),new Paint()};
    int RADIUS = 50;

    int x1 =0; int x2 = 0; int x3 = 0;
    int y1 =0; int y2 = 0; int y3 = 0;

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Log.d(TAG, "onSensorChanged: "+ "X:  "+sensorEvent.values[0] +" Y:  "+ sensorEvent.values[1] + sensorEvent.values[2] );

            x1 -= sensorEvent.values[0];
            y1 += sensorEvent.values[1];
            x2 -= sensorEvent.values[0] * 2;
            y2 += sensorEvent.values[1] * 2;
            x3 -= sensorEvent.values[0] * 3;
            y3 += sensorEvent.values[1] * 3;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) { }

    };

    public class GraphicView extends View {

        public GraphicView(Context context){
            super (context);
            paint[0].setColor(getColor(R.color.colorPrimary));
            paint[1].setColor(getColor(R.color.colorAccent));
            paint[2].setColor(getColor(R.color.colorPrimaryDark));
        }

        @Override
        protected void onDraw(Canvas canvas){

            int maxX = getWidth();
            int maxY = getHeight();

            super.onDraw(canvas);
            if(x1+ RADIUS > maxX){
               x1  =maxX - RADIUS;
            }
            if(y1 + RADIUS > maxY){
                y1 = maxY - RADIUS;
            }
            if(x1 < RADIUS){
                x1 = RADIUS;
            }
            if(y1 < RADIUS){
                y1 = RADIUS;
            }

            if(x2 + RADIUS > maxX){
                x2 =maxX - RADIUS;
            }
            if(y2 + RADIUS > maxY){
                y2 =maxY - RADIUS;
            }
            if(x2 < RADIUS){
                x2 = RADIUS;
            }
            if(y2 < RADIUS){
                y2 = RADIUS;
            }

            if(x3 + RADIUS > maxX){
                x3 =maxX - RADIUS;
            }
            if(y3 + RADIUS > maxY){
                y3 =maxY - RADIUS;
            }
            if(x3 < RADIUS){
                x3 = RADIUS;
            }
            if(y3 < RADIUS){
                y3 = RADIUS;
            }

            canvas.drawCircle(x1,y1,RADIUS,paint[0]);
            canvas.drawCircle(x2,y2,RADIUS,paint[1]);
            canvas.drawCircle(x3,y3,RADIUS,paint[2]);
            invalidate();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sensor part
        sensorManger = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Draw the circle
        GraphicView view = new GraphicView(this);
        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.inner);
        constraintLayout.addView(view);

        //Full-Screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManger.registerListener(sensorEventListener,accelerometer,SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManger.unregisterListener(sensorEventListener,accelerometer);
    }
}
