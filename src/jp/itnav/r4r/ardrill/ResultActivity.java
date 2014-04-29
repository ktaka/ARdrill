package jp.itnav.r4r.ardrill;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class ResultActivity extends Activity implements OnClickListener{
	private RelativeLayout ShowMapButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		ShowMapButton = (RelativeLayout)findViewById(R.id.ShowMapButton);
		ShowMapButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ShowMapButton:
			Intent intent = new Intent(this,MapActivity.class);
			startActivity(intent);
		}
		
	}

}
