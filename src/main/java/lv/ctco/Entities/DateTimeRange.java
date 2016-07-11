package lv.ctco.Entities;

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
}
