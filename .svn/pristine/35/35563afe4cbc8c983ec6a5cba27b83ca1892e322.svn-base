package kr.co.genexon.factFinder.Sqlite;


import android.provider.BaseColumns;

/**
 * Created by topgu on 2018-04-16.
 */

public class LoginDB {

    public static final class createDB implements BaseColumns {
        public static final String MB_ID = "mb_id";
        public static final String CUST_ID = "cust_id";
        public static final String PASSWD = "password";
        public static final String _TABLENAME = "tb_login";
        public static final String _CREATE =
                "create table " + _TABLENAME + "("
                        + _ID + " integer primary key autoincrement, "
                        + MB_ID + " text not null, "
                        + CUST_ID + " text not null, "
                        + PASSWD + " text not null);";
    }
}

