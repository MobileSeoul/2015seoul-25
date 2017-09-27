package com.safebackhome.j6.safeme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManagerHandler {
	private DBManager mDBManager;
	private SQLiteDatabase db;

	public DBManagerHandler(Context context) {
		this.mDBManager = new DBManager(context);
	}

	public void close() {
		db.close();
	}


	public void insert(String tableName, String text1, String text2) {
		db = mDBManager.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.put("gu", text1);
		val.put("number", text2);
		db.insert(tableName, null, val);
	}
	public void insert_quick(String tableName, String text1, int text2,String text3,String text4,String text5) {
		db = mDBManager.getWritableDatabase();

		ContentValues val = new ContentValues();
		val.put("name", text1);
		val.put("icon",text2);
		val.put("station", text3);
		val.put("gu", text4);
		val.put("phone", text5);
		db.insert(tableName, null, val);
		Log.d("g", text1);
		//Toast.makeText(,"ok",Toast.LENGTH_SHORT).show();
	}
	public void insert2(String tableName, String line, String nm, String gu) {
		db = mDBManager.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.put("line", line);
		val.put("station", nm);
		val.put("gu", gu);
		db.insert(tableName, null, val);
		Log.d("insert",nm);
	}
	public void insert3(String tableName, String text1, String text2) {
		db = mDBManager.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.put("name", text1);
		val.put("number", text2);
		db.insert(tableName, null, val);
		Log.d("g", text1);
	}
	public void insert4(String tableName, String text,int size, int time){
		db=mDBManager.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.put("siren", text);
		val.put("size", size);
		val.put("time", time);
		db.insert(tableName, null, val);
		Log.d("a",text);
	}

	public Cursor select(String tableName, String text) {
		db = mDBManager.getReadableDatabase();
		Cursor cursor = db.rawQuery("select distinct * from "+ tableName +" where gu like '" + text + "';", null);
		Log.d("select",String.valueOf(cursor.moveToNext()));
		return cursor;
	}
	public Cursor select2(String tableName, String sub){
		db = mDBManager.getReadableDatabase();
		Cursor cursor = db.rawQuery("select distinct * from " + tableName +" where station like '"+sub+"';",null);
		Log.d("select",String.valueOf(cursor.getCount()));
		return cursor;
	}

	public Cursor select_quick(String tableName) {
		db = mDBManager.getReadableDatabase();
		Cursor cursor = db.rawQuery("select distinct * from "+ tableName +";", null);
		return cursor;
	}

	public void deleteByTableName(String tableName, int data){
		db = mDBManager.getWritableDatabase();
		db.execSQL("DELETE from "+tableName+" where _id like '"+data+"';");

		Log.i("db",data+" Delete Complete.");
	}
	public Boolean getCountByTableName1(String tableName){
		db = mDBManager.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from "+tableName +";", null);
		Boolean cnt=cursor.moveToNext();
		Log.d("count",String.valueOf(cnt));
		return cnt;
	}
	public int getCountByTableName(String tableName){
		db = mDBManager.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from "+tableName +";", null);
		int cnt=cursor.getCount();
		return cnt;
	}
	public void deleteByTableName2(String tableName, String col, String data){
		db = mDBManager.getWritableDatabase();
		db.delete(tableName, col+"=?",new String[]{data});
		Log.i("db",data+" Delete Complete.");
	}
	public void modify(String tableName, String siren, int time, int volume){
		db=mDBManager.getWritableDatabase();
		ContentValues values=new ContentValues();

		values.put("siren",siren);
		values.put("time",time);
		values.put("size",volume);

		db.update(tableName,values,"'1'=?",new String[]{"1"});
	}
	/*public Cursor select2(String sub){
		db = mDBManager.getReadableDatabase();
		Cursor cursor = db.rawQuery("select distinct * from subway where name like '"+sub+"';",null);
		return cursor;
	}*/
}
