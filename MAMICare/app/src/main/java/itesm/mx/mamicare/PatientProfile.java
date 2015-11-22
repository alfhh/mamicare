package itesm.mx.mamicare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PatientProfile extends Activity {

    Bundle data; // Data from the intent
    TextView tvUserName;
    TextView tvUserAddress;
    TextView tvUserPregWeek;
    TextView tvUserBday;
    TextView tvUserLastCheck;
    ImageView imvUserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        // Get data from intent
        data = getIntent().getExtras();

        // Bind views
        tvUserName = (TextView) findViewById(R.id.tv_ActiveUserName);
        tvUserAddress = (TextView) findViewById(R.id.tv_ActiveUserAddress);
        tvUserPregWeek = (TextView) findViewById(R.id.tv_ActiveUserPregWeek);
        tvUserBday = (TextView) findViewById(R.id.tv_ActiveUserBirthday);
        tvUserLastCheck = (TextView) findViewById(R.id.tv_ActiveUserLastCheck);
        imvUserPhoto = (ImageView) findViewById(R.id.imv_PatientPhoto);

        // Set data to the views
        tvUserName.setText(data.getString("name"));
        tvUserAddress.setText(data.getString("address"));
        tvUserPregWeek.setText(data.getString("pregweek"));
        tvUserBday.setText(data.getString("bday"));
        tvUserLastCheck.setText(data.getString("lastcheck"));
        imvUserPhoto.setImageResource(data.getByte("img"));

    }

}
