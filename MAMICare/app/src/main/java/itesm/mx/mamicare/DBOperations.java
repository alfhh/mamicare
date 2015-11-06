package itesm.mx.mamicare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.List;

/**
 * Created by SergioJesúsCorderoBa on 11/2/2015.
 */
public class DBOperations {

    public String TAG = "";

    private SQLiteDatabase db;
    private DBHelper dbHelper;

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


    ////////////////////////////////////////
    // Patient operations
    public int addPatient(Patient patient){
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            //inserting known values, remember id is automatically inserted
            values.put(COLUMN_PATIENT_NAME, patient.getName());
            values.put(COLUMN_PATIENT_BIRTHDAY, patient.getBirthday());
            values.put(COLUMN_PATIENT_LAST_CHECK, patient.getLastCheck());

            db.insert(TABLE_PATIENTS, null, values);
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to add patient to database");
        }
        // TODO change so that it returns the id of the patient created
        return 1;
    }

    public boolean deletePatient(String patientName){
        boolean result = false;

        db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PATIENTS +
                " WHERE " + COLUMN_PATIENT_NAME + " = \"" + patientName + "\"";

        Cursor cursor = db.rawQuery(query, null);
        try
        {
            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                db.delete( TABLE_PATIENTS, COLUMN_PATIENT_ID + " = ?",
                        new String[] {String.valueOf(id) });
                cursor.close();
                result = true;
            }
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to delete a patient");
        }
        db.close();
        return result;
    }

    public Patient findPatient(String patientName){
        String query = "SELECT * FROM " + TABLE_PATIENTS +
                " WHERE " + COLUMN_PATIENT_NAME + " = \"" + patientName + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Patient patient = null;
        try {
            if (cursor.moveToFirst()) {
                patient = new Patient ( cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4));
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get aptient from database.");
        }
        db.close();
        return patient;
    }

    public List<Patient> getAllPatients(){
        String query = "SELECT * FROM " + TABLE_PATIENTS;

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Patient> patients = null;
        try {
            do {
                //fetch product from db
                Patient patient = new Patient ( cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4));
                //adding to list
                patients.add(patient);
            } while (cursor.moveToNext());
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get all patients");
        }
        db.close();
        return patients;

    }

    public int getPatientCount() {
        String query = "SELECT * FROM " + TABLE_PATIENTS;

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        return cursor.getCount();
    }
    ///////////////////////////////////////////

    ///////////////////////////////////////////
    // Pregnancy operations
    public void addPregnancy(int patient_id, Pregnancy pregnancy){
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PREGNANCY_PATIENT, patient_id);
            values.put(COLUMN_PREGNANCY_ALERT, pregnancy.getAlert());
            values.put(COLUMN_PREGNANCY_WEEK, pregnancy.getPregnancyWeek());

            db.insert(TABLE_PREGNANCIES, null, values);
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to add pregnancy to database");
        }
    }

    public List<Pregnancy> getAllPregnanciesFromPatient(String patientName){
        Integer patientId = 1;
        String query = "SELECT * FROM " + TABLE_PREGNANCIES + " WHERE patient_id = " + Integer.toString(patientId);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Pregnancy> pregnancies = null;
        try {
            do {
                //fetch pregnancies from db
                Pregnancy pregnancy = new Pregnancy ( Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)));
                //adding to list
                pregnancies.add(pregnancy);
            } while (cursor.moveToNext());
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get all pregnancies from patient number " + patientId);
        }
        db.close();
        return pregnancies;

    }

    public int getPregnanciesCount(String patient_name) {
        Integer patientId = 1;
        String query = "SELECT * FROM " + TABLE_PREGNANCIES + " WHERE patient_id = " + Integer.toString(patientId);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        return cursor.getCount();
    }
    ///////////////////////////////////////////////

    public DBOperations(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }
}
