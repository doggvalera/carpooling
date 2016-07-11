package lv.ctco.Entities;

import javax.persistence.*;


@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @Embedded
    private Coordinate coordinate;
    @Embedded
    private DateTimeRange date;
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

    public DateTimeRange getDate() {
        return date;
    }

    public void setDate(DateTimeRange date) {
        this.date = date;
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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
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

