package itesm.mx.mamicare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

public class MainActivity extends Activity {

    DBOperations dbo; // Database API
    final int NEW_PATIENT = 1;
    List<Patient> pacientes; // List of the current patients
    ListView pacientList; // ListView with patients
    ImageButton btn_Addpatient; // Used to add a new patient
    Patient selectedPatient; // Used for getting the view
    PatientListAdapter patientAdapter; // Adapter used for the ListView of Cards

    /**
     * Goes to PatientProfile activity
     * @param p patient selected from the ListView
     */
    public void goToPatientProfile(Patient p){
        Intent i;

        if(p != null){
            i = new Intent(MainActivity.this, PatientProfile.class);
            i.putExtra("_id", p.getId());
            startActivity(i);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        reloadData();
    }

    /**
     * Method that reloads all the patients registered in the database and then updates the view
     * sending the new List to the adapter
     * @return a List of patients
     */
    public void reloadData(){
        pacientes = dbo.getAllPatients();
        patientAdapter.getData().clear();
        patientAdapter.getData().addAll(pacientes);
        patientAdapter.notifyDataSetChanged();
    }

    /**
     * Method that handles the returning values of the intents
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 1: // Returns after adding a new patient
                    Toast.makeText(getApplicationContext(), "Paciente agregada correctamente",
                            Toast.LENGTH_SHORT).show();
                    reloadData();
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode){
                case 1: // Canceled new patient
                    Toast.makeText(getApplicationContext(), "Operación Cancelada",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database connection
        dbo = new DBOperations(getApplicationContext());

        // Bind views
        pacientList = (ListView) findViewById(R.id.lvPatient);
        btn_Addpatient = (ImageButton) findViewById(R.id.btnNewPatient);
        pacientes = dbo.getAllPatients(); // Load the actual patients in the database
        patientAdapter = new PatientListAdapter(getApplicationContext(), R.layout.item, pacientes);
        pacientList.setAdapter(patientAdapter);


        // Listener for the ListView
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedPatient =  patientAdapter.getItem(position); // Get te patient
                Log.d("PATIENT INFO", "Selected: " + selectedPatient.getName());
                goToPatientProfile(selectedPatient);
            }

        };
        pacientList.setOnItemClickListener(itemListener);

        // Listener for buttons
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;

                if (btn_Addpatient.isPressed()){
                    intent = new Intent(MainActivity.this, NewPatient.class);
                    startActivityForResult(intent, NEW_PATIENT); // Go to activity to add new patient
                }
            }
        };
        // Register the buttons to the listener
        btn_Addpatient.setOnClickListener(listener);
    }
}
