package com.allinone.smartocity.customer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.allinone.smartocity.customer.utility.UtilsConstants;


import java.util.*;

/**
 * Created by admin on 7/18/2016.
 */
public class OpenHelper {

    Context context;
    DatabaseHandler database;
    String TAG = "OpenHelper";

    public OpenHelper(Context context) {
        this.context = context;
        database = new DatabaseHandler(context);
    }

    public void addProduct(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_PR_COMPID, hashMap.get(UtilsConstants.KEY_PR_COMPID));
        values.put(UtilsConstants.KEY_PR_USERNAME, hashMap.get(UtilsConstants.KEY_PR_USERNAME));
        values.put(UtilsConstants.KEY_PR_PASS, hashMap.get(UtilsConstants.KEY_PR_PASS));
        values.put(UtilsConstants.KEY_PR_ID, hashMap.get(UtilsConstants.KEY_PR_ID));
        values.put(UtilsConstants.KEY_PR_NAME, hashMap.get(UtilsConstants.KEY_PR_NAME));
        values.put(UtilsConstants.KEY_PR_MRP, hashMap.get(UtilsConstants.KEY_PR_MRP));
        values.put(UtilsConstants.KEY_PR_CAT, hashMap.get(UtilsConstants.KEY_PR_CAT));
        values.put(UtilsConstants.KEY_PR_SUB_CAT, hashMap.get(UtilsConstants.KEY_PR_SUB_CAT));
        values.put(UtilsConstants.KEY_PR_BRAND, hashMap.get(UtilsConstants.KEY_PR_BRAND));
        values.put(UtilsConstants.KEY_PR_PACK, hashMap.get(UtilsConstants.KEY_PR_PACK));
        values.put(UtilsConstants.KEY_PR_DISC, hashMap.get(UtilsConstants.KEY_PR_DISC));
        values.put(UtilsConstants.KEY_PR_IMGURL, hashMap.get(UtilsConstants.KEY_PR_IMGURL));
        values.put(UtilsConstants.KEY_PR_FAV, hashMap.get(UtilsConstants.KEY_PR_FAV));
        values.put(UtilsConstants.KEY_PR_SYNCSTATUS, hashMap.get(UtilsConstants.KEY_PR_SYNCSTATUS));

        String condition = UtilsConstants.KEY_PR_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_PR_ID)}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_PRODUCTS, values, UtilsConstants.KEY_PR_ID + " = ?", new String[]{hashMap.get(UtilsConstants.KEY_PR_ID)});
            Log.d("update product", hashMap.get(UtilsConstants.KEY_PR_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_PRODUCTS, null, values);
            Log.d(TAG, "product insert : " + status);
        }
        db.close();
    }

   /* public void addLocation(Location location) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_LOC_COMPID, SharedPreferencesUtil.getInstance(context).getData("compay_id"));
        values.put(UtilsConstants.KEY_LOC_USERNAME, SharedPreferencesUtil.getInstance(context).getData("email"));
        values.put(UtilsConstants.KEY_LOC_PASS, SharedPreferencesUtil.getInstance(context).getData("password"));
        values.put(UtilsConstants.KEY_LOC_LAT, location.getLatitude());
        values.put(UtilsConstants.KEY_LOC_LONG, location.getLongitude());
        values.put(UtilsConstants.KEY_LOC_ACU, location.getAccuracy());
        values.put(UtilsConstants.KEY_LOC_ADDRESS, "");
        values.put(UtilsConstants.KEY_LOC_SYNCSTATUS, "0");
        values.put(UtilsConstants.KEY_CRATED_AT, Generic.nowDate());
        db.insert(UtilsConstants.TABLE_LOCATION, null, values);
        Log.d(TAG, "addLocation: " + location.getLongitude() + " : " + location.getLatitude());
        db.close();
    }

    public void addAttendance(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ATTEND_COMPID, hashMap.get(UtilsConstants.KEY_ATTEND_COMPID));
        values.put(UtilsConstants.KEY_ATTEND_USERNAME, hashMap.get(UtilsConstants.KEY_ATTEND_USERNAME));
        values.put(UtilsConstants.KEY_ATTEND_PASS, hashMap.get(UtilsConstants.KEY_ATTEND_PASS));
        values.put(UtilsConstants.KEY_ATTEND_LAT, hashMap.get(UtilsConstants.KEY_ATTEND_LAT));
        values.put(UtilsConstants.KEY_ATTEND_LONG, hashMap.get(UtilsConstants.KEY_ATTEND_LONG));
        values.put(UtilsConstants.KEY_ATTEND_ACU, hashMap.get(UtilsConstants.KEY_ATTEND_ACU));
        values.put(UtilsConstants.KEY_ATTEND_ADDRESS, hashMap.get(UtilsConstants.KEY_ATTEND_ADDRESS));
        values.put(UtilsConstants.KEY_ATTEND_TYPE, hashMap.get(UtilsConstants.KEY_ATTEND_TYPE));
        values.put(UtilsConstants.KEY_ATTEND_SYNCSTATUS, hashMap.get(UtilsConstants.KEY_ATTEND_SYNCSTATUS));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        String formattedDate = "";
        try {
            date = originalFormat.parse(hashMap.get(UtilsConstants.KEY_CRATED_AT));
            if (date != null) {
                formattedDate = targetFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String query = "select * from  " + UtilsConstants.TABLE_ATTENDANCE + " where STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_CRATED_AT + ") = '" + formattedDate + "' and " + UtilsConstants.KEY_ATTEND_TYPE + " = '" + hashMap.get(UtilsConstants.KEY_ATTEND_TYPE) + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_ATTENDANCE, values, UtilsConstants.KEY_ID + " = ?", new String[]{cursor.getString(0)});
            Log.d("update", hashMap.get(UtilsConstants.KEY_CRATED_AT));
        } else {
            long status = db.insert(UtilsConstants.TABLE_ATTENDANCE, null, values);
            Log.d(TAG, "addAttendance: " + status);
        }
        cursor.close();
        db.close();
    }

    public void addLeave(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_LEAVE_COMPID, hashMap.get(UtilsConstants.KEY_LEAVE_COMPID));
        values.put(UtilsConstants.KEY_LEAVE_USERNAME, hashMap.get(UtilsConstants.KEY_LEAVE_USERNAME));
        values.put(UtilsConstants.KEY_LEAVE_PASS, hashMap.get(UtilsConstants.KEY_LEAVE_PASS));
        values.put(UtilsConstants.KEY_LEAVE_TITLE, hashMap.get(UtilsConstants.KEY_LEAVE_TITLE));
        values.put(UtilsConstants.KEY_LEAVE_FROM, hashMap.get(UtilsConstants.KEY_LEAVE_FROM));
        values.put(UtilsConstants.KEY_LEAVE_TO, hashMap.get(UtilsConstants.KEY_LEAVE_TO));
        values.put(UtilsConstants.KEY_LEAVE_LAT, hashMap.get(UtilsConstants.KEY_LEAVE_LAT));
        values.put(UtilsConstants.KEY_LEAVE_LONG, hashMap.get(UtilsConstants.KEY_LEAVE_LONG));
        values.put(UtilsConstants.KEY_LEAVE_ACU, hashMap.get(UtilsConstants.KEY_LEAVE_ACU));
        values.put(UtilsConstants.KEY_LEAVE_STATUS, hashMap.get(UtilsConstants.KEY_LEAVE_STATUS));
        values.put(UtilsConstants.KEY_LEAVE_SYNCSTATUS, hashMap.get(UtilsConstants.KEY_LEAVE_SYNCSTATUS));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));

        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fdate;
        Date tdate;
        String fromDate = "";
        String toDate = "";
        try {
            fdate = originalFormat.parse(hashMap.get(UtilsConstants.KEY_LEAVE_FROM));
            if (fdate != null) {
                fromDate = targetFormat.format(fdate);
            }
            tdate = originalFormat.parse(hashMap.get(UtilsConstants.KEY_LEAVE_TO));
            if (tdate != null) {
                toDate = targetFormat.format(tdate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String query = "select * from  " + UtilsConstants.TABLE_LEAVE + " where STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_LEAVE_FROM + ") = '" + fromDate + "' and STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_LEAVE_TO + ") = '" + toDate + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_LEAVE, values, UtilsConstants.KEY_ID + " = ?", new String[]{cursor.getString(0)});
            Log.d("update Leave", hashMap.get(UtilsConstants.KEY_CRATED_AT));
        } else {
            long status = db.insert(UtilsConstants.TABLE_LEAVE, null, values);
            Log.d(TAG, "addLeave: " + status);
        }

        db.close();
    }

    public String getProduct(String id) {
        String price = "0";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(UtilsConstants.TABLE_PRODUCTS, new String[]{UtilsConstants.KEY_PR_MRP}, UtilsConstants.KEY_PR_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor.moveToFirst()) {
            price = cursor.getString(0);
        }
        db.close();
        return price;
    }

    public HashMap<String, String> getCustomerByID(String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(UtilsConstants.TABLE_CUSTOMERS, null
                , UtilsConstants.KEY_CUS_ID + "=?", new String[]{id}, null, null, null, null);
        if (cursor.moveToFirst()) {
            hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            hashMap.put(UtilsConstants.KEY_CUS_DISTRIBUTOR, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_DISTRIBUTOR)));
            hashMap.put(UtilsConstants.KEY_CUS_ROUTE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ROUTE)));
            hashMap.put(UtilsConstants.KEY_CUS_STATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_STATE)));
            hashMap.put(UtilsConstants.KEY_CUS_CITY, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_CITY)));
            hashMap.put(UtilsConstants.KEY_CUS_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_IMGURL)));
            hashMap.put(UtilsConstants.KEY_CUS_MOBILE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_MOBILE)));
            hashMap.put(UtilsConstants.KEY_CUS_EMAIL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_EMAIL)));
            hashMap.put(UtilsConstants.KEY_CUS_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ADDRESS)));
            hashMap.put(UtilsConstants.KEY_CUS_BILL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_BILL)));
            hashMap.put(UtilsConstants.KEY_CUS_OUTSTANDING, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUTSTANDING)));
            hashMap.put(UtilsConstants.KEY_CUS_OUT_RECORDS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUT_RECORDS)));
            hashMap.put(UtilsConstants.KEY_CUS_OUT_UPDATED, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUT_UPDATED)));
            hashMap.put(UtilsConstants.KEY_CUS_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LAT)));
            hashMap.put(UtilsConstants.KEY_CUS_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LONG)));
            hashMap.put(UtilsConstants.KEY_CUS_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_SYNCSTATUS)));
            Log.d("TAG", "getCustomer: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
        }
        db.close();
        return hashMap;
    }


    public HashMap<String, String> getAttendanceDay(String dateString, String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_ATTENDANCE + " where STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_CRATED_AT + ") = '" + dateString + "' and " + UtilsConstants.KEY_ATTEND_TYPE + " = '" + type + "' and " + UtilsConstants.KEY_ATTEND_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                hashMap.put(UtilsConstants.KEY_ATTEND_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_COMPID)));
                hashMap.put(UtilsConstants.KEY_ATTEND_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_USERNAME)));
                hashMap.put(UtilsConstants.KEY_ATTEND_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_PASS)));
                hashMap.put(UtilsConstants.KEY_ATTEND_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_LAT)));
                hashMap.put(UtilsConstants.KEY_ATTEND_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_LONG)));
                hashMap.put(UtilsConstants.KEY_ATTEND_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_ATTEND_TYPE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_TYPE)));
                hashMap.put(UtilsConstants.KEY_ATTEND_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_SYNCSTATUS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                Log.d(TAG, "getAttendance: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7) + " " + cursor.getString(8) + " " + cursor.getString(9));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap;
    }

    public HashMap<String, HashMap<String, String>> getAllAttendanceMonth(String monthString, String type) {
        HashMap<String, HashMap<String, String>> hashMap1 = new HashMap<>();

        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_ATTENDANCE + " where STRFTIME('%Y-%m'," + UtilsConstants.KEY_CRATED_AT + ") = '" + monthString + "' and " + UtilsConstants.KEY_ATTEND_TYPE + " = '" + type + "' and " + UtilsConstants.KEY_ATTEND_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ATTEND_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_COMPID)));
                hashMap.put(UtilsConstants.KEY_ATTEND_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_USERNAME)));
                hashMap.put(UtilsConstants.KEY_ATTEND_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_PASS)));
                hashMap.put(UtilsConstants.KEY_ATTEND_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_LAT)));
                hashMap.put(UtilsConstants.KEY_ATTEND_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_LONG)));
                hashMap.put(UtilsConstants.KEY_ATTEND_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_ATTEND_TYPE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_TYPE)));
                hashMap.put(UtilsConstants.KEY_ATTEND_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_SYNCSTATUS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date;
                String formattedDate = "";
                try {
                    date = originalFormat.parse(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                    if (date != null) {
                        formattedDate = targetFormat.format(date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                hashMap1.put(formattedDate, hashMap);
                Log.d(TAG, "getAttendance: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7) + " " + cursor.getString(8) + " " + cursor.getString(9));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap1;
    }

    public LinkedHashMap<String, HashMap<String, String>> getAllProduct(String categoryName) {
        LinkedHashMap<String, HashMap<String, String>> hashMap1 = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor;
        String sort = "CAST (" + UtilsConstants.KEY_PR_MRP + " AS INTEGER) ASC";
        if (categoryName.isEmpty()) {
            cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, UtilsConstants.KEY_PR_USERNAME + "=? ", new String[]{SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, sort);
        } else {
            cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, UtilsConstants.KEY_PR_CAT + "=? and " + UtilsConstants.KEY_PR_USERNAME + "=? ", new String[]{categoryName, SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, sort);
        }
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_PR_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_COMPID)));
                hashMap.put(UtilsConstants.KEY_PR_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_USERNAME)));
                hashMap.put(UtilsConstants.KEY_PR_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_PASS)));
                hashMap.put(UtilsConstants.KEY_PR_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)));
                hashMap.put(UtilsConstants.KEY_PR_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_NAME)));
                hashMap.put(UtilsConstants.KEY_PR_MRP, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP)));
                hashMap.put(UtilsConstants.KEY_PR_CAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_CAT)));
                hashMap.put(UtilsConstants.KEY_PR_SUB_CAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_SUB_CAT)));
                hashMap.put(UtilsConstants.KEY_PR_BRAND, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_BRAND)));
                hashMap.put(UtilsConstants.KEY_PR_PACK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_PACK)));
                hashMap.put(UtilsConstants.KEY_PR_DISC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_DISC)));
                hashMap.put(UtilsConstants.KEY_PR_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_IMGURL)));
                hashMap.put(UtilsConstants.KEY_PR_FAV, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_FAV)));
                hashMap.put(UtilsConstants.KEY_PR_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_SYNCSTATUS)));
                hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), hashMap);
                Log.d("TAG", "getAllProduct: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap1;
    }

    public HashMap<String, HashMap<String, String>> getAllDistributorsList() {
        HashMap<String, HashMap<String, String>> hashMap1 = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String condition = UtilsConstants.KEY_DIST_USERNAME + " =?";
        Cursor cursor = db.query(
                UtilsConstants.TABLE_DISTRIBUTOR,  // The table to query
                null,                               // The columns to return
                condition,                                // The columns for the WHERE clause
                new String[]{SharedPreferencesUtil.getInstance(context).getData("email")},                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_DIST_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_DIST_ID)));
                hashMap.put(UtilsConstants.KEY_DIST_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_DIST_NAME)));
                hashMap.put(UtilsConstants.KEY_DIST_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_DIST_SYNCSTATUS)));
                hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_DIST_ID)), hashMap);
                Log.d("TAG", "getAllProduct: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap1;
    }

    public LinkedHashMap<String, HashMap<String, String>> getFavoriteProducts() {
        LinkedHashMap<String, HashMap<String, String>> hashMap1 = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String sort = "CAST (" + UtilsConstants.KEY_PR_MRP + " AS INTEGER) ASC";
        Cursor cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, UtilsConstants.KEY_PR_FAV + "=? and " + UtilsConstants.KEY_PR_USERNAME + "=? ", new String[]{"1", SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, sort);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_PR_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_COMPID)));
                hashMap.put(UtilsConstants.KEY_PR_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_USERNAME)));
                hashMap.put(UtilsConstants.KEY_PR_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_PASS)));
                hashMap.put(UtilsConstants.KEY_PR_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)));
                hashMap.put(UtilsConstants.KEY_PR_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_NAME)));
                hashMap.put(UtilsConstants.KEY_PR_MRP, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP)));
                hashMap.put(UtilsConstants.KEY_PR_CAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_CAT)));
                hashMap.put(UtilsConstants.KEY_PR_SUB_CAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_SUB_CAT)));
                hashMap.put(UtilsConstants.KEY_PR_BRAND, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_BRAND)));
                hashMap.put(UtilsConstants.KEY_PR_PACK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_PACK)));
                hashMap.put(UtilsConstants.KEY_PR_DISC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_DISC)));
                hashMap.put(UtilsConstants.KEY_PR_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_IMGURL)));
                hashMap.put(UtilsConstants.KEY_PR_FAV, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_FAV)));
                hashMap.put(UtilsConstants.KEY_PR_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_SYNCSTATUS)));
                hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), hashMap);
                Log.d("TAG", "getAllProduct: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap1;
    }

    public void updateProductSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_PR_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_PR_SYNCSTATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, condition, new String[]{"2"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_PRODUCTS, values, UtilsConstants.KEY_PR_SYNCSTATUS + "=?", new String[]{"2"});
            Log.d("ProductSyncStatus", "updateProductSyncStatus");
        }
        db.close();
    }


    public void deleteProductSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_PR_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_PR_SYNCSTATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, condition, new String[]{"3"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.delete(UtilsConstants.TABLE_PRODUCTS, UtilsConstants.KEY_PR_SYNCSTATUS + "=?", new String[]{"3"});
            Log.d("ProductSyncStatus", "deleteProductSyncStatus");
        }
        db.close();
    }


    public void updateAttendanceSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ATTEND_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_ATTEND_SYNCSTATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_ATTENDANCE, null, condition, new String[]{"2"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_ATTENDANCE, values, UtilsConstants.KEY_ATTEND_SYNCSTATUS + "=?", new String[]{"2"});
            Log.d("AttendanceSyncStatus", "updateAttendanceSyncStatus");
        }
        cursor.close();
        db.close();
    }


    public void deleteAttendanceSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ATTEND_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_ATTEND_SYNCSTATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_ATTENDANCE, null, condition, new String[]{"3"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.delete(UtilsConstants.TABLE_ATTENDANCE, UtilsConstants.KEY_ATTEND_SYNCSTATUS + "=?", new String[]{"3"});
            Log.d("AttendanceSyncStatus", "deleteAttendanceSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public void addProductToFavourite(String id) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_PR_FAV, "1");
        db.update(UtilsConstants.TABLE_PRODUCTS, values, UtilsConstants.KEY_PR_ID + " = ?", new String[]{id});
        db.close();
    }

    public HashMap<String, String> checkDateFromTo(String date) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_LEAVE + " where '" + date + "' between STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_LEAVE_FROM + ") and STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_LEAVE_TO + ") and " + UtilsConstants.KEY_LEAVE_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.KEY_LEAVE_STATUS + " != '3'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                hashMap.put(UtilsConstants.KEY_LEAVE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_COMPID)));
                hashMap.put(UtilsConstants.KEY_LEAVE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_USERNAME)));
                hashMap.put(UtilsConstants.KEY_LEAVE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_PASS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TITLE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TITLE)));
                hashMap.put(UtilsConstants.KEY_LEAVE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_FROM)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TO)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LAT, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LAT)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LONG, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LONG)));
                hashMap.put(UtilsConstants.KEY_LEAVE_STATUS, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_STATUS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_SYNCSTATUS, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_SYNCSTATUS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                Log.d("TAG", "checkDateFrom: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5) + " " + cursor.getString(6));
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap;
    }


    public HashMap<String, String> checkDateFromBetween(String from, String to) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_LEAVE + " where STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_LEAVE_FROM + ") between '" + from + "' and '" + to + "'  and " + UtilsConstants.KEY_LEAVE_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.KEY_LEAVE_STATUS + " != '3'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                hashMap.put(UtilsConstants.KEY_LEAVE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_COMPID)));
                hashMap.put(UtilsConstants.KEY_LEAVE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_USERNAME)));
                hashMap.put(UtilsConstants.KEY_LEAVE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_PASS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TITLE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TITLE)));
                hashMap.put(UtilsConstants.KEY_LEAVE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_FROM)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TO)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LAT, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LAT)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LONG, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LONG)));
                hashMap.put(UtilsConstants.KEY_LEAVE_STATUS, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_STATUS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_SYNCSTATUS, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_SYNCSTATUS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                Log.d("TAG", "checkDateFromBetween: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5) + " " + cursor.getString(6));
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap;
    }

    public HashMap<String, String> checkDateToBetween(String from, String to) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_LEAVE + " where STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_LEAVE_TO + ") between '" + from + "' and '" + to + "' and " + UtilsConstants.KEY_LEAVE_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.KEY_LEAVE_STATUS + " != '3'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                hashMap.put(UtilsConstants.KEY_LEAVE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_COMPID)));
                hashMap.put(UtilsConstants.KEY_LEAVE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_USERNAME)));
                hashMap.put(UtilsConstants.KEY_LEAVE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_PASS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TITLE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TITLE)));
                hashMap.put(UtilsConstants.KEY_LEAVE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_FROM)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TO)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LAT, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LAT)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LONG, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LONG)));
                hashMap.put(UtilsConstants.KEY_LEAVE_STATUS, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_STATUS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_SYNCSTATUS, hashMap.get(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_SYNCSTATUS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                Log.d("TAG", "checkDateToBetween: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5) + " " + cursor.getString(6));
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap;
    }

    public LinkedHashMap<String, HashMap<String, String>> checkAllLeaves() {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_LEAVE + " where " + UtilsConstants.KEY_LEAVE_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_LEAVE_FROM + ") DESC";
        Log.d(TAG, "checkAllLeaves: " + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap;
        if (cursor.moveToFirst()) {
            do {
                hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_LEAVE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_COMPID)));
                hashMap.put(UtilsConstants.KEY_LEAVE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_USERNAME)));
                hashMap.put(UtilsConstants.KEY_LEAVE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_PASS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TITLE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TITLE)));
                hashMap.put(UtilsConstants.KEY_LEAVE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_FROM)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TO)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LAT)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LONG)));
                hashMap.put(UtilsConstants.KEY_LEAVE_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_STATUS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_SYNCSTATUS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                listItems.put(cursor.getString(0), hashMap);
                Log.d("TAG", "checkAllLeaves: " + hashMap);
            } while (cursor.moveToNext());
        }
        db.close();
        return listItems;
    }

    public HashMap<String, HashMap<String, String>> getAllAttendanceDataToUpload() {
        HashMap<String, HashMap<String, String>> hashMap1 = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_ATTENDANCE + " where " + UtilsConstants.KEY_ATTEND_SYNCSTATUS + " = '0'";
        Log.d(TAG, "getAttendance: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_ATTEND_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_COMPID)));
                hashMap.put(UtilsConstants.KEY_ATTEND_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_USERNAME)));
                hashMap.put(UtilsConstants.KEY_ATTEND_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_PASS)));
                hashMap.put(UtilsConstants.KEY_ATTEND_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_LAT)));
                hashMap.put(UtilsConstants.KEY_ATTEND_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_LONG)));
                hashMap.put(UtilsConstants.KEY_ATTEND_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_ACU)));
                hashMap.put(UtilsConstants.KEY_ATTEND_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_ATTEND_TYPE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_TYPE)));
                hashMap.put(UtilsConstants.KEY_ATTEND_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ATTEND_SYNCSTATUS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), hashMap);
                Log.d(TAG, "getAttendance All : " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7) + " " + cursor.getString(8) + " " + cursor.getString(9));
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap1;
    }


    public HashMap<String, HashMap<String, String>> getAllLeaveDataToUpload() {
        HashMap<String, HashMap<String, String>> hashMap1 = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_LEAVE + " where " + UtilsConstants.KEY_LEAVE_SYNCSTATUS + " = '0'";
        Log.d(TAG, "getAllLeaveDataToUpload : " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_LEAVE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_COMPID)));
                hashMap.put(UtilsConstants.KEY_LEAVE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_USERNAME)));
                hashMap.put(UtilsConstants.KEY_LEAVE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_PASS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TITLE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TITLE)));
                hashMap.put(UtilsConstants.KEY_LEAVE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_FROM)));
                hashMap.put(UtilsConstants.KEY_LEAVE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_TO)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LAT)));
                hashMap.put(UtilsConstants.KEY_LEAVE_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_ACU)));
                hashMap.put(UtilsConstants.KEY_LEAVE_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_LONG)));
                hashMap.put(UtilsConstants.KEY_LEAVE_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_STATUS)));
                hashMap.put(UtilsConstants.KEY_LEAVE_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LEAVE_SYNCSTATUS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), hashMap);
                Log.d(TAG, "getLeave All : " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7) + " " + cursor.getString(8) + " " + cursor.getString(9) + " " + cursor.getString(10) + " " + cursor.getString(11));
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap1;
    }

    public void updateDistributorSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_DIST_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_DIST_SYNCSTATUS + " =? AND " + UtilsConstants.KEY_DIST_USERNAME + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_DISTRIBUTOR, null, condition, new String[]{"2", SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_DISTRIBUTOR, values, UtilsConstants.KEY_DIST_SYNCSTATUS + "=?", new String[]{"2"});
            Log.d("DistributorSyncStatus", "updateDistributorSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public void addDistributor(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_DIST_COMPID, hashMap.get(UtilsConstants.KEY_DIST_COMPID));
        values.put(UtilsConstants.KEY_DIST_USERNAME, hashMap.get(UtilsConstants.KEY_DIST_USERNAME));
        values.put(UtilsConstants.KEY_DIST_PASS, hashMap.get(UtilsConstants.KEY_DIST_PASS));
        values.put(UtilsConstants.KEY_DIST_ID, hashMap.get(UtilsConstants.KEY_DIST_ID));
        values.put(UtilsConstants.KEY_DIST_NAME, hashMap.get(UtilsConstants.KEY_DIST_NAME));
        values.put(UtilsConstants.KEY_DIST_SYNCSTATUS, hashMap.get(UtilsConstants.KEY_DIST_SYNCSTATUS));

        String condition = UtilsConstants.KEY_DIST_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_DISTRIBUTOR, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_DIST_ID)}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_DISTRIBUTOR, values, UtilsConstants.KEY_DIST_ID + " = ?", new String[]{hashMap.get(UtilsConstants.KEY_DIST_ID)});
            Log.d("update Distributor", hashMap.get(UtilsConstants.KEY_DIST_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_DISTRIBUTOR, null, values);
            Log.d(TAG, "Distributor insert : " + status);
        }
        cursor.close();
        db.close();
    }

    public void deleteDistributorSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_DIST_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_DIST_SYNCSTATUS + " =? AND " + UtilsConstants.KEY_DIST_USERNAME + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_DISTRIBUTOR, null, condition, new String[]{"3", SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, null);
        if (cursor.moveToFirst()) {
            db.delete(UtilsConstants.TABLE_DISTRIBUTOR, UtilsConstants.KEY_DIST_SYNCSTATUS + "=?", new String[]{"3"});
            Log.d("DistributorSyncStatus", "deleteDistributorSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public void updateRouteSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ROUTE_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_ROUTE_SYNCSTATUS + " =? AND " + UtilsConstants.KEY_ROUTE_USERNAME + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_ROUTE, null, condition, new String[]{"2", SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_ROUTE, values, UtilsConstants.KEY_ROUTE_SYNCSTATUS + "=?", new String[]{"2"});
            Log.d("RouteSyncStatus", "updateRouteSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public void addRoute(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ROUTE_COMPID, hashMap.get(UtilsConstants.KEY_ROUTE_COMPID));
        values.put(UtilsConstants.KEY_ROUTE_USERNAME, hashMap.get(UtilsConstants.KEY_ROUTE_USERNAME));
        values.put(UtilsConstants.KEY_ROUTE_PASS, hashMap.get(UtilsConstants.KEY_ROUTE_PASS));
        values.put(UtilsConstants.KEY_ROUTE_ID, hashMap.get(UtilsConstants.KEY_ROUTE_ID));
        values.put(UtilsConstants.KEY_ROUTE_NAME, hashMap.get(UtilsConstants.KEY_ROUTE_NAME));
        values.put(UtilsConstants.KEY_ROUTE_DISTID, hashMap.get(UtilsConstants.KEY_ROUTE_DISTID));
        values.put(UtilsConstants.KEY_ROUTE_DISTNAME, hashMap.get(UtilsConstants.KEY_ROUTE_DISTNAME));
        values.put(UtilsConstants.KEY_ROUTE_SELECTION, hashMap.get(UtilsConstants.KEY_ROUTE_SELECTION));
        values.put(UtilsConstants.KEY_ROUTE_SYNCSTATUS, hashMap.get(UtilsConstants.KEY_ROUTE_SYNCSTATUS));

        String condition = UtilsConstants.KEY_ROUTE_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_ROUTE, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_ROUTE_ID)}, null, null, null);
        if (cursor.moveToFirst()) {
            if (cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ROUTE_SELECTION)).equals("1")) {
                values.put(UtilsConstants.KEY_ROUTE_SELECTION, "1");
                db.update(UtilsConstants.TABLE_ROUTE, values, UtilsConstants.KEY_ID + " = ?", new String[]{cursor.getString(0)});
                Log.d("update Route", hashMap.get(UtilsConstants.KEY_ROUTE_NAME));
            } else {
                db.update(UtilsConstants.TABLE_ROUTE, values, UtilsConstants.KEY_ID + " = ?", new String[]{cursor.getString(0)});
                Log.d("update Route", hashMap.get(UtilsConstants.KEY_ROUTE_ID));
            }
        } else {
            long status = db.insert(UtilsConstants.TABLE_ROUTE, null, values);
            Log.d(TAG, "Route insert : " + status);
        }
        cursor.close();
        db.close();
    }

    public void deleteRouteSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ROUTE_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_ROUTE_SYNCSTATUS + " =? AND " + UtilsConstants.KEY_ROUTE_USERNAME + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_ROUTE, null, condition, new String[]{"3", SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, null);
        if (cursor.moveToFirst()) {
            db.delete(UtilsConstants.TABLE_ROUTE, UtilsConstants.KEY_ROUTE_SYNCSTATUS + "=?", new String[]{"3"});
            Log.d("RouteSyncStatus", "deleteRouteSyncStatus");
        }
        cursor.close();
        db.close();
    }


    public void updateCustomerSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CUS_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_CUS_SYNCSTATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CUSTOMERS, null, condition, new String[]{"2"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CUSTOMERS, values, UtilsConstants.KEY_CUS_SYNCSTATUS + "=?", new String[]{"2"});
            Log.d("CustomerSyncStatus", "updateCustomerSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public void addCustomer(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CUS_ID, hashMap.get(UtilsConstants.KEY_CUS_ID));
        values.put(UtilsConstants.KEY_CUS_NAME, hashMap.get(UtilsConstants.KEY_CUS_NAME));
        values.put(UtilsConstants.KEY_CUS_MOBILE, hashMap.get(UtilsConstants.KEY_CUS_MOBILE));
        values.put(UtilsConstants.KEY_CUS_CITY, hashMap.get(UtilsConstants.KEY_CUS_CITY));
        values.put(UtilsConstants.KEY_CUS_STATE, hashMap.get(UtilsConstants.KEY_CUS_STATE));
        values.put(UtilsConstants.KEY_CUS_DISTRIBUTOR, hashMap.get(UtilsConstants.KEY_CUS_DISTRIBUTOR));
        values.put(UtilsConstants.KEY_CUS_IMGURL, hashMap.get(UtilsConstants.KEY_CUS_IMGURL));
        values.put(UtilsConstants.KEY_CUS_ROUTE, hashMap.get(UtilsConstants.KEY_CUS_ROUTE));
        values.put(UtilsConstants.KEY_CUS_ADDRESS, hashMap.get(UtilsConstants.KEY_CUS_ADDRESS));
        values.put(UtilsConstants.KEY_CUS_BILL, hashMap.get(UtilsConstants.KEY_CUS_BILL));
        values.put(UtilsConstants.KEY_CUS_OUTSTANDING, hashMap.get(UtilsConstants.KEY_CUS_OUTSTANDING));
        values.put(UtilsConstants.KEY_CUS_OUT_RECORDS, hashMap.get(UtilsConstants.KEY_CUS_OUT_RECORDS));
        values.put(UtilsConstants.KEY_CUS_OUT_UPDATED, hashMap.get(UtilsConstants.KEY_CUS_OUT_UPDATED));
        values.put(UtilsConstants.KEY_CUS_EMAIL, hashMap.get(UtilsConstants.KEY_CUS_EMAIL));
        values.put(UtilsConstants.KEY_CUS_LAT, hashMap.get(UtilsConstants.KEY_CUS_LAT));
        values.put(UtilsConstants.KEY_CUS_LONG, hashMap.get(UtilsConstants.KEY_CUS_LONG));
        values.put(UtilsConstants.KEY_CUS_ACU, hashMap.get(UtilsConstants.KEY_CUS_ACU));
        values.put(UtilsConstants.KEY_CUS_SYNCSTATUS, hashMap.get(UtilsConstants.KEY_CUS_SYNCSTATUS));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));
        String condition = UtilsConstants.KEY_CUS_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CUSTOMERS, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_CUS_ID)}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CUSTOMERS, values, UtilsConstants.KEY_CUS_ID + " = ?", new String[]{hashMap.get(UtilsConstants.KEY_CUS_ID)});
            Log.d("update Customer", hashMap.get(UtilsConstants.KEY_CUS_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_CUSTOMERS, null, values);
            Log.d(TAG, "Customer insert : " + status);
        }
        db.close();
    }

    public void deleteCustomerSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CUS_SYNCSTATUS, "3");
        String condition = UtilsConstants.KEY_CUS_SYNCSTATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CUSTOMERS, null, condition, new String[]{"3"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.delete(UtilsConstants.TABLE_CUSTOMERS, UtilsConstants.KEY_CUS_SYNCSTATUS + "=?", new String[]{"3"});
            Log.d("CustomerSyncStatus", "deleteCustomerSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public void updateSyncStatusLeave(String id) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_LEAVE_SYNCSTATUS, "2");
        String condition = UtilsConstants.KEY_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_LEAVE, null, condition, new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_LEAVE, values, UtilsConstants.KEY_ID + "=?", new String[]{id});
            Log.d("LeaveSyncStatus", "updateSyncStatusLeave");
        }
        db.close();
    }


    public LinkedHashMap<String, HashMap<String, String>> getCustomersByRouteName(ArrayList<String> routes) {
        LinkedHashMap<String, HashMap<String, String>> hashMap1 = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        for (int i = 0; i < routes.size(); i++) {
            String query = "select * from  " + UtilsConstants.TABLE_CUSTOMERS + " where " + UtilsConstants.KEY_CUS_ROUTE + " = '" + routes.get(i) + "'";
            Log.d(TAG, "getCustomersByRouteName : " + query);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                    hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                    hashMap.put(UtilsConstants.KEY_CUS_DISTRIBUTOR, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_DISTRIBUTOR)));
                    hashMap.put(UtilsConstants.KEY_CUS_ROUTE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ROUTE)));
                    hashMap.put(UtilsConstants.KEY_CUS_STATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_STATE)));
                    hashMap.put(UtilsConstants.KEY_CUS_CITY, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_CITY)));
                    hashMap.put(UtilsConstants.KEY_CUS_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_IMGURL)));
                    hashMap.put(UtilsConstants.KEY_CUS_MOBILE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_MOBILE)));
                    hashMap.put(UtilsConstants.KEY_CUS_EMAIL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_EMAIL)));
                    hashMap.put(UtilsConstants.KEY_CUS_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ADDRESS)));
                    hashMap.put(UtilsConstants.KEY_CUS_BILL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_BILL)));
                    hashMap.put(UtilsConstants.KEY_CUS_OUTSTANDING, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUTSTANDING)));
                    hashMap.put(UtilsConstants.KEY_CUS_OUT_RECORDS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUT_RECORDS)));
                    hashMap.put(UtilsConstants.KEY_CUS_OUT_UPDATED, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUT_UPDATED)));
                    hashMap.put(UtilsConstants.KEY_CUS_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LAT)));
                    hashMap.put(UtilsConstants.KEY_CUS_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LONG)));
                    hashMap.put(UtilsConstants.KEY_CUS_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_SYNCSTATUS)));
                    hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)), hashMap);
                    Log.d(TAG, "getCustomersByDistributorsList: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7) + " " + cursor.getString(8) + " " + cursor.getString(9));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return hashMap1;
    }

    public HashMap<String, HashMap<String, String>> getRoutesByDistributorsId(String s) {
        HashMap<String, HashMap<String, String>> hashMap1 = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_ROUTE + " where " + UtilsConstants.KEY_ROUTE_DISTID + " = '" + s + "'";
        Log.d(TAG, "getRoutesByDistributorsId: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ROUTE_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ROUTE_ID)));
                hashMap.put(UtilsConstants.KEY_ROUTE_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ROUTE_NAME)));
                hashMap.put(UtilsConstants.KEY_ROUTE_DISTID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ROUTE_DISTID)));
                hashMap.put(UtilsConstants.KEY_ROUTE_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ROUTE_SYNCSTATUS)));
                hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ROUTE_NAME)), hashMap);
                Log.d(TAG, "getRoutesByDistributorsId: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap1;
    }

    public void updateSelectedRoutes(ArrayList<String> selected) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ROUTE_SELECTION, "1");
        for (int i = 0; i < selected.size(); i++) {
            String condition = UtilsConstants.KEY_ROUTE_ID + " =?";
            Cursor cursor = db.query(UtilsConstants.TABLE_ROUTE, null, condition, new String[]{selected.get(i)}, null, null, null);
            if (cursor.moveToFirst()) {
                db.update(UtilsConstants.TABLE_ROUTE, values, UtilsConstants.KEY_ID + "=?", new String[]{cursor.getString(0)});
                Log.d("updateSelectedRoutes", "updateSelectedRoutes " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4));
            }
            cursor.close();
        }
        db.close();
    }

    public void updateRoutesUnchecked() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ROUTE_SELECTION, "0");
        db.update(UtilsConstants.TABLE_ROUTE, values, null, null);
        Log.d("updateRoutesUnchecked", "updateRoutesUnchecked");
        db.close();
    }

    public ArrayList<String> getCheckedRoutes() {
        ArrayList<String> selected = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_ROUTE + " where " + UtilsConstants.KEY_ROUTE_SELECTION + " = '1'";
        Log.d(TAG, "getCheckedRoutes: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                selected.add(cursor.getString(1));
                Log.d(TAG, "getCheckedRoutes: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return selected;
    }

    public ArrayList<String> getCheckedRoutesNames() {
        ArrayList<String> selected = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_ROUTE + " where " + UtilsConstants.KEY_ROUTE_SELECTION + " = '1' and " + UtilsConstants.KEY_ROUTE_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Log.d(TAG, "getCheckedRoutes: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                selected.add(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ROUTE_NAME)));
                Log.d(TAG, "getCheckedRoutes: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return selected;
    }

    public ArrayList<String> getAllCategories() {

        ArrayList<String> catList = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + UtilsConstants.TABLE_PRODUCTS
                + " group by " + UtilsConstants.KEY_PR_CAT;
        final Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String categoryName = cursor.getString(cursor
                        .getColumnIndex(UtilsConstants.KEY_PR_CAT));
                if (categoryName != null && !categoryName.isEmpty()) {
                    catList.add(categoryName);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return catList;
    }

    public void updateFavFlag(String favflag, String strProductId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_PR_FAV, favflag);
        String condition = UtilsConstants.KEY_PR_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, condition, new String[]{strProductId}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_PRODUCTS, values, UtilsConstants.KEY_PR_ID + "=?", new String[]{strProductId});
            Log.d("Set FavFlag", favflag);
        }
        db.close();
    }

    public boolean getCartCount(String custId, String prdctId) {
        boolean cartFlag = false;
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_CART + " where " + UtilsConstants.KEY_PR_ID + " = '" + prdctId + "' AND " + UtilsConstants.KEY_CUS_ID + " = '" + custId
                + "' AND " + UtilsConstants.KEY_CART_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' AND " + UtilsConstants.KEY_CART_FLAG + "='0'";
        Log.d(TAG, "Cart Count: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cartFlag = true;
        }
        db.close();
        return cartFlag;

    }

    public void insertIntoCart(String custId, String custname, String strProductId, String strMRP) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_PR_ID, strProductId);
        values.put(UtilsConstants.KEY_CUS_ID, custId);
        values.put(UtilsConstants.KEY_CART_QTY, "1");
        values.put(UtilsConstants.KEY_CART_FLAG, "0");
        values.put(UtilsConstants.KEY_CUS_NAME, custname);
        values.put(UtilsConstants.KEY_CART_STATUS, "0");
        values.put(UtilsConstants.KEY_CART_STOCK, "0");
        values.put(UtilsConstants.KEY_PR_MRP, strMRP);
        values.put(UtilsConstants.KEY_CART_COMPID, SharedPreferencesUtil.getInstance(context).getData("compay_id"));
        values.put(UtilsConstants.KEY_CART_USERNAME, SharedPreferencesUtil.getInstance(context).getData("email"));
        values.put(UtilsConstants.KEY_CART_PASS, SharedPreferencesUtil.getInstance(context).getData("password"));
        try {
            db.insert(UtilsConstants.TABLE_CART, null, values);
            Log.d("Cart", "Insert Succssful");
        } catch (Exception e) {
            Log.d("Cart", e + "");
        }
        db.close();
    }

    public void deleteCart(String custId, String strProductId) {

        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + UtilsConstants.TABLE_CART + " WHERE " + UtilsConstants.KEY_PR_ID + "='" + strProductId + "' AND "
                    + UtilsConstants.KEY_CUS_ID + "= '" + custId + "' AND " + UtilsConstants.KEY_CART_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email")
                    + "' AND " + UtilsConstants.KEY_CART_FLAG + "='0'");
        } catch (Exception e) {
        }
        db.close();

    }

    public int getCartCounter() {
        int cartCount = 0;
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select DISTINCT " + UtilsConstants.KEY_CUS_ID + " from  " + UtilsConstants.TABLE_CART + " where "
                + UtilsConstants.KEY_CART_USERNAME + " ='" + SharedPreferencesUtil.getInstance(context).getData("email")
                + "' AND " + UtilsConstants.KEY_CART_FLAG + "='0'";
        Log.d(TAG, "Cart Count: " + query);
        Cursor cursor = db.rawQuery(query, null);
        cartCount = cursor.getCount();
        cursor.close();
        return cartCount;
    }

    public ArrayList<String> getCustomerList() {
        ArrayList customerList = new ArrayList();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select DISTINCT " + UtilsConstants.KEY_CUS_ID + " from  " + UtilsConstants.TABLE_CART + " where " + UtilsConstants.KEY_CART_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email")
                + "' AND " + UtilsConstants.KEY_CART_FLAG + "='0'";
        Log.d(TAG, "Cart Count: " + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String customerName = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID));
            customerList.add(customerName);
        }
        cursor.close();
        return customerList;
    }

    public String getCustomerByIDs(String id) {
        String str = "";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(UtilsConstants.TABLE_CUSTOMERS, null
                , UtilsConstants.KEY_CUS_ID + "=?", new String[]{id}, null, null, null, null);
        if (cursor.moveToFirst()) {
            str = cursor.getString(2);
        }
        db.close();
        return str;
    }

    public ArrayList<HashMap<String, String>> getProductCartInfo(String s) {
        ArrayList<HashMap<String, String>> arr = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "SELECT * FROM " + UtilsConstants.TABLE_CART + " a INNER JOIN " + UtilsConstants.TABLE_PRODUCTS + " b ON a.productId = b.productId where a.customerId='" + s
                + "' and a.username='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and a.OrderFlag='0'";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
            map.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            map.put(UtilsConstants.KEY_PR_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)));
            map.put(UtilsConstants.KEY_PR_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_IMGURL)));
            map.put(UtilsConstants.KEY_PR_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_NAME)));
            map.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            map.put(UtilsConstants.KEY_PR_MRP, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP)));
            map.put(UtilsConstants.KEY_CART_QTY, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY)));
            map.put(UtilsConstants.KEY_CART_STOCK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STOCK)));
            arr.add(map);
        }
        cursor.close();

        return arr;
    }

    public String getCartQty(String customID, String strProductID) {
        String strQty = "1";
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_CART + " where " + UtilsConstants.KEY_PR_ID + " = '" + strProductID + "' AND " + UtilsConstants.KEY_CUS_ID + " = '" + customID + "' AND " + UtilsConstants.KEY_CART_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' AND " + UtilsConstants.KEY_CART_FLAG + "='0'";
        Log.d(TAG, "Cart Count: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            strQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
        }

        return strQty;
    }

    public void updateCartCount(String customID, String customname, String strProductID, String qty, String strMRP) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CART_QTY, qty);
        values.put(UtilsConstants.KEY_CUS_NAME, customname);
        String strUsername = SharedPreferencesUtil.getInstance(context).getData("email");
        String condition = UtilsConstants.KEY_PR_ID + " =? AND " + UtilsConstants.KEY_CUS_ID + " =? AND " + UtilsConstants.KEY_CART_USERNAME + " =? AND " + UtilsConstants.KEY_CART_FLAG + "=?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CART, null, condition, new String[]{strProductID, customID, strUsername, "0"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CART, values, condition, new String[]{strProductID, customID, strUsername, "0"});
            Log.d("Cart Count Update :", qty);
        } else {
            values.put(UtilsConstants.KEY_CART_COMPID, SharedPreferencesUtil.getInstance(context).getData("compay_id"));
            values.put(UtilsConstants.KEY_CART_USERNAME, SharedPreferencesUtil.getInstance(context).getData("email"));
            values.put(UtilsConstants.KEY_CART_PASS, SharedPreferencesUtil.getInstance(context).getData("password"));
            values.put(UtilsConstants.KEY_PR_ID, strProductID);
            values.put(UtilsConstants.KEY_CUS_ID, customID);
            values.put(UtilsConstants.KEY_PR_MRP, strMRP);
            values.put(UtilsConstants.KEY_CART_FLAG, "0");
            values.put(UtilsConstants.KEY_CART_STOCK, "0");
            values.put(UtilsConstants.KEY_CART_STATUS, "0");
            db.insert(UtilsConstants.TABLE_CART, null, values);
            Log.d("Cart Count Insert :", qty);
        }

        db.close();
    }

    public HashMap<String, HashMap<String, String>> getAllCustomersDataToUpload() {
        HashMap<String, HashMap<String, String>> hashMap1 = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_CUSTOMERS + " where " + UtilsConstants.KEY_CUS_SYNCSTATUS + " = '0'";
        Log.d(TAG, "getAllCustomersDataToUpload: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_CUS_DISTRIBUTOR, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_DISTRIBUTOR)));
                hashMap.put(UtilsConstants.KEY_CUS_ROUTE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ROUTE)));
                hashMap.put(UtilsConstants.KEY_CUS_STATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_STATE)));
                hashMap.put(UtilsConstants.KEY_CUS_CITY, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_CITY)));
                hashMap.put(UtilsConstants.KEY_CUS_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_IMGURL)));
                hashMap.put(UtilsConstants.KEY_CUS_MOBILE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_MOBILE)));
                hashMap.put(UtilsConstants.KEY_CUS_EMAIL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_EMAIL)));
                hashMap.put(UtilsConstants.KEY_CUS_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_CUS_BILL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_BILL)));
                hashMap.put(UtilsConstants.KEY_CUS_OUTSTANDING, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUTSTANDING)));
                hashMap.put(UtilsConstants.KEY_CUS_OUT_UPDATED, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUT_UPDATED)));
                hashMap.put(UtilsConstants.KEY_CUS_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LAT)));
                hashMap.put(UtilsConstants.KEY_CUS_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LONG)));
                hashMap.put(UtilsConstants.KEY_CUS_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ACU)));
                hashMap.put(UtilsConstants.KEY_CUS_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_SYNCSTATUS)));
                hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), hashMap);
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap1;
    }

    public void updateSyncStatusCustomer(String oldid, String newid) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CUS_SYNCSTATUS, "2");
        values.put(UtilsConstants.KEY_CUS_ID, newid);
        String condition = UtilsConstants.KEY_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CUSTOMERS, null, condition, new String[]{oldid}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CUSTOMERS, values, UtilsConstants.KEY_ID + "=?", new String[]{oldid});
            Log.d("CustomerSyncStatus", "updateSyncStatusCustomer");
        }
        cursor.close();
        db.close();
    }

    public String getDistributorIdByName(String name) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_DISTRIBUTOR + " where " + UtilsConstants.KEY_DIST_NAME + " = '" + name + "'";
        Log.d(TAG, "getDistributorIdByName: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                db.close();
                return cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return "";
    }

    public String getRouteIdByName(String name) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_ROUTE + " where " + UtilsConstants.KEY_ROUTE_NAME + " = '" + name + "'";
        Log.d(TAG, "getRouteIdByName: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                db.close();
                return cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return "";
    }

    public HashMap<String, HashMap<String, String>> getAllLocationDataToUpload() {
        HashMap<String, HashMap<String, String>> hashMap1 = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_LOCATION + " where " + UtilsConstants.KEY_LOC_SYNCSTATUS + " = '0'";
        Log.d(TAG, "getAllLocation: " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_LOC_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LOC_COMPID)));
                hashMap.put(UtilsConstants.KEY_LOC_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LOC_USERNAME)));
                hashMap.put(UtilsConstants.KEY_LOC_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LOC_PASS)));
                hashMap.put(UtilsConstants.KEY_LOC_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LOC_LAT)));
                hashMap.put(UtilsConstants.KEY_LOC_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LOC_LONG)));
                hashMap.put(UtilsConstants.KEY_LOC_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LOC_ACU)));
                hashMap.put(UtilsConstants.KEY_LOC_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LOC_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                hashMap.put(UtilsConstants.KEY_LOC_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LOC_SYNCSTATUS)));
                hashMap1.put(cursor.getString(0), hashMap);
                Log.d(TAG, "getAllLocation All : " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7) + " " + cursor.getString(8));
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap1;
    }

    public void updateSyncStatusLocation(String id) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_LOC_SYNCSTATUS, "2");
        String condition = UtilsConstants.KEY_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_LOCATION, null, condition, new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_LOCATION, values, UtilsConstants.KEY_ID + "=?", new String[]{id});
            Log.d("LocationSyncStatus", "updateSyncStatusLocation");
        }
        db.close();
    }

    public void insertNotification(String msg, String msgType, String strdate) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_NOTIF_COMPID, SharedPreferencesUtil.getInstance(context).getData("compay_id"));
        values.put(UtilsConstants.KEY_NOTIF_USERNAME, SharedPreferencesUtil.getInstance(context).getData("email"));
        values.put(UtilsConstants.KEY_NOTIF_PASS, SharedPreferencesUtil.getInstance(context).getData("password"));
        values.put(UtilsConstants.KEY_NOTE_MESSAGE, msg);
        values.put(UtilsConstants.KEY_NOTE_READSTATUS, "0");
        values.put(UtilsConstants.KEY_NOTE_TYPE, msgType);
        values.put(UtilsConstants.KEY_CRATED_AT, strdate);
        try {
            db.insert(UtilsConstants.TABLE_NOTIFICATION, null, values);
            Log.d("Notification :", "Notification insert Successful");
        } catch (Exception e) {
            Log.d("Notification :", e + "");
        }
        db.close();
    }

    public ArrayList<HashMap<String, String>> getNotificationData() {
        ArrayList<HashMap<String, String>> mapList = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_NOTIFICATION + " where " + UtilsConstants.KEY_NOTIF_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' ORDER BY " + UtilsConstants.KEY_ID + " DESC";
        Log.d(TAG, "getNotification: " + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put(UtilsConstants.KEY_NOTE_MESSAGE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_NOTE_MESSAGE)));
            map.put(UtilsConstants.KEY_NOTE_TYPE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_NOTE_TYPE)));
            map.put(UtilsConstants.KEY_NOTE_READSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_NOTE_READSTATUS)));
            map.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            mapList.add(map);
        }
        Log.d(TAG, "getNotificationData: " + mapList);
        db.close();
        return mapList;
    }

    public void updateNotificationReadStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_NOTE_READSTATUS, "1");
        db.update(UtilsConstants.TABLE_NOTIFICATION, values, null, null);
        Log.d("NotificationReadStatus", "updateNotificationReadStatus");
        db.close();
    }

    public int getNotificationUnReadCounter() {
        int cartCount = 0;
        SQLiteDatabase db = database.getWritableDatabase();
        String condition = UtilsConstants.KEY_NOTE_READSTATUS + " =? and " + UtilsConstants.KEY_NOTIF_USERNAME + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_NOTIFICATION, null, condition, new String[]{"0", SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, null);
        cartCount = cursor.getCount();
        Log.d(TAG, "getNotificationUnReadCounter: " + cartCount);
        cursor.close();
        db.close();
        return cartCount;
    }

    public void changeEmailEverywhere(String email, String username) {
        Log.d(TAG, "changeEmailEverywhere: username :" + username);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_LOC_USERNAME, username);
        db.update(UtilsConstants.TABLE_LOCATION, values, UtilsConstants.KEY_LOC_USERNAME + " =?", new String[]{email});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_LOCATION");
        ContentValues values1 = new ContentValues();
        values1.put(UtilsConstants.KEY_ATTEND_USERNAME, username);
        db.update(UtilsConstants.TABLE_ATTENDANCE, values1, UtilsConstants.KEY_ATTEND_USERNAME + " =?", new String[]{email});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_ATTENDANCE");
        ContentValues values2 = new ContentValues();
        values2.put(UtilsConstants.KEY_LEAVE_USERNAME, username);
        db.update(UtilsConstants.TABLE_LEAVE, values2, UtilsConstants.KEY_LEAVE_USERNAME + " =?", new String[]{email});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_LEAVE");
        ContentValues values3 = new ContentValues();
        values3.put(UtilsConstants.KEY_NOTIF_USERNAME, username);
        db.update(UtilsConstants.TABLE_NOTIFICATION, values3, UtilsConstants.KEY_NOTIF_USERNAME + " =?", new String[]{email});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_NOTIFICATION");
        ContentValues values4 = new ContentValues();
        values4.put(UtilsConstants.KEY_CART_USERNAME, username);
        db.update(UtilsConstants.TABLE_CART, values4, UtilsConstants.KEY_CART_USERNAME + " =?", new String[]{email});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_CART");
        ContentValues values5 = new ContentValues();
        values5.put(UtilsConstants.KEY_VISIT_USERNAME, username);
        db.update(UtilsConstants.TABLE_VISIT, values5, UtilsConstants.KEY_VISIT_USERNAME + " =?", new String[]{email});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_VISIT");
        db.close();
    }

    public void changePasswordEverywhere(String user, String password) {
        Log.d(TAG, "changeEmailEverywhere: username :" + password);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_LOC_PASS, password);
        db.update(UtilsConstants.TABLE_LOCATION, values, UtilsConstants.KEY_LOC_USERNAME + " =?", new String[]{user});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_LOCATION");
        ContentValues values1 = new ContentValues();
        values1.put(UtilsConstants.KEY_ATTEND_PASS, password);
        db.update(UtilsConstants.TABLE_ATTENDANCE, values1, UtilsConstants.KEY_ATTEND_USERNAME + " =?", new String[]{user});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_ATTENDANCE");
        ContentValues values2 = new ContentValues();
        values2.put(UtilsConstants.KEY_LEAVE_PASS, password);
        db.update(UtilsConstants.TABLE_LEAVE, values2, UtilsConstants.KEY_LEAVE_USERNAME + " =?", new String[]{user});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_LEAVE");
        ContentValues values3 = new ContentValues();
        values3.put(UtilsConstants.KEY_NOTIF_PASS, password);
        db.update(UtilsConstants.TABLE_NOTIFICATION, values3, UtilsConstants.KEY_NOTIF_PASS + " =?", new String[]{user});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_NOTIFICATION");
        ContentValues values4 = new ContentValues();
        values4.put(UtilsConstants.KEY_CART_PASS, password);
        db.update(UtilsConstants.TABLE_CART, values4, UtilsConstants.KEY_CART_USERNAME + " =?", new String[]{user});
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_CART");
        ContentValues values5 = new ContentValues();
        values5.put(UtilsConstants.KEY_VISIT_PASS, password);
        db.update(UtilsConstants.TABLE_VISIT, values5, null, null);
        Log.d("changeEmailEverywhere", "changeEmailEverywhere : TABLE_VISIT");
        db.close();
    }

    public void updateCartInfo(String customerID, String customerName, String latitude, String longitude, String acu, String address, String imgPath, String orderId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CART_FLAG, "1");
        values.put(UtilsConstants.KEY_CART_SIGNATURE, imgPath);
        values.put(UtilsConstants.KEY_CART_ORDERID, orderId);
        values.put(UtilsConstants.KEY_CUS_NAME, customerName);
        values.put(UtilsConstants.KEY_LAT, latitude);
        values.put(UtilsConstants.KEY_LONG, longitude);
        values.put(UtilsConstants.KEY_CART_ACU, acu);
        values.put(UtilsConstants.KEY_ADDRESS, address);
        values.put(UtilsConstants.KEY_CRATED_AT, Generic.nowDate());
        String condition = UtilsConstants.KEY_CUS_ID + " =? and " + UtilsConstants.KEY_CART_FLAG + " =?";
        try {
            db.update(UtilsConstants.TABLE_CART, values, condition, new String[]{customerID, "0"});
            Log.d("Upadte CartInfo", "Successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CartInfo Error", "" + e);
        }
        db.close();
    }

    public void insertPlanVisitData(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_VISIT_COMPID, hashMap.get(UtilsConstants.KEY_VISIT_COMPID));
        values.put(UtilsConstants.KEY_VISIT_USERNAME, hashMap.get(UtilsConstants.KEY_VISIT_USERNAME));
        values.put(UtilsConstants.KEY_VISIT_PASS, hashMap.get(UtilsConstants.KEY_VISIT_PASS));
        values.put(UtilsConstants.KEY_CUS_ID, hashMap.get(UtilsConstants.KEY_CUS_ID));
        values.put(UtilsConstants.KEY_CUS_NAME, hashMap.get(UtilsConstants.KEY_CUS_NAME));
        values.put(UtilsConstants.KEY_VISIT_DESC, hashMap.get(UtilsConstants.KEY_VISIT_DESC));
        values.put(UtilsConstants.KEY_LAT, hashMap.get(UtilsConstants.KEY_LAT));
        values.put(UtilsConstants.KEY_LONG, hashMap.get(UtilsConstants.KEY_LONG));
        values.put(UtilsConstants.KEY_VISIT_ACU, hashMap.get(UtilsConstants.KEY_VISIT_ACU));
        values.put(UtilsConstants.KEY_ADDRESS, hashMap.get(UtilsConstants.KEY_ADDRESS));
        values.put(UtilsConstants.KEY_VISIT_ID, hashMap.get(UtilsConstants.KEY_VISIT_ID));
        values.put(UtilsConstants.KEY_VISIT_PLANDATE, hashMap.get(UtilsConstants.KEY_VISIT_PLANDATE));
        values.put(UtilsConstants.KEY_VISIT_STATUS, hashMap.get(UtilsConstants.KEY_VISIT_STATUS));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));

        String condition = UtilsConstants.KEY_VISIT_ID + " =? and " + UtilsConstants.KEY_VISIT_USERNAME + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_VISIT_PLAN, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_VISIT_ID), SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_VISIT_PLAN, values, UtilsConstants.KEY_ID + " = ?", new String[]{cursor.getString(0)});
            Log.d("Visit Plan update", hashMap.get(UtilsConstants.KEY_VISIT_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_VISIT_PLAN, null, values);
            Log.d(TAG, "Visit  Plan insert : " + status);
        }
        db.close();
    }

    public void insertVisitNowData(String strCusID, String strCustName, String strDes, String strRemark, String strCompt, String strdate,
                                   String imagePath, String createdDate, String strVisitId, String strLat, String strLong, String acu, String strAddress) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_VISIT_COMPID, SharedPreferencesUtil.getInstance(context).getData("compay_id"));
        values.put(UtilsConstants.KEY_VISIT_USERNAME, SharedPreferencesUtil.getInstance(context).getData("email"));
        values.put(UtilsConstants.KEY_VISIT_PASS, SharedPreferencesUtil.getInstance(context).getData("password"));
        values.put(UtilsConstants.KEY_CUS_ID, strCusID);
        values.put(UtilsConstants.KEY_CUS_NAME, strCustName);
        values.put(UtilsConstants.KEY_VISIT_DESC, strDes);
        values.put(UtilsConstants.KEY_VISIT_REMARK, strRemark);
        values.put(UtilsConstants.KEY_VISIT_COMP_INFO, strCompt);
        values.put(UtilsConstants.KEY_VISIT_IMGPATH, imagePath);
        values.put(UtilsConstants.KEY_VISIT_PLANDATE, strdate);
        values.put(UtilsConstants.KEY_VISIT_CRE_DATE, createdDate);
        values.put(UtilsConstants.KEY_VISIT_ID, strVisitId);
        values.put(UtilsConstants.KEY_VISIT_STATUS, "0");
        values.put(UtilsConstants.KEY_CRATED_AT, Generic.nowDate());
        values.put(UtilsConstants.KEY_LAT, strLat);
        values.put(UtilsConstants.KEY_LONG, strLong);
        values.put(UtilsConstants.KEY_VISIT_ACU, acu);
        values.put(UtilsConstants.KEY_ADDRESS, strAddress);

        try {
            db.insert(UtilsConstants.TABLE_VISIT, null, values);
            Log.d("Visit Now :", "Visit Now insert Successful");
        } catch (Exception e) {
            Log.d("Visit Now :", e + "");
        }
        db.close();

    }

    public LinkedHashMap<String, HashMap<String, String>> getVisitPlanInfo() {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT_PLAN + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_VISIT_PLANDATE + ") DESC";
        Log.d(TAG, "checkAllPlanVisit: " + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap;
        if (cursor.moveToFirst()) {
            do {
                hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_VISIT_PLANDATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_PLANDATE)));
                hashMap.put(UtilsConstants.KEY_VISIT_DESC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_DESC)));
                hashMap.put(UtilsConstants.KEY_VISIT_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_ID)));
                hashMap.put(UtilsConstants.KEY_VISIT_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_STATUS)));
                listItems.put(cursor.getString(0), hashMap);
            } while (cursor.moveToNext());
        }
        db.close();
        return listItems;
    }

    public LinkedHashMap<String, HashMap<String, String>> getVisitNowInfo() {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_VISIT_PLANDATE + ") DESC";
        Log.d(TAG, "checkAllVisitNow: " + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap;
        if (cursor.moveToFirst()) {
            do {
                hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_VISIT_PLANDATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_PLANDATE)));
                hashMap.put(UtilsConstants.KEY_VISIT_DESC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_DESC)));
                hashMap.put(UtilsConstants.KEY_VISIT_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_ID)));
                hashMap.put(UtilsConstants.KEY_VISIT_REMARK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_REMARK)));
                hashMap.put(UtilsConstants.KEY_VISIT_COMP_INFO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_COMP_INFO)));
                hashMap.put(UtilsConstants.KEY_VISIT_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_STATUS)));
                listItems.put(cursor.getString(0), hashMap);
            } while (cursor.moveToNext());
        }
        db.close();
        return listItems;
    }

    public ArrayList<HashMap<String, String>> getAllAsyncVisitNowData() {
        ArrayList<HashMap<String, String>> listItems = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' AND " + UtilsConstants.KEY_VISIT_STATUS + "='0'";
        Log.d(TAG, "checkAllVisitNow: " + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap;
        if (cursor.moveToFirst()) {
            do {
                hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_VISIT_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_COMPID)));
                hashMap.put(UtilsConstants.KEY_VISIT_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_USERNAME)));
                hashMap.put(UtilsConstants.KEY_VISIT_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_PASS)));
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_VISIT_PLANDATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_PLANDATE)));
                hashMap.put(UtilsConstants.KEY_VISIT_DESC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_DESC)));
                hashMap.put(UtilsConstants.KEY_VISIT_REMARK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_REMARK)));
                hashMap.put(UtilsConstants.KEY_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LAT)));
                hashMap.put(UtilsConstants.KEY_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LONG)));
                hashMap.put(UtilsConstants.KEY_VISIT_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_ACU)));
                hashMap.put(UtilsConstants.KEY_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_VISIT_COMP_INFO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_COMP_INFO)));
                hashMap.put(UtilsConstants.KEY_VISIT_IMGPATH, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_IMGPATH)));
                hashMap.put(UtilsConstants.KEY_VISIT_CRE_DATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_CRE_DATE)));
                listItems.add(hashMap);
            } while (cursor.moveToNext());
        }
        db.close();
        return listItems;
    }

    public void updateVisitNowData(String strKeyId, String strCustomerID, String strVisitId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_VISIT_STATUS, "2");
        values.put(UtilsConstants.KEY_VISIT_ID, strVisitId);
        String condition = UtilsConstants.KEY_CUS_ID + " =? and " + UtilsConstants.KEY_ID + " =?";
        try {
            db.update(UtilsConstants.TABLE_VISIT, values, condition, new String[]{strCustomerID, strKeyId});
            Log.d("Upadte Visit Now data", "Successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(" Update Visit Now Error", "" + e);
        }
        db.close();
    }

    public HashMap<String, HashMap<String, String>> getAllOrderDataToUploadByCustomerId(String orderId) {
        HashMap<String, HashMap<String, String>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_CART + " where " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email")
                + "' AND " + UtilsConstants.KEY_CART_FLAG + " = '1' AND " + UtilsConstants.KEY_CART_STATUS + " = '0' AND " + UtilsConstants.KEY_CART_ORDERID + " = '" + orderId + "'";
        Log.d(TAG, "Cart : Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap;
        while (cursor.moveToNext()) {
            hashMap = new HashMap<>();
            hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
            hashMap.put(UtilsConstants.KEY_PR_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)));
            hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            hashMap.put(UtilsConstants.KEY_CART_QTY, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY)));
            hashMap.put(UtilsConstants.KEY_CART_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_COMPID)));
            hashMap.put(UtilsConstants.KEY_CART_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_USERNAME)));
            hashMap.put(UtilsConstants.KEY_CART_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_PASS)));
            hashMap.put(UtilsConstants.KEY_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LAT)));
            hashMap.put(UtilsConstants.KEY_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LONG)));
            hashMap.put(UtilsConstants.KEY_CART_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ACU)));
            hashMap.put(UtilsConstants.KEY_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADDRESS)));
            hashMap.put(UtilsConstants.KEY_CART_FLAG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_FLAG)));
            hashMap.put(UtilsConstants.KEY_CART_ORDERID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID)));
            hashMap.put(UtilsConstants.KEY_CART_SIGNATURE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_SIGNATURE)));
            hashMap.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            hashMap.put(UtilsConstants.KEY_CART_STOCK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STOCK)));
            hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(0), hashMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public ArrayList<String> getOrderIDsToUpload() {
        ArrayList customerList = new ArrayList();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select DISTINCT " + UtilsConstants.KEY_CART_ORDERID + " from  " + UtilsConstants.TABLE_CART + " where " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email")
                + "' AND " + UtilsConstants.KEY_CART_FLAG + " = '1' AND " + UtilsConstants.KEY_CART_STATUS + " = '0' ";
        Log.d(TAG, "Cart Count: Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String customerName = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            customerList.add(customerName);
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public void updateSyncStatusOrder(String id, String orderID) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CART_STATUS, "2");
        values.put(UtilsConstants.KEY_CART_ORDERID, orderID);
        String condition = UtilsConstants.KEY_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CART, null, condition, new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CART, values, UtilsConstants.KEY_ID + "=?", new String[]{id});
            Log.d("SyncStatusOrder", "updateSyncStatusOrder");
        }
        db.close();
    }

    public void updateOrderSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CART_STATUS, "3");
        String condition = UtilsConstants.KEY_CART_STATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CART, null, condition, new String[]{"2"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CART, values, UtilsConstants.KEY_CART_STATUS + "=?", new String[]{"2"});
            Log.d("ProductSyncStatus", "updateProductSyncStatus");
        }
        db.close();
    }

    public void addCartProduct(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_PR_ID, hashMap.get(UtilsConstants.KEY_PR_ID));
        values.put(UtilsConstants.KEY_CUS_ID, hashMap.get(UtilsConstants.KEY_CUS_ID));
        values.put(UtilsConstants.KEY_CART_QTY, hashMap.get(UtilsConstants.KEY_CART_QTY));
        values.put(UtilsConstants.KEY_CART_COMPID, hashMap.get(UtilsConstants.KEY_CART_COMPID));
        values.put(UtilsConstants.KEY_CART_USERNAME, hashMap.get(UtilsConstants.KEY_CART_USERNAME));
        values.put(UtilsConstants.KEY_CUS_NAME, hashMap.get(UtilsConstants.KEY_CUS_NAME));
        values.put(UtilsConstants.KEY_CART_PASS, hashMap.get(UtilsConstants.KEY_CART_PASS));
        values.put(UtilsConstants.KEY_CART_STOCK, hashMap.get(UtilsConstants.KEY_CART_STOCK));
        values.put(UtilsConstants.KEY_CART_FLAG, hashMap.get(UtilsConstants.KEY_CART_FLAG));
        values.put(UtilsConstants.KEY_CART_ORDERID, hashMap.get(UtilsConstants.KEY_CART_ORDERID));
        values.put(UtilsConstants.KEY_CART_SIGNATURE, hashMap.get(UtilsConstants.KEY_CART_SIGNATURE));
        values.put(UtilsConstants.KEY_CART_STATUS, hashMap.get(UtilsConstants.KEY_CART_STATUS));
        values.put(UtilsConstants.KEY_PR_MRP, hashMap.get(UtilsConstants.KEY_PR_MRP));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));

        String condition = UtilsConstants.KEY_PR_ID + " =? and " + UtilsConstants.KEY_CUS_ID + " =? and " + UtilsConstants.KEY_CART_ORDERID + " =? and " + UtilsConstants.KEY_CART_USERNAME + " =? ";
        Cursor cursor = db.query(UtilsConstants.TABLE_CART, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_PR_ID), hashMap.get(UtilsConstants.KEY_CUS_ID), hashMap.get(UtilsConstants.KEY_CART_ORDERID), SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CART, values, UtilsConstants.KEY_ID + " = ?", new String[]{cursor.getString(0)});
            Log.d("Cart product update", hashMap + "");
        } else {
            long status = db.insert(UtilsConstants.TABLE_CART, null, values);
            Log.d(TAG, "Cart insert : " + status);
        }
        db.close();
    }

    public LinkedHashMap<String, ArrayList<HashMap<String, String>>> getOrderIDsToShow(String from, String to) {
        LinkedHashMap<String, ArrayList<HashMap<String, String>>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from CartInfo where STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_CRATED_AT + ") between '" + from + "' and '" + to + "' and OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d %HH:%mm:%ss'," + UtilsConstants.KEY_CRATED_AT + ") DESC";
        Log.d(TAG, "Cart Count: Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        ArrayList<HashMap<String, String>> arrMap;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            arrMap = new ArrayList<>();
            String strOrderId = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            String strCartQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
            String strMRP = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP));
            listItems1.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems1.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            listItems1.put(UtilsConstants.KEY_CART_QTY, strCartQty);
            listItems1.put(UtilsConstants.KEY_PR_MRP, strMRP);
            String strTotalAmt = "0";
            if (strMRP != null && strCartQty != null) {
                if (Double.parseDouble(strMRP) > 0) {
                    strTotalAmt = "" + (Double.parseDouble(strMRP) * Integer.parseInt(strCartQty));
                }
            }
            Log.d(TAG, "getOrderIDsToShow: " + strTotalAmt);
            BigDecimal bd1 = new BigDecimal(strTotalAmt);
            long val1 = bd1.longValue();
            listItems1.put("ProductAmt", val1 + "");
            listItems1.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            if (listItems.containsKey(strOrderId)) {
                arrMap = listItems.get(strOrderId);
                arrMap.add(listItems1);
            } else {
                arrMap.add(listItems1);
            }
            listItems.put(strOrderId, arrMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public void addVisitNowData(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CUS_ID, hashMap.get(UtilsConstants.KEY_CUS_ID));
        values.put(UtilsConstants.KEY_CUS_NAME, hashMap.get(UtilsConstants.KEY_CUS_NAME));
        values.put(UtilsConstants.KEY_VISIT_ID, hashMap.get(UtilsConstants.KEY_VISIT_ID));
        values.put(UtilsConstants.KEY_VISIT_USERNAME, hashMap.get(UtilsConstants.KEY_VISIT_USERNAME));
        values.put(UtilsConstants.KEY_VISIT_COMPID, hashMap.get(UtilsConstants.KEY_VISIT_COMPID));
        values.put(UtilsConstants.KEY_VISIT_DESC, hashMap.get(UtilsConstants.KEY_VISIT_DESC));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));
        values.put(UtilsConstants.KEY_VISIT_PLANDATE, hashMap.get(UtilsConstants.KEY_VISIT_PLANDATE));
        values.put(UtilsConstants.KEY_VISIT_COMP_INFO, hashMap.get(UtilsConstants.KEY_VISIT_COMP_INFO));
        values.put(UtilsConstants.KEY_VISIT_REMARK, hashMap.get(UtilsConstants.KEY_VISIT_REMARK));
        values.put(UtilsConstants.KEY_VISIT_IMGPATH, hashMap.get(UtilsConstants.KEY_VISIT_IMGPATH));
        values.put(UtilsConstants.KEY_VISIT_STATUS, hashMap.get(UtilsConstants.KEY_VISIT_STATUS));
        Log.d(TAG, "addVisitNowData: visit id : " + hashMap.get(UtilsConstants.KEY_VISIT_ID));
        String condition = UtilsConstants.KEY_VISIT_ID + " =? and " + UtilsConstants.KEY_VISIT_USERNAME + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_VISIT, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_VISIT_ID), SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_VISIT, values, UtilsConstants.KEY_ID + " = ?", new String[]{cursor.getString(0)});
            Log.d("Visit update", hashMap.get(UtilsConstants.KEY_VISIT_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_VISIT, null, values);
            Log.d(TAG, "Visit insert : " + status);
        }
        cursor.close();
        db.close();
    }

    public void updateVisitSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_VISIT_STATUS, "3");
        String condition = UtilsConstants.KEY_VISIT_STATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_VISIT, null, condition, new String[]{"2"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_VISIT, values, UtilsConstants.KEY_VISIT_STATUS + "=?", new String[]{"2"});
            Log.d("ProductSyncStatus", "updateProductSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public void updateVisitPlanSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_VISIT_STATUS, "3");
        String condition = UtilsConstants.KEY_VISIT_STATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_VISIT_PLAN, null, condition, new String[]{"2"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_VISIT_PLAN, values, UtilsConstants.KEY_VISIT_STATUS + "=?", new String[]{"2"});
            Log.d("ProductSyncStatus", "updateProductSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public ArrayList<HashMap<String, String>> getAllAsyncPlanVisitListData() {
        ArrayList<HashMap<String, String>> listItems = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT_PLAN + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.KEY_VISIT_STATUS + " = '0'";
        Log.d(TAG, "getAllAsyncPlanVisitListData: " + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap;
        if (cursor.moveToFirst()) {
            do {
                hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                hashMap.put(UtilsConstants.KEY_VISIT_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_COMPID)));
                hashMap.put(UtilsConstants.KEY_VISIT_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_USERNAME)));
                hashMap.put(UtilsConstants.KEY_VISIT_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_PASS)));
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_VISIT_PLANDATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_PLANDATE)));
                hashMap.put(UtilsConstants.KEY_VISIT_DESC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_DESC)));
                hashMap.put(UtilsConstants.KEY_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LAT)));
                hashMap.put(UtilsConstants.KEY_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_LONG)));
                hashMap.put(UtilsConstants.KEY_VISIT_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_ACU)));
                hashMap.put(UtilsConstants.KEY_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                hashMap.put(UtilsConstants.KEY_VISIT_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_STATUS)));
                listItems.add(hashMap);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public void updatePlanVisitStatus(HashMap<String, String> hashMap, String visitId) {
        Log.d(TAG, "updatePlanVisitStatus: visitId :" + visitId);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_VISIT_STATUS, "2");
        values.put(UtilsConstants.KEY_VISIT_ID, visitId);
        String condition = UtilsConstants.KEY_ID + " =?";
        try {
            db.update(UtilsConstants.TABLE_VISIT_PLAN, values, condition, new String[]{hashMap.get(UtilsConstants.KEY_ID)});
            Log.d("Upadte PlanVisit", "Successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(" Update PlanVisit Error", "" + e);
        }
        db.close();
    }

    public ArrayList<HashMap<String, String>> getOrderListInfo(String s, String orderId) {
        ArrayList<HashMap<String, String>> arr = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "SELECT * FROM " + UtilsConstants.TABLE_CART + " a INNER JOIN " + UtilsConstants.TABLE_PRODUCTS + " b ON a.productId = b.productId where a.customerId='" + s
                + "' and a.username='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and a.OrderID='" + orderId + "'";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            map.put(UtilsConstants.KEY_PR_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)));
            map.put(UtilsConstants.KEY_PR_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_IMGURL)));
            map.put(UtilsConstants.KEY_PR_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_NAME)));
            map.put(UtilsConstants.KEY_PR_MRP, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP)));
            map.put(UtilsConstants.KEY_CART_QTY, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY)));
            map.put(UtilsConstants.KEY_CART_STOCK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STOCK)));
            arr.add(map);
        }
        cursor.close();
        db.close();
        return arr;
    }

    public int getTodaysVisit() {
        int totalVisit = 0;
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' AND " + UtilsConstants.KEY_CRATED_AT + " >= date('now','localtime','start of day')";
        Log.d(TAG, "checkAllVisitNow: " + query);
        Cursor cursor = db.rawQuery(query, null);
        totalVisit = cursor.getCount();
        cursor.close();
        db.close();
        return totalVisit;
    }

    public int getTodaysOrder() {
        int totalVisit = 0;
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select DISTINCT " + UtilsConstants.KEY_CART_ORDERID + "  from  " + UtilsConstants.TABLE_CART + " where " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' AND " + UtilsConstants.KEY_CRATED_AT + " >= date('now','localtime','start of day') AND " + UtilsConstants.KEY_CART_FLAG + " ='1'";
        Log.d(TAG, "Todays Order : " + query);
        Cursor cursor = db.rawQuery(query, null);
        totalVisit = cursor.getCount();
        db.close();
        return totalVisit;
    }

    public void updateCartStock(String customID, String customName, String strProductID, String stock) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CART_STOCK, stock);
        values.put(UtilsConstants.KEY_CUS_NAME, customName);
        String strUsername = SharedPreferencesUtil.getInstance(context).getData("email");
        String condition = UtilsConstants.KEY_PR_ID + " =? AND " + UtilsConstants.KEY_CUS_ID + " =? AND " + UtilsConstants.KEY_CART_USERNAME + " =? AND " + UtilsConstants.KEY_CART_FLAG + "=?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CART, null, condition, new String[]{strProductID, customID, strUsername, "0"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CART, values, condition, new String[]{strProductID, customID, strUsername, "0"});
            Log.d("Cart Count Update :", stock);
        } else {
            values.put(UtilsConstants.KEY_CART_COMPID, SharedPreferencesUtil.getInstance(context).getData("compay_id"));
            values.put(UtilsConstants.KEY_CART_USERNAME, SharedPreferencesUtil.getInstance(context).getData("email"));
            values.put(UtilsConstants.KEY_CART_PASS, SharedPreferencesUtil.getInstance(context).getData("password"));
            values.put(UtilsConstants.KEY_PR_ID, strProductID);
            values.put(UtilsConstants.KEY_CUS_ID, customID);
            values.put(UtilsConstants.KEY_CART_FLAG, "0");
            values.put(UtilsConstants.KEY_CART_STATUS, "0");
            db.insert(UtilsConstants.TABLE_CART, null, values);
            Log.d("Cart Count Insert :", stock);
        }

        db.close();
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> getTodaysOrderIDsToShow() {
        HashMap<String, ArrayList<HashMap<String, String>>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
//        String query = "select DISTINCT " + UtilsConstants.KEY_CART_ORDERID + " , " + UtilsConstants.KEY_CUS_ID+" , "+UtilsConstants.KEY_CRATED_AT+ " from  " + UtilsConstants.TABLE_CART +" where "+UtilsConstants.KEY_CART_USERNAME+" = '"+SharedPreferencesUtil.getInstance(context).getData("email")
//                +"' AND "+UtilsConstants.KEY_CART_FLAG+" = '1'";

        String query = "select * from CartInfo where OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' AND " + UtilsConstants.KEY_CRATED_AT + " >= date('now','localtime','start of day')";
        Log.d(TAG, "Cart Count: Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        ArrayList<HashMap<String, String>> arrMap;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            arrMap = new ArrayList<>();
            String strOrderId = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            String strCartQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
            String strMRP = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP));
            listItems1.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems1.put(UtilsConstants.KEY_CART_QTY, strCartQty);
            listItems1.put(UtilsConstants.KEY_PR_MRP, strMRP);
            String strTotalAmt = "0";
            if (strMRP != null && strCartQty != null) {
                if (Double.parseDouble(strMRP) > 0 && Integer.parseInt(strCartQty) > 0) {
                    strTotalAmt = "" + (Double.parseDouble(strMRP) * Integer.parseInt(strCartQty));
                }
            }
            listItems1.put("ProductAmt", strTotalAmt);
            listItems1.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            if (listItems.containsKey(strOrderId)) {
                arrMap = listItems.get(strOrderId);
                arrMap.add(listItems1);
            } else {
                arrMap.add(listItems1);
            }
            listItems.put(strOrderId, arrMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }


    public HashMap<String, String> getVisitByID(String visit_id) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT_PLAN + " where " + UtilsConstants.KEY_VISIT_ID + " = '" + visit_id + "'";
        Log.d(TAG, "checkAllPlanVisit: " + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap = null;
        if (cursor.moveToFirst()) {
            do {
                hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_VISIT_PLANDATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_PLANDATE)));
                hashMap.put(UtilsConstants.KEY_VISIT_DESC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_DESC)));
                hashMap.put(UtilsConstants.KEY_VISIT_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_ID)));
                hashMap.put(UtilsConstants.KEY_VISIT_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_STATUS)));
            } while (cursor.moveToNext());
        }
        db.close();
        return hashMap;
    }

    public HashMap<String, HashMap<String, String>> getOtherPlanVisitsToday() {
        HashMap<String, HashMap<String, String>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT_PLAN + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' and STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_VISIT_PLANDATE + ") = '" + Generic.GetFormatedDate(Generic.nowDate()) + "'";
        Log.d(TAG, "getOtherPlanVisitsToday: " + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> hashMap;
        if (cursor.moveToFirst()) {
            do {
                hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_VISIT_PLANDATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_PLANDATE)));
                hashMap.put(UtilsConstants.KEY_VISIT_DESC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_DESC)));
                hashMap.put(UtilsConstants.KEY_VISIT_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_ID)));
                hashMap.put(UtilsConstants.KEY_VISIT_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_VISIT_STATUS)));
                listItems.put(cursor.getString(0), hashMap);
            } while (cursor.moveToNext());
        }
        db.close();
        return listItems;
    }

    public void removeProductFromCart(HashMap<String, String> hash) {
        SQLiteDatabase db = database.getWritableDatabase();
        String condition = UtilsConstants.KEY_PR_ID + " =? AND " + UtilsConstants.KEY_CUS_ID + " =? AND " + UtilsConstants.KEY_CART_USERNAME + " =? AND " + UtilsConstants.KEY_CART_FLAG + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CART, null, condition, new String[]{hash.get(UtilsConstants.KEY_PR_ID), hash.get(UtilsConstants.KEY_CUS_ID), SharedPreferencesUtil.getInstance(context).getData("email"), "0"}, null, null, null);
        if (cursor.moveToFirst()) {
            int oj = db.delete(UtilsConstants.TABLE_CART, condition, new String[]{hash.get(UtilsConstants.KEY_PR_ID), hash.get(UtilsConstants.KEY_CUS_ID), SharedPreferencesUtil.getInstance(context).getData("email"), "0"});
            Log.d(TAG, "onClick: delete : " + hash);
            Log.d("removeProduct", "removeProductFromCart : " + oj + "  : " + cursor.getString(0));
        }
        db.close();
    }

    public void updateAttendanceStatus(String id) {
        Log.d(TAG, "updateAttendanceStatus: id :" + id);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ATTEND_SYNCSTATUS, "2");
        String condition = UtilsConstants.KEY_ID + " =?";
        try {
            db.update(UtilsConstants.TABLE_ATTENDANCE, values, condition, new String[]{id});
            Log.d("Attendance Status", "Successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(" Attendance Error", "" + e);
        }
        db.close();
    }

    public int getVisitByDate(String date) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' and STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_CRATED_AT + ") = '" + date + "'";
        Log.d(TAG, "checkAllVisitNow: " + date);
        Cursor cursor = db.rawQuery(query, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> getOrderIDsToShowByDate(String date) {
        HashMap<String, ArrayList<HashMap<String, String>>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from CartInfo where OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_CRATED_AT + ") = '" + date + "'";
        Log.d(TAG, "Cart Count: Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        ArrayList<HashMap<String, String>> arrMap;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            arrMap = new ArrayList<>();
            String strOrderId = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            String strCartQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
            String strMRP = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP));
            listItems1.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems1.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            listItems1.put(UtilsConstants.KEY_CART_QTY, strCartQty);
            listItems1.put(UtilsConstants.KEY_PR_MRP, strMRP);
            String strTotalAmt = "0";
            if (strMRP != null && strCartQty != null) {
                if (Double.parseDouble(strMRP) > 0) {
                    strTotalAmt = "" + (Double.parseDouble(strMRP) * Integer.parseInt(strCartQty));
                }
            }
            listItems1.put("ProductAmt", strTotalAmt);
            listItems1.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            if (listItems.containsKey(strOrderId)) {
                arrMap = listItems.get(strOrderId);
                arrMap.add(listItems1);
            } else {
                arrMap.add(listItems1);
            }
            listItems.put(strOrderId, arrMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public ArrayList<String> getAllOrdersDate() {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from CartInfo where OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Log.d(TAG, "getAllOrdersDate :" + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            String formattedDate = "";
            try {
                date = originalFormat.parse(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                if (date != null) {
                    formattedDate = targetFormat.format(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            arrayList.add(formattedDate);
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<String> getAllVisitsDate() {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Log.d(TAG, "getAllVisitsDate : " + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            String formattedDate = "";
            try {
                date = originalFormat.parse(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
                if (date != null) {
                    formattedDate = targetFormat.format(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            arrayList.add(formattedDate);
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public int getVisitBetWeenDates(String startDate, String endDate) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT + " where STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_CRATED_AT + ") between '" + startDate + "' and '" + endDate + "'  and " + UtilsConstants.KEY_VISIT_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Log.d(TAG, "getVisitBetWeenDates: " + startDate + " : " + endDate);
        Cursor cursor = db.rawQuery(query, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> getOrderIDsToShowBetweenDate(String startDate, String endDate) {
        HashMap<String, ArrayList<HashMap<String, String>>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from CartInfo where OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and STRFTIME('%Y-%m-%d'," + UtilsConstants.KEY_CRATED_AT + ") between '" + startDate + "' and '" + endDate + "'";
        Log.d(TAG, "Cart Count: Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        ArrayList<HashMap<String, String>> arrMap;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            arrMap = new ArrayList<>();
            String strOrderId = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            String strCartQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
            String strMRP = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP));
            listItems1.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems1.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            listItems1.put(UtilsConstants.KEY_CART_QTY, strCartQty);
            listItems1.put(UtilsConstants.KEY_PR_MRP, strMRP);
            String strTotalAmt = "0";
            if (strMRP != null && strCartQty != null) {
                if (Double.parseDouble(strMRP) > 0) {
                    strTotalAmt = "" + (Double.parseDouble(strMRP) * Integer.parseInt(strCartQty));
                }
            }
            listItems1.put("ProductAmt", strTotalAmt);
            listItems1.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            if (listItems.containsKey(strOrderId)) {
                arrMap = listItems.get(strOrderId);
                arrMap.add(listItems1);
            } else {
                arrMap.add(listItems1);
            }
            listItems.put(strOrderId, arrMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public int getVisitByMonth(String date) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_VISIT + " where " + UtilsConstants.KEY_VISIT_USERNAME + " = '" +
                SharedPreferencesUtil.getInstance(context).getData("email") + "' and STRFTIME('%Y-%m'," + UtilsConstants.KEY_CRATED_AT + ") = '" + date + "'";
        Log.d(TAG, "checkAllVisitNow: " + date);
        Cursor cursor = db.rawQuery(query, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> getOrderIDsToShowByMonth(String date) {
        HashMap<String, ArrayList<HashMap<String, String>>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from CartInfo where OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and STRFTIME('%Y-%m'," + UtilsConstants.KEY_CRATED_AT + ") = '" + date + "'";
        Log.d(TAG, "Cart Count: Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        ArrayList<HashMap<String, String>> arrMap;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            arrMap = new ArrayList<>();
            String strOrderId = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            String strCartQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
            String strMRP = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP));
            listItems1.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems1.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            listItems1.put(UtilsConstants.KEY_CART_QTY, strCartQty);
            listItems1.put(UtilsConstants.KEY_PR_MRP, strMRP);
            String strTotalAmt = "0";
            if (strMRP != null && strCartQty != null) {
                if (Double.parseDouble(strMRP) > 0) {
                    strTotalAmt = "" + (Double.parseDouble(strMRP) * Integer.parseInt(strCartQty));
                }
            }
            listItems1.put("ProductAmt", strTotalAmt);
            listItems1.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            if (listItems.containsKey(strOrderId)) {
                arrMap = listItems.get(strOrderId);
                arrMap.add(listItems1);
            } else {
                arrMap.add(listItems1);
            }
            listItems.put(strOrderId, arrMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public String getCustomerByIDCArt(String id) {
        String str = "";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(UtilsConstants.TABLE_CART, null
                , UtilsConstants.KEY_CUS_ID + "=?", new String[]{id}, null, null, null, null);
        if (cursor.moveToFirst()) {
            str = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME));
            Log.d(TAG, "getCustomerByIDCArt: " + cursor.getString(1) + " : " + cursor.getString(2) + " : " + cursor.getString(3) + " : " + cursor.getString(4) + " : " + cursor.getString(5) + " : " + cursor.getString(6) + " : " + cursor.getString(7) + " : " + cursor.getString(8));
        }
        if (str == null)
            str = "";
        db.close();
        return str;
    }

    public void insertExpense(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_EXPENSE_COMPID, hashMap.get(UtilsConstants.KEY_EXPENSE_COMPID));
        values.put(UtilsConstants.KEY_EXPENSE_USERNAME, hashMap.get(UtilsConstants.KEY_EXPENSE_USERNAME));
        values.put(UtilsConstants.KEY_EXPENSE_PASS, hashMap.get(UtilsConstants.KEY_EXPENSE_PASS));
        values.put(UtilsConstants.KEY_EXPENSE_GROUP_ID, hashMap.get(UtilsConstants.KEY_EXPENSE_GROUP_ID));
        values.put(UtilsConstants.KEY_EXPENSE_ID, hashMap.get(UtilsConstants.KEY_EXPENSE_ID));
        values.put(UtilsConstants.KEY_EXPENSE_LAT, hashMap.get(UtilsConstants.KEY_EXPENSE_LAT));
        values.put(UtilsConstants.KEY_EXPENSE_LONG, hashMap.get(UtilsConstants.KEY_EXPENSE_LONG));
        values.put(UtilsConstants.KEY_EXPENSE_ADDRESS, hashMap.get(UtilsConstants.KEY_EXPENSE_ADDRESS));
        values.put(UtilsConstants.KEY_EXPENSE_TYPE, hashMap.get(UtilsConstants.KEY_EXPENSE_TYPE));
        values.put(UtilsConstants.KEY_EXPENSE_CLAIM_DATE, hashMap.get(UtilsConstants.KEY_EXPENSE_CLAIM_DATE));
        values.put(UtilsConstants.KEY_EXPENSE_FROM, hashMap.get(UtilsConstants.KEY_EXPENSE_FROM));
        values.put(UtilsConstants.KEY_EXPENSE_TO, hashMap.get(UtilsConstants.KEY_EXPENSE_TO));
        values.put(UtilsConstants.KEY_EXPENSE_OTHER, hashMap.get(UtilsConstants.KEY_EXPENSE_OTHER));
        values.put(UtilsConstants.KEY_EXPENSE_MODE, hashMap.get(UtilsConstants.KEY_EXPENSE_MODE));
        values.put(UtilsConstants.KEY_EXPENSE_KM, hashMap.get(UtilsConstants.KEY_EXPENSE_KM));
        values.put(UtilsConstants.KEY_EXPENSE_RATE, hashMap.get(UtilsConstants.KEY_EXPENSE_RATE));
        values.put(UtilsConstants.KEY_EXPENSE_AMOUNT, hashMap.get(UtilsConstants.KEY_EXPENSE_AMOUNT));
        values.put(UtilsConstants.KEY_EXPENSE_IMG, hashMap.get(UtilsConstants.KEY_EXPENSE_IMG));
        values.put(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS, hashMap.get(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS));
        values.put(UtilsConstants.KEY_EXPENSE_STATUS, hashMap.get(UtilsConstants.KEY_EXPENSE_STATUS));
        values.put(UtilsConstants.KEY_EXPENSE_SYNC_STATUS, hashMap.get(UtilsConstants.KEY_EXPENSE_SYNC_STATUS));
        values.put(UtilsConstants.KEY_EXPENSE_GROUP_ID, hashMap.get(UtilsConstants.KEY_EXPENSE_GROUP_ID));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));

        String condition = UtilsConstants.KEY_EXPENSE_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_EXPENSE, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_EXPENSE_ID)}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_EXPENSE, values, UtilsConstants.KEY_EXPENSE_ID + " = ?", new String[]{hashMap.get(UtilsConstants.KEY_EXPENSE_ID)});
            Log.d("update Expense", hashMap.get(UtilsConstants.KEY_EXPENSE_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_EXPENSE, null, values);
            Log.d(TAG, "Expense insert : " + status);
        }
        cursor.close();
        db.close();
    }

    public LinkedHashMap<String, HashMap<String, String>> getRequestExpenseList() {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_EXPENSE + " where " + UtilsConstants.KEY_EXPENSE_REQUEST_STATUS + " = '0'  and " + UtilsConstants.KEY_EXPENSE_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d %H:%M:%S'," + UtilsConstants.KEY_CRATED_AT + ") DESC";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_COMPID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_USERNAME)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_PASS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_GROUP_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_GROUP_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_LAT)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_LONG)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_TYPE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_TYPE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_CLAIM_DATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_CLAIM_DATE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_FROM)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_TO)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_OTHER, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_OTHER)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_MODE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_MODE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_KM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_KM)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_RATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_RATE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_AMOUNT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_AMOUNT)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_IMG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_IMG)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_STATUS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public void placeExpenseRequest(int group_id, HashMap<String, String> items1) {
        Log.d(TAG, "placeExpenseRequest: id :" + group_id);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS, "1");
        values.put(UtilsConstants.KEY_EXPENSE_GROUP_ID, group_id + "");
        values.put(UtilsConstants.KEY_EXPENSE_LAT, items1.get(UtilsConstants.KEY_EXPENSE_LAT));
        values.put(UtilsConstants.KEY_EXPENSE_LONG, items1.get(UtilsConstants.KEY_EXPENSE_LONG));
        values.put(UtilsConstants.KEY_EXPENSE_ACU, items1.get(UtilsConstants.KEY_EXPENSE_ACU));
        values.put(UtilsConstants.KEY_CRATED_AT, Generic.nowDate() + "");
        String condition = UtilsConstants.KEY_EXPENSE_ID + " =?";
        try {
            db.update(UtilsConstants.TABLE_EXPENSE, values, condition, new String[]{items1.get(UtilsConstants.KEY_EXPENSE_ID)});
            Log.d("Expense Status", "Successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(" Expense Error", "" + e);
        }
        db.close();
    }

    public ArrayList<String> getAllExpGroupIds() {
        ArrayList customerList = new ArrayList();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select DISTINCT " + UtilsConstants.KEY_EXPENSE_GROUP_ID + " from  " + UtilsConstants.TABLE_EXPENSE + " where " + UtilsConstants.KEY_EXPENSE_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email")
                + "' AND " + UtilsConstants.KEY_EXPENSE_REQUEST_STATUS + " = '1' order by STRFTIME('%Y-%m-%d %H:%M:%S'," + UtilsConstants.KEY_CRATED_AT + ") DESC";
        Log.d(TAG, "getAllExpGroupIds : " + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String customerName = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_GROUP_ID));
            customerList.add(customerName);
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public HashMap<String, HashMap<String, String>> getAllExpRequestsByID(String groupId) {
        HashMap<String, HashMap<String, String>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_EXPENSE + " where " + UtilsConstants.KEY_EXPENSE_REQUEST_STATUS + " = '1' and " + UtilsConstants.KEY_EXPENSE_GROUP_ID + " = '" + groupId + "' and " + UtilsConstants.KEY_EXPENSE_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_EXPENSE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_COMPID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_USERNAME)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_PASS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_GROUP_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_GROUP_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_LAT)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_LONG)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_TYPE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_TYPE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_CLAIM_DATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_CLAIM_DATE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_FROM)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_TO)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_OTHER, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_OTHER)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_MODE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_MODE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_KM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_KM)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_RATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_RATE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_AMOUNT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_AMOUNT)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_IMG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_IMG)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_STATUS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public ArrayList<String> getAllExpGroupIdsToUpload() {
        ArrayList customerList = new ArrayList();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select DISTINCT " + UtilsConstants.KEY_EXPENSE_GROUP_ID + " from  " + UtilsConstants.TABLE_EXPENSE + " where " + UtilsConstants.KEY_EXPENSE_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email")
                + "' AND " + UtilsConstants.KEY_EXPENSE_REQUEST_STATUS + " = '1' AND " + UtilsConstants.KEY_EXPENSE_SYNC_STATUS + " = '0'";
        Log.d(TAG, "getAllExpGroupIds To Upload : " + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String customerName = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_GROUP_ID));
            customerList.add(customerName);
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public HashMap<String, HashMap<String, String>> getAllExpRequestsByIDToUpload(String groupId) {
        HashMap<String, HashMap<String, String>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_EXPENSE + " where " + UtilsConstants.KEY_EXPENSE_REQUEST_STATUS + " = '1' and " + UtilsConstants.KEY_EXPENSE_GROUP_ID + " = '" + groupId + "' and " + UtilsConstants.KEY_EXPENSE_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.KEY_EXPENSE_SYNC_STATUS + " = '0'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_EXPENSE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_COMPID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_USERNAME)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_PASS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_GROUP_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_GROUP_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_LAT)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_LONG)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ACU)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_TYPE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_TYPE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_CLAIM_DATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_CLAIM_DATE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_FROM)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_TO)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_OTHER, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_OTHER)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_MODE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_MODE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_KM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_KM)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_RATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_RATE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_AMOUNT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_AMOUNT)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_IMG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_IMG)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_STATUS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public HashMap<String, HashMap<String, String>> getAllExpRequestsByIDSync(String groupId) {
        HashMap<String, HashMap<String, String>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_EXPENSE + " where " + UtilsConstants.KEY_EXPENSE_REQUEST_STATUS + " = '1' and " + UtilsConstants.KEY_EXPENSE_GROUP_ID + " = '" + groupId + "' and " + UtilsConstants.KEY_EXPENSE_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.KEY_EXPENSE_SYNC_STATUS + " = '1'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_EXPENSE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_COMPID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_USERNAME)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_PASS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_GROUP_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_GROUP_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ID)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_LAT)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_LONG)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_TYPE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_TYPE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_CLAIM_DATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_CLAIM_DATE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_FROM)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_TO)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_OTHER, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_OTHER)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_MODE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_MODE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_KM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_KM)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_RATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_RATE)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_AMOUNT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_AMOUNT)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_IMG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_IMG)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_REQUEST_STATUS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_STATUS)));
            listItems1.put(UtilsConstants.KEY_EXPENSE_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_EXPENSE_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public void updateExpenseStatus(String expense_details_id, String expense_old_id) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_EXPENSE_SYNC_STATUS, "1");
        values.put(UtilsConstants.KEY_EXPENSE_ID, expense_details_id);
        String condition = UtilsConstants.KEY_EXPENSE_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_EXPENSE, null, condition, new String[]{expense_old_id}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_EXPENSE, values, UtilsConstants.KEY_EXPENSE_ID + "=?", new String[]{expense_old_id});
            Log.d("updateExpenseStatus", "updateExpenseStatus");
        }
        cursor.close();
        db.close();
    }

    public void updateExpMasterID(String master_id, String oldmasterId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_EXPENSE_GROUP_ID, master_id);
        String condition = UtilsConstants.KEY_EXPENSE_GROUP_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_EXPENSE, null, condition, new String[]{oldmasterId}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_EXPENSE, values, UtilsConstants.KEY_EXPENSE_GROUP_ID + "=?", new String[]{oldmasterId});
            Log.d("updateExpMasterID", "updateExpMasterID");
        }
        cursor.close();
        db.close();
    }

    public void insertTravel(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_TRAVEL_COMPID, hashMap.get(UtilsConstants.KEY_TRAVEL_COMPID));
        values.put(UtilsConstants.KEY_TRAVEL_USERNAME, hashMap.get(UtilsConstants.KEY_TRAVEL_USERNAME));
        values.put(UtilsConstants.KEY_TRAVEL_PASS, hashMap.get(UtilsConstants.KEY_TRAVEL_PASS));
        values.put(UtilsConstants.KEY_TRAVEL_GROUP_ID, hashMap.get(UtilsConstants.KEY_TRAVEL_GROUP_ID));
        values.put(UtilsConstants.KEY_TRAVEL_ID, hashMap.get(UtilsConstants.KEY_TRAVEL_ID));
        values.put(UtilsConstants.KEY_TRAVEL_LAT, hashMap.get(UtilsConstants.KEY_TRAVEL_LAT));
        values.put(UtilsConstants.KEY_TRAVEL_LONG, hashMap.get(UtilsConstants.KEY_TRAVEL_LONG));
        values.put(UtilsConstants.KEY_TRAVEL_ADDRESS, hashMap.get(UtilsConstants.KEY_TRAVEL_ADDRESS));
        values.put(UtilsConstants.KEY_TRAVEL_DATE, hashMap.get(UtilsConstants.KEY_TRAVEL_DATE));
        values.put(UtilsConstants.KEY_TRAVEL_FROM, hashMap.get(UtilsConstants.KEY_TRAVEL_FROM));
        values.put(UtilsConstants.KEY_TRAVEL_TO, hashMap.get(UtilsConstants.KEY_TRAVEL_TO));
        values.put(UtilsConstants.KEY_TRAVEL_MODE, hashMap.get(UtilsConstants.KEY_TRAVEL_MODE));
        values.put(UtilsConstants.KEY_TRAVEL_OTHER, hashMap.get(UtilsConstants.KEY_TRAVEL_OTHER));
        values.put(UtilsConstants.KEY_TRAVEL_PRF_TIME, hashMap.get(UtilsConstants.KEY_TRAVEL_PRF_TIME));
        values.put(UtilsConstants.KEY_TRAVEL_MET_TIME, hashMap.get(UtilsConstants.KEY_TRAVEL_MET_TIME));
        values.put(UtilsConstants.KEY_TRAVEL_REMARK, hashMap.get(UtilsConstants.KEY_TRAVEL_REMARK));
        values.put(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS, hashMap.get(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS));
        values.put(UtilsConstants.KEY_TRAVEL_STATUS, hashMap.get(UtilsConstants.KEY_TRAVEL_STATUS));
        values.put(UtilsConstants.KEY_TRAVEL_SYNC_STATUS, hashMap.get(UtilsConstants.KEY_TRAVEL_SYNC_STATUS));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));

        String condition = UtilsConstants.KEY_TRAVEL_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_TRAVEL, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_TRAVEL_ID)}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_TRAVEL, values, UtilsConstants.KEY_TRAVEL_ID + " = ?", new String[]{hashMap.get(UtilsConstants.KEY_TRAVEL_ID)});
            Log.d("update Travel", hashMap.get(UtilsConstants.KEY_TRAVEL_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_TRAVEL, null, values);
            Log.d(TAG, "Travel insert : " + status);
        }
        cursor.close();
        db.close();
    }

    public LinkedHashMap<String, HashMap<String, String>> getRequestTravelList() {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_TRAVEL + " where " + UtilsConstants.KEY_TRAVEL_REQUEST_STATUS + " = '0' and " + UtilsConstants.KEY_EXPENSE_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d %H:%M:%S'," + UtilsConstants.KEY_CRATED_AT + ") DESC";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_COMPID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_USERNAME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_PASS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_GROUP_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_GROUP_ID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_LAT)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_LONG)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_DATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_DATE)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_FROM)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_TO)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_MODE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_MODE)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_OTHER, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_OTHER)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_PRF_TIME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_PRF_TIME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_MET_TIME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_MET_TIME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_REMARK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_REMARK)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_STATUS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public void placeTravelRequest(int group_id, HashMap<String, String> items) {
        Log.d(TAG, "placeTravelRequest: id :" + group_id);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS, "1");
        values.put(UtilsConstants.KEY_TRAVEL_GROUP_ID, group_id + "");
        values.put(UtilsConstants.KEY_TRAVEL_LAT, items.get(UtilsConstants.KEY_TRAVEL_LAT));
        values.put(UtilsConstants.KEY_TRAVEL_LONG, items.get(UtilsConstants.KEY_TRAVEL_LONG));
        values.put(UtilsConstants.KEY_TRAVEL_ACU, items.get(UtilsConstants.KEY_TRAVEL_ACU));
        values.put(UtilsConstants.KEY_CRATED_AT, Generic.nowDate() + "");
        String condition = UtilsConstants.KEY_TRAVEL_ID + " =?";
        try {
            db.update(UtilsConstants.TABLE_TRAVEL, values, condition, new String[]{items.get(UtilsConstants.KEY_TRAVEL_ID)});
            Log.d("Travel Status", "Successful");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Travel Error", "" + e);
        }
        db.close();
    }

    public ArrayList<String> getAllTrlGroupIds() {
        ArrayList customerList = new ArrayList();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select DISTINCT " + UtilsConstants.KEY_TRAVEL_GROUP_ID + " from  " + UtilsConstants.TABLE_TRAVEL + " where " + UtilsConstants.KEY_TRAVEL_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email")
                + "' AND " + UtilsConstants.KEY_TRAVEL_REQUEST_STATUS + " = '1'  order by STRFTIME('%Y-%m-%d %H:%M:%S'," + UtilsConstants.KEY_CRATED_AT + ") DESC";
        Log.d(TAG, "getAllTrlGroupIds : " + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String customerName = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_GROUP_ID));
            customerList.add(customerName);
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public HashMap<String, HashMap<String, String>> getAllTrlRequestsByID(String groupId) {
        HashMap<String, HashMap<String, String>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_TRAVEL + " where " + UtilsConstants.KEY_TRAVEL_REQUEST_STATUS + " = '1' and " + UtilsConstants.KEY_TRAVEL_GROUP_ID + " = '" + groupId + "' and " + UtilsConstants.KEY_TRAVEL_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_TRAVEL_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_COMPID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_USERNAME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_PASS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_GROUP_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_GROUP_ID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_LAT)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_LONG)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_DATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_DATE)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_FROM)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_TO)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_MODE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_MODE)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_OTHER, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_OTHER)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_PRF_TIME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_PRF_TIME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_MET_TIME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_MET_TIME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_REMARK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_REMARK)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_STATUS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public ArrayList<String> getAllTrlGroupIdsToUpload() {
        ArrayList customerList = new ArrayList();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select DISTINCT " + UtilsConstants.KEY_TRAVEL_GROUP_ID + " from  " + UtilsConstants.TABLE_TRAVEL + " where " + UtilsConstants.KEY_TRAVEL_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email")
                + "' AND " + UtilsConstants.KEY_TRAVEL_REQUEST_STATUS + " = '1' AND " + UtilsConstants.KEY_TRAVEL_SYNC_STATUS + " = '0'";
        Log.d(TAG, "getAllTrlGroupIds To Upload : " + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String customerName = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_GROUP_ID));
            customerList.add(customerName);
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public HashMap<String, HashMap<String, String>> getAllTrlRequestsByIDToUpload(String groupId) {
        HashMap<String, HashMap<String, String>> listItems = new HashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_TRAVEL + " where " + UtilsConstants.KEY_TRAVEL_REQUEST_STATUS + " = '1' and " + UtilsConstants.KEY_TRAVEL_GROUP_ID + " = '" + groupId + "' and " + UtilsConstants.KEY_TRAVEL_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.KEY_TRAVEL_SYNC_STATUS + " = '0'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_TRAVEL_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_COMPID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_USERNAME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_PASS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_GROUP_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_GROUP_ID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ID)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_LAT)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_LONG)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ACU)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_DATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_DATE)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_FROM)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_TO)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_MODE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_MODE)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_OTHER, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_OTHER)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_PRF_TIME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_PRF_TIME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_MET_TIME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_MET_TIME)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_REMARK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_REMARK)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_REQUEST_STATUS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_STATUS)));
            listItems1.put(UtilsConstants.KEY_TRAVEL_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_TRAVEL_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public void updateTravelStatus(String master_id, String ref, String travel_details_id) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_TRAVEL_SYNC_STATUS, "1");
        values.put(UtilsConstants.KEY_TRAVEL_STATUS, "1");
        values.put(UtilsConstants.KEY_TRAVEL_GROUP_ID, master_id);
        values.put(UtilsConstants.KEY_TRAVEL_ID, travel_details_id);
        String condition = UtilsConstants.KEY_TRAVEL_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_TRAVEL, null, condition, new String[]{ref}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_TRAVEL, values, UtilsConstants.KEY_TRAVEL_ID + "=?", new String[]{ref});
            Log.d("updateTravelStatus", "updateTravelStatus");
        }
        cursor.close();
        db.close();
    }

    public void insertAdvanceRequest(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ADVANCE_COMPID, hashMap.get(UtilsConstants.KEY_ADVANCE_COMPID));
        values.put(UtilsConstants.KEY_ADVANCE_USERNAME, hashMap.get(UtilsConstants.KEY_ADVANCE_USERNAME));
        values.put(UtilsConstants.KEY_ADVANCE_PASS, hashMap.get(UtilsConstants.KEY_ADVANCE_PASS));
        values.put(UtilsConstants.KEY_ADVANCE_ID, hashMap.get(UtilsConstants.KEY_ADVANCE_ID));
        values.put(UtilsConstants.KEY_ADVANCE_FROMDATE, hashMap.get(UtilsConstants.KEY_ADVANCE_FROMDATE));
        values.put(UtilsConstants.KEY_ADVANCE_TODATE, hashMap.get(UtilsConstants.KEY_ADVANCE_TODATE));
        values.put(UtilsConstants.KEY_ADVANCE_FROM, hashMap.get(UtilsConstants.KEY_ADVANCE_FROM));
        values.put(UtilsConstants.KEY_ADVANCE_TO, hashMap.get(UtilsConstants.KEY_ADVANCE_TO));
        values.put(UtilsConstants.KEY_ADVANCE_REASON, hashMap.get(UtilsConstants.KEY_ADVANCE_REASON));
        values.put(UtilsConstants.KEY_ADVANCE_AMOUNT, hashMap.get(UtilsConstants.KEY_ADVANCE_AMOUNT));
        values.put(UtilsConstants.KEY_ADVANCE_LAT, hashMap.get(UtilsConstants.KEY_ADVANCE_LAT));
        values.put(UtilsConstants.KEY_ADVANCE_LONG, hashMap.get(UtilsConstants.KEY_ADVANCE_LONG));
        values.put(UtilsConstants.KEY_ADVANCE_ACU, hashMap.get(UtilsConstants.KEY_ADVANCE_ACU));
        values.put(UtilsConstants.KEY_ADVANCE_ADDRESS, hashMap.get(UtilsConstants.KEY_ADVANCE_ADDRESS));
        values.put(UtilsConstants.KEY_ADVANCE_SYNC_STATUS, hashMap.get(UtilsConstants.KEY_ADVANCE_SYNC_STATUS));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));

        String condition = UtilsConstants.KEY_ADVANCE_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_ADVANCE, null, condition, new String[]{hashMap.get(UtilsConstants.KEY_ADVANCE_ID)}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_ADVANCE, values, UtilsConstants.KEY_ADVANCE_ID + " = ?", new String[]{hashMap.get(UtilsConstants.KEY_ADVANCE_ID)});
            Log.d("update Advance", hashMap.get(UtilsConstants.KEY_ADVANCE_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_ADVANCE, null, values);
            Log.d(TAG, "Advance insert : " + status);
        }
        cursor.close();
        db.close();
    }

    public LinkedHashMap<String, HashMap<String, String>> getAllAdvacnceRequests() {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_ADVANCE + " where " + UtilsConstants.KEY_TRAVEL_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d %H:%M:%S'," + UtilsConstants.KEY_CRATED_AT + ") DESC";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_ADVANCE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_COMPID)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_USERNAME)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_PASS)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_ID)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_FROMDATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_FROMDATE)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_TODATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_TODATE)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_FROM)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_TO)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_REASON, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_REASON)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_AMOUNT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_AMOUNT)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_LAT)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_LONG)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public HashMap<String, HashMap<String, String>> getAllAdvanceDataToUpload() {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_ADVANCE + " where " + UtilsConstants.KEY_ADVANCE_SYNC_STATUS + " = '0'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_COMPID)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_USERNAME)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_PASS)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_ID)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_FROMDATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_FROMDATE)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_TODATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_TODATE)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_FROM, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_FROM)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_TO, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_TO)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_REASON, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_REASON)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_AMOUNT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_AMOUNT)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_LAT)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_LONG)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_ACU, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_ACU)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_ADDRESS)));
            listItems1.put(UtilsConstants.KEY_ADVANCE_SYNC_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_SYNC_STATUS)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ADVANCE_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public void updateSyncStatusAdvance(String id, String advance_request_id) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_ADVANCE_ID, advance_request_id);
        values.put(UtilsConstants.KEY_ADVANCE_SYNC_STATUS, "1");
        String condition = UtilsConstants.KEY_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_ADVANCE, null, condition, new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_ADVANCE, values, UtilsConstants.KEY_ID + "=?", new String[]{id});
            Log.d("SyncStatusAdvance", "updateSyncStatusAdvance");
        }
        db.close();
    }

    public void removeExpense(String id) {
        SQLiteDatabase db = database.getWritableDatabase();
        String condition = UtilsConstants.KEY_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_EXPENSE, null, condition, new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            int oj = db.delete(UtilsConstants.TABLE_EXPENSE, condition, new String[]{id});
            Log.d("removeExpense", "removeExpenseFrom : " + oj + "  : " + cursor.getString(0));
        }
        cursor.close();
        db.close();
    }

    public void removeTravel(String id) {
        SQLiteDatabase db = database.getWritableDatabase();
        String condition = UtilsConstants.KEY_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_TRAVEL, null, condition, new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            int oj = db.delete(UtilsConstants.TABLE_TRAVEL, condition, new String[]{id});
            Log.d("removeTravel", "removeTravel : " + oj + "  : " + cursor.getString(0));
        }
        cursor.close();
        db.close();
    }

    public void insertDistStockRequest(HashMap<String, String> hashMap) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.DIST_COMPID, hashMap.get(UtilsConstants.DIST_COMPID));
        values.put(UtilsConstants.DIST_USERNAME, hashMap.get(UtilsConstants.DIST_USERNAME));
        values.put(UtilsConstants.DIST_PASS, hashMap.get(UtilsConstants.DIST_PASS));
        values.put(UtilsConstants.DIST_ID, hashMap.get(UtilsConstants.DIST_ID));
        values.put(UtilsConstants.DIST_NAME, hashMap.get(UtilsConstants.DIST_NAME));
        values.put(UtilsConstants.DIST_PRID, hashMap.get(UtilsConstants.DIST_PRID));
        values.put(UtilsConstants.DIST_PRNAME, hashMap.get(UtilsConstants.DIST_PRNAME));
        values.put(UtilsConstants.DIST_STOCK, hashMap.get(UtilsConstants.DIST_STOCK));
        values.put(UtilsConstants.DIST_SYNC_STATUS, hashMap.get(UtilsConstants.DIST_SYNC_STATUS));
        values.put(UtilsConstants.KEY_CRATED_AT, hashMap.get(UtilsConstants.KEY_CRATED_AT));

        Log.d(TAG, "insertDistStockRequest: " + hashMap);
        String condition = UtilsConstants.DIST_ID + " =? AND " + UtilsConstants.DIST_PRID + " =? ";
        Cursor cursor = db.query(UtilsConstants.TABLE_DIST_STOCK, null, condition, new String[]{hashMap.get(UtilsConstants.DIST_ID), hashMap.get(UtilsConstants.DIST_PRID)}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_DIST_STOCK, values, UtilsConstants.KEY_ID + " = ?", new String[]{cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID))});
            Log.d("update DistStock", hashMap.get(UtilsConstants.DIST_ID));
        } else {
            long status = db.insert(UtilsConstants.TABLE_DIST_STOCK, null, values);
            Log.d(TAG, "DistStock insert : " + status);
        }
        cursor.close();
        db.close();
    }

    public LinkedHashMap<String, HashMap<String, String>> getAllDistStockList() {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_DIST_STOCK + " where " + UtilsConstants.DIST_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
            listItems1.put(UtilsConstants.DIST_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_ID)));
            listItems1.put(UtilsConstants.DIST_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_NAME)));
            listItems1.put(UtilsConstants.DIST_PRID, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_PRID)));
            listItems1.put(UtilsConstants.DIST_PRNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_PRNAME)));
            listItems1.put(UtilsConstants.DIST_STOCK, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_STOCK)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public HashMap<String, String> getDistStockByNameAndProId(String distname, String proid) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from " + UtilsConstants.TABLE_DIST_STOCK + " where " + UtilsConstants.DIST_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.DIST_PRID + " = '" + proid + "' and " + UtilsConstants.DIST_NAME + " = '" + distname + "'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1 = new HashMap<>();
        while (cursor.moveToNext()) {
            listItems1.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
            listItems1.put(UtilsConstants.DIST_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_ID)));
            listItems1.put(UtilsConstants.DIST_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_NAME)));
            listItems1.put(UtilsConstants.DIST_PRID, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_PRID)));
            listItems1.put(UtilsConstants.DIST_PRNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_PRNAME)));
            listItems1.put(UtilsConstants.DIST_STOCK, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_STOCK)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
        }
        cursor.close();
        db.close();
        return listItems1;
    }

    public ArrayList<String> getAllDistStock() {
        ArrayList<String> catList = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + UtilsConstants.TABLE_DIST_STOCK
                + " group by " + UtilsConstants.DIST_NAME;
        final Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String categoryName = cursor.getString(cursor
                        .getColumnIndex(UtilsConstants.DIST_NAME));
                if (categoryName != null && !categoryName.isEmpty()) {
                    catList.add(categoryName);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return catList;
    }

    public LinkedHashMap<String, HashMap<String, String>> getAllDistStockListByDist(String distname) {
        LinkedHashMap<String, HashMap<String, String>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        // String query = "select * from " + UtilsConstants.TABLE_DIST_STOCK + " where " + UtilsConstants.DIST_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and "+ UtilsConstants.DIST_NAME + "='" + distname + "'";
        String query = "SELECT * from " + UtilsConstants.TABLE_DIST_STOCK + " INNER JOIN " + UtilsConstants.TABLE_PRODUCTS + " ON " + UtilsConstants.TABLE_DIST_STOCK + "." + UtilsConstants.DIST_PRID + "=" + UtilsConstants.TABLE_PRODUCTS + "." + UtilsConstants.KEY_PR_ID + " where " + UtilsConstants.DIST_USERNAME + "='" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.DIST_NAME + "='" + distname + "'";
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            listItems1.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
            listItems1.put(UtilsConstants.DIST_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_ID)));
            listItems1.put(UtilsConstants.DIST_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_ID)));
            listItems1.put(UtilsConstants.KEY_PR_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_IMGURL)));
            listItems1.put(UtilsConstants.DIST_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_NAME)));
            listItems1.put(UtilsConstants.DIST_PRID, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_PRID)));
            listItems1.put(UtilsConstants.DIST_PRNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_PRNAME)));
            listItems1.put(UtilsConstants.DIST_STOCK, cursor.getString(cursor.getColumnIndex(UtilsConstants.DIST_STOCK)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), listItems1);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public LinkedHashMap<String, HashMap<String, String>> getAllCustomers() {
        LinkedHashMap<String, HashMap<String, String>> hashMap1 = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_CUSTOMERS;
        Log.d(TAG, "getCustomersByRouteName : " + query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_CUS_DISTRIBUTOR, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_DISTRIBUTOR)));
                hashMap.put(UtilsConstants.KEY_CUS_ROUTE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ROUTE)));
                hashMap.put(UtilsConstants.KEY_CUS_STATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_STATE)));
                hashMap.put(UtilsConstants.KEY_CUS_CITY, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_CITY)));
                hashMap.put(UtilsConstants.KEY_CUS_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_IMGURL)));
                hashMap.put(UtilsConstants.KEY_CUS_MOBILE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_MOBILE)));
                hashMap.put(UtilsConstants.KEY_CUS_EMAIL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_EMAIL)));
                hashMap.put(UtilsConstants.KEY_CUS_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_CUS_BILL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_BILL)));
                hashMap.put(UtilsConstants.KEY_CUS_OUTSTANDING, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUTSTANDING)));
                hashMap.put(UtilsConstants.KEY_CUS_OUT_UPDATED, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUT_UPDATED)));
                hashMap.put(UtilsConstants.KEY_CUS_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LAT)));
                hashMap.put(UtilsConstants.KEY_CUS_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LONG)));
                hashMap.put(UtilsConstants.KEY_CUS_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_SYNCSTATUS)));
                if (!(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_BILL)).equals("0.0") && cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUTSTANDING)).equals("0.0"))) {
                    hashMap1.put(cursor.getString(2), hashMap);
                }
                Log.d(TAG, "getCustomersByDistributorsList: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7) + " " + cursor.getString(8) + " " + cursor.getString(9));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap1;
    }

    public HashMap<String, String> getOutstandingById(String id) {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_CUSTOMERS + " where " + UtilsConstants.KEY_CUS_ID + " = " + id;
        Log.d(TAG, "getCustomersByRouteName : " + query);
        HashMap<String, String> hashMap = new HashMap<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                hashMap.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
                hashMap.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
                hashMap.put(UtilsConstants.KEY_CUS_DISTRIBUTOR, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_DISTRIBUTOR)));
                hashMap.put(UtilsConstants.KEY_CUS_ROUTE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ROUTE)));
                hashMap.put(UtilsConstants.KEY_CUS_STATE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_STATE)));
                hashMap.put(UtilsConstants.KEY_CUS_CITY, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_CITY)));
                hashMap.put(UtilsConstants.KEY_CUS_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_IMGURL)));
                hashMap.put(UtilsConstants.KEY_CUS_MOBILE, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_MOBILE)));
                hashMap.put(UtilsConstants.KEY_CUS_EMAIL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_EMAIL)));
                hashMap.put(UtilsConstants.KEY_CUS_ADDRESS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ADDRESS)));
                hashMap.put(UtilsConstants.KEY_CUS_BILL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_BILL)));
                hashMap.put(UtilsConstants.KEY_CUS_OUTSTANDING, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUTSTANDING)));
                hashMap.put(UtilsConstants.KEY_CUS_OUT_UPDATED, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_OUT_UPDATED)));
                hashMap.put(UtilsConstants.KEY_CUS_LAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LAT)));
                hashMap.put(UtilsConstants.KEY_CUS_LONG, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_LONG)));
                hashMap.put(UtilsConstants.KEY_CUS_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_SYNCSTATUS)));
                Log.d(TAG, "getCustomersByDistributorsList: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7) + " " + cursor.getString(8) + " " + cursor.getString(9));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap;
    }

    public void updateCartStatusCustomer(String oldid, String newid) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CUS_ID, newid);
        String condition = UtilsConstants.KEY_CUS_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_CART, null, condition, new String[]{oldid}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_CART, values, UtilsConstants.KEY_CUS_ID + "=?", new String[]{oldid});
            Log.d("CartStatusCustomer", "updateCartStatusCustomer");
        }
        cursor.close();
        db.close();
    }

    public void updateVisitNowStatusCustomer(String oldid, String newid) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CUS_ID, newid);
        String condition = UtilsConstants.KEY_CUS_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_VISIT, null, condition, new String[]{oldid}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_VISIT, values, UtilsConstants.KEY_CUS_ID + "=?", new String[]{oldid});
            Log.d("CartStatusCustomer", "updateCartStatusCustomer");
        }
        cursor.close();
        db.close();
    }

    public void updateVisitPlanStatusCustomer(String oldid, String newid) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_CUS_ID, newid);
        String condition = UtilsConstants.KEY_CUS_ID + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_VISIT_PLAN, null, condition, new String[]{oldid}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_VISIT_PLAN, values, UtilsConstants.KEY_CUS_ID + "=?", new String[]{oldid});
            Log.d("CartStatusCustomer", "updateCartStatusCustomer");
        }
        cursor.close();
        db.close();
    }

    public void insertNotificationTracking(String msg, String msgType, String strdate) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.KEY_NOTIF_COMPID, SharedPreferencesUtil.getInstance(context).getData("compay_id"));
        values.put(UtilsConstants.KEY_NOTIF_USERNAME, SharedPreferencesUtil.getInstance(context).getData("email"));
        values.put(UtilsConstants.KEY_NOTIF_PASS, SharedPreferencesUtil.getInstance(context).getData("password"));
        values.put(UtilsConstants.KEY_NOTE_MESSAGE, msg);
        values.put(UtilsConstants.KEY_NOTE_READSTATUS, "0");
        values.put(UtilsConstants.KEY_NOTE_TYPE, msgType);
        values.put(UtilsConstants.KEY_CRATED_AT, strdate);
        String condition = UtilsConstants.KEY_NOTE_TYPE + " =? AND " + UtilsConstants.KEY_NOTE_MESSAGE + " =? ";
        Cursor cursor = db.query(UtilsConstants.TABLE_NOTIFICATION, null, condition, new String[]{msgType, msg}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_NOTIFICATION, values, condition, new String[]{msgType, msg});
            Log.d("Notification : ", "updateNotificationTracking");
        } else {
            long status = db.insert(UtilsConstants.TABLE_NOTIFICATION, null, values);
            Log.d("Notification : ", "insertNotificationTracking");
        }
        cursor.close();
        db.close();
    }

    public void updateStockSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.DIST_SYNC_STATUS, "3");
        String condition = UtilsConstants.DIST_SYNC_STATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_DIST_STOCK, null, condition, new String[]{"2"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.update(UtilsConstants.TABLE_DIST_STOCK, values, UtilsConstants.DIST_SYNC_STATUS + "=?", new String[]{"2"});
            Log.d("AttendanceSyncStatus", "updateAttendanceSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public void deleteStockSyncStatus() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilsConstants.DIST_SYNC_STATUS, "3");
        String condition = UtilsConstants.DIST_SYNC_STATUS + " =?";
        Cursor cursor = db.query(UtilsConstants.TABLE_DIST_STOCK, null, condition, new String[]{"3"}, null, null, null);
        if (cursor.moveToFirst()) {
            db.delete(UtilsConstants.TABLE_DIST_STOCK, UtilsConstants.DIST_SYNC_STATUS + "=?", new String[]{"3"});
            Log.d("deleteStockSyncStatus", "deleteStockSyncStatus");
        }
        cursor.close();
        db.close();
    }

    public ArrayList<String> getOrderedProductsBy(ArrayList<String> cusIDlist) {
        ArrayList<String> ids = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        if (cusIDlist.size() > 0) {
            for (int i = 0; i < cusIDlist.size(); i++) {
                String query = "select * from  " + UtilsConstants.TABLE_CART + " where " + UtilsConstants.KEY_CUS_ID + " = " + cusIDlist.get(i) + " and OrderFlag = '1' ";
                Log.d(TAG, "getOrderedProductsBy : " + query);
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        if (!ids.contains(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)))) {
                            ids.add(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)));
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }
        db.close();
        return ids;
    }

    public LinkedHashMap<String, HashMap<String, String>> getAllProductBilled(ArrayList<String> ids) {
        LinkedHashMap<String, HashMap<String, String>> hashMap1 = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor;
        String sort = "CAST (" + UtilsConstants.KEY_PR_MRP + " AS INTEGER) ASC";
        cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, UtilsConstants.KEY_PR_USERNAME + "=? ", new String[]{SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, sort);
        if (cursor.moveToFirst()) {
            do {
                if (ids.contains(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)))) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                    hashMap.put(UtilsConstants.KEY_PR_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_COMPID)));
                    hashMap.put(UtilsConstants.KEY_PR_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_USERNAME)));
                    hashMap.put(UtilsConstants.KEY_PR_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_PASS)));
                    hashMap.put(UtilsConstants.KEY_PR_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)));
                    hashMap.put(UtilsConstants.KEY_PR_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_NAME)));
                    hashMap.put(UtilsConstants.KEY_PR_MRP, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP)));
                    hashMap.put(UtilsConstants.KEY_PR_CAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_CAT)));
                    hashMap.put(UtilsConstants.KEY_PR_SUB_CAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_SUB_CAT)));
                    hashMap.put(UtilsConstants.KEY_PR_BRAND, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_BRAND)));
                    hashMap.put(UtilsConstants.KEY_PR_PACK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_PACK)));
                    hashMap.put(UtilsConstants.KEY_PR_DISC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_DISC)));
                    hashMap.put(UtilsConstants.KEY_PR_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_IMGURL)));
                    hashMap.put(UtilsConstants.KEY_PR_FAV, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_FAV)));
                    hashMap.put(UtilsConstants.KEY_PR_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_SYNCSTATUS)));
                    hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), hashMap);
                }
                Log.d("TAG", "getAllProduct: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap1;
    }

    public LinkedHashMap<String, HashMap<String, String>> getAllProductunbilled(ArrayList<String> ids) {
        LinkedHashMap<String, HashMap<String, String>> hashMap1 = new LinkedHashMap<>();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor;
        String sort = "CAST (" + UtilsConstants.KEY_PR_MRP + " AS INTEGER) ASC";
        cursor = db.query(UtilsConstants.TABLE_PRODUCTS, null, UtilsConstants.KEY_PR_USERNAME + "=? ", new String[]{SharedPreferencesUtil.getInstance(context).getData("email")}, null, null, sort);
        if (cursor.moveToFirst()) {
            do {
                if (!ids.contains(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)))) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(UtilsConstants.KEY_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)));
                    hashMap.put(UtilsConstants.KEY_PR_COMPID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_COMPID)));
                    hashMap.put(UtilsConstants.KEY_PR_USERNAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_USERNAME)));
                    hashMap.put(UtilsConstants.KEY_PR_PASS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_PASS)));
                    hashMap.put(UtilsConstants.KEY_PR_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_ID)));
                    hashMap.put(UtilsConstants.KEY_PR_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_NAME)));
                    hashMap.put(UtilsConstants.KEY_PR_MRP, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP)));
                    hashMap.put(UtilsConstants.KEY_PR_CAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_CAT)));
                    hashMap.put(UtilsConstants.KEY_PR_SUB_CAT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_SUB_CAT)));
                    hashMap.put(UtilsConstants.KEY_PR_BRAND, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_BRAND)));
                    hashMap.put(UtilsConstants.KEY_PR_PACK, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_PACK)));
                    hashMap.put(UtilsConstants.KEY_PR_DISC, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_DISC)));
                    hashMap.put(UtilsConstants.KEY_PR_IMGURL, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_IMGURL)));
                    hashMap.put(UtilsConstants.KEY_PR_FAV, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_FAV)));
                    hashMap.put(UtilsConstants.KEY_PR_SYNCSTATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_SYNCSTATUS)));
                    hashMap1.put(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_ID)), hashMap);
                }
                Log.d("TAG", "getAllProduct: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hashMap1;
    }

    public ArrayList<String> getAllCustomersNames() {
        SQLiteDatabase db = database.getReadableDatabase();
        String query = "select * from  " + UtilsConstants.TABLE_CUSTOMERS;
        Log.d(TAG, "getCustomersByRouteName : " + query);
        ArrayList<String> names = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return names;
    }


    public LinkedHashMap<String, ArrayList<HashMap<String, String>>> getOrderIDsToShow(String custId) {
        LinkedHashMap<String, ArrayList<HashMap<String, String>>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from CartInfo where " + UtilsConstants.KEY_CUS_ID + " = '" + custId + "' and OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d %HH:%mm:%ss'," + UtilsConstants.KEY_CRATED_AT + ") DESC";
        Log.d(TAG, "Cart Count: Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        ArrayList<HashMap<String, String>> arrMap;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            arrMap = new ArrayList<>();
            String strOrderId = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            String strCartQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
            String strMRP = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP));
            listItems1.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems1.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            listItems1.put(UtilsConstants.KEY_CART_QTY, strCartQty);
            listItems1.put(UtilsConstants.KEY_PR_MRP, strMRP);
            String strTotalAmt = "0";
            if (strMRP != null && strCartQty != null) {
                if (Double.parseDouble(strMRP) > 0) {
                    strTotalAmt = "" + (Double.parseDouble(strMRP) * Integer.parseInt(strCartQty));
                }
            }
            Log.d(TAG, "getOrderIDsToShow: " + strTotalAmt);
            BigDecimal bd1 = new BigDecimal(strTotalAmt);
            long val1 = bd1.longValue();
            listItems1.put("ProductAmt", val1 + "");
            listItems1.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            if (listItems.containsKey(strOrderId)) {
                arrMap = listItems.get(strOrderId);
                arrMap.add(listItems1);
            } else {
                arrMap.add(listItems1);
            }
            listItems.put(strOrderId, arrMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> getOrderIDsToShowAll() {
        LinkedHashMap<String, ArrayList<HashMap<String, String>>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from CartInfo where OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' order by STRFTIME('%Y-%m-%d %HH:%mm:%ss'," + UtilsConstants.KEY_CRATED_AT + ") DESC";
        Log.d(TAG, "Cart Count: Upload" + query);
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        ArrayList<HashMap<String, String>> arrMap;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            arrMap = new ArrayList<>();
            String strOrderId = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            String strCartQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
            String strMRP = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP));
            listItems1.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems1.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            listItems1.put(UtilsConstants.KEY_CART_QTY, strCartQty);
            listItems1.put(UtilsConstants.KEY_PR_MRP, strMRP);
            String strTotalAmt = "0";
            if (strMRP != null && strCartQty != null) {
                if (Double.parseDouble(strMRP) > 0) {
                    strTotalAmt = "" + (Double.parseDouble(strMRP) * Integer.parseInt(strCartQty));
                }
            }
            Log.d(TAG, "getOrderIDsToShow: " + strTotalAmt);
            BigDecimal bd1 = new BigDecimal(strTotalAmt);
            long val1 = bd1.longValue();
            listItems1.put("ProductAmt", val1 + "");
            listItems1.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            if (listItems.containsKey(strOrderId)) {
                arrMap = listItems.get(strOrderId);
                arrMap.add(listItems1);
            } else {
                arrMap.add(listItems1);
            }
            listItems.put(strOrderId, arrMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public LinkedHashMap<String, ArrayList<HashMap<String, String>>> getOrderIDsToShowGrowth(String cusID, String date) {
        LinkedHashMap<String, ArrayList<HashMap<String, String>>> listItems = new LinkedHashMap<>();
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "select * from CartInfo where OrderFlag = '1' and " + UtilsConstants.KEY_CART_USERNAME + " = '" + SharedPreferencesUtil.getInstance(context).getData("email") + "' and " + UtilsConstants.KEY_CUS_ID + " = '" + cusID + "' and STRFTIME('%Y-%m'," + UtilsConstants.KEY_CRATED_AT + ") = '" + date + "'";
        if (cusID.equals("239")){
            Log.d(TAG, "Cart Count: Upload" + query);
        }
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> listItems1;
        ArrayList<HashMap<String, String>> arrMap;
        while (cursor.moveToNext()) {
            listItems1 = new HashMap<>();
            arrMap = new ArrayList<>();
            String strOrderId = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_ORDERID));
            String strCartQty = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_QTY));
            String strMRP = cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_PR_MRP));
            listItems1.put(UtilsConstants.KEY_CUS_ID, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_ID)));
            listItems1.put(UtilsConstants.KEY_CRATED_AT, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CRATED_AT)));
            listItems1.put(UtilsConstants.KEY_CUS_NAME, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CUS_NAME)));
            listItems1.put(UtilsConstants.KEY_CART_QTY, strCartQty);
            listItems1.put(UtilsConstants.KEY_PR_MRP, strMRP);
            String strTotalAmt = "0";
            if (strMRP != null && strCartQty != null) {
                if (Double.parseDouble(strMRP) > 0) {
                    strTotalAmt = "" + (Double.parseDouble(strMRP) * Integer.parseInt(strCartQty));
                }
            }
            listItems1.put("ProductAmt", strTotalAmt);
            listItems1.put(UtilsConstants.KEY_CART_STATUS, cursor.getString(cursor.getColumnIndex(UtilsConstants.KEY_CART_STATUS)));
            if (listItems.containsKey(strOrderId)) {
                arrMap = listItems.get(strOrderId);
                arrMap.add(listItems1);
            } else {
                arrMap.add(listItems1);
            }
            listItems.put(strOrderId, arrMap);
        }
        cursor.close();
        db.close();
        return listItems;
    }*/
}
