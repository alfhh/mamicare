package itesm.mx.mamicare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SergioJesúsCorderoBa on 11/2/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "mamicare.db";
    // patients table
    private static final String TABLE_PATIENTS = "patients";
    private static final String COLUMN_PATIENT_ID = "_id";
    private static final String COLUMN_PATIENT_NAME = "name";
    private static final String COLUMN_PATIENT_LAST_CHECK = "lastC";
    private static final String COLUMN_PATIENT_BIRTHDAY = "birthday";
    private static final String COLUMN_PATIENT_PHOTO = "photo_path";
    // pregnancy table
    private static final String TABLE_PREGNANCIES = "pregnancies";
    private static final String COLUMN_PREGNANCY_ID = "_id";
    private static final String COLUMN_PATIENT_FK = "patinet_id";
    private static final String COLUMN_PREGNANCY_ALERT = "alert";
    private static final String COLUMN_PREGNANCY_WEEK = "pregnancy_week";
    // assesment table
    private static final String TABLE_ASSESMENTS = "assesments";
    private static final String COLUMN_ASSESMENT_ID = "_id";
    private static final String COLUMN_PREGNANCY_FK = "pregancy_id";
    private static final String COLUMN_STARTDATE = "start_date";
    private static final String COLUMN_ENDDATE = "end_date";
    private static final String COLUMN_PREASSURE = "blood_preassure";

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
                "(" + COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PATIENT_NAME + " TEXT," +
                COLUMN_PATIENT_LAST_CHECK + " DATE," +
                COLUMN_PATIENT_BIRTHDAY + " DATE," +
                COLUMN_PATIENT_PHOTO + " TEXT)";

        String CREATE_PREGNANCIES_TABLE = "CREATE TABLE " + TABLE_PREGNANCIES +
                "(" + COLUMN_PREGNANCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PATIENT_FK + " INTEGER," +
                COLUMN_PREGNANCY_ALERT + " INTEGER," +
                COLUMN_PREGNANCY_WEEK + " INTEGER," +
                "FOREIGN KEY(" + COLUMN_PATIENT_FK + ") " +
                "REFERENCES " + TABLE_PATIENTS + "(" + COLUMN_PATIENT_ID + "))";

        String CREATE_ASSESMENTES_TABLE = "CREATE TABLE " + TABLE_ASSESMENTS +
                "(" + COLUMN_ASSESMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PREGNANCY_FK + " INTEGER," +
                COLUMN_STARTDATE + " INTEGER," +
                COLUMN_ENDDATE + " INTEGER," +
                COLUMN_PREASSURE + " INTEGER" +
                "FOREIGN KEY(" + COLUMN_PREGNANCY_FK + ") " +
                "REFERENCES " + TABLE_ASSESMENTS + "(" + COLUMN_ASSESMENT_ID + "))";

        Log.i("\n\n\n>PatientTable", CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);

        Log.i("\n\n\n>PregnancyTable", CREATE_PREGNANCIES_TABLE);
        db.execSQL(CREATE_PREGNANCIES_TABLE);

        Log.i("\n\n\n>AssesmentTable", CREATE_ASSESMENTES_TABLE);
        db.execSQL(CREATE_ASSESMENTES_TABLE);
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
