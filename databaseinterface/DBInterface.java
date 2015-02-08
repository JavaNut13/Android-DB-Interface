package databaseinterface;

import model.Location;
import model.Person;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBInterface extends SQLiteOpenHelper {
	public static final String COLUMN_ID = "_id";
	public static final int DEFAULT_ID = -1;

	private static final String DATABASE_NAME = "logger.db";
	private static final int DATABASE_VERSION = 17;

	private SQLiteDatabase database;

	public DBInterface(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public SQLiteOpenHelper open() throws SQLException {
		database = getWritableDatabase();
		return this;
	}
	public SQLiteDatabase getDB() {
		return database;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(Location.CREATE);
		database.execSQL(Person.CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBInterface.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data :(");
		db.execSQL("DROP TABLE IF EXISTS " + Record.getTableName(Location.class));
		db.execSQL("DROP TABLE IF EXISTS " + Record.getTableName(Person.class));
		onCreate(db);
	}

}