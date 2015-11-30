package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewAssesment extends Activity {

    Bundle data; // Data from the intent
    Button btnCancel; // Cancel the assesment
    Button btnAdd; // Add the assesment
    DBOperations dbo; // Database API
    EditText etHRate; // EditText of Heart
    EditText etOxygen; // EditText of Oxygen in blood
    Pregnancy currentPregnancy; // Pregnancy that is under assesment
    Calendar startTime; // Time when assesment started

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assesment);

        // Connection with database
        dbo = new DBOperations(getApplicationContext());

        // Get data from intent
        data = getIntent().getExtras();
        currentPregnancy = dbo.findPregnancy(data.getInt("_id"));

        // Bind views
        etHRate = (EditText) findViewById(R.id.etRate);
        etOxygen = (EditText) findViewById(R.id.etOxygen);
        btnAdd = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        // Register start time of assesment
        startTime = Calendar.getInstance();

        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                String hr = etHRate.getText().toString(); // heart rate
                String ox = etOxygen.getText().toString(); // oxygen

                if (btnAdd.isPressed()){
                    if(!hr.matches("") && !ox.matches("")){ // Add new assesment
                        if(saveAssesment(Integer.parseInt(hr), Integer.parseInt(ox)) != 0){
                            Toast.makeText(getApplicationContext(), "Revisión agregada correctamente",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error, no se agrego revisión",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else { // Avoid empty values
                        Toast.makeText(getApplicationContext(), "Campos vacios",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if(btnCancel.isPressed()){ // Cancel activity
                    Toast.makeText(getApplicationContext(), "Revisión cancelada",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        // Register the buttons to the listener
        btnAdd.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

    }

    // TODO IMPLEMENT CORRECT EVALUATION
    public int getMedicalEvaluation(int hr, int ox){
        int evaluation = 0;
        return evaluation;
    }

    /**
     * Method that adds an assesment to the database by sending an Assesment objetc with
     * five paraments, also the id of the pregnancy is used.
     * @param hr the heart beat
     * @param ox the oxygen in blood
     * @return a number different than zero if the insert was successful
     */
    public int saveAssesment(int hr, int ox){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -1);
        startTime.add(Calendar.HOUR, -1);
        String start = df.format(startTime.getTime());
        String end = df.format(c.getTime());

        Assesment a = new Assesment(currentPregnancy.getId(), start, end, hr, ox,
                getMedicalEvaluation(hr, ox));

        return dbo.addAssesment(currentPregnancy, a);
    }


}
