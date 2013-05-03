package my.samples.activity.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public abstract class MyBaseActivity extends Activity {

	protected abstract String logTag();

	protected abstract String decoration();

	protected static final int REQUEST_CODE = 1;

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onInvoke();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		onInvoke();
	}

	@Override
	protected void onStart() {
		super.onStart();
		onInvoke();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		onInvoke();
	}

	@Override
	protected void onResume() {
		super.onResume();
		onInvoke();
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		onInvoke();
		Log.d(logTag(),
				"-------------------------------------------------------------------------");
	}

	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------

	@Override
	protected void onPause() {
		super.onPause();
		onInvoke();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		onInvoke();
	}

	@Override
	protected void onStop() {
		super.onStop();
		onInvoke();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onInvoke();
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		onInvoke();
		Log.d(logTag(),
				"-------------------------------------------------------------------------");
	}
}
