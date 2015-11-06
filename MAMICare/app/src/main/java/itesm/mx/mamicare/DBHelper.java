package itesm.mx.mamicare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SergioJesúsCorderoBa on 11/2/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mamicare.db";
    // patients table
    private static final String TABLE_PATIENTS = "patients";
    private static final String COLUMN_PATIENT_ID = "_id";
    private static final String COLUMN_PATIENT_NAME = "name";
    private static final String COLUMN_PATIENT_LAST_CHECK = "lastC";
    private static final String COLUMN_PATIENT_BIRTHDAY = "birthday";
    private static final String PATIENT_BIRTHDAY = "";
    private static final String PATIENT_BIRTHMONTH = "";
    private static final String PATIENT_BIRTHYEAR = "";
    // pregnancy table
    private static final String TABLE_PREGNANCIES = "pregnancies";
    private static final String COLUMN_PREGNANCY_ID = "_id";
    private static final String COLUMN_PREGNANCY_PATIENT = "patinet_id";
    private static final String COLUMN_PREGNANCY_ALERT = "alert";
    private static final String COLUMN_PREGNANCY_WEEK = "pregnancy_week";

    //singleton helper
    private static DBHelper dbHelperInstance;

    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelperInstance == null) {
            dbHelperInstance = new DBHelper(context.getApplicationContext());
        }
        return dbHelperInstance;
    }

    private DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PATIENTS +
                "(" + COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY," +
                COLUMN_PATIENT_NAME + " TEXT," +
                COLUMN_PATIENT_LAST_CHECK + " DATE," +
                COLUMN_PATIENT_BIRTHDAY + " DATE)";

        String CREATE_PREGNANCIES_TABLE = "CREATE TABLE " + TABLE_PREGNANCIES +
                "(" + COLUMN_PREGNANCY_ID + " INTEGER PRIMARY KEY," +
                COLUMN_PREGNANCY_PATIENT + " INTEGER," +
                COLUMN_PREGNANCY_ALERT + " INTEGER," +
                COLUMN_PREGNANCY_WEEK + " INTEGER," +
                "FOREIGN KEY(" + COLUMN_PREGNANCY_PATIENT + ") " +
                "REFERENCES " + TABLE_PATIENTS + "(" + COLUMN_PATIENT_ID + "))";

        Log.i("\n\n\n>PatientTable", CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);

        Log.i("\n\n\n>PregnancyTable", CREATE_PREGNANCIES_TABLE);
        db.execSQL(CREATE_PREGNANCIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            Log.w("Producthelper onUpgrade", "Upgrading database from version " +
                    oldVersion + " to " + newVersion +
                    ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
            onCreate(db);
        }
    }
}
