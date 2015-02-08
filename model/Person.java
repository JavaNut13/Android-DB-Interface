package model;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import databaseinterface.Record;
import databaseinterface.DBInterface;

public class Person extends Record {

	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_NOTES = "notes";

	public static final String[] columns = new String[] { DBInterface.COLUMN_ID, COLUMN_NAME, COLUMN_NOTES };

	public static final String CREATE = "create table " + Record.getTableName(Person.class) + " (" + DBInterface.COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME + " text, " + COLUMN_NOTES + " text);";

	private String name;
	private String notes;

	public Person(Cursor c) {
		// setFromCursor(c);
		super(c);
	}
	public Person() {
		super();
	}

	@Override
	public ContentValues getValues() {
		ContentValues cv = super.getValues();
		cv.put(COLUMN_NAME, getName());
		cv.put(COLUMN_NOTES, getNotes());
		return cv;
	}
	@Override
	public void setFromCursor(Cursor c) {
		setFromCursor(c, true);
	}
	@Override
	public void setFromCursor(Cursor c, boolean moveFirst) {
		super.setFromCursor(c, moveFirst);
		setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
		setNotes(c.getString(c.getColumnIndex(COLUMN_NOTES)));
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public static ArrayList<Person> getAll(Cursor c) {
		ArrayList<Person> ar = new ArrayList<Person>();
		if(c.moveToFirst()) {
			do {
				Person l = new Person();
				l.setFromCursor(c, false);
				ar.add(l);
			} while((c.moveToNext()));
		}
		return ar;
	}
	@Override
	public String toString() {
		return getName();
	}
	@Override
	protected boolean willDrop(SQLiteDatabase sqldb) {
		// drop all shipments from this location

		return false;
	}
}
