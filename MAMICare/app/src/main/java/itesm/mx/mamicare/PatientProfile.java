package itesm.mx.mamicare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PatientProfile extends Activity {

    Bundle data; // Data from the intent
    Button btnPreg; // Button to handle pregnancy
    Button btnCheck; // Button to handle Assesment
    Button btnAssesments; // Button to show the list of assesments
    DBOperations dbo; // Database API
    TextView tvUserName;
    TextView tvUserAddress;
    TextView tvUserPregWeek;
    TextView tvUserBday;
    TextView tvUserLastCheck;
    ImageView imvUserPhoto;
    Patient currentPatient; // The selected patient

    @Override
    protected void onResume(){
        super.onResume();
        reloadData();
    }

    /**
     * Method used to realod data when returing from an activity
     */
    public void reloadData(){

        // Get date of last assesment
        String last = dbo.getLastAssesment(currentPatient);
        if(last != null) {
            tvUserLastCheck.setText("Fecha de ultima revisión: " + last);
        } else {
            tvUserLastCheck.setText("No existen revisiones previas");
        }

        // Get actual weeks of pregnancy
        Pregnancy p = dbo.findActivePregnancy(currentPatient);
        if(p != null){
            tvUserPregWeek.setText("Semana " + dbo.getPassedWeeks(p.getId()));
        } else {
            tvUserPregWeek.setText("Embarazo no registrado");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        // Database connection
        dbo = new DBOperations(getApplicationContext());

        // Get data from intent
        data = getIntent().getExtras();
        currentPatient = dbo.findPatient(data.getInt("_id"));

        // Bind views
        btnCheck = (Button) findViewById(R.id.btn_Assesment);
        btnPreg = (Button) findViewById(R.id.btn_Pregnancy);
        btnAssesments = (Button) findViewById(R.id.btn_ListAssesment);
        tvUserName = (TextView) findViewById(R.id.tv_ActiveUserName);
        tvUserAddress = (TextView) findViewById(R.id.tv_ActiveUserAddress);
        tvUserPregWeek = (TextView) findViewById(R.id.tv_ActiveUserPregWeek);
        tvUserBday = (TextView) findViewById(R.id.tv_ActiveUserBirthday);
        tvUserLastCheck = (TextView) findViewById(R.id.tv_ActiveUserLastCheck);
        imvUserPhoto = (ImageView) findViewById(R.id.imv_PatientPhoto);


        // Set data to the views
        tvUserName.setText(currentPatient.getName());
        tvUserAddress.setText(currentPatient.getAddress());
        tvUserBday.setText(currentPatient.getBirthday());

        // Get patient photo
        if(currentPatient.getPhoto_path() != null) { // Fix image
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // Sets the size of the photo
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPatient.getPhoto_path(), options);
            imvUserPhoto.setImageBitmap(imageBitmap);
        } else {
            imvUserPhoto.setImageResource(R.drawable.nophoto);
        }

        // Load data that could be updated
        reloadData();

        // Button listener
        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                Pregnancy p = dbo.findActivePregnancy(currentPatient);
                Intent i;

                if (btnPreg.isPressed()){
                    i = new Intent(PatientProfile.this, PregnancyProfile.class);
                    i.putExtra("_id", currentPatient.getId());
                    startActivity(i); // Go to PregnancyProfile
                } else if(btnCheck.isPressed()){
                    if(p != null){
                        i = new Intent(PatientProfile.this, NewAssesment.class);
                        i.putExtra("_id", p.getId());
                        startActivity(i); // Go to PregnancyProfile
                    } else {
                        Toast.makeText(getApplication(), "No existen embarazo activo, por favor crear uno",
                                Toast.LENGTH_LONG).show();
                    }
                } else if(btnAssesments.isPressed()){
                    if(p != null){
                        i = new Intent(PatientProfile.this, AssesmentList.class);
                        i.putExtra("_id", p.getId());
                        startActivity(i); // Go to PregnancyProfile
                    } else {
                        Toast.makeText(getApplication(), "No existen embarazo activo, por favor crear uno",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        // Register the buttons to the listener
        btnPreg.setOnClickListener(listener);
        btnCheck.setOnClickListener(listener);
        btnAssesments.setOnClickListener(listener);

    }

}
