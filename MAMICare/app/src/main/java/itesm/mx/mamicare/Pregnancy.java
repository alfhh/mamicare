package itesm.mx.mamicare_db_testing;

/**
 * Created by SergioJesúsCorderoBa on 11/2/2015.
 */
public class Pregnancy {

    private int id;
    private int patient_id;
    private int alert;
    private String pregnancyStart;

    public Pregnancy(int id, int patient_id, int alert, String pregnancyStart) {
        this.id = id;
        this.patient_id = patient_id;
        this.alert = alert;
        this.pregnancyStart = pregnancyStart;
    }

    public Pregnancy(int patient_id, int alert, String pregnancyStart) {
        this.patient_id = patient_id;
        this.alert = alert;
        this.pregnancyStart = pregnancyStart;
    }

    public Pregnancy(int alert, String pregnancyStart) {
        this.alert = alert;
        this.pregnancyStart = pregnancyStart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getAlert() {
        return alert;
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }

    public String getPregnancyStart() {
        return pregnancyStart;
    }

    public void setPregnancyStart(String pregnancyStart) {
        this.pregnancyStart = pregnancyStart;
    }
}
