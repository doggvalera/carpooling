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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (Double.compare(request.radius, radius) != 0) return false;
        if (coordinateFrom != null ? !coordinateFrom.equals(request.coordinateFrom) : request.coordinateFrom != null)
            return false;
        if (coordinateTo != null ? !coordinateTo.equals(request.coordinateTo) : request.coordinateTo != null)
            return false;
        if (date != null ? !date.equals(request.date) : request.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        temp = Double.doubleToLongBits(radius);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (coordinateFrom != null ? coordinateFrom.hashCode() : 0);
        result = 31 * result + (coordinateTo != null ? coordinateTo.hashCode() : 0);
        return result;
    }
}