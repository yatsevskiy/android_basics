package my.samples.database.lifecycle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	static final String ELEMENTS = "ELEMENTS";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "DATABASE_NAME";

	static final String ELEMENT_ID = "_id";
	static final String ELEMENT_KEY = "ELEMENT_KEY";
	static final String ELEMENT_VALUE = "ELEMENT_VALUE";

	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + ELEMENTS + "(" + ELEMENT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + ELEMENT_KEY
				+ " TEXT, " + ELEMENT_VALUE + " TEXT);");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + ELEMENTS);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);

		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA synchronous=OFF;");
			db.execSQL("PRAGMA temp_store=MEMORY;");
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}
}