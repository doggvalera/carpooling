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
    @ManyToOne
    private User user;
    private DataTimeRange startTime;



}
