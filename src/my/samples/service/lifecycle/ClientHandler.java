package my.samples.service.lifecycle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ClientHandler extends Handler {

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
		switch (msg.what) {
			case ServiceHandler.SEND_BROADCAST_MESSAGE :

				onInvoke(0);

				Log.d(logTag(), "message: arg1 " + msg.arg1 + ", arg2 "
						+ msg.arg2);

				break;
			default :
				throw new IllegalArgumentException(Integer.toString(msg.what));
		}
	}
}
