package itesm.mx.mamicare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
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
    private static final String COLUMN_PATIENT_ADDRESS = "address";
    private static final String COLUMN_PATIENT_LAST_CHECK = "lastC";
    private static final String COLUMN_PATIENT_BIRTHDAY = "birthday";
    private static final String COLUMN_PATIENT_PHOTO = "photo_path";


    ////////////////////////////////////////
    // Patient operations
    public int addPatient(Patient patient){
        long insertedId=-1;
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            /*
                date format is:
                  birthday - dd-mm-yyyy
                  lastCheck - dd-mm-yyyy-fff (fff = days from day 1)
            */
            //inserting known values, remember id is automatically inserted
            values.put(COLUMN_PATIENT_NAME, patient.getName());
            values.put(COLUMN_PATIENT_ADDRESS, patient.getAddress());
            values.put(COLUMN_PATIENT_LAST_CHECK, patient.getLastCheck());
            values.put(COLUMN_PATIENT_BIRTHDAY, patient.getBirthday());
            values.put(COLUMN_PATIENT_PHOTO, patient.getPhoto_path());

            insertedId = db.insert(TABLE_PATIENTS, null, values);
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to add patient to database");
        }
        return (int) insertedId;
    }

    public boolean deletePatient(int patientId){
        boolean result = false;

        db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PATIENTS +
                " WHERE " + COLUMN_PATIENT_ID + " = \"" + patientId + "\"";

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
            Log.d(TAG, "Error while trying to delete patient number " + patientId);
        }
        db.close();
        return result;
    }

    public Patient findPatient(int patientId){
        String query = "SELECT * FROM " + TABLE_PATIENTS +
                " WHERE " + COLUMN_PATIENT_ID + " = \"" + patientId + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Patient patient = null;
        try {
            if (cursor.moveToFirst()) {
                patient = new Patient (
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get patient number " + patientId);
        }
        db.close();
        return patient;
    }

    public List<Patient> getAllPatients(){
        String query = "SELECT * FROM " + TABLE_PATIENTS;

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Patient> patients = new ArrayList<>();
        try {
            while(cursor.moveToNext()){
                Patient  patient = new Patient (
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                //adding to list
                patients.add(patient);
            }
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
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    ///////////////////////////////////////////

    public DBOperations(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }
}
