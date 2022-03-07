package com.allinone.smartocity.customer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.allinone.smartocity.customer.utility.UtilsConstants;


/**
 * Created by admin on 7/18/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "SmartoCityRetailDB";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_PRODUCTS + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_PR_COMPID + " TEXT,"
                + UtilsConstants.KEY_PR_USERNAME + " TEXT," + UtilsConstants.KEY_PR_PASS + " TEXT,"
                + UtilsConstants.KEY_PR_ID + " TEXT," + UtilsConstants.KEY_PR_NAME + " TEXT,"
                + UtilsConstants.KEY_PR_MRP + " TEXT," + UtilsConstants.KEY_PR_CAT + " TEXT,"
                + UtilsConstants.KEY_PR_SUB_CAT + " TEXT," + UtilsConstants.KEY_PR_BRAND + " TEXT,"
                + UtilsConstants.KEY_PR_PACK + " TEXT," + UtilsConstants.KEY_PR_DISC + " TEXT,"
                + UtilsConstants.KEY_PR_IMGURL + " TEXT," + UtilsConstants.KEY_PR_FAV + " TEXT,"
                + UtilsConstants.KEY_PR_SYNCSTATUS + " TEXT DEFAULT '0'" + ")";

        String CREATE_CUSTOMERS_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_CUSTOMERS + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_CUS_ID + " TEXT," + UtilsConstants.KEY_CUS_NAME + " TEXT,"
                + UtilsConstants.KEY_CUS_DISTRIBUTOR + " TEXT," + UtilsConstants.KEY_CUS_ROUTE + " TEXT,"
                + UtilsConstants.KEY_CUS_STATE + " TEXT," + UtilsConstants.KEY_CUS_CITY + " TEXT," + UtilsConstants.KEY_CUS_IMGURL + " TEXT,"
                + UtilsConstants.KEY_CUS_BILL + " TEXT," + UtilsConstants.KEY_CUS_OUTSTANDING + " TEXT," + UtilsConstants.KEY_CUS_OUT_RECORDS + " TEXT," + UtilsConstants.KEY_CUS_OUT_UPDATED + " TEXT,"
                + UtilsConstants.KEY_CUS_MOBILE + " TEXT," + UtilsConstants.KEY_CUS_EMAIL + " TEXT," + UtilsConstants.KEY_CUS_ADDRESS + " TEXT,"
                + UtilsConstants.KEY_CUS_LAT + " TEXT," + UtilsConstants.KEY_CUS_LONG + " TEXT," + UtilsConstants.KEY_CUS_ACU + " TEXT,"
                + UtilsConstants.KEY_CUS_SYNCSTATUS + " TEXT DEFAULT '0'," + UtilsConstants.KEY_CRATED_AT + " TEXT" + ")";

        String CREATE_LOCATION_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_LOCATION + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_LOC_COMPID + " TEXT,"
                + UtilsConstants.KEY_LOC_USERNAME + " TEXT," + UtilsConstants.KEY_LOC_PASS + " TEXT,"
                + UtilsConstants.KEY_LOC_LAT + " TEXT," + UtilsConstants.KEY_LOC_LONG + " TEXT," + UtilsConstants.KEY_LOC_ACU + " TEXT,"
                + UtilsConstants.KEY_LOC_ADDRESS + " TEXT," + UtilsConstants.KEY_CRATED_AT + " TEXT,"
                + UtilsConstants.KEY_LOC_SYNCSTATUS + " TEXT DEFAULT '0'" + ")";

        String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_ATTENDANCE + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_ATTEND_COMPID + " TEXT,"
                + UtilsConstants.KEY_ATTEND_USERNAME + " TEXT," + UtilsConstants.KEY_ATTEND_PASS + " TEXT,"
                + UtilsConstants.KEY_ATTEND_LAT + " TEXT," + UtilsConstants.KEY_ATTEND_LONG + " TEXT," + UtilsConstants.KEY_ATTEND_ACU + " TEXT,"
                + UtilsConstants.KEY_ATTEND_ADDRESS + " TEXT," + UtilsConstants.KEY_ATTEND_TYPE + " TEXT,"
                + UtilsConstants.KEY_ATTEND_SYNCSTATUS + " TEXT DEFAULT '0'," + UtilsConstants.KEY_CRATED_AT + " TEXT" + ")";

        String CREATE_LEAVE_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_LEAVE + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_LEAVE_COMPID + " TEXT,"
                + UtilsConstants.KEY_LEAVE_USERNAME + " TEXT," + UtilsConstants.KEY_LEAVE_PASS + " TEXT,"
                + UtilsConstants.KEY_LEAVE_TITLE + " TEXT," + UtilsConstants.KEY_LEAVE_FROM + " TEXT,"
                + UtilsConstants.KEY_LEAVE_TO + " TEXT," + UtilsConstants.KEY_LEAVE_LAT + " TEXT," + UtilsConstants.KEY_LEAVE_ACU + " TEXT,"
                + UtilsConstants.KEY_LEAVE_LONG + " TEXT," + UtilsConstants.KEY_ADDRESS + " TEXT ," + UtilsConstants.KEY_LEAVE_STATUS + " TEXT,"
                + UtilsConstants.KEY_LEAVE_SYNCSTATUS + " TEXT DEFAULT '0'," + UtilsConstants.KEY_CRATED_AT + " TEXT" + ")";

        String CREATE_DISTRIBUTOR_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_DISTRIBUTOR + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_DIST_ID
                + " TEXT," + UtilsConstants.KEY_DIST_NAME + " TEXT," + UtilsConstants.KEY_DIST_SYNCSTATUS + " TEXT DEFAULT '0', " + UtilsConstants.KEY_DIST_COMPID + " TEXT,"
                + UtilsConstants.KEY_DIST_USERNAME + " TEXT," + UtilsConstants.KEY_DIST_PASS + " TEXT" + ")";

        String CREATE_ROUTE_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_ROUTE + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_ROUTE_ID + " TEXT,"
                + UtilsConstants.KEY_ROUTE_NAME + " TEXT," + UtilsConstants.KEY_ROUTE_DISTID + " TEXT," + UtilsConstants.KEY_ROUTE_DISTNAME + " TEXT,"
                + UtilsConstants.KEY_ROUTE_SELECTION + " TEXT," + UtilsConstants.KEY_ROUTE_SYNCSTATUS + " TEXT DEFAULT '0'," + UtilsConstants.KEY_ROUTE_COMPID + " TEXT,"
                + UtilsConstants.KEY_ROUTE_USERNAME + " TEXT," + UtilsConstants.KEY_ROUTE_PASS + " TEXT" + ")";

        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_NOTIFICATION + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_NOTIF_COMPID + " TEXT,"
                + UtilsConstants.KEY_NOTIF_USERNAME + " TEXT," + UtilsConstants.KEY_NOTIF_PASS + " TEXT,"
                + UtilsConstants.KEY_NOTE_MESSAGE + " TEXT," + UtilsConstants.KEY_NOTE_READSTATUS + " TEXT,"
                + UtilsConstants.KEY_NOTE_TYPE + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT" + ")";

        String CREATE_LOGS_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_LOGS + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_LOG_LOG + " TEXT,"
                + UtilsConstants.KEY_LOG_TYPE + " TEXT" + ")";

        String CREATE_CART_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_CART + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_PR_ID + " TEXT,"
                + UtilsConstants.KEY_CUS_ID + " TEXT, " + UtilsConstants.KEY_CUS_NAME + " TEXT, " + UtilsConstants.KEY_CART_QTY + " TEXT ," + UtilsConstants.KEY_PR_MRP + " TEXT, " + UtilsConstants.KEY_CART_COMPID + " TEXT, "
                + UtilsConstants.KEY_CART_USERNAME + " TEXT, " + UtilsConstants.KEY_CART_PASS + " TEXT ," + UtilsConstants.KEY_CART_FLAG + " TEXT,"
                + UtilsConstants.KEY_CART_ORDERID + " TEXT, " + UtilsConstants.KEY_CART_SIGNATURE + " TEXT," + UtilsConstants.KEY_CART_STATUS + " TEXT, "
                + UtilsConstants.KEY_CRATED_AT + " TEXT ," + UtilsConstants.KEY_CART_STOCK + " TEXT DEFAULT '1', "
                + UtilsConstants.KEY_LAT + " TEXT, " + UtilsConstants.KEY_LONG + " TEXT, " + UtilsConstants.KEY_CART_ACU + " TEXT," + UtilsConstants.KEY_ADDRESS + " TEXT " + ")";

        String CREATE_VISIT_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_VISIT + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_VISIT_COMPID + " TEXT,"
                + UtilsConstants.KEY_VISIT_USERNAME + " TEXT, " + UtilsConstants.KEY_VISIT_PASS + " TEXT ," + UtilsConstants.KEY_VISIT_ID + " TEXT, " + UtilsConstants.KEY_CUS_ID + " TEXT, "
                + UtilsConstants.KEY_CUS_NAME + " TEXT, " + UtilsConstants.KEY_VISIT_DESC + " TEXT," + UtilsConstants.KEY_VISIT_REMARK + " TEXT,"
                + UtilsConstants.KEY_VISIT_COMP_INFO + " TEXT," + UtilsConstants.KEY_VISIT_IMGPATH + " TEXT," + UtilsConstants.KEY_CRATED_AT + " TEXT,"
                + UtilsConstants.KEY_VISIT_PLANDATE + " TEXT, " + UtilsConstants.KEY_VISIT_CRE_DATE + " TEXT, " + UtilsConstants.KEY_VISIT_STATUS + " TEXT, "
                + UtilsConstants.KEY_LAT + " TEXT, " + UtilsConstants.KEY_LONG + " TEXT, " + UtilsConstants.KEY_VISIT_ACU + " TEXT," + UtilsConstants.KEY_ADDRESS + " TEXT " + ")";

        String CREATE_VISITPLAN_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_VISIT_PLAN + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_VISIT_COMPID + " TEXT,"
                + UtilsConstants.KEY_VISIT_USERNAME + " TEXT, " + UtilsConstants.KEY_VISIT_PASS + " TEXT ," + UtilsConstants.KEY_VISIT_ID + " TEXT, " + UtilsConstants.KEY_CUS_ID + " TEXT, "
                + UtilsConstants.KEY_CUS_NAME + " TEXT, " + UtilsConstants.KEY_VISIT_DESC + " TEXT," + UtilsConstants.KEY_CRATED_AT + " TEXT,"
                + UtilsConstants.KEY_VISIT_PLANDATE + " TEXT, " + UtilsConstants.KEY_VISIT_STATUS + " TEXT ,"
                + UtilsConstants.KEY_LAT + " TEXT ," + UtilsConstants.KEY_LONG + " TEXT ," + UtilsConstants.KEY_VISIT_ACU + " TEXT," + UtilsConstants.KEY_ADDRESS + " TEXT " + ")";

        String CREATE_EXPENSE_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_EXPENSE + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_EXPENSE_COMPID + " TEXT," + UtilsConstants.KEY_EXPENSE_USERNAME + " TEXT, " + UtilsConstants.KEY_EXPENSE_PASS + " TEXT ,"
                + UtilsConstants.KEY_EXPENSE_LAT + " TEXT, " + UtilsConstants.KEY_EXPENSE_LONG + " TEXT, " + UtilsConstants.KEY_EXPENSE_ACU + " TEXT," + UtilsConstants.KEY_ADDRESS + " TEXT ," + UtilsConstants.KEY_EXPENSE_GROUP_ID + " TEXT, "
                + UtilsConstants.KEY_EXPENSE_ID + " TEXT," + UtilsConstants.KEY_EXPENSE_ADDRESS + " TEXT, " + UtilsConstants.KEY_EXPENSE_TYPE + " TEXT," + UtilsConstants.KEY_EXPENSE_CLAIM_DATE + " TEXT,"
                + UtilsConstants.KEY_EXPENSE_FROM + " TEXT, " + UtilsConstants.KEY_EXPENSE_TO + " TEXT ," + UtilsConstants.KEY_EXPENSE_OTHER + " TEXT, " + UtilsConstants.KEY_EXPENSE_MODE + " TEXT ,"
                + UtilsConstants.KEY_EXPENSE_KM + " TEXT, " + UtilsConstants.KEY_EXPENSE_RATE + " TEXT ," + UtilsConstants.KEY_EXPENSE_AMOUNT + " TEXT ," + UtilsConstants.KEY_EXPENSE_IMG + " TEXT ,"
                + UtilsConstants.KEY_EXPENSE_REQUEST_STATUS + " TEXT ," + UtilsConstants.KEY_EXPENSE_STATUS + " TEXT ," + UtilsConstants.KEY_EXPENSE_SYNC_STATUS + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT " + ")";

        String CREATE_TRAVEL_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_TRAVEL + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_TRAVEL_COMPID + " TEXT," + UtilsConstants.KEY_TRAVEL_USERNAME + " TEXT, " + UtilsConstants.KEY_TRAVEL_PASS + " TEXT ,"
                + UtilsConstants.KEY_TRAVEL_LAT + " TEXT, " + UtilsConstants.KEY_TRAVEL_LONG + " TEXT, " + UtilsConstants.KEY_TRAVEL_ACU + " TEXT," + UtilsConstants.KEY_TRAVEL_ADDRESS + " TEXT ," + UtilsConstants.KEY_TRAVEL_GROUP_ID + " TEXT, "
                + UtilsConstants.KEY_TRAVEL_ID + " TEXT," + UtilsConstants.KEY_TRAVEL_DATE + " TEXT," + UtilsConstants.KEY_TRAVEL_PRF_TIME + " TEXT," + UtilsConstants.KEY_TRAVEL_MET_TIME + " TEXT," + UtilsConstants.KEY_TRAVEL_REMARK + " TEXT,"
                + UtilsConstants.KEY_TRAVEL_FROM + " TEXT, " + UtilsConstants.KEY_TRAVEL_TO + " TEXT ," + UtilsConstants.KEY_TRAVEL_OTHER + " TEXT, " + UtilsConstants.KEY_TRAVEL_MODE + " TEXT ,"
                + UtilsConstants.KEY_TRAVEL_REQUEST_STATUS + " TEXT ," + UtilsConstants.KEY_TRAVEL_STATUS + " TEXT ," + UtilsConstants.KEY_TRAVEL_SYNC_STATUS + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT " + ")";

        String CREATE_ADVANCE_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_ADVANCE + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_ADVANCE_COMPID + " TEXT," + UtilsConstants.KEY_ADVANCE_USERNAME + " TEXT, "
                + UtilsConstants.KEY_ADVANCE_PASS + " TEXT , " + UtilsConstants.KEY_ADVANCE_ID + " TEXT, " + UtilsConstants.KEY_ADVANCE_FROMDATE + " TEXT, "
                + UtilsConstants.KEY_ADVANCE_TODATE + " TEXT, " + UtilsConstants.KEY_ADVANCE_FROM + " TEXT, " + UtilsConstants.KEY_ADVANCE_TO + " TEXT, "
                + UtilsConstants.KEY_ADVANCE_REASON + " TEXT, " + UtilsConstants.KEY_ADVANCE_AMOUNT + " TEXT, " + UtilsConstants.KEY_ADVANCE_LAT + " TEXT, "
                + UtilsConstants.KEY_ADVANCE_LONG + " TEXT, " + UtilsConstants.KEY_ADVANCE_ACU + " TEXT," + UtilsConstants.KEY_ADVANCE_ADDRESS + " TEXT, "
                + UtilsConstants.KEY_ADVANCE_SYNC_STATUS + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT " + ")";

        String CREATE_DISTSTOCK_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_DIST_STOCK + "("
                + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.DIST_COMPID + " TEXT," + UtilsConstants.DIST_USERNAME + " TEXT, "
                + UtilsConstants.DIST_PASS + " TEXT , " + UtilsConstants.DIST_ID + " TEXT, " + UtilsConstants.DIST_NAME + " TEXT, "
                + UtilsConstants.DIST_PRID + " TEXT, " + UtilsConstants.DIST_PRNAME + " TEXT, " + UtilsConstants.DIST_STOCK + " TEXT, "
                + UtilsConstants.DIST_SYNC_STATUS + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT " + ")";

        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_CUSTOMERS_TABLE);
        db.execSQL(CREATE_LOCATION_TABLE);
        db.execSQL(CREATE_ATTENDANCE_TABLE);
        db.execSQL(CREATE_LEAVE_TABLE);
        db.execSQL(CREATE_DISTRIBUTOR_TABLE);
        db.execSQL(CREATE_ROUTE_TABLE);
        db.execSQL(CREATE_NOTIFICATION_TABLE);
        db.execSQL(CREATE_LOGS_TABLE);
        db.execSQL(CREATE_CART_TABLE);
        db.execSQL(CREATE_VISIT_TABLE);
        db.execSQL(CREATE_VISITPLAN_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_TRAVEL_TABLE);
        db.execSQL(CREATE_ADVANCE_TABLE);
        db.execSQL(CREATE_DISTSTOCK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            String ALTER_CARTTABLE = "ALTER TABLE " + UtilsConstants.TABLE_CART
                    + " ADD COLUMN " + UtilsConstants.KEY_CRATED_AT + " TEXT ";
            db.execSQL(ALTER_CARTTABLE);
            String ALTER_CARTTABLE1 = "ALTER TABLE " + UtilsConstants.TABLE_CART
                    + " ADD COLUMN " + UtilsConstants.KEY_CART_STOCK + " TEXT DEFAULT '1'";
            db.execSQL(ALTER_CARTTABLE1);
        }

        if (oldVersion < 3) {
            String ALTER_CUSTOMER_TABALE = "ALTER TABLE " + UtilsConstants.TABLE_CUSTOMERS
                    + " ADD COLUMN " + UtilsConstants.KEY_CRATED_AT + " TEXT ";
            db.execSQL(ALTER_CUSTOMER_TABALE);
            String ALTER_ROUTE_TABALE1 = "ALTER TABLE " + UtilsConstants.TABLE_ROUTE
                    + " ADD COLUMN " + UtilsConstants.KEY_ROUTE_COMPID + " TEXT ";
            db.execSQL(ALTER_ROUTE_TABALE1);
            String ALTER_ROUTE_TABALE2 = "ALTER TABLE " + UtilsConstants.TABLE_ROUTE
                    + " ADD COLUMN " + UtilsConstants.KEY_ROUTE_USERNAME + " TEXT ";
            db.execSQL(ALTER_ROUTE_TABALE2);
            String ALTER_ROUTE_TABALE3 = "ALTER TABLE " + UtilsConstants.TABLE_ROUTE
                    + " ADD COLUMN " + UtilsConstants.KEY_ROUTE_PASS + " TEXT ";
            db.execSQL(ALTER_ROUTE_TABALE3);
        }

        if (oldVersion < 6) {
            String CREATE_EXPENSE_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_EXPENSE + "("
                    + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_EXPENSE_COMPID + " TEXT," + UtilsConstants.KEY_EXPENSE_USERNAME + " TEXT, " + UtilsConstants.KEY_EXPENSE_PASS + " TEXT ,"
                    + UtilsConstants.KEY_EXPENSE_LAT + " TEXT, " + UtilsConstants.KEY_EXPENSE_LONG + " TEXT, " + UtilsConstants.KEY_ADDRESS + " TEXT ," + UtilsConstants.KEY_EXPENSE_GROUP_ID + " TEXT, "
                    + UtilsConstants.KEY_EXPENSE_ID + " TEXT," + UtilsConstants.KEY_EXPENSE_ADDRESS + " TEXT, " + UtilsConstants.KEY_EXPENSE_TYPE + " TEXT," + UtilsConstants.KEY_EXPENSE_CLAIM_DATE + " TEXT,"
                    + UtilsConstants.KEY_EXPENSE_FROM + " TEXT, " + UtilsConstants.KEY_EXPENSE_TO + " TEXT ," + UtilsConstants.KEY_EXPENSE_OTHER + " TEXT, " + UtilsConstants.KEY_EXPENSE_MODE + " TEXT ,"
                    + UtilsConstants.KEY_EXPENSE_KM + " TEXT, " + UtilsConstants.KEY_EXPENSE_RATE + " TEXT ," + UtilsConstants.KEY_EXPENSE_AMOUNT + " TEXT ," + UtilsConstants.KEY_EXPENSE_IMG + " TEXT ,"
                    + UtilsConstants.KEY_EXPENSE_REQUEST_STATUS + " TEXT ," + UtilsConstants.KEY_EXPENSE_STATUS + " TEXT ," + UtilsConstants.KEY_EXPENSE_SYNC_STATUS + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT " + ")";

            String CREATE_TRAVEL_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_TRAVEL + "("
                    + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_TRAVEL_COMPID + " TEXT," + UtilsConstants.KEY_TRAVEL_USERNAME + " TEXT, " + UtilsConstants.KEY_TRAVEL_PASS + " TEXT ,"
                    + UtilsConstants.KEY_TRAVEL_LAT + " TEXT, " + UtilsConstants.KEY_TRAVEL_LONG + " TEXT, " + UtilsConstants.KEY_TRAVEL_ADDRESS + " TEXT ," + UtilsConstants.KEY_TRAVEL_GROUP_ID + " TEXT, "
                    + UtilsConstants.KEY_TRAVEL_ID + " TEXT," + UtilsConstants.KEY_TRAVEL_DATE + " TEXT," + UtilsConstants.KEY_TRAVEL_PRF_TIME + " TEXT," + UtilsConstants.KEY_TRAVEL_MET_TIME + " TEXT," + UtilsConstants.KEY_TRAVEL_REMARK + " TEXT,"
                    + UtilsConstants.KEY_TRAVEL_FROM + " TEXT, " + UtilsConstants.KEY_TRAVEL_TO + " TEXT ," + UtilsConstants.KEY_TRAVEL_OTHER + " TEXT, " + UtilsConstants.KEY_TRAVEL_MODE + " TEXT ,"
                    + UtilsConstants.KEY_TRAVEL_REQUEST_STATUS + " TEXT ," + UtilsConstants.KEY_TRAVEL_STATUS + " TEXT ," + UtilsConstants.KEY_TRAVEL_SYNC_STATUS + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT " + ")";

            String CREATE_ADVANCE_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_ADVANCE + "("
                    + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.KEY_ADVANCE_COMPID + " TEXT," + UtilsConstants.KEY_ADVANCE_USERNAME + " TEXT, "
                    + UtilsConstants.KEY_ADVANCE_PASS + " TEXT , " + UtilsConstants.KEY_ADVANCE_ID + " TEXT, " + UtilsConstants.KEY_ADVANCE_FROMDATE + " TEXT, "
                    + UtilsConstants.KEY_ADVANCE_TODATE + " TEXT, " + UtilsConstants.KEY_ADVANCE_FROM + " TEXT, " + UtilsConstants.KEY_ADVANCE_TO + " TEXT, "
                    + UtilsConstants.KEY_ADVANCE_REASON + " TEXT, " + UtilsConstants.KEY_ADVANCE_AMOUNT + " TEXT, " + UtilsConstants.KEY_ADVANCE_LAT + " TEXT, "
                    + UtilsConstants.KEY_ADVANCE_LONG + " TEXT, " + UtilsConstants.KEY_ADVANCE_ADDRESS + " TEXT, "
                    + UtilsConstants.KEY_ADVANCE_SYNC_STATUS + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT " + ")";

            db.execSQL(CREATE_EXPENSE_TABLE);
            db.execSQL(CREATE_TRAVEL_TABLE);
            db.execSQL(CREATE_ADVANCE_TABLE);
        }

        if (oldVersion < 7) {
            String CREATE_DISTSTOCK_TABLE = "CREATE TABLE " + UtilsConstants.TABLE_DIST_STOCK + "("
                    + UtilsConstants.KEY_ID + " INTEGER PRIMARY KEY," + UtilsConstants.DIST_COMPID + " TEXT," + UtilsConstants.DIST_USERNAME + " TEXT, "
                    + UtilsConstants.DIST_PASS + " TEXT , " + UtilsConstants.DIST_ID + " TEXT, " + UtilsConstants.DIST_NAME + " TEXT, "
                    + UtilsConstants.DIST_PRID + " TEXT, " + UtilsConstants.DIST_PRNAME + " TEXT, " + UtilsConstants.DIST_STOCK + " TEXT, "
                    + UtilsConstants.DIST_SYNC_STATUS + " TEXT ," + UtilsConstants.KEY_CRATED_AT + " TEXT " + ")";

            db.execSQL(CREATE_DISTSTOCK_TABLE);

            String ALTER_CARTTABLE = "ALTER TABLE " + UtilsConstants.TABLE_CUSTOMERS
                    + " ADD COLUMN " + UtilsConstants.KEY_CUS_BILL + " TEXT ";
            db.execSQL(ALTER_CARTTABLE);
            String ALTER_CARTTABLE1 = "ALTER TABLE " + UtilsConstants.TABLE_CUSTOMERS
                    + " ADD COLUMN " + UtilsConstants.KEY_CUS_OUTSTANDING + " TEXT ";
            db.execSQL(ALTER_CARTTABLE1);
            String ALTER_CARTTABLE2 = "ALTER TABLE " + UtilsConstants.TABLE_CUSTOMERS
                    + " ADD COLUMN " + UtilsConstants.KEY_CUS_OUT_UPDATED + " TEXT ";
            db.execSQL(ALTER_CARTTABLE2);
        }

        if (oldVersion < 8) {
            String ALTER_CARTTABLE = "ALTER TABLE " + UtilsConstants.TABLE_PRODUCTS
                    + " ADD COLUMN " + UtilsConstants.KEY_PR_COMPID + " TEXT ";
            db.execSQL(ALTER_CARTTABLE);
            String ALTER_CARTTABLE1 = "ALTER TABLE " + UtilsConstants.TABLE_PRODUCTS
                    + " ADD COLUMN " + UtilsConstants.KEY_PR_USERNAME + " TEXT ";
            db.execSQL(ALTER_CARTTABLE1);
            String ALTER_CARTTABLE2 = "ALTER TABLE " + UtilsConstants.TABLE_PRODUCTS
                    + " ADD COLUMN " + UtilsConstants.KEY_PR_PASS + " TEXT ";
            db.execSQL(ALTER_CARTTABLE2);
        }

        if (oldVersion < 9) {

            String ALTER_TABLE1 = "ALTER TABLE " + UtilsConstants.TABLE_CUSTOMERS
                    + " ADD COLUMN " + UtilsConstants.KEY_CUS_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE1);

            String ALTER_TABLE2 = "ALTER TABLE " + UtilsConstants.TABLE_LOCATION
                    + " ADD COLUMN " + UtilsConstants.KEY_LOC_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE2);

            String ALTER_TABLE3 = "ALTER TABLE " + UtilsConstants.TABLE_ATTENDANCE
                    + " ADD COLUMN " + UtilsConstants.KEY_ATTEND_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE3);

            String ALTER_TABLE4 = "ALTER TABLE " + UtilsConstants.TABLE_LEAVE
                    + " ADD COLUMN " + UtilsConstants.KEY_LEAVE_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE4);

            String ALTER_TABLE5 = "ALTER TABLE " + UtilsConstants.TABLE_CART
                    + " ADD COLUMN " + UtilsConstants.KEY_CART_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE5);

            String ALTER_TABLE6 = "ALTER TABLE " + UtilsConstants.TABLE_VISIT
                    + " ADD COLUMN " + UtilsConstants.KEY_VISIT_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE6);

            String ALTER_TABLE7 = "ALTER TABLE " + UtilsConstants.TABLE_VISIT_PLAN
                    + " ADD COLUMN " + UtilsConstants.KEY_VISIT_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE7);

            String ALTER_TABLE8 = "ALTER TABLE " + UtilsConstants.TABLE_EXPENSE
                    + " ADD COLUMN " + UtilsConstants.KEY_EXPENSE_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE8);

            String ALTER_TABLE9 = "ALTER TABLE " + UtilsConstants.TABLE_TRAVEL
                    + " ADD COLUMN " + UtilsConstants.KEY_TRAVEL_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE9);

            String ALTER_TABLE10 = "ALTER TABLE " + UtilsConstants.TABLE_ADVANCE
                    + " ADD COLUMN " + UtilsConstants.KEY_ADVANCE_ACU + " TEXT ";
            db.execSQL(ALTER_TABLE10);
        }

        if (oldVersion < 10) {
            String ALTER_TABLE1 = "ALTER TABLE " + UtilsConstants.TABLE_CUSTOMERS
                    + " ADD COLUMN " + UtilsConstants.KEY_CUS_OUT_RECORDS + " TEXT ";
            db.execSQL(ALTER_TABLE1);
        }
    }
}
