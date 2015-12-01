package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

public class AssesmentList extends Activity {

    Bundle data; // Data from the intent
    DBOperations dbo; // Database API
    ImageView imvEmptyList; // Image displayed when no elements are on the list
    List<Assesment> assesments; // List of assesments
    ListView asstList; // ListView with assesments
    AssesmentListAdapter assmtAdapter; // Adapter used for the ListView
    Pregnancy currentPregnancy; // The activePregnancy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment_list);

        // Connection with database
        dbo = new DBOperations(getApplicationContext());

        // Get data from intent
        data = getIntent().getExtras();
        currentPregnancy = dbo.findPregnancy(data.getInt("_id"));

        // Bind views
        asstList = (ListView) findViewById(R.id.lvAssesments);
        imvEmptyList = (ImageView) findViewById(R.id.imvListaVacia);

        // Avoid errors by checking if there are any previous pregnancies
        if(dbo.getAssesmentsCountFromPregnancy(currentPregnancy) == 0){
            assesments = new ArrayList<>();
            imvEmptyList.setVisibility(View.VISIBLE);
        } else { // Load data from DB
            imvEmptyList.setVisibility(View.INVISIBLE);
            assesments = dbo.getAllAssesmentsFromPregnancy(currentPregnancy);
        }

        assmtAdapter = new AssesmentListAdapter(getApplicationContext(), R.layout.assesment_row,
                assesments);
        asstList.setAdapter(assmtAdapter);

    }

}
