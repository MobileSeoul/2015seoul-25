package com.safebackhome.j6.safeme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
	String sql1,sql2,sql3,sql4,sql5;

	public DBManager(Context context) {
		super(context, "temp.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		sql1 = "CREATE TABLE PHONE( _id INTEGER PRIMARY KEY AUTOINCREMENT, first TEXT, second TEXT);";
//		sql2 = "CREATE TABLE SUBWAY( _id INTEGER PRIMARY KEY AUTOINCREMENT, first TEXT, second TEXT);";
//		sql3 = "CREATE TABLE QUICK( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, station TEXT, gu TEXT, phone TEXT);";
//		sql4 = "CREATE TABLE MAN( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, relation TEXT, number TEXT);";
		sql1 = "CREATE TABLE PHONE( _id INTEGER PRIMARY KEY AUTOINCREMENT, gu TEXT, number TEXT);";
		sql2 = "CREATE TABLE SUBWAY( _id INTEGER PRIMARY KEY AUTOINCREMENT, line TEXT, station TEXT, gu TEXT);";
		sql3 = "CREATE TABLE QUICK( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, icon INT, station TEXT, gu TEXT, phone TEXT);";
		sql4 = "CREATE TABLE MAN( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , number TEXT);";
		sql5 = "CREATE TABLE SIREN( _id INTEGER PRIMARY KEY AUTOINCREMENT, siren TEXT , size INT,time INT);";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS PHONE");
		db.execSQL("DROP TABLE IF EXISTS SUBWAY");
		db.execSQL("DROP TABLE IF EXISTS QUICK");
		db.execSQL("DROP TABLE IF EXISTS MAN");
		db.execSQL("DROP TABLE IF EXISTS SIREN");
	}
}
