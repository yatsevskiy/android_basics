package my.samples.database.lifecycle;

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
	private static final String AUTHORITY = "my.samples.database.lifecycle";
	static final String CONTENT = "content://" + AUTHORITY + "/";

	private static final Uri CONTENT_URI = Uri.parse(CONTENT);
	private static final Uri CONTENT_URI_BEGIN_TRANSACTION = Uri.parse(CONTENT
			+ "begin_transaction");
	private static final Uri CONTENT_URI_APPROVE_TRANSACTION = Uri
			.parse(CONTENT + "approve_transaction");
	private static final Uri CONTENT_URI_END_TRANSACTION = Uri.parse(CONTENT
			+ "end_transaction");

	private SQLiteDatabase db;

	private ContentProviderClient client;

	public ContentProviderClient initialize(Context context) {
		client = context.getApplicationContext().getContentResolver()
				.acquireContentProviderClient(CONTENT_URI);
		return client;
	}

	@Override
	public boolean onCreate() {
		db = new MySQLiteOpenHelper(getContext()).getWritableDatabase();
		return db != null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		if (uri.equals(CONTENT_URI_BEGIN_TRANSACTION)) {
			db.beginTransaction();
			return null;
		}

		if (uri.equals(CONTENT_URI_APPROVE_TRANSACTION)) {
			db.setTransactionSuccessful();
			return null;
		}

		if (uri.equals(CONTENT_URI_END_TRANSACTION)) {
			db.endTransaction();
			return null;
		}

		SQLiteQueryBuilder b = new SQLiteQueryBuilder();
		b.setTables(getType(uri));
		b.buildQuery(projection, selection, selectionArgs, null, null,
				sortOrder, null);
		return b.query(db, projection, selection, selectionArgs, null, null,
				sortOrder);
	}

	@Override
	public String getType(Uri uri) {
		List<String> segments = uri.getPathSegments();

		if (AUTHORITY.equals(uri.getAuthority()) && !segments.isEmpty()) {
			return segments.get(0);
		}

		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		long id;

		if (values != null && (id = db.insert(getType(uri), "", values)) != -1) {
			Uri rUri = ContentUris.withAppendedId(uri, id);
			getContext().getContentResolver().notifyChange(rUri, null);
			return rUri;
		}

		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return db.delete(getType(uri), selection, selectionArgs);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return db.update(getType(uri), values, selection, selectionArgs);
	}
}
