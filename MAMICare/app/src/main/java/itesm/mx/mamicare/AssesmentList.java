package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class AssesmentList extends Activity {

    Bundle data; // Data from the intent
    DBOperations dbo; // Database API
    List<Assesment> assesments; // List of assesments
    ListView asstList; // ListView with assesments
    PregnanciesAdapter pregAdapter; // Adapter used for the ListView
    Pregnancy currentPregnancy; // The activePregnancy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment_list);
    }

}
