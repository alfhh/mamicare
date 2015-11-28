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


public class PatientProfile extends Activity {

    Bundle data; // Data from the intent
    Button btnPreg; // Button to handle pregnancy
    Button btnCheck; // Button to handle Assesment
    DBOperations dbo; // Database API
    TextView tvUserName;
    TextView tvUserAddress;
    TextView tvUserPregWeek;
    TextView tvUserBday;
    TextView tvUserLastCheck;
    ImageView imvUserPhoto;
    Patient currentPatient; // The selected patient

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
                Intent i;

                if (btnPreg.isPressed()){
                    Log.d("Button", "Checking action");
                    i = new Intent(PatientProfile.this, PregnancyProfile.class);
                    i.putExtra("_id", currentPatient.getId());
                    startActivity(i); // Go to PregnancyProfile
                }
            }
        };

        // Register the buttons to the listener
        btnPreg.setOnClickListener(listener);

    }

}
