package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class NewPatient extends Activity {

    Button btnSubmit; // Button to save new patient
    Button btnCancel; // Cancel the submission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

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
