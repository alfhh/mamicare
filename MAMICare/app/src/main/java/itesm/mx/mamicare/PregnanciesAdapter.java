package itesm.mx.mamicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

/**
 * Created by Alfredo Hinojosa on 11/28/2015.
 */
public class PregnanciesAdapter extends ArrayAdapter<Pregnancy> {
    int layoutResourceId;
    List<Pregnancy> adapterList;
    DBOperations dbo; // Database API

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
        TextView startDate = (TextView) row.findViewById(R.id.tvActiveStartDate);
        TextView actualWeek = (TextView) row.findViewById(R.id.tvActiveActualWeek);
        TextView remainingWeeks = (TextView) row.findViewById(R.id.tvActiveRemainingWeeks);
        TextView alert = (TextView) row.findViewById(R.id.tvActiveAlert);
        TextView endDate = (TextView) row.findViewById(R.id.tvActiveEndDate);


        // Load data
        Pregnancy pregnancy = adapterList.get(position);

        // Default
        startDate.setText(pregnancy.getPregnancyStart());

        // Pregancy ended
        if(pregnancy.getAlert() == -1){
            actualWeek.setText("Embarazo finalizado");
            remainingWeeks.setText("Faltan 0 semenas");
            alert.setText("Embarazo finalizado");
            endDate.setText(pregnancy.getPregnancyEnd());

        } else { // Actual pregnancy

            int weeks = dbo.getPassedWeeks(pregnancy.getId());
            actualWeek.setText("Semana " + weeks);
            remainingWeeks.setText("Faltan " + (49 - weeks)  + " semanas");
            alert.setText(dbo.transformAlerttoString(pregnancy.getAlert()));
            endDate.setText("Embarazo actual");

        }

        return row;
    }
}
