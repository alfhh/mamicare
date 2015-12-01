package itesm.mx.mamicare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

public class PregnancyProfile extends Activity {

    Bundle data; // Data from the intent
    DatePicker datePicker; // Date picker dialog
    Calendar calendar; // Used to get dates
    DBOperations dbo; // Database API
    ImageView imvEmptyList; // Image displayed when no elements are on the list
    List<Pregnancy> embarazos; // List of pregnancies
    ListView pregList; // ListView with pregnancies
    ImageButton btn_Addpregnancy; // Used to add a new pregnancy
    PregnanciesAdapter pregAdapter; // Adapter used for the ListView
    Patient currentPatient; // The selected patient
    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_profile);

        // Connection with database
        dbo = new DBOperations(getApplicationContext());

        // Get data from intent
        data = getIntent().getExtras();
        currentPatient = dbo.findPatient(data.getInt("_id"));

        // Bind views
        pregList = (ListView) findViewById(R.id.lvPregnancies);
        btn_Addpregnancy = (ImageButton) findViewById(R.id.btnNewPregnancy);
        imvEmptyList = (ImageView) findViewById(R.id.imvListaVacia);

        // Avoid errors by checking if there are any previous pregnancies
        if(dbo.getPregnanciesCountFromPatient(currentPatient) == 0){
            embarazos = new ArrayList<>();
            imvEmptyList.setVisibility(View.VISIBLE);
        } else { // Load data from DB
            imvEmptyList.setVisibility(View.INVISIBLE);
            embarazos = dbo.getAllPregnanciesFromPatient(currentPatient);
        }

        pregAdapter = new PregnanciesAdapter(getApplicationContext(),
                R.layout.pregnancy_row, embarazos);
        pregList.setAdapter(pregAdapter);
        registerForContextMenu(pregList);

        // Building DateDialog helpers
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Listener for add button
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_Addpregnancy.isPressed()){
                    setDate(datePicker);
                }
            }
        };
        // Register the buttons to the listener
        btn_Addpregnancy.setOnClickListener(listener);

    }

    /**
     * Method that reloads all the data in the views when info is updated
     */
    private void reloadData(){
        pregAdapter.getData().clear();
        pregAdapter.getData().addAll(dbo.getAllPregnanciesFromPatient(currentPatient));
        pregAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Log.d("DATEPICKER", "DONE");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    // DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int arg1, int arg2, int arg3) {
            Pregnancy p = new Pregnancy(0, ""+arg3+"-"+(arg2+1)+"-"+arg1);

            // Check if there is any active pregnancy, if true then update to end and add the new one
            // Else just add the new pregnancy
            Pregnancy temp = dbo.findActivePregnancy(currentPatient);
            if(temp != null){
                dbo.endPregnancy(temp.getId());
                dbo.addPregnancy(currentPatient, p); // <- Add to DataBase
            } else {
                dbo.addPregnancy(currentPatient, p); // <- Add to DataBase
            }

            reloadData();
            Toast.makeText(getApplicationContext(), "Embarazo registrado", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        getMenuInflater().inflate(R.menu.menu_pregnancy_item, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        Pregnancy selectedP = embarazos.get(info.position);

        if (id == R.id.end){
            if(selectedP.getAlert() != -1){ // Update in BD
                if(dbo.endPregnancy(selectedP.getId()) != 0) {
                    reloadData();
                    Toast.makeText(getApplicationContext(),
                            "Embarazo finalizado", Toast.LENGTH_SHORT).show();
                }
            } else { // Don't update, pregnancy already finished
                Toast.makeText(getApplicationContext(),
                        "Embarazo ya finalizado", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
