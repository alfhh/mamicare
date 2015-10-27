package itesm.mx.mamicare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfredo Hinojosa on 10/25/2015.
 */
class Patient {

    private String name;
    private String birthday;
    private String address;
    private String pregnancyWeek;
    private String lastCheck;
    private int photoID;

    Patient(String name, String week, int photo, String lastC) {
        this.name = name;
        this.pregnancyWeek = week;
        this.photoID = photo;
        this.lastCheck = lastC;
    }

    public String getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(String lastCheck) {
        this.lastCheck = lastCheck;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
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
