package lv.ctco.entities;

import javax.persistence.*;


@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "latitudeFrom")),
            @AttributeOverride(name = "longitude", column = @Column(name = "longitudeFrom")),
    })
    private Coordinate coordinateFrom;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "latitudeTo")),
            @AttributeOverride(name = "longitude", column = @Column(name = "longitudeTo")),
    })
    private Coordinate coordinateTo;

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

    public Coordinate getCoordinateFrom() {
        return coordinateFrom;
    }

    public void setCoordinateFrom(Coordinate coordinateFrom) {
        this.coordinateFrom = coordinateFrom;
    }

    public Coordinate getCoordinateTo() {
        return coordinateTo;
    }

    public void setCoordinateTo(Coordinate coordinateTo) {
        this.coordinateTo = coordinateTo;
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

