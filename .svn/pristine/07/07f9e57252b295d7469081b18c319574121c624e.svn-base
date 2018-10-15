package kr.co.genexon.factFinder.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by topgu on 2017-10-26.
 */

public class DBLoginHelper {

    protected static final String TAG = "FactFinder";
    private static final String DATABASE_NAME = "fflogin.db";
//    private static final String DATABASE_NAME = "/mnt/sdcard/fflogin.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DataBaseHelper mDBHelper;
    private Context mCtx;

    private class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(LoginDB.createDB._CREATE);
            Log.d(TAG, "디비생성완료");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + LoginDB.createDB._TABLENAME);
            onCreate(db);
        }
    }

    public DBLoginHelper(Context context) {
        this.mCtx = context;
    }

    public DBLoginHelper open() throws SQLException {
        mDBHelper = new DataBaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDB.close();
    }

    public long insertColumn(String mb_id, String cust_id, String passwd) {
        ContentValues values = new ContentValues();
        values.put(LoginDB.createDB.MB_ID, mb_id);
        values.put(LoginDB.createDB.CUST_ID, cust_id);
        values.put(LoginDB.createDB.PASSWD, passwd);
        return mDB.insert(LoginDB.createDB._TABLENAME, null, values);
    }

    public boolean updateColumn(long id, String mb_id, String cust_id, String passwd) {
        ContentValues values = new ContentValues();
        values.put(LoginDB.createDB.MB_ID, mb_id);
        values.put(LoginDB.createDB.CUST_ID, cust_id);
        values.put(LoginDB.createDB.PASSWD, passwd);
        return mDB.update(LoginDB.createDB._TABLENAME, values, "_id="+id, null) > 0;
    }

    public boolean deleteColumn(long id) {
        return mDB.delete(LoginDB.createDB._TABLENAME, "_id=" + id, null) > 0;
    }

    public Cursor getAllColumns() {
        return mDB.query(LoginDB.createDB._TABLENAME, null, null, null, null, null, null);
    }

    public Cursor getSecondaryColumn()  {
        Cursor c = mDB.rawQuery("select * from tb_login order by _id desc limit 1, 1", null);

        return c;
    }

    public Cursor getLastColumn()  {
        Cursor c = mDB.rawQuery("select * from tb_login order by _id desc limit 1", null);

        return c;
    }

    public Cursor getMatchCustId(String cust_id) {
        Cursor c = mDB.rawQuery("Select * from " + LoginDB.createDB._TABLENAME + " where cust_id" + "'" + cust_id + "'", null);
        return c;
    }
}
