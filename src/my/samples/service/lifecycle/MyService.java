package my.samples.service.lifecycle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

public class MyService extends Service {

	private static final String LOG_TAG = MyService.class.getSimpleName();

	protected String logTag() {
		return LOG_TAG;
	}

	private void onInvoke(int level) {
		Log.d(logTag(), android.os.Process.myPid() + "; "
				+ Thread.currentThread().getName() + "; "
				+ Thread.currentThread().getStackTrace()[level + 3].toString());
	}

	private NotificationManager mNotification;

	private void notification() {
		Notification notification = new Notification(R.drawable.icon,
				getText(R.string.service_started), System.currentTimeMillis());
		notification.setLatestEventInfo(this, getText(R.string.title),
				getText(R.string.content), PendingIntent.getActivity(this, 0,
						new Intent(this, ServiceStarter.class), 0));
		mNotification.notify(R.string.service_started, notification);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		onInvoke(0);
		mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		onInvoke(0);
		Log.d(logTag(),
				"-------------------------------------------------------------------------");
		return super.onStartCommand(intent, flags, startId);
	}

	private final Messenger messenger = new Messenger(new ServiceHandler());

	@Override
	public IBinder onBind(Intent intent) {
		onInvoke(0);
		Log.d(logTag(),
				"-------------------------------------------------------------------------");
		return messenger.getBinder();

	}

	@Override
	public boolean onUnbind(Intent intent) {
		onInvoke(0);
		Log.d(logTag(),
				"-------------------------------------------------------------------------");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		onInvoke(0);
		mNotification.cancel(R.string.service_started);
		Log.d(logTag(),
				"-------------------------------------------------------------------------");
	}
}