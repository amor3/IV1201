package se.kth.integration;

import java.util.Date;

/**
 *
 * @author AMore
 */
public class AvailabilityDTO {
    private Date from;
    private Date to;

    public AvailabilityDTO(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
    
    
}
