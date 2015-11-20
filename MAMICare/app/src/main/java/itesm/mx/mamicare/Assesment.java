package itesm.mx.mamicare;

/**
 * Created by SergioJesúsCorderoBa on 11/20/2015.
 */
public class Assesment {
    private int id;
    private int pregnancy_id;
    private String start_date;
    private String end_date;
    private int blood_preassure;

    public Assesment(int id, int pregnancy_id, String start_date, String end_date, int blood_preassure) {
        this.id = id;
        this.pregnancy_id = pregnancy_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.blood_preassure = blood_preassure;
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

    public int getBlood_preassure() {
        return blood_preassure;
    }

    public void setBlood_preassure(int blood_preassure) {
        this.blood_preassure = blood_preassure;
    }
}
