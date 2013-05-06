package my.samples.fragment.lifecycle;

import my.samples.service.lifecycle.R;
import android.os.Bundle;
import android.widget.ProgressBar;

public class WorkerFragment extends MyFragment {
	private static final String LOG_TAG = UiFragment.class.getSimpleName();

	@Override
	protected String logTag() {
		return LOG_TAG;
	}

	@Override
	protected String decoration() {
		return this.getClass().getName();
	}

	private ProgressBar mProgressBar;
	private int mProgress;
	private boolean mReady;
	private boolean mQuiting;

	final Thread mThread = new Thread() {
		@Override
		public void run() {

			int maxProgress = 10000;

			while (true) {
				synchronized (this) {
					while (!mReady || mProgress >= maxProgress) {
						if (mQuiting) {
							return;
						}
						try {
							wait();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}

					maxProgress = mProgressBar.getMax();
					mProgressBar.setProgress(++mProgress);

					try {
						wait(50);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mThread.start();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mProgressBar = (ProgressBar) getTargetFragment().getView()
				.findViewById(R.id.progress_horizontal);

		synchronized (mThread) {
			mReady = true;
			mThread.notify();
		}
	}

	@Override
	public void onDestroy() {
		synchronized (mThread) {
			mQuiting = true;

			mReady = false;
			mThread.notify();
		}

		super.onDestroy();
	}

	@Override
	public void onDetach() {
		synchronized (mThread) {
			mReady = false;
			mThread.notify();
		}

		super.onDetach();
	}

	public void restart() {
		synchronized (mThread) {
			mProgress = 0;
			mThread.notify();
		}
	}
}
