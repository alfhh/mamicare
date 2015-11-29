package itesm.mx.mamicare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    // pregnancy table
    private static final String TABLE_PREGNANCIES = "pregnancies";
    private static final String COLUMN_PREGNANCY_ID = "_id";
    private static final String COLUMN_PATIENT_FK = "patinet_id";
    private static final String COLUMN_PREGNANCY_ALERT = "alert";
    private static final String COLUMN_PREGNANCY_START = "pregnancy_start";
    private static final String COLUMN_PREGNANCY_END = "pregnancy_end";


    ////////////////////////////////////////
    // Patient operations

    /**
     * Adds a patient to the database
     * @param patient
     * @return the id of the patient
     */
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

    /**
     * Method used to find a patient in the data base and return an object of type patient
     * @param patientId
     * @return the found patient, if not a null object
     */
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

    /**
     * Returns an ArrayList of patients found in the database
     * @return the List of patients
     */
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

    ////////////////////////////////////////
    // Pregnancy operations

    public int addPregnancy(Patient patient, Pregnancy pregnancy){
        int fk = patient.getId();
        db = dbHelper.getWritableDatabase();
        long pregnancyId = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PATIENT_FK, fk);
            values.put(COLUMN_PREGNANCY_ALERT, pregnancy.getAlert());
            values.put(COLUMN_PREGNANCY_START, pregnancy.getPregnancyStart());

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
                        cursor.getString(3),
                        cursor.getString(4));
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get pregnancy number " + pregnancyId);
        }
        db.close();
        return pregnancy;
    }

    /**
     * Method used to get the weeks passed since the begining of the pregnancy until
     * actual date
     * @param pregnancyId
     * @return integer equal to the weeks passed
     */
    public int getRemainingWeeks(int pregnancyId){
        int weeks = 0;
        String query = "SELECT * FROM " + TABLE_PREGNANCIES +
                " WHERE " + COLUMN_PREGNANCY_ID + " = \"" + pregnancyId + "\"";
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        String now = df.format(c.getTime());
        String start = "";

        try{

            if(cursor.moveToFirst()){
                start = cursor.getString(3);
            }

            Date date2= df.parse(now);
            Date date1 = df.parse(start);
            long diff = date2.getTime() - date1.getTime(); // Get difference
            diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            diff = diff / 7;
            weeks = (int) diff;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch(SQLiteException e2){
            e2.printStackTrace();
        }
        db.close();
        return weeks;
    }

    public List<Pregnancy> getAllPregnanciesFromPatient(Patient patient){
        int patientId = patient.getId();
        String query = "SELECT * FROM " + TABLE_PREGNANCIES + " WHERE " + COLUMN_PATIENT_FK + " = " + Integer.toString(patientId);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Pregnancy> pregnancies = new ArrayList<>();
        try {
            while (cursor.moveToNext()){
                //fetch pregnancies from db
                Pregnancy pregnancy = new Pregnancy (
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3),
                        cursor.getString(4));
                //adding to list
                pregnancies.add(pregnancy);
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get all pregnancies from patient number " + patientId);
        }
        db.close();
        return pregnancies;

    }

    public int getPregnanciesCountFromPatient(Patient patient) {
        int patientId = patient.getId();
        String query = "SELECT * FROM " + TABLE_PREGNANCIES + " WHERE " + COLUMN_PATIENT_FK + " = " + Integer.toString(patientId);

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
