package congress.dwp.libraryservices.model.dto;

import java.time.LocalDate;

public class DateRangeDTO {
    private LocalDate start;
    private LocalDate end;

    public DateRangeDTO() {}

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public DateRangeDTO(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
}
