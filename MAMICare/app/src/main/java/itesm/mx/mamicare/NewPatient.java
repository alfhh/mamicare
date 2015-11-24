package itesm.mx.mamicare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewPatient extends Activity {

    Button btnSubmit; // Button to save new patient
    Button btnCancel; // Cancel the submission
    DatePicker datepick; // DatePicker
    ImageView imvProfile; // ImageView profile
    private File imageFile; // To set the destination of the photo
    String imagePath = ""; // Path referencing the imvProfile
    static final int REQUEST_IMAGE_CAPTURE = 1; // Code for picture intent

    /**
     * Used to return the date selected by the user in a correct format to handle
     * in the data base.
     * @return formated string date
     */
    String getDate(){

        String date;
        date = String.valueOf(datepick.getDayOfMonth()); //TODO fix starting @ zero
        date += "-";
        date += String.valueOf(datepick.getMonth());
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
            Toast.makeText(getApplicationContext(), "Path: " + imageFile.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // Sets the size of the photo

            imagePath = imageFile.getAbsolutePath(); // Save the path for BD
            Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
            imvProfile.setImageBitmap(imageBitmap);
        }
    }

    public boolean submitPatient(String name, String address, String bday ){
        boolean result = false;



        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        // Bind views
        btnSubmit = (Button) findViewById(R.id.btn_AddNewPatient);
        btnCancel = (Button) findViewById(R.id.btn_CancelNewPatient);
        datepick = (DatePicker) findViewById(R.id.datePicker);
        imvProfile = (ImageView) findViewById(R.id.imv_PatientPhoto);

        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (btnSubmit.isPressed()){
                    //Log.d("Name", )
                    //finish();
                } else if(btnCancel.isPressed()){
                    Toast.makeText(getApplication(), "Registro cancelado",
                            Toast.LENGTH_SHORT).show();
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
