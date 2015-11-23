package itesm.mx.mamicare_db_testing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfredo Hinojosa on 10/25/2015.
 */
class Patient {

    private int id;
    private String name;
    private String birthday;
    private String pregnancyWeek;
    private String lastCheck;
    private String photo_path;

    public Patient(String name, String birthday, String lastCheck, String photo_path) {
        this.name = name;
        this.birthday = birthday;
        this.lastCheck = lastCheck;
        this.photo_path = photo_path;
    }

    public Patient(int id, String name, String birthday, String lastCheck, String photo_path) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.lastCheck = lastCheck;
        this.photo_path = photo_path;
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

    public void setPhotoID(String photo_path) {
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

}
