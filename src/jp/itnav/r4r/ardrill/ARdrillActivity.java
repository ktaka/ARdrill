/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.itnav.r4r.ardrill;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

import java.io.File;


public class ARdrillActivity extends Activity implements SensorEventListener {

	private static final float ALPHA = 0.8f;
	private static final int SENSITIVITY = 5;
	
	private SensorManager mSensorManager;
	private float mGravity[];
    ARdrillJNIView mView;

    @Override protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.ardrill);
        mView = new ARdrillJNIView(getApplication(), true, 24, 0);
        mView.setZOrderMediaOverlay(true);
        addContentView(mView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setupSensors();
    }
    
    void setupSensors() {
		mGravity = new float[3];
		mSensorManager = (SensorManager)getSystemService(
				Context.SENSOR_SERVICE);
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR),
				SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	super.onWindowFocusChanged(hasFocus);
    }
    
    @Override protected void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override protected void onResume() {
        super.onResume();
        mView.onResume();
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
//			mGravity[0] = ALPHA * mGravity[0] + (1 - ALPHA) * event.values[0];
//			mGravity[1] = ALPHA * mGravity[1] + (1 - ALPHA) * event.values[1];
//			mGravity[2] = ALPHA * mGravity[2] + (1 - ALPHA) * event.values[2];

			mGravity[0] = event.values[0];
			mGravity[1] = event.values[1];
			mGravity[2] = event.values[2];
			
			float q[] = new float[4];
			
			SensorManager.getQuaternionFromVector(q, event.values);
			//Log.i("ARdrill", "q[0]=" + q[0]);
			mView.setCameraAxis(-q[0], -q[1], q[2], q[3]);
//			mView.setCameraAxis(
//					event.values[1] - mGravity[1] * SENSITIVITY,
//					event.values[0] - mGravity[0] * SENSITIVITY, 0);
			
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
