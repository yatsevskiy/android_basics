package my.samples.activity.lifecycle;

import my.samples.fragment.lifecycle.MyFragment;
import my.samples.service.lifecycle.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

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

		findViewById(R.id.hide_fragment_1).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						FragmentManager fragmentManager = getSupportFragmentManager();

						Fragment fragment = fragmentManager
								.findFragmentById(R.id.my_fragment_layout_1);

						FragmentTransaction ft = fragmentManager
								.beginTransaction();

						ft.setCustomAnimations(android.R.anim.slide_in_left,
								android.R.anim.slide_in_left);

						if (fragment.isHidden()) {
							ft.show(fragment);
						} else {
							ft.hide(fragment);
						}
						ft.commit();
					}
				});

		findViewById(R.id.push_fragment).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						FragmentTransaction ft = getSupportFragmentManager()
								.beginTransaction();
						ft.replace(R.id.my_fragment_layout_1, new MyFragment());
						ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						ft.addToBackStack(null);
						ft.commit();
					}
				});
	}
}
