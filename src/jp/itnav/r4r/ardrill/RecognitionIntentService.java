package jp.itnav.r4r.ardrill;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

public class RecognitionIntentService extends IntentService {


	public RecognitionIntentService() {
		super("RecognitionIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.v("onHandleIntent", "OK");
		if (ActivityRecognitionResult.hasResult(intent)) {
			ActivityRecognitionResult result = ActivityRecognitionResult
					.extractResult(intent);
			DetectedActivity mostProbableActivity = result
					.getMostProbableActivity();

			int activityType = mostProbableActivity.getType();	
			
			Intent resultIntent = new Intent("receive_recognition");
			resultIntent.setPackage(getPackageName());
			resultIntent.putExtra("activity_type", activityType);
			sendBroadcast(resultIntent);

		}
	}

	
}
