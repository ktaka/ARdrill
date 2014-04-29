package jp.itnav.r4r.ardrill;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.ActivityRecognitionClient;

public class MyActivityRecognition {

	private Context mContext;
	private ActivityRecognitionClient mClient;

	public MyActivityRecognition(Context context) {
		this.mContext = context;
		mClient = new ActivityRecognitionClient(mContext, mConnectionCallbacks,
				mOnConnectionFailedListener);
	}

	public void start() {
		mClient.connect();
	}

	public void stop() {
		mClient.disconnect();
	}

	private void getActivityRecognition() {
		Log.v("getActivityRecognition", "OK");
		// PendingIntent の生成
		Intent intent = new Intent(mContext, RecognitionIntentService.class);
		PendingIntent pendingIntent = PendingIntent.getService(mContext, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mClient.requestActivityUpdates(1000, pendingIntent);
	}

	private GooglePlayServicesClient.ConnectionCallbacks mConnectionCallbacks = new GooglePlayServicesClient.ConnectionCallbacks() {
		@Override
		public void onConnected(Bundle bundle) {
			Log.v("RecognitionActivityConnected", "OK");
			getActivityRecognition();
		}

		@Override
		public void onDisconnected() {
			Log.v("RecognitionActivityConnectedDisconnected", "OK");
		}
	};

	private GooglePlayServicesClient.OnConnectionFailedListener mOnConnectionFailedListener = new GooglePlayServicesClient.OnConnectionFailedListener() {
		@Override
		public void onConnectionFailed(ConnectionResult connectionResult) {
			Log.v("RecognitionActivityConnectedConnectionFailed", "OK");
			Log.v("connectionResult", String.valueOf(connectionResult));

		}
	};

}
