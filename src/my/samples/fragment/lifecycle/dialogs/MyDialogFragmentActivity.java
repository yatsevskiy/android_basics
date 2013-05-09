package my.samples.fragment.lifecycle.dialogs;

import my.samples.service.lifecycle.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class MyDialogFragmentActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_fragment);
		findViewById(R.id.show_dialog).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						MyAlertDialogFragment fragment = new MyAlertDialogFragment();
						Bundle args = new Bundle();
						args.putInt("title",
								R.string.alert_dialog_fragment_title);
						fragment.setArguments(args);
						fragment.show(getSupportFragmentManager(), "dialog");
					}
				});
	}
}