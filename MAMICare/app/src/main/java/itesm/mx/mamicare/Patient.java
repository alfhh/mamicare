package itesm.mx.mamicare;

// Licencia GPL 3.0
// Autores: Alfredo Hinojosa, Emilio Flores, Sergio Cordero
// Profesora: Martha Sordia, Director: Mario de la Fuente

/**
 * Created by Alfredo Hinojosa on 10/25/2015.
 */
class Patient {

    private int id;
    private String name;
    private String birthday;
    private String address;
    private String lastCheck;
    private String photo_path;

    Patient(String name, String address, String lastC, String bday, String photo) {
        this.name = name;
        this.address = address;
        this.lastCheck = lastC;
        this.birthday = bday;
        this.photo_path = photo;
    }

    Patient(int id, String name, String address, String lastC, String bday, String photo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lastCheck = lastC;
        this.birthday = bday;
        this.photo_path = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(String lastCheck) {
        this.lastCheck = lastCheck;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
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

}
