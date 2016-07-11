package lv.ctco.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ride")
public class Ride {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;
    @OneToOne
    private Offer offer;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requestList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }
}
