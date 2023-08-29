package budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Budget {
    String yearMonth;
    int amount;

    public Budget(String yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    double overlappingAmount(Period period) {
        return dailyAmount() * period.getOverlappingDays(createPeriod());
    }

    private Period createPeriod() {
        return new Period(firstDay(), lastDay());
    }

    private LocalDate firstDay() {
        return getYearMonthInstance().atDay(1);
    }

    private LocalDate lastDay() {
        return getYearMonthInstance().atEndOfMonth();
    }

    private double dailyAmount() {
        return (double) this.amount / getYearMonthInstance().lengthOfMonth();
    }

    private YearMonth getYearMonthInstance() {
        return YearMonth.parse(this.yearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
    }
}
