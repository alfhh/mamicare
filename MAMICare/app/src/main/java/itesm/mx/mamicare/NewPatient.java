package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class NewPatient extends Activity {

    //databse operations
    DBOperations dao;//databse operations

    Button btnSubmit; // Button to save new patient
    Button btnCancel; // Cancel the submission
    EditText etPatientName;
    EditText etPatientAddress;
    EditText etPregnancyWeek;

    public void newPatient(View view) {
        //get variables from interface
        //int id = dao.getProductsCount()+1;
        String name = etPatientName.getText().toString();
        int pregnancyWeek = Integer.parseInt(etPregnancyWeek.getText().toString());
        String address = etPatientAddress.getText().toString();

        //declare and create product
        Patient newPatient = new Patient(address, "2015-10-01", "2015-01-01", name);
        Pregnancy newPregnancy = new Pregnancy(1, 1, 5);

        //call product operations method addPatient
        int patient_id = dao.addPatient(newPatient);
        dao.addPregnancy(patient_id, newPregnancy);

        //toast that product was added
        Toast.makeText(getApplicationContext(), "Patient added", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        etPatientName = (EditText) findViewById(R.id.et_PatientName);
        etPatientAddress = (EditText) findViewById(R.id.et_PatientAddresss);
        etPregnancyWeek = (EditText) findViewById(R.id.et_PregnancyWeek);

        btnSubmit = (Button) findViewById(R.id.btn_AddNewPatient);
        btnCancel = (Button) findViewById(R.id.btn_CancelNewPatient);

        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (btnSubmit.isPressed()){
                    Toast.makeText(getApplication(), "Paciente agregado",
                            Toast.LENGTH_SHORT).show();
                            finish();
                } else if(btnCancel.isPressed()){
                    Toast.makeText(getApplication(), "Registro cancelado",
                            Toast.LENGTH_SHORT).show();
                            finish();
                }
            }
        };
        // Register the buttons to the listener
        btnSubmit.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
