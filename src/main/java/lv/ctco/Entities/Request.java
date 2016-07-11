package lv.ctco.Entities;

import javax.persistence.*;


@Entity
@Table(name = "request")
public class Request {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;
    @Column(name = "USER")
    private User user;
    @Column(name = "DATE")
    private DateTimeRange date;
    @Column(name = "RADIUS")
    private double radius;
    @Column(name = "ORIGIN")
    private Coordinate origin;
    @Column(name = "DESTINATION")
    private Coordinate destination;

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

    public Coordinate getOrigin() {
        return origin;
    }

    public void setOrigin(Coordinate origin) {
        this.origin = origin;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public void setDestination(Coordinate destination) {
        this.destination = destination;
    }
}