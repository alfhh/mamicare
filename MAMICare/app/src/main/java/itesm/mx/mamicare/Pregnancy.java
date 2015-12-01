package itesm.mx.mamicare;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

/**
 * Created by SergioJesúsCorderoBa on 11/2/2015.
 */
public class Pregnancy {

    private int id;
    private int patient_id;
    private int alert;
    private String pregnancyStart;
    private String pregnancyEnd;

    public Pregnancy(int id, int patient_id, int alert, String pregnancyStart, String end) { // Of List
        this.id = id;
        this.patient_id = patient_id;
        this.alert = alert;
        this.pregnancyStart = pregnancyStart;
        this.pregnancyEnd = end;
    }

    public Pregnancy(int patient_id, int alert, String pregnancyStart) {// For creating a record
        this.patient_id = patient_id;
        this.alert = alert;
        this.pregnancyStart = pregnancyStart;
    }

    public Pregnancy(int alert, String pregnancyStart) {
        this.alert = alert;
        this.pregnancyStart = pregnancyStart;
    }


    public String getPregnancyEnd() {
        return pregnancyEnd;
    }

    public void setPregnancyEnd(String pregnancyEnd) {
        this.pregnancyEnd = pregnancyEnd;
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
