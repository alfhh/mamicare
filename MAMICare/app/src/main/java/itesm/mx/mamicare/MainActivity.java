package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<Patient> pacientes; // List of the current patients
    private RecyclerView rv; // Handle to the recycler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the recycler view
        rv = (RecyclerView) findViewById(R.id.theRecyclerView);

        // This makes the RecyclerView behave as a ListView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);// used to improve performance

        initializeData(); // Load the sample data
        initializeAdapter(); // Initialize the adapter
    }

    private void initializeData(){
        pacientes = new ArrayList<>();
        pacientes.add(new Patient("Rosa Jimenez", "Semana 12", R.drawable.photorosa));
        pacientes.add(new Patient("Brenda Hernandez", "Semana 30", R.drawable.emma));
        pacientes.add(new Patient("Teresa Ramirez", "Semana 4", R.drawable.lavery));
        pacientes.add(new Patient("Guadalupe Gonzales", "Semana 20", R.drawable.lillie));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(pacientes);
        rv.setAdapter(adapter);
    }

}
