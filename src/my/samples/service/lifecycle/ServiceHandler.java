package my.samples.service.lifecycle;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class ServiceHandler extends Handler {
	private final List<Messenger> clients = new ArrayList<Messenger>();
	private final List<Messenger> deadClients = new ArrayList<Messenger>();

	public static final int REGISTER_CLIENT = 1;
	public static final int UNREGISTER_CLIENT = 2;
	public static final int SEND_BROADCAST_MESSAGE = 3;

	private static final String LOG_TAG = ClientHandler.class.getSimpleName();

	protected String logTag() {
		return LOG_TAG;
	}

	private void onInvoke(int level) {
		Log.d(logTag(), android.os.Process.myPid() + "; "
				+ Thread.currentThread().getName() + "; "
				+ Thread.currentThread().getStackTrace()[level + 3].toString());
	}

	@Override
	public void handleMessage(Message msg) {
		onInvoke(0);

		switch (msg.what) {
			case REGISTER_CLIENT :
				clients.add(msg.replyTo);
				break;
			case UNREGISTER_CLIENT :
				clients.remove(msg.replyTo);
				break;
			case SEND_BROADCAST_MESSAGE :
				for (Messenger client : clients) {
					try {
						client.send(Message.obtain(null,
								SEND_BROADCAST_MESSAGE, msg.arg1, 0));
					} catch (RemoteException e) {
						deadClients.add(client);
					}
				}

				if (!deadClients.isEmpty()) {
					clients.removeAll(deadClients);
					deadClients.clear();
				}

				break;
			default :
				throw new IllegalArgumentException(Integer.toString(msg.what));
		}
	}
}