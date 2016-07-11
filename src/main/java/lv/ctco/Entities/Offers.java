package lv.ctco.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "offers")
public class Offers {

    private String title;
    private int id;
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
