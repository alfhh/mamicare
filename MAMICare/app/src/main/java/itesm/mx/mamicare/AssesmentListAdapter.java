package itesm.mx.mamicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

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
        date.setText(assmnt.getStart_date());
        duration.setText("2 horas"); // TODO FIX THIS
        bloodP.setText("" + assmnt.getOxygen() +"/" + assmnt.gethRate());
        evaluation.setText(String.valueOf(assmnt.getAlert()));

        return row;
    }

}
