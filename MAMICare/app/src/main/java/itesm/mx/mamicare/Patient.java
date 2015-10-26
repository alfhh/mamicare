package itesm.mx.mamicare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfredo Hinojosa on 10/25/2015.
 */
public class Patient {

    private String name;
    private String birthday;
    private String address;
    private int pregnancyWeek;
    private int photoID;

    public Patient(String name, String address, int pregnancyWeek, int photoID) {
        this.name = name;
        this.address = address;
        this.pregnancyWeek = pregnancyWeek;
    }

    private List<Patient> pacientes;

    private void initializeData(){
        pacientes = new ArrayList<>();
        pacientes.add(new Patient("Rosa Jimenez", "Santiago NL", 16, R.drawable.photorosa));
        pacientes.add(new Patient("Brenda Hernandez", "Santiago NL", 20, R.drawable.photorosa));
        pacientes.add(new Patient("Teresa Ramirez", "Santiago NL", 4, R.drawable.photorosa));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPregnancyWeek() {
        return pregnancyWeek;
    }

    public void setPregnancyWeek(int pregnancyWeek) {
        this.pregnancyWeek = pregnancyWeek;
    }
}
