package databaseinterface;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Query {

	private String where = null;
	private String[] whereargs = null;
	private String[] select = null;
	private String groupBy = null;
	private String orderBy = null;
	private String table = null;
	private SQLiteDatabase database = null;

	public Query(String table, SQLiteDatabase database, String[] select, String where, String[] whereargs, String groupBy, String orderBy) {
		this.table = table;
		this.where = where;
		this.whereargs = whereargs;
		this.select = select;
		this.groupBy = groupBy;
		this.orderBy = orderBy;
		this.database = database;
	}
	public Query(SQLiteDatabase database) {
		this.database = database;
	}
	public Query(DBInterface face) {
		this.database = face.getDB();
	}
	public Query(SQLiteDatabase database, String table) {
		this.database = database;
		this.table = table;
	}
	public Query() {

	}
	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
	public Query db(DBInterface database) {
		this.database = database.getDB();
		return this;
	}

	public Query where(String where, String... whereargs) {
		if(this.where != null) {
			return and(where, whereargs);
		}
		this.where = where;
		this.whereargs = whereargs;
		if(whereargs.length == 0) {
			this.whereargs = null;
		}
		return this;
	}
	private Query and(String where, String... whereargs) {
		this.where += " AND " + where;
		if(whereargs.length > 0) {
			if(this.whereargs == null || this.whereargs.length == 0) {
				this.whereargs = whereargs;
			} else {
				String[] newArgs = new String[whereargs.length + this.whereargs.length];
				for(int i = 0; i < this.whereargs.length; i++) {
					newArgs[i] = this.whereargs[i];
				}
				for(int i = 0; i < whereargs.length; i++) {
					newArgs[i + this.whereargs.length] = whereargs[i];
				}
				this.whereargs = newArgs;
			}
		}
		return this;
	}
	public Query where(String where, Object... whereargs) {
		if(this.where != null) {
			String[] newArgs = new String[whereargs.length];
			for(int i = 0; i < whereargs.length; i++) {
				newArgs[i] = whereargs[i].toString();
			}
			return and(where, newArgs);
		}
		this.where = where;
		this.whereargs = new String[whereargs.length];
		for(int i = 0; i < whereargs.length; i++) {
			this.whereargs[i] = whereargs[i].toString();
		}
		if(whereargs.length == 0) {
			this.whereargs = null;
		}
		return this;
	}
	public Query whereID(int id) {
		return where(DBInterface.COLUMN_ID + "=?", Integer.toString(id));
	}
	public Cursor all() {
		Cursor c = database.query(table, select, where, whereargs, groupBy, null, orderBy);
		return c;
	}
	public Cursor first() {
		Cursor c = database.query(table, select, where, whereargs, groupBy, null, orderBy, "1");
		return c;
	}
	public Query groupBy(String groupBy) {
		this.groupBy = groupBy;
		return this;
	}
	public Query orderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}
	public Query select(String... select) {
		this.select = select;
		return this;
	}
	public Query from(String table) {
		this.table = table;
		return this;
	}
	public Query in(Class<? extends Record> cl) {
		return from(Record.getTableName(cl));
	}
	public Query in(Record cl) {
		return from(Record.getTableName(cl.getClass()));
	}
	public Query from(Class<? extends Record> cl) {
		return from(Record.getTableName(cl));
	}
	public Query from(Record cl) {
		return from(Record.getTableName(cl.getClass()));
	}
	public int insert(ContentValues values) {
		return (int) database.insert(table, null, values);
	}
	public int update(ContentValues values) {
		return database.update(table, values, where, whereargs);
	}
	public int update(int id, ContentValues values) {
		String where = DBInterface.COLUMN_ID + " = " + Integer.toString(id);
		return database.update(table, values, where, whereargs);
	}
	public int drop() {
		return database.delete(table, where, whereargs);
	}
	public int drop(int id) {
		whereID(id);
		return database.delete(table, where, whereargs);
	}
	public Cursor find(int id) {
		return where(DBInterface.COLUMN_ID + " = " + Integer.toString(id)).first();
	}
	public Cursor sql(String sql, String... selectionArgs) {
		return database.rawQuery(sql, selectionArgs);
	}
}
