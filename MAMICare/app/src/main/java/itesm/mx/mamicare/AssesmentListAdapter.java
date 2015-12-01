package itesm.mx.mamicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

/**
 * Created by Alfredo Hinojosa on 11/30/2015.
 */
public class AssesmentListAdapter extends ArrayAdapter<Assesment> {
    int layoutResourceId;
    List<Assesment> adapterList;
    DBOperations dbo; // Database API

    private Context context;

    public AssesmentListAdapter(Context context, int idResource, List<Assesment> assesments){
        super(context, idResource, assesments);
        this.context = context;
        this.layoutResourceId = idResource;
        this.adapterList = assesments;
    }

    public List<Assesment> getData(){
        return adapterList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Database connection
        dbo = new DBOperations(context);
        View row = convertView;

        // convertView
        if(row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        // Bind views
        TextView date = (TextView) row.findViewById(R.id.tvActiveStartDate);
        TextView duration = (TextView) row.findViewById(R.id.tvActiveDuration);
        TextView bloodP = (TextView) row.findViewById(R.id.tvActiveBloodP);
        TextView evaluation = (TextView) row.findViewById(R.id.tvActiveAlert);


        // Load data
        Assesment assmnt = adapterList.get(position);

       // Bind views
        bloodP.setText("" + assmnt.getOxygen() +"/" + assmnt.gethRate());
        evaluation.setText(dbo.transformAlerttoString(assmnt.getAlert()));

        // Fix hour issues
        String theDate = "";
        String duracion = "";

        String start_dt = assmnt.getStart_date();
        String end_dt = assmnt.getEnd_date();

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date tempDate =  formatter.parse(start_dt);
            Date temp2 = formatter.parse(end_dt);

            long diff = temp2.getTime() - tempDate.getTime(); // Get difference
            diff = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            duracion = String.valueOf(diff);
            theDate = newFormat.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        date.setText(theDate);
        duration.setText(duracion);

        return row;
    }

}
