package org.example.tablayoutsfragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "categoryManager";
    private static final String TABLE_CATEGORY = "category";
    private static final String KEY_ID = "id";
//    private static final String KEY_NAME = "name";
//    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_TYPE = "EI";
    private static final String KEY_WANT_NEED = "want_need";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VALUE = "value";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TYPE + " INTEGER,"
                + KEY_WANT_NEED + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_VALUE + " INTEGER,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        // Create tables again
        onCreate(db);
    }

    void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName()); // Contact Name
//        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
        values.put(KEY_TYPE, category.getEI());
        values.put(KEY_WANT_NEED, category.getWant_need());
        values.put(KEY_TITLE, category.getTitle());
        values.put(KEY_VALUE, category.getValue());
        values.put(KEY_CATEGORY, category.getCategory());
        values.put(KEY_DATE, category.getDate());
        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<Category> getAllCategories() {
        List<Category> contactList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY + " ORDER BY " + KEY_DATE +"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(Integer.parseInt(cursor.getString(0)));
                category.setEI(Integer.parseInt(cursor.getString(1)));
                category.setWant_need(Integer.parseInt(cursor.getString(2)));
                category.setTitle(cursor.getString(3));
                category.setValue(Integer.parseInt(cursor.getString(4)));
                category.setCategory(cursor.getString(5));
                category.setDate(cursor.getString(6));

//                category.setName(cursor.getString(1));
//                category.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(category);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}
