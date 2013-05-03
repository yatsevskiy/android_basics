package my.samples.service.lifecycle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ServiceStarter extends Activity {

	private static final String LOG_TAG = ServiceStarter.class.getSimpleName();

	protected String logTag() {
		return LOG_TAG;
	}

	protected static final int REQUEST_CODE = 1;

	private void onInvoke(int level) {
		Log.d(logTag(), android.os.Process.myPid() + "; "
				+ Thread.currentThread().getName() + "; "
				+ Thread.currentThread().getStackTrace()[level + 3].toString());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		onInvoke(0);

		findViewById(R.id.start).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startService(new Intent(ServiceStarter.this, MyService.class));
			}
		});
		findViewById(R.id.stop).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				stopService(new Intent(ServiceStarter.this, MyService.class));
			}
		});
		findViewById(R.id.bind).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				bindService(new Intent(ServiceStarter.this, MyService.class),
						connection, Context.BIND_AUTO_CREATE);
			}
		});
		findViewById(R.id.unbind).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				unbindService(connection);
			}
		});

		findViewById(R.id.register_client).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Message message = Message.obtain(null,
								ServiceHandler.REGISTER_CLIENT);
						message.replyTo = clientMessenger;
						try {
							serviceMessenger.send(message);
						} catch (RemoteException e) {
							throw new RuntimeException(e);
						}
					}
				});

		findViewById(R.id.unregister_client).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Message message = Message.obtain(null,
								ServiceHandler.UNREGISTER_CLIENT);
						message.replyTo = clientMessenger;
						try {
							serviceMessenger.send(message);
						} catch (RemoteException e) {
							throw new RuntimeException(e);
						}
					}
				});

		findViewById(R.id.send_broadcast_message).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Message message = Message.obtain(null,
								ServiceHandler.SEND_BROADCAST_MESSAGE,
								1234567890, 0);

						try {
							serviceMessenger.send(message);
						} catch (RemoteException e) {
							throw new RuntimeException(e);
						}
					}
				});
	}

	private final Messenger clientMessenger = new Messenger(new ClientHandler());

	private IBinder service;

	private Messenger serviceMessenger;

	private final ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			onInvoke(1);
			ServiceStarter.this.service = service;
			if (ServiceStarter.this.service != null) {
				serviceMessenger = new Messenger(service);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			onInvoke(1);
		}
	};
}