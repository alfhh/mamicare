package itesm.mx.mamicare;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

/**
 * Created by SergioJesúsCorderoBa on 11/20/2015.
 */
public class Assesment {
    private int id;
    private int pregnancy_id;
    private String start_date;
    private String end_date;
    private int hRate;
    private int oxygen;
    private int alert;

    public Assesment(int id, int pregnancy_id, String start_date, String end_date, int hRate, int oxygen, int alert) {
        this.id = id;
        this.pregnancy_id = pregnancy_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.hRate = hRate;
        this.oxygen = oxygen;
        this.alert = alert;
    }

    public Assesment(int pregnancy_id, String start_date, String end_date, int hRate, int oxygen, int alert) {
        this.pregnancy_id = pregnancy_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.hRate = hRate;
        this.oxygen = oxygen;
        this.alert = alert;
    }

    public Assesment(String start_date, String end_date, int hRate, int oxygen, int alert) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.hRate = hRate;
        this.oxygen = oxygen;
        this.alert = alert;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPregnancy_id() {
        return pregnancy_id;
    }

    public void setPregnancy_id(int pregnancy_id) {
        this.pregnancy_id = pregnancy_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int gethRate() {
        return hRate;
    }

    public void sethRate(int hRate) {
        this.hRate = hRate;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public int getAlert() {
        return alert;
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }
}
