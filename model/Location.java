package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import databaseinterface.Record;
import databaseinterface.DBInterface;

public class Location extends Record {
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_LAT_LONG = "lat_long";
	public static final String CREATE = "create table " + Record.getTableName(Location.class) + " (" + DBInterface.COLUMN_ID
			+ " integer primary key autoincrement, " + Location.COLUMN_NAME + " text not null, " + COLUMN_LAT_LONG + " text not null);";

	private String name;
	private String location;

	public Location(Cursor c) {
		super(c);
	}
	public Location() {
		super();
	}
	public Location(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}
	@Override
	public ContentValues getValues() {
		ContentValues cv = super.getValues();
		cv.put(COLUMN_NAME, getName());
		cv.put(COLUMN_LAT_LONG, getLocation());
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
		setLocation(c.getString(c.getColumnIndex(COLUMN_LAT_LONG)));
		setBalance(c.getString(c.getColumnIndex(COLUMN_BALANCE)));
		setGrades(c.getString(c.getColumnIndex(COLUMN_GRADES)));
		setScale(c.getFloat(c.getColumnIndex(COLUMN_SCALE)));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public static ArrayList<Location> getAll(Cursor c) {
		ArrayList<Location> ar = new ArrayList<Location>();
		if(c.moveToFirst()) {
			do {
				Location l = new Location();
				l.setFromCursor(c, false);
				ar.add(l);
			} while(c.moveToNext());
		}
		return ar;
	}
	@Override
	public String toString() {
		return getName() + " " + getLocation() + " (" + getID() + ")";
	}
	@Override
	protected boolean willDrop(SQLiteDatabase sqldb) {
		// Drop anything important

		return false;
	}
}
