package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ImageButton btn_Addpatient; // Used to add a new patient

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
        initializeAdapter(); // Initialize the adapter

        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (btn_Addpatient.isPressed()){
                    Toast.makeText(getApplication(), "Create patient", Toast.LENGTH_SHORT).show();
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
        pacientes.add(new Patient("Rosa Jimenez", "Semana 12", R.drawable.photorosa));
        pacientes.add(new Patient("Brenda Hernandez", "Semana 30", R.drawable.emma));
        pacientes.add(new Patient("Teresa Ramirez", "Semana 4", R.drawable.lavery));
        pacientes.add(new Patient("Guadalupe Gonzales", "Semana 20", R.drawable.lillie));
    }

    /**
     * Initialize the adapter to display the cards
     */
    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(pacientes);
        rv.setAdapter(adapter);
    }

}
