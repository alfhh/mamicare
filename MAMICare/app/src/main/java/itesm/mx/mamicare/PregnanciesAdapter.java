package itesm.mx.mamicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alfredo Hinojosa on 11/28/2015.
 */
public class PregnanciesAdapter extends ArrayAdapter<Pregnancy> {
    int layoutResourceId;
    List<Pregnancy> adapterList;

    private Context context;

    public PregnanciesAdapter(Context context, int idResource, List<Pregnancy> pregnancies){
        super(context, idResource, pregnancies);
        this.context = context;
        this.layoutResourceId = idResource;
        this.adapterList = pregnancies;
    }

    public List<Pregnancy> getData(){
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
        TextView startDate = (TextView) row.findViewById(R.id.tvActiveStartDate);
        TextView actualWeek = (TextView) row.findViewById(R.id.tvActiveActualWeek);
        TextView remainingWeeks = (TextView) row.findViewById(R.id.tvActiveRemainingWeeks);
        TextView alert = (TextView) row.findViewById(R.id.tvActiveAlert);
        TextView endDate = (TextView) row.findViewById(R.id.tvActiveEndDate);


        // Load data
        Pregnancy pregnancy = adapterList.get(position);

        // Default
        startDate.setText(pregnancy.getPregnancyStart());
        alert.setText(String.valueOf(pregnancy.getAlert()));
        endDate.setText("Embarazo actual");

        // Extra dates TODO IMPLEMENT CALENDAR FUNCTIONS
        actualWeek.setText("Semana 1");
        remainingWeeks.setText("Faltan 39 semanas");

        // Possible null values
        if(pregnancy.getPregnancyEnd() != null)
            endDate.setText(pregnancy.getPregnancyEnd());


        return row;
    }
}
