package com.allinone.smartocity.customer.utility;

/**
 * Created by admin on 7/18/2016.
 */
public class UtilsConstants {

    // Contacts table name
    public static final String TABLE_PRODUCTS = "Product";
    public static final String TABLE_CUSTOMERS = "Customers";
    public static final String TABLE_LOCATION = "location";
    public static final String TABLE_ATTENDANCE = "Attendance";
    public static final String TABLE_LEAVE = "Leave";
    public static final String TABLE_DISTRIBUTOR = "Distributor";
    public static final String TABLE_ROUTE = "Route";
    public static final String TABLE_NOTIFICATION = "Notifications";
    public static final String TABLE_LOGS = "LogReport";
    public static final String TABLE_CART = "CartInfo";
    public static final String TABLE_VISIT_PLAN = "VisitPlanInfo";
    public static final String TABLE_VISIT = "VisitInfo";
    public static final String TABLE_EXPENSE = "Expense";
    public static final String TABLE_TRAVEL = "Travel";
    public static final String TABLE_ADVANCE = "Advance";
    public static final String TABLE_DIST_STOCK = "DistStock";

    //common columns
    public static final String KEY_ID = "id";
    public static final String KEY_CRATED_AT = "created_at";

    // Products Table Columns names
    public static final String KEY_PR_COMPID = "procompanyId";
    public static final String KEY_PR_USERNAME = "prousername";
    public static final String KEY_PR_PASS = "propassword";
    public static final String KEY_PR_ID = "productId";
    public static final String KEY_PR_NAME = "productName";
    public static final String KEY_PR_MRP = "mrp";
    public static final String KEY_PR_CAT = "category";
    public static final String KEY_PR_SUB_CAT = "subCategory";
    public static final String KEY_PR_BRAND = "brand";
    public static final String KEY_PR_PACK = "pack";
    public static final String KEY_PR_DISC = "description";
    public static final String KEY_PR_IMGURL = "imgUrl";
    public static final String KEY_PR_FAV = "favourite_status";
    public static final String KEY_PR_SYNCSTATUS = "productSyncStatus";

    // Customer Table Columns names
    public static final String KEY_CUS_ID = "customerId";
    public static final String KEY_CUS_NAME = "customerName";
    public static final String KEY_CUS_DISTRIBUTOR = "distributor";
    public static final String KEY_CUS_ROUTE = "route";
    public static final String KEY_CUS_STATE = "state";
    public static final String KEY_CUS_CITY = "city";
    public static final String KEY_CUS_IMGURL = "imgUrl";
    public static final String KEY_CUS_MOBILE = "mobile";
    public static final String KEY_CUS_EMAIL = "email";
    public static final String KEY_CUS_ADDRESS = "address";
    public static final String KEY_CUS_BILL = "total_bill";
    public static final String KEY_CUS_OUTSTANDING = "outstanding";
    public static final String KEY_CUS_OUT_RECORDS = "outstanding_records";
    public static final String KEY_CUS_OUT_UPDATED = "outstanding_updated_on";
    public static final String KEY_CUS_LAT = "latitude";
    public static final String KEY_CUS_LONG = "longitude";
    public static final String KEY_CUS_ACU = "accuracy";
    public static final String KEY_CUS_SYNCSTATUS = "customerSyncStatus";

    // Location Table Columns names
    public static final String KEY_LOC_COMPID = "companyId";
    public static final String KEY_LOC_USERNAME = "username";
    public static final String KEY_LOC_PASS = "password";
    public static final String KEY_LOC_LAT = "latitude";
    public static final String KEY_LOC_LONG = "longitude";
    public static final String KEY_LOC_ACU = "accuracy";
    public static final String KEY_LOC_ADDRESS = "address";
    public static final String KEY_LOC_SYNCSTATUS = "locationSyncStatus";

    // Attendance Table Columns names
    public static final String KEY_ATTEND_COMPID = "companyId";
    public static final String KEY_ATTEND_USERNAME = "username";
    public static final String KEY_ATTEND_PASS = "password";
    public static final String KEY_ATTEND_LAT = "latitude";
    public static final String KEY_ATTEND_LONG = "longitude";
    public static final String KEY_ATTEND_ACU = "accuracy";
    public static final String KEY_ATTEND_ADDRESS = "address";
    public static final String KEY_ATTEND_TYPE = "type";
    public static final String KEY_ATTEND_SYNCSTATUS = "attendanceSyncStatus";

    // Attendance KEY_ATTEND_TYPE Columns values
    public static final String KEY_ATTEND_TYPE_CHECKIN = "1";
    public static final String KEY_ATTEND_TYPE_CHECKOUT = "2";

    // Leave Table Columns names
    public static final String KEY_LEAVE_COMPID = "companyId";
    public static final String KEY_LEAVE_USERNAME = "username";
    public static final String KEY_LEAVE_PASS = "password";
    public static final String KEY_LEAVE_TITLE = "leaveTitle";
    public static final String KEY_LEAVE_FROM = "fromDate";
    public static final String KEY_LEAVE_TO = "toDate";
    public static final String KEY_LEAVE_LAT = "latitude";
    public static final String KEY_LEAVE_LONG = "longitude";
    public static final String KEY_LEAVE_ACU = "accuracy";
    public static final String KEY_LEAVE_STATUS = "status";
    public static final String KEY_LEAVE_SYNCSTATUS = "leaveSyncStatus";

    // distributor Table Columns names
    public static final String KEY_DIST_COMPID = "companyId";
    public static final String KEY_DIST_USERNAME = "username";
    public static final String KEY_DIST_PASS = "password";
    public static final String KEY_DIST_ID = "distributorId";
    public static final String KEY_DIST_NAME = "distributorName";
    public static final String KEY_DIST_SYNCSTATUS = "distributorSyncStatus";

    // Route Table Columns names
    public static final String KEY_ROUTE_COMPID = "companyId";
    public static final String KEY_ROUTE_USERNAME = "username";
    public static final String KEY_ROUTE_PASS = "password";
    public static final String KEY_ROUTE_ID = "routeId";
    public static final String KEY_ROUTE_NAME = "routeName";
    public static final String KEY_ROUTE_DISTID = "routedistributorId";
    public static final String KEY_ROUTE_DISTNAME = "routedistributorname";
    public static final String KEY_ROUTE_SELECTION = "routeselection";
    public static final String KEY_ROUTE_SYNCSTATUS = "routeSyncStatus";

    // Notifications Table Columns names
    public static final String KEY_NOTIF_COMPID = "companyId";
    public static final String KEY_NOTIF_USERNAME = "username";
    public static final String KEY_NOTIF_PASS = "password";
    public static final String KEY_NOTE_MESSAGE = "message";
    public static final String KEY_NOTE_READSTATUS = "readStatus";
    public static final String KEY_NOTE_TYPE = "notificationType";
    public static final String KEY_NOFY_DATE = "notificationDate";

    // LogReport Table Columns names
    public static final String KEY_LOG_LOG = "log";
    public static final String KEY_LOG_TYPE = "type";

    //Cart Table Columns
    public static final String KEY_CART_COMPID = "companyId";
    public static final String KEY_CART_USERNAME = "username";
    public static final String KEY_CART_PASS = "password";
    public static final String KEY_CART_QTY = "CartQty";
    public static final String KEY_CART_FLAG = "OrderFlag";
    public static final String KEY_CART_ORDERID = "OrderID";
    public static final String KEY_CART_SIGNATURE = "Signature";
    public static final String KEY_CART_STATUS = "Status";
    public static final String KEY_CART_ACU = "accuracy";
    public static final String KEY_CART_STOCK = "Stock";


    //notification Type
    public static final String NOTE_ATTENDANCE_TYPE = "Attendance";
    public static final String NOTE_LEAVE_TYPE = "Leave";
    public static final String NOTE_LOGIN_TYPE = "Login";
    public static final String NOTE_ORDER_TYPE = "Order";
    public static final String NOTE_CUSTOMER = "Customer";
    public static final String NOTE_PLANVISIT_TYPE = "Plan Visit";
    public static final String NOTE_VISIT_TYPE = "Visit Now";
    public static final String TRACKING = "Tracking";

    //Visit Table
    public static final String KEY_VISIT_COMPID = "companyId";
    public static final String KEY_VISIT_USERNAME = "username";
    public static final String KEY_VISIT_PASS = "password";
    public static final String KEY_VISIT_DESC = "Description";
    public static final String KEY_VISIT_REMARK = "Remark";
    public static final String KEY_VISIT_COMP_INFO = "Competition_info";
    public static final String KEY_VISIT_IMGPATH = "image_name";
    public static final String KEY_VISIT_PLANDATE = "Plan_date";
    public static final String KEY_VISIT_CRE_DATE = "Create_DateTime";
    public static final String KEY_VISIT_STATUS = "SyncStatus";
    public static final String KEY_VISIT_ID = "VisitID";
    public static final String KEY_LAT = "VisitLat";
    public static final String KEY_LONG = "VisitLong";
    public static final String KEY_VISIT_ACU = "accuracy";
    public static final String KEY_ADDRESS = "VisitAddress";

    //Expense Table
    public static final String KEY_EXPENSE_COMPID = "companyId";
    public static final String KEY_EXPENSE_USERNAME = "username";
    public static final String KEY_EXPENSE_PASS = "password";
    public static final String KEY_EXPENSE_GROUP_ID = "expgroupid";
    public static final String KEY_EXPENSE_ID = "expid";
    public static final String KEY_EXPENSE_LAT = "latitude";
    public static final String KEY_EXPENSE_LONG = "longitude";
    public static final String KEY_EXPENSE_ADDRESS = "address";
    public static final String KEY_EXPENSE_TYPE = "type";
    public static final String KEY_EXPENSE_CLAIM_DATE = "claimdate";
    public static final String KEY_EXPENSE_FROM = "fromplace";
    public static final String KEY_EXPENSE_TO = "toplace";
    public static final String KEY_EXPENSE_ACU = "accuracy";
    public static final String KEY_EXPENSE_OTHER = "other";
    public static final String KEY_EXPENSE_MODE = "mode";
    public static final String KEY_EXPENSE_KM = "km";
    public static final String KEY_EXPENSE_RATE = "rate";
    public static final String KEY_EXPENSE_AMOUNT = "amount";
    public static final String KEY_EXPENSE_IMG = "image";
    public static final String KEY_EXPENSE_REQUEST_STATUS = "reqstatus";
    public static final String KEY_EXPENSE_STATUS = "status";
    public static final String KEY_EXPENSE_SYNC_STATUS = "syncstatus";

    //Travel Table
    public static final String KEY_TRAVEL_COMPID = "companyId";
    public static final String KEY_TRAVEL_USERNAME = "username";
    public static final String KEY_TRAVEL_PASS = "password";
    public static final String KEY_TRAVEL_GROUP_ID = "trlgroupid";
    public static final String KEY_TRAVEL_ID = "trlid";
    public static final String KEY_TRAVEL_LAT = "latitude";
    public static final String KEY_TRAVEL_LONG = "longitude";
    public static final String KEY_TRAVEL_ACU = "accuracy";
    public static final String KEY_TRAVEL_ADDRESS = "address";
    public static final String KEY_TRAVEL_DATE = "claimdate";
    public static final String KEY_TRAVEL_FROM = "fromplace";
    public static final String KEY_TRAVEL_TO = "toplace";
    public static final String KEY_TRAVEL_OTHER = "other";
    public static final String KEY_TRAVEL_MODE = "mode";
    public static final String KEY_TRAVEL_PRF_TIME = "preftime";
    public static final String KEY_TRAVEL_MET_TIME = "mettime";
    public static final String KEY_TRAVEL_REMARK = "remark";
    public static final String KEY_TRAVEL_REQUEST_STATUS = "reqstatus";
    public static final String KEY_TRAVEL_STATUS = "status";
    public static final String KEY_TRAVEL_SYNC_STATUS = "syncstatus";

    //Advance Table
    public static final String KEY_ADVANCE_COMPID = "companyId";
    public static final String KEY_ADVANCE_USERNAME = "username";
    public static final String KEY_ADVANCE_PASS = "password";
    public static final String KEY_ADVANCE_ID = "adv_id";
    public static final String KEY_ADVANCE_FROMDATE = "fromdate";
    public static final String KEY_ADVANCE_TODATE = "todate";
    public static final String KEY_ADVANCE_FROM = "fromLoc";
    public static final String KEY_ADVANCE_TO = "toLoc";
    public static final String KEY_ADVANCE_REASON = "reason";
    public static final String KEY_ADVANCE_AMOUNT = "amount";
    public static final String KEY_ADVANCE_LAT = "latitude";
    public static final String KEY_ADVANCE_LONG = "longitude";
    public static final String KEY_ADVANCE_ACU = "accuracy";
    public static final String KEY_ADVANCE_ADDRESS = "address";
    public static final String KEY_ADVANCE_SYNC_STATUS = "syncstatus";

    //Dist Stock Table
    public static final String DIST_COMPID = "companyId";
    public static final String DIST_USERNAME = "username";
    public static final String DIST_PASS = "password";
    public static final String DIST_ID = "distId";
    public static final String DIST_NAME = "distName";
    public static final String DIST_PRID = "prId";
    public static final String DIST_PRNAME = "prName";
    public static final String DIST_STOCK = "stock";
    public static final String DIST_SYNC_STATUS = "syncstatus";

}
