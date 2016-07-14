package lv.ctco.entities;

import javax.persistence.*;


@Entity
@Table(name = "request")
public class Request {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @Embedded
    private DateTimeRange date;
    @Column(name = "RADIUS")
    private double radius;

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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Coordinate getCoordinateTo() {
        return coordinateTo;
    }

    public void setCoordinateTo(Coordinate coordinateTo) {
        this.coordinateTo = coordinateTo;
    }

    public Coordinate getCoordinateFrom() {
        return coordinateFrom;
    }

    public void setCoordinateFrom(Coordinate coordinateFrom) {
        this.coordinateFrom = coordinateFrom;
    }
}