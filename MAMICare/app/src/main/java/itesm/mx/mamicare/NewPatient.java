package itesm.mx.mamicare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewPatient extends Activity {

    Button btnSubmit; // Button to save new patient
    Button btnCancel; // Cancel the submission
    DatePicker datepick; // DatePicker
    DBOperations dbo; // Database API
    ImageView imvProfile; // ImageView profile
    private File imageFile; // To set the destination of the photo
    String imagePath = null; // Path referencing the imvProfile
    static final int REQUEST_IMAGE_CAPTURE = 1; // Code for picture intent
    EditText etName; // EditText of the patient's name
    EditText etAddress; // EditText of the patient's address

    /**
     * Used to return the date selected by the user in a correct format to handle
     * in the data base.
     * @return formated string date
     */
    String getBirthDate(){

        String date;
        date = String.valueOf(datepick.getDayOfMonth());
        date += "-";
        date += String.valueOf(datepick.getMonth() + 1);
        date += "-";
        date += String.valueOf(datepick.getYear());
        return date;
    }

    /**
     * Returns the system date formated to be used as name of the image.
     * @return
     */
    public String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy_hh-mm-ss");
        return df.format(c.getTime());
    }

    /**
     * Called after photo is taken, it also sets the imvProfile to the photo taken.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // Sets the size of the photo

            imagePath = imageFile.getAbsolutePath(); // Save the path for BD
            Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
            imvProfile.setImageBitmap(imageBitmap);
        }
    }

    /**
     * Method that makes the insert in the database to add a new patient
     * @param name of the patient
     * @param address of the patient
     * @return a boolean which is true if the insert is correct
     */
    public boolean submitPatient(String name, String address){
        boolean result = false;

        Patient p = new Patient(name, address, null, getBirthDate(), imagePath);
        if(dbo.addPatient(p) != -1) {
            result = true; // patient correctly added
        }

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        // Initialize DB connection
        dbo = new DBOperations(getApplicationContext());

        // Bind views
        btnSubmit = (Button) findViewById(R.id.btn_AddNewPatient);
        btnCancel = (Button) findViewById(R.id.btn_CancelNewPatient);
        datepick = (DatePicker) findViewById(R.id.datePicker);
        imvProfile = (ImageView) findViewById(R.id.imv_PatientPhoto);
        etName = (EditText) findViewById(R.id.et_PatientName);
        etAddress = (EditText) findViewById(R.id.et_PatientAddress);

        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (btnSubmit.isPressed()){
                    String name = etName.getText().toString();
                    String address = etAddress.getText().toString();

                    if(!name.matches("") && !address.matches("")){ // Checks for empty values
                        if(submitPatient(name, address)){
                            setResult(RESULT_OK);
                            finish(); // Return to MainActivity
                        } else { // DB insert not correct
                            Toast.makeText(getApplicationContext(),
                                    "Error: paciente no agregada", Toast.LENGTH_LONG).show();
                        }

                    } else { // Informs the user which ET is empty
                        if(name.matches("")){
                            Toast.makeText(getApplicationContext(), "Falta llenar nombre",
                                    Toast.LENGTH_LONG).show();
                        } else if(address.matches("")){
                            Toast.makeText(getApplicationContext(), "Falta llenar dirección",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                } else if(btnCancel.isPressed()){ // Operation canceled
                            finish();
                } else if(imvProfile.isPressed()){

                    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // The name of the file is the name of the patient
                    imageFile = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), getCurrentDate()+".jpg");
                    Uri tempUri = Uri.fromFile(imageFile);

                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri); // Destination
                    pictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // Picture quality

                    if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        };
        // Register the buttons to the listener
        btnSubmit.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        imvProfile.setOnClickListener(listener);
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
