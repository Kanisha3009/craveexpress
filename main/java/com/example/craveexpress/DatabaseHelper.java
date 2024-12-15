package com.example.craveexpress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "Crave.db";
    private static final int DATABASE_VERSION = 2;

    // User Table Information
    private static final String TABLE_USER = "user";
    private static final String COL_USER_1 = "F_NAME";
    private static final String COL_USER_2 = "EMAIL";
    private static final String COL_USER_3 = "PHONE_NUM";
    private static final String COL_USER_4 = "PASSWORD";

    // Restaurant Table Information
    private static final String TABLE_RESTAURANT = "restaurant";
    private static final String COL_RES_1 = "ID";
    private static final String COL_RES_2 = "NAME";
    private static final String COL_RES_3 = "RATING";
    private static final String COL_RES_4 = "IMAGE_RESOURCE";

    // Order Details Table Information
    private static final String TABLE_ORDER_DETAIL = "order_detail";
    private static final String COL_ORDER_1 = "ID";
    private static final String COL_ORDER_2 = "ITEM_NAME";
    private static final String COL_ORDER_3 = "QUANTITY";
    private static final String COL_ORDER_4 = "PRICE_PER_UNIT";
    private static final String COL_ORDER_5 = "TOTAL_PRICE";

    // Order Summary Table Information
    private static final String TABLE_ORDER_SUMMARY = "order_summary";
    private static final String COL_SUMMARY_1 = "ID";
    private static final String COL_SUMMARY_2 = "ORDER_SUMMARY";
    private static final String COL_SUMMARY_3 = "TOTAL_PRICE";
    private static final String COL_SUMMARY_4 = "DELIVERY_ADDRESS";
    private static final String COL_SUMMARY_5 = "CONTACT_NUMBER";
    private static final String COL_SUMMARY_6 = "PAYMENT_METHOD";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User Table
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COL_USER_1 + " TEXT, " +
                COL_USER_2 + " TEXT PRIMARY KEY, " +
                COL_USER_3 + " TEXT, " +
                COL_USER_4 + " TEXT)");

        // Create Restaurant Table
        db.execSQL("CREATE TABLE " + TABLE_RESTAURANT + " (" +
                COL_RES_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RES_2 + " TEXT, " +
                COL_RES_3 + " TEXT, " +
                COL_RES_4 + " INTEGER)");

        // Create Order Details Table
        db.execSQL("CREATE TABLE " + TABLE_ORDER_DETAIL + " (" +
                COL_ORDER_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ORDER_2 + " TEXT, " +
                COL_ORDER_3 + " INTEGER, " +
                COL_ORDER_4 + " REAL, " +
                COL_ORDER_5 + " REAL)");

        // Create Order Summary Table
        db.execSQL("CREATE TABLE " + TABLE_ORDER_SUMMARY + " (" +
                COL_SUMMARY_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SUMMARY_2 + " TEXT, " +
                COL_SUMMARY_3 + " REAL, " +
                COL_SUMMARY_4 + " TEXT, " +
                COL_SUMMARY_5 + " TEXT, " +
                COL_SUMMARY_6 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_SUMMARY);
        onCreate(db);
    }

    // -------------------- USER TABLE METHODS --------------------

    public boolean insertUser(String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER_1, name);
        contentValues.put(COL_USER_2, email);
        contentValues.put(COL_USER_3, phone);
        contentValues.put(COL_USER_4, password);

        long result = db.insert(TABLE_USER, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE EMAIL = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // -------------------- RESTAURANT TABLE METHODS --------------------

    public boolean insertRestaurant(String name, String rating, int imageResource) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RES_2, name);
        contentValues.put(COL_RES_3, rating);
        contentValues.put(COL_RES_4, imageResource);

        long result = db.insert(TABLE_RESTAURANT, null, contentValues);
        db.close();
        return result != -1;
    }

    public List<Restaurant> getAllRestaurants() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Restaurant> restaurantList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RESTAURANT, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_2));
                String rating = cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_3));
                int imageResource = cursor.getInt(cursor.getColumnIndexOrThrow(COL_RES_4));
                restaurantList.add(new Restaurant(name, rating, imageResource));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return restaurantList;
    }

    public void clearAllRestaurants() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_RESTAURANT);
        db.close();
    }

    // -------------------- ORDER DETAIL TABLE METHODS --------------------

    public boolean insertOrderDetail(String itemName, int quantity, double pricePerUnit, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ORDER_2, itemName);
        contentValues.put(COL_ORDER_3, quantity);
        contentValues.put(COL_ORDER_4, pricePerUnit);
        contentValues.put(COL_ORDER_5, totalPrice);

        long result = db.insert(TABLE_ORDER_DETAIL, null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDER_DETAIL, null);
    }

    // -------------------- ORDER SUMMARY TABLE METHODS --------------------

    public boolean insertOrderSummary(String orderSummary, double totalPrice, String deliveryAddress, String contactNumber, String paymentMethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUMMARY_2, orderSummary);
        contentValues.put(COL_SUMMARY_3, totalPrice);
        contentValues.put(COL_SUMMARY_4, deliveryAddress);
        contentValues.put(COL_SUMMARY_5, contactNumber);
        contentValues.put(COL_SUMMARY_6, paymentMethod);

        long result = db.insert(TABLE_ORDER_SUMMARY, null, contentValues);
        Log.d("OrderSummary", "Insert result: " + result);
        db.close();
        return result != -1;
    }

    public List<String> getOrderSummaries() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> summaries = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER_SUMMARY, null);

        if (cursor.moveToFirst()) {
            do {
                String summary = cursor.getString(cursor.getColumnIndexOrThrow(COL_SUMMARY_2));
                summaries.add(summary);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return summaries;
    }

    public void clearAllOrderSummaries() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ORDER_SUMMARY);
        db.close();
    }
}
