package congress.dwp.libraryservices.model.dto;

import java.util.Date;

public class DateRangeDTO {
    private Date start;
    private Date end;

    public DateRangeDTO() {}

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public DateRangeDTO(Date start, Date end) {
        this.start = start;
        this.end = end;
    }
}
