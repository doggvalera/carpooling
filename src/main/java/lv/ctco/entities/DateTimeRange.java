package lv.ctco.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class DateTimeRange {

    @Column(name = "START_DATE")
    private LocalDateTime earliestDeparture;
    @Column(name = "END_DATE")
    private LocalDateTime latestDeparture;

    public boolean overlaps() {

        return true;
    }

    public LocalDateTime getEarliestDeparture() {
        return earliestDeparture;
    }

    public void setEarliestDeparture(LocalDateTime earliestDeparture) {
        this.earliestDeparture = earliestDeparture;
    }


    public LocalDateTime getLatestDeparture() {
        return latestDeparture;
    }

    public void setLatestDeparture(LocalDateTime latestDeparture) {
        this.latestDeparture = latestDeparture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateTimeRange that = (DateTimeRange) o;

        if (earliestDeparture != null ? !earliestDeparture.equals(that.earliestDeparture) : that.earliestDeparture != null)
            return false;
        if (latestDeparture != null ? !latestDeparture.equals(that.latestDeparture) : that.latestDeparture != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = earliestDeparture != null ? earliestDeparture.hashCode() : 0;
        result = 31 * result + (latestDeparture != null ? latestDeparture.hashCode() : 0);
        return result;
    }
}
