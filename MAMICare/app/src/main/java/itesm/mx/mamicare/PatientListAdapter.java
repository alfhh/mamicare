package itesm.mx.mamicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alfredo Hinojosa on 11/22/2015.
 */
public class PatientListAdapter extends ArrayAdapter<Patient> {
    int layoutResourceId;
    List<Patient> adapterList;

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
        //pregWeek.setText(patient.getPregnancyWeek());
        lastCheck.setText(patient.getLastCheck());
        //profilePic.setImageResource(patient.getPhoto_path()); TODO FIX

        return row;
    }
}

