package my.samples.database.lifecycle;

import java.util.Set;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

public class ContantProviderActivity extends Activity {

	private static final String ELEMENT_ID = MySQLiteOpenHelper.ELEMENT_ID;
	private static final String ELEMENT_KEY = MySQLiteOpenHelper.ELEMENT_KEY;
	private static final String ELEMENT_VALUE = MySQLiteOpenHelper.ELEMENT_VALUE;
	private static final Uri ELEMENTS_TABLE_URI = Uri
			.parse(MyContentProvider.CONTENT + MySQLiteOpenHelper.ELEMENTS);

	private static final String LOG_TAG = ContantProviderActivity.class
			.getName();

	private ContentProviderClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		client = new MyContentProvider().initialize(getApplicationContext()
				.getApplicationContext());

		long s = System.currentTimeMillis();

		for (int i = 0; i < 1000; ++i) {
			final String value = String.valueOf(i);
			create(value, value);
		}

		Log.d(LOG_TAG, System.currentTimeMillis() - s + " ms");

		load();
	}

	private void load() {
		Cursor cursor;
		try {
			cursor = client.query(ELEMENTS_TABLE_URI, null, null, null,
					ELEMENT_ID);
		} catch (RemoteException e) {
			throw new AssertionError();
		}

		int id = cursor.getColumnIndex(ELEMENT_ID);
		int key = cursor.getColumnIndex(ELEMENT_KEY);
		int value = cursor.getColumnIndex(ELEMENT_VALUE);

		if (cursor.moveToFirst()) {

			do {
				Log.d(LOG_TAG, "id = " + cursor.getInt(id));
				Log.d(LOG_TAG, "key = " + cursor.getString(key));
				Log.d(LOG_TAG, "value = " + cursor.getString(value));
			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	public long create(String key, String value) {
		ContentValues values = new ContentValues();
		values.put(ELEMENT_KEY, key);
		values.put(ELEMENT_VALUE, value);

		long rValue;
		try {

			Uri uri = client.insert(ELEMENTS_TABLE_URI, values);
			rValue = Long.parseLong(uri.getPathSegments().get(1));

		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		return rValue;
	}

	public void delete(Set<Long> ids) {
		for (Long id : ids) {
			try {
				client.delete(ELEMENTS_TABLE_URI, "_id=?",
						new String[]{String.valueOf(id)});
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void update(long id, String newKey, String newValue) {
		ContentValues values = new ContentValues();
		values.put(ELEMENT_ID, id);
		values.put(ELEMENT_KEY, newKey);
		values.put(ELEMENT_VALUE, newValue);

		try {
			client.update(ELEMENTS_TABLE_URI, values, ELEMENT_ID + "=" + id,
					null);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}
}
