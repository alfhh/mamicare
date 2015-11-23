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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    List<Patient> pacientes; // List of the current patients
    ListView pacientList; // ListView with patients
    ImageButton btn_Addpatient; // Used to add a new patient
    Patient selectedPatient; // Used for getting the view
    PatientListAdapter patientAdapter;

    /**
     * Goes to PatientProfile activity
     * @param p patient selected from the ListView
     */
    public void goToPatientProfile(Patient p){
        Intent i;

        if(p != null){ // TODO CHANGE THIS WITH DB OPERATIONS
            i = new Intent(MainActivity.this, PatientProfile.class);
            i.putExtra("name", p.getName());
            i.putExtra("address", p.getAddress());
            i.putExtra("pregweek", p.getPregnancyWeek());
            i.putExtra("bday", p.getBirthday());
            i.putExtra("lastcheck", p.getLastCheck());
            i.putExtra("img", p.getPhoto_path());

            startActivity(i);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views
        pacientList = (ListView) findViewById(R.id.lvPatient);
        btn_Addpatient = (ImageButton) findViewById(R.id.btnNewPatient);
        initializeData(); // Load the sample data
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
                    startActivity(intent); // Go to activity to add new patient
                }
            }
        };
        // Register the buttons to the listener
        btn_Addpatient.setOnClickListener(listener);
    }

    /**
     * Initialize dummy data
     */
    private void initializeData(){
        pacientes = new ArrayList<>();
        pacientes.add(new Patient("Rosa Jimenez", "Semana de emabarazo actual: 12",
                null, "Ultima revisión: 10 de agosto 2015"));

        pacientes.add(new Patient("Brenda Hernandez", "Semana de emabarazo actual:30",
                null, "Ultima revisión: 15 de agosto 2015"));

        pacientes.add(new Patient("Teresa Ramirez", "Semana de emabarazo actual: 4",
                null, "Ultima revisión: 1 de septiembre 2015"));

        pacientes.add(new Patient("Guadalupe Gonzales", "Semana de emabarazo actual: 20",
                null, "Ultima revisión: 10 de septiembre 2015"));
    }


}
