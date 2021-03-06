package itesm.mx.mamicare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

/**
 * Created by Alfredo Hinojosa on 11/22/2015.
 */
public class PatientListAdapter extends ArrayAdapter<Patient> {
    int layoutResourceId;
    List<Patient> adapterList;
    DBOperations dbo; // Database API

    private Context context;

    public PatientListAdapter(Context context, int idResource, List<Patient> Patients){
        super(context, idResource, Patients);
        this.context = context;
        this.layoutResourceId = idResource;
        this.adapterList = Patients;
    }

    public List<Patient> getData(){
        return adapterList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;

        // Database connection
        dbo = new DBOperations(context);

        // convertView
        if(row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        // Bind views
        TextView name = (TextView) row.findViewById(R.id.tv_PatientName);
        TextView pregWeek = (TextView) row.findViewById(R.id.tv_PregnancyWeek);
        TextView lastCheck = (TextView) row.findViewById(R.id.tv_LastCheck);
        ImageView profilePic = (ImageView) row.findViewById(R.id.imv_PatientPhoto);


        // Load data
        Patient patient = adapterList.get(position);
        name.setText(patient.getName());

        // Get date of last assesment
        String last = dbo.getLastAssesment(patient);
        if(last != null) {
            lastCheck.setText("Fecha de ultima revisión: " + last);
        } else {
            lastCheck.setText("No existen revisiones previas");
        }

        // Get active pregnancy
        Pregnancy p = dbo.findActivePregnancy(patient);
        if(p != null){
            pregWeek.setText("Semana " + dbo.getPassedWeeks(p.getId()));
        } else {
            pregWeek.setText("Embarazo no registrado");
        }

        // Set Patient image
        if(patient.getPhoto_path() != null){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap imageBitmap = BitmapFactory.decodeFile(patient.getPhoto_path(), options);
            profilePic.setImageBitmap(imageBitmap);
        } else { // The patient has no photo
            profilePic.setImageResource(R.drawable.nophoto);
        }


        return row;
    }
}

