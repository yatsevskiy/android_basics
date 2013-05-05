package my.samples.activity.lifecycle;

import my.samples.fragment.lifecycle.MyFragment;
import my.samples.service.lifecycle.R;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

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

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.my_fragment_layout_1, new MyFragment());
		ft.commit();
	}
}
