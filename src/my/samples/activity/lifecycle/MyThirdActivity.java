package my.samples.activity.lifecycle;

import my.samples.fragment.lifecycle.UiFragment;
import android.os.Bundle;

public class MyThirdActivity extends MyBaseActivity {
	private static final String LOG_TAG = MyThirdActivity.class.getSimpleName();

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

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(android.R.id.content, new UiFragment()).commit();
		}
	}
}
