package lv.ctco.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class DateTimeRange {

    @Column(name = "START_DATE")
    private LocalDateTime erliestDeparture;
    @Column(name = "END_DATE")
    private LocalDateTime lastesDeparture;

    public boolean overlaps() {

        return true;
    }

}
