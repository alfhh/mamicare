package itesm.mx.mamicare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class PatientProfile extends Activity {

    DatePicker datePicker; // Date picker dialog
    Bundle data; // Data from the intent
    Button btnPreg; // Button to handle pregnancy
    Button btnCheck; // Button to handle Assesment
    Calendar calendar; // Used to get dates
    DBOperations dbo; // Database API
    List<Pregnancy> actualPreg; // The list of pregnancy of the patient
    TextView tvUserName;
    TextView tvUserAddress;
    TextView tvUserPregWeek;
    TextView tvUserBday;
    TextView tvUserLastCheck;
    ImageView imvUserPhoto;
    Patient currentPatient; // The selected patient
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        // Database connection
        dbo = new DBOperations(getApplicationContext());

        // Get data from intent
        data = getIntent().getExtras();
        currentPatient = dbo.findPatient(data.getInt("_id"));

        // Get pregnancies from patient
        actualPreg = dbo.getAllPregnanciesFromPatient(currentPatient);

        // Bind views
        btnCheck = (Button) findViewById(R.id.btn_Assesment);
        btnPreg = (Button) findViewById(R.id.btn_Pregnancy);
        tvUserName = (TextView) findViewById(R.id.tv_ActiveUserName);
        tvUserAddress = (TextView) findViewById(R.id.tv_ActiveUserAddress);
        tvUserPregWeek = (TextView) findViewById(R.id.tv_ActiveUserPregWeek);
        tvUserBday = (TextView) findViewById(R.id.tv_ActiveUserBirthday);
        tvUserLastCheck = (TextView) findViewById(R.id.tv_ActiveUserLastCheck);
        imvUserPhoto = (ImageView) findViewById(R.id.imv_PatientPhoto);

        // Building DateDialog helpers
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Set data to the views
        tvUserName.setText(currentPatient.getName());
        tvUserAddress.setText(currentPatient.getAddress());
        tvUserBday.setText(currentPatient.getBirthday());

        // Possible empty views
        imvUserPhoto.setImageResource(R.drawable.nophoto);
        tvUserLastCheck.setText("No existen revisiones previas");
        tvUserPregWeek.setText("No existe embarazo actual");

        if(currentPatient.getPhoto_path() != null) { // Fix image
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // Sets the size of the photo
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPatient.getPhoto_path(), options);
            imvUserPhoto.setImageBitmap(imageBitmap);
        }

        if(currentPatient.getLastCheck() != null) { // Fix last check
            tvUserLastCheck.setText(currentPatient.getLastCheck());
        }

        // TODO IMPLEMENT GET PREGNANCY WEEK

        // Button listener
        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (btnPreg.isPressed()){
                    Log.d("Button", "Checking action");
                    pregnancyButton(); // Process the pregnancy request
                }
            }
        };

        // Register the buttons to the listener
        btnPreg.setOnClickListener(listener);

    }

    public void pregnancyButton(){
        if(actualPreg.isEmpty()){
            setDate(datePicker);
        } else {
            Log.d("START ACTIVITY", "PREGNANCY");
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Log.d("DATEPICKER", "DONE");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    // DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int arg1, int arg2, int arg3) {
            Log.d("DATE SET", "Year: " + arg1 + " Month: " + (arg2 + 1) + " Day: " + arg3);
        }
    };

}
