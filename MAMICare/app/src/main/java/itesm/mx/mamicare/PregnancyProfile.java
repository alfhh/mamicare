package itesm.mx.mamicare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

public class PregnancyProfile extends Activity {

    Bundle data; // Data from the intent
    DatePicker datePicker; // Date picker dialog
    Calendar calendar; // Used to get dates
    DBOperations dbo; // Database API
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
        embarazos = dbo.getAllPregnanciesFromPatient(currentPatient);
        pregAdapter = new PregnanciesAdapter(getApplicationContext(),
                R.layout.pregnancy_row, embarazos);
        pregList.setAdapter(pregAdapter);

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
                    pregnancyButton();
                }
            }
        };
        // Register the buttons to the listener
        btn_Addpregnancy.setOnClickListener(listener);

    }

    public void pregnancyButton(){
        setDate(datePicker);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Log.d("DATEPICKER", "DONE");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    // DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int arg1, int arg2, int arg3) {
            Log.d("DATE SET", "Year: " + arg1 + " Month: " + (arg2 + 1) + " Day: " + arg3);
            Pregnancy p = new Pregnancy(0, ""+arg3+"-"+(arg2+1)+"-"+arg1);
            dbo.addPregnancy(currentPatient, p);
        }
    };
}
