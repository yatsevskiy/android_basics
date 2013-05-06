package my.samples.fragment.lifecycle;

import my.samples.service.lifecycle.R;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class UiFragment extends MyFragment {
	private static final String LOG_TAG = UiFragment.class.getSimpleName();

	@Override
	protected String logTag() {
		return LOG_TAG;
	}

	@Override
	protected String decoration() {
		return this.getClass().getName();
	}

	private WorkerFragment mWorkerFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.ui_fragment, container, false);
		v.findViewById(R.id.restart).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mWorkerFragment.restart();
			}
		});
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentManager fm = getFragmentManager();
		mWorkerFragment = (WorkerFragment) fm.findFragmentByTag("worker");
		if (mWorkerFragment == null) {
			mWorkerFragment = new WorkerFragment();
			mWorkerFragment.setTargetFragment(this, 0);
			fm.beginTransaction().add(mWorkerFragment, "worker").commit();
		}
	}
}