package itesm.mx.mamicare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alfredo Hinojosa on 10/26/2015.
 */
public class  RVAdapter extends RecyclerView.Adapter<RVAdapter.PatienViewHolder> {

    private Context context;


    class PatienViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv; // The Material Design card
        TextView patientName; // Name of the patient
        TextView preganancyWeek; // Week of pregnancy of the patient
        TextView lastCheck; // The date of the last health check
        ImageView patientPhoto; // Photo of the patient
        Intent intent; // Used to go to other activity

        // Initialize the views that compose the Card (aka cv)
        PatienViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            patientName = (TextView) itemView.findViewById(R.id.tv_PatientName);
            preganancyWeek = (TextView) itemView.findViewById(R.id.tv_PregnancyWeek);
            lastCheck = (TextView) itemView.findViewById(R.id.tv_LastCheck);
            patientPhoto = (ImageView) itemView.findViewById(R.id.imv_PatientPhoto);

            // Listener used detect a click in a card
            cv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("USEFUL LOG", "Card of " + patientName.getText() + " clicked");

                }
            });
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, NewPatient.class));
        }
    }

    // Get the list of all the patients
    List<Patient> pacientes;

    // Constructor of the RVAdapter receiving a list of patients
    public  RVAdapter(Context context, List<Patient> pacientes){
        this.pacientes = pacientes;
        this.context = context;
    }

    /**
     * Abstract method of RecyclerView.Adapter
     * Attach the recyclerview
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Abstract method of RecyclerView.Adapter
     * Called when the custom ViewHolder needs to be initialized.
     * Inflates the layout using LayoutInflater, passing the output
     *  to the constructor of the custom ViewHolder.
     * @param viewGroup
     * @param i
     * @return The holder
     */
    @Override
    public PatienViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,
                viewGroup, false);
        PatienViewHolder pvh = new PatienViewHolder(v);
        return pvh;
    }

    /**
     * Abstract method of RecyclerView.Adapter
     * Used to specify the contents of each item of the RecyclerView.
     * @param personViewHolder the patient
     * @param i the position of the patient in the list
     */
    @Override
    public void onBindViewHolder(PatienViewHolder personViewHolder, final int i) {
        personViewHolder.patientName.setText(pacientes.get(i).getName());
        //personViewHolder.preganancyWeek.setText(pacientes.get(i).getPregnancyWeek());
        //personViewHolder.patientPhoto.setImageResource(pacientes.get(i).getPhoto_path()); TODO FIX
        personViewHolder.lastCheck.setText(pacientes.get(i).getLastCheck());
    }

    /**
     * Abstract method of RecyclerView.Adapter
     * Returns the size of the data
     * @return Integer, size of the list of patients
     */
    @Override
    public int getItemCount() {
        return pacientes.size();
    }

}
