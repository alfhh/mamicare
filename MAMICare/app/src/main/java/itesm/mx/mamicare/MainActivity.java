package itesm.mx.mamicare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ImageButton btn_Addpatient; // Used to add a new patient
    Patient selectedPatient; // Used for getting the view

    private List<Patient> pacientes; // List of the current patients
    private RecyclerView rv; // Handle to the recycler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the recycler view
        rv = (RecyclerView) findViewById(R.id.theRecyclerView);

        // Initialize views
        btn_Addpatient = (ImageButton) findViewById(R.id.btnNewPatient);

        // This makes the RecyclerView behave as a ListView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);// used to improve performance

        initializeData(); // Load the sample data

        final RVAdapter adapter = new RVAdapter(getApplicationContext(),pacientes);
        rv.setAdapter(adapter);


        // Listener for buttons
        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent;

                if (btn_Addpatient.isPressed()){
                    // TODO CHANGE THE ACTION OF THIS BUTTON
                    intent = new Intent(MainActivity.this, NewPatient.class);
                    //intent = new Intent(MainActivity.this, PatientProfile.class);
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
                R.drawable.photorosa, "Ultima revisión: 10 de agosto 2015"));

        pacientes.add(new Patient("Brenda Hernandez", "Semana de emabarazo actual:30",
                R.drawable.emma, "Ultima revisión: 15 de agosto 2015"));

        pacientes.add(new Patient("Teresa Ramirez", "Semana de emabarazo actual: 4",
                R.drawable.lavery, "Ultima revisión: 1 de septiembre 2015"));

        pacientes.add(new Patient("Guadalupe Gonzales", "Semana de emabarazo actual: 20",
                R.drawable.lillie, "Ultima revisión: 10 de septiembre 2015"));
    }


}
