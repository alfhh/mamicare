package itesm.mx.mamicare;

/**
 * Created by Alfredo Hinojosa on 10/25/2015.
 */
class Patient {

    private int id;
    private String name;
    private String birthday;
    private String address;
    private String pregnancyWeek;
    private String lastCheck;
    private String photo_path;

    Patient(String name, String week, String photo, String lastC) {
        this.name = name;
        this.pregnancyWeek = week;
        this.photo_path = photo;
        this.lastCheck = lastC;
    }

    Patient(int id, String name, String week, String photo, String lastC) {
        this.id = id;
        this.name = name;
        this.pregnancyWeek = week;
        this.photo_path = photo;
        this.lastCheck = lastC;
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

    public String getPregnancyWeek() {
        return pregnancyWeek;
    }

    public void setPregnancyWeek(String pregnancyWeek) {
        this.pregnancyWeek = pregnancyWeek;
    }
}
