package databaseinterface;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class Record {
	private int id = DBInterface.DEFAULT_ID;

	public Record(Cursor c) {
		if(c.getCount() > 0) {
			setFromCursor(c);
		}
	}
	public Record() {
	}

	// For insert and update
	public ContentValues getValues() {
		ContentValues cv = new ContentValues();
		if(id != -1) {
			cv.put(DBInterface.COLUMN_ID, getID());
		}
		return cv;
	}
	// For read
	public void setFromCursor(Cursor c) {
		setFromCursor(c, true);
	}
	public void setFromCursor(Cursor c, boolean moveFirst) {
		if(moveFirst) c.moveToFirst();
		setID(c.getInt(c.getColumnIndex(DBInterface.COLUMN_ID)));
	}

	public int getID() {
		return id;
	}
	private void setID(int id) {
		this.id = id;
	}

	public void save(SQLiteDatabase database) {
		if(getID() == -1) {
			setID(new Query(database).in(this).insert(getValues()));
		} else {
			new Query(database).in(this).update(getID(), getValues());
		}
	}
	public int drop(SQLiteDatabase database) {
		if(getID() == -1) {
			return 0;
		}
		willDrop(database);
		return new Query(database).in(this).drop(getID());
	}
	public boolean isSaved() {
		return getID() > -1;
	}
	public void save(DBInterface database) {
		save(database.getDB());
	}
	public void drop(DBInterface database) {
		drop(database.getDB());
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof Record) {
			return getID() == ((Record) o).getID();
		}
		return super.equals(o);
	}
	// Called when the record is going to drop, delete dependencies.
	protected abstract boolean willDrop(SQLiteDatabase sqldb);
	public static String getTableName(Class<? extends Record> cl) {
		return cl.getClass().getSimpleName();
	}
	public String getTableName() {
		return getTableName(getClass());
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + " id: " + Integer.toString(getID());
	}
}
