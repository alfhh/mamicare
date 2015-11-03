package itesm.mx.mamicare;

/**
 * Created by SergioJesúsCorderoBa on 11/2/2015.
 */
public class Pregnancy {

    private int id;
    private int patient_id;
    private int alert;
    private int pregnancyWeek;

    public Pregnancy(int patient_id, int alert, int pregnancyWeek) {
        this.patient_id = patient_id;
        this.alert = alert;
        this.pregnancyWeek = pregnancyWeek;
    }

    public Pregnancy(int pregnancyWeek) {
        this.pregnancyWeek = pregnancyWeek;
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

    public int getPregnancyWeek() {
        return pregnancyWeek;
    }

    public void setPregnancyWeek(int pregnancyWeek) {
        this.pregnancyWeek = pregnancyWeek;
    }
}
