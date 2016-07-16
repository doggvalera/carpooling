package lv.ctco.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Request> requests = new ArrayList<>();
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
    @Column(name = "DESCRIPTION")
    private String carDescription;
    @Column(name = "PASSENGERS")
    private int passengersAmount;

    public List<Request> getRequests() {
        return requests;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateTimeRange getDate() {
        return date;
    }

    public void setDate(DateTimeRange date) {
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (passengersAmount != offer.passengersAmount) return false;
        if (carDescription != null ? !carDescription.equals(offer.carDescription) : offer.carDescription != null)
            return false;
        if (coordinateFrom != null ? !coordinateFrom.equals(offer.coordinateFrom) : offer.coordinateFrom != null)
            return false;
        if (coordinateTo != null ? !coordinateTo.equals(offer.coordinateTo) : offer.coordinateTo != null) return false;
        if (date != null ? !date.equals(offer.date) : offer.date != null) return false;
        if (requests != null ? !requests.equals(offer.requests) : offer.requests != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (requests != null ? requests.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (coordinateFrom != null ? coordinateFrom.hashCode() : 0);
        result = 31 * result + (coordinateTo != null ? coordinateTo.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (carDescription != null ? carDescription.hashCode() : 0);
        result = 31 * result + passengersAmount;
        return result;
    }

    public void add(Request request) {
        requests.add(request);
    }
}

