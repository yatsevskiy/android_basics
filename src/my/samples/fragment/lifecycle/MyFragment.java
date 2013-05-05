package my.samples.fragment.lifecycle;

import java.util.Random;

import my.samples.service.lifecycle.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends Fragment {
	private static final String LOG_TAG = MyFragment.class.getSimpleName();

	protected String logTag() {
		return LOG_TAG;
	}

	protected String decoration() {
		return this.getClass().getName();
	}

	private void onInvoke() {
		Log.d(logTag(),
				Thread.currentThread().getName()
						+ "; "
						+ decoration()
						+ "::"
						+ Thread.currentThread().getStackTrace()[3]
								.getMethodName());
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		onInvoke();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onInvoke();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		onInvoke();

		View v = inflater.inflate(R.layout.fragment, container, false);

		Random rnd = new Random(System.currentTimeMillis());

		v.findViewById(R.id.textbox).setBackgroundColor(
				Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),
						rnd.nextInt(256)));

		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		onInvoke();
	}

	@Override
	public void onStart() {
		super.onStart();
		onInvoke();
	}

	@Override
	public void onResume() {
		super.onResume();
		onInvoke();
	}

	@Override
	public void onPause() {
		super.onPause();
		onInvoke();
	}

	@Override
	public void onStop() {
		super.onStop();
		onInvoke();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		onInvoke();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		onInvoke();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		onInvoke();
	}
}
