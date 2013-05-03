package my.samples.activity.lifecycle;

import my.samples.service.lifecycle.R;
import android.os.Bundle;

public class MySecondActivity extends MyBaseActivity {
	private static final String LOG_TAG = MySecondActivity.class
			.getSimpleName();

	@Override
	protected String logTag() {
		return LOG_TAG;
	}
	@Override
	protected String decoration() {
		return getClass().getName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
	}
}
