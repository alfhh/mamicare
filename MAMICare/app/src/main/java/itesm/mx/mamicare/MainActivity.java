package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView patientName;
    TextView pregnancyWeek;
    ImageView patientPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientName = (TextView) findViewById(R.id.tv_PatientName);
        pregnancyWeek = (TextView) findViewById(R.id.tv_PregnancyWeek);
        patientPhoto = (ImageView) findViewById(R.id.imv_PatientPhoto);

        patientName.setText("Emma Wilson");
        pregnancyWeek.setText("La semana es 16");
        patientPhoto.setImageResource(R.drawable.photorosa);

    }

}
