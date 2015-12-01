package itesm.mx.mamicare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

/**
 * Created by SergioJesúsCorderoBa on 11/2/2015.
 */
public class DBOperations {

    public String TAG = "";

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // Patients table
    private static final String TABLE_PATIENTS = "patients";
    private static final String COLUMN_PATIENT_ID = "_id";
    private static final String COLUMN_PATIENT_NAME = "name";
    private static final String COLUMN_PATIENT_ADDRESS = "address";
    private static final String COLUMN_PATIENT_LAST_CHECK = "lastC";
    private static final String COLUMN_PATIENT_BIRTHDAY = "birthday";
    private static final String COLUMN_PATIENT_PHOTO = "photo_path";

    // Pregnancy table
    private static final String TABLE_PREGNANCIES = "pregnancies";
    private static final String COLUMN_PREGNANCY_ID = "_id";
    private static final String COLUMN_PATIENT_FK = "patinet_id";
    private static final String COLUMN_PREGNANCY_ALERT = "alert";
    private static final String COLUMN_PREGNANCY_START = "pregnancy_start";
    private static final String COLUMN_PREGNANCY_END = "pregnancy_end";

    // Assesment table
    private static final String TABLE_ASSESMENTS = "assesments";
    private static final String COLUMN_ASSESMENT_ID = "_id";
    private static final String COLUMN_PREGNANCY_FK = "pregancy_id";
    private static final String COLUMN_ASSESMENT_STARTDATE = "start_date";
    private static final String COLUMN_ASSESMENT_ENDDATE = "end_date";
    private static final String COLUMN_ASSESMENT_HRATE = "hRate";
    private static final String COLUMN_ASSESMENT_OXYGEN = "oxygen";
    private static final String COLUMN_ASSESMENT_ALERT = "alert";


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

    /**
     * Adds a pregnancy to the data base using the id of the patient as foreign key
     * @param patient
     * @param pregnancy
     * @return
     */
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

    public Pregnancy findActivePregnancy(Patient p){
        Pregnancy pregnancy = null;
        String query = "SELECT * FROM " + TABLE_PREGNANCIES +
                " WHERE " + COLUMN_PATIENT_FK + " = \"" + p.getId() + "\" AND " +
                COLUMN_PREGNANCY_ALERT + " != \"" + "-1" + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
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
            Log.d(TAG, "Error while trying to get active pregnancy ");
        }
        db.close();
        return pregnancy;
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
     * Method that ends the pregnancy of a patient. The pregnancy alert changes to -1 and
     * the endDate of the pregnancy sets to actual day.
     * @param pregnancyId
     * @return an integer, if it's different than 0 then the table was correctly updated
     */
    public int endPregnancy(int pregnancyId){
        int result = 0;
        String query = "SELECT * FROM " + TABLE_PREGNANCIES +
                " WHERE " + COLUMN_PREGNANCY_ID + " = \"" + pregnancyId + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        String now = df.format(c.getTime());
        try {
            if (cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_PREGNANCY_ALERT, -1);
                values.put(COLUMN_PREGNANCY_END, now);

                result = db.update(TABLE_PREGNANCIES, values, "_id="+pregnancyId, null);
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to end pregnancy number " + pregnancyId);
        }
        db.close();
        return result;
    }

    /**
     * Method used to change thd current alert of a pregnancy.
     * @param pregnancyId the id of the pregnancy
     * @param alert the integer equal to the alert level
     * @return an integer different than zero if successful
     */
    public int changeAlertofPregnancy(int pregnancyId, int alert){
        int result = 0;
        String query = "SELECT * FROM " + TABLE_PREGNANCIES +
                " WHERE " + COLUMN_PREGNANCY_ID + " = \"" + pregnancyId + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_PREGNANCY_ALERT, alert);

                result = db.update(TABLE_PREGNANCIES, values, "_id="+pregnancyId, null);
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying edit pregnancy alert " + pregnancyId);
        }
        db.close();
        return result;
    }

    /**
     * Method used to get the weeks passed since the begining of the pregnancy until
     * actual date
     * @param pregnancyId
     * @return integer equal to the weeks passed
     */
    public int getPassedWeeks(int pregnancyId){
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
            cursor.moveToLast(); // Data is stored with newest data first
            do {
                //fetch pregnancies from db
                Pregnancy pregnancy = new Pregnancy (
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3),
                        cursor.getString(4));
                //adding to list
                pregnancies.add(pregnancy);
            } while (cursor.moveToPrevious());
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get all pregnancies from patient number " + patientId);
        }
        db.close();
        return pregnancies;

    }

    public int getPregnanciesCountFromPatient(Patient patient) {
        int patientId = patient.getId();
        String query = "SELECT * FROM " + TABLE_PREGNANCIES + " WHERE " + COLUMN_PATIENT_FK + " = "
                + Integer.toString(patientId);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    ///////////////////////////////////////////

    ////////////////////////////////////////
    // Assement operations

    public int addAssesment(Pregnancy pregnancy, Assesment assesment){
        db = dbHelper.getWritableDatabase();
        long assesmentId = 0;
        /*
        *  Date format
        *  dd-MM-yyyy-hh-mm-ss
        * */
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PREGNANCY_FK, pregnancy.getId());
            values.put(COLUMN_ASSESMENT_STARTDATE, assesment.getStart_date());
            values.put(COLUMN_ASSESMENT_ENDDATE, assesment.getEnd_date());
            values.put(COLUMN_ASSESMENT_HRATE, assesment.gethRate());
            values.put(COLUMN_ASSESMENT_OXYGEN, assesment.getOxygen());
            values.put(COLUMN_ASSESMENT_ALERT, assesment.getAlert());

            assesmentId = db.insert(TABLE_ASSESMENTS, null, values);
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
                " WHERE " + COLUMN_ASSESMENT_ID + " = \"" + assesmentId + "\"";

        Cursor cursor = db.rawQuery(query, null);
        try
        {
            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                db.delete( TABLE_ASSESMENTS, COLUMN_ASSESMENT_ID + " = ?",
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
                " WHERE " + COLUMN_ASSESMENT_ID + " = \"" + assesmentId + "\"";

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
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6)));
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get assesment number " + assesmentId);
        }
        db.close();
        return assesment;
    }

    public String getLastAssesment(Patient p){
        String theDate = null;
        Assesment result = null;
        Pregnancy activePregnancy = findActivePregnancy(p);

        if(activePregnancy != null){
            String query = "SELECT * FROM " + TABLE_ASSESMENTS +
                    " WHERE " + COLUMN_PREGNANCY_FK + " = \"" + activePregnancy.getId() + "\"";

            db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.moveToLast()) {
                    result = new Assesment (
                            Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)),
                            cursor.getString(2),
                            cursor.getString(3),
                            Integer.parseInt(cursor.getString(4)),
                            Integer.parseInt(cursor.getString(5)),
                            Integer.parseInt(cursor.getString(6)));
                }
            } catch (SQLiteException e) {
                Log.d(TAG, "Error while trying to get last assesment ");
            }
            if(result != null){
                String start_dt = result.getStart_date();
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = (Date) formatter.parse(start_dt);
                    theDate = newFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        db.close();
        return theDate;
    }

    public List<Assesment> getAllAssesmentsFromPregnancy(Pregnancy pregnancy){
        int pregnancyId = pregnancy.getId();
        String query = "SELECT * FROM " + TABLE_ASSESMENTS + " WHERE " + COLUMN_PREGNANCY_FK + " = " + Integer.toString(pregnancyId);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Assesment> assesments = new ArrayList<>();
        try {
            cursor.moveToLast(); // Data is stored with newest data first
            do {
                //fetch pregnancies from db
                Assesment  assesment = new Assesment (
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6)));
                //adding to list
                assesments.add(assesment);
            } while (cursor.moveToPrevious());
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
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    ///////////////////////////////////////////


    ////////////////////////////////////////
    // Evaluation center

    public String transformAlerttoString(int alertId){
        String result = "";

        switch (alertId){
            case -1:
                result = "Embarazo finalizado";
                break;
            case 0:
                result = "Nivel de presión arterial normal";
                break;
            case 1:
                result = "ADVERTENCIA - Nivel de presión arterial baja";
                break;
            case 2:
                result = "ADVERTENCIA - Nivel de presión arterial alta";
                break;
            case 3:
                result = "PELIGRO - Nivel de presión arterial muy alta";
                break;
        }
        return result;
    }

    ///////////////////////////////////////////

    public DBOperations(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }
}
