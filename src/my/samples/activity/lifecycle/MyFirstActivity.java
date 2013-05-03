package my.samples.activity.lifecycle;

import my.samples.service.lifecycle.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MyFirstActivity extends MyBaseActivity {
	private static final String LOG_TAG = MyFirstActivity.class.getSimpleName();

	private static final int REQUEST_CODE = 1;

	@Override
	protected String logTag() {
		return LOG_TAG;
	}

	@Override
	protected String decoration() {
		return this.getClass().getName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main1);

		findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MyFirstActivity.this,
						MySecondActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		Log.d(LOG_TAG, "finalize() [" + this + "]");
	}

}
