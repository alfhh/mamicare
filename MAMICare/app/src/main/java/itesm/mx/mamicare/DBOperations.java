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
    private static final String COLUMN_PATIENT_PHOTO = "photo_path";
    // pregnancy table
    private static final String TABLE_PREGNANCIES = "pregnancies";
    private static final String COLUMN_PREGNANCY_ID = "_id";
    private static final String COLUMN_PATIENT_FK = "patinet_id";
    private static final String COLUMN_PREGNANCY_ALERT = "alert";
    private static final String COLUMN_PREGNANCY_WEEK = "pregnancy_week";
    // assesment table
    private static final String TABLE_ASSESMENTS = "assesments";
    private static final String COLUMN_ASSESMENTS_ID = "_id";
    private static final String COLUMN_PREGNANCY_FK = "pregancy_id";
    private static final String COLUMN_ASSESMENTS_STARTDATE = "start_date";
    private static final String COLUMN_ASSESMENTS_ENDDATE = "end_date";
    private static final String COLUMN_ASSESMENTS_PREASSURE = "blood_preassure";


    ////////////////////////////////////////
    // Patient operations
    public int addPatient(Patient patient){
        long insertedId=0;
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            //inserting known values, remember id is automatically inserted
            values.put(COLUMN_PATIENT_NAME, patient.getName());
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
                        Integer.parseInt(cursor.getString(0)),
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
        List<Patient> patients = null;
        try {
            do {
                Patient patient = null;
                //fetch product from db
                patient = new Patient (
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
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
    public int addPregnancy(Patient patient, Pregnancy pregnancy){
        db = dbHelper.getWritableDatabase();
        long pregnancyId = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PATIENT_FK, patient.getId());
            values.put(COLUMN_PREGNANCY_ALERT, pregnancy.getAlert());
            values.put(COLUMN_PREGNANCY_WEEK, pregnancy.getPregnancyWeek());

            pregnancyId = db.insert(TABLE_PREGNANCIES, null, values);
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to add pregnancy to database");
        }
        return (int) pregnancyId;
    }

    public boolean deletePregnancy(int pregnancyId){
        boolean result = false;

        db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PREGNANCIES +
                " WHERE " + COLUMN_PREGNANCY_ID + " = \"" + pregnancyId + "\"";

        Cursor cursor = db.rawQuery(query, null);
        try
        {
            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                db.delete( TABLE_PREGNANCIES, COLUMN_PREGNANCY_ID + " = ?",
                        new String[] {String.valueOf(id) });
                cursor.close();
                result = true;
            }
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to delete pregnancy number " + pregnancyId);
        }
        db.close();
        return result;
    }

    public Pregnancy findPregnancy(int pregnancyId){
        String query = "SELECT * FROM " + TABLE_PREGNANCIES +
                " WHERE " + COLUMN_PREGNANCY_ID + " = \"" + pregnancyId + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pregnancy pregnancy = null;
        try {
            if (cursor.moveToFirst()) {
                pregnancy = new Pregnancy (
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)));
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get pregnancy number " + pregnancyId);
        }
        db.close();
        return pregnancy;
    }

    public List<Pregnancy> getAllPregnanciesFromPatient(Patient patient){
        int patientId = patient.getId();
        String query = "SELECT * FROM " + TABLE_PREGNANCIES + " WHERE " + COLUMN_PATIENT_FK + " = " + Integer.toString(patientId);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Pregnancy> pregnancies = null;
        try {
            do {
                //fetch pregnancies from db
                Pregnancy pregnancy = new Pregnancy (
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
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

    public int getPregnanciesCount(Patient patient) {
        int patientId = patient.getId();
        String query = "SELECT * FROM " + TABLE_PREGNANCIES + " WHERE " + COLUMN_PATIENT_FK + " = " + Integer.toString(patientId);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        return cursor.getCount();
    }
    ///////////////////////////////////////////////

    ///////////////////////////////////////////
    // Assesment operations
    public int addAssesment(Pregnancy pregnancy, Assesment assesment){
        db = dbHelper.getWritableDatabase();
        long assesmentId = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PREGNANCY_FK, pregnancy.getId());
            values.put(COLUMN_ASSESMENTS_STARTDATE, assesment.getStart_date());
            values.put(COLUMN_ASSESMENTS_ENDDATE, assesment.getEnd_date());
            values.put(COLUMN_ASSESMENTS_PREASSURE, assesment.getBlood_preassure());

            assesmentId = db.insert(TABLE_PREGNANCIES, null, values);
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to add assesment to database");
        }
        return (int) assesmentId;
    }

    public boolean deleteAssesment(int assesmentId){
        boolean result = false;

        db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ASSESMENTS +
                " WHERE " + COLUMN_ASSESMENTS_ID + " = \"" + assesmentId + "\"";

        Cursor cursor = db.rawQuery(query, null);
        try
        {
            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                db.delete( TABLE_ASSESMENTS, COLUMN_ASSESMENTS_ID + " = ?",
                        new String[] {String.valueOf(id) });
                cursor.close();
                result = true;
            }
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to delete assesment number" + assesmentId);
        }
        db.close();
        return result;
    }

    public Assesment findAssesment(int assesmentId){
        String query = "SELECT * FROM " + TABLE_ASSESMENTS +
                " WHERE " + COLUMN_ASSESMENTS_ID + " = \"" + assesmentId + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Assesment assesment = null;
        try {
            if (cursor.moveToFirst()) {
                assesment = new Assesment (
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)));
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get assesment number " + assesmentId);
        }
        db.close();
        return assesment;
    }

    public List<Assesment> getAllAssesmentsFromPregnancy(Pregnancy pregnancy){
        int pregnancyId = pregnancy.getId();
        String query = "SELECT * FROM " + TABLE_ASSESMENTS + " WHERE " + COLUMN_PREGNANCY_FK + " = " + Integer.toString(pregnancyId);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Assesment> assesments = null;
        try {
            do {
                //fetch pregnancies from db
                Assesment assesment = new Assesment (
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)));
                //adding to list
                assesments.add(assesment);
            } while (cursor.moveToNext());
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get all assesments from pregnancy number " + pregnancyId);
        }
        db.close();
        return assesments;

    }

    public int getAssesmentsCountFromPregnancy(Pregnancy pregnancy) {
        int pregnancyId = pregnancy.getId();
        String query = "SELECT * FROM " + TABLE_ASSESMENTS + " WHERE " + COLUMN_PREGNANCY_FK + " = " + Integer.toString(pregnancyId);

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
