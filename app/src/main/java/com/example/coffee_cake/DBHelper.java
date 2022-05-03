package com.example.coffee_cake;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static String DBNAME = "Coffeedata.db";
    private static int DBVERSION = 1;
    public static String MONTHLY = "SELECT SELECT strftime('%m', NGHD) AS THANG, SUM(TRIGIA) FROM HOADON ";

    // lay du lieu thong ke theo thang
    public String getMonthlyStatistic(int year)
    {
        return "SELECT strftime('%m', NGHD) AS THANG, SUM(TRIGIA) " +
                "FROM HOADON WHERE NGHD LIKE '" + year + "-%' "+
                "GROUP BY strftime('%m', NGHD) "+
                "ORDER BY strftime('%m', NGHD) ASC";
    }

    public String getSeasonlyStatistic(int year)
    {
        return "";
    }

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
