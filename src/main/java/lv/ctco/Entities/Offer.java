
package lv.ctco.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "offers")
public class Offers {

    @Id
    @Column(name ="ID")
    @GeneratedValue
    private int id;
    @Column(name = "DRIVER")
    private User user;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "TIME")
    private String time;
    @Column(name = "DELAY")
    private int delayTime;
    @Column(name = "DESCRIPTION")
    private String carDescription;
    @Column(name = "PASSENGERS")
    private int passengersAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delay) {
        this.delayTime = delay;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public int getPassengersAmount() {
        return passengersAmount;
    }

    public void setPassengersAmount(int passengersAmount) {
        this.passengersAmount = passengersAmount;
    }
}

